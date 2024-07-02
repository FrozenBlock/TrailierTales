package net.frozenblock.trailiertales.registry;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class RegisterStructureProcessors {
	public static final float SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE = 0.85F;
	public static final ResourceKey<StructureProcessorList> SUSPICIOUS_BLOCK_TO_NORMAL_085 = createKey("suspicious_block_to_normal_085");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_DEGRADATION = createKey("catacombs_degradation");
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY = createKey("desert_ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> JUNGLE_RUINS_ARCHAEOLOGY = createKey("jungle_ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> JUNGLE_RUINS_ARCHAEOLOGY_WITH_STAIRS = createKey("jungle_ruins_archaeology_with_stairs");

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
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(BlockStateProperties.HALF, Half.TOP), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(BlockStateProperties.HALF, Half.TOP)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST).setValue(BlockStateProperties.HALF, Half.TOP), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST).setValue(BlockStateProperties.HALF, Half.TOP)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH).setValue(BlockStateProperties.HALF, Half.TOP), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH).setValue(BlockStateProperties.HALF, Half.TOP)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST).setValue(BlockStateProperties.HALF, Half.TOP), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST).setValue(BlockStateProperties.HALF, Half.TOP)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(BlockStateProperties.HALF, Half.BOTTOM), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(BlockStateProperties.HALF, Half.BOTTOM)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST).setValue(BlockStateProperties.HALF, Half.BOTTOM), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST).setValue(BlockStateProperties.HALF, Half.BOTTOM)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH).setValue(BlockStateProperties.HALF, Half.BOTTOM), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH).setValue(BlockStateProperties.HALF, Half.BOTTOM)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST).setValue(BlockStateProperties.HALF, Half.BOTTOM), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST).setValue(BlockStateProperties.HALF, Half.BOTTOM)
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
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(BlockStateProperties.HALF, Half.BOTTOM), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(BlockStateProperties.HALF, Half.BOTTOM)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST).setValue(BlockStateProperties.HALF, Half.BOTTOM), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST).setValue(BlockStateProperties.HALF, Half.BOTTOM)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH).setValue(BlockStateProperties.HALF, Half.BOTTOM), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH).setValue(BlockStateProperties.HALF, Half.BOTTOM)
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(
								Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST).setValue(BlockStateProperties.HALF, Half.BOTTOM), 0.15F),
							AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST).setValue(BlockStateProperties.HALF, Half.BOTTOM)
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
						),

						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.POTTED_DEAD_BUSH, 0.1F),
							AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.POTTED_DEAD_BUSH, 0.6F),
							AlwaysTrueTest.INSTANCE, Blocks.FLOWER_POT.defaultBlockState()
						),

						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.225F),
							AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			DESERT_RUINS_ARCHAEOLOGY,
			List.of(
				new RuleProcessor(
					List.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.SAND, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.SANDSTONE.defaultBlockState())
					)
				),
				desertArchyLootProcessor(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY, 0.3F),
				desertArchyLootProcessor(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY_RARE, 0.1F)
			)
		);

		register(
			context,
			JUNGLE_RUINS_ARCHAEOLOGY,
			List.of(
				new RuleProcessor(
					List.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState())
					)
				),
				jungleArchyLootProcessor(RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.3F),
				jungleArchyLootProcessor(RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_RARE, 0.1F)
			)
		);

		register(
			context,
			JUNGLE_RUINS_ARCHAEOLOGY_WITH_STAIRS,
			List.of(
				new RuleProcessor(
					List.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE_STAIRS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState())
					)
				),
				new RuleProcessor(
					List.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState())
					)
				),
				bottomWaterloggedStairToMossyProcessor(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS, 0.4F),
				jungleArchyLootProcessor(RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.3F),
				jungleArchyLootProcessor(RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_RARE, 0.1F)
			)
		);
	}

	@Contract("_, _ -> new")
	private static @NotNull RuleProcessor desertArchyLootProcessor(ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.SAND, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.SUSPICIOUS_SAND.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	@Contract("_, _ -> new")
	private static @NotNull RuleProcessor jungleArchyLootProcessor(ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.GRAVEL, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.SUSPICIOUS_GRAVEL.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	@Contract("_, _, _ -> new")
	private static @NotNull RuleProcessor bottomWaterloggedStairToMossyProcessor(@NotNull Block original, Block mossy, float chance) {
		ArrayList<ProcessorRule> rules = new ArrayList<>();
		for (BlockState state : original.getStateDefinition().getPossibleStates()) {
			if (state.getValue(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.HALF) == Half.BOTTOM) {
				rules.add(
					new ProcessorRule(
						new RandomBlockStateMatchTest(state, chance),
						AlwaysTrueTest.INSTANCE,
						PosAlwaysTrueTest.INSTANCE,
						mossy.withPropertiesOf(state)
					)
				);
			}
		}

		return new RuleProcessor(rules);
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
