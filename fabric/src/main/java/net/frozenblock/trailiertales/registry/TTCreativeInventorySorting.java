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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.api.creative.CreativeModeTabSorter;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public final class TTCreativeInventorySorting {

	public static void init() {
		// BLOCKS
		insertBeforeInFunctionalBlocks(Items.SUSPICIOUS_SAND, TTItems.SUSPICIOUS_DIRT);
		insertAfterInFunctionalBlocks(TTItems.SUSPICIOUS_DIRT, TTItems.SUSPICIOUS_CLAY);
		insertAfterInFunctionalBlocks(Items.SUSPICIOUS_SAND, TTItems.SUSPICIOUS_RED_SAND);

		insertAfterInNaturalBlocks(Items.TORCHFLOWER, TTItems.CYAN_ROSE);
		insertAfterInNaturalBlocks(Items.PITCHER_PLANT, TTItems.MANEDROP);
		insertAfterInNaturalBlocks(TTItems.MANEDROP, TTItems.GUZMANIA);
		insertAfterInNaturalBlocks(TTItems.GUZMANIA, TTItems.LITHOPS);
		insertAfterInNaturalBlocks(Items.GLOW_LICHEN, TTItems.DAWNTRAIL);

		insertAfterInBuildingBlocks(Items.STONE_SLAB, TTItems.STONE_WALL);

		insertAfterInBuildingBlocks(Items.POLISHED_GRANITE_SLAB, TTItems.POLISHED_GRANITE_WALL);
		insertAfterInBuildingBlocks(TTItems.POLISHED_GRANITE_WALL, TTItems.GRANITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.GRANITE_BRICKS, TTItems.CRACKED_GRANITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CRACKED_GRANITE_BRICKS, TTItems.GRANITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.GRANITE_BRICK_STAIRS, TTItems.GRANITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.GRANITE_BRICK_SLAB, TTItems.GRANITE_BRICK_WALL);
		insertAfterInBuildingBlocks(TTItems.GRANITE_BRICK_WALL, TTItems.CHISELED_GRANITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CHISELED_GRANITE_BRICKS, TTItems.MOSSY_GRANITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_GRANITE_BRICKS, TTItems.MOSSY_GRANITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_GRANITE_BRICK_STAIRS, TTItems.MOSSY_GRANITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_GRANITE_BRICK_SLAB, TTItems.MOSSY_GRANITE_BRICK_WALL);

		insertAfterInBuildingBlocks(Items.POLISHED_DIORITE_SLAB, TTItems.POLISHED_DIORITE_WALL);
		insertAfterInBuildingBlocks(TTItems.POLISHED_DIORITE_WALL, TTItems.DIORITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.DIORITE_BRICKS, TTItems.CRACKED_DIORITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CRACKED_DIORITE_BRICKS, TTItems.DIORITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.DIORITE_BRICK_STAIRS, TTItems.DIORITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.DIORITE_BRICK_SLAB, TTItems.DIORITE_BRICK_WALL);
		insertAfterInBuildingBlocks(TTItems.DIORITE_BRICK_WALL, TTItems.CHISELED_DIORITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CHISELED_DIORITE_BRICKS, TTItems.MOSSY_DIORITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DIORITE_BRICKS, TTItems.MOSSY_DIORITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DIORITE_BRICK_STAIRS, TTItems.MOSSY_DIORITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DIORITE_BRICK_SLAB, TTItems.MOSSY_DIORITE_BRICK_WALL);

		insertAfterInBuildingBlocks(Items.POLISHED_ANDESITE_SLAB, TTItems.POLISHED_ANDESITE_WALL);
		insertAfterInBuildingBlocks(TTItems.POLISHED_ANDESITE_WALL, TTItems.ANDESITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.ANDESITE_BRICKS, TTItems.CRACKED_ANDESITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CRACKED_ANDESITE_BRICKS, TTItems.ANDESITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.ANDESITE_BRICK_STAIRS, TTItems.ANDESITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.ANDESITE_BRICK_SLAB, TTItems.ANDESITE_BRICK_WALL);
		insertAfterInBuildingBlocks(TTItems.ANDESITE_BRICK_WALL, TTItems.CHISELED_ANDESITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CHISELED_ANDESITE_BRICKS, TTItems.MOSSY_ANDESITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_ANDESITE_BRICKS, TTItems.MOSSY_ANDESITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_ANDESITE_BRICK_STAIRS, TTItems.MOSSY_ANDESITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_ANDESITE_BRICK_SLAB, TTItems.MOSSY_ANDESITE_BRICK_WALL);

		insertBeforeInBuildingBlocks(Items.DEEPSLATE, Blocks.CALCITE);
		insertAfterInBuildingBlocks(Items.CALCITE, TTItems.CALCITE_STAIRS);
		insertAfterInBuildingBlocks(TTItems.CALCITE_STAIRS, TTItems.CALCITE_SLAB);
		insertAfterInBuildingBlocks(TTItems.CALCITE_SLAB, TTItems.CALCITE_WALL);
		insertAfterInBuildingBlocks(TTItems.CALCITE_WALL, TTItems.POLISHED_CALCITE);
		insertAfterInBuildingBlocks(TTItems.POLISHED_CALCITE, TTItems.POLISHED_CALCITE_STAIRS);
		insertAfterInBuildingBlocks(TTItems.POLISHED_CALCITE_STAIRS, TTItems.POLISHED_CALCITE_SLAB);
		insertAfterInBuildingBlocks(TTItems.POLISHED_CALCITE_SLAB, TTItems.POLISHED_CALCITE_WALL);
		insertAfterInBuildingBlocks(TTItems.POLISHED_CALCITE_WALL, TTItems.CALCITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CALCITE_BRICKS, TTItems.CRACKED_CALCITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CRACKED_CALCITE_BRICKS, TTItems.CALCITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.CALCITE_BRICK_STAIRS, TTItems.CALCITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.CALCITE_BRICK_SLAB, TTItems.CALCITE_BRICK_WALL);
		insertAfterInBuildingBlocks(TTItems.CALCITE_BRICK_WALL, TTItems.CHISELED_CALCITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CHISELED_CALCITE_BRICKS, TTItems.MOSSY_CALCITE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_CALCITE_BRICKS, TTItems.MOSSY_CALCITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_CALCITE_BRICK_STAIRS, TTItems.MOSSY_CALCITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_CALCITE_BRICK_SLAB, TTItems.MOSSY_CALCITE_BRICK_WALL);

		insertAfterInBuildingBlocks(Items.TUFF_BRICKS, TTItems.CRACKED_TUFF_BRICKS);
		insertAfterInBuildingBlocks(Items.CHISELED_TUFF_BRICKS, TTItems.MOSSY_TUFF_BRICKS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_TUFF_BRICKS, TTItems.MOSSY_TUFF_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_TUFF_BRICK_STAIRS, TTItems.MOSSY_TUFF_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_TUFF_BRICK_SLAB, TTItems.MOSSY_TUFF_BRICK_WALL);

		insertAfterInBuildingBlocks(Items.BRICKS, TTItems.CRACKED_BRICKS);
		insertAfterInBuildingBlocks(Items.BRICK_WALL, TTItems.MOSSY_BRICKS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_BRICKS, TTItems.MOSSY_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_BRICK_STAIRS, TTItems.MOSSY_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_BRICK_SLAB, TTItems.MOSSY_BRICK_WALL);

		insertBeforeInBuildingBlocks(Items.RESIN_BRICKS, Blocks.RESIN_BLOCK);
		insertAfterInBuildingBlocks(Items.RESIN_BLOCK, TTItems.POLISHED_RESIN_BLOCK);
		insertAfterInBuildingBlocks(TTItems.POLISHED_RESIN_BLOCK, TTItems.POLISHED_RESIN_STAIRS);
		insertAfterInBuildingBlocks(TTItems.POLISHED_RESIN_STAIRS, TTItems.POLISHED_RESIN_SLAB);
		insertAfterInBuildingBlocks(TTItems.POLISHED_RESIN_SLAB, TTItems.POLISHED_RESIN_WALL);
		insertAfterInBuildingBlocks(Items.RESIN_BRICKS, TTItems.CRACKED_RESIN_BRICKS);
		insertAfterInBuildingBlocks(Items.RESIN_BRICK_WALL, TTItems.PALE_MOSSY_RESIN_BRICKS);
		insertAfterInBuildingBlocks(TTItems.PALE_MOSSY_RESIN_BRICKS, TTItems.PALE_MOSSY_RESIN_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.PALE_MOSSY_RESIN_BRICK_STAIRS, TTItems.PALE_MOSSY_RESIN_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.PALE_MOSSY_RESIN_BRICK_SLAB, TTItems.PALE_MOSSY_RESIN_BRICK_WALL);

		insertAfterInBuildingBlocks(Items.COBBLED_DEEPSLATE_WALL, TTItems.MOSSY_COBBLED_DEEPSLATE);
		insertAfterInBuildingBlocks(TTItems.MOSSY_COBBLED_DEEPSLATE, TTItems.MOSSY_COBBLED_DEEPSLATE_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_COBBLED_DEEPSLATE_STAIRS, TTItems.MOSSY_COBBLED_DEEPSLATE_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_COBBLED_DEEPSLATE_SLAB, TTItems.MOSSY_COBBLED_DEEPSLATE_WALL);

		insertAfterInBuildingBlocks(Items.DEEPSLATE_BRICK_WALL, TTItems.MOSSY_DEEPSLATE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DEEPSLATE_BRICKS, TTItems.MOSSY_DEEPSLATE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DEEPSLATE_BRICK_STAIRS, TTItems.MOSSY_DEEPSLATE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DEEPSLATE_BRICK_SLAB, TTItems.MOSSY_DEEPSLATE_BRICK_WALL);

		insertAfterInBuildingBlocks(Items.DEEPSLATE_TILE_WALL, TTItems.MOSSY_DEEPSLATE_TILES);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DEEPSLATE_TILES, TTItems.MOSSY_DEEPSLATE_TILE_STAIRS);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DEEPSLATE_TILE_STAIRS, TTItems.MOSSY_DEEPSLATE_TILE_SLAB);
		insertAfterInBuildingBlocks(TTItems.MOSSY_DEEPSLATE_TILE_SLAB, TTItems.MOSSY_DEEPSLATE_TILE_WALL);

		insertAfterInBuildingBlocks(Items.SMOOTH_SANDSTONE_SLAB, TTItems.SMOOTH_SANDSTONE_WALL);
		insertBeforeInBuildingBlocks(Items.CUT_STANDSTONE_SLAB, TTItems.CUT_SANDSTONE_STAIRS);
		insertAfterInBuildingBlocks(Items.CUT_STANDSTONE_SLAB, TTItems.CUT_SANDSTONE_WALL);

		insertAfterInBuildingBlocks(Items.SMOOTH_RED_SANDSTONE_SLAB, TTItems.SMOOTH_RED_SANDSTONE_WALL);
		insertBeforeInBuildingBlocks(Items.CUT_RED_SANDSTONE_SLAB, TTItems.CUT_RED_SANDSTONE_STAIRS);
		insertAfterInBuildingBlocks(Items.CUT_RED_SANDSTONE_SLAB, TTItems.CUT_RED_SANDSTONE_WALL);

		insertAfterInBuildingBlocks(Items.PRISMARINE_BRICK_SLAB, TTItems.PRISMARINE_BRICK_WALL);
		insertAfterInBuildingBlocks(Items.DARK_PRISMARINE_SLAB, TTItems.DARK_PRISMARINE_WALL);

		insertAfterInBuildingBlocks(Items.END_STONE, TTItems.END_STONE_STAIRS);
		insertAfterInBuildingBlocks(TTItems.END_STONE_STAIRS, TTItems.END_STONE_SLAB);
		insertAfterInBuildingBlocks(TTItems.END_STONE_SLAB, TTItems.END_STONE_WALL);

		insertAfterInBuildingBlocks(TTItems.END_STONE_WALL, TTItems.CHORAL_END_STONE);
		insertAfterInBuildingBlocks(TTItems.CHORAL_END_STONE, TTItems.CHORAL_END_STONE_STAIRS);
		insertAfterInBuildingBlocks(TTItems.CHORAL_END_STONE_STAIRS, TTItems.CHORAL_END_STONE_SLAB);
		insertAfterInBuildingBlocks(TTItems.CHORAL_END_STONE_SLAB, TTItems.CHORAL_END_STONE_WALL);

		insertAfterInBuildingBlocks(Items.END_STONE_BRICKS, TTItems.CRACKED_END_STONE_BRICKS);
		insertAfterInBuildingBlocks(Items.END_STONE_BRICK_WALL, TTItems.CHISELED_END_STONE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CHISELED_END_STONE_BRICKS, TTItems.CHORAL_END_STONE_BRICKS);
		insertAfterInBuildingBlocks(TTItems.CHORAL_END_STONE_BRICKS, TTItems.CHORAL_END_STONE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTItems.CHORAL_END_STONE_BRICK_STAIRS, TTItems.CHORAL_END_STONE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTItems.CHORAL_END_STONE_BRICK_SLAB, TTItems.CHORAL_END_STONE_BRICK_WALL);

		insertAfterInBuildingBlocks(Items.PURPUR_BLOCK, TTItems.CRACKED_PURPUR_BLOCK);
		insertAfterInBuildingBlocks(Items.PURPUR_SLAB, TTItems.PURPUR_WALL);
		insertAfterInBuildingBlocks(TTItems.PURPUR_WALL, TTItems.CHISELED_PURPUR_BLOCK);

		insertAfterInSpawnEggs(Items.TRIAL_SPAWNER, TTItems.COFFIN);
		insertAfterInRedstone(Items.OBSERVER, TTItems.SURVEYOR);
		insertAfterInFunctionalBlocks(Items.MAGMA_BLOCK, TTItems.ECTOPLASM_BLOCK);
		insertAfterInNaturalBlocks(Items.HONEY_BLOCK, TTItems.ECTOPLASM_BLOCK);

		// ITEMS
		insertAfterInSpawnEggs(Items.ALLAY_SPAWN_EGG, TTItems.APPARITION_SPAWN_EGG);
		insertAfterInIngredients(Items.SLIME_BALL, TTItems.ECTOPLASM);

		insertAfterInNaturalBlocks(Items.TORCHFLOWER_SEEDS, TTItems.CYAN_ROSE_SEEDS);
		insertAfterInNaturalBlocks(TTItems.CYAN_ROSE_SEEDS, TTItems.DAWNTRAIL_SEEDS);
		insertAfterInNaturalBlocks(Items.PITCHER_POD, TTItems.MANEDROP_GERM);
		insertAfterInNaturalBlocks(TTItems.MANEDROP_GERM, TTItems.GUZMANIA_SEEDS);
		insertAfterInNaturalBlocks(TTItems.GUZMANIA_SEEDS, TTItems.LITHOPS_SEEDS);

		insertAfterInIngredients(Items.ARMS_UP_POTTERY_SHERD, TTItems.AURORA_POTTERY_SHERD);
		insertBeforeInIngredients(Items.BLADE_POTTERY_SHERD, TTItems.BAIT_POTTERY_SHERD);
		insertAfterInIngredients(Items.BLADE_POTTERY_SHERD, TTItems.BLOOM_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.BLOOM_POTTERY_SHERD, TTItems.BOLT_POTTERY_SHERD);
		insertBeforeInIngredients(Items.BURN_POTTERY_SHERD, TTItems.BULLSEYE_POTTERY_SHERD);
		insertBeforeInIngredients(Items.DANGER_POTTERY_SHERD, TTItems.CARRIER_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.CARRIER_POTTERY_SHERD, TTItems.CLUCK_POTTERY_SHERD);
		insertBeforeInIngredients(TTItems.CLUCK_POTTERY_SHERD, TTItems.CRAWL_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.CRAWL_POTTERY_SHERD, TTItems.CRESCENT_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.CRESCENT_POTTERY_SHERD, TTItems.CULTIVATOR_POTTERY_SHERD);
		insertAfterInIngredients(Items.DANGER_POTTERY_SHERD, TTItems.DROUGHT_POTTERY_SHERD);
		insertBeforeInIngredients(Items.EXPLORER_POTTERY_SHERD, TTItems.ENCLOSURE_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.ENCLOSURE_POTTERY_SHERD, TTItems.ESSENCE_POTTERY_SHERD);
		insertAfterInIngredients(Items.EXPLORER_POTTERY_SHERD, TTItems.EYE_POTTERY_SHERD);
		insertAfterInIngredients(Items.FLOW_POTTERY_SHERD, TTItems.FOCUS_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.FOCUS_POTTERY_SHERD, TTItems.FROST_POTTERY_SHERD);
		insertBeforeInIngredients(Items.HEART_POTTERY_SHERD, TTItems.HARE_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.HARE_POTTERY_SHERD, TTItems.HEIGHT_POTTERY_SHERD);
		insertAfterInIngredients(Items.HOWL_POTTERY_SHERD, TTItems.HUMP_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.HUMP_POTTERY_SHERD, TTItems.ILLUMINATOR_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.ILLUMINATOR_POTTERY_SHERD, TTItems.INCIDENCE_POTTERY_SHERD);
		insertBeforeInIngredients(Items.MINER_POTTERY_SHERD, TTItems.LUMBER_POTTERY_SHERD);
		insertAfterInIngredients(Items.MOURNER_POTTERY_SHERD, TTItems.NAVIGATOR_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.NAVIGATOR_POTTERY_SHERD, TTItems.NEEDLES_POTTERY_SHERD);
		insertBeforeInIngredients(Items.PLENTY_POTTERY_SHERD, TTItems.OMEN_POTTERY_SHERD);
		insertAfterInIngredients(Items.PLENTY_POTTERY_SHERD, TTItems.PLUME_POTTERY_SHERD);
		insertAfterInIngredients(Items.PRIZE_POTTERY_SHERD,TTItems. PROTECTION_POTTERY_SHERD);
		insertAfterInIngredients(Items.SHEAF_POTTERY_SHERD, TTItems.SHED_POTTERY_SHERD);
		insertAfterInIngredients(Items.SHELTER_POTTERY_SHERD, TTItems.SHINE_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.SHINE_POTTERY_SHERD, TTItems.SHOWER_POTTERY_SHERD);
		insertAfterInIngredients(Items.SNORT_POTTERY_SHERD, TTItems.SPADE_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.SPADE_POTTERY_SHERD, TTItems.SPROUT_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.SPROUT_POTTERY_SHERD, TTItems.VESSEL_POTTERY_SHERD);
		insertAfterInIngredients(TTItems.VESSEL_POTTERY_SHERD, TTItems.WITHER_POTTERY_SHERD);

		insertAfterInIngredients(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE);
		insertAfterInIngredients(TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE);
		insertBeforeInIngredients(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE);
		insertBeforeInIngredients(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE);
		insertAfterInIngredients(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE);
		insertAfterInIngredients(TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE);
		insertAfterInIngredients(TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE);
		insertAfterInIngredients(TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE);

		// Ruins discs should come before Trail Ruins!
		insertBeforeInToolsAndUtilities(Items.MUSIC_DISC_RELIC, TTItems.MUSIC_DISC_STASIS);
		// Catacombs discs should come after Trail Ruins!
		insertAfterInToolsAndUtilities(Items.MUSIC_DISC_RELIC, TTItems.MUSIC_DISC_FAUSSE_VIE);
		insertAfterInToolsAndUtilities(TTItems.MUSIC_DISC_FAUSSE_VIE, TTItems.MUSIC_DISC_OSSUAIRE);
	}

	private static void insertBeforeInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertBefore(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertBeforeInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertBefore(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void insertAfterInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void insertAfterInRedstone(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void insertBeforeInFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertBefore(comparedItem, item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void insertAfterInFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void insertBeforeInRedstoneBlocks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertBefore(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void insertInToolsAndUtilities(ItemLike item) {
		CreativeModeTabSorter.insert(item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertBeforeInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertBefore(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertAfterInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertBeforeInIngredients(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertBefore(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void insertAfterInIngredients(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void insertBeforeInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertBefore(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void insertAfterInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void insertAfterInCombat(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.COMBAT);
	}

	private static void insertBeforeInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertBefore(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

	private static void insertAfterInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		CreativeModeTabSorter.insertAfter(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}
}
