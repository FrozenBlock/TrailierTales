package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.BlockConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class BlockConfigGui {
	private BlockConfigGui() {
		throw new UnsupportedOperationException("BlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = BlockConfig.get(true);
		var modifiedConfig = BlockConfig.getWithSync();
		Config<? extends BlockConfig> configInstance = BlockConfig.INSTANCE;
		var defaultConfig = BlockConfig.INSTANCE.defaultInstance();

		var blockSounds = config.blockSounds;
		var modifiedBlockSounds = modifiedConfig.blockSounds;

		var brickSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("brick_sounds"), modifiedBlockSounds.bricks)
				.setDefaultValue(defaultConfig.blockSounds.bricks)
				.setSaveConsumer(newValue -> blockSounds.bricks = newValue)
				.setTooltip(TrailierConstants.tooltip("brick_sounds"))
				.requireRestart()
				.build(),
			blockSounds.getClass(),
			"brick_sounds",
			configInstance
		);
		var stoneBrickSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("stone_brick_sounds"), modifiedBlockSounds.stone_bricks)
				.setDefaultValue(defaultConfig.blockSounds.stone_bricks)
				.setSaveConsumer(newValue -> blockSounds.stone_bricks = newValue)
				.setTooltip(TrailierConstants.tooltip("stone_brick_sounds"))
				.requireRestart()
				.build(),
			blockSounds.getClass(),
			"stone_brick_sounds",
			configInstance
		);

		var blockSoundsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("block_sounds"),
			false,
			TrailierConstants.tooltip("block_sounds"),
			brickSounds, stoneBrickSounds
		);
	}
}
