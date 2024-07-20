package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.frozenblock.lib.worldgen.structure.api.AppendSherds;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DesertRuinsGenerator {
	public static final ResourceKey<StructureSet> DESERT_RUINS_KEY =  RegisterStructures.ofSet("desert_ruins");
	private static final ResourceKey<Structure> DESERT_RUIN_KEY = RegisterStructures.createKey("desert_ruins");
	public static final ResourceKey<StructureTemplatePool> DESERT_RUINS = Pools.parseKey(TrailierConstants.string("desert_ruins"));
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY = createKey("desert_ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY_SURFACE = createKey("desert_ruins_archaeology_surface");
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY_FOSSIL = createKey("desert_ruins_archaeology_fossil");
	public static final ResourceKey<StructureProcessorList> DESERT_RUINS_ARCHAEOLOGY_POTS = createKey("desert_ruins_archaeology_pots");

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> processor = structureProcessorGetter.getOrThrow(DESERT_RUINS_ARCHAEOLOGY);
		Holder<StructureProcessorList> fossilProcessor = structureProcessorGetter.getOrThrow(DESERT_RUINS_ARCHAEOLOGY_FOSSIL);
		Holder<StructureProcessorList> surfaceProcessor = structureProcessorGetter.getOrThrow(DESERT_RUINS_ARCHAEOLOGY_SURFACE);
		Holder<StructureProcessorList> potWellProcessor = structureProcessorGetter.getOrThrow(DESERT_RUINS_ARCHAEOLOGY_POTS);

		pool.register(
			DESERT_RUINS,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("archway"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("bunker"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("coliseum"), processor), 1),
					Pair.of(StructurePoolElement.single(string("dune"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort"), processor), 1),
					Pair.of(StructurePoolElement.single(string("fossil1"), fossilProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fossil2"), fossilProcessor), 1),
					Pair.of(StructurePoolElement.single(string("house1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("house2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("house3"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("house4"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("monastery"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("pillar1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("pillar2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("pillar3"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("well"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("well_aquifer"), surfaceProcessor), 1)
				),
			StructureTemplatePool.Projection.RIGID
			)
		);


		RegisterStructures.register(
			pool,
			string("bunker"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("bunker_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("dune"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("dune_bottom"), processor), 1)
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
			string("house3"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("house3_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("house4"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("house4_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("monastery"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("monastery_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("well"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("well_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);


		RegisterStructures.register(
			pool,
			string("well_aquifer"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("well_aquifer_bottom"), potWellProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			DESERT_RUIN_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_DESERT_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TrailierTerrainAdjustment.SMALL_PLATFORM
				),
				templatePool.getOrThrow(DesertRuinsGenerator.DESERT_RUINS),
				2,
				UniformHeight.of(VerticalAnchor.absolute(-4), VerticalAnchor.absolute(-1)),
				false,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			DESERT_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(DESERT_RUIN_KEY),
				new RandomSpreadStructurePlacement(22, 17, RandomSpreadType.LINEAR, 9432401)
			)
		);
	}

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
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
		return TrailierConstants.string("ruins/desert/" + name);
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
