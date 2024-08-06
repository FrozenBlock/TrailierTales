package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.ItemConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class ItemConfigGui {
	private ItemConfigGui() {
		throw new UnsupportedOperationException("ItemConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = ItemConfig.get(true);
		var modifiedConfig = ItemConfig.getWithSync();
		Config<? extends ItemConfig> configInstance = ItemConfig.INSTANCE;
		var defaultConfig = ItemConfig.INSTANCE.defaultInstance();

		var brush = config.brush;
		var modifiedBrush = modifiedConfig.brush;

		var smoothBrushAnimations = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("smooth_brush_animations"), modifiedBrush.smooth_animations)
				.setDefaultValue(defaultConfig.brush.smooth_animations)
				.setSaveConsumer(newValue -> brush.smooth_animations = newValue)
				.setTooltip(TrailierConstants.tooltip("smooth_brush_animations"))
				.build(),
			brush.getClass(),
			"smooth_animations",
			configInstance
		);
		var halfBrushEffects = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("half_brush_effects"), modifiedBrush.half_brush_effects)
				.setDefaultValue(defaultConfig.brush.half_brush_effects)
				.setSaveConsumer(newValue -> brush.half_brush_effects = newValue)
				.setTooltip(TrailierConstants.tooltip("half_brush_effects"))
				.build(),
			brush.getClass(),
			"half_brush_effects",
			configInstance
		);

		var brushCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("brush"),
			false,
			TrailierConstants.tooltip("brush"),
			smoothBrushAnimations, halfBrushEffects
		);
	}
}
