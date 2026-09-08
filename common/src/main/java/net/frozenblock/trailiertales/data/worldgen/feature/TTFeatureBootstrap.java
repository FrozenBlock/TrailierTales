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

package net.frozenblock.trailiertales.data.worldgen.feature;

import java.util.Arrays;
import java.util.List;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.FlowerBedStateProvider;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;

public final class TTFeatureBootstrap {
	public static final ResourceKey<Feature> TORCHFLOWER = ResourceKey.create(Registries.FEATURE, TTConstants.id("torchflower"));
	public static final ResourceKey<PlacedFeature> PATCH_TORCHFLOWER = ResourceKey.create(Registries.PLACED_FEATURE, TTConstants.id("patch_torchflower"));

	public static final ResourceKey<Feature> PITCHER = ResourceKey.create(Registries.FEATURE, TTConstants.id("pitcher"));
	public static final ResourceKey<PlacedFeature> PATCH_PITCHER = ResourceKey.create(Registries.PLACED_FEATURE, TTConstants.id("patch_pitcher"));

	public static final ResourceKey<Feature> CYAN_ROSE = ResourceKey.create(Registries.FEATURE, TTConstants.id("cyan_rose"));
	public static final ResourceKey<PlacedFeature> PATCH_CYAN_ROSE = ResourceKey.create(Registries.PLACED_FEATURE, TTConstants.id("patch_cyan_rose"));

	public static final ResourceKey<Feature> MANEDROP = ResourceKey.create(Registries.FEATURE, TTConstants.id("manedrop"));
	public static final ResourceKey<PlacedFeature> PATCH_MANEDROP = ResourceKey.create(Registries.PLACED_FEATURE, TTConstants.id("patch_manedrop"));

	public static final ResourceKey<Feature> GUZMANIA = ResourceKey.create(Registries.FEATURE, TTConstants.id("guzmania"));
	public static final ResourceKey<PlacedFeature> PATCH_GUZMANIA = ResourceKey.create(Registries.PLACED_FEATURE, TTConstants.id("patch_guzmania"));

	public static final ResourceKey<Feature> DAWNTRAIL = ResourceKey.create(Registries.FEATURE, TTConstants.id("dawntrail"));
	public static final ResourceKey<PlacedFeature> DAWNTRAIL_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, TTConstants.id("dawntrail"));

	public static final ResourceKey<Feature> LITHOPS = ResourceKey.create(Registries.FEATURE, TTConstants.id("lithops"));
	public static final ResourceKey<PlacedFeature> PATCH_LITHOPS = ResourceKey.create(Registries.PLACED_FEATURE, TTConstants.id("patch_lithops"));
	public static final ResourceKey<PlacedFeature> PATCH_LITHOPS_RARE = ResourceKey.create(Registries.PLACED_FEATURE, TTConstants.id("patch_lithops_rare"));

	public static void bootstrapConfigured(BootstrapContext<Feature> entries) {
		final HolderGetter<Feature> features = entries.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		register(entries, TORCHFLOWER, new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.TORCHFLOWER)));
		register(entries, PITCHER, new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.PITCHER_PLANT)));
		register(entries, CYAN_ROSE, new SimpleBlockFeature(BlockStateProvider.holderOf(TTBlocks.CYAN_ROSE.get())));
		register(entries, MANEDROP, new SimpleBlockFeature(BlockStateProvider.holderOf(TTBlocks.MANEDROP.get())));
		register(entries, GUZMANIA, new SimpleBlockFeature(BlockStateProvider.holderOf(TTBlocks.GUZMANIA.get())));
		register(
			entries,
			DAWNTRAIL,
			new MultifaceGrowthFeature(
				TTBlocks.DAWNTRAIL.get(),
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

		register(entries, LITHOPS, new SimpleBlockFeature(new FlowerBedStateProvider(TTBlocks.LITHOPS.get())));
	}

	public static void bootstrapPlaced(BootstrapContext<PlacedFeature> entries) {
		final HolderGetter<Feature> features = entries.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		register(
			entries,
			PATCH_TORCHFLOWER,
			features.getOrThrow(TORCHFLOWER),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		register(
			entries,
			PATCH_PITCHER,
			features.getOrThrow(PITCHER),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		register(
			entries,
			PATCH_CYAN_ROSE,
			features.getOrThrow(CYAN_ROSE),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(15),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		register(
			entries,
			PATCH_MANEDROP,
			features.getOrThrow(MANEDROP),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		register(
			entries,
			PATCH_GUZMANIA,
			features.getOrThrow(GUZMANIA),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		register(
			entries,
			DAWNTRAIL_PLACED,
			features.getOrThrow(DAWNTRAIL),
			CountPlacement.of(UniformInt.of(52, 90)),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			InSquarePlacement.spread(),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, -6, 64),
			BiomeFilter.biome()
		);

		register(
			entries,
			PATCH_LITHOPS,
			features.getOrThrow(LITHOPS),
			RarityFilter.onAverageOnceEvery(11),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			OffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		register(
			entries,
			PATCH_LITHOPS_RARE,
			features.getOrThrow(LITHOPS),
			RarityFilter.onAverageOnceEvery(16),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			OffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);
	}

	public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<Feature> configuredHolder, PlacementModifier... modifiers) {
		register(context, key, configuredHolder, Arrays.asList(modifiers));
	}

	private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<Feature> configuredHolder, List<PlacementModifier> modifiers) {
		PlacementUtils.register(context, key, configuredHolder, modifiers);
	}

	private static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, Feature feature) {
		context.register(key, feature);
	}

	private TTFeatureBootstrap() {}
}
