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

package net.frozenblock.trailiertales.data.config;

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.config.v2.entry.predicates.FrozenLibConfigPredicates;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.trailiertales.registry.TTConfigPredicates;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;

public final class TTConfigPredicateProvider {

	public static void bootstrap(BootstrapContext<ConfigPredicate> context) {
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);

		context.register(
			TTConfigPredicates.WILDER_WILD_CLAY_SOUNDS,
			ConfigPredicate.withFallback(
				WWBlockConfig.CLAY_SOUNDS,
				WWBlockConfig.CLAY_SOUNDS.equalTo(true).asHolder(),
				configPredicates.getOrThrow(FrozenLibConfigPredicates.FALSE)
			)
		);

		context.register(
			TTConfigPredicates.WILDER_WILD_GRAVEL_SOUNDS,
			ConfigPredicate.withFallback(
				WWBlockConfig.GRAVEL_SOUNDS,
				WWBlockConfig.GRAVEL_SOUNDS.equalTo(true).asHolder(),
				configPredicates.getOrThrow(FrozenLibConfigPredicates.FALSE)
			)
		);
	}

	private TTConfigPredicateProvider() {}
}
