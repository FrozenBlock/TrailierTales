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

public class SavannaRuinsGenerator {
	public static final ResourceKey<StructureSet> SAVANNA_RUINS_KEY =  RegisterStructures.ofSet("savanna_ruins");
	private static final ResourceKey<Structure> SAVANNA_RUIN_KEY = RegisterStructures.createKey("savanna_ruins");
	public static final ResourceKey<StructureTemplatePool> SAVANNA_RUINS = Pools.parseKey(TrailierConstants.string("savanna_ruins"));

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> surfaceProcessor = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.SAVANNA_RUINS_ARCHAEOLOGY_SURFACE);
		Holder<StructureProcessorList> processor = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.SAVANNA_RUINS_ARCHAEOLOGY);

		pool.register(
			SAVANNA_RUINS,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("archway"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("bunker"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("courtyard"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("fort"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("house1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("house2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("monastery1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("monastery2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("monastery3"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("tower1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("tower2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("town"), surfaceProcessor), 1)
				),
			StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("archway"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("archway_bottom"), processor), 1)
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
			string("courtyard"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("courtyard_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("fort"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fort_bottom"), processor), 1)
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
			string("monastery1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("monastery1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("monastery2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("monastery2_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("monastery3"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("monastery3_bottom"), processor), 1)
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
			string("tower1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("tower1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("tower2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("tower2_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("town"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("town_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			SAVANNA_RUIN_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_SAVANNA_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TrailierTerrainAdjustment.SMALL_PLATFORM
				),
				templatePool.getOrThrow(SavannaRuinsGenerator.SAVANNA_RUINS),
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
			SAVANNA_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(SAVANNA_RUIN_KEY),
				new RandomSpreadStructurePlacement(20, 17, RandomSpreadType.LINEAR, 78872547)
			)
		);
	}

	private static @NotNull String string(String name) {
		return TrailierConstants.string("ruins/savanna/" + name);
	}
}
