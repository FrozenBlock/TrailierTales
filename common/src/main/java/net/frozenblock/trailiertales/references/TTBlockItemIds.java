/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.trailiertales.references;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

public final class TTBlockItemIds {
	// SUSPICIOUS BLOCKS
	public static final BlockItemId SUSPICIOUS_RED_SAND = create("suspicious_red_sand");
	public static final BlockItemId SUSPICIOUS_DIRT = create("suspicious_dirt");
	public static final BlockItemId SUSPICIOUS_CLAY = create("suspicious_clay");

	// PLANTS
	public static final BlockItemId CYAN_ROSE_CROP = create("cyan_rose_crop", "cyan_rose_seeds");
	public static final BlockItemId CYAN_ROSE = create("cyan_rose");

	public static final BlockItemId MANEDROP_CROP = create("manedrop_crop", "manedrop_germ");
	public static final BlockItemId MANEDROP = create("manedrop");

	public static final BlockItemId DAWNTRAIL_CROP = create("dawntrail_crop", "dawntrail_seeds");
	public static final BlockItemId DAWNTRAIL = create("dawntrail");

	public static final BlockItemId GUZMANIA_CROP = create("guzmania_crop", "guzmania_seeds");
	public static final BlockItemId GUZMANIA = create("guzmania");

	public static final BlockItemId LITHOPS_CROP = create("lithops_crop", "lithops_seeds");
	public static final BlockItemId LITHOPS = create("lithops");

	// STONE
	public static final BlockItemId STONE_WALL = create("stone_wall");

	// GRANITE
	public static final BlockItemId POLISHED_GRANITE_WALL = create("polished_granite_wall");

	public static final BlockItemId GRANITE_BRICKS = create("granite_bricks");
	public static final BlockItemId CHISELED_GRANITE_BRICKS = create("chiseled_granite_bricks");
	public static final BlockItemId GRANITE_BRICK_STAIRS = create("granite_brick_stairs");
	public static final BlockItemId GRANITE_BRICK_SLAB = create("granite_brick_slab");
	public static final BlockItemId GRANITE_BRICK_WALL = create("granite_brick_wall");
	public static final BlockItemId CRACKED_GRANITE_BRICKS = create("cracked_granite_bricks");

	public static final BlockItemId MOSSY_GRANITE_BRICKS = create("mossy_granite_bricks");
	public static final BlockItemId MOSSY_GRANITE_BRICK_STAIRS = create("mossy_granite_brick_stairs");
	public static final BlockItemId MOSSY_GRANITE_BRICK_SLAB = create("mossy_granite_brick_slab");
	public static final BlockItemId MOSSY_GRANITE_BRICK_WALL = create("mossy_granite_brick_wall");

	// DIORITE
	public static final BlockItemId POLISHED_DIORITE_WALL = create("polished_diorite_wall");

	public static final BlockItemId DIORITE_BRICKS = create("diorite_bricks");
	public static final BlockItemId DIORITE_BRICK_STAIRS = create("diorite_brick_stairs");
	public static final BlockItemId DIORITE_BRICK_SLAB = create("diorite_brick_slab");
	public static final BlockItemId DIORITE_BRICK_WALL = create("diorite_brick_wall");
	public static final BlockItemId CRACKED_DIORITE_BRICKS = create("cracked_diorite_bricks");
	public static final BlockItemId CHISELED_DIORITE_BRICKS = create("chiseled_diorite_bricks");

	public static final BlockItemId MOSSY_DIORITE_BRICKS = create("mossy_diorite_bricks");
	public static final BlockItemId MOSSY_DIORITE_BRICK_STAIRS = create("mossy_diorite_brick_stairs");
	public static final BlockItemId MOSSY_DIORITE_BRICK_SLAB = create("mossy_diorite_brick_slab");
	public static final BlockItemId MOSSY_DIORITE_BRICK_WALL = create("mossy_diorite_brick_wall");

	// ANDESITE
	public static final BlockItemId POLISHED_ANDESITE_WALL = create("polished_andesite_wall");

	public static final BlockItemId ANDESITE_BRICKS = create("andesite_bricks");
	public static final BlockItemId ANDESITE_BRICK_STAIRS = create("andesite_brick_stairs");
	public static final BlockItemId ANDESITE_BRICK_SLAB = create("andesite_brick_slab");
	public static final BlockItemId ANDESITE_BRICK_WALL = create("andesite_brick_wall");
	public static final BlockItemId CRACKED_ANDESITE_BRICKS = create("cracked_andesite_bricks");
	public static final BlockItemId CHISELED_ANDESITE_BRICKS = create("chiseled_andesite_bricks");

	public static final BlockItemId MOSSY_ANDESITE_BRICKS = create("mossy_andesite_bricks");
	public static final BlockItemId MOSSY_ANDESITE_BRICK_STAIRS = create("mossy_andesite_brick_stairs");
	public static final BlockItemId MOSSY_ANDESITE_BRICK_SLAB = create("mossy_andesite_brick_slab");
	public static final BlockItemId MOSSY_ANDESITE_BRICK_WALL = create("mossy_andesite_brick_wall");

	// CALCITE
	public static final BlockItemId CALCITE_STAIRS = create("calcite_stairs");
	public static final BlockItemId CALCITE_SLAB = create("calcite_slab");
	public static final BlockItemId CALCITE_WALL = create("calcite_wall");

	public static final BlockItemId POLISHED_CALCITE = create("polished_calcite");
	public static final BlockItemId POLISHED_CALCITE_STAIRS = create("polished_calcite_stairs");
	public static final BlockItemId POLISHED_CALCITE_SLAB = create("polished_calcite_slab");
	public static final BlockItemId POLISHED_CALCITE_WALL = create("polished_calcite_wall");

	public static final BlockItemId CALCITE_BRICKS = create("calcite_bricks");
	public static final BlockItemId CALCITE_BRICK_STAIRS = create("calcite_brick_stairs");
	public static final BlockItemId CALCITE_BRICK_SLAB = create("calcite_brick_slab");
	public static final BlockItemId CALCITE_BRICK_WALL = create("calcite_brick_wall");
	public static final BlockItemId CRACKED_CALCITE_BRICKS = create("cracked_calcite_bricks");
	public static final BlockItemId CHISELED_CALCITE_BRICKS = create("chiseled_calcite_bricks");

	public static final BlockItemId MOSSY_CALCITE_BRICKS = create("mossy_calcite_bricks");
	public static final BlockItemId MOSSY_CALCITE_BRICK_STAIRS = create("mossy_calcite_brick_stairs");
	public static final BlockItemId MOSSY_CALCITE_BRICK_SLAB = create("mossy_calcite_brick_slab");
	public static final BlockItemId MOSSY_CALCITE_BRICK_WALL = create("mossy_calcite_brick_wall");

	// TUFF
	public static final BlockItemId CRACKED_TUFF_BRICKS = create("cracked_tuff_bricks");
	public static final BlockItemId MOSSY_TUFF_BRICKS = create("mossy_tuff_bricks");
	public static final BlockItemId MOSSY_TUFF_BRICK_STAIRS = create("mossy_tuff_brick_stairs");
	public static final BlockItemId MOSSY_TUFF_BRICK_SLAB = create("mossy_tuff_brick_slab");
	public static final BlockItemId MOSSY_TUFF_BRICK_WALL = create("mossy_tuff_brick_wall");

	// BRICKS
	public static final BlockItemId CRACKED_BRICKS = create("cracked_bricks");
	public static final BlockItemId MOSSY_BRICKS = create("mossy_bricks");
	public static final BlockItemId MOSSY_BRICK_STAIRS = create("mossy_brick_stairs");
	public static final BlockItemId MOSSY_BRICK_SLAB = create("mossy_brick_slab");
	public static final BlockItemId MOSSY_BRICK_WALL = create("mossy_brick_wall");

	// RESIN
	public static final BlockItemId POLISHED_RESIN_BLOCK = create("polished_resin_block");
	public static final BlockItemId POLISHED_RESIN_STAIRS = create("polished_resin_stairs");
	public static final BlockItemId POLISHED_RESIN_SLAB = create("polished_resin_slab");
	public static final BlockItemId POLISHED_RESIN_WALL = create("polished_resin_wall");

	public static final BlockItemId CRACKED_RESIN_BRICKS = create("cracked_resin_bricks");

	public static final BlockItemId PALE_MOSSY_RESIN_BRICKS = create("pale_mossy_resin_bricks");
	public static final BlockItemId PALE_MOSSY_RESIN_BRICK_STAIRS = create("pale_mossy_resin_brick_stairs");
	public static final BlockItemId PALE_MOSSY_RESIN_BRICK_SLAB = create("pale_mossy_resin_brick_slab");
	public static final BlockItemId PALE_MOSSY_RESIN_BRICK_WALL = create("pale_mossy_resin_brick_wall");

	// MOSSY DEEPSLATE
	public static final BlockItemId MOSSY_COBBLED_DEEPSLATE = create("mossy_cobbled_deepslate");
	public static final BlockItemId MOSSY_COBBLED_DEEPSLATE_STAIRS = create("mossy_cobbled_deepslate_stairs");
	public static final BlockItemId MOSSY_COBBLED_DEEPSLATE_SLAB = create("mossy_cobbled_deepslate_slab");
	public static final BlockItemId MOSSY_COBBLED_DEEPSLATE_WALL = create("mossy_cobbled_deepslate_wall");

	public static final BlockItemId MOSSY_DEEPSLATE_BRICKS = create("mossy_deepslate_bricks");
	public static final BlockItemId MOSSY_DEEPSLATE_BRICK_STAIRS = create("mossy_deepslate_brick_stairs");
	public static final BlockItemId MOSSY_DEEPSLATE_BRICK_SLAB = create("mossy_deepslate_brick_slab");
	public static final BlockItemId MOSSY_DEEPSLATE_BRICK_WALL = create("mossy_deepslate_brick_wall");

	public static final BlockItemId MOSSY_DEEPSLATE_TILES = create("mossy_deepslate_tiles");
	public static final BlockItemId MOSSY_DEEPSLATE_TILE_STAIRS = create("mossy_deepslate_tile_stairs");
	public static final BlockItemId MOSSY_DEEPSLATE_TILE_SLAB = create("mossy_deepslate_tile_slab");
	public static final BlockItemId MOSSY_DEEPSLATE_TILE_WALL = create("mossy_deepslate_tile_wall");

	// SANDSTONE
	public static final BlockItemId SMOOTH_SANDSTONE_WALL = create("smooth_sandstone_wall");
	public static final BlockItemId CUT_SANDSTONE_STAIRS = create("cut_sandstone_stairs");
	public static final BlockItemId CUT_SANDSTONE_WALL = create("cut_sandstone_wall");

	// RED SANDSTONE
	public static final BlockItemId SMOOTH_RED_SANDSTONE_WALL = create("smooth_red_sandstone_wall");
	public static final BlockItemId CUT_RED_SANDSTONE_STAIRS = create("cut_red_sandstone_stairs");
	public static final BlockItemId CUT_RED_SANDSTONE_WALL = create("cut_red_sandstone_wall");

	// PRISMARINE
	public static final BlockItemId PRISMARINE_BRICK_WALL = create("prismarine_brick_wall");
	public static final BlockItemId DARK_PRISMARINE_WALL = create("dark_prismarine_wall");

	// END STONE
	public static final BlockItemId END_STONE_STAIRS = create("end_stone_stairs");
	public static final BlockItemId END_STONE_SLAB = create("end_stone_slab");
	public static final BlockItemId END_STONE_WALL = create("end_stone_wall");

	public static final BlockItemId CHORAL_END_STONE = create("choral_end_stone");
	public static final BlockItemId CHORAL_END_STONE_STAIRS = create("choral_end_stone_stairs");
	public static final BlockItemId CHORAL_END_STONE_SLAB = create("choral_end_stone_slab");
	public static final BlockItemId CHORAL_END_STONE_WALL = create("choral_end_stone_wall"	);

	public static final BlockItemId CRACKED_END_STONE_BRICKS = create("cracked_end_stone_bricks");
	public static final BlockItemId CHISELED_END_STONE_BRICKS = create("chiseled_end_stone_bricks");
	public static final BlockItemId CHORAL_END_STONE_BRICKS = create("choral_end_stone_bricks");
	public static final BlockItemId CHORAL_END_STONE_BRICK_STAIRS = create("choral_end_stone_brick_stairs");
	public static final BlockItemId CHORAL_END_STONE_BRICK_SLAB = create("choral_end_stone_brick_slab");
	public static final BlockItemId CHORAL_END_STONE_BRICK_WALL = create("choral_end_stone_brick_wall");

	// PURPUR
	public static final BlockItemId CRACKED_PURPUR_BLOCK = create("cracked_purpur_block");
	public static final BlockItemId PURPUR_WALL = create("purpur_wall");
	public static final BlockItemId CHISELED_PURPUR_BLOCK = create("chiseled_purpur_block");

	// CATACOMBS
	public static final BlockItemId COFFIN = create("coffin");
	public static final BlockItemId SURVEYOR = create("surveyor");
	public static final BlockItemId ECTOPLASM_BLOCK = create("ectoplasm_block");

	private static BlockItemId create(String name) {
		final Identifier id = TTConstants.id(name);
		return BlockItemId.create(id, id);
	}

	private static BlockItemId create(String blockName, String itemName) {
		return BlockItemId.create(TTConstants.id(blockName), TTConstants.id(itemName));
	}
}
