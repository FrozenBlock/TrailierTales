package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class LumibloomBlock extends MultifaceBlock implements BonemealableBlock {
	public static final MapCodec<LumibloomBlock> CODEC = simpleCodec(LumibloomBlock::new);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final int MAX_AGE = 2;

	private final MultifaceSpreader spreader = new MultifaceSpreader(this);

	@Override
	public @NotNull MapCodec<LumibloomBlock> codec() {
		return CODEC;
	}

	public LumibloomBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}

	@Override
	protected void randomTick(BlockState state, @NotNull ServerLevel world, BlockPos pos, RandomSource random) {
		if (!this.isMaxAge(state) && world.getRawBrightness(pos, 0) >= 9 && random.nextInt(5) == 0) {
			this.grow(world, pos, state);
		}
	}

	private boolean isMaxAge(@NotNull BlockState state) {
		return state.getValue(AGE) >= MAX_AGE;
	}

	private void grow(@NotNull Level level, BlockPos pos, @NotNull BlockState state) {
		level.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), UPDATE_CLIENTS);
	}

	@Override
	protected boolean canBeReplaced(BlockState state, @NotNull BlockPlaceContext context) {
		return context.getItemInHand().is(this.asItem()) && super.canBeReplaced(state, context);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return !isMaxAge(state) || Direction.stream().anyMatch(direction -> this.spreader.canSpreadInAnyDirection(state, world, pos, direction.getOpposite()));
	}

	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		if (!this.isMaxAge(state)) {
			this.grow(world, pos, state);
		} else {
			this.spreader.spreadFromRandomFaceTowardRandomDirection(state, world, pos, random);
		}
	}

	@Override
	public @NotNull MultifaceSpreader getSpreader() {
		return this.spreader;
	}
}
