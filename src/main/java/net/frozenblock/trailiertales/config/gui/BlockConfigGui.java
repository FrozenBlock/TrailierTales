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

		var suspiciousBlocks = config.suspiciousBlocks;
		var modifiedSuspiciousBlocks = modifiedConfig.suspiciousBlocks;

		var smoothSuspiciousBlocks = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("smooth_suspicious_blocks"), modifiedSuspiciousBlocks.smooth_animations)
				.setDefaultValue(defaultConfig.suspiciousBlocks.smooth_animations)
				.setSaveConsumer(newValue -> suspiciousBlocks.smooth_animations = newValue)
				.setTooltip(TrailierConstants.tooltip("smooth_suspicious_blocks"))
				.build(),
			suspiciousBlocks.getClass(),
			"smooth_animations",
			configInstance
		);
		var suspiciousBlockParticles = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("suspicious_block_particles"), modifiedSuspiciousBlocks.particle)
				.setDefaultValue(defaultConfig.suspiciousBlocks.particle)
				.setSaveConsumer(newValue -> suspiciousBlocks.particle = newValue)
				.setTooltip(TrailierConstants.tooltip("suspicious_block_particles"))
				.build(),
			suspiciousBlocks.getClass(),
			"particle",
			configInstance
		);
		var placeItemsInSupiciousBlocks = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("place_items_in_suspicious_blocks"), modifiedSuspiciousBlocks.place_items)
				.setDefaultValue(defaultConfig.suspiciousBlocks.place_items)
				.setSaveConsumer(newValue -> suspiciousBlocks.place_items = newValue)
				.setTooltip(TrailierConstants.tooltip("place_items_in_suspicious_blocks"))
				.build(),
			suspiciousBlocks.getClass(),
			"place_items",
			configInstance
		);

		var suspiciousBlocksCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("suspicious_blocks"),
			false,
			TrailierConstants.tooltip("suspicious_blocks"),
			smoothSuspiciousBlocks, suspiciousBlockParticles, placeItemsInSupiciousBlocks
		);

		var blockSounds = config.blockSounds;
		var modifiedBlockSounds = modifiedConfig.blockSounds;

		var brickSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("brick_sounds"), modifiedBlockSounds.bricks)
				.setDefaultValue(defaultConfig.blockSounds.bricks)
				.setSaveConsumer(newValue -> blockSounds.bricks = newValue)
				.setTooltip(TrailierConstants.tooltip("brick_sounds"))
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
				.build(),
			blockSounds.getClass(),
			"stone_brick_sounds",
			configInstance
		);
		var polishedSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TrailierConstants.text("polished_sounds"), modifiedBlockSounds.polished)
				.setDefaultValue(defaultConfig.blockSounds.polished)
				.setSaveConsumer(newValue -> blockSounds.polished = newValue)
				.setTooltip(TrailierConstants.tooltip("polished_sounds"))
				.build(),
			blockSounds.getClass(),
			"polished_sounds",
			configInstance
		);

		var blockSoundsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TrailierConstants.text("block_sounds"),
			false,
			TrailierConstants.tooltip("block_sounds"),
			brickSounds, stoneBrickSounds, polishedSounds
		);
	}
}
