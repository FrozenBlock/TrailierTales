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
import net.frozenblock.lib.platform.api.registry.DeferredItem;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.frozenblock.trailiertales.item.CoffinItem;
import net.frozenblock.trailiertales.references.TTBlockItemIds;
import net.frozenblock.trailiertales.references.TTItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public final class TTItems {
	private static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(TTConstants.MOD_ID).requiredFeatures(TTFeatureFlags.FEATURE_FLAG);

	// BLOCK ITEMS
	// SUSPICIOUS BLOCKS
	public static final DeferredItem<BlockItem> SUSPICIOUS_RED_SAND = REGISTER.registerSimpleBlockItem(TTBlockItemIds.SUSPICIOUS_RED_SAND, TTBlocks.SUSPICIOUS_RED_SAND);
	public static final DeferredItem<BlockItem> SUSPICIOUS_DIRT = REGISTER.registerSimpleBlockItem(TTBlockItemIds.SUSPICIOUS_DIRT, TTBlocks.SUSPICIOUS_DIRT);
	public static final DeferredItem<BlockItem> SUSPICIOUS_CLAY = REGISTER.registerSimpleBlockItem(TTBlockItemIds.SUSPICIOUS_CLAY, TTBlocks.SUSPICIOUS_CLAY);

	// PLANTS
	public static final DeferredItem<BlockItem> CYAN_ROSE_SEEDS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CYAN_ROSE_CROP, TTBlocks.CYAN_ROSE_CROP,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<BlockItem> CYAN_ROSE = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CYAN_ROSE, TTBlocks.CYAN_ROSE,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH)
	);

	public static final DeferredItem<BlockItem> MANEDROP_GERM = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MANEDROP_CROP, TTBlocks.MANEDROP_CROP,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<BlockItem> MANEDROP = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MANEDROP, TTBlocks.MANEDROP,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH)
	);

	public static final DeferredItem<BlockItem> DAWNTRAIL_SEEDS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.DAWNTRAIL_CROP, TTBlocks.DAWNTRAIL_CROP,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<BlockItem> DAWNTRAIL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.DAWNTRAIL, TTBlocks.DAWNTRAIL,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH)
	);

	public static final DeferredItem<BlockItem> GUZMANIA_SEEDS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.GUZMANIA_CROP, TTBlocks.GUZMANIA_CROP,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<DoubleHighBlockItem> GUZMANIA = REGISTER.registerDoubleHighBlockItem(TTBlockItemIds.GUZMANIA, TTBlocks.GUZMANIA,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH)
	);

	public static final DeferredItem<BlockItem> LITHOPS_SEEDS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.LITHOPS_CROP, TTBlocks.LITHOPS_CROP,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<BlockItem> LITHOPS = REGISTER.registerFlowerBedItem(TTBlockItemIds.LITHOPS, TTBlocks.LITHOPS);

	// STONE
	public static final DeferredItem<BlockItem> STONE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.STONE_WALL, TTBlocks.STONE_WALL);

	// GRANITE
	public static final DeferredItem<BlockItem> POLISHED_GRANITE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_GRANITE_WALL, TTBlocks.POLISHED_GRANITE_WALL);

	public static final DeferredItem<BlockItem> GRANITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.GRANITE_BRICKS, TTBlocks.GRANITE_BRICKS);
	public static final DeferredItem<BlockItem> CHISELED_GRANITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHISELED_GRANITE_BRICKS, TTBlocks.CHISELED_GRANITE_BRICKS);
	public static final DeferredItem<BlockItem> GRANITE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.GRANITE_BRICK_STAIRS, TTBlocks.GRANITE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> GRANITE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.GRANITE_BRICK_SLAB, TTBlocks.GRANITE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> GRANITE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.GRANITE_BRICK_WALL, TTBlocks.GRANITE_BRICK_WALL);
	public static final DeferredItem<BlockItem> CRACKED_GRANITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_GRANITE_BRICKS, TTBlocks.CRACKED_GRANITE_BRICKS);

	public static final DeferredItem<BlockItem> MOSSY_GRANITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_GRANITE_BRICKS, TTBlocks.MOSSY_GRANITE_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_GRANITE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_GRANITE_BRICK_STAIRS, TTBlocks.MOSSY_GRANITE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_GRANITE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_GRANITE_BRICK_SLAB, TTBlocks.MOSSY_GRANITE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_GRANITE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_GRANITE_BRICK_WALL, TTBlocks.MOSSY_GRANITE_BRICK_WALL);

	// DIORITE
	public static final DeferredItem<BlockItem> POLISHED_DIORITE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_DIORITE_WALL, TTBlocks.POLISHED_DIORITE_WALL);

	public static final DeferredItem<BlockItem> DIORITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.DIORITE_BRICKS, TTBlocks.DIORITE_BRICKS);
	public static final DeferredItem<BlockItem> DIORITE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.DIORITE_BRICK_STAIRS, TTBlocks.DIORITE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> DIORITE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.DIORITE_BRICK_SLAB, TTBlocks.DIORITE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> DIORITE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.DIORITE_BRICK_WALL, TTBlocks.DIORITE_BRICK_WALL);
	public static final DeferredItem<BlockItem> CRACKED_DIORITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_DIORITE_BRICKS, TTBlocks.CRACKED_DIORITE_BRICKS);
	public static final DeferredItem<BlockItem> CHISELED_DIORITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHISELED_DIORITE_BRICKS, TTBlocks.CHISELED_DIORITE_BRICKS);

	public static final DeferredItem<BlockItem> MOSSY_DIORITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DIORITE_BRICKS, TTBlocks.MOSSY_DIORITE_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_DIORITE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DIORITE_BRICK_STAIRS, TTBlocks.MOSSY_DIORITE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_DIORITE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DIORITE_BRICK_SLAB, TTBlocks.MOSSY_DIORITE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_DIORITE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DIORITE_BRICK_WALL, TTBlocks.MOSSY_DIORITE_BRICK_WALL);

	// ANDESITE
	public static final DeferredItem<BlockItem> POLISHED_ANDESITE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_ANDESITE_WALL, TTBlocks.POLISHED_ANDESITE_WALL);

	public static final DeferredItem<BlockItem> ANDESITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.ANDESITE_BRICKS, TTBlocks.ANDESITE_BRICKS);
	public static final DeferredItem<BlockItem> ANDESITE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.ANDESITE_BRICK_STAIRS, TTBlocks.ANDESITE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> ANDESITE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.ANDESITE_BRICK_SLAB, TTBlocks.ANDESITE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> ANDESITE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.ANDESITE_BRICK_WALL, TTBlocks.ANDESITE_BRICK_WALL);
	public static final DeferredItem<BlockItem> CRACKED_ANDESITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_ANDESITE_BRICKS, TTBlocks.CRACKED_ANDESITE_BRICKS);
	public static final DeferredItem<BlockItem> CHISELED_ANDESITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHISELED_ANDESITE_BRICKS, TTBlocks.CHISELED_ANDESITE_BRICKS);

	public static final DeferredItem<BlockItem> MOSSY_ANDESITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_ANDESITE_BRICKS, TTBlocks.MOSSY_ANDESITE_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_ANDESITE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_ANDESITE_BRICK_STAIRS, TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_ANDESITE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_ANDESITE_BRICK_SLAB, TTBlocks.MOSSY_ANDESITE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_ANDESITE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_ANDESITE_BRICK_WALL, TTBlocks.MOSSY_ANDESITE_BRICK_WALL);

	// CALCITE
	public static final DeferredItem<BlockItem> CALCITE_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CALCITE_STAIRS, TTBlocks.CALCITE_STAIRS);
	public static final DeferredItem<BlockItem> CALCITE_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CALCITE_SLAB, TTBlocks.CALCITE_SLAB);
	public static final DeferredItem<BlockItem> CALCITE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CALCITE_WALL, TTBlocks.CALCITE_WALL);
	public static final DeferredItem<BlockItem> POLISHED_CALCITE = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_CALCITE, TTBlocks.POLISHED_CALCITE);
	public static final DeferredItem<BlockItem> POLISHED_CALCITE_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_CALCITE_STAIRS, TTBlocks.POLISHED_CALCITE_STAIRS);
	public static final DeferredItem<BlockItem> POLISHED_CALCITE_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_CALCITE_SLAB, TTBlocks.POLISHED_CALCITE_SLAB);
	public static final DeferredItem<BlockItem> POLISHED_CALCITE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_CALCITE_WALL, TTBlocks.POLISHED_CALCITE_WALL);

	public static final DeferredItem<BlockItem> CALCITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CALCITE_BRICKS, TTBlocks.CALCITE_BRICKS);
	public static final DeferredItem<BlockItem> CALCITE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CALCITE_BRICK_STAIRS, TTBlocks.CALCITE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> CALCITE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CALCITE_BRICK_SLAB, TTBlocks.CALCITE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> CALCITE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CALCITE_BRICK_WALL, TTBlocks.CALCITE_BRICK_WALL);
	public static final DeferredItem<BlockItem> CRACKED_CALCITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_CALCITE_BRICKS, TTBlocks.CRACKED_CALCITE_BRICKS);
	public static final DeferredItem<BlockItem> CHISELED_CALCITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHISELED_CALCITE_BRICKS, TTBlocks.CHISELED_CALCITE_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_CALCITE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_CALCITE_BRICKS, TTBlocks.MOSSY_CALCITE_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_CALCITE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_CALCITE_BRICK_STAIRS, TTBlocks.MOSSY_CALCITE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_CALCITE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_CALCITE_BRICK_SLAB, TTBlocks.MOSSY_CALCITE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_CALCITE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_CALCITE_BRICK_WALL, TTBlocks.MOSSY_CALCITE_BRICK_WALL);

	// TUFF
	public static final DeferredItem<BlockItem> CRACKED_TUFF_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_TUFF_BRICKS, TTBlocks.CRACKED_TUFF_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_TUFF_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_TUFF_BRICKS, TTBlocks.MOSSY_TUFF_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_TUFF_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_TUFF_BRICK_STAIRS, TTBlocks.MOSSY_TUFF_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_TUFF_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_TUFF_BRICK_SLAB, TTBlocks.MOSSY_TUFF_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_TUFF_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_TUFF_BRICK_WALL, TTBlocks.MOSSY_TUFF_BRICK_WALL);

	// BRICKS
	public static final DeferredItem<BlockItem> CRACKED_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_BRICKS, TTBlocks.CRACKED_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_BRICKS, TTBlocks.MOSSY_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_BRICK_STAIRS, TTBlocks.MOSSY_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_BRICK_SLAB, TTBlocks.MOSSY_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_BRICK_WALL, TTBlocks.MOSSY_BRICK_WALL);

	// RESIN
	public static final DeferredItem<BlockItem> POLISHED_RESIN_BLOCK = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_RESIN_BLOCK, TTBlocks.POLISHED_RESIN_BLOCK);
	public static final DeferredItem<BlockItem> POLISHED_RESIN_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_RESIN_STAIRS, TTBlocks.POLISHED_RESIN_STAIRS);
	public static final DeferredItem<BlockItem> POLISHED_RESIN_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_RESIN_SLAB, TTBlocks.POLISHED_RESIN_SLAB);
	public static final DeferredItem<BlockItem> POLISHED_RESIN_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.POLISHED_RESIN_WALL, TTBlocks.POLISHED_RESIN_WALL);
	public static final DeferredItem<BlockItem> CRACKED_RESIN_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_RESIN_BRICKS, TTBlocks.CRACKED_RESIN_BRICKS);

	public static final DeferredItem<BlockItem> PALE_MOSSY_RESIN_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.PALE_MOSSY_RESIN_BRICKS, TTBlocks.PALE_MOSSY_RESIN_BRICKS);
	public static final DeferredItem<BlockItem> PALE_MOSSY_RESIN_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_STAIRS, TTBlocks.PALE_MOSSY_RESIN_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> PALE_MOSSY_RESIN_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_SLAB, TTBlocks.PALE_MOSSY_RESIN_BRICK_SLAB);
	public static final DeferredItem<BlockItem> PALE_MOSSY_RESIN_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_WALL, TTBlocks.PALE_MOSSY_RESIN_BRICK_WALL);

	// MOSSY DEEPSLATE
	public static final DeferredItem<BlockItem> MOSSY_COBBLED_DEEPSLATE = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
	public static final DeferredItem<BlockItem> MOSSY_COBBLED_DEEPSLATE_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_STAIRS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_COBBLED_DEEPSLATE_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_SLAB, TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_COBBLED_DEEPSLATE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_WALL, TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL);

	public static final DeferredItem<BlockItem> MOSSY_DEEPSLATE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DEEPSLATE_BRICKS, TTBlocks.MOSSY_DEEPSLATE_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_DEEPSLATE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_STAIRS, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_DEEPSLATE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_SLAB, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_DEEPSLATE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_WALL, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL);

	public static final DeferredItem<BlockItem> MOSSY_DEEPSLATE_TILES = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DEEPSLATE_TILES, TTBlocks.MOSSY_DEEPSLATE_TILES);
	public static final DeferredItem<BlockItem> MOSSY_DEEPSLATE_TILE_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_STAIRS, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_DEEPSLATE_TILE_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_SLAB, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_DEEPSLATE_TILE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_WALL, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);

	// SANDSTONE
	public static final DeferredItem<BlockItem> SMOOTH_SANDSTONE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.SMOOTH_SANDSTONE_WALL, TTBlocks.SMOOTH_SANDSTONE_WALL);
	public static final DeferredItem<BlockItem> CUT_SANDSTONE_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CUT_SANDSTONE_STAIRS, TTBlocks.CUT_SANDSTONE_STAIRS);
	public static final DeferredItem<BlockItem> CUT_SANDSTONE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CUT_SANDSTONE_WALL, TTBlocks.CUT_SANDSTONE_WALL);

	// RED SANDSTONE
	public static final DeferredItem<BlockItem> SMOOTH_RED_SANDSTONE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.SMOOTH_RED_SANDSTONE_WALL, TTBlocks.SMOOTH_RED_SANDSTONE_WALL);
	public static final DeferredItem<BlockItem> CUT_RED_SANDSTONE_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CUT_RED_SANDSTONE_STAIRS, TTBlocks.CUT_RED_SANDSTONE_STAIRS);
	public static final DeferredItem<BlockItem> CUT_RED_SANDSTONE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CUT_RED_SANDSTONE_WALL, TTBlocks.CUT_RED_SANDSTONE_WALL);

	// PRISMARINE
	public static final DeferredItem<BlockItem> PRISMARINE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.PRISMARINE_BRICK_WALL, TTBlocks.PRISMARINE_BRICK_WALL);
	public static final DeferredItem<BlockItem> DARK_PRISMARINE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.DARK_PRISMARINE_WALL, TTBlocks.DARK_PRISMARINE_WALL);

	// END STONE
	public static final DeferredItem<BlockItem> END_STONE_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.END_STONE_STAIRS, TTBlocks.END_STONE_STAIRS);
	public static final DeferredItem<BlockItem> END_STONE_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.END_STONE_SLAB, TTBlocks.END_STONE_SLAB);
	public static final DeferredItem<BlockItem> END_STONE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.END_STONE_WALL, TTBlocks.END_STONE_WALL);

	public static final DeferredItem<BlockItem> CHORAL_END_STONE = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHORAL_END_STONE, TTBlocks.CHORAL_END_STONE);
	public static final DeferredItem<BlockItem> CHORAL_END_STONE_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHORAL_END_STONE_STAIRS, TTBlocks.CHORAL_END_STONE_STAIRS);
	public static final DeferredItem<BlockItem> CHORAL_END_STONE_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHORAL_END_STONE_SLAB, TTBlocks.CHORAL_END_STONE_SLAB);
	public static final DeferredItem<BlockItem> CHORAL_END_STONE_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHORAL_END_STONE_WALL, TTBlocks.CHORAL_END_STONE_WALL);

	public static final DeferredItem<BlockItem> CRACKED_END_STONE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_END_STONE_BRICKS, TTBlocks.CRACKED_END_STONE_BRICKS);
	public static final DeferredItem<BlockItem> CHISELED_END_STONE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHISELED_END_STONE_BRICKS, TTBlocks.CHISELED_END_STONE_BRICKS);
	public static final DeferredItem<BlockItem> CHORAL_END_STONE_BRICKS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHORAL_END_STONE_BRICKS, TTBlocks.CHORAL_END_STONE_BRICKS);
	public static final DeferredItem<BlockItem> CHORAL_END_STONE_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHORAL_END_STONE_BRICK_STAIRS, TTBlocks.CHORAL_END_STONE_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> CHORAL_END_STONE_BRICK_SLAB = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHORAL_END_STONE_BRICK_SLAB, TTBlocks.CHORAL_END_STONE_BRICK_SLAB);
	public static final DeferredItem<BlockItem> CHORAL_END_STONE_BRICK_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHORAL_END_STONE_BRICK_WALL, TTBlocks.CHORAL_END_STONE_BRICK_WALL);

	// PURPUR
	public static final DeferredItem<BlockItem> CRACKED_PURPUR_BLOCK = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CRACKED_PURPUR_BLOCK, TTBlocks.CRACKED_PURPUR_BLOCK);
	public static final DeferredItem<BlockItem> PURPUR_WALL = REGISTER.registerSimpleBlockItem(TTBlockItemIds.PURPUR_WALL, TTBlocks.PURPUR_WALL);
	public static final DeferredItem<BlockItem> CHISELED_PURPUR_BLOCK = REGISTER.registerSimpleBlockItem(TTBlockItemIds.CHISELED_PURPUR_BLOCK, TTBlocks.CHISELED_PURPUR_BLOCK);

	// CATACOMBS
	public static final DeferredItem<CoffinItem> COFFIN = REGISTER.registerBlockItem(TTBlockItemIds.COFFIN, (properties, block) -> new CoffinItem(block, properties), TTBlocks.COFFIN);
	public static final DeferredItem<BlockItem> SURVEYOR = REGISTER.registerSimpleBlockItem(TTBlockItemIds.SURVEYOR, TTBlocks.SURVEYOR);
	public static final DeferredItem<PlaceInAirBlockItem> ECTOPLASM_BLOCK = REGISTER.registerBlockItem(TTBlockItemIds.ECTOPLASM_BLOCK, (properties, block) -> new PlaceInAirBlockItem(block, properties), TTBlocks.ECTOPLASM_BLOCK);

	// ITEMS
	// SPAWN EGGS
	public static final DeferredItem<SpawnEggItem> APPARITION_SPAWN_EGG = REGISTER.registerSpawnEgg(TTItemIds.APPARITION_SPAWN_EGG, TTEntityTypes.APPARITION);

	// CATACOMBS
	public static final DeferredItem<Item> ECTOPLASM = REGISTER.registerSimpleItem(TTItemIds.ECTOPLASM);

	// POTTERY SHERDS
	/**
	 * Don't forget to go to {@link TTDecoratedPotPatterns} to register patterns!
	 */
	public static final DeferredItem<Item> AURORA_POTTERY_SHERD = registerPotterySherd(TTItemIds.AURORA_POTTERY_SHERD, TTDecoratedPotPatterns.AURORA);
	public static final DeferredItem<Item> BAIT_POTTERY_SHERD = registerPotterySherd(TTItemIds.BAIT_POTTERY_SHERD, TTDecoratedPotPatterns.BAIT);
	public static final DeferredItem<Item> BLOOM_POTTERY_SHERD = registerPotterySherd(TTItemIds.BLOOM_POTTERY_SHERD, TTDecoratedPotPatterns.BLOOM);
	public static final DeferredItem<Item> BOLT_POTTERY_SHERD = registerPotterySherd(TTItemIds.BOLT_POTTERY_SHERD, TTDecoratedPotPatterns.BOLT);
	public static final DeferredItem<Item> BULLSEYE_POTTERY_SHERD = registerPotterySherd(TTItemIds.BULLSEYE_POTTERY_SHERD, TTDecoratedPotPatterns.BULLSEYE);
	public static final DeferredItem<Item> CARRIER_POTTERY_SHERD = registerPotterySherd(TTItemIds.CARRIER_POTTERY_SHERD, TTDecoratedPotPatterns.CARRIER);
	public static final DeferredItem<Item> CLUCK_POTTERY_SHERD = registerPotterySherd(TTItemIds.CLUCK_POTTERY_SHERD, TTDecoratedPotPatterns.CLUCK);
	public static final DeferredItem<Item> CRAWL_POTTERY_SHERD = registerPotterySherd(TTItemIds.CRAWL_POTTERY_SHERD, TTDecoratedPotPatterns.CRAWL);
	public static final DeferredItem<Item> CRESCENT_POTTERY_SHERD = registerPotterySherd(TTItemIds.CRESCENT_POTTERY_SHERD, TTDecoratedPotPatterns.CRESCENT);
	public static final DeferredItem<Item> CULTIVATOR_POTTERY_SHERD = registerPotterySherd(TTItemIds.CULTIVATOR_POTTERY_SHERD, TTDecoratedPotPatterns.CULTIVATOR);
	public static final DeferredItem<Item> DROUGHT_POTTERY_SHERD = registerPotterySherd(TTItemIds.DROUGHT_POTTERY_SHERD, TTDecoratedPotPatterns.DROUGHT);
	public static final DeferredItem<Item> ENCLOSURE_POTTERY_SHERD = registerPotterySherd(TTItemIds.ENCLOSURE_POTTERY_SHERD, TTDecoratedPotPatterns.ENCLOSURE);
	public static final DeferredItem<Item> ESSENCE_POTTERY_SHERD = registerPotterySherd(TTItemIds.ESSENCE_POTTERY_SHERD, TTDecoratedPotPatterns.ESSENCE);
	public static final DeferredItem<Item> EYE_POTTERY_SHERD = registerPotterySherd(TTItemIds.EYE_POTTERY_SHERD, TTDecoratedPotPatterns.EYE);
	public static final DeferredItem<Item> FOCUS_POTTERY_SHERD = registerPotterySherd(TTItemIds.FOCUS_POTTERY_SHERD, TTDecoratedPotPatterns.FOCUS);
	public static final DeferredItem<Item> FROST_POTTERY_SHERD = registerPotterySherd(TTItemIds.FROST_POTTERY_SHERD, TTDecoratedPotPatterns.FROST);
	public static final DeferredItem<Item> HARE_POTTERY_SHERD = registerPotterySherd(TTItemIds.HARE_POTTERY_SHERD, TTDecoratedPotPatterns.HARE);
	public static final DeferredItem<Item> HEIGHT_POTTERY_SHERD = registerPotterySherd(TTItemIds.HEIGHT_POTTERY_SHERD, TTDecoratedPotPatterns.HEIGHT);
	public static final DeferredItem<Item> HUMP_POTTERY_SHERD = registerPotterySherd(TTItemIds.HUMP_POTTERY_SHERD, TTDecoratedPotPatterns.HUMP);
	public static final DeferredItem<Item> ILLUMINATOR_POTTERY_SHERD = registerPotterySherd(TTItemIds.ILLUMINATOR_POTTERY_SHERD, TTDecoratedPotPatterns.ILLUMINATOR);
	public static final DeferredItem<Item> INCIDENCE_POTTERY_SHERD = registerPotterySherd(TTItemIds.INCIDENCE_POTTERY_SHERD, TTDecoratedPotPatterns.INCIDENCE);
	public static final DeferredItem<Item> LUMBER_POTTERY_SHERD = registerPotterySherd(TTItemIds.LUMBER_POTTERY_SHERD, TTDecoratedPotPatterns.LUMBER);
	public static final DeferredItem<Item> NAVIGATOR_POTTERY_SHERD = registerPotterySherd(TTItemIds.NAVIGATOR_POTTERY_SHERD, TTDecoratedPotPatterns.NAVIGATOR);
	public static final DeferredItem<Item> NEEDLES_POTTERY_SHERD = registerPotterySherd(TTItemIds.NEEDLES_POTTERY_SHERD, TTDecoratedPotPatterns.NEEDLES);
	public static final DeferredItem<Item> OMEN_POTTERY_SHERD = registerPotterySherd(TTItemIds.OMEN_POTTERY_SHERD, TTDecoratedPotPatterns.OMEN);
	public static final DeferredItem<Item> PLUME_POTTERY_SHERD = registerPotterySherd(TTItemIds.PLUME_POTTERY_SHERD, TTDecoratedPotPatterns.PLUME);
	public static final DeferredItem<Item> PROTECTION_POTTERY_SHERD = registerPotterySherd(TTItemIds.PROTECTION_POTTERY_SHERD, TTDecoratedPotPatterns.PROTECTION);
	public static final DeferredItem<Item> SHED_POTTERY_SHERD = registerPotterySherd(TTItemIds.SHED_POTTERY_SHERD, TTDecoratedPotPatterns.SHED);
	public static final DeferredItem<Item> SHINE_POTTERY_SHERD = registerPotterySherd(TTItemIds.SHINE_POTTERY_SHERD, TTDecoratedPotPatterns.SHINE);
	public static final DeferredItem<Item> SHOWER_POTTERY_SHERD = registerPotterySherd(TTItemIds.SHOWER_POTTERY_SHERD, TTDecoratedPotPatterns.SHOWER);
	public static final DeferredItem<Item> SPADE_POTTERY_SHERD = registerPotterySherd(TTItemIds.SPADE_POTTERY_SHERD, TTDecoratedPotPatterns.SPADE);
	public static final DeferredItem<Item> SPROUT_POTTERY_SHERD = registerPotterySherd(TTItemIds.SPROUT_POTTERY_SHERD, TTDecoratedPotPatterns.SPROUT);
	public static final DeferredItem<Item> VESSEL_POTTERY_SHERD = registerPotterySherd(TTItemIds.VESSEL_POTTERY_SHERD, TTDecoratedPotPatterns.VESSEL);
	public static final DeferredItem<Item> WITHER_POTTERY_SHERD = registerPotterySherd(TTItemIds.WITHER_POTTERY_SHERD, TTDecoratedPotPatterns.WITHER);

	// SMITHING TEMPLATES
	public static final DeferredItem<SmithingTemplateItem> UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final DeferredItem<SmithingTemplateItem> MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final DeferredItem<SmithingTemplateItem> GEODE_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final DeferredItem<SmithingTemplateItem> OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final DeferredItem<SmithingTemplateItem> MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final DeferredItem<SmithingTemplateItem> ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final DeferredItem<SmithingTemplateItem> COT_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.COT_ARMOR_TRIM_SMITHING_TEMPLATE);
	public static final DeferredItem<SmithingTemplateItem> EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE = registerArmorTrimTemplate(TTItemIds.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE);

	// MUSIC DISCS
	public static final DeferredItem<Item> MUSIC_DISC_FAUSSE_VIE = REGISTER.registerMusicDisc(TTItemIds.MUSIC_DISC_FAUSSE_VIE, TTJukeboxSongs.FAUSSE_VIE);
	public static final DeferredItem<Item> MUSIC_DISC_STASIS = REGISTER.registerMusicDisc(TTItemIds.MUSIC_DISC_STASIS, TTJukeboxSongs.STASIS);
	public static final DeferredItem<Item> MUSIC_DISC_OSSUAIRE = REGISTER.registerMusicDisc(TTItemIds.MUSIC_DISC_OSSUAIRE, TTJukeboxSongs.OSSUAIRE);

	// MAPS
	public static final DeferredItem<Item> BURIED_CATACOMBS_MAP = REGISTER.registerItem(TTItemIds.BURIED_CATACOMBS_MAP, MapItem::new, Items::mapProperties);

	static {
		REGISTER.register();
	}

	public static void init() {}

	public static DeferredItem<Item> registerPotterySherd(ResourceKey<Item> key, ResourceKey<DecoratedPotPattern> potPattern) {
		return REGISTER.registerSimpleItem(key, () -> new Properties().rarity(Rarity.UNCOMMON).potPattern(potPattern));
	}

	public static DeferredItem<SmithingTemplateItem> registerArmorTrimTemplate(ResourceKey<Item> key) {
		return REGISTER.registerItem(key, SmithingTemplateItem::createArmorTrimTemplate, () -> new Properties().rarity(Rarity.UNCOMMON));
	}

	private TTItems() {}
}
