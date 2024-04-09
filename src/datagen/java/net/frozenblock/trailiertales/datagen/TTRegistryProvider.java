package net.frozenblock.trailiertales.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.trailiertales.worldgen.TrailierFeatureBootstrap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

final class TTRegistryProvider extends FabricDynamicRegistryProvider {

	TTRegistryProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(@NotNull HolderLookup.Provider registries, @NotNull Entries entries) {
		final var damageTypes = registries.lookupOrThrow(Registries.DAMAGE_TYPE);

		entries.addAll(damageTypes);

		TrailierFeatureBootstrap.bootstrap(entries);
	}

	@Override
	@NotNull
	public String getName() {
		return "Trailier Tales Dynamic Registries";
	}
}
