package net.frozenblock.trailiertales.config.modmenu;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.BlockConfig;
import net.frozenblock.trailiertales.config.WorldgenConfig;
import net.frozenblock.trailiertales.config.gui.BlockConfigGui;
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
		});

		ConfigEntryBuilder entryBuilder = configBuilder.getEntryBuilder();

		var block = configBuilder.getOrCreateCategory(TrailierConstants.text("block"));
		BlockConfigGui.setupEntries(block, entryBuilder);

		var worldgen = configBuilder.getOrCreateCategory(TrailierConstants.text("worldgen"));
		WorldgenConfigGui.setupEntries(worldgen, entryBuilder);

		return configBuilder.build();
	}
}
