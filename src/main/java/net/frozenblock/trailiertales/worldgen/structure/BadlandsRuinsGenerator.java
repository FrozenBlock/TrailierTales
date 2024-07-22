package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

public class BadlandsRuinsGenerator {
	public static final ResourceKey<StructureSet> BADLANDS_RUINS_KEY =  RegisterStructures.ofSet("badlands_ruins");
	private static final ResourceKey<Structure> BADLANDS_RUIN_KEY = RegisterStructures.createKey("badlands_ruins");
	public static final ResourceKey<StructureTemplatePool> BADLANDS_RUINS = Pools.parseKey(TrailierConstants.string("badlands_ruins"));
	public static final ResourceKey<StructureProcessorList> BADLANDS_RUINS_FORT_ARCHAEOLOGY = createKey("badlands_ruins_fort_archaeology");
	public static final ResourceKey<StructureProcessorList> BADLANDS_RUINS_TOWER_ARCHAEOLOGY = createKey("badlands_ruins_tower_archaeology");
	public static final ResourceKey<StructureProcessorList> BADLANDS_RUINS_ARCHAEOLOGY_SURFACE = createKey("badlands_ruins_archaeology_surface");
	public static final ResourceKey<StructureProcessorList> BADLANDS_RUINS_ARCHAEOLOGY = createKey("badlands_ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> BADLANDS_RUINS_ARCHAEOLOGY_FOSSIL = createKey("badlands_ruins_archaeology_fossil");

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> fortArchyProcessor = structureProcessorGetter.getOrThrow(BADLANDS_RUINS_FORT_ARCHAEOLOGY);
		Holder<StructureProcessorList> towerArchyProcessor = structureProcessorGetter.getOrThrow(BADLANDS_RUINS_TOWER_ARCHAEOLOGY);
		Holder<StructureProcessorList> surfaceProcessor = structureProcessorGetter.getOrThrow(BADLANDS_RUINS_ARCHAEOLOGY_SURFACE);
		Holder<StructureProcessorList> archyProcessor = structureProcessorGetter.getOrThrow(BADLANDS_RUINS_ARCHAEOLOGY);
		Holder<StructureProcessorList> fossilProcessor = structureProcessorGetter.getOrThrow(BADLANDS_RUINS_ARCHAEOLOGY_FOSSIL);

		pool.register(
			BADLANDS_RUINS,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fort/fort1"), fortArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("fort/fort2"), fortArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("fort/fort3"), fortArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("fort/fort4"), fortArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("fort/fort5"), fortArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("tower/tower1"), towerArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("tower/tower2"), towerArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("tower/tower3"), towerArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("tower/tower4"), towerArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("tower/tower5"), towerArchyProcessor), 2),
					Pair.of(StructurePoolElement.single(string("fossil1"), fossilProcessor), 5),
					Pair.of(StructurePoolElement.single(string("fossil2"), fossilProcessor), 5),
					Pair.of(StructurePoolElement.single(string("house1"), towerArchyProcessor), 5),
					Pair.of(StructurePoolElement.single(string("house2"), towerArchyProcessor), 5),
					Pair.of(StructurePoolElement.single(string("house3"), towerArchyProcessor), 5),
					Pair.of(StructurePoolElement.single(string("house4"), towerArchyProcessor), 5),
					Pair.of(StructurePoolElement.single(string("house5"), towerArchyProcessor), 5),
					Pair.of(StructurePoolElement.single(string("meeting_point1"), towerArchyProcessor), 5),
					Pair.of(StructurePoolElement.single(string("outpost1"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine1"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine2"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine3"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine4"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine5"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine6"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine7"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine8"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine9"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine10"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine11"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("shrine12"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("temple1"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("town1"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("town2"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("twin_houses1"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("twin_houses2"), surfaceProcessor), 5),
					Pair.of(StructurePoolElement.single(string("watchtower1"), surfaceProcessor), 5)
				),
			StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("fort/pillar"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fort/pillar1")), 5),
					Pair.of(StructurePoolElement.single(string("fort/pillar2")), 17),
					Pair.of(StructurePoolElement.single(string("fort/pillar3")), 17),
					Pair.of(StructurePoolElement.single(string("fort/pillar4")), 9),
					Pair.of(StructurePoolElement.single(string("fort/pillar_fallen1")), 6),
					Pair.of(StructurePoolElement.single(string("fort/pillar_fallen2")), 5),
					Pair.of(StructurePoolElement.single(string("fort/pillar_fallen3")), 3),
					Pair.of(StructurePoolElement.single(string("fort/pillar_fallen4")), 4),
					Pair.of(StructurePoolElement.single(string("fort/pillar_fallen5")), 6),
					Pair.of(StructurePoolElement.single(string("fort/pillar_fallen6")), 6),
					Pair.of(StructurePoolElement.single(string("fort/pillar_fallen7")), 6)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("fossil1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fossil1_bottom"), fossilProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("fossil2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fossil2_bottom"), fossilProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("house1_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("house2_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("house3_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("house4_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("house5"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("house5_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("meeting_point1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("meeting_point1_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("outpost1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("outpost1_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine1_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine2_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine3_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine4_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine5_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine6_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine7_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine8_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine9"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine9_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine10"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine10_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine11"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine11_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine12"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine12_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("temple1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("temple1_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("town1_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("town2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("town2_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("twin_houses1_bottom"), archyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("twin_houses2_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("watchtower1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("watchtower1_bottom"), archyProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			BADLANDS_RUIN_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_BADLANDS_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TrailierTerrainAdjustment.SMALL_PLATFORM
				),
				templatePool.getOrThrow(BadlandsRuinsGenerator.BADLANDS_RUINS),
				8,
				ConstantHeight.of(VerticalAnchor.absolute(-2)),
				false,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			BADLANDS_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(BADLANDS_RUIN_KEY),
				new RandomSpreadStructurePlacement(17, 11, RandomSpreadType.LINEAR, 21338252) // ancient city salt is 20083232
			)
		);
	}

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		register(
			context,
			BADLANDS_RUINS_FORT_ARCHAEOLOGY,
			ImmutableList.of(
				badlandsArchy(RegisterLootTables.BADLANDS_RUINS_ARCHAEOLOGY_SURFACE, 0.15F),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.RED_SANDSTONE, 0.2F),
							AlwaysTrueTest.INSTANCE, Blocks.CUT_RED_SANDSTONE.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.RED_SANDSTONE, 0.075F),
							AlwaysTrueTest.INSTANCE, Blocks.CHISELED_RED_SANDSTONE.defaultBlockState()
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			BADLANDS_RUINS_TOWER_ARCHAEOLOGY,
			ImmutableList.of(
				badlandsArchy(RegisterLootTables.BADLANDS_RUINS_ARCHAEOLOGY_SURFACE, 0.15F),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.CHISELED_RED_SANDSTONE, 0.15F),
							AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.CUT_RED_SANDSTONE, 0.15F),
							AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.RED_SANDSTONE, 0.2F),
							AlwaysTrueTest.INSTANCE, Blocks.CUT_RED_SANDSTONE.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.RED_SANDSTONE, 0.075F),
							AlwaysTrueTest.INSTANCE, Blocks.CHISELED_RED_SANDSTONE.defaultBlockState()
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		final RuleProcessor redSandProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.RED_SAND, 0.1F),
					AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()
				)
			)
		);

		final RuleProcessor redSandstoneProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.CHISELED_RED_SANDSTONE, 0.15F),
					AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.CUT_RED_SANDSTONE, 0.15F),
					AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.CUT_RED_SANDSTONE, 0.75F),
					AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState()
				)
			)
		);

		register(
			context,
			BADLANDS_RUINS_ARCHAEOLOGY_SURFACE,
			ImmutableList.of(
				badlandsArchy(RegisterLootTables.BADLANDS_RUINS_ARCHAEOLOGY_SURFACE, 0.3F),
				redSandstoneProcessor,
				redSandProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			BADLANDS_RUINS_ARCHAEOLOGY,
			ImmutableList.of(
				badlandsArchy(RegisterLootTables.BADLANDS_RUINS_ARCHAEOLOGY, 0.3F),
				redSandstoneProcessor,
				redSandProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			BADLANDS_RUINS_ARCHAEOLOGY_FOSSIL,
			ImmutableList.of(
				badlandsArchy(RegisterLootTables.BADLANDS_RUINS_ARCHAEOLOGY_FOSSIL, 0.4F),
				redSandProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);
	}

	private static @NotNull RuleProcessor badlandsArchy(ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.RED_SAND, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					RegisterBlocks.SUSPICIOUS_RED_SAND.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	private static @NotNull String string(String name) {
		return TrailierConstants.string("ruins/badlands/" + name);
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
