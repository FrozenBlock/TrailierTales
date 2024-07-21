package net.frozenblock.trailiertales.registry;

import java.util.Map;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.worldgen.structure.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.CatacombsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.DesertRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.JungleRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.RuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.SavannaRuinsGenerator;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public final class RegisterStructures {

	private RegisterStructures() {
		throw new UnsupportedOperationException("RegisterStructures contains only static declarations.");
	}

	@NotNull
	public static ResourceKey<StructureSet> ofSet(@NotNull String id) {
		return ResourceKey.create(Registries.STRUCTURE_SET, TrailierConstants.id(id));
	}

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> context) {
		BadlandsRuinsGenerator.bootstrapTemplatePool(context);
		CatacombsGenerator.bootstrapTemplatePool(context);
		DesertRuinsGenerator.bootstrapTemplatePool(context);
		JungleRuinsGenerator.bootstrapTemplatePool(context);
		SavannaRuinsGenerator.bootstrapTemplatePool(context);
		RuinsGenerator.bootstrapTemplatePool(context);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		BadlandsRuinsGenerator.bootstrap(context);
		CatacombsGenerator.bootstrap(context);
		DesertRuinsGenerator.bootstrap(context);
		JungleRuinsGenerator.bootstrap(context);
		SavannaRuinsGenerator.bootstrap(context);
		RuinsGenerator.bootstrap(context);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		BadlandsRuinsGenerator.bootstrapStructureSet(context);
		CatacombsGenerator.bootstrapStructureSet(context);
		DesertRuinsGenerator.bootstrapStructureSet(context);
		JungleRuinsGenerator.bootstrapStructureSet(context);
		SavannaRuinsGenerator.bootstrapStructureSet(context);
		RuinsGenerator.bootstrapStructureSet(context);
	}

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		BadlandsRuinsGenerator.bootstrapProcessor(context);
		CatacombsGenerator.bootstrapProcessor(context);
		DesertRuinsGenerator.bootstrapProcessor(context);
		JungleRuinsGenerator.bootstrapProcessor(context);
		SavannaRuinsGenerator.bootstrapProcessor(context);
		RuinsGenerator.bootstrapProcessor(context);
	}

	@NotNull
	public static ResourceKey<Structure> createKey(@NotNull String id) {
		return ResourceKey.create(Registries.STRUCTURE, TrailierConstants.id(id));
	}

	@NotNull
	public static Structure.StructureSettings structure(
		@NotNull HolderSet<Biome> holderSet,
		@NotNull Map<MobCategory, StructureSpawnOverride> spawns,
		@NotNull GenerationStep.Decoration featureStep,
		@NotNull TerrainAdjustment terrainAdaptation
	) {
		return new Structure.StructureSettings(holderSet, spawns, featureStep, terrainAdaptation);
	}

	@NotNull
	public static Structure.StructureSettings structure(
		@NotNull HolderSet<Biome> holderSet,
		@NotNull GenerationStep.Decoration featureStep,
		@NotNull TerrainAdjustment terrainAdaptation
	) {
		return structure(holderSet, Map.of(), featureStep, terrainAdaptation);
	}

	public static void register(@NotNull BootstrapContext<StructureTemplatePool> pool, String location, StructureTemplatePool templatePool) {
		pool.register(Pools.parseKey(location), templatePool);
	}

}
