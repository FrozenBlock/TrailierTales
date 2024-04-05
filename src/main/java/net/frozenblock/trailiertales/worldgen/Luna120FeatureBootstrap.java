package net.frozenblock.trailiertales.worldgen;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

public class Luna120FeatureBootstrap {

	public static final ResourceKey<ConfiguredFeature<?, ?>> TORCHFLOWER = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TrailierTalesSharedConstants.MOD_ID, "torchflower"));
	public static final ResourceKey<PlacedFeature> TORCHFLOWER_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TrailierTalesSharedConstants.MOD_ID, "torchflower_placed"));

	public static void bootstrapConfigured(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		register(entries, TORCHFLOWER, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(4, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TORCHFLOWER)))));
	}

	public static void bootstrapPlaced(@NotNull BootstrapContext<PlacedFeature> entries) {
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		register(entries, TORCHFLOWER_PLACED,
			configuredFeatures.getOrThrow(TORCHFLOWER),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
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

	public static void bootstrap(FabricDynamicRegistryProvider.Entries entries) {
		final var configuredFeatures = asLookup(entries.getLookup(Registries.CONFIGURED_FEATURE));
		final var placedFeatures = asLookup(entries.placedFeatures());
		final var biomes = asLookup(entries.getLookup(Registries.BIOME));
		final var noises = asLookup(entries.getLookup(Registries.NOISE));
		final var processorLists = asLookup(entries.getLookup(Registries.PROCESSOR_LIST));
		final var templatePools = asLookup(entries.getLookup(Registries.TEMPLATE_POOL));
		final var structures = asLookup(entries.getLookup(Registries.STRUCTURE));
		final var structureSets = asLookup(entries.getLookup(Registries.STRUCTURE_SET));

		TrailierTalesSharedConstants.log("Adding finalized configured features to datagen", true);
		entries.addAll(configuredFeatures);
		TrailierTalesSharedConstants.log("Adding finalized placed features to datagen", true);
		entries.addAll(placedFeatures);
		TrailierTalesSharedConstants.log("Adding finalized biomes to datagen", true);
		entries.addAll(biomes);
		TrailierTalesSharedConstants.log("Adding finalized noises to datagen", true);
		entries.addAll(noises);
		TrailierTalesSharedConstants.log("Adding finalized processor lists to datagen", true);
		entries.addAll(processorLists);
		TrailierTalesSharedConstants.log("Adding finalized template pools to datagen", true);
		entries.addAll(templatePools);
		TrailierTalesSharedConstants.log("Adding finalized structures to datagen", true);
		entries.addAll(structures);
		TrailierTalesSharedConstants.log("Adding finalized structure sets to datagen", true);
		entries.addAll(structureSets);
	}

	public static <T> HolderLookup.RegistryLookup<T> asLookup(HolderGetter<T> getter) {
		return (HolderLookup.RegistryLookup<T>) getter;
	}
}
