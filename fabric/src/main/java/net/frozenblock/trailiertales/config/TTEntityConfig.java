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

public final class TTEntityConfig {
	public static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(TTConstants.id("entity")), ConfigSettings.JSON5);

	// APPARITION
	public static final ConfigEntry<Boolean> APPARITION_PICKS_UP_ITEMS = CONFIG.entry("apparition/pickUpItems", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> APPARITION_CATCHES_PROJECTILES = CONFIG.entry("apparition/catchProjectile", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> APPARITION_IGNORES_MOB_GRIEFING = CONFIG.entry("apparition/ignoreMobGriefing", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> APPARITION_HYPNOTIZES_MOBS = CONFIG.entry("apparition/hypnotizeMobs", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> APPARITION_HAUNTS_PLAYERS = CONFIG.entry("apparition/hauntPlayers", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> APPARITION_HAUNTED_COFFINS = CONFIG.entry("apparition/hauntedCoffins", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> APPARITION_HAUNTED_FOG = CONFIG.entry("apparition/hauntedFog", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> APPARITION_HAUNTED_LIGHTMAP = CONFIG.entry("apparition/hauntedLightmap", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> APPARITION_HAUNTED_SOUNDS = CONFIG.entry("apparition/hauntedSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> APPARITION_HAUNTED_HUD = CONFIG.entry("apparition/hauntedHud", EntryType.BOOL, true);

	// SNIFFER
	public static final ConfigEntry<Boolean> SNIFFER_DIGS_CYAN_ROSE_SEEDS = CONFIG.entryBuilder("sniffer/digCyanRoseSeeds", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNIFFER_DIGS_MANEDROP_GERMS = CONFIG.entryBuilder("sniffer/digManedropGerms", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNIFFER_DIGS_GUZMANIA_SEEDS = CONFIG.entryBuilder("sniffer/digGuzmaniaSeeds", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNIFFER_DIGS_DAWNTRAIL_SEEDS = CONFIG.entryBuilder("sniffer/digDawntrailSeeds", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SNIFFER_DIGS_LITHOPS_SEEDS = CONFIG.entryBuilder("sniffer/digLithopsSeeds", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> SPAWN_SNIFFERS = CONFIG.entryBuilder("sniffer/spawnSniffer", EntryType.BOOL, false).requireRestart().build();

	// VILLAGER
	public static final ConfigEntry<Boolean> VILLAGER_SELLS_CATACOMBS_MAP = CONFIG.entryBuilder("villager/sellCatacombsMap", EntryType.BOOL, true).requireRestart().build();

	// ARMOR STAND
	public static final ConfigEntry<Boolean> ARMOR_STAND_HAS_ARMS = CONFIG.entryBuilder("armorStand/hasArms", EntryType.BOOL, true).requireRestart().build();
}
