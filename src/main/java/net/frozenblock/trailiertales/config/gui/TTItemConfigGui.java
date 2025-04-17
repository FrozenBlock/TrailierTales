/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTItemConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class TTItemConfigGui {
	private TTItemConfigGui() {
		throw new UnsupportedOperationException("TTItemConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = TTItemConfig.get(true);
		var modifiedConfig = TTItemConfig.getWithSync();
		Config<? extends TTItemConfig> configInstance = TTItemConfig.INSTANCE;
		var defaultConfig = TTItemConfig.INSTANCE.defaultInstance();

		var sherdDuplicationRecipe = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(TTConstants.text("sherd_duplication_recipe"), modifiedConfig.sherd_duplication_recipe)
					.setDefaultValue(defaultConfig.sherd_duplication_recipe)
					.setSaveConsumer(newValue -> config.sherd_duplication_recipe = newValue)
					.setTooltip(TTConstants.tooltip("sherd_duplication_recipe"))
					.build(),
				config.getClass(),
				"sherd_duplication_recipe",
				configInstance
			)
		);

		var brush = config.brush;
		var modifiedBrush = modifiedConfig.brush;

		var smoothBrushAnimations = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("smooth_brush_animations"), modifiedBrush.smooth_animations)
				.setDefaultValue(defaultConfig.brush.smooth_animations)
				.setSaveConsumer(newValue -> brush.smooth_animations = newValue)
				.setTooltip(TTConstants.tooltip("smooth_brush_animations"))
				.build(),
			brush.getClass(),
			"smooth_animations",
			configInstance
		);
		var halfBrushEffects = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("half_brush_effects"), modifiedBrush.half_brush_effects)
				.setDefaultValue(defaultConfig.brush.half_brush_effects)
				.setSaveConsumer(newValue -> brush.half_brush_effects = newValue)
				.setTooltip(TTConstants.tooltip("half_brush_effects"))
				.build(),
			brush.getClass(),
			"half_brush_effects",
			configInstance
		);

		var brushCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("brush"),
			false,
			TTConstants.tooltip("brush"),
			smoothBrushAnimations, halfBrushEffects
		);
	}
}
