package net.frozenblock.trailiertales.registry;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class RegisterStructureProcessors {
	public static final float SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE = 0.85F;
	public static final ResourceKey<StructureProcessorList> SUSPICIOUS_BLOCK_TO_NORMAL_085 = createKey("suspicious_block_to_normal_085");
	public static final float SKULL_SMALL_ROTATE_CHANCE = 0.3F;
	public static final float SKULL_LARGE_ROTATE_CHANCE = 0.075F;
	public static final ResourceKey<StructureProcessorList> CATACOMBS_DEGRADATION = createKey("catacombs_degradation");

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);

		register(
			context,
			RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085,
			List.of(
				new RuleProcessor(
					List.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_GRAVEL, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_SAND, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.SAND.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_RED_SAND, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.RED_SAND.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_CLAY, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.CLAY.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_DIRT, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_DEGRADATION,
			ImmutableList.of(
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.SKELETON_SKULL, 0.55F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),

						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS.defaultBlockState()),

						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_SLAB, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_SLAB, 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB.defaultBlockState()),

						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILES.defaultBlockState()),

						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB.defaultBlockState()),

						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_WALL.defaultBlockState(), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL.defaultBlockState()
						),

						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState(), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST)
						),

						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(BlockStateProperties.HALF, Half.TOP), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(BlockStateProperties.HALF, Half.TOP)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST).setValue(BlockStateProperties.HALF, Half.TOP), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST).setValue(BlockStateProperties.HALF, Half.TOP)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH).setValue(BlockStateProperties.HALF, Half.TOP), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH).setValue(BlockStateProperties.HALF, Half.TOP)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST).setValue(BlockStateProperties.HALF, Half.TOP), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST).setValue(BlockStateProperties.HALF, Half.TOP)
						),

						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_GRAVEL, 0.425F),
							AlwaysTrueTest.INSTANCE, Blocks.TUFF.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_GRAVEL, 0.9225F),
							AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()
						),

						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.COBWEB, 0.65F),
							AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
						),

						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.CANDLE, 0.8F),
							AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.15F),
							AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 3)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.5F),
							AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 2)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.7F),
							AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 1)
						),

						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4).setValue(BlockStateProperties.LIT, true), 0.15F),
							AlwaysTrueTest.INSTANCE, Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 3).setValue(BlockStateProperties.LIT, true)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4).setValue(BlockStateProperties.LIT, true), 0.5F),
							AlwaysTrueTest.INSTANCE, Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 2).setValue(BlockStateProperties.LIT, true)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4).setValue(BlockStateProperties.LIT, true), 0.7F),
							AlwaysTrueTest.INSTANCE, Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 1).setValue(BlockStateProperties.LIT, true)
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);
	}

	@NotNull
	private static ResourceKey<StructureProcessorList> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, TrailierTalesSharedConstants.id(string));
	}

	@NotNull
	private static Holder<StructureProcessorList> register(
		@NotNull BootstrapContext<StructureProcessorList> entries, @NotNull ResourceKey<StructureProcessorList> key, @NotNull List<StructureProcessor> list
	) {
		return entries.register(key, new StructureProcessorList(list));
	}
}
