package net.frozenblock.trailiertales.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class TTBiomeModifications {

	public static void init() {
		BiomeModifications.create(TTConstants.id("sniffer"))
			.add(
				ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				((biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					BiomeModificationContext.SpawnSettingsContext spawnSettings = context.getSpawnSettings();

					if (TTWorldgenConfig.get().vegetation.generateTorchflower) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.TORCHFLOWER_PLACED);
						}
					}

					if (TTWorldgenConfig.get().vegetation.generatePitcher) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.PITCHER_PLACED);
						}
					}

					if (TTWorldgenConfig.get().vegetation.generateCyanRose) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.CYAN_ROSE_PLACED);
						}
					}

					if (TTWorldgenConfig.get().vegetation.generateManedrop) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.MANEDROP_PLACED);
						}
					}

					if (TTWorldgenConfig.get().vegetation.generateDawntrail) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.DAWNTRAIL_PLACED);
						}
					}

					if (TTEntityConfig.get().sniffer.spawn) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
							spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SNIFFER, 5, 1, 4));
						}
					}
				})
			);

		BiomeModifications.create(TTConstants.id("camel"))
			.add(
				ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				((biomeSelectionContext, context) -> {
					BiomeModificationContext.SpawnSettingsContext spawnSettings = context.getSpawnSettings();

					if (TTEntityConfig.get().camel.spawn) {
						if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_DESERT)) {
							spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CAMEL, 1, 1, 1));
						}
					}
				})
			);
	}

}
