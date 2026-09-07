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
import net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper;
import static net.frozenblock.trailiertales.TTConstants.text;
import static net.frozenblock.trailiertales.TTConstants.tooltip;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.booleanEntry;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class TTBlockConfigGui {

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		// SUSPICIOUS BLOCKS
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, text("suspicious_blocks"),
			false,
			tooltip("suspicious_blocks"),
			booleanEntry(builder, "smooth_suspicious_blocks", TTBlockConfig.SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS),
			booleanEntry(builder, "suspicious_block_particles", TTBlockConfig.SUSPICIOUS_BLOCK_ACCESSIBILITY_PARTICLES),
			booleanEntry(builder, "place_items_in_suspicious_blocks", TTBlockConfig.SUSPICIOUS_BLOCK_PLACE_ITEMS)
		);

		// COFFIN
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, text("coffin"),
			false,
			tooltip("coffin"),
			booleanEntry(builder, "ignore_do_mob_spawning_gamerule", TTBlockConfig.COFFIN_IGNORES_DO_MOB_SPAWNING_GAMERULE),
			booleanEntry(builder, "coffin_wobble", TTBlockConfig.COFFIN_WOBBLING),
			booleanEntry(builder, "coffin_wobble_activate", TTBlockConfig.COFFIN_WOBBLE_ACTIVATION),
			booleanEntry(builder, "coffin_wobble_loot", TTBlockConfig.COFFIN_WOBBLE_LOOT),
			booleanEntry(builder, "coffin_wobble_potion", TTBlockConfig.COFFIN_WOBBLE_POTION_SPAWNING),
			booleanEntry(builder, "coffin_wobble_experience_bottle", TTBlockConfig.COFFIN_WOBBLE_EXPERIENCE_BOTTLE_SPAWNING)
		);

		// BLOCK SOUNDS
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, text("block_sounds"),
			false,
			tooltip("block_sounds"),
			booleanEntry(builder, "unpolished_bricks_sounds", TTBlockConfig.UNPOLISHED_BRICKS_SOUNDS),
			booleanEntry(builder, "polished_bricks_sounds", TTBlockConfig.POLISHED_BRICKS_SOUNDS),
			booleanEntry(builder, "polished_sounds", TTBlockConfig.POLISHED_SOUNDS),
			booleanEntry(builder, "polished_basalt_sounds", TTBlockConfig.POLISHED_BASALT_SOUNDS),
			booleanEntry(builder, "polished_deepslate_sounds", TTBlockConfig.POLISHED_DEEPSLATE_SOUNDS),
			booleanEntry(builder, "polished_tuff_sounds", TTBlockConfig.POLISHED_TUFF_SOUNDS),
			booleanEntry(builder, "polished_calcite_sounds", TTBlockConfig.POLISHED_CALCITE_SOUNDS),
			booleanEntry(builder, "calcite_bricks_sounds", TTBlockConfig.CALCITE_BRICKS_SOUNDS)
		);
	}

	private TTBlockConfigGui() {}
}
