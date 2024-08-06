package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.EntityConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class EntityConfigGui {
	private EntityConfigGui() {
		throw new UnsupportedOperationException("EntityConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = EntityConfig.get(true);
		var modifiedConfig = EntityConfig.getWithSync();
		Config<? extends EntityConfig> configInstance = EntityConfig.INSTANCE;
		var defaultConfig = EntityConfig.INSTANCE.defaultInstance();

		var villagersSellCatacombsMap = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(TrailierConstants.text("villagers_sell_catacombs_map"), modifiedConfig.villagers_sell_catacombs_map)
					.setDefaultValue(defaultConfig.villagers_sell_catacombs_map)
					.setSaveConsumer(newValue -> config.villagers_sell_catacombs_map = newValue)
					.setTooltip(TrailierConstants.tooltip("villagers_sell_catacombs_map"))
					.requireRestart()
					.build(),
				config.getClass(),
				"villagers_sell_catacombs_map",
				configInstance
			)
		);

		var sniffer = config.sniffer;
		var modifiedSniffer = modifiedConfig.sniffer;

		var snifferDigsCyanRoseSeeds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("sniffer_digs_cyan_rose_seeds"), modifiedSniffer.cyan_rose_seeds)
				.setDefaultValue(defaultConfig.sniffer.cyan_rose_seeds)
				.setSaveConsumer(newValue -> sniffer.cyan_rose_seeds = newValue)
				.setTooltip(TrailierConstants.tooltip("sniffer_digs_cyan_rose_seeds"))
				.requireRestart()
				.build(),
			sniffer.getClass(),
			"dig_cyan_rose_seeds",
			configInstance
		);
		var spawnSniffer = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("sniffer_spawns_naturally"), modifiedSniffer.spawn)
				.setDefaultValue(defaultConfig.sniffer.spawn)
				.setSaveConsumer(newValue -> sniffer.spawn = newValue)
				.setTooltip(TrailierConstants.tooltip("sniffer_spawns_naturally"))
				.requireRestart()
				.build(),
			sniffer.getClass(),
			"spawn_sniffer",
			configInstance
		);

		var snifferCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("sniffer"),
			false,
			TrailierConstants.tooltip("sniffer"),
			snifferDigsCyanRoseSeeds, spawnSniffer
		);
	}
}
