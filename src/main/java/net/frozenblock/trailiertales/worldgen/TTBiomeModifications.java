/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
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
		BiomeModifications.create(TTConstants.id("sniffer")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				final BiomeModificationContext.MobSpawnSettingsContext spawnSettings = context.getMobSpawnSettings();

				if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_JUNGLE)) {
					if (TTWorldgenConfig.TORCHFLOWER_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.PATCH_TORCHFLOWER);
					}

					if (TTWorldgenConfig.PITCHER_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.PATCH_PITCHER);
					}

					if (TTWorldgenConfig.CYAN_ROSE_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.PATCH_CYAN_ROSE);
					}

					if (TTWorldgenConfig.GUZMANIA_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.PATCH_GUZMANIA);
					}

					if (TTWorldgenConfig.DAWNTRAIL_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.DAWNTRAIL_PLACED);
					}

					if (TTEntityConfig.SPAWN_SNIFFERS.get()) {
						spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SNIFFER, 1, 4), 5);
					}
				}

				if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_BIRCH_FOREST)) {
					if (TTWorldgenConfig.MANEDROP_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.PATCH_MANEDROP);
					}
				}

				if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_DESERT)) {
					if (TTWorldgenConfig.LITHOPS_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.PATCH_LITHOPS);
					}
				}

				if (biomeSelectionContext.hasTag(ConventionalBiomeTags.IS_STONY_SHORES)) {
					if (TTWorldgenConfig.LITHOPS_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TTFeatureBootstrap.PATCH_LITHOPS_RARE);
					}
				}
			}
		);
	}

}
