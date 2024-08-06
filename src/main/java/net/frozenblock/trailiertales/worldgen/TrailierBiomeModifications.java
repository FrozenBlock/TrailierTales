package net.frozenblock.trailiertales.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.EntityConfig;
import net.frozenblock.trailiertales.config.WorldgenConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class TrailierBiomeModifications {

	public static void init() {
		BiomeModifications.create(TrailierConstants.id("vegetation"))
			.add(
				ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				((biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					BiomeModificationContext.SpawnSettingsContext spawnSettings = context.getSpawnSettings();

					if (WorldgenConfig.get().vegetation.generateTorchflower) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TrailierFeatureBootstrap.TORCHFLOWER_PLACED);
						}
					}

					if (WorldgenConfig.get().vegetation.generatePitcher) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TrailierFeatureBootstrap.PITCHER_PLACED);
						}
					}

					if (WorldgenConfig.get().vegetation.generateCyanRose) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TrailierFeatureBootstrap.CYAN_ROSE_PLACED);
						}
					}

					if (EntityConfig.get().sniffer.spawn) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SNIFFER, 5, 1, 4));
						}
					}
				})
			);
	}

}
