package net.frozenblock.trailiertales.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.datagen.advancement.TTAdvancementProvider;
import net.frozenblock.trailiertales.datagen.loot.TTBlockLootProvider;
import net.frozenblock.trailiertales.datagen.model.TTModelProvider;
import net.frozenblock.trailiertales.datagen.recipe.TTRecipeProvider;
import net.frozenblock.trailiertales.datagen.tag.TTBiomeTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTBlockTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTDamageTypeTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTEntityTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTGameEventTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTItemTagProvider;
import net.frozenblock.trailiertales.registry.RegisterStructureProcessors;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.worldgen.TrailierFeatureBootstrap;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

public final class TTDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		FrozenFeatureFlags.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS

		pack.addProvider(TTModelProvider::new);

		// DATA

		pack.addProvider(TTBlockLootProvider::new);
		pack.addProvider(TTRegistryProvider::new);
		pack.addProvider(TTBiomeTagProvider::new);
		pack.addProvider(TTBlockTagProvider::new);
		pack.addProvider(TTDamageTypeTagProvider::new);
		pack.addProvider(TTItemTagProvider::new);
		pack.addProvider(TTEntityTagProvider::new);
		pack.addProvider(TTGameEventTagProvider::new);
		pack.addProvider(TTRecipeProvider::new);
		pack.addProvider(TTAdvancementProvider::new);
	}

	@Override
	public void buildRegistry(@NotNull RegistrySetBuilder registryBuilder) {
		TrailierTalesSharedConstants.log("Registering Biomes for Trailier Tales", TrailierTalesSharedConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.CONFIGURED_FEATURE, TrailierFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, TrailierFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.PROCESSOR_LIST, RegisterStructureProcessors::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, RegisterStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, RegisterStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, RegisterStructures::bootstrapStructureSet);
	}

}
