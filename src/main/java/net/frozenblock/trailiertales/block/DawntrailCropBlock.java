package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DawntrailCropBlock extends CropBlock {
	public static final MapCodec<DawntrailCropBlock> CODEC = simpleCodec(DawntrailCropBlock::new);
	public static final int MAX_AGE = 4;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
		Block.box(5D, 0D, 5D, 11D, 6D, 11D),
		Block.box(4D, 0D, 4D, 12D, 6D, 12D),
		Block.box(2D, 0D, 2D, 14D, 8D, 14D),
		Block.box(0D, 0D, 0D, 16D, 4D, 16D),
	};
	private static final int BONEMEAL_INCREASE = 1;

	@Override
	@NotNull
	public MapCodec<DawntrailCropBlock> codec() {
		return CODEC;
	}

	public DawntrailCropBlock(Properties settings) {
		super(settings);
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	@NotNull
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[this.getAge(state)];
	}

	@Override
	@NotNull
	protected IntegerProperty getAgeProperty() {
		return AGE;
	}

	@Override
	public int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	@NotNull
	protected ItemLike getBaseSeedId() {
		return TTItems.DAWNTRAIL_SEEDS;
	}

	@Override
	@NotNull
	public BlockState getStateForAge(int age) {
		return age == MAX_AGE ?
			TTBlocks.DAWNTRAIL.defaultBlockState().setValue(DawntrailBlock.getFaceProperty(Direction.DOWN), true).setValue(DawntrailBlock.AGE, DawntrailBlock.MAX_AGE)
			:
			super.getStateForAge(age);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
		if (random.nextInt(3) != 0) {
			super.randomTick(state, level, pos, random);
		}
	}

	@Override
	protected int getBonemealAgeIncrease(Level level) {
		return BONEMEAL_INCREASE;
	}
}
