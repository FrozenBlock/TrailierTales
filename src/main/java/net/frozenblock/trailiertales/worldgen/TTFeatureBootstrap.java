/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.worldgen;

import java.util.Arrays;
import java.util.List;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import org.jetbrains.annotations.NotNull;

public class TTFeatureBootstrap {

	public static final ResourceKey<ConfiguredFeature<?, ?>> TORCHFLOWER = ResourceKey.create(
		Registries.CONFIGURED_FEATURE,
			TTConstants.id("torchflower")
	);
	public static final ResourceKey<PlacedFeature> TORCHFLOWER_PLACED = ResourceKey.create(
		Registries.PLACED_FEATURE,
		TTConstants.id("torchflower")
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> PITCHER = ResourceKey.create(
		Registries.CONFIGURED_FEATURE,
			TTConstants.id("pitcher")
		);
	public static final ResourceKey<PlacedFeature> PITCHER_PLACED = ResourceKey.create(
		Registries.PLACED_FEATURE,
		TTConstants.id("pitcher")
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> CYAN_ROSE = ResourceKey.create(
		Registries.CONFIGURED_FEATURE,
			TTConstants.id("cyan_rose")
		);
	public static final ResourceKey<PlacedFeature> CYAN_ROSE_PLACED = ResourceKey.create(
		Registries.PLACED_FEATURE,
		TTConstants.id("cyan_rose")
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> MANEDROP = ResourceKey.create(
		Registries.CONFIGURED_FEATURE,
			TTConstants.id("manedrop")
		);
	public static final ResourceKey<PlacedFeature> MANEDROP_PLACED = ResourceKey.create(
		Registries.PLACED_FEATURE,
		TTConstants.id("manedrop")
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> DAWNTRAIL = ResourceKey.create(
		Registries.CONFIGURED_FEATURE,
		TTConstants.id("dawntrail")
	);
	public static final ResourceKey<PlacedFeature> DAWNTRAIL_PLACED = ResourceKey.create(
		Registries.PLACED_FEATURE,
		TTConstants.id("dawntrail")
	);

	public static void bootstrapConfigured(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		register(
			entries,
			TORCHFLOWER,
			Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				18,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TORCHFLOWER))
				)
			)
		);

		register(
			entries,
			PITCHER,
			Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				10,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PITCHER_PLANT))
				)
			)
		);

		register(
			entries,
			CYAN_ROSE,
			Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				15,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(TTBlocks.CYAN_ROSE))
				)
			)
		);

		register(
			entries,
			MANEDROP,
			Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				10,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(TTBlocks.MANEDROP))
				)
			)
		);

		register(
			entries,
			DAWNTRAIL,
			Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				(MultifaceBlock) TTBlocks.DAWNTRAIL,
				20,
				true,
				true,
				true,
				0.9F,
				HolderSet.direct(
					Block::builtInRegistryHolder,
					Blocks.DIRT,
					Blocks.GRASS_BLOCK,
					Blocks.PODZOL,
					Blocks.JUNGLE_LOG,
					Blocks.JUNGLE_LEAVES
				)
			)
		);
	}

	public static void bootstrapPlaced(@NotNull BootstrapContext<PlacedFeature> entries) {
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		register(
			entries,
			TORCHFLOWER_PLACED,
			configuredFeatures.getOrThrow(TORCHFLOWER),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		register(
			entries,
			PITCHER_PLACED,
			configuredFeatures.getOrThrow(PITCHER),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		register(
			entries,
			CYAN_ROSE_PLACED,
			configuredFeatures.getOrThrow(CYAN_ROSE),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		register(
			entries,
			MANEDROP_PLACED,
			configuredFeatures.getOrThrow(MANEDROP),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		register(
			entries,
			DAWNTRAIL_PLACED,
			configuredFeatures.getOrThrow(DAWNTRAIL),
			CountPlacement.of(UniformInt.of(52, 90)),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			InSquarePlacement.spread(),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, -6, 64),
			BiomeFilter.biome()
		);
	}

	public static void register(BootstrapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, PlacementModifier... modifiers) {
		register(entries, resourceKey, configuredHolder, Arrays.asList(modifiers));
	}

	private static void register(BootstrapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, List<PlacementModifier> modifiers) {
		PlacementUtils.register(entries, resourceKey, configuredHolder, modifiers);
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> entries, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, F feature, FC featureConfiguration) {
		FeatureUtils.register(entries, resourceKey, feature, featureConfiguration);
	}
}
