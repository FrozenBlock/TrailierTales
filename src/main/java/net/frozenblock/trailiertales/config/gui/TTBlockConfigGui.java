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
import me.shedaniel.clothconfig2.api.Requirement;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class TTBlockConfigGui {
	private TTBlockConfigGui() {
		throw new UnsupportedOperationException("TTBlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = TTBlockConfig.get(true);
		var modifiedConfig = TTBlockConfig.getWithSync();
		Config<? extends TTBlockConfig> configInstance = TTBlockConfig.INSTANCE;
		var defaultConfig = TTBlockConfig.INSTANCE.defaultInstance();

		var suspiciousBlocks = config.suspiciousBlocks;
		var modifiedSuspiciousBlocks = modifiedConfig.suspiciousBlocks;

		var smoothSuspiciousBlocks = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("smooth_suspicious_blocks"), modifiedSuspiciousBlocks.smooth_animations)
				.setDefaultValue(defaultConfig.suspiciousBlocks.smooth_animations)
				.setSaveConsumer(newValue -> suspiciousBlocks.smooth_animations = newValue)
				.setTooltip(TTConstants.tooltip("smooth_suspicious_blocks"))
				.build(),
			suspiciousBlocks.getClass(),
			"smooth_animations",
			configInstance
		);
		var suspiciousBlockParticles = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("suspicious_block_particles"), modifiedSuspiciousBlocks.particle)
				.setDefaultValue(defaultConfig.suspiciousBlocks.particle)
				.setSaveConsumer(newValue -> suspiciousBlocks.particle = newValue)
				.setTooltip(TTConstants.tooltip("suspicious_block_particles"))
				.build(),
			suspiciousBlocks.getClass(),
			"particle",
			configInstance
		);
		var placeItemsInSuspiciousBlocks = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("place_items_in_suspicious_blocks"), modifiedSuspiciousBlocks.place_items)
				.setDefaultValue(defaultConfig.suspiciousBlocks.place_items)
				.setSaveConsumer(newValue -> suspiciousBlocks.place_items = newValue)
				.requireRestart()
				.setTooltip(TTConstants.tooltip("place_items_in_suspicious_blocks"))
				.build(),
			suspiciousBlocks.getClass(),
			"place_items",
			configInstance
		);

		var suspiciousBlocksCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("suspicious_blocks"),
			false,
			TTConstants.tooltip("suspicious_blocks"),
			smoothSuspiciousBlocks, suspiciousBlockParticles, placeItemsInSuspiciousBlocks
		);

		var coffin = config.coffin;
		var modifiedCoffin = modifiedConfig.coffin;
		var wobbleEnabled = Requirement.isTrue(() -> modifiedCoffin.wobble);

		var ignoreDoMobSpawningGamerule = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("ignore_do_mob_spawning_gamerule"), modifiedCoffin.ignore_do_mob_spawning_gamerule)
				.setDefaultValue(defaultConfig.coffin.ignore_do_mob_spawning_gamerule)
				.setSaveConsumer(newValue -> coffin.ignore_do_mob_spawning_gamerule = newValue)
				.setTooltip(TTConstants.tooltip("ignore_do_mob_spawning_gamerule"))
				.build(),
			coffin.getClass(),
			"ignore_do_mob_spawning_gamerule",
			configInstance
		);
		var coffinWobble = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("coffin_wobble"), modifiedCoffin.wobble)
				.setDefaultValue(defaultConfig.coffin.wobble)
				.setSaveConsumer(newValue -> coffin.wobble = newValue)
				.setTooltip(TTConstants.tooltip("coffin_wobble"))
				.build(),
			coffin.getClass(),
			"wobble",
			configInstance
		);
		var coffinWobbleActivate = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("coffin_wobble_activate"), modifiedCoffin.wobble_activate)
				.setDefaultValue(defaultConfig.coffin.wobble_activate)
				.setSaveConsumer(newValue -> coffin.wobble_activate = newValue)
				.setTooltip(TTConstants.tooltip("coffin_wobble_activate"))
				.setRequirement(wobbleEnabled)
				.build(),
			coffin.getClass(),
			"wobble_activate",
			configInstance
		);
		var coffinWobbleLoot = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("coffin_wobble_loot"), modifiedCoffin.wobble_loot)
				.setDefaultValue(defaultConfig.coffin.wobble_loot)
				.setSaveConsumer(newValue -> coffin.wobble_loot = newValue)
				.setTooltip(TTConstants.tooltip("coffin_wobble_loot"))
				.setRequirement(wobbleEnabled)
				.build(),
			coffin.getClass(),
			"wobble_loot",
			configInstance
		);
		var coffinWobblePotion = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("coffin_wobble_potion"), modifiedCoffin.wobble_potion)
				.setDefaultValue(defaultConfig.coffin.wobble_potion)
				.setSaveConsumer(newValue -> coffin.wobble_potion = newValue)
				.setTooltip(TTConstants.tooltip("coffin_wobble_potion"))
				.setRequirement(wobbleEnabled)
				.build(),
			coffin.getClass(),
			"wobble_potion",
			configInstance
		);
		var coffinWobbleExperienceBottle = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("coffin_wobble_experience_bottle"), modifiedCoffin.wobble_experience_bottle)
				.setDefaultValue(defaultConfig.coffin.wobble_experience_bottle)
				.setSaveConsumer(newValue -> coffin.wobble_experience_bottle = newValue)
				.setTooltip(TTConstants.tooltip("coffin_wobble_experience_bottle"))
				.setRequirement(wobbleEnabled)
				.build(),
			coffin.getClass(),
			"wobble_experience_bottle",
			configInstance
		);

		var coffinCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("coffin"),
			false,
			TTConstants.tooltip("coffin"),
			ignoreDoMobSpawningGamerule,
			coffinWobble, coffinWobbleActivate, coffinWobbleExperienceBottle, coffinWobbleLoot, coffinWobblePotion
		);

		var blockSounds = config.blockSounds;
		var modifiedBlockSounds = modifiedConfig.blockSounds;

		var unpolishedBrickSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("unpolished_brick_sounds"), modifiedBlockSounds.unpolished_bricks)
				.setDefaultValue(defaultConfig.blockSounds.unpolished_bricks)
				.setSaveConsumer(newValue -> blockSounds.unpolished_bricks = newValue)
				.setTooltip(TTConstants.tooltip("unpolished_brick_sounds"))
				.build(),
			blockSounds.getClass(),
			"unpolished_brick_sounds",
			configInstance
		);
		var polishedBrickSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("polished_brick_sounds"), modifiedBlockSounds.polished_bricks)
				.setDefaultValue(defaultConfig.blockSounds.polished_bricks)
				.setSaveConsumer(newValue -> blockSounds.polished_bricks = newValue)
				.setTooltip(TTConstants.tooltip("polished_brick_sounds"))
				.build(),
			blockSounds.getClass(),
			"polished_brick_sounds",
			configInstance
		);
		var polishedSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("polished_sounds"), modifiedBlockSounds.polished)
				.setDefaultValue(defaultConfig.blockSounds.polished)
				.setSaveConsumer(newValue -> blockSounds.polished = newValue)
				.setTooltip(TTConstants.tooltip("polished_sounds"))
				.build(),
			blockSounds.getClass(),
			"polished_sounds",
			configInstance
		);
		var polishedBasaltSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("polished_basalt_sounds"), modifiedBlockSounds.polished_basalt)
				.setDefaultValue(defaultConfig.blockSounds.polished_basalt)
				.setSaveConsumer(newValue -> blockSounds.polished_basalt = newValue)
				.setTooltip(TTConstants.tooltip("polished_basalt_sounds"))
				.build(),
			blockSounds.getClass(),
			"polished_basalt_sounds",
			configInstance
		);
		var polishedDeepslateSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("polished_deepslate_sounds"), modifiedBlockSounds.polished_deepslate)
				.setDefaultValue(defaultConfig.blockSounds.polished_deepslate)
				.setSaveConsumer(newValue -> blockSounds.polished_deepslate = newValue)
				.setTooltip(TTConstants.tooltip("polished_deepslate_sounds"))
				.build(),
			blockSounds.getClass(),
			"polished_deepslate_sounds",
			configInstance
		);
		var polishedTuffSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("polished_tuff_sounds"), modifiedBlockSounds.polished_tuff)
				.setDefaultValue(defaultConfig.blockSounds.polished_tuff)
				.setSaveConsumer(newValue -> blockSounds.polished_tuff = newValue)
				.setTooltip(TTConstants.tooltip("polished_tuff_sounds"))
				.build(),
			blockSounds.getClass(),
			"polished_tuff_sounds",
			configInstance
		);
		var polishedCalciteSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("polished_calcite_sounds"), modifiedBlockSounds.polished_calcite)
				.setDefaultValue(defaultConfig.blockSounds.polished_calcite)
				.setSaveConsumer(newValue -> blockSounds.polished_calcite = newValue)
				.setTooltip(TTConstants.tooltip("polished_calcite_sounds"))
				.build(),
			blockSounds.getClass(),
			"polished_calcite_sounds",
			configInstance
		);
		var calciteBricksSounds = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(TTConstants.text("calcite_bricks_sounds"), modifiedBlockSounds.calcite_bricks)
				.setDefaultValue(defaultConfig.blockSounds.calcite_bricks)
				.setSaveConsumer(newValue -> blockSounds.calcite_bricks = newValue)
				.setTooltip(TTConstants.tooltip("calcite_bricks_sounds"))
				.build(),
			blockSounds.getClass(),
			"calcite_bricks_sounds",
			configInstance
		);

		var blockSoundsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, TTConstants.text("block_sounds"),
			false,
			TTConstants.tooltip("block_sounds"),
			unpolishedBrickSounds, polishedBrickSounds, polishedSounds, polishedBasaltSounds, polishedDeepslateSounds,
			polishedTuffSounds, polishedCalciteSounds, calciteBricksSounds
		);
	}
}
