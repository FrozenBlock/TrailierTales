/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
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
	public MapCodec<ManedropCropBlock> codec() {
		return CODEC;
	}

	public ManedropCropBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(HALF) == DoubleBlockHalf.UPPER
			? UPPER_SHAPE_BY_AGE[Math.min(Math.abs(MAX_AGE - (state.getValue(AGE) + 1)), UPPER_SHAPE_BY_AGE.length - 1)]
			: LOWER_SHAPE_BY_AGE[state.getValue(AGE)];
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess tickAccess,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (isDouble(state.getValue(AGE))) return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
		return state.canSurvive(level, pos) ? state : Blocks.AIR.defaultBlockState();
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return (!isLower(state) || sufficientLight(level, pos)) && super.canSurvive(state, level, pos);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(Blocks.FARMLAND);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
		super.createBlockStateDefinition(builder);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier applier, boolean bl) {
		if (level instanceof ServerLevel server && entity instanceof Ravager && server.getGameRules().get(GameRules.MOB_GRIEFING)) level.destroyBlock(pos, true, entity);
		super.entityInside(state, level, pos, entity, applier, bl);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return false;
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(HALF) == DoubleBlockHalf.LOWER && !this.isMaxAge(state);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		float growthSpeed = CropBlock.getGrowthSpeed(this, level, pos);
		if (random.nextInt((int)(25F / growthSpeed) + 1) == 0) this.grow(level, state, pos, 1);
	}

	private void grow(ServerLevel level, BlockState state, BlockPos pos, int amount) {
		final int grownAge = Math.min(state.getValue(AGE) + amount, MAX_AGE);
		if (!this.canGrow(level, pos, state, grownAge)) return;

		final BlockState newState = state.setValue(AGE, grownAge);
		level.setBlock(pos, newState, UPDATE_CLIENTS);
		if (isDouble(grownAge)) level.setBlock(pos.above(), newState.setValue(HALF, DoubleBlockHalf.UPPER), UPDATE_ALL);
	}

	private static boolean canGrowInto(LevelReader level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		return state.isAir() || state.is(TTBlocks.MANEDROP_CROP);
	}

	private static boolean sufficientLight(LevelReader level, BlockPos pos) {
		return CropBlock.hasSufficientLight(level, pos);
	}

	private static boolean isLower(BlockState state) {
		return state.is(TTBlocks.MANEDROP_CROP) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
	}

	private static boolean isDouble(int age) {
		return age >= DOUBLE_PLANT_AGE_INTERSECTION;
	}

	private boolean canGrow(LevelReader level, BlockPos pos, BlockState state, int age) {
		return !this.isMaxAge(state) && sufficientLight(level, pos) && (!isDouble(age) || canGrowInto(level, pos.above()));
	}

	private boolean isMaxAge(BlockState state) {
		return state.getValue(AGE) >= MAX_AGE;
	}

	@Nullable
	private PosAndState getLowerHalf(LevelReader level, BlockPos pos, BlockState state) {
		if (isLower(state)) return new PosAndState(pos, state);
		final BlockPos belowPos = pos.below();
		final BlockState belowState = level.getBlockState(belowPos);
		return isLower(belowState) ? new PosAndState(belowPos, belowState) : null;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		final PosAndState lowerHalf = this.getLowerHalf(level, pos, state);
		return lowerHalf != null && this.canGrow(level, lowerHalf.pos, state, lowerHalf.state.getValue(AGE) + 1);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		final PosAndState posAndState = this.getLowerHalf(level, pos, state);
		if (posAndState != null) this.grow(level, posAndState.state, posAndState.pos, 1);
	}

	record PosAndState(BlockPos pos, BlockState state) {
	}
}
