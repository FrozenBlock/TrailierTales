/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.impl.sherd.DecoratedPotPatternRegistryEntrypoint;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import org.jetbrains.annotations.NotNull;

public final class TTDecoratedPotPatternRegistry implements DecoratedPotPatternRegistryEntrypoint {

	@Override
	public void bootstrap(Registry<DecoratedPotPattern> registry) {
		register(registry, "aurora");
		register(registry, "bait");
		register(registry, "bloom");
		register(registry, "bolt");
		register(registry, "bullseye");
		register(registry, "carrier");
		register(registry, "cluck");
		register(registry, "crawl");
		register(registry, "crescent");
		register(registry, "cultivator");
		register(registry, "drought");
		register(registry, "enclosure");
		register(registry, "essence");
		register(registry, "eye");
		register(registry, "focus");
		register(registry, "frost");
		register(registry, "hare");
		register(registry, "height");
		register(registry, "hump");
		register(registry, "illuminator");
		register(registry, "incidence");
		register(registry, "lumber");
		register(registry, "navigator");
		register(registry, "needles");
		register(registry, "omen");
		register(registry, "plume");
		register(registry, "protection");
		register(registry, "shed");
		register(registry, "shine");
		register(registry, "shower");
		register(registry, "spade");
		register(registry, "sprout");
		register(registry, "vessel");
		register(registry, "wither");
	}

	public static void register(@NotNull Registry<DecoratedPotPattern> registry, String sherdName) {
		ResourceLocation location = TTConstants.id(sherdName + "_pottery_pattern");
		DecoratedPotPatternRegistryEntrypoint.register(
			registry,
			ResourceKey.create(Registries.DECORATED_POT_PATTERN, location),
			location
		);
	}

}
