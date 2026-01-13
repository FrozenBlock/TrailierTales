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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public final class TTCreativeInventorySorting {

	public static void init() {
		// BLOCKS

		insertBeforeInFunctionalBlocks(Blocks.SUSPICIOUS_SAND, TTBlocks.SUSPICIOUS_DIRT);
		insertAfterInFunctionalBlocks(TTBlocks.SUSPICIOUS_DIRT, TTBlocks.SUSPICIOUS_CLAY);
		insertAfterInFunctionalBlocks(Blocks.SUSPICIOUS_SAND, TTBlocks.SUSPICIOUS_RED_SAND);

		insertAfterInNaturalBlocks(Blocks.TORCHFLOWER, TTBlocks.CYAN_ROSE);
		insertAfterInNaturalBlocks(Blocks.PITCHER_PLANT, TTBlocks.MANEDROP);
		insertAfterInNaturalBlocks(TTBlocks.MANEDROP, TTBlocks.GUZMANIA);
		insertAfterInNaturalBlocks(TTBlocks.GUZMANIA, TTBlocks.LITHOPS);
		insertAfterInNaturalBlocks(Blocks.GLOW_LICHEN, TTBlocks.DAWNTRAIL);

		insertAfterInBuildingBlocks(Blocks.STONE_SLAB, TTBlocks.STONE_WALL);

		insertAfterInBuildingBlocks(Blocks.POLISHED_GRANITE_SLAB, TTBlocks.POLISHED_GRANITE_WALL);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_GRANITE_WALL, TTBlocks.GRANITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.GRANITE_BRICKS, TTBlocks.CRACKED_GRANITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CRACKED_GRANITE_BRICKS, TTBlocks.GRANITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.GRANITE_BRICK_STAIRS, TTBlocks.GRANITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.GRANITE_BRICK_SLAB, TTBlocks.GRANITE_BRICK_WALL);
		insertAfterInBuildingBlocks(TTBlocks.GRANITE_BRICK_WALL, TTBlocks.CHISELED_GRANITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CHISELED_GRANITE_BRICKS, TTBlocks.MOSSY_GRANITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_GRANITE_BRICKS, TTBlocks.MOSSY_GRANITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS, TTBlocks.MOSSY_GRANITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_GRANITE_BRICK_SLAB, TTBlocks.MOSSY_GRANITE_BRICK_WALL);

		insertAfterInBuildingBlocks(Blocks.POLISHED_DIORITE_SLAB, TTBlocks.POLISHED_DIORITE_WALL);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_DIORITE_WALL, TTBlocks.DIORITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.DIORITE_BRICKS, TTBlocks.CRACKED_DIORITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CRACKED_DIORITE_BRICKS, TTBlocks.DIORITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.DIORITE_BRICK_STAIRS, TTBlocks.DIORITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.DIORITE_BRICK_SLAB, TTBlocks.DIORITE_BRICK_WALL);
		insertAfterInBuildingBlocks(TTBlocks.DIORITE_BRICK_WALL, TTBlocks.CHISELED_DIORITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CHISELED_DIORITE_BRICKS, TTBlocks.MOSSY_DIORITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DIORITE_BRICKS, TTBlocks.MOSSY_DIORITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS, TTBlocks.MOSSY_DIORITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DIORITE_BRICK_SLAB, TTBlocks.MOSSY_DIORITE_BRICK_WALL);

		insertAfterInBuildingBlocks(Blocks.POLISHED_ANDESITE_SLAB, TTBlocks.POLISHED_ANDESITE_WALL);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_ANDESITE_WALL, TTBlocks.ANDESITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.ANDESITE_BRICKS, TTBlocks.CRACKED_ANDESITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CRACKED_ANDESITE_BRICKS, TTBlocks.ANDESITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.ANDESITE_BRICK_STAIRS, TTBlocks.ANDESITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.ANDESITE_BRICK_SLAB, TTBlocks.ANDESITE_BRICK_WALL);
		insertAfterInBuildingBlocks(TTBlocks.ANDESITE_BRICK_WALL, TTBlocks.CHISELED_ANDESITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CHISELED_ANDESITE_BRICKS, TTBlocks.MOSSY_ANDESITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_ANDESITE_BRICKS, TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS, TTBlocks.MOSSY_ANDESITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB, TTBlocks.MOSSY_ANDESITE_BRICK_WALL);

		insertBeforeInBuildingBlocks(Blocks.DEEPSLATE, Blocks.CALCITE);
		insertAfterInBuildingBlocks(Blocks.CALCITE, TTBlocks.CALCITE_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.CALCITE_STAIRS, TTBlocks.CALCITE_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.CALCITE_SLAB, TTBlocks.CALCITE_WALL);
		insertAfterInBuildingBlocks(TTBlocks.CALCITE_WALL, TTBlocks.POLISHED_CALCITE);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_CALCITE, TTBlocks.POLISHED_CALCITE_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_CALCITE_STAIRS, TTBlocks.POLISHED_CALCITE_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_CALCITE_SLAB, TTBlocks.POLISHED_CALCITE_WALL);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_CALCITE_WALL, TTBlocks.CALCITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CALCITE_BRICKS, TTBlocks.CRACKED_CALCITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CRACKED_CALCITE_BRICKS, TTBlocks.CALCITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.CALCITE_BRICK_STAIRS, TTBlocks.CALCITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.CALCITE_BRICK_SLAB, TTBlocks.CALCITE_BRICK_WALL);
		insertAfterInBuildingBlocks(TTBlocks.CALCITE_BRICK_WALL, TTBlocks.CHISELED_CALCITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CHISELED_CALCITE_BRICKS, TTBlocks.MOSSY_CALCITE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_CALCITE_BRICKS, TTBlocks.MOSSY_CALCITE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS, TTBlocks.MOSSY_CALCITE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_CALCITE_BRICK_SLAB, TTBlocks.MOSSY_CALCITE_BRICK_WALL);

		insertAfterInBuildingBlocks(Blocks.TUFF_BRICKS, TTBlocks.CRACKED_TUFF_BRICKS);
		insertAfterInBuildingBlocks(Blocks.CHISELED_TUFF_BRICKS, TTBlocks.MOSSY_TUFF_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_TUFF_BRICKS, TTBlocks.MOSSY_TUFF_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_TUFF_BRICK_STAIRS, TTBlocks.MOSSY_TUFF_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_TUFF_BRICK_SLAB, TTBlocks.MOSSY_TUFF_BRICK_WALL);

		insertAfterInBuildingBlocks(Blocks.BRICKS, TTBlocks.CRACKED_BRICKS);
		insertAfterInBuildingBlocks(Blocks.BRICK_WALL, TTBlocks.MOSSY_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_BRICKS, TTBlocks.MOSSY_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_BRICK_STAIRS, TTBlocks.MOSSY_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_BRICK_SLAB, TTBlocks.MOSSY_BRICK_WALL);

		insertBeforeInBuildingBlocks(Blocks.RESIN_BRICKS, Blocks.RESIN_BLOCK);
		insertAfterInBuildingBlocks(Blocks.RESIN_BLOCK, TTBlocks.POLISHED_RESIN_BLOCK);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_RESIN_BLOCK, TTBlocks.POLISHED_RESIN_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_RESIN_STAIRS, TTBlocks.POLISHED_RESIN_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.POLISHED_RESIN_SLAB, TTBlocks.POLISHED_RESIN_WALL);
		insertAfterInBuildingBlocks(Blocks.RESIN_BRICKS, TTBlocks.CRACKED_RESIN_BRICKS);
		insertAfterInBuildingBlocks(Blocks.RESIN_BRICK_WALL, TTBlocks.PALE_MOSSY_RESIN_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.PALE_MOSSY_RESIN_BRICKS, TTBlocks.PALE_MOSSY_RESIN_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.PALE_MOSSY_RESIN_BRICK_STAIRS, TTBlocks.PALE_MOSSY_RESIN_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.PALE_MOSSY_RESIN_BRICK_SLAB, TTBlocks.PALE_MOSSY_RESIN_BRICK_WALL);

		insertAfterInBuildingBlocks(Blocks.COBBLED_DEEPSLATE_WALL, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_COBBLED_DEEPSLATE, TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB, TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL);

		insertAfterInBuildingBlocks(Blocks.DEEPSLATE_BRICK_WALL, TTBlocks.MOSSY_DEEPSLATE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_BRICKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL);

		insertAfterInBuildingBlocks(Blocks.DEEPSLATE_TILE_WALL, TTBlocks.MOSSY_DEEPSLATE_TILES);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_TILES, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);

		insertAfterInBuildingBlocks(Blocks.SMOOTH_SANDSTONE_SLAB, TTBlocks.SMOOTH_SANDSTONE_WALL);
		insertBeforeInBuildingBlocks(Blocks.CUT_SANDSTONE_SLAB, TTBlocks.CUT_SANDSTONE_STAIRS);
		insertAfterInBuildingBlocks(Blocks.CUT_SANDSTONE_SLAB, TTBlocks.CUT_SANDSTONE_WALL);

		insertAfterInBuildingBlocks(Blocks.SMOOTH_RED_SANDSTONE_SLAB, TTBlocks.SMOOTH_RED_SANDSTONE_WALL);
		insertBeforeInBuildingBlocks(Blocks.CUT_RED_SANDSTONE_SLAB, TTBlocks.CUT_RED_SANDSTONE_STAIRS);
		insertAfterInBuildingBlocks(Blocks.CUT_RED_SANDSTONE_SLAB, TTBlocks.CUT_RED_SANDSTONE_WALL);

		insertAfterInBuildingBlocks(Blocks.PRISMARINE_BRICK_SLAB, TTBlocks.PRISMARINE_BRICK_WALL);
		insertAfterInBuildingBlocks(Blocks.DARK_PRISMARINE_SLAB, TTBlocks.DARK_PRISMARINE_WALL);

		insertAfterInBuildingBlocks(Blocks.END_STONE, TTBlocks.END_STONE_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.END_STONE_STAIRS, TTBlocks.END_STONE_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.END_STONE_SLAB, TTBlocks.END_STONE_WALL);

		insertAfterInBuildingBlocks(TTBlocks.END_STONE_WALL, TTBlocks.CHORAL_END_STONE);
		insertAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE, TTBlocks.CHORAL_END_STONE_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_STAIRS, TTBlocks.CHORAL_END_STONE_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_SLAB, TTBlocks.CHORAL_END_STONE_WALL);

		insertAfterInBuildingBlocks(Blocks.END_STONE_BRICKS, TTBlocks.CRACKED_END_STONE_BRICKS);
		insertAfterInBuildingBlocks(Blocks.END_STONE_BRICK_WALL, TTBlocks.CHISELED_END_STONE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CHISELED_END_STONE_BRICKS, TTBlocks.CHORAL_END_STONE_BRICKS);
		insertAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_BRICKS, TTBlocks.CHORAL_END_STONE_BRICK_STAIRS);
		insertAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS, TTBlocks.CHORAL_END_STONE_BRICK_SLAB);
		insertAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_BRICK_SLAB, TTBlocks.CHORAL_END_STONE_BRICK_WALL);

		insertAfterInBuildingBlocks(Blocks.PURPUR_BLOCK, TTBlocks.CRACKED_PURPUR_BLOCK);
		insertAfterInBuildingBlocks(Blocks.PURPUR_SLAB, TTBlocks.PURPUR_WALL);
		insertAfterInBuildingBlocks(TTBlocks.PURPUR_WALL, TTBlocks.CHISELED_PURPUR_BLOCK);

		insertAfterInSpawnEggs(Blocks.TRIAL_SPAWNER, TTBlocks.COFFIN);
		insertAfterInRedstone(Blocks.OBSERVER, TTBlocks.SURVEYOR);
		insertAfterInFunctionalBlocks(Blocks.MAGMA_BLOCK, TTBlocks.ECTOPLASM_BLOCK);
		insertAfterInNaturalBlocks(Blocks.HONEY_BLOCK, TTBlocks.ECTOPLASM_BLOCK);

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
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertBeforeInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void insertAfterInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void insertAfterInRedstone(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void insertBeforeInFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void insertAfterInFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void insertBeforeInRedstoneBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void insertInToolsAndUtilities(ItemLike item) {
		FrozenCreativeTabs.insert(item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertBeforeInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertAfterInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertBeforeInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void insertAfterInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void insertBeforeInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void insertAfterInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void insertAfterInCombat(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.COMBAT);
	}

	private static void insertBeforeInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

	private static void insertAfterInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}
}
