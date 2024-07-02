package net.frozenblock.trailiertales.worldgen.structure;

import com.mojang.datafixers.util.Pair;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.registry.RegisterStructureProcessors;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.worldgen.TrailierTerrainAdjustment;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
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
import java.util.List;

public class JungleRuinsGenerator {
	public static final ResourceKey<StructureSet> JUNGLE_RUINS_KEY =  RegisterStructures.ofSet("jungle_ruins");
	private static final ResourceKey<Structure> JUNGLE_RUIN_KEY = RegisterStructures.createKey("jungle_ruins");
	public static final ResourceKey<StructureTemplatePool> JUNGLE_RUINS = Pools.parseKey(TrailierTalesSharedConstants.string("jungle_ruins"));

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> jungleRuinsProcessor = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.JUNGLE_RUINS_ARCHAEOLOGY);
		Holder<StructureProcessorList> jungleRuinsProcessorWaterloggedStairs = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.JUNGLE_RUINS_ARCHAEOLOGY_WITH_STAIRS);

		pool.register(
			JUNGLE_RUINS,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("monastery"), jungleRuinsProcessor), 1),
					Pair.of(StructurePoolElement.single(string("pillar"), jungleRuinsProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine1"), jungleRuinsProcessorWaterloggedStairs), 1),
					Pair.of(StructurePoolElement.single(string("shrine2"), jungleRuinsProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine3"), jungleRuinsProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine4"), jungleRuinsProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine5"), jungleRuinsProcessor), 1),
					Pair.of(StructurePoolElement.single(string("twin_shrines"), jungleRuinsProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("monastery_bottom"), jungleRuinsProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("pillar"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("pillar_bottom"), jungleRuinsProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine3_bottom"), jungleRuinsProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine4_bottom"), jungleRuinsProcessor), 1)
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
					Pair.of(StructurePoolElement.single(string("shrine5_bottom"), jungleRuinsProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("twin_shrines"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("twin_shrines_bottom"), jungleRuinsProcessor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			JUNGLE_RUIN_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(BiomeTags.IS_JUNGLE),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TrailierTerrainAdjustment.SMALL_PLATFORM
				),
				templatePool.getOrThrow(JungleRuinsGenerator.JUNGLE_RUINS),
				2,
				UniformHeight.of(VerticalAnchor.absolute(-1), VerticalAnchor.absolute(0)),
				false,
				Heightmap.Types.WORLD_SURFACE_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			JUNGLE_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(JUNGLE_RUIN_KEY),
				new RandomSpreadStructurePlacement(18, 10, RandomSpreadType.LINEAR, 343577861)
			)
		);
	}

	private static @NotNull String string(String name) {
		return TrailierTalesSharedConstants.string("ruins/jungle/" + name);
	}
}
