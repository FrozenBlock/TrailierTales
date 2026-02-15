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

package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.trailiertales.TTConstants.text;
import static net.frozenblock.trailiertales.TTConstants.tooltip;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.booleanEntry;

@Environment(EnvType.CLIENT)
public final class TTBlockConfigGui {

	private TTBlockConfigGui() {
		throw new UnsupportedOperationException("TTBlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		// SUSPICIOUS BLOCKS
		var smoothSuspiciousBlocks = booleanEntry(entryBuilder, "smooth_suspicious_blocks", TTBlockConfig.SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS);
		var suspiciousBlockParticles = booleanEntry(entryBuilder, "suspicious_block_particles", TTBlockConfig.SUSPICIOUS_BLOCK_ACCESSIBILITY_PARTICLES);
		var placeItemsInSuspiciousBlocks = booleanEntry(entryBuilder, "place_items_in_suspicious_blocks", TTBlockConfig.SUSPICIOUS_BLOCK_PLACE_ITEMS);

		FrozenClothConfig.createSubCategory(entryBuilder, category, text("suspicious_blocks"),
			false,
			tooltip("suspicious_blocks"),
			smoothSuspiciousBlocks, suspiciousBlockParticles, placeItemsInSuspiciousBlocks
		);

		// COFFIN
		var ignoreDoMobSpawningGamerule = booleanEntry(entryBuilder, "ignore_do_mob_spawning_gamerule", TTBlockConfig.COFFIN_IGNORES_DO_MOB_SPAWNING_GAMERULE);
		var coffinWobble = booleanEntry(entryBuilder, "coffin_wobble", TTBlockConfig.COFFIN_WOBBLING);
		var coffinWobbleActivate = booleanEntry(entryBuilder, "coffin_wobble_activate", TTBlockConfig.COFFIN_WOBBLE_ACTIVATION);
		var coffinWobbleLoot = booleanEntry(entryBuilder, "coffin_wobble_loot", TTBlockConfig.COFFIN_WOBBLE_LOOT);
		var coffinWobblePotion = booleanEntry(entryBuilder, "coffin_wobble_potion", TTBlockConfig.COFFIN_WOBBLE_POTION_SPAWNING);
		var coffinWobbleExperienceBottle = booleanEntry(entryBuilder, "coffin_wobble_experience_bottle", TTBlockConfig.COFFIN_WOBBLE_EXPERIENCE_BOTTLE_SPAWNING);

		FrozenClothConfig.createSubCategory(entryBuilder, category, text("coffin"),
			false,
			tooltip("coffin"),
			ignoreDoMobSpawningGamerule,
			coffinWobble, coffinWobbleActivate, coffinWobbleExperienceBottle, coffinWobbleLoot, coffinWobblePotion
		);

		// BLOCK SOUNDS
		var unpolishedBricksSounds = booleanEntry(entryBuilder, "unpolished_bricks_sounds", TTBlockConfig.UNPOLISHED_BRICKS_SOUNDS);
		var polishedBricksSounds = booleanEntry(entryBuilder, "polished_bricks_sounds", TTBlockConfig.POLISHED_BRICKS_SOUNDS);
		var polishedSounds = booleanEntry(entryBuilder, "polished_sounds", TTBlockConfig.POLISHED_SOUNDS);
		var polishedBasaltSounds = booleanEntry(entryBuilder, "polished_basalt_sounds", TTBlockConfig.POLISHED_BASALT_SOUNDS);
		var polishedDeepslateSounds = booleanEntry(entryBuilder, "polished_deepslate_sounds", TTBlockConfig.POLISHED_DEEPSLATE_SOUNDS);
		var polishedTuffSounds = booleanEntry(entryBuilder, "polished_tuff_sounds", TTBlockConfig.POLISHED_TUFF_SOUNDS);
		var polishedCalciteSounds = booleanEntry(entryBuilder, "polished_calcite_sounds", TTBlockConfig.POLISHED_CALCITE_SOUNDS);
		var calciteBricksSounds = booleanEntry(entryBuilder, "calcite_bricks_sounds", TTBlockConfig.CALCITE_BRICKS_SOUNDS);

		FrozenClothConfig.createSubCategory(entryBuilder, category, text("block_sounds"),
			false,
			tooltip("block_sounds"),
			unpolishedBricksSounds, polishedBricksSounds, polishedSounds, polishedBasaltSounds, polishedDeepslateSounds,
			polishedTuffSounds, polishedCalciteSounds, calciteBricksSounds
		);
	}
}
