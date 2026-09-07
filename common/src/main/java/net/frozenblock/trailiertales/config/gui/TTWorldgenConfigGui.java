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
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.booleanEntry;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class TTWorldgenConfigGui {

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "catacombs", TTWorldgenConfig.CATACOMBS_GENERATION));

		// RUINS GENERATION
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, TTConstants.text("ruins"),
			false,
			TTConstants.tooltip("ruins"),
			booleanEntry(builder, "generic_ruins", TTWorldgenConfig.GENERIC_RUINS_GENERATION),
			booleanEntry(builder, "snowy_ruins", TTWorldgenConfig.SNOWY_RUINS_GENERATION),
			booleanEntry(builder, "jungle_ruins", TTWorldgenConfig.JUNGLE_RUINS_GENERATION),
			booleanEntry(builder, "savanna_ruins", TTWorldgenConfig.SAVANNA_RUINS_GENERATION),
			booleanEntry(builder, "desert_ruins", TTWorldgenConfig.DESERT_RUINS_GENERATION),
			booleanEntry(builder, "badlands_ruins", TTWorldgenConfig.BADLANDS_RUINS_GENERATION),
			booleanEntry(builder, "deepslate_ruins", TTWorldgenConfig.DEEPSLATE_RUINS_GENERATION)
		);

		// VEGETATION GENERATION
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, TTConstants.text("vegetation"),
			false,
			TTConstants.tooltip("vegetation"),
			booleanEntry(builder, "generate_torchflower", TTWorldgenConfig.TORCHFLOWER_GENERATION),
			booleanEntry(builder, "generate_pitcher", TTWorldgenConfig.PITCHER_GENERATION),
			booleanEntry(builder, "generate_cyan_rose", TTWorldgenConfig.CYAN_ROSE_GENERATION),
			booleanEntry(builder, "generate_manedrop", TTWorldgenConfig.MANEDROP_GENERATION),
			booleanEntry(builder, "generate_guzmania", TTWorldgenConfig.GUZMANIA_GENERATION),
			booleanEntry(builder, "generate_dawntrail", TTWorldgenConfig.DAWNTRAIL_GENERATION),
			booleanEntry(builder, "generate_lithops", TTWorldgenConfig.LITHOPS_GENERATION)
		);

		// END CITY GENERATION
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, TTConstants.text("end_city"),
			false,
			TTConstants.tooltip("end_city"),
			booleanEntry(builder, "cracked_end_city", TTWorldgenConfig.END_CITY_CRACKED_GENERATION),
			booleanEntry(builder, "choral_end_city", TTWorldgenConfig.END_CITY_CHORAL_GENERATION),
			booleanEntry(builder, "chiseled_end_city", TTWorldgenConfig.END_CITY_CHISELED_GENERATION)
		);
	}

	private TTWorldgenConfigGui() {}
}
