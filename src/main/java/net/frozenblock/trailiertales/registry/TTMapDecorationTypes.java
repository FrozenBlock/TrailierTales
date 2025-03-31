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

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.jetbrains.annotations.NotNull;

public final class TTMapDecorationTypes {
	public static final Holder<MapDecorationType> CATACOMBS = register(
		"catacombs",
		"catacombs",
		true,
		3684151,
		false,
		true
	);

	public static void init() {
	}

	private static @NotNull Holder<MapDecorationType> register(String string, String string2, boolean showOnItemFrame, boolean trackCount) {
		return register(string, string2, showOnItemFrame, -1, trackCount, false);
	}

	private static @NotNull Holder<MapDecorationType> register(
		String string, String string2, boolean showOnItemFrame, int mapColor, boolean trackCount, boolean explorationMapElement
	) {
		ResourceKey<MapDecorationType> resourceKey = ResourceKey.create(Registries.MAP_DECORATION_TYPE, TTConstants.id(string));
		MapDecorationType mapDecorationType = new MapDecorationType(
			TTConstants.id(string2), showOnItemFrame, mapColor, explorationMapElement, trackCount
		);
		return Registry.registerForHolder(BuiltInRegistries.MAP_DECORATION_TYPE, resourceKey, mapDecorationType);
	}

}
