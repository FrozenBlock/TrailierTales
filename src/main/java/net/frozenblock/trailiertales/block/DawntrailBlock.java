package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DawntrailBlock extends MultifaceBlock implements BonemealableBlock {
	public static final MapCodec<DawntrailBlock> CODEC = simpleCodec(DawntrailBlock::new);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final int MAX_AGE = 2;

	private final MultifaceSpreader spreader = new LumibloomSpreader(this);

	@Override
	public @NotNull MapCodec<DawntrailBlock> codec() {
		return CODEC;
	}

	public DawntrailBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}

	public static boolean canAttachTo(BlockGetter world, @NotNull Direction direction, BlockPos pos, @NotNull BlockState state) {
		return MultifaceBlock.canAttachTo(world, direction, pos, state) || state.is(Blocks.FARMLAND);
	}

	@Override
	protected @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (!hasAnyFace(state)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			return hasFace(state, direction) && !canAttachTo(world, direction, neighborPos, neighborState) ? removeFace(state, getFaceProperty(direction)) : state;
		}
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		boolean bl = false;
		for (Direction direction : DIRECTIONS) {
			if (hasFace(state, direction)) {
				BlockPos blockPos = pos.relative(direction);
				if (!canAttachTo(world, direction, blockPos, world.getBlockState(blockPos))) return false;
				bl = true;
			}
		}
		return bl;
	}

	@Override
	public boolean isValidStateForPlacement(BlockGetter view, @NotNull BlockState state, BlockPos pos, Direction dir) {
		if (!state.getFluidState().isEmpty()) {
			return false;
		}
		if (this.isFaceSupported(dir) && (!state.is(this) || !hasFace(state, dir))) {
			BlockPos blockPos = pos.relative(dir);
			return canAttachTo(view, dir, blockPos, view.getBlockState(blockPos));
		} else {
			return false;
		}
	}

	public static boolean isMaxAge(@NotNull BlockState state) {
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
		if (!isMaxAge(state)) {
			this.grow(world, pos, state);
		} else {
			this.spreader.spreadFromRandomFaceTowardRandomDirection(state, world, pos, random);
		}
	}

	@Override
	@NotNull
	public ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (level instanceof ServerLevel && isMaxAge(state) && stack.is(Items.SHEARS)) {
			shear(level, pos, state, player);
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			return ItemInteractionResult.SUCCESS;
		} else {
			return super.useItemOn(stack, state, level, pos, player, hand, hit);
		}
	}

	public static void shear(@NotNull Level level, BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
		level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
		ItemStack seeds = new ItemStack(RegisterItems.DAWNTRAIL_SEEDS);
		seeds.setCount(availableFaces(state).size());
		popResource(level, pos, seeds);
		level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
		level.playSound(null, pos, RegisterSounds.DAWNTRAIL_PICK, SoundSource.BLOCKS, 1F, 0.95F + (level.random.nextFloat() * 0.1F));
		level.gameEvent(player, GameEvent.SHEAR, pos);
	}

	@Override
	public @NotNull MultifaceSpreader getSpreader() {
		return this.spreader;
	}

	public static class LumibloomSpreader extends MultifaceSpreader {
		public LumibloomSpreader(@NotNull DawntrailBlock block) {
			super(new DefaultSpreaderConfig(block));
		}
	}
}
