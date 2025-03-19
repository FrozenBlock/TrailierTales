/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

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
							spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SNIFFER, 1, 4), 5);
						}
					}
				})
			);
	}

}
