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

import net.frozenblock.lib.item.api.PlaceInAirBlockItem;
import net.frozenblock.trailiertales.item.CoffinItem;
import net.frozenblock.trailiertales.references.TTBlockItemIds;
import net.frozenblock.trailiertales.references.TTItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;

public final class TTItems {
	// BLOCK ITEMS
	// SUSPICIOUS BLOCKS
	public static final Item SUSPICIOUS_RED_SAND = Items.registerBlock(TTBlockItemIds.SUSPICIOUS_RED_SAND, TTBlocks.SUSPICIOUS_RED_SAND);
	public static final Item SUSPICIOUS_DIRT = Items.registerBlock(TTBlockItemIds.SUSPICIOUS_DIRT, TTBlocks.SUSPICIOUS_DIRT);
	public static final Item SUSPICIOUS_CLAY = Items.registerBlock(TTBlockItemIds.SUSPICIOUS_CLAY, TTBlocks.SUSPICIOUS_CLAY);

	// PLANTS
	public static final Item CYAN_ROSE_SEEDS = Items.registerItem(TTBlockItemIds.CYAN_ROSE_CROP, Items.createBlockItemWithCustomItemName(TTBlocks.CYAN_ROSE_CROP));
	public static final Item CYAN_ROSE = Items.registerBlock(TTBlockItemIds.CYAN_ROSE, TTBlocks.CYAN_ROSE);

	public static final Item MANEDROP_GERM = Items.registerItem(TTBlockItemIds.MANEDROP_CROP, Items.createBlockItemWithCustomItemName(TTBlocks.MANEDROP_CROP));
	public static final Item MANEDROP = Items.registerBlock(TTBlockItemIds.MANEDROP, TTBlocks.MANEDROP);

	public static final Item DAWNTRAIL_SEEDS = Items.registerItem(TTBlockItemIds.DAWNTRAIL_CROP, Items.createBlockItemWithCustomItemName(TTBlocks.DAWNTRAIL_CROP));
	public static final Item DAWNTRAIL = Items.registerBlock(TTBlockItemIds.DAWNTRAIL, TTBlocks.DAWNTRAIL);

	public static final Item GUZMANIA_SEEDS = Items.registerItem(TTBlockItemIds.GUZMANIA_CROP, Items.createBlockItemWithCustomItemName(TTBlocks.GUZMANIA_CROP));
	public static final Item GUZMANIA = Items.registerBlock(TTBlockItemIds.GUZMANIA, TTBlocks.GUZMANIA);

	public static final Item LITHOPS_SEEDS = Items.registerItem(TTBlockItemIds.LITHOPS_CROP, Items.createBlockItemWithCustomItemName(TTBlocks.LITHOPS_CROP));
	public static final Item LITHOPS = Items.registerBlock(TTBlockItemIds.LITHOPS, TTBlocks.LITHOPS);

	// STONE
	public static final Item STONE_WALL = Items.registerBlock(TTBlockItemIds.STONE_WALL, TTBlocks.STONE_WALL);

	// GRANITE
	public static final Item POLISHED_GRANITE_WALL = Items.registerBlock(TTBlockItemIds.POLISHED_GRANITE_WALL, TTBlocks.POLISHED_GRANITE_WALL);

	public static final Item GRANITE_BRICKS = Items.registerBlock(TTBlockItemIds.GRANITE_BRICKS, TTBlocks.GRANITE_BRICKS);
	public static final Item CHISELED_GRANITE_BRICKS = Items.registerBlock(TTBlockItemIds.CHISELED_GRANITE_BRICKS, TTBlocks.CHISELED_GRANITE_BRICKS);
	public static final Item GRANITE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.GRANITE_BRICK_STAIRS, TTBlocks.GRANITE_BRICK_STAIRS);
	public static final Item GRANITE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.GRANITE_BRICK_SLAB, TTBlocks.GRANITE_BRICK_SLAB);
	public static final Item GRANITE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.GRANITE_BRICK_WALL, TTBlocks.GRANITE_BRICK_WALL);
	public static final Item CRACKED_GRANITE_BRICKS = Items.registerBlock(TTBlockItemIds.CRACKED_GRANITE_BRICKS, TTBlocks.CRACKED_GRANITE_BRICKS);

	public static final Item MOSSY_GRANITE_BRICKS = Items.registerBlock(TTBlockItemIds.MOSSY_GRANITE_BRICKS, TTBlocks.MOSSY_GRANITE_BRICKS);
	public static final Item MOSSY_GRANITE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_GRANITE_BRICK_STAIRS, TTBlocks.MOSSY_GRANITE_BRICK_STAIRS);
	public static final Item MOSSY_GRANITE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_GRANITE_BRICK_SLAB, TTBlocks.MOSSY_GRANITE_BRICK_SLAB);
	public static final Item MOSSY_GRANITE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_GRANITE_BRICK_WALL, TTBlocks.MOSSY_GRANITE_BRICK_WALL);

	// DIORITE
	public static final Item POLISHED_DIORITE_WALL = Items.registerBlock(TTBlockItemIds.POLISHED_DIORITE_WALL, TTBlocks.POLISHED_DIORITE_WALL);

	public static final Item DIORITE_BRICKS = Items.registerBlock(TTBlockItemIds.DIORITE_BRICKS, TTBlocks.DIORITE_BRICKS);
	public static final Item DIORITE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.DIORITE_BRICK_STAIRS, TTBlocks.DIORITE_BRICK_STAIRS);
	public static final Item DIORITE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.DIORITE_BRICK_SLAB, TTBlocks.DIORITE_BRICK_SLAB);
	public static final Item DIORITE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.DIORITE_BRICK_WALL, TTBlocks.DIORITE_BRICK_WALL);
	public static final Item CRACKED_DIORITE_BRICKS = Items.registerBlock(TTBlockItemIds.CRACKED_DIORITE_BRICKS, TTBlocks.CRACKED_DIORITE_BRICKS);
	public static final Item CHISELED_DIORITE_BRICKS = Items.registerBlock(TTBlockItemIds.CHISELED_DIORITE_BRICKS, TTBlocks.CHISELED_DIORITE_BRICKS);

	public static final Item MOSSY_DIORITE_BRICKS = Items.registerBlock(TTBlockItemIds.MOSSY_DIORITE_BRICKS, TTBlocks.MOSSY_DIORITE_BRICKS);
	public static final Item MOSSY_DIORITE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_DIORITE_BRICK_STAIRS, TTBlocks.MOSSY_DIORITE_BRICK_STAIRS);
	public static final Item MOSSY_DIORITE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_DIORITE_BRICK_SLAB, TTBlocks.MOSSY_DIORITE_BRICK_SLAB);
	public static final Item MOSSY_DIORITE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_DIORITE_BRICK_WALL, TTBlocks.MOSSY_DIORITE_BRICK_WALL);

	// ANDESITE
	public static final Item POLISHED_ANDESITE_WALL = Items.registerBlock(TTBlockItemIds.POLISHED_ANDESITE_WALL, TTBlocks.POLISHED_ANDESITE_WALL);

	public static final Item ANDESITE_BRICKS = Items.registerBlock(TTBlockItemIds.ANDESITE_BRICKS, TTBlocks.ANDESITE_BRICKS);
	public static final Item ANDESITE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.ANDESITE_BRICK_STAIRS, TTBlocks.ANDESITE_BRICK_STAIRS);
	public static final Item ANDESITE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.ANDESITE_BRICK_SLAB, TTBlocks.ANDESITE_BRICK_SLAB);
	public static final Item ANDESITE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.ANDESITE_BRICK_WALL, TTBlocks.ANDESITE_BRICK_WALL);
	public static final Item CRACKED_ANDESITE_BRICKS = Items.registerBlock(TTBlockItemIds.CRACKED_ANDESITE_BRICKS, TTBlocks.CRACKED_ANDESITE_BRICKS);
	public static final Item CHISELED_ANDESITE_BRICKS = Items.registerBlock(TTBlockItemIds.CHISELED_ANDESITE_BRICKS, TTBlocks.CHISELED_ANDESITE_BRICKS);

	public static final Item MOSSY_ANDESITE_BRICKS = Items.registerBlock(TTBlockItemIds.MOSSY_ANDESITE_BRICKS, TTBlocks.MOSSY_ANDESITE_BRICKS);
	public static final Item MOSSY_ANDESITE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_ANDESITE_BRICK_STAIRS, TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS);
	public static final Item MOSSY_ANDESITE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_ANDESITE_BRICK_SLAB, TTBlocks.MOSSY_ANDESITE_BRICK_SLAB);
	public static final Item MOSSY_ANDESITE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_ANDESITE_BRICK_WALL, TTBlocks.MOSSY_ANDESITE_BRICK_WALL);

	// CALCITE
	public static final Item CALCITE_STAIRS = Items.registerBlock(TTBlockItemIds.CALCITE_STAIRS, TTBlocks.CALCITE_STAIRS);
	public static final Item CALCITE_SLAB = Items.registerBlock(TTBlockItemIds.CALCITE_SLAB, TTBlocks.CALCITE_SLAB);
	public static final Item CALCITE_WALL = Items.registerBlock(TTBlockItemIds.CALCITE_WALL, TTBlocks.CALCITE_WALL);
	public static final Item POLISHED_CALCITE = Items.registerBlock(TTBlockItemIds.POLISHED_CALCITE, TTBlocks.POLISHED_CALCITE);
	public static final Item POLISHED_CALCITE_STAIRS = Items.registerBlock(TTBlockItemIds.POLISHED_CALCITE_STAIRS, TTBlocks.POLISHED_CALCITE_STAIRS);
	public static final Item POLISHED_CALCITE_SLAB = Items.registerBlock(TTBlockItemIds.POLISHED_CALCITE_SLAB, TTBlocks.POLISHED_CALCITE_SLAB);
	public static final Item POLISHED_CALCITE_WALL = Items.registerBlock(TTBlockItemIds.POLISHED_CALCITE_WALL, TTBlocks.POLISHED_CALCITE_WALL);

	public static final Item CALCITE_BRICKS = Items.registerBlock(TTBlockItemIds.CALCITE_BRICKS, TTBlocks.CALCITE_BRICKS);
	public static final Item CALCITE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.CALCITE_BRICK_STAIRS, TTBlocks.CALCITE_BRICK_STAIRS);
	public static final Item CALCITE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.CALCITE_BRICK_SLAB, TTBlocks.CALCITE_BRICK_SLAB);
	public static final Item CALCITE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.CALCITE_BRICK_WALL, TTBlocks.CALCITE_BRICK_WALL);
	public static final Item CRACKED_CALCITE_BRICKS = Items.registerBlock(TTBlockItemIds.CRACKED_CALCITE_BRICKS, TTBlocks.CRACKED_CALCITE_BRICKS);
	public static final Item CHISELED_CALCITE_BRICKS = Items.registerBlock(TTBlockItemIds.CHISELED_CALCITE_BRICKS, TTBlocks.CHISELED_CALCITE_BRICKS);
	public static final Item MOSSY_CALCITE_BRICKS = Items.registerBlock(TTBlockItemIds.MOSSY_CALCITE_BRICKS, TTBlocks.MOSSY_CALCITE_BRICKS);
	public static final Item MOSSY_CALCITE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_CALCITE_BRICK_STAIRS, TTBlocks.MOSSY_CALCITE_BRICK_STAIRS);
	public static final Item MOSSY_CALCITE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_CALCITE_BRICK_SLAB, TTBlocks.MOSSY_CALCITE_BRICK_SLAB);
	public static final Item MOSSY_CALCITE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_CALCITE_BRICK_WALL, TTBlocks.MOSSY_CALCITE_BRICK_WALL);

	// TUFF
	public static final Item CRACKED_TUFF_BRICKS = Items.registerBlock(TTBlockItemIds.CRACKED_TUFF_BRICKS, TTBlocks.CRACKED_TUFF_BRICKS);
	public static final Item MOSSY_TUFF_BRICKS = Items.registerBlock(TTBlockItemIds.MOSSY_TUFF_BRICKS, TTBlocks.MOSSY_TUFF_BRICKS);
	public static final Item MOSSY_TUFF_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_TUFF_BRICK_STAIRS, TTBlocks.MOSSY_TUFF_BRICK_STAIRS);
	public static final Item MOSSY_TUFF_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_TUFF_BRICK_SLAB, TTBlocks.MOSSY_TUFF_BRICK_SLAB);
	public static final Item MOSSY_TUFF_BRICK_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_TUFF_BRICK_WALL, TTBlocks.MOSSY_TUFF_BRICK_WALL);

	// BRICKS
	public static final Item CRACKED_BRICKS = Items.registerBlock(TTBlockItemIds.CRACKED_BRICKS, TTBlocks.CRACKED_BRICKS);
	public static final Item MOSSY_BRICKS = Items.registerBlock(TTBlockItemIds.MOSSY_BRICKS, TTBlocks.MOSSY_BRICKS);
	public static final Item MOSSY_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_BRICK_STAIRS, TTBlocks.MOSSY_BRICK_STAIRS);
	public static final Item MOSSY_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_BRICK_SLAB, TTBlocks.MOSSY_BRICK_SLAB);
	public static final Item MOSSY_BRICK_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_BRICK_WALL, TTBlocks.MOSSY_BRICK_WALL);

	// RESIN
	public static final Item POLISHED_RESIN_BLOCK = Items.registerBlock(TTBlockItemIds.POLISHED_RESIN_BLOCK, TTBlocks.POLISHED_RESIN_BLOCK);
	public static final Item POLISHED_RESIN_STAIRS = Items.registerBlock(TTBlockItemIds.POLISHED_RESIN_STAIRS, TTBlocks.POLISHED_RESIN_STAIRS);
	public static final Item POLISHED_RESIN_SLAB = Items.registerBlock(TTBlockItemIds.POLISHED_RESIN_SLAB, TTBlocks.POLISHED_RESIN_SLAB);
	public static final Item POLISHED_RESIN_WALL = Items.registerBlock(TTBlockItemIds.POLISHED_RESIN_WALL, TTBlocks.POLISHED_RESIN_WALL);
	public static final Item CRACKED_RESIN_BRICKS = Items.registerBlock(TTBlockItemIds.CRACKED_RESIN_BRICKS, TTBlocks.CRACKED_RESIN_BRICKS);

	public static final Item PALE_MOSSY_RESIN_BRICKS = Items.registerBlock(TTBlockItemIds.PALE_MOSSY_RESIN_BRICKS, TTBlocks.PALE_MOSSY_RESIN_BRICKS);
	public static final Item PALE_MOSSY_RESIN_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_STAIRS, TTBlocks.PALE_MOSSY_RESIN_BRICK_STAIRS);
	public static final Item PALE_MOSSY_RESIN_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_SLAB, TTBlocks.PALE_MOSSY_RESIN_BRICK_SLAB);
	public static final Item PALE_MOSSY_RESIN_BRICK_WALL = Items.registerBlock(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_WALL, TTBlocks.PALE_MOSSY_RESIN_BRICK_WALL);

	// MOSSY DEEPSLATE
	public static final Item MOSSY_COBBLED_DEEPSLATE = Items.registerBlock(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
	public static final Item MOSSY_COBBLED_DEEPSLATE_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_STAIRS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS);
	public static final Item MOSSY_COBBLED_DEEPSLATE_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_SLAB, TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB);
	public static final Item MOSSY_COBBLED_DEEPSLATE_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_WALL, TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL);

	public static final Item MOSSY_DEEPSLATE_BRICKS = Items.registerBlock(TTBlockItemIds.MOSSY_DEEPSLATE_BRICKS, TTBlocks.MOSSY_DEEPSLATE_BRICKS);
	public static final Item MOSSY_DEEPSLATE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_STAIRS, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS);
	public static final Item MOSSY_DEEPSLATE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_SLAB, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB);
	public static final Item MOSSY_DEEPSLATE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_WALL, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL);

	public static final Item MOSSY_DEEPSLATE_TILES = Items.registerBlock(TTBlockItemIds.MOSSY_DEEPSLATE_TILES, TTBlocks.MOSSY_DEEPSLATE_TILES);
	public static final Item MOSSY_DEEPSLATE_TILE_STAIRS = Items.registerBlock(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_STAIRS, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);
	public static final Item MOSSY_DEEPSLATE_TILE_SLAB = Items.registerBlock(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_SLAB, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB);
	public static final Item MOSSY_DEEPSLATE_TILE_WALL = Items.registerBlock(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_WALL, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);

	// SANDSTONE
	public static final Item SMOOTH_SANDSTONE_WALL = Items.registerBlock(TTBlockItemIds.SMOOTH_SANDSTONE_WALL, TTBlocks.SMOOTH_SANDSTONE_WALL);
	public static final Item CUT_SANDSTONE_STAIRS = Items.registerBlock(TTBlockItemIds.CUT_SANDSTONE_STAIRS, TTBlocks.CUT_SANDSTONE_STAIRS);
	public static final Item CUT_SANDSTONE_WALL = Items.registerBlock(TTBlockItemIds.CUT_SANDSTONE_WALL, TTBlocks.CUT_SANDSTONE_WALL);

	// RED SANDSTONE
	public static final Item SMOOTH_RED_SANDSTONE_WALL = Items.registerBlock(TTBlockItemIds.SMOOTH_RED_SANDSTONE_WALL, TTBlocks.SMOOTH_RED_SANDSTONE_WALL);
	public static final Item CUT_RED_SANDSTONE_STAIRS = Items.registerBlock(TTBlockItemIds.CUT_RED_SANDSTONE_STAIRS, TTBlocks.CUT_RED_SANDSTONE_STAIRS);
	public static final Item CUT_RED_SANDSTONE_WALL = Items.registerBlock(TTBlockItemIds.CUT_RED_SANDSTONE_WALL, TTBlocks.CUT_RED_SANDSTONE_WALL);

	// PRISMARINE
	public static final Item PRISMARINE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.PRISMARINE_BRICK_WALL, TTBlocks.PRISMARINE_BRICK_WALL);
	public static final Item DARK_PRISMARINE_WALL = Items.registerBlock(TTBlockItemIds.DARK_PRISMARINE_WALL, TTBlocks.DARK_PRISMARINE_WALL);

	// END STIBE
	public static final Item END_STONE_STAIRS = Items.registerBlock(TTBlockItemIds.END_STONE_STAIRS, TTBlocks.END_STONE_STAIRS);
	public static final Item END_STONE_SLAB = Items.registerBlock(TTBlockItemIds.END_STONE_SLAB, TTBlocks.END_STONE_SLAB);
	public static final Item END_STONE_WALL = Items.registerBlock(TTBlockItemIds.END_STONE_WALL, TTBlocks.END_STONE_WALL);

	public static final Item CHORAL_END_STONE = Items.registerBlock(TTBlockItemIds.CHORAL_END_STONE, TTBlocks.CHORAL_END_STONE);
	public static final Item CHORAL_END_STONE_STAIRS = Items.registerBlock(TTBlockItemIds.CHORAL_END_STONE_STAIRS, TTBlocks.CHORAL_END_STONE_STAIRS);
	public static final Item CHORAL_END_STONE_SLAB = Items.registerBlock(TTBlockItemIds.CHORAL_END_STONE_SLAB, TTBlocks.CHORAL_END_STONE_SLAB);
	public static final Item CHORAL_END_STONE_WALL = Items.registerBlock(TTBlockItemIds.CHORAL_END_STONE_WALL, TTBlocks.CHORAL_END_STONE_WALL);

	public static final Item CRACKED_END_STONE_BRICKS = Items.registerBlock(TTBlockItemIds.CRACKED_END_STONE_BRICKS, TTBlocks.CRACKED_END_STONE_BRICKS);
	public static final Item CHISELED_END_STONE_BRICKS = Items.registerBlock(TTBlockItemIds.CHISELED_END_STONE_BRICKS, TTBlocks.CHISELED_END_STONE_BRICKS);
	public static final Item CHORAL_END_STONE_BRICKS = Items.registerBlock(TTBlockItemIds.CHORAL_END_STONE_BRICKS, TTBlocks.CHORAL_END_STONE_BRICKS);
	public static final Item CHORAL_END_STONE_BRICK_STAIRS = Items.registerBlock(TTBlockItemIds.CHORAL_END_STONE_BRICK_STAIRS, TTBlocks.CHORAL_END_STONE_BRICK_STAIRS);
	public static final Item CHORAL_END_STONE_BRICK_SLAB = Items.registerBlock(TTBlockItemIds.CHORAL_END_STONE_BRICK_SLAB, TTBlocks.CHORAL_END_STONE_BRICK_SLAB);
	public static final Item CHORAL_END_STONE_BRICK_WALL = Items.registerBlock(TTBlockItemIds.CHORAL_END_STONE_BRICK_WALL, TTBlocks.CHORAL_END_STONE_BRICK_WALL);

	// PURPUR
	public static final Item CRACKED_PURPUR_BLOCK = Items.registerBlock(TTBlockItemIds.CRACKED_PURPUR_BLOCK, TTBlocks.CRACKED_PURPUR_BLOCK);
	public static final Item PURPUR_WALL = Items.registerBlock(TTBlockItemIds.PURPUR_WALL, TTBlocks.PURPUR_WALL);
	public static final Item CHISELED_PURPUR_BLOCK = Items.registerBlock(TTBlockItemIds.CHISELED_PURPUR_BLOCK, TTBlocks.CHISELED_PURPUR_BLOCK);

	// CATACOMBS
	public static final Item COFFIN = Items.registerBlock(TTBlockItemIds.COFFIN, TTBlocks.COFFIN, CoffinItem::new);
	public static final Item SURVEYOR = Items.registerBlock(TTBlockItemIds.SURVEYOR, TTBlocks.SURVEYOR);
	public static final Item ECTOPLASM_BLOCK = Items.registerBlock(TTBlockItemIds.ECTOPLASM_BLOCK, TTBlocks.ECTOPLASM_BLOCK, PlaceInAirBlockItem::new);

	// ITEMS
	// SPAWN EGGS
	public static final Item APPARITION_SPAWN_EGG = Items.registerSpawnEgg(TTItemIds.APPARITION_SPAWN_EGG, TTEntityTypes.APPARITION);

	// CATACOMBS
	public static final Item ECTOPLASM = Items.registerItem(TTItemIds.ECTOPLASM);

	// POTTERY SHERDS
	/**
	 * Don't forget to go to {@link TTDecoratedPotPatternRegistry} to register patterns!
	 */
	public static final Item AURORA_POTTERY_SHERD = registerPotterySherd(TTItemIds.AURORA_POTTERY_SHERD);
	public static final Item BAIT_POTTERY_SHERD = registerPotterySherd(TTItemIds.BAIT_POTTERY_SHERD);
	public static final Item BLOOM_POTTERY_SHERD = registerPotterySherd(TTItemIds.BLOOM_POTTERY_SHERD);
	public static final Item BOLT_POTTERY_SHERD = registerPotterySherd(TTItemIds.BOLT_POTTERY_SHERD);
	public static final Item BULLSEYE_POTTERY_SHERD = registerPotterySherd(TTItemIds.BULLSEYE_POTTERY_SHERD);
	public static final Item CARRIER_POTTERY_SHERD = registerPotterySherd(TTItemIds.CARRIER_POTTERY_SHERD);
	public static final Item CLUCK_POTTERY_SHERD = registerPotterySherd(TTItemIds.CLUCK_POTTERY_SHERD);
	public static final Item CRAWL_POTTERY_SHERD = registerPotterySherd(TTItemIds.CRAWL_POTTERY_SHERD);
	public static final Item CRESCENT_POTTERY_SHERD = registerPotterySherd(TTItemIds.CRESCENT_POTTERY_SHERD);
	public static final Item CULTIVATOR_POTTERY_SHERD = registerPotterySherd(TTItemIds.CULTIVATOR_POTTERY_SHERD);
	public static final Item DROUGHT_POTTERY_SHERD = registerPotterySherd(TTItemIds.DROUGHT_POTTERY_SHERD);
	public static final Item ENCLOSURE_POTTERY_SHERD = registerPotterySherd(TTItemIds.ENCLOSURE_POTTERY_SHERD);
	public static final Item ESSENCE_POTTERY_SHERD = registerPotterySherd(TTItemIds.ESSENCE_POTTERY_SHERD);
	public static final Item EYE_POTTERY_SHERD = registerPotterySherd(TTItemIds.EYE_POTTERY_SHERD);
	public static final Item FOCUS_POTTERY_SHERD = registerPotterySherd(TTItemIds.FOCUS_POTTERY_SHERD);
	public static final Item FROST_POTTERY_SHERD = registerPotterySherd(TTItemIds.FROST_POTTERY_SHERD);
	public static final Item HARE_POTTERY_SHERD = registerPotterySherd(TTItemIds.HARE_POTTERY_SHERD);
	public static final Item HEIGHT_POTTERY_SHERD = registerPotterySherd(TTItemIds.HEIGHT_POTTERY_SHERD);
	public static final Item HUMP_POTTERY_SHERD = registerPotterySherd(TTItemIds.HUMP_POTTERY_SHERD);
	public static final Item ILLUMINATOR_POTTERY_SHERD = registerPotterySherd(TTItemIds.ILLUMINATOR_POTTERY_SHERD);
	public static final Item INCIDENCE_POTTERY_SHERD = registerPotterySherd(TTItemIds.INCIDENCE_POTTERY_SHERD);
	public static final Item LUMBER_POTTERY_SHERD = registerPotterySherd(TTItemIds.LUMBER_POTTERY_SHERD);
	public static final Item NAVIGATOR_POTTERY_SHERD = registerPotterySherd(TTItemIds.NAVIGATOR_POTTERY_SHERD);
	public static final Item NEEDLES_POTTERY_SHERD = registerPotterySherd(TTItemIds.NEEDLES_POTTERY_SHERD);
	public static final Item OMEN_POTTERY_SHERD = registerPotterySherd(TTItemIds.OMEN_POTTERY_SHERD);
	public static final Item PLUME_POTTERY_SHERD = registerPotterySherd(TTItemIds.PLUME_POTTERY_SHERD);
	public static final Item PROTECTION_POTTERY_SHERD = registerPotterySherd(TTItemIds.PROTECTION_POTTERY_SHERD);
	public static final Item SHED_POTTERY_SHERD = registerPotterySherd(TTItemIds.SHED_POTTERY_SHERD);
	public static final Item SHINE_POTTERY_SHERD = registerPotterySherd(TTItemIds.SHINE_POTTERY_SHERD);
	public static final Item SHOWER_POTTERY_SHERD = registerPotterySherd(TTItemIds.SHOWER_POTTERY_SHERD);
	public static final Item SPADE_POTTERY_SHERD = registerPotterySherd(TTItemIds.SPADE_POTTERY_SHERD);
	public static final Item SPROUT_POTTERY_SHERD = registerPotterySherd(TTItemIds.SPROUT_POTTERY_SHERD);
	public static final Item VESSEL_POTTERY_SHERD = registerPotterySherd(TTItemIds.VESSEL_POTTERY_SHERD);
	public static final Item WITHER_POTTERY_SHERD = registerPotterySherd(TTItemIds.WITHER_POTTERY_SHERD);

	// SMITHING TEMPLATES
	public static final Item UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final Item MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final Item GEODE_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final Item OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final Item MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final Item ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final Item COT_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.COT_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final Item EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE);

	// MUSIC DISCS
	public static final Item MUSIC_DISC_FAUSSE_VIE = registerMusicDisc(TTItemIds.MUSIC_DISC_FAUSSE_VIE, TTJukeboxSongs.FAUSSE_VIE);
	public static final Item MUSIC_DISC_STASIS = registerMusicDisc(TTItemIds.MUSIC_DISC_STASIS, TTJukeboxSongs.STASIS);
	public static final Item MUSIC_DISC_OSSUAIRE = registerMusicDisc(TTItemIds.MUSIC_DISC_OSSUAIRE, TTJukeboxSongs.OSSUAIRE);

	public static void init() {}

	public static Item registerPotterySherd(ResourceKey<Item> id) {
		return Items.registerItem(id, new Properties().rarity(Rarity.UNCOMMON));
	}

	public static Item registerArmorTrimTemplate(ResourceKey<Item> id) {
		return Items.registerItem(id, SmithingTemplateItem::createArmorTrimTemplate, new Properties().rarity(Rarity.UNCOMMON));
	}

	public static Item registerMusicDisc(ResourceKey<Item> id, ResourceKey<JukeboxSong> song) {
		return Items.registerItem(id, new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(song));
	}
}
