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

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> fortArchyProcessor = structureProcessorGetter.getOrThrow(BADLANDS_RUINS_FORT_ARCHAEOLOGY);
		Holder<StructureProcessorList> towerArchyProcessor = structureProcessorGetter.getOrThrow(BADLANDS_RUINS_TOWER_ARCHAEOLOGY);

		pool.register(
			BADLANDS_RUINS,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fort/fort1"), fortArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort/fort2"), fortArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort/fort3"), fortArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort/fort4"), fortArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort/fort5"), fortArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("tower/tower1"), towerArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("tower/tower2"), towerArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("tower/tower3"), towerArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("tower/tower4"), towerArchyProcessor), 1),
					Pair.of(StructurePoolElement.single(string("tower/tower5"), towerArchyProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("fort/pillar2")), 20),
					Pair.of(StructurePoolElement.single(string("fort/pillar3")), 20),
					Pair.of(StructurePoolElement.single(string("fort/pillar4")), 13),
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
				new RandomSpreadStructurePlacement(20, 15, RandomSpreadType.LINEAR, 21338252) // ancient city salt is 20083232
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
