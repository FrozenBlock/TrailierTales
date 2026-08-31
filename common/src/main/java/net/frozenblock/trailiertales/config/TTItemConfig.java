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

package net.frozenblock.trailiertales.config;

import net.frozenblock.lib.config.v2.config.ConfigData;
import net.frozenblock.lib.config.v2.config.ConfigSettings;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.frozenblock.lib.config.v2.registry.ID;
import net.frozenblock.trailiertales.TTConstants;

public final class TTItemConfig {
	public static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(TTConstants.id("item")), ConfigSettings.JSON5);

	public static final ConfigEntry<Boolean> SHERD_DUPLICATION_RECIPE = CONFIG.entry("sherdDuplicationRecipe", EntryType.BOOL, false);

	// BRUSH
	public static final ConfigEntry<Boolean> BRUSH_SMOOTH_ANIMATION = CONFIG.entry("brush/smoothAnimation", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> BRUSH_HALF_BRUSH_EFFECTS = CONFIG.entry("brush/halsBrushEffects", EntryType.BOOL, true);
}
