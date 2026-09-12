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

package net.frozenblock.trailiertales.data;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

final class TTRegistryProvider extends FabricDynamicRegistryProvider {

	TTRegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.DAMAGE_TYPE));
		entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
		entries.addAll(registries.lookupOrThrow(Registries.JUKEBOX_SONG));
		entries.addAll(registries.lookupOrThrow(Registries.TRIM_PATTERN));
		entries.addAll(registries.lookupOrThrow(Registries.BANNER_PATTERN));
		entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
		entries.addAll(asLookup(entries.placedFeatures()));
		entries.addAll(registries.lookupOrThrow(Registries.BIOME));
		entries.addAll(registries.lookupOrThrow(Registries.NOISE));
		entries.addAll(registries.lookupOrThrow(Registries.PROCESSOR_LIST));
		entries.addAll(registries.lookupOrThrow(Registries.TEMPLATE_POOL));
		entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE));
		entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE_SET));
		entries.addAll(registries.lookupOrThrow(Registries.VILLAGER_TRADE));

		// FrozenLib Dynamic Registries
		entries.addAll(registries.lookupOrThrow(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER));
		entries.addAll(registries.lookupOrThrow(FrozenLibRegistries.SOUND_TYPE_OVERRIDE));
		entries.addAll(registries.lookupOrThrow(FrozenLibRegistries.CLIP_GROUP));
	}

	public static <T> HolderLookup.RegistryLookup<T> asLookup(HolderGetter<T> getter) {
		return (HolderLookup.RegistryLookup<T>) getter;
	}

	@Override
	public String getName() {
		return "Trailier Tales Dynamic Registries";
	}
}
