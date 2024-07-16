package net.frozenblock.trailiertales.worldgen.structure;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterStructureProcessors;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.frozenblock.trailiertales.worldgen.TrailierTerrainAdjustment;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class DesertRuinsGenerator {
	public static final ResourceKey<StructureSet> DESERT_RUINS_KEY =  RegisterStructures.ofSet("desert_ruins");
	private static final ResourceKey<Structure> DESERT_RUIN_KEY = RegisterStructures.createKey("desert_ruins");
	public static final ResourceKey<StructureTemplatePool> DESERT_RUINS = Pools.parseKey(TrailierConstants.string("desert_ruins"));

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> processor = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.DESERT_RUINS_ARCHAEOLOGY);
		Holder<StructureProcessorList> fossilProcessor = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.DESERT_RUINS_ARCHAEOLOGY_FOSSIL);
		Holder<StructureProcessorList> surfaceProcessor = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.DESERT_RUINS_ARCHAEOLOGY_SURFACE);
		Holder<StructureProcessorList> potWellProcessor = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.DESERT_RUINS_ARCHAEOLOGY_POTS);

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

	private static @NotNull String string(String name) {
		return TrailierConstants.string("ruins/desert/" + name);
	}
}
