package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.MiscConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class MiscConfigGui {
	private MiscConfigGui() {
		throw new UnsupportedOperationException("MiscConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = MiscConfig.get(true);
		var modifiedConfig = MiscConfig.getWithSync();
		Config<? extends MiscConfig> configInstance = MiscConfig.INSTANCE;
		var defaultConfig = MiscConfig.INSTANCE.defaultInstance();

		var modifyAdvancements = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(TrailierConstants.text("modify_advancements"), modifiedConfig.modify_advancements)
					.setDefaultValue(defaultConfig.modify_advancements)
					.setSaveConsumer(newValue -> config.modify_advancements = newValue)
					.setTooltip(TrailierConstants.tooltip("modify_advancements"))
					.requireRestart()
					.build(),
				config.getClass(),
				"modify_advancements",
				configInstance
			)
		);

		var titleResourcePackEnabled = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(TrailierConstants.text("title_resource_pack_enabled"), modifiedConfig.titleResourcePackEnabled)
					.setDefaultValue(defaultConfig.titleResourcePackEnabled)
					.setSaveConsumer(newValue -> config.titleResourcePackEnabled = newValue)
					.setTooltip(TrailierConstants.tooltip("title_resource_pack_enabled"))
					.requireRestart()
					.build(),
				config.getClass(),
				"titleResourcePackEnabled",
				configInstance
			)
		);
	}
}
