package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.WorldgenConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WorldgenConfigGui {
	private WorldgenConfigGui() {
		throw new UnsupportedOperationException("WorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = WorldgenConfig.get(true);
		var modifiedConfig = WorldgenConfig.getWithSync();
		Config<? extends WorldgenConfig> configInstance = WorldgenConfig.INSTANCE;
		var defaultConfig = WorldgenConfig.INSTANCE.defaultInstance();

		var ruins = config.ruins;
		var modifiedRuins = modifiedConfig.ruins;

		var genericRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("generic_ruins"), modifiedRuins.generic)
				.setDefaultValue(defaultConfig.ruins.generic)
				.setSaveConsumer(newValue -> ruins.generic = newValue)
				.setTooltip(TrailierConstants.tooltip("generic_ruins"))
				.build(),
			ruins.getClass(),
			"generic",
			configInstance
		);
		var jungleRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("jungle_ruins"), modifiedRuins.jungle)
				.setDefaultValue(defaultConfig.ruins.jungle)
				.setSaveConsumer(newValue -> ruins.jungle = newValue)
				.setTooltip(TrailierConstants.tooltip("jungle_ruins"))
				.build(),
			ruins.getClass(),
			"jungle",
			configInstance
		);
		var savannaRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("savanna_ruins"), modifiedRuins.savanna)
				.setDefaultValue(defaultConfig.ruins.savanna)
				.setSaveConsumer(newValue -> ruins.savanna = newValue)
				.setTooltip(TrailierConstants.tooltip("savanna_ruins"))
				.build(),
			ruins.getClass(),
			"savanna",
			configInstance
		);
		var desertRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("desert_ruins"), modifiedRuins.desert)
				.setDefaultValue(defaultConfig.ruins.desert)
				.setSaveConsumer(newValue -> ruins.desert = newValue)
				.setTooltip(TrailierConstants.tooltip("desert_ruins"))
				.build(),
			ruins.getClass(),
			"desert",
			configInstance
		);
		var badlandsRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("badlands_ruins"), modifiedRuins.badlands)
				.setDefaultValue(defaultConfig.ruins.badlands)
				.setSaveConsumer(newValue -> ruins.badlands = newValue)
				.setTooltip(TrailierConstants.tooltip("badlands_ruins"))
				.build(),
			ruins.getClass(),
			"badlands",
			configInstance
		);
		var deepslateRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("deepslate_ruins"), modifiedRuins.deepslate)
				.setDefaultValue(defaultConfig.ruins.deepslate)
				.setSaveConsumer(newValue -> ruins.deepslate = newValue)
				.setTooltip(TrailierConstants.tooltip("deepslate_ruins"))
				.build(),
			ruins.getClass(),
			"deepslate",
			configInstance
		);

		var ruinsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("ruins"),
			false,
			TrailierConstants.tooltip("ruins"),
			genericRuins, jungleRuins, savannaRuins, desertRuins, badlandsRuins, deepslateRuins
		);

		var vegetation = config.vegetation;
		var modifiedVegetation = modifiedConfig.vegetation;

		var generateTorchflower = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("generate_torchflower"), modifiedVegetation.generateTorchflower)
				.setDefaultValue(defaultConfig.vegetation.generateTorchflower)
				.setSaveConsumer(newValue -> vegetation.generateTorchflower = newValue)
				.setTooltip(TrailierConstants.tooltip("generate_torchflower"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generateTorchflower",
			configInstance
		);
		var generatePitcher = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("generate_pitcher"), modifiedVegetation.generatePitcher)
				.setDefaultValue(defaultConfig.vegetation.generatePitcher)
				.setSaveConsumer(newValue -> vegetation.generatePitcher = newValue)
				.setTooltip(TrailierConstants.tooltip("generate_pitcher"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generatePitcher",
			configInstance
		);
		var generateCyanRose = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("generate_cyan_rose"), modifiedVegetation.generateCyanRose)
				.setDefaultValue(defaultConfig.vegetation.generateCyanRose)
				.setSaveConsumer(newValue -> vegetation.generateCyanRose = newValue)
				.setTooltip(TrailierConstants.tooltip("generate_cyan_rose"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generateCyanRose",
			configInstance
		);
		var generateManedrop = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("generate_manedrop"), modifiedVegetation.generateManedrop)
				.setDefaultValue(defaultConfig.vegetation.generateManedrop)
				.setSaveConsumer(newValue -> vegetation.generateManedrop = newValue)
				.setTooltip(TrailierConstants.tooltip("generate_manedrop"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generateManedrop",
			configInstance
		);
		var generateDawntrail = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("generate_dawntrail"), modifiedVegetation.generateDawntrail)
				.setDefaultValue(defaultConfig.vegetation.generateDawntrail)
				.setSaveConsumer(newValue -> vegetation.generateDawntrail = newValue)
				.setTooltip(TrailierConstants.tooltip("generate_dawntrail"))
				.requireRestart()
				.build(),
			vegetation.getClass(),
			"generateDawntrail",
			configInstance
		);

		var vegetationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("vegetation"),
			false,
			TrailierConstants.tooltip("vegetation"),
			generateTorchflower, generatePitcher, generateCyanRose, generateManedrop, generateDawntrail
		);

		var endCity = config.endCity;
		var modifiedEndCity = modifiedConfig.endCity;

		var crackedEndCity = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("cracked_end_city"), modifiedEndCity.generateCracked)
				.setDefaultValue(defaultConfig.endCity.generateCracked)
				.setSaveConsumer(newValue -> endCity.generateCracked = newValue)
				.setTooltip(TrailierConstants.tooltip("cracked_end_city"))
				.requireRestart()
				.build(),
			endCity.getClass(),
			"generateCracked",
			configInstance
		);
		var choralEndCity = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("choral_end_city"), modifiedEndCity.generateChoral)
				.setDefaultValue(defaultConfig.endCity.generateChoral)
				.setSaveConsumer(newValue -> endCity.generateChoral = newValue)
				.setTooltip(TrailierConstants.tooltip("choral_end_city"))
				.requireRestart()
				.build(),
			endCity.getClass(),
			"generateChoral",
			configInstance
		);
		var chiseledEndCity = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("chiseled_end_city"), modifiedEndCity.generateChiseled)
				.setDefaultValue(defaultConfig.endCity.generateChiseled)
				.setSaveConsumer(newValue -> endCity.generateChiseled = newValue)
				.setTooltip(TrailierConstants.tooltip("chiseled_end_city"))
				.requireRestart()
				.build(),
			endCity.getClass(),
			"generateChiseled",
			configInstance
		);

		var endCityCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("end_city"),
			false,
			TrailierConstants.tooltip("end_city"),
			crackedEndCity, choralEndCity, chiseledEndCity
		);
	}
}
