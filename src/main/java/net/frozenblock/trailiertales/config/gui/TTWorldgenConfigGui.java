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
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.booleanEntry;

@Environment(EnvType.CLIENT)
public final class TTWorldgenConfigGui {

	private TTWorldgenConfigGui() {
		throw new UnsupportedOperationException("TTWorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		category.addEntry(booleanEntry(entryBuilder, "catacombs", TTWorldgenConfig.CATACOMBS_GENERATION));

		// RUINS GENERATION
		var genericRuins = booleanEntry(entryBuilder, "generic_ruins", TTWorldgenConfig.GENERIC_RUINS_GENERATION);
		var snowyRuins = booleanEntry(entryBuilder, "snowy_ruins", TTWorldgenConfig.SNOWY_RUINS_GENERATION);
		var jungleRuins = booleanEntry(entryBuilder, "jungle_ruins", TTWorldgenConfig.JUNGLE_RUINS_GENERATION);
		var savannaRuins = booleanEntry(entryBuilder, "savanna_ruins", TTWorldgenConfig.SAVANNA_RUINS_GENERATION);
		var desertRuins = booleanEntry(entryBuilder, "desert_ruins", TTWorldgenConfig.DESERT_RUINS_GENERATION);
		var badlandsRuins = booleanEntry(entryBuilder, "badlands_ruins", TTWorldgenConfig.BADLANDS_RUINS_GENERATION);
		var deepslateRuins = booleanEntry(entryBuilder, "deepslate_ruins", TTWorldgenConfig.DEEPSLATE_RUINS_GENERATION);

		FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("ruins"),
			false,
			TTConstants.tooltip("ruins"),
			genericRuins, snowyRuins, jungleRuins, savannaRuins, desertRuins, badlandsRuins, deepslateRuins
		);

		// VEGETATION GENERATION
		var generateTorchflower = booleanEntry(entryBuilder, "generate_torchflower", TTWorldgenConfig.TORCHFLOWER_GENERATION);
		var generatePitcher = booleanEntry(entryBuilder, "generate_pitcher", TTWorldgenConfig.PITCHER_GENERATION);
		var generateCyanRose = booleanEntry(entryBuilder, "generate_cyan_rose", TTWorldgenConfig.CYAN_ROSE_GENERATION);
		var generateManedrop = booleanEntry(entryBuilder, "generate_manedrop", TTWorldgenConfig.MANEDROP_GENERATION);
		var generateGuzmania = booleanEntry(entryBuilder, "generate_guzmania", TTWorldgenConfig.GUZMANIA_GENERATION);
		var generateDawntrail = booleanEntry(entryBuilder, "generate_dawntrail", TTWorldgenConfig.DAWNTRAIL_GENERATION);
		var generateLithops = booleanEntry(entryBuilder, "generate_lithops", TTWorldgenConfig.LITHOPS_GENERATION);

		FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("vegetation"),
			false,
			TTConstants.tooltip("vegetation"),
			generateTorchflower, generatePitcher, generateCyanRose, generateManedrop, generateGuzmania, generateLithops, generateDawntrail
		);

		// END CITY GENERATION
		var crackedEndCity = booleanEntry(entryBuilder, "cracked_end_city", TTWorldgenConfig.END_CITY_CRACKED_GENERATION);
		var choralEndCity = booleanEntry(entryBuilder, "choral_end_city", TTWorldgenConfig.END_CITY_CHORAL_GENERATION);
		var chiseledEndCity = booleanEntry(entryBuilder, "chiseled_end_city", TTWorldgenConfig.END_CITY_CHISELED_GENERATION);

		FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("end_city"),
			false,
			TTConstants.tooltip("end_city"),
			crackedEndCity, choralEndCity, chiseledEndCity
		);
	}
}
