/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
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

package net.frozenblock.trailiertales.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.datagen.advancement.TTAdvancementProvider;
import net.frozenblock.trailiertales.datagen.loot.TTBlockLootProvider;
import net.frozenblock.trailiertales.datagen.recipe.TTRecipeProvider;
import net.frozenblock.trailiertales.datagen.tag.TTBiomeTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTBlockTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTDamageTypeTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTEntityTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTGameEventTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTItemTagProvider;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.worldgen.Luna120FeatureBootstrap;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

public final class TTDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		FrozenFeatureFlags.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

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

		registryBuilder.add(Registries.CONFIGURED_FEATURE, Luna120FeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, Luna120FeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.PROCESSOR_LIST, RegisterStructures::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, RegisterStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, RegisterStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, RegisterStructures::bootstrapStructureSet);
	}

}
