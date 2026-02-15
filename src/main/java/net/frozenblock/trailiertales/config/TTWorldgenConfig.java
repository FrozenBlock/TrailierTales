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

public final class TTWorldgenConfig {
	public static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(TTConstants.id("worldgen")), ConfigSettings.JSON5);

	public static final ConfigEntry<Boolean> CATACOMBS_GENERATION = CONFIG.entry("catacombs", EntryType.BOOL, true);

	// RUINS GENERATION
	public static final ConfigEntry<Boolean> GENERIC_RUINS_GENERATION = CONFIG.entry("ruins/generic", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SNOWY_RUINS_GENERATION = CONFIG.entry("ruins/snowy", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> JUNGLE_RUINS_GENERATION = CONFIG.entry("ruins/jungle", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SAVANNA_RUINS_GENERATION = CONFIG.entry("ruins/savanna", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> DESERT_RUINS_GENERATION = CONFIG.entry("ruins/desert", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> BADLANDS_RUINS_GENERATION = CONFIG.entry("ruins/badlands", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> DEEPSLATE_RUINS_GENERATION = CONFIG.entry("ruins/deepslate", EntryType.BOOL, true);

	// VEGETATION GENERATION
	public static final ConfigEntry<Boolean> TORCHFLOWER_GENERATION = CONFIG.entryBuilder("vegetation/generateTorchflower", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> PITCHER_GENERATION = CONFIG.entryBuilder("vegetation/generatePitcher", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> CYAN_ROSE_GENERATION = CONFIG.entryBuilder("vegetation/generateCyanRose", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> MANEDROP_GENERATION = CONFIG.entryBuilder("vegetation/generateManedrop", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> GUZMANIA_GENERATION = CONFIG.entryBuilder("vegetation/generateGuzmania", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> DAWNTRAIL_GENERATION = CONFIG.entryBuilder("vegetation/generateDawntrail", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> LITHOPS_GENERATION = CONFIG.entryBuilder("vegetation/generateLithops", EntryType.BOOL, false).requireRestart().build();

	// END CITY GENERATION
	public static final ConfigEntry<Boolean> END_CITY_CRACKED_GENERATION = CONFIG.entryBuilder("endCity/generateCracked", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> END_CITY_CHORAL_GENERATION = CONFIG.entryBuilder("endCity/generateChoral", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> END_CITY_CHISELED_GENERATION = CONFIG.entryBuilder("endCity/generateChiseled", EntryType.BOOL, true).requireRestart().build();
}
