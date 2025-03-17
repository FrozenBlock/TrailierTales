package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class TTWorldgenConfigGui {
	private TTWorldgenConfigGui() {
		throw new UnsupportedOperationException("WorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = TTWorldgenConfig.get(true);
		var modifiedConfig = TTWorldgenConfig.getWithSync();
		Config<? extends TTWorldgenConfig> configInstance = TTWorldgenConfig.INSTANCE;
		var defaultConfig = TTWorldgenConfig.INSTANCE.defaultInstance();

		var catacombs = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(TTConstants.text("catacombs"), modifiedConfig.catacombs)
					.setDefaultValue(defaultConfig.catacombs)
					.setSaveConsumer(newValue -> config.catacombs = newValue)
					.setTooltip(TTConstants.tooltip("catacombs"))
					.build(),
				config.getClass(),
				"catacombs",
				configInstance
			)
		);

		var ruins = config.ruins;
		var modifiedRuins = modifiedConfig.ruins;

		var genericRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("generic_ruins"), modifiedRuins.generic)
				.setDefaultValue(defaultConfig.ruins.generic)
				.setSaveConsumer(newValue -> ruins.generic = newValue)
				.setTooltip(TTConstants.tooltip("generic_ruins"))
				.build(),
			ruins.getClass(),
			"generic",
			configInstance
		);
		var snowyRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("snowy_ruins"), modifiedRuins.snowy)
				.setDefaultValue(defaultConfig.ruins.snowy)
				.setSaveConsumer(newValue -> ruins.snowy = newValue)
				.setTooltip(TTConstants.tooltip("snowy_ruins"))
				.build(),
			ruins.getClass(),
			"snowy",
			configInstance
		);
		var jungleRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("jungle_ruins"), modifiedRuins.jungle)
				.setDefaultValue(defaultConfig.ruins.jungle)
				.setSaveConsumer(newValue -> ruins.jungle = newValue)
				.setTooltip(TTConstants.tooltip("jungle_ruins"))
				.build(),
			ruins.getClass(),
			"jungle",
			configInstance
		);
		var savannaRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("savanna_ruins"), modifiedRuins.savanna)
				.setDefaultValue(defaultConfig.ruins.savanna)
				.setSaveConsumer(newValue -> ruins.savanna = newValue)
				.setTooltip(TTConstants.tooltip("savanna_ruins"))
				.build(),
			ruins.getClass(),
			"savanna",
			configInstance
		);
		var desertRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("desert_ruins"), modifiedRuins.desert)
				.setDefaultValue(defaultConfig.ruins.desert)
				.setSaveConsumer(newValue -> ruins.desert = newValue)
				.setTooltip(TTConstants.tooltip("desert_ruins"))
				.build(),
			ruins.getClass(),
			"desert",
			configInstance
		);
		var badlandsRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("badlands_ruins"), modifiedRuins.badlands)
				.setDefaultValue(defaultConfig.ruins.badlands)
				.setSaveConsumer(newValue -> ruins.badlands = newValue)
				.setTooltip(TTConstants.tooltip("badlands_ruins"))
				.build(),
			ruins.getClass(),
			"badlands",
			configInstance
		);
		var deepslateRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("deepslate_ruins"), modifiedRuins.deepslate)
				.setDefaultValue(defaultConfig.ruins.deepslate)
				.setSaveConsumer(newValue -> ruins.deepslate = newValue)
				.setTooltip(TTConstants.tooltip("deepslate_ruins"))
				.build(),
			ruins.getClass(),
			"deepslate",
			configInstance
		);

		var ruinsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("ruins"),
			false,
			TTConstants.tooltip("ruins"),
			genericRuins, jungleRuins, savannaRuins, desertRuins, badlandsRuins, deepslateRuins
		);

		var vegetation = config.vegetation;
		var modifiedVegetation = modifiedConfig.vegetation;

		var generateTorchflower = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("generate_torchflower"), modifiedVegetation.generateTorchflower)
				.setDefaultValue(defaultConfig.vegetation.generateTorchflower)
				.setSaveConsumer(newValue -> vegetation.generateTorchflower = newValue)
				.setTooltip(TTConstants.tooltip("generate_torchflower"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generateTorchflower",
			configInstance
		);
		var generatePitcher = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("generate_pitcher"), modifiedVegetation.generatePitcher)
				.setDefaultValue(defaultConfig.vegetation.generatePitcher)
				.setSaveConsumer(newValue -> vegetation.generatePitcher = newValue)
				.setTooltip(TTConstants.tooltip("generate_pitcher"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generatePitcher",
			configInstance
		);
		var generateCyanRose = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("generate_cyan_rose"), modifiedVegetation.generateCyanRose)
				.setDefaultValue(defaultConfig.vegetation.generateCyanRose)
				.setSaveConsumer(newValue -> vegetation.generateCyanRose = newValue)
				.setTooltip(TTConstants.tooltip("generate_cyan_rose"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generateCyanRose",
			configInstance
		);
		var generateManedrop = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("generate_manedrop"), modifiedVegetation.generateManedrop)
				.setDefaultValue(defaultConfig.vegetation.generateManedrop)
				.setSaveConsumer(newValue -> vegetation.generateManedrop = newValue)
				.setTooltip(TTConstants.tooltip("generate_manedrop"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generateManedrop",
			configInstance
		);
		var generateDawntrail = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("generate_dawntrail"), modifiedVegetation.generateDawntrail)
				.setDefaultValue(defaultConfig.vegetation.generateDawntrail)
				.setSaveConsumer(newValue -> vegetation.generateDawntrail = newValue)
				.setTooltip(TTConstants.tooltip("generate_dawntrail"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generateDawntrail",
			configInstance
		);

		var vegetationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("vegetation"),
			false,
			TTConstants.tooltip("vegetation"),
			generateTorchflower, generatePitcher, generateCyanRose, generateManedrop, generateDawntrail
		);

		var endCity = config.endCity;
		var modifiedEndCity = modifiedConfig.endCity;

		var crackedEndCity = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("cracked_end_city"), modifiedEndCity.generateCracked)
				.setDefaultValue(defaultConfig.endCity.generateCracked)
				.setSaveConsumer(newValue -> endCity.generateCracked = newValue)
				.setTooltip(TTConstants.tooltip("cracked_end_city"))
				.requireRestart()
				.build(),
			endCity.getClass(),
			"generateCracked",
			configInstance
		);
		var choralEndCity = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("choral_end_city"), modifiedEndCity.generateChoral)
				.setDefaultValue(defaultConfig.endCity.generateChoral)
				.setSaveConsumer(newValue -> endCity.generateChoral = newValue)
				.setTooltip(TTConstants.tooltip("choral_end_city"))
				.requireRestart()
				.build(),
			endCity.getClass(),
			"generateChoral",
			configInstance
		);
		var chiseledEndCity = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("chiseled_end_city"), modifiedEndCity.generateChiseled)
				.setDefaultValue(defaultConfig.endCity.generateChiseled)
				.setSaveConsumer(newValue -> endCity.generateChiseled = newValue)
				.setTooltip(TTConstants.tooltip("chiseled_end_city"))
				.requireRestart()
				.build(),
			endCity.getClass(),
			"generateChiseled",
			configInstance
		);

		var endCityCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("end_city"),
			false,
			TTConstants.tooltip("end_city"),
			crackedEndCity, choralEndCity, chiseledEndCity
		);
	}
}
