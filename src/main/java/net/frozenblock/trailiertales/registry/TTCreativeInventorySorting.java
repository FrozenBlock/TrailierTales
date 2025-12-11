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
		addAfterInFunctionalBlocks(Blocks.SUSPICIOUS_SAND, TTBlocks.SUSPICIOUS_RED_SAND);
		addAfterInFunctionalBlocks(TTBlocks.SUSPICIOUS_RED_SAND, TTBlocks.SUSPICIOUS_DIRT);
		addAfterInFunctionalBlocks(TTBlocks.SUSPICIOUS_DIRT, TTBlocks.SUSPICIOUS_CLAY);

		addAfterInNaturalBlocks(Blocks.TORCHFLOWER, TTBlocks.CYAN_ROSE);
		addAfterInNaturalBlocks(Blocks.PITCHER_PLANT, TTBlocks.MANEDROP);
		addAfterInNaturalBlocks(TTBlocks.MANEDROP, TTBlocks.GUZMANIA);
		addAfterInNaturalBlocks(TTBlocks.GUZMANIA, TTBlocks.LITHOPS);
		addAfterInNaturalBlocks(Blocks.GLOW_LICHEN, TTBlocks.DAWNTRAIL);

		addAfterInBuildingBlocks(Blocks.POLISHED_GRANITE_SLAB, TTBlocks.POLISHED_GRANITE_WALL);
		addAfterInBuildingBlocks(TTBlocks.POLISHED_GRANITE_WALL, TTBlocks.GRANITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.GRANITE_BRICKS, TTBlocks.CRACKED_GRANITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CRACKED_GRANITE_BRICKS, TTBlocks.GRANITE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.GRANITE_BRICK_STAIRS, TTBlocks.GRANITE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.GRANITE_BRICK_SLAB, TTBlocks.GRANITE_BRICK_WALL);
		addAfterInBuildingBlocks(TTBlocks.GRANITE_BRICK_WALL, TTBlocks.CHISELED_GRANITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CHISELED_GRANITE_BRICKS, TTBlocks.MOSSY_GRANITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_GRANITE_BRICKS, TTBlocks.MOSSY_GRANITE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS, TTBlocks.MOSSY_GRANITE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_GRANITE_BRICK_SLAB, TTBlocks.MOSSY_GRANITE_BRICK_WALL);

		addAfterInBuildingBlocks(Blocks.POLISHED_DIORITE_SLAB, TTBlocks.POLISHED_DIORITE_WALL);
		addAfterInBuildingBlocks(TTBlocks.POLISHED_DIORITE_WALL, TTBlocks.DIORITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.DIORITE_BRICKS, TTBlocks.CRACKED_DIORITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CRACKED_DIORITE_BRICKS, TTBlocks.DIORITE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.DIORITE_BRICK_STAIRS, TTBlocks.DIORITE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.DIORITE_BRICK_SLAB, TTBlocks.DIORITE_BRICK_WALL);
		addAfterInBuildingBlocks(TTBlocks.DIORITE_BRICK_WALL, TTBlocks.CHISELED_DIORITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CHISELED_DIORITE_BRICKS, TTBlocks.MOSSY_DIORITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DIORITE_BRICKS, TTBlocks.MOSSY_DIORITE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS, TTBlocks.MOSSY_DIORITE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DIORITE_BRICK_SLAB, TTBlocks.MOSSY_DIORITE_BRICK_WALL);

		addAfterInBuildingBlocks(Blocks.POLISHED_ANDESITE_SLAB, TTBlocks.POLISHED_ANDESITE_WALL);
		addAfterInBuildingBlocks(TTBlocks.POLISHED_ANDESITE_WALL, TTBlocks.ANDESITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.ANDESITE_BRICKS, TTBlocks.CRACKED_ANDESITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CRACKED_ANDESITE_BRICKS, TTBlocks.ANDESITE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.ANDESITE_BRICK_STAIRS, TTBlocks.ANDESITE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.ANDESITE_BRICK_SLAB, TTBlocks.ANDESITE_BRICK_WALL);
		addAfterInBuildingBlocks(TTBlocks.ANDESITE_BRICK_WALL, TTBlocks.CHISELED_ANDESITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CHISELED_ANDESITE_BRICKS, TTBlocks.MOSSY_ANDESITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_ANDESITE_BRICKS, TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS, TTBlocks.MOSSY_ANDESITE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB, TTBlocks.MOSSY_ANDESITE_BRICK_WALL);

		addBeforeInBuildingBlocks(Blocks.DEEPSLATE, Blocks.CALCITE);
		addAfterInBuildingBlocks(Blocks.CALCITE, TTBlocks.CALCITE_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.CALCITE_STAIRS, TTBlocks.CALCITE_SLAB);
		addAfterInBuildingBlocks(TTBlocks.CALCITE_SLAB, TTBlocks.CALCITE_WALL);
		addAfterInBuildingBlocks(TTBlocks.CALCITE_WALL, TTBlocks.POLISHED_CALCITE);
		addAfterInBuildingBlocks(TTBlocks.POLISHED_CALCITE, TTBlocks.POLISHED_CALCITE_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.POLISHED_CALCITE_STAIRS, TTBlocks.POLISHED_CALCITE_SLAB);
		addAfterInBuildingBlocks(TTBlocks.POLISHED_CALCITE_SLAB, TTBlocks.POLISHED_CALCITE_WALL);
		addAfterInBuildingBlocks(TTBlocks.POLISHED_CALCITE_WALL, TTBlocks.CALCITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CALCITE_BRICKS, TTBlocks.CRACKED_CALCITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CRACKED_CALCITE_BRICKS, TTBlocks.CALCITE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.CALCITE_BRICK_STAIRS, TTBlocks.CALCITE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.CALCITE_BRICK_SLAB, TTBlocks.CALCITE_BRICK_WALL);
		addAfterInBuildingBlocks(TTBlocks.CALCITE_BRICK_WALL, TTBlocks.CHISELED_CALCITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CHISELED_CALCITE_BRICKS, TTBlocks.MOSSY_CALCITE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_CALCITE_BRICKS, TTBlocks.MOSSY_CALCITE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS, TTBlocks.MOSSY_CALCITE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_CALCITE_BRICK_SLAB, TTBlocks.MOSSY_CALCITE_BRICK_WALL);

		addAfterInBuildingBlocks(Blocks.TUFF_BRICKS, TTBlocks.CRACKED_TUFF_BRICKS);
		addAfterInBuildingBlocks(Blocks.CHISELED_TUFF_BRICKS, TTBlocks.MOSSY_TUFF_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_TUFF_BRICKS, TTBlocks.MOSSY_TUFF_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_TUFF_BRICK_STAIRS, TTBlocks.MOSSY_TUFF_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_TUFF_BRICK_SLAB, TTBlocks.MOSSY_TUFF_BRICK_WALL);

		addAfterInBuildingBlocks(Blocks.BRICKS, TTBlocks.CRACKED_BRICKS);
		addAfterInBuildingBlocks(Blocks.BRICK_WALL, TTBlocks.MOSSY_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_BRICKS, TTBlocks.MOSSY_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_BRICK_STAIRS, TTBlocks.MOSSY_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_BRICK_SLAB, TTBlocks.MOSSY_BRICK_WALL);

		addAfterInBuildingBlocks(Blocks.COBBLED_DEEPSLATE_WALL, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_COBBLED_DEEPSLATE, TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB, TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL);

		addAfterInBuildingBlocks(Blocks.DEEPSLATE_BRICK_WALL, TTBlocks.MOSSY_DEEPSLATE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_BRICKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL);

		addAfterInBuildingBlocks(Blocks.DEEPSLATE_TILE_WALL, TTBlocks.MOSSY_DEEPSLATE_TILES);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_TILES, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB);
		addAfterInBuildingBlocks(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);

		addAfterInBuildingBlocks(Blocks.SMOOTH_SANDSTONE_SLAB, TTBlocks.SMOOTH_SANDSTONE_WALL);
		addBeforeInBuildingBlocks(Blocks.CUT_SANDSTONE_SLAB, TTBlocks.CUT_SANDSTONE_STAIRS);
		addAfterInBuildingBlocks(Blocks.CUT_SANDSTONE_SLAB, TTBlocks.CUT_SANDSTONE_WALL);

		addAfterInBuildingBlocks(Blocks.SMOOTH_RED_SANDSTONE_SLAB, TTBlocks.SMOOTH_RED_SANDSTONE_WALL);
		addBeforeInBuildingBlocks(Blocks.CUT_RED_SANDSTONE_SLAB, TTBlocks.CUT_RED_SANDSTONE_STAIRS);
		addAfterInBuildingBlocks(Blocks.CUT_RED_SANDSTONE_SLAB, TTBlocks.CUT_RED_SANDSTONE_WALL);

		addAfterInBuildingBlocks(Blocks.PRISMARINE_BRICK_SLAB, TTBlocks.PRISMARINE_BRICK_WALL);
		addAfterInBuildingBlocks(Blocks.DARK_PRISMARINE_SLAB, TTBlocks.DARK_PRISMARINE_WALL);

		addAfterInBuildingBlocks(Blocks.END_STONE, TTBlocks.END_STONE_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.END_STONE_STAIRS, TTBlocks.END_STONE_SLAB);
		addAfterInBuildingBlocks(TTBlocks.END_STONE_SLAB, TTBlocks.END_STONE_WALL);

		addAfterInBuildingBlocks(TTBlocks.END_STONE_WALL, TTBlocks.CHORAL_END_STONE);
		addAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE, TTBlocks.CHORAL_END_STONE_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_STAIRS, TTBlocks.CHORAL_END_STONE_SLAB);
		addAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_SLAB, TTBlocks.CHORAL_END_STONE_WALL);

		addAfterInBuildingBlocks(Blocks.END_STONE_BRICKS, TTBlocks.CRACKED_END_STONE_BRICKS);
		addAfterInBuildingBlocks(Blocks.END_STONE_BRICK_WALL, TTBlocks.CHISELED_END_STONE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CHISELED_END_STONE_BRICKS, TTBlocks.CHORAL_END_STONE_BRICKS);
		addAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_BRICKS, TTBlocks.CHORAL_END_STONE_BRICK_STAIRS);
		addAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS, TTBlocks.CHORAL_END_STONE_BRICK_SLAB);
		addAfterInBuildingBlocks(TTBlocks.CHORAL_END_STONE_BRICK_SLAB, TTBlocks.CHORAL_END_STONE_BRICK_WALL);

		addAfterInBuildingBlocks(Blocks.PURPUR_BLOCK, TTBlocks.CRACKED_PURPUR_BLOCK);
		addAfterInBuildingBlocks(Blocks.PURPUR_SLAB, TTBlocks.PURPUR_WALL);
		addAfterInBuildingBlocks(TTBlocks.PURPUR_WALL, TTBlocks.CHISELED_PURPUR_BLOCK);

		addAfterInSpawnEggs(Blocks.TRIAL_SPAWNER, TTBlocks.COFFIN);
		addAfterInRedstone(Blocks.OBSERVER, TTBlocks.SURVEYOR);
		addAfterInFunctionalBlocks(Blocks.MAGMA_BLOCK, TTBlocks.ECTOPLASM_BLOCK);

		// ITEMS
		addAfterInSpawnEggs(Items.ALLAY_SPAWN_EGG, TTItems.APPARITION_SPAWN_EGG);
		addBeforeInIngredients(Items.MAGMA_CREAM, TTItems.ECTOPLASM);

		addAfterInNaturalBlocks(Items.TORCHFLOWER_SEEDS, TTItems.CYAN_ROSE_SEEDS);
		addAfterInNaturalBlocks(TTItems.CYAN_ROSE_SEEDS, TTItems.DAWNTRAIL_SEEDS);
		addAfterInNaturalBlocks(Items.PITCHER_POD, TTItems.MANEDROP_GERM);
		addAfterInNaturalBlocks(TTItems.MANEDROP_GERM, TTItems.GUZMANIA_SEEDS);
		addAfterInNaturalBlocks(TTItems.GUZMANIA_SEEDS, TTItems.LITHOPS_SEEDS);

		addAfterInIngredients(Items.ARMS_UP_POTTERY_SHERD, TTItems.AURORA_POTTERY_SHERD);
		addBeforeInIngredients(Items.BLADE_POTTERY_SHERD, TTItems.BAIT_POTTERY_SHERD);
		addAfterInIngredients(Items.BLADE_POTTERY_SHERD, TTItems.BLOOM_POTTERY_SHERD);
		addAfterInIngredients(TTItems.BLOOM_POTTERY_SHERD, TTItems.BOLT_POTTERY_SHERD);
		addBeforeInIngredients(Items.BURN_POTTERY_SHERD, TTItems.BULLSEYE_POTTERY_SHERD);
		addBeforeInIngredients(Items.DANGER_POTTERY_SHERD, TTItems.CARRIER_POTTERY_SHERD);
		addAfterInIngredients(TTItems.CARRIER_POTTERY_SHERD, TTItems.CLUCK_POTTERY_SHERD);
		addBeforeInIngredients(TTItems.CLUCK_POTTERY_SHERD, TTItems.CRAWL_POTTERY_SHERD);
		addAfterInIngredients(TTItems.CRAWL_POTTERY_SHERD, TTItems.CRESCENT_POTTERY_SHERD);
		addAfterInIngredients(TTItems.CRESCENT_POTTERY_SHERD, TTItems.CULTIVATOR_POTTERY_SHERD);
		addAfterInIngredients(Items.DANGER_POTTERY_SHERD, TTItems.DROUGHT_POTTERY_SHERD);
		addBeforeInIngredients(Items.EXPLORER_POTTERY_SHERD, TTItems.ENCLOSURE_POTTERY_SHERD);
		addAfterInIngredients(TTItems.ENCLOSURE_POTTERY_SHERD, TTItems.ESSENCE_POTTERY_SHERD);
		addAfterInIngredients(Items.EXPLORER_POTTERY_SHERD, TTItems.EYE_POTTERY_SHERD);
		addAfterInIngredients(Items.FLOW_POTTERY_SHERD, TTItems.FOCUS_POTTERY_SHERD);
		addAfterInIngredients(TTItems.FOCUS_POTTERY_SHERD, TTItems.FROST_POTTERY_SHERD);
		addBeforeInIngredients(Items.HEART_POTTERY_SHERD, TTItems.HARE_POTTERY_SHERD);
		addAfterInIngredients(TTItems.HARE_POTTERY_SHERD, TTItems.HEIGHT_POTTERY_SHERD);
		addAfterInIngredients(Items.HOWL_POTTERY_SHERD, TTItems.HUMP_POTTERY_SHERD);
		addAfterInIngredients(TTItems.HUMP_POTTERY_SHERD, TTItems.ILLUMINATOR_POTTERY_SHERD);
		addAfterInIngredients(TTItems.ILLUMINATOR_POTTERY_SHERD, TTItems.INCIDENCE_POTTERY_SHERD);
		addBeforeInIngredients(Items.MINER_POTTERY_SHERD, TTItems.LUMBER_POTTERY_SHERD);
		addAfterInIngredients(Items.MOURNER_POTTERY_SHERD, TTItems.NAVIGATOR_POTTERY_SHERD);
		addAfterInIngredients(TTItems.NAVIGATOR_POTTERY_SHERD, TTItems.NEEDLES_POTTERY_SHERD);
		addBeforeInIngredients(Items.PLENTY_POTTERY_SHERD, TTItems.OMEN_POTTERY_SHERD);
		addAfterInIngredients(Items.PLENTY_POTTERY_SHERD, TTItems.PLUME_POTTERY_SHERD);
		addAfterInIngredients(Items.PRIZE_POTTERY_SHERD,TTItems. PROTECTION_POTTERY_SHERD);
		addAfterInIngredients(Items.SHEAF_POTTERY_SHERD, TTItems.SHED_POTTERY_SHERD);
		addAfterInIngredients(Items.SHELTER_POTTERY_SHERD, TTItems.SHINE_POTTERY_SHERD);
		addAfterInIngredients(TTItems.SHINE_POTTERY_SHERD, TTItems.SHOWER_POTTERY_SHERD);
		addAfterInIngredients(Items.SNORT_POTTERY_SHERD, TTItems.SPADE_POTTERY_SHERD);
		addAfterInIngredients(TTItems.SPADE_POTTERY_SHERD, TTItems.SPROUT_POTTERY_SHERD);
		addAfterInIngredients(TTItems.SPROUT_POTTERY_SHERD, TTItems.VESSEL_POTTERY_SHERD);
		addAfterInIngredients(TTItems.VESSEL_POTTERY_SHERD, TTItems.WITHER_POTTERY_SHERD);

		addAfterInIngredients(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE);
		addAfterInIngredients(TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE);
		addBeforeInIngredients(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE);
		addBeforeInIngredients(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE);
		addAfterInIngredients(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE);
		addAfterInIngredients(TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE);
		addAfterInIngredients(TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE);
		addAfterInIngredients(TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE, TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE);

		// Ruins discs should come before Trail Ruins!
		addBeforeInToolsAndUtilities(Items.MUSIC_DISC_RELIC, TTItems.MUSIC_DISC_STASIS);
		// Catacombs discs should come after Trail Ruins!
		addAfterInToolsAndUtilities(Items.MUSIC_DISC_RELIC, TTItems.MUSIC_DISC_FAUSSE_VIE);
		addAfterInToolsAndUtilities(TTItems.MUSIC_DISC_FAUSSE_VIE, TTItems.MUSIC_DISC_OSSUAIRE);
	}

	private static void addAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void addBeforeInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void addAfterInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void addAfterInRedstone(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void addAfterInFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void addBeforeInRedstoneBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void addInToolsAndUtilities(ItemLike item) {
		FrozenCreativeTabs.add(item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void addBeforeInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void addAfterInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void addBeforeInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void addAfterInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void addBeforeInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void addAfterInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void addAfterInCombat(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.COMBAT);
	}

	private static void addBeforeInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

	private static void addAfterInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}
}
