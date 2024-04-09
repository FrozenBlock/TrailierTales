package net.frozenblock.trailiertales.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class TrailierBiomeModifications {

	public static void init() {
		BiomeModifications.create(TrailierTalesSharedConstants.id("suspicious_block_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.foundInOverworld(),
				(biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, TrailierFeatureBootstrap.SUSPICIOUS_DIRT_PLACED);
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, TrailierFeatureBootstrap.SUSPICIOUS_CLAY_PLACED);
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, TrailierFeatureBootstrap.SUSPICIOUS_GRAVEL_PLACED);
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, TrailierFeatureBootstrap.SUSPICIOUS_SAND_PLACED);
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, TrailierFeatureBootstrap.SUSPICIOUS_RED_SAND_PLACED);
			});
	}
}
