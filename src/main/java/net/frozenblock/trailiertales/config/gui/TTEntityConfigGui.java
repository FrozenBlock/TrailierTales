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
