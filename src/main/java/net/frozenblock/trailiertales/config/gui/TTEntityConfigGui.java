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

package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class TTEntityConfigGui {
	private TTEntityConfigGui() {
		throw new UnsupportedOperationException("TTEntityConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = TTEntityConfig.get(true);
		var modifiedConfig = TTEntityConfig.getWithSync();
		Config<? extends TTEntityConfig> configInstance = TTEntityConfig.INSTANCE;
		var defaultConfig = TTEntityConfig.INSTANCE.defaultInstance();

		var apparition = config.apparition;
		var modifiedApparition = modifiedConfig.apparition;

		var ignore_mob_griefing = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("ignore_mob_griefing"), modifiedApparition.ignore_mob_griefing)
				.setDefaultValue(defaultConfig.apparition.ignore_mob_griefing)
				.setSaveConsumer(newValue -> apparition.ignore_mob_griefing = newValue)
				.setTooltip(TTConstants.tooltip("ignore_mob_griefing"))
				.build(),
			apparition.getClass(),
			"ignore_mob_griefing",
			configInstance
		);

		var apparitionCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("apparition"),
			false,
			TTConstants.tooltip("apparition"),
			ignore_mob_griefing
		);

		var sniffer = config.sniffer;
		var modifiedSniffer = modifiedConfig.sniffer;

		var snifferDigsCyanRoseSeeds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("sniffer_digs_cyan_rose_seeds"), modifiedSniffer.cyan_rose_seeds)
				.setDefaultValue(defaultConfig.sniffer.cyan_rose_seeds)
				.setSaveConsumer(newValue -> sniffer.cyan_rose_seeds = newValue)
				.setTooltip(TTConstants.tooltip("sniffer_digs_cyan_rose_seeds"))
				.requireRestart()
				.build(),
			sniffer.getClass(),
			"dig_cyan_rose_seeds",
			configInstance
		);
		var snifferDigsManedropGerms = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("sniffer_digs_manedrop_germs"), modifiedSniffer.manedrop_germs)
				.setDefaultValue(defaultConfig.sniffer.manedrop_germs)
				.setSaveConsumer(newValue -> sniffer.manedrop_germs = newValue)
				.setTooltip(TTConstants.tooltip("sniffer_digs_manedrop_germs"))
				.requireRestart()
				.build(),
			sniffer.getClass(),
			"dig_manedrop_germs",
			configInstance
		);
		var sniffersDigDawntrailSeeds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("sniffer_digs_dawntrail_seeds"), modifiedSniffer.dawntrail_seeds)
				.setDefaultValue(defaultConfig.sniffer.dawntrail_seeds)
				.setSaveConsumer(newValue -> sniffer.dawntrail_seeds = newValue)
				.setTooltip(TTConstants.tooltip("sniffer_digs_dawntrail_seeds"))
				.requireRestart()
				.build(),
			sniffer.getClass(),
			"dig_dawntrail_seeds",
			configInstance
		);
		var spawnSniffer = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("sniffer_spawns_naturally"), modifiedSniffer.spawn)
				.setDefaultValue(defaultConfig.sniffer.spawn)
				.setSaveConsumer(newValue -> sniffer.spawn = newValue)
				.setTooltip(TTConstants.tooltip("sniffer_spawns_naturally"))
				.requireRestart()
				.build(),
			sniffer.getClass(),
			"spawn_sniffer",
			configInstance
		);

		var snifferCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("sniffer"),
			false,
			TTConstants.tooltip("sniffer"),
			snifferDigsCyanRoseSeeds, snifferDigsManedropGerms, sniffersDigDawntrailSeeds, spawnSniffer
		);

		var villager = config.villager;
		var modifiedVillager = modifiedConfig.villager;

		var villagersSellCatacombsMap = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("villagers_sell_catacombs_map"), modifiedVillager.sell_catacombs_map)
				.setDefaultValue(defaultConfig.villager.sell_catacombs_map)
				.setSaveConsumer(newValue -> villager.sell_catacombs_map = newValue)
				.setTooltip(TTConstants.tooltip("villagers_sell_catacombs_map"))
				.requireRestart()
				.build(),
			villager.getClass(),
			"villagers_sell_catacombs_map",
			configInstance
		);

		var villagerCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("villager"),
			false,
			TTConstants.tooltip("villager"),
			villagersSellCatacombsMap
		);

		var armorStand = config.armorStand;
		var modifiedArmorStand = modifiedConfig.armorStand;

		var armorStandArms = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("armor_stand_arms"), modifiedArmorStand.armor_stand_arms)
				.setDefaultValue(defaultConfig.armorStand.armor_stand_arms)
				.setSaveConsumer(newValue -> armorStand.armor_stand_arms = newValue)
				.setTooltip(TTConstants.tooltip("armor_stand_arms"))
				.requireRestart()
				.build(),
			armorStand.getClass(),
			"armor_stand_arms",
			configInstance
		);

		var armorStandCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("armor_stand"),
			false,
			TTConstants.tooltip("armor_stand"),
			armorStandArms
		);
	}
}
