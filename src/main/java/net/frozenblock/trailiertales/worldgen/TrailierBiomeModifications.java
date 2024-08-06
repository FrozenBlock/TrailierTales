package net.frozenblock.trailiertales.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.WorldgenConfig;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class TrailierBiomeModifications {

	public static void init() {
		BiomeModifications.create(TrailierConstants.id("vegetation"))
			.add(
				ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				((biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
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
				})
			);
	}

}
