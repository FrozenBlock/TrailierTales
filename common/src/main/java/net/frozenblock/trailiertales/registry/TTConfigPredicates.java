/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;

public final class TTConfigPredicates {
	public static final ResourceKey<ConfigPredicate> WILDER_WILD_CLAY_SOUNDS = create("wilder_wild_clay_sounds");
	public static final ResourceKey<ConfigPredicate> WILDER_WILD_GRAVEL_SOUNDS = create("wilder_wild_gravel_sounds");

	private static ResourceKey<ConfigPredicate> create(String name) {
		return ResourceKey.create(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER, TTConstants.id(name));
	}

	public static boolean wwClaySounds(RegistryAccess registries) {
		return ConfigPredicate.lookupAndTest(registries, WILDER_WILD_CLAY_SOUNDS);
	}

	public static boolean wwGravelSounds(RegistryAccess registries) {
		return ConfigPredicate.lookupAndTest(registries, WILDER_WILD_GRAVEL_SOUNDS);
	}

	private TTConfigPredicates() {}
}
