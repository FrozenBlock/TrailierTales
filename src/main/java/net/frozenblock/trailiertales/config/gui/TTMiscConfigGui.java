package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class TTMiscConfigGui {
	private TTMiscConfigGui() {
		throw new UnsupportedOperationException("TTMiscConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = TTMiscConfig.get(true);
		var modifiedConfig = TTMiscConfig.getWithSync();
		Config<? extends TTMiscConfig> configInstance = TTMiscConfig.INSTANCE;
		var defaultConfig = TTMiscConfig.INSTANCE.defaultInstance();

		var modifyAdvancements = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(TTConstants.text("modify_advancements"), modifiedConfig.modify_advancements)
					.setDefaultValue(defaultConfig.modify_advancements)
					.setSaveConsumer(newValue -> config.modify_advancements = newValue)
					.setTooltip(TTConstants.tooltip("modify_advancements"))
					.requireRestart()
					.build(),
				config.getClass(),
				"modify_advancements",
				configInstance
			)
		);

		var distortedCatacombsMusic = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(TTConstants.text("distorted_catacombs_music"), modifiedConfig.distortedCatacombsMusic)
					.setDefaultValue(defaultConfig.distortedCatacombsMusic)
					.setSaveConsumer(newValue -> config.distortedCatacombsMusic = newValue)
					.setTooltip(TTConstants.tooltip("distorted_catacombs_music"))
					.requireRestart()
					.build(),
				config.getClass(),
				"distortedCatacombsMusic",
				configInstance
			)
		);
	}
}
