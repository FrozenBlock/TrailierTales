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

public final class TTBlockConfig {
	public static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(TTConstants.id("block")), ConfigSettings.JSON5);

	// SUSPICIOUS BLOCKS
	public static final ConfigEntry<Boolean> SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS = CONFIG.unsyncableEntry("suspiciousBlocks/smoothAnimations", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SUSPICIOUS_BLOCK_ACCESSIBILITY_PARTICLES = CONFIG.unsyncableEntry("suspiciousBlocks/accessibilityParticles", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> SUSPICIOUS_BLOCK_PLACE_ITEMS = CONFIG.entry("suspiciousBlocks/placeItems", EntryType.BOOL, false);

	// COFFIN
	public static final ConfigEntry<Boolean> COFFIN_IGNORES_DO_MOB_SPAWNING_GAMERULE = CONFIG.entry("coffin/ignoreDoMobSpawningGamerule", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> COFFIN_WOBBLING = CONFIG.entry("coffin/wobbling", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> COFFIN_WOBBLE_ACTIVATION = CONFIG.entry("coffin/wobbleActivation", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> COFFIN_WOBBLE_LOOT = CONFIG.entry("coffin/wobbleLoot", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> COFFIN_WOBBLE_POTION_SPAWNING = CONFIG.entry("coffin/wobblePotion", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> COFFIN_WOBBLE_EXPERIENCE_BOTTLE_SPAWNING = CONFIG.entry("coffin/wobbleExperienceBottle", EntryType.BOOL, false);

	// BLOCK SOUNDS
	public static final ConfigEntry<Boolean> UNPOLISHED_BRICKS_SOUNDS = CONFIG.entry("blockSounds/unpolishedBricksSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> POLISHED_BRICKS_SOUNDS = CONFIG.entry("blockSounds/polishedBricksSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> POLISHED_SOUNDS = CONFIG.entry("blockSounds/polishedSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> POLISHED_BASALT_SOUNDS = CONFIG.entry("blockSounds/polishedBasaltSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> POLISHED_DEEPSLATE_SOUNDS = CONFIG.entry("blockSounds/polishedDeepslateSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> POLISHED_TUFF_SOUNDS = CONFIG.entry("blockSounds/polishedTuffSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> POLISHED_CALCITE_SOUNDS = CONFIG.entry("blockSounds/polishedCalciteSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> CALCITE_BRICKS_SOUNDS = CONFIG.entry("blockSounds/calciteBricksSounds", EntryType.BOOL, true);
}
