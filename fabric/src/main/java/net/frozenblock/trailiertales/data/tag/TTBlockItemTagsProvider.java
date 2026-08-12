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

package net.frozenblock.trailiertales.data.tag;

import java.util.function.Function;
import net.frozenblock.trailiertales.references.TTBlockItemIds;
import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.tags.BlockItemTags;

public final class TTBlockItemTagsProvider extends BlockItemTagsProvider {

	TTBlockItemTagsProvider(Function<BlockItemTagId, CombinedAppender> tagSupplier) {
		super(tagSupplier);
	}

	@Override
	protected void run() {
		this.tag(BlockItemTags.SAND)
			.add(TTBlockItemIds.SUSPICIOUS_RED_SAND);

		this.tag(BlockItemTags.DIRT)
			.add(TTBlockItemIds.SUSPICIOUS_DIRT);

		this.tag(BlockItemTags.SMALL_FLOWERS)
			.add(TTBlockItemIds.CYAN_ROSE);

		this.tag(BlockItemTags.BEE_FOOD)
			.add(TTBlockItemIds.CYAN_ROSE)
			.add(TTBlockItemIds.MANEDROP)
			.add(TTBlockItemIds.GUZMANIA);

		this.tag(BlockItemTags.FLOWERS)
			.add(TTBlockItemIds.DAWNTRAIL);

		this.tag(BlockItemTags.STAIRS)
			.add(TTBlockItemIds.GRANITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_GRANITE_BRICK_STAIRS)
			.add(TTBlockItemIds.DIORITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_DIORITE_BRICK_STAIRS)
			.add(TTBlockItemIds.ANDESITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_ANDESITE_BRICK_STAIRS)
			.add(TTBlockItemIds.CALCITE_STAIRS)
			.add(TTBlockItemIds.POLISHED_CALCITE_STAIRS)
			.add(TTBlockItemIds.CALCITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_CALCITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_TUFF_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_BRICK_STAIRS)
			.add(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlockItemIds.POLISHED_RESIN_STAIRS)
			.add(TTBlockItemIds.CUT_SANDSTONE_STAIRS)
			.add(TTBlockItemIds.CUT_RED_SANDSTONE_STAIRS)
			.add(TTBlockItemIds.CHORAL_END_STONE_STAIRS)
			.add(TTBlockItemIds.CHORAL_END_STONE_BRICK_STAIRS)
			.add(TTBlockItemIds.END_STONE_STAIRS);

		this.tag(BlockItemTags.SLABS)
			.add(TTBlockItemIds.GRANITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_GRANITE_BRICK_SLAB)
			.add(TTBlockItemIds.DIORITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_DIORITE_BRICK_SLAB)
			.add(TTBlockItemIds.ANDESITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_ANDESITE_BRICK_SLAB)
			.add(TTBlockItemIds.CALCITE_SLAB)
			.add(TTBlockItemIds.POLISHED_CALCITE_SLAB)
			.add(TTBlockItemIds.CALCITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_CALCITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_TUFF_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_BRICK_SLAB)
			.add(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlockItemIds.POLISHED_RESIN_SLAB)
			.add(TTBlockItemIds.CHORAL_END_STONE_SLAB)
			.add(TTBlockItemIds.CHORAL_END_STONE_BRICK_SLAB)
			.add(TTBlockItemIds.END_STONE_SLAB);

		this.tag(BlockItemTags.WALLS)
			.add(TTBlockItemIds.STONE_WALL)
			.add(TTBlockItemIds.GRANITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_GRANITE_BRICK_WALL)
			.add(TTBlockItemIds.POLISHED_GRANITE_WALL)
			.add(TTBlockItemIds.DIORITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_DIORITE_BRICK_WALL)
			.add(TTBlockItemIds.POLISHED_DIORITE_WALL)
			.add(TTBlockItemIds.ANDESITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_ANDESITE_BRICK_WALL)
			.add(TTBlockItemIds.POLISHED_ANDESITE_WALL)
			.add(TTBlockItemIds.CALCITE_WALL)
			.add(TTBlockItemIds.POLISHED_CALCITE_WALL)
			.add(TTBlockItemIds.CALCITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_CALCITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_TUFF_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_BRICK_WALL)
			.add(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_WALL)
			.add(TTBlockItemIds.POLISHED_RESIN_WALL)
			.add(TTBlockItemIds.SMOOTH_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_SANDSTONE_WALL)
			.add(TTBlockItemIds.SMOOTH_RED_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_RED_SANDSTONE_WALL)
			.add(TTBlockItemIds.PRISMARINE_BRICK_WALL)
			.add(TTBlockItemIds.DARK_PRISMARINE_WALL)
			.add(TTBlockItemIds.CHORAL_END_STONE_WALL)
			.add(TTBlockItemIds.CHORAL_END_STONE_BRICK_WALL)
			.add(TTBlockItemIds.END_STONE_WALL)
			.add(TTBlockItemIds.PURPUR_WALL);
	}
}
