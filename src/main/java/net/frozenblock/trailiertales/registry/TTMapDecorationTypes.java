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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;

public final class TTMapDecorationTypes {
	public static final Holder<MapDecorationType> CATACOMBS = register(
		"catacombs",
		"catacombs",
		true,
		3684151,
		false,
		true
	);

	public static void init() {}

	private static Holder<MapDecorationType> register(String name, String assetName, boolean showOnItemFrame, boolean trackCount) {
		return register(name, assetName, showOnItemFrame, -1, trackCount, false);
	}

	private static Holder<MapDecorationType> register(
		String name, String assetName, boolean showOnItemFrame, int mapColor, boolean trackCount, boolean explorationMapElement
	) {
		final ResourceKey<MapDecorationType> key = ResourceKey.create(Registries.MAP_DECORATION_TYPE, TTConstants.id(name));
		final MapDecorationType mapDecorationType = new MapDecorationType(TTConstants.id(assetName), showOnItemFrame, mapColor, explorationMapElement, trackCount);
		return Registry.registerForHolder(BuiltInRegistries.MAP_DECORATION_TYPE, key, mapDecorationType);
	}
}
