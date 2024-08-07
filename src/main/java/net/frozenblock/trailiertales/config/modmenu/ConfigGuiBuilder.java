package net.frozenblock.trailiertales.config.modmenu;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.BlockConfig;
import net.frozenblock.trailiertales.config.EntityConfig;
import net.frozenblock.trailiertales.config.ItemConfig;
import net.frozenblock.trailiertales.config.MiscConfig;
import net.frozenblock.trailiertales.config.WorldgenConfig;
import net.frozenblock.trailiertales.config.gui.BlockConfigGui;
import net.frozenblock.trailiertales.config.gui.EntityConfigGui;
import net.frozenblock.trailiertales.config.gui.ItemConfigGui;
import net.frozenblock.trailiertales.config.gui.MiscConfigGui;
import net.frozenblock.trailiertales.config.gui.WorldgenConfigGui;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ConfigGuiBuilder {

	public static Screen buildScreen(@NotNull Screen parent) {
		var configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(TrailierConstants.text("component.title"));

		configBuilder.setSavingRunnable(() -> {
			WorldgenConfig.INSTANCE.save();
			BlockConfig.INSTANCE.save();
			EntityConfig.INSTANCE.save();
			ItemConfig.INSTANCE.save();
			MiscConfig.INSTANCE.save();
		});

		ConfigEntryBuilder entryBuilder = configBuilder.getEntryBuilder();

		var block = configBuilder.getOrCreateCategory(TrailierConstants.text("block"));
		BlockConfigGui.setupEntries(block, entryBuilder);

		var item = configBuilder.getOrCreateCategory(TrailierConstants.text("item"));
		ItemConfigGui.setupEntries(item, entryBuilder);

		var entity = configBuilder.getOrCreateCategory(TrailierConstants.text("entity"));
		EntityConfigGui.setupEntries(entity, entryBuilder);

		var worldgen = configBuilder.getOrCreateCategory(TrailierConstants.text("worldgen"));
		WorldgenConfigGui.setupEntries(worldgen, entryBuilder);

		var misc = configBuilder.getOrCreateCategory(TrailierConstants.text("misc"));
		MiscConfigGui.setupEntries(misc, entryBuilder);

		return configBuilder.build();
	}
}
