/*
 * Copyright 2025 FrozenBlock
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

		var picksUpItems = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("picks_up_items"), modifiedApparition.picks_up_items)
				.setDefaultValue(defaultConfig.apparition.picks_up_items)
				.setSaveConsumer(newValue -> apparition.picks_up_items = newValue)
				.setTooltip(TTConstants.tooltip("picks_up_items"))
				.build(),
			apparition.getClass(),
			"picks_up_items",
			configInstance
		);
		var catchesProjectiles = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("catches_projectiles"), modifiedApparition.catches_projectiles)
				.setDefaultValue(defaultConfig.apparition.catches_projectiles)
				.setSaveConsumer(newValue -> apparition.catches_projectiles = newValue)
				.setTooltip(TTConstants.tooltip("catches_projectiles"))
				.build(),
			apparition.getClass(),
			"catches_projectiles",
			configInstance
		);
		var ignoreMobGriefing = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("ignore_mob_griefing"), modifiedApparition.ignore_mob_griefing)
				.setDefaultValue(defaultConfig.apparition.ignore_mob_griefing)
				.setSaveConsumer(newValue -> apparition.ignore_mob_griefing = newValue)
				.setTooltip(TTConstants.tooltip("ignore_mob_griefing"))
				.build(),
			apparition.getClass(),
			"ignore_mob_griefing",
			configInstance
		);
		var hypnotizesMobs = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("hypnotizes_mobs"), modifiedApparition.hypnotizes_mobs)
				.setDefaultValue(defaultConfig.apparition.hypnotizes_mobs)
				.setSaveConsumer(newValue -> apparition.hypnotizes_mobs = newValue)
				.setTooltip(TTConstants.tooltip("hypnotizes_mobs"))
				.build(),
			apparition.getClass(),
			"hypnotizes_mobs",
			configInstance
		);
		var hauntsPlayers = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("haunts_players"), modifiedApparition.haunts_players)
				.setDefaultValue(defaultConfig.apparition.haunts_players)
				.setSaveConsumer(newValue -> apparition.haunts_players = newValue)
				.setTooltip(TTConstants.tooltip("haunts_players"))
				.build(),
			apparition.getClass(),
			"haunts_players",
			configInstance
		);
		var hauntedCoffins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("haunted_coffins"), modifiedApparition.haunted_coffins)
				.setDefaultValue(defaultConfig.apparition.haunted_coffins)
				.setSaveConsumer(newValue -> apparition.haunted_coffins = newValue)
				.setTooltip(TTConstants.tooltip("haunted_coffins"))
				.build(),
			apparition.getClass(),
			"haunted_coffins",
			configInstance
		);
		var hauntedFog = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("haunted_fog"), modifiedApparition.haunted_fog)
				.setDefaultValue(defaultConfig.apparition.haunted_fog)
				.setSaveConsumer(newValue -> apparition.haunted_fog = newValue)
				.setTooltip(TTConstants.tooltip("haunted_fog"))
				.build(),
			apparition.getClass(),
			"haunted_fog",
			configInstance
		);
		var hauntedLightmap = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("haunted_lightmap"), modifiedApparition.haunted_lightmap)
				.setDefaultValue(defaultConfig.apparition.haunted_lightmap)
				.setSaveConsumer(newValue -> apparition.haunted_lightmap = newValue)
				.setTooltip(TTConstants.tooltip("haunted_lightmap"))
				.build(),
			apparition.getClass(),
			"haunted_lightmap",
			configInstance
		);
		var hauntedSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("haunted_sounds"), modifiedApparition.haunted_sounds)
				.setDefaultValue(defaultConfig.apparition.haunted_sounds)
				.setSaveConsumer(newValue -> apparition.haunted_sounds = newValue)
				.setTooltip(TTConstants.tooltip("haunted_sounds"))
				.build(),
			apparition.getClass(),
			"haunted_sounds",
			configInstance
		);
		var hauntedHUD = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("haunted_hud"), modifiedApparition.haunted_hud)
				.setDefaultValue(defaultConfig.apparition.haunted_hud)
				.setSaveConsumer(newValue -> apparition.haunted_hud = newValue)
				.setTooltip(TTConstants.tooltip("haunted_hud"))
				.build(),
			apparition.getClass(),
			"haunted_hud",
			configInstance
		);

		var apparitionCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("apparition"),
			false,
			TTConstants.tooltip("apparition"),
			picksUpItems, catchesProjectiles, ignoreMobGriefing, hypnotizesMobs,
			hauntsPlayers, hauntedCoffins, hauntedFog, hauntedLightmap, hauntedSounds, hauntedHUD
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
		var snifferDigsGuzmaniaSeeds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("sniffer_digs_guzmania_seeds"), modifiedSniffer.guzmania_seeds)
				.setDefaultValue(defaultConfig.sniffer.manedrop_germs)
				.setSaveConsumer(newValue -> sniffer.guzmania_seeds = newValue)
				.setTooltip(TTConstants.tooltip("sniffer_digs_guzmania_seeds"))
				.requireRestart()
				.build(),
			sniffer.getClass(),
			"dig_guzmania_seeds",
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
			snifferDigsCyanRoseSeeds, snifferDigsManedropGerms, snifferDigsGuzmaniaSeeds, sniffersDigDawntrailSeeds, spawnSniffer
		);

		var camel = config.camel;
		var modifiedCamel = modifiedConfig.camel;

		var spawnCamel = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("camel_spawns_naturally"), modifiedCamel.spawn)
				.setDefaultValue(defaultConfig.camel.spawn)
				.setSaveConsumer(newValue -> camel.spawn = newValue)
				.setTooltip(TTConstants.tooltip("camel_spawns_naturally"))
				.requireRestart()
				.build(),
			camel.getClass(),
			"spawn_camel",
			configInstance
		);

		var camelCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("camel"),
			false,
			TTConstants.tooltip("camel"),
			spawnCamel
		);

		var villager = config.villager;
		var modifiedVillager = modifiedConfig.villager;

		var villagersSellCatacombsMap = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("sell_catacombs_map"), modifiedVillager.sell_catacombs_map)
				.setDefaultValue(defaultConfig.villager.sell_catacombs_map)
				.setSaveConsumer(newValue -> villager.sell_catacombs_map = newValue)
				.setTooltip(TTConstants.tooltip("sell_catacombs_map"))
				.requireRestart()
				.build(),
			villager.getClass(),
			"sell_catacombs_map",
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
