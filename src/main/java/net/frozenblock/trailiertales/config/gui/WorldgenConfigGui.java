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

		var vegetationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("vegetation"),
			false,
			TrailierConstants.tooltip("vegetation"),
			generateTorchflower, generatePitcher, generateCyanRose
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
