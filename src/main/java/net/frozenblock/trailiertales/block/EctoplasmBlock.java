package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class EctoplasmBlock extends HalfTransparentBlock {
	public static final VoxelShape COLLISION_SHAPE = Shapes.empty();
	public static final int LIGHT_BLOCK = 2;
	public static final double GRAVITY_SLOWDOWN = 0.4D;
	public static final MapCodec<EctoplasmBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		propertiesCodec()
	).apply(instance, EctoplasmBlock::new));

	public EctoplasmBlock(@NotNull Properties properties) {
		super(properties.pushReaction(PushReaction.DESTROY));
	}

	@NotNull
	@Override
	protected MapCodec<? extends EctoplasmBlock> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		return COLLISION_SHAPE;
	}

	@Override
	public int getLightBlock(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
		return LIGHT_BLOCK;
	}
}
