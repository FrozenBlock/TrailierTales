package net.frozenblock.trailiertales.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

final class TTRegistryProvider extends FabricDynamicRegistryProvider {

	TTRegistryProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(@NotNull HolderLookup.Provider registries, @NotNull Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.DAMAGE_TYPE));
		entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
		entries.addAll(registries.lookupOrThrow(Registries.JUKEBOX_SONG));
		entries.addAll(registries.lookupOrThrow(Registries.TRIM_PATTERN));
		entries.addAll(registries.lookupOrThrow(Registries.BANNER_PATTERN));

		bootstrap(entries);
	}

	public static void bootstrap(FabricDynamicRegistryProvider.@NotNull Entries entries) {
		final var configuredFeatures = asLookup(entries.getLookup(Registries.CONFIGURED_FEATURE));
		final var placedFeatures = asLookup(entries.placedFeatures());
		final var biomes = asLookup(entries.getLookup(Registries.BIOME));
		final var noises = asLookup(entries.getLookup(Registries.NOISE));
		final var processorLists = asLookup(entries.getLookup(Registries.PROCESSOR_LIST));
		final var templatePools = asLookup(entries.getLookup(Registries.TEMPLATE_POOL));
		final var structures = asLookup(entries.getLookup(Registries.STRUCTURE));
		final var structureSets = asLookup(entries.getLookup(Registries.STRUCTURE_SET));

		TrailierConstants.log("Adding finalized configured features to datagen", true);
		entries.addAll(configuredFeatures);
		TrailierConstants.log("Adding finalized placed features to datagen", true);
		entries.addAll(placedFeatures);
		TrailierConstants.log("Adding finalized biomes to datagen", true);
		entries.addAll(biomes);
		TrailierConstants.log("Adding finalized noises to datagen", true);
		entries.addAll(noises);
		TrailierConstants.log("Adding finalized processor lists to datagen", true);
		entries.addAll(processorLists);
		TrailierConstants.log("Adding finalized template pools to datagen", true);
		entries.addAll(templatePools);
		TrailierConstants.log("Adding finalized structures to datagen", true);
		entries.addAll(structures);
		TrailierConstants.log("Adding finalized structure sets to datagen", true);
		entries.addAll(structureSets);
	}

	public static <T> HolderLookup.RegistryLookup<T> asLookup(HolderGetter<T> getter) {
		return (HolderLookup.RegistryLookup<T>) getter;
	}

	@Override
	@NotNull
	public String getName() {
		return "Trailier Tales Dynamic Registries";
	}
}
