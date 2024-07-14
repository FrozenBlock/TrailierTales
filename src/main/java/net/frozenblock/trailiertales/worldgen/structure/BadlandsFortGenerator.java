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
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class BadlandsFortGenerator {
	public static final ResourceKey<StructureSet> BADLANDS_FORTS_KEY =  RegisterStructures.ofSet("badlands_fort");
	private static final ResourceKey<Structure> BADLANDS_FORT_KEY = RegisterStructures.createKey("badlands_fort");
	public static final ResourceKey<StructureTemplatePool> BADLANDS_FORT = Pools.parseKey(TrailierConstants.string("badlands_fort"));

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> badlandsFortDegradation = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.BADLANDS_FORT_ARCHAEOLOGY);

		pool.register(
			BADLANDS_FORT,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("fort1"), badlandsFortDegradation), 1),
					Pair.of(StructurePoolElement.single(string("fort2"), badlandsFortDegradation), 1),
					Pair.of(StructurePoolElement.single(string("fort_tower1"), badlandsFortDegradation), 1),
					Pair.of(StructurePoolElement.single(string("fort_tower2"), badlandsFortDegradation), 1)
				),
			StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			BADLANDS_FORT_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_BADLANDS_FORT),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TrailierTerrainAdjustment.SMALL_PLATFORM
				),
				templatePool.getOrThrow(BadlandsFortGenerator.BADLANDS_FORT),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(-2)),
				false,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			BADLANDS_FORTS_KEY,
			new StructureSet(
				structure.getOrThrow(BADLANDS_FORT_KEY),
				new RandomSpreadStructurePlacement(20, 15, RandomSpreadType.LINEAR, 21338252) // ancient city salt is 20083232
			)
		);
	}

	private static @NotNull String string(String name) {
		return TrailierConstants.string("badlands_fort/" + name);
	}
}
