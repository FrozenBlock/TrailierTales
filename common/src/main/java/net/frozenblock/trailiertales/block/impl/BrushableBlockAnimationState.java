package net.frozenblock.trailiertales.block.impl;

import net.frozenblock.trailiertales.registry.TTAttachmentTypes;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jspecify.annotations.Nullable;

// TODO: only apply sync if brushable block is rebrushed
public final class BrushableBlockAnimationState {
	public static final StreamCodec<FriendlyByteBuf, BrushableBlockAnimationState> STREAM_CODEC = StreamCodec.composite(
		InterpolatingValue.STREAM_CODEC, state -> state.x,
		InterpolatingValue.STREAM_CODEC, state -> state.y,
		InterpolatingValue.STREAM_CODEC, state -> state.z,
		InterpolatingValue.STREAM_CODEC, state -> state.scale,
		ByteBufCodecs.FLOAT, state -> state.rotation0,
		ByteBufCodecs.FLOAT, state -> state.rotation,
		BrushableBlockAnimationState::new
	);
	private final InterpolatingValue x;
	private final InterpolatingValue y;
	private final InterpolatingValue z;
	private final InterpolatingValue scale;
	private float rotation0;
	private float rotation;

	private BrushableBlockAnimationState(InterpolatingValue x, InterpolatingValue y, InterpolatingValue z, InterpolatingValue scale, float rotation0, float rotation) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.scale = scale;
		this.rotation0 = rotation0;
		this.rotation = rotation;
	}

	private BrushableBlockAnimationState(InterpolatingValue x, InterpolatingValue y, InterpolatingValue z, InterpolatingValue scale) {
		this(x, y, z, scale, 0F, 0F);
	}

	public void tick(@Nullable Direction direction, int completionState) {
		this.rotation0 = this.rotation;
		if (direction != null) {
			float[] translation = translations(direction, completionState);
			this.x.setTarget(translation[0]);
			this.y.setTarget(translation[1]);
			this.z.setTarget(translation[2]);
			this.rotation = direction.getAxis() == Direction.Axis.X ? 90F : 0F;
		}

		this.scale.setTarget(Math.min(1F, completionState));
		this.scale.tick();
		this.x.tick();
		this.y.tick();
		this.z.tick();
	}

	public static void tick(BlockEntity blockEntity, BlockState blockState) {
		if (!(blockEntity instanceof BrushableBlockEntity brushableBlockEntity)) return;
		TTAttachmentTypes.BRUSHABLE_BLOCK_ANIMATION_STATE
			.getAttachedOrCreate(blockEntity, BrushableBlockAnimationState::create)
			.tick(brushableBlockEntity.getHitDirection(), blockState.getValueOrElse(BlockStateProperties.DUSTED, 1));
	}

	public float getX(float partialTicks) {
		return this.x.position(partialTicks);
	}

	public float getY(float partialTicks) {
		return this.y.position(partialTicks);
	}

	public float getZ(float partialTicks) {
		return this.z.position(partialTicks);
	}

	public float getRotation(float partialTicks) {
		return Mth.lerp(partialTicks, this.rotation0, this.rotation);
	}

	public float getScale(float partialTicks) {
		return this.scale.position(partialTicks);
	}

	public static BrushableBlockAnimationState create() {
		return new BrushableBlockAnimationState(
			new InterpolatingValue(0.5F),
			new InterpolatingValue(0F),
			new InterpolatingValue(0.5F),
			new InterpolatingValue(0F)
		);
	}

	private static float[] translations(Direction direction, int completionState) {
		final float[] xyzTranslations = new float[]{0.5F, 0F, 0.5F};
		final float offset = (float) completionState / 9F;
		switch (direction) {
			case EAST -> xyzTranslations[0] = 0.73F + offset;
			case WEST -> xyzTranslations[0] = 0.25F - offset;
			case UP -> xyzTranslations[1] = 0.25F + offset;
			case DOWN -> xyzTranslations[1] = -0.23F - offset;
			case NORTH -> xyzTranslations[2] = 0.25F - offset;
			case SOUTH -> xyzTranslations[2] = 0.73F + offset;
		}
		return xyzTranslations;
	}

	public static final class InterpolatingValue {
		private static final float STEP_SCALE = 0.2F;
		public static final float LENIENT_RANGE = 0.05F;
		private static final StreamCodec<FriendlyByteBuf, InterpolatingValue> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT, interpolatingValue -> interpolatingValue.previous,
			ByteBufCodecs.FLOAT, interpolatingValue -> interpolatingValue.current,
			ByteBufCodecs.FLOAT, interpolatingValue -> interpolatingValue.target,
			InterpolatingValue::new
		);
		private float previous;
		private float current;
		private float target;

		public InterpolatingValue(float previous, float current, float target) {
			this.previous = previous;
			this.current = current;
			this.target = target;
		}

		public InterpolatingValue(float initialValue) {
			this(initialValue, initialValue, initialValue);
		}

		public float position(float partialTicks) {
			return Mth.lerp(partialTicks, this.previous, this.current);
		}

		public void tick() {
			this.previous = this.current;
			this.current += (this.target - this.current) * STEP_SCALE;
			if (this.withinTargetRange(LENIENT_RANGE)) this.previous = this.current = this.target;
		}

		public void setTarget(float target) {
			this.target = target;
		}

		public boolean withinTargetRange(float lenience) {
			return (this.target - this.current) <= lenience;
		}
	}
}
