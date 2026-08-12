/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.config.modmenu;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.config.gui.TTBlockConfigGui;
import net.frozenblock.trailiertales.config.gui.TTEntityConfigGui;
import net.frozenblock.trailiertales.config.gui.TTItemConfigGui;
import net.frozenblock.trailiertales.config.gui.TTMiscConfigGui;
import net.frozenblock.trailiertales.config.gui.TTWorldgenConfigGui;
import net.minecraft.client.gui.screens.Screen;

@Environment(EnvType.CLIENT)
public class TTConfigGuiBuilder {

	public static Screen buildScreen(Screen parent) {
		final ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(TTConstants.text("component.title"));
		configBuilder.setSavingRunnable(() -> {
			TTWorldgenConfig.CONFIG.save();
			TTBlockConfig.CONFIG.save();
			TTEntityConfig.CONFIG.save();
			TTItemConfig.CONFIG.save();
			TTMiscConfig.CONFIG.save();
		});

		final ConfigEntryBuilder entryBuilder = configBuilder.getEntryBuilder();
		TTBlockConfigGui.setupEntries(configBuilder.getOrCreateCategory(TTConstants.text("block")), entryBuilder);
		TTItemConfigGui.setupEntries(configBuilder.getOrCreateCategory(TTConstants.text("item")), entryBuilder);
		TTEntityConfigGui.setupEntries(configBuilder.getOrCreateCategory(TTConstants.text("entity")), entryBuilder);
		TTWorldgenConfigGui.setupEntries(configBuilder.getOrCreateCategory(TTConstants.text("worldgen")), entryBuilder);
		TTMiscConfigGui.setupEntries(configBuilder.getOrCreateCategory(TTConstants.text("misc")), entryBuilder);

		return configBuilder.build();
	}
}
