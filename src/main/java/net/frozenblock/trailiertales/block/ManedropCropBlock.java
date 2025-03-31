/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManedropCropBlock extends DoublePlantBlock implements BonemealableBlock {
	public static final MapCodec<ManedropCropBlock> CODEC = simpleCodec(ManedropCropBlock::new);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	public static final int MAX_AGE = 3;
	public static final int DOUBLE_PLANT_AGE_INTERSECTION = 2;
	private static final VoxelShape[] UPPER_SHAPE_BY_AGE = new VoxelShape[]{
		Block.box(3, 0, 3, 13, 11, 13),
		Block.box(2, 0, 2, 14, 16, 14)
	};
	private static final VoxelShape[] LOWER_SHAPE_BY_AGE = new VoxelShape[]{
		Block.box(6, -1, 6, 10, 5, 10),
		Block.box(4, 0, 4, 12, 10, 13),
		Block.box(3, 0, 3, 13, 16, 13),
		Block.box(2, 0, 2, 14, 16, 14)
	};

	@Override
	public @NotNull MapCodec<ManedropCropBlock> codec() {
		return CODEC;
	}

	public ManedropCropBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState();
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return state.getValue(HALF) == DoubleBlockHalf.UPPER
			? UPPER_SHAPE_BY_AGE[Math.min(Math.abs(MAX_AGE - (state.getValue(AGE) + 1)), UPPER_SHAPE_BY_AGE.length - 1)]
			: LOWER_SHAPE_BY_AGE[state.getValue(AGE)];
	}

	@Override
	public @NotNull BlockState updateShape(@NotNull BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (isDouble(state.getValue(AGE))) {
			return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
		} else {
			return state.canSurvive(world, pos) ? state : Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return (!isLower(state) || sufficientLight(world, pos)) && super.canSurvive(state, world, pos);
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState floor, BlockGetter world, BlockPos pos) {
		return floor.is(Blocks.FARMLAND);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(AGE);
		super.createBlockStateDefinition(builder);
	}

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (entity instanceof Ravager && world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			world.destroyBlock(pos, true, entity);
		}

		super.entityInside(state, world, pos, entity);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return false;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
	}

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state) {
		return state.getValue(HALF) == DoubleBlockHalf.LOWER && !this.isMaxAge(state);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, @NotNull RandomSource random) {
		float f = CropBlock.getGrowthSpeed(this, world, pos);
		boolean bl = random.nextInt((int)(25F / f) + 1) == 0;
		if (bl) {
			this.grow(world, state, pos, 1);
		}
	}

	private void grow(ServerLevel world, @NotNull BlockState state, BlockPos pos, int amount) {
		int i = Math.min(state.getValue(AGE) + amount, MAX_AGE);
		if (this.canGrow(world, pos, state, i)) {
			BlockState blockState = state.setValue(AGE, i);
			world.setBlock(pos, blockState, UPDATE_CLIENTS);
			if (isDouble(i)) {
				world.setBlock(pos.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER), UPDATE_ALL);
			}
		}
	}

	private static boolean canGrowInto(@NotNull LevelReader world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		return blockState.isAir() || blockState.is(TTBlocks.MANEDROP_CROP);
	}

	private static boolean sufficientLight(LevelReader world, BlockPos pos) {
		return CropBlock.hasSufficientLight(world, pos);
	}

	private static boolean isLower(@NotNull BlockState state) {
		return state.is(TTBlocks.MANEDROP_CROP) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
	}

	private static boolean isDouble(int age) {
		return age >= DOUBLE_PLANT_AGE_INTERSECTION;
	}

	private boolean canGrow(LevelReader world, BlockPos pos, BlockState state, int age) {
		return !this.isMaxAge(state) && sufficientLight(world, pos) && (!isDouble(age) || canGrowInto(world, pos.above()));
	}

	private boolean isMaxAge(@NotNull BlockState state) {
		return state.getValue(AGE) >= MAX_AGE;
	}

	@Nullable
	private PosAndState getLowerHalf(LevelReader world, BlockPos pos, BlockState state) {
		if (isLower(state)) {
			return new PosAndState(pos, state);
		} else {
			BlockPos blockPos = pos.below();
			BlockState blockState = world.getBlockState(blockPos);
			return isLower(blockState) ? new PosAndState(blockPos, blockState) : null;
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		PosAndState posAndState = this.getLowerHalf(world, pos, state);
		return posAndState != null && this.canGrow(world, posAndState.pos, state, posAndState.state.getValue(AGE) + 1);
	}

	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		PosAndState posAndState = this.getLowerHalf(world, pos, state);
		if (posAndState != null) {
			this.grow(world, posAndState.state, posAndState.pos, 1);
		}
	}

	record PosAndState(BlockPos pos, BlockState state) {
	}
}
