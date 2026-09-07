package net.frozenblock.trailiertales.block.impl.client;

import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jspecify.annotations.Nullable;

@ClientOnly
public final class BrushableBlockAnimationState {
	private static final float HORIZONTAL_DEFAULT = 0.5F;
	private static final float VERTICAL_DEFAULT = 0F;
	private static final float SCALE_DEFAULT = 0F;
	private final InterpolatingValue x;
	private final InterpolatingValue y;
	private final InterpolatingValue z;
	private final InterpolatingValue scale;
	private float rotation0;
	private float rotation;

	private BrushableBlockAnimationState(InterpolatingValue x, InterpolatingValue y, InterpolatingValue z, InterpolatingValue scale) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.scale = scale;
	}

	public void tick(@Nullable Direction direction, int completionState) {
		this.rotation0 = this.rotation;
		if (direction != null) {
			float[] translation = translations(direction, completionState);
			this.x.setTarget(translation[0], false);
			this.y.setTarget(translation[1], false);
			this.z.setTarget(translation[2], false);
			this.rotation = direction.getAxis() == Direction.Axis.X ? 90F : 0F;
		}

		this.scale.setTarget(Math.min(1F, completionState), false);
		this.scale.tick();
		this.x.tick();
		this.y.tick();
		this.z.tick();
	}

	public static void tick(@Nullable BlockEntity blockEntity, BlockState blockState) {
		if (!(blockEntity instanceof BrushableBlockEntity brushableBlockEntity) || !(blockEntity instanceof BrushableBlockEntityInterface blockEntityInterface)) return;
		blockEntityInterface.trailierTales$getAnimationState()
			.tick(brushableBlockEntity.getHitDirection(), blockState.getValueOrElse(BlockStateProperties.DUSTED, 1));
	}

	public void reset() {
		this.x.setTarget(HORIZONTAL_DEFAULT, true);
		this.y.setTarget(VERTICAL_DEFAULT, true);
		this.z.setTarget(HORIZONTAL_DEFAULT, true);
		this.scale.setTarget(SCALE_DEFAULT, true);
		this.rotation0 = this.rotation = 0F;
	}

	public static void reset(@Nullable BlockEntity blockEntity) {
		if (blockEntity instanceof BrushableBlockEntityInterface blockEntityInterface) blockEntityInterface.trailierTales$getAnimationState().reset();
	}

	@Nullable
	public static BrushableBlockAnimationState get(@Nullable BlockEntity blockEntity) {
		if ((blockEntity instanceof BrushableBlockEntityInterface blockEntityInterface)) return blockEntityInterface.trailierTales$getAnimationState();
		return null;
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
			new InterpolatingValue(HORIZONTAL_DEFAULT),
			new InterpolatingValue(VERTICAL_DEFAULT),
			new InterpolatingValue(HORIZONTAL_DEFAULT),
			new InterpolatingValue(SCALE_DEFAULT, 0.3F)
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
		private static final float DEFAULT_STEP_SCALE = 0.20F;
		private final float stepScale;
		private float previous;
		private float current;
		private float target;

		public InterpolatingValue(float stepScale, float previous, float current, float target) {
			this.stepScale = stepScale;
			this.previous = previous;
			this.current = current;
			this.target = target;
		}

		public InterpolatingValue(float initialValue, float stepScale) {
			this(stepScale, initialValue, initialValue, initialValue);
		}

		public InterpolatingValue(float initialValue) {
			this(DEFAULT_STEP_SCALE, initialValue, initialValue, initialValue);
		}

		public float position(float partialTicks) {
			return Mth.lerp(partialTicks, this.previous, this.current);
		}

		public void tick() {
			this.previous = this.current;
			this.current += (this.target - this.current) * this.stepScale;
		}

		public void setTarget(float target, boolean setAll) {
			this.target = target;
			if (setAll) this.previous = this.current = this.target;
		}
	}
}
