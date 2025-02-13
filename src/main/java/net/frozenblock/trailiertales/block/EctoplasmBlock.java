package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.lib.block.api.shape.FrozenShapes;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class EctoplasmBlock extends HalfTransparentBlock {
	public static final float APPARITION_COLLISION_FROM_SIDE = 0.25F;
	public static final double GRAVITY_SLOWDOWN = 0.2D;
	public static final MapCodec<EctoplasmBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		propertiesCodec()
	).apply(instance, EctoplasmBlock::new));

	public EctoplasmBlock(@NotNull Properties properties) {
		super(properties);
	}

	@NotNull
	@Override
	protected MapCodec<? extends EctoplasmBlock> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		VoxelShape shape = Shapes.empty();

		if (collisionContext instanceof EntityCollisionContext entityCollisionContext) {
			if (entityCollisionContext.getEntity() instanceof Apparition) {
				for (Direction direction : Direction.values()) {
					if (!blockGetter.getBlockState(blockPos.relative(direction)).is(this)) {
						shape = Shapes.or(shape, FrozenShapes.makePlaneFromDirection(direction, APPARITION_COLLISION_FROM_SIDE));
					}
				}
			}
		}

		return shape;
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
		return true;
	}

	@Override
	protected int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return 0;
	}
}
