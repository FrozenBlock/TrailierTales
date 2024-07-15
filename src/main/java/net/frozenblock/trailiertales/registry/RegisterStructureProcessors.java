package net.frozenblock.trailiertales.registry;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.frozenblock.lib.worldgen.structure.api.AppendSherds;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
	public static final ResourceKey<StructureProcessorList> BADLANDS_FORT_ARCHAEOLOGY = createKey("badlands_fort_archaeology");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_DEGRADATION = createKey("catacombs_degradation");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_DEGRADATION_ARCHERY = createKey("catacombs_degradation_archery");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_DEGRADATION_FIRE = createKey("catacombs_degradation_fire");
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY = createKey("desert_ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY_SURFACE = createKey("desert_ruins_archaeology_surface");
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY_FOSSIL = createKey("desert_ruins_archaeology_fossil");
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY_POTS = createKey("desert_ruins_archaeology_pots");
	public static final ResourceKey<StructureProcessorList> JUNGLE_RUINS_ARCHAEOLOGY = createKey("jungle_ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> JUNGLE_RUINS_ARCHAEOLOGY_SURFACE = createKey("jungle_ruins_archaeology_surface");
	public static final ResourceKey<StructureProcessorList> SAVANNA_RUINS_ARCHAEOLOGY = createKey("savanna_ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> SAVANNA_RUINS_ARCHAEOLOGY_SURFACE = createKey("savanna_ruins_archaeology_surface");

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);

		register(
			context,
			RegisterStructureProcessors.BADLANDS_FORT_ARCHAEOLOGY,
			ImmutableList.of(
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_RED_SAND, 0.85F),
							AlwaysTrueTest.INSTANCE, Blocks.RED_SAND.defaultBlockState()
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		final RuleProcessor catacombsRuleProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS.defaultBlockState()),

				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_SLAB, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),

				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILES.defaultBlockState()),

				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),

				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.SUSPICIOUS_GRAVEL, 0.425F),
					AlwaysTrueTest.INSTANCE, Blocks.TUFF.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.SUSPICIOUS_GRAVEL, 0.9225F),
					AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_CLAY, 0.35F),
					AlwaysTrueTest.INSTANCE, Blocks.CLAY.defaultBlockState()
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
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.333F),
					AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
				)
			)
		);

		final BlockStateRespectingRuleProcessor catacombsBlockStateRespectingRuleProcessor = new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_BRICK_WALL.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_TILE_WALL.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_BRICK_SLAB.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_TILE_SLAB.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB
				)
			)
		);

		register(
			context,
			CATACOMBS_DEGRADATION,
			ImmutableList.of(
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					Items.PRIZE_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					Items.SHEAF_POTTERY_SHERD,
					Items.HEART_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					Items.BLADE_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_DEGRADATION_ARCHERY,
			ImmutableList.of(
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					RegisterItems.BULLSEYE_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD,
					RegisterItems.BULLSEYE_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_DEGRADATION_FIRE,
			ImmutableList.of(
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.ARMS_UP_POTTERY_SHERD,
					Items.BURN_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		RuleProcessor desertRuinsProcessor = new RuleProcessor(
			ImmutableList.of(new ProcessorRule(new RandomBlockMatchTest(Blocks.SAND, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.SANDSTONE.defaultBlockState()))
		);

		register(
			context,
			DESERT_RUINS_ARCHAEOLOGY,
			ImmutableList.of(
				desertRuinsProcessor,
				desertArchyLootProcessor(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY, 0.35F)
			)
		);

		register(
			context,
			DESERT_RUINS_ARCHAEOLOGY_SURFACE,
			ImmutableList.of(
				desertRuinsProcessor,
				desertArchyLootProcessor(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY_SURFACE, 0.35F)
			)
		);

		register(
			context,
			DESERT_RUINS_ARCHAEOLOGY_FOSSIL,
			ImmutableList.of(
				desertRuinsProcessor,
				desertArchyLootProcessor(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY_FOSSIl, 0.4F)
			)
		);

		register(
			context,
			DESERT_RUINS_ARCHAEOLOGY_POTS,
			ImmutableList.of(
				desertRuinsProcessor,
				desertArchyLootProcessor(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY_POTS, 0.3F),
				desertArchyLootProcessorClay(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY_POTS, 0.3F),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.5F),
							AlwaysTrueTest.INSTANCE, Blocks.WATER.defaultBlockState()
						)
					)
				),
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.ARMS_UP_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					Items.BREWER_POTTERY_SHERD,
					Items.MINER_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					Items.PRIZE_POTTERY_SHERD
				)
			)
		);

		RuleProcessor jungleStairGravelProcessor = new RuleProcessor(
			ImmutableList.of(new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE_STAIRS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()))
		);
		RuleProcessor jungleDegradationProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.defaultBlockState())
			)
		);
		BlockStateRespectingRuleProcessor jungleSlabWallStairsMossyProcessor = new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_SLAB.defaultBlockState(), 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_SLAB
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_WALL.defaultBlockState(), 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_STAIRS.defaultBlockState(), 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_STAIRS
				)
			)
		);

		register(
			context,
			JUNGLE_RUINS_ARCHAEOLOGY,
			ImmutableList.of(
				jungleStairGravelProcessor,
				jungleDegradationProcessor,
				jungleSlabWallStairsMossyProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.35F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.25F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.25F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.4F)
			)
		);

		register(
			context,
			JUNGLE_RUINS_ARCHAEOLOGY_SURFACE,
			ImmutableList.of(
				jungleStairGravelProcessor,
				jungleDegradationProcessor,
				jungleSlabWallStairsMossyProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_SURFACE, 0.295F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_SURFACE, 0.195F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_SURFACE, 0.195F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_SURFACE, 0.4F)
			)
		);

		RuleProcessor savannaRuleProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState())
			)
		);

		register(
			context,
			SAVANNA_RUINS_ARCHAEOLOGY,
			ImmutableList.of(
				savannaRuleProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY, 0.3F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY, 0.25F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY, 0.25F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY, 0.4F)
			)
		);

		register(
			context,
			SAVANNA_RUINS_ARCHAEOLOGY_SURFACE,
			ImmutableList.of(
				savannaRuleProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY_SURFACE, 0.295F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY_SURFACE, 0.195F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY_SURFACE, 0.195F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY_SURFACE, 0.4F)
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
	private static @NotNull RuleProcessor desertArchyLootProcessorClay(ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.CLAY, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					RegisterBlocks.SUSPICIOUS_CLAY.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	@Contract("_, _, _, _ -> new")
	private static @NotNull RuleProcessor archyLootProcessor(Block original, @NotNull Block suspicious, ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(original, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					suspicious.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	@Contract("_, _, _ -> new")
	private static @NotNull BlockStateRespectingRuleProcessor decoratedPotSherdProcessor(float chance, boolean defaultToBricks, Item... sherds) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.DECORATED_POT,
					new AppendSherds(chance, defaultToBricks, sherds)
				)
			)
		);
	}

	@NotNull
	private static ResourceKey<StructureProcessorList> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, TrailierConstants.id(string));
	}

	@NotNull
	private static Holder<StructureProcessorList> register(
		@NotNull BootstrapContext<StructureProcessorList> entries, @NotNull ResourceKey<StructureProcessorList> key, @NotNull List<StructureProcessor> list
	) {
		return entries.register(key, new StructureProcessorList(list));
	}
}
