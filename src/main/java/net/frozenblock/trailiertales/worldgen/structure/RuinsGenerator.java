package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.frozenblock.lib.worldgen.structure.api.AppendSherds;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.frozenblock.trailiertales.worldgen.TrailierTerrainAdjustment;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class RuinsGenerator {
	public static final ResourceKey<StructureSet> RUINS_KEY =  RegisterStructures.ofSet("ruins");
	private static final ResourceKey<Structure> RUIN_KEY = RegisterStructures.createKey("ruins");
	public static final ResourceKey<StructureTemplatePool> RUINS = Pools.parseKey(TrailierConstants.string("ruins"));
	public static final ResourceKey<StructureProcessorList> RUINS_ARCHAEOLOGY = createKey("ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> RUINS_ARCHAEOLOGY_SURFACE = createKey("ruins_archaeology_surface");
	public static final ResourceKey<StructureProcessorList> RUINS_ARCHAEOLOGY_LIBRARY = createKey("ruins_archaeology_library");
	public static final ResourceKey<StructureProcessorList> RUINS_ARCHAEOLOGY_POTS = createKey("ruins_archaeology_pots");

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> surfaceProcessor = structureProcessorGetter.getOrThrow(RUINS_ARCHAEOLOGY_SURFACE);
		Holder<StructureProcessorList> processor = structureProcessorGetter.getOrThrow(RUINS_ARCHAEOLOGY);
		Holder<StructureProcessorList> libraryProcessor = structureProcessorGetter.getOrThrow(RUINS_ARCHAEOLOGY_LIBRARY);
		Holder<StructureProcessorList> potsProcessor = structureProcessorGetter.getOrThrow(RUINS_ARCHAEOLOGY_POTS);

		pool.register(
			RUINS,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("archway1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("archway2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("archway3"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort3"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("house1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("house2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("houses1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("library1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("pillar1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("pillars1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine3"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine4"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine5"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine6"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine7"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine8"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("town1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("twin_houses1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("twin_houses2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("well1"), surfaceProcessor), 1)
				),
			StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("archway1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("archway1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("archway2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("archway2_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("fort1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fort1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("fort2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fort2_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("fort3"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fort3_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("house1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("house1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("house2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("house2_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("houses1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("houses1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("library1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("library1_bottom"), libraryProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("pillar1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("pillar1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine2_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine3"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine3_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine4"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine4_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine5"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine5_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine6"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine6_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine7"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine7_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine8"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine8_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("town1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("town1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("twin_houses1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("twin_houses1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("twin_houses2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("twin_houses2_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("well1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("well1_bottom"), potsProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			RUIN_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TrailierTerrainAdjustment.SMALL_PLATFORM
				),
				templatePool.getOrThrow(RuinsGenerator.RUINS),
				2,
				UniformHeight.of(VerticalAnchor.absolute(-1), VerticalAnchor.absolute(0)),
				false,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(RUIN_KEY),
				new RandomSpreadStructurePlacement(28, 20, RandomSpreadType.LINEAR, 68415318)
			)
		);
	}

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		RuleProcessor genericDegradationProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.STONE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.defaultBlockState())
			)
		);
		BlockStateRespectingRuleProcessor genericSlabWallStairsMossyProcessor = new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_SLAB.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_SLAB
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_WALL.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_STAIRS.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_STAIRS
				)
			)
		);

		register(
			context,
			RUINS_ARCHAEOLOGY,
			ImmutableList.of(
				genericDegradationProcessor,
				genericSlabWallStairsMossyProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.275F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.2F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.2F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.4F)
			)
		);

		register(
			context,
			RUINS_ARCHAEOLOGY_SURFACE,
			ImmutableList.of(
				genericDegradationProcessor,
				genericSlabWallStairsMossyProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.RUINS_ARCHAEOLOGY_SURFACE, 0.2F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY_SURFACE, 0.15F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY_SURFACE, 0.15F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.RUINS_ARCHAEOLOGY_SURFACE, 0.4F)
			)
		);

		register(
			context,
			RUINS_ARCHAEOLOGY_LIBRARY,
			ImmutableList.of(
				genericDegradationProcessor,
				genericSlabWallStairsMossyProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.RUINS_ARCHAEOLOGY_LIBRARY, 0.275F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY_LIBRARY, 0.2F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY_LIBRARY, 0.2F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.RUINS_ARCHAEOLOGY_LIBRARY, 0.4F)
			)
		);

		register(
			context,
			RUINS_ARCHAEOLOGY_POTS,
			ImmutableList.of(
				genericDegradationProcessor,
				genericSlabWallStairsMossyProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.275F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.2F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.2F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.4F),
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.MINER_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					Items.BREWER_POTTERY_SHERD,
					Items.FRIEND_POTTERY_SHERD
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

	private static @NotNull String string(String name) {
		return TrailierConstants.string("ruins/generic/" + name);
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
