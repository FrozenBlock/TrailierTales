package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.trailiertales.block.entity.SurveyorBlockEntity;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SurveyorBlock extends BaseEntityBlock {
	public static final MapCodec<SurveyorBlock> CODEC = RecordCodecBuilder.mapCodec(
		color -> color.group(propertiesCodec()).apply(color, SurveyorBlock::new)
	);
	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	@Override
	public @NotNull MapCodec<SurveyorBlock> codec() {
		return CODEC;
	}

	public SurveyorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH).setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED);
	}

	@Override
	protected @NotNull BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected @NotNull BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected @NotNull RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SurveyorBlockEntity(pos, state);
	}

	protected void updateNeighborsInFront(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
		Direction direction = state.getValue(FACING);
		BlockPos blockPos = pos.relative(direction.getOpposite());
		level.neighborChanged(blockPos, this, pos);
		level.updateNeighborsAtExceptFromFacing(blockPos, this, direction);
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	protected int getDirectSignal(@NotNull BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return state.getSignal(level, pos, direction);
	}

	@Override
	protected int getSignal(@NotNull BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return state.getValue(POWERED) && state.getValue(FACING) == direction ? 15 : 0;
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, @NotNull Level level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof SurveyorBlockEntity surveyorBlockEntity) {
			return state.getValue(POWERED) ? surveyorBlockEntity.getLastDetectionPower() : 0;
		} else {
			return 0;
		}
	}

	@Override
	protected void onRemove(@NotNull BlockState state, Level level, BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		if (!state.is(newState.getBlock())) {
			if (!level.isClientSide && state.getValue(POWERED)) {
				this.updateNeighborsInFront(level, pos, state.setValue(POWERED, false));
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite().getOpposite());
	}

	public static void updatePower(@NotNull Level level, BlockPos pos, @NotNull BlockState state, int lastDetectionPower, boolean updateNeighbors) {
		boolean shouldPower = lastDetectionPower > 0;
		if (shouldPower != state.getValue(POWERED)) {
			level.setBlockAndUpdate(pos, state.setValue(POWERED, shouldPower));
		}
		if (updateNeighbors && state.getBlock() instanceof SurveyorBlock surveyorBlock) {
			surveyorBlock.updateNeighborsInFront(level, pos, state);
		}
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return level instanceof ServerLevel serverLevel
			? createTickerHelper(blockEntityType, RegisterBlockEntities.SURVEYOR, (unusedWorld, pos, statex, surveyor) -> surveyor.tickServer(serverLevel, pos, statex))
			: null;
	}
}
