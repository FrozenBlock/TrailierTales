package net.frozenblock.trailiertales.worldgen;

import java.util.Arrays;
import java.util.List;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.NotNull;

public class TrailierFeatureBootstrap {

	public static final ResourceKey<ConfiguredFeature<?, ?>> TORCHFLOWER = ResourceKey.create
		(Registries.CONFIGURED_FEATURE,
			TrailierConstants.id("torchflower")
	);
	public static final ResourceKey<PlacedFeature> TORCHFLOWER_PLACED = ResourceKey.create(
		Registries.PLACED_FEATURE,
		TrailierConstants.id("torchflower")
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> PITCHER = ResourceKey.create
		(Registries.CONFIGURED_FEATURE,
			TrailierConstants.id("pitcher")
		);
	public static final ResourceKey<PlacedFeature> PITCHER_PLACED = ResourceKey.create(
		Registries.PLACED_FEATURE,
		TrailierConstants.id("pitcher")
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> CYAN_ROSE = ResourceKey.create
		(Registries.CONFIGURED_FEATURE,
			TrailierConstants.id("cyan_rose")
		);
	public static final ResourceKey<PlacedFeature> CYAN_ROSE_PLACED = ResourceKey.create(
		Registries.PLACED_FEATURE,
		TrailierConstants.id("cyan_rose")
	);

	public static void bootstrapConfigured(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		register(
			entries,
			TORCHFLOWER,
			Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				20,
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
				13,
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
				20,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CYAN_ROSE))
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
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		register(
			entries,
			PITCHER_PLACED,
			configuredFeatures.getOrThrow(PITCHER),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		register(
			entries,
			CYAN_ROSE_PLACED,
			configuredFeatures.getOrThrow(CYAN_ROSE),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
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
