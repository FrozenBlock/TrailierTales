package net.frozenblock.trailiertales.block;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.frozenblock.trailiertales.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
	public static final IntegerProperty SPREAD_AGE = RegisterProperties.SPREAD_AGE;
	public static final int MAX_AGE = 2;

	private final MultifaceSpreader spreader = new LumibloomSpreader(this);

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
		builder.add(AGE, SPREAD_AGE);
	}

	@Override
	protected void randomTick(BlockState state, @NotNull ServerLevel world, BlockPos pos, RandomSource random) {
		if (world.getRawBrightness(pos, 0) >= 9 && random.nextInt(5) == 0) {
			if (!this.isMaxAge(state)) {
				this.grow(world, pos, state);
			} else if (!this.isMaxSpreadAge(state) && random.nextInt(3) == 0) {
				this.spreader.spreadFromRandomFaceTowardRandomDirection(state, world, pos, random);
			}
		}
	}

	private boolean isMaxAge(@NotNull BlockState state) {
		return state.getValue(AGE) >= MAX_AGE;
	}

	private boolean isMaxSpreadAge(@NotNull BlockState state) {
		return state.getValue(SPREAD_AGE) >= RegisterProperties.MAX_SPREAD_AGE;
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
		if (!this.isMaxAge(state)) return true;
		return !this.isMaxSpreadAge(state) && Direction.stream().anyMatch(direction -> this.spreader.canSpreadInAnyDirection(state, world, pos, direction.getOpposite()));
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

	public static class LumibloomSpreader extends MultifaceSpreader {
		private final LumibloomSpreaderConfig lumibloomConfig;

		public LumibloomSpreader(@NotNull LumibloomBlock block) {
			super(new LumibloomSpreaderConfig(block));
			this.lumibloomConfig = (LumibloomSpreaderConfig) this.config;
		}

		@VisibleForTesting
		@Override
		public @NotNull Optional<SpreadPos> spreadFromFaceTowardDirection(
			BlockState state, LevelAccessor world, BlockPos pos, Direction facing, Direction direction, boolean postProcess
		) {
			return this.getSpreadFromFaceTowardDirection(state, world, pos, facing, direction, this.config::canSpreadInto)
				.flatMap(placement -> this.spreadToFace(world, state, placement, postProcess));
		}

		public Optional<MultifaceSpreader.SpreadPos> spreadToFace(@NotNull LevelAccessor world, BlockState state, MultifaceSpreader.@NotNull SpreadPos placement, boolean postProcess) {
			BlockState blockState = world.getBlockState(placement.pos());
			return this.lumibloomConfig.placeBlock(world, state, placement, blockState, postProcess) ? Optional.of(placement) : Optional.empty();
		}

		public static class LumibloomSpreaderConfig extends DefaultSpreaderConfig {
			public LumibloomSpreaderConfig(MultifaceBlock block) {
				super(block);
			}

			public boolean placeBlock(LevelAccessor world, BlockState originalState, MultifaceSpreader.@NotNull SpreadPos placement, BlockState state, boolean postProcess) {
				BlockState blockState = this.getStateForPlacement(state, world, placement.pos(), placement.face());
				if (blockState != null) {
					if (postProcess) {
						world.getChunk(placement.pos()).markPosForPostprocessing(placement.pos());
					}

					if (state.getBlock() != this.block) {
						blockState = blockState.setValue(SPREAD_AGE, Math.min(originalState.getValue(SPREAD_AGE) + 1, RegisterProperties.MAX_SPREAD_AGE));
					}

					return world.setBlock(placement.pos(), blockState, 2);
				} else {
					return false;
				}
			}
		}
	}
}
