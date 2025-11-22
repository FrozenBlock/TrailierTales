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

package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.tag.TTItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class TTItemTagProvider extends FabricTagProvider.ItemTagProvider {

	public TTItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	private TagKey<Item> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.valueLookupBuilder(ConventionalItemTags.MUSIC_DISCS)
			.add(TTItems.MUSIC_DISC_STASIS)
			.add(TTItems.MUSIC_DISC_FAUSSE_VIE)
			.add(TTItems.MUSIC_DISC_OSSUAIRE);

		this.valueLookupBuilder(ItemTags.SAND)
			.add(TTBlocks.SUSPICIOUS_RED_SAND.asItem());

		this.valueLookupBuilder(ItemTags.DECORATED_POT_SHERDS)
			.add(TTItems.AURORA_POTTERY_SHERD)
			.add(TTItems.BAIT_POTTERY_SHERD)
			.add(TTItems.BLOOM_POTTERY_SHERD)
			.add(TTItems.BOLT_POTTERY_SHERD)
			.add(TTItems.BULLSEYE_POTTERY_SHERD)
			.add(TTItems.CARRIER_POTTERY_SHERD)
			.add(TTItems.CLUCK_POTTERY_SHERD)
			.add(TTItems.CRAWL_POTTERY_SHERD)
			.add(TTItems.CRESCENT_POTTERY_SHERD)
			.add(TTItems.CULTIVATOR_POTTERY_SHERD)
			.add(TTItems.DROUGHT_POTTERY_SHERD)
			.add(TTItems.ENCLOSURE_POTTERY_SHERD)
			.add(TTItems.ESSENCE_POTTERY_SHERD)
			.add(TTItems.EYE_POTTERY_SHERD)
			.add(TTItems.FOCUS_POTTERY_SHERD)
			.add(TTItems.FROST_POTTERY_SHERD)
			.add(TTItems.HARE_POTTERY_SHERD)
			.add(TTItems.HEIGHT_POTTERY_SHERD)
			.add(TTItems.HUMP_POTTERY_SHERD)
			.add(TTItems.ILLUMINATOR_POTTERY_SHERD)
			.add(TTItems.INCIDENCE_POTTERY_SHERD)
			.add(TTItems.LUMBER_POTTERY_SHERD)
			.add(TTItems.NAVIGATOR_POTTERY_SHERD)
			.add(TTItems.NEEDLES_POTTERY_SHERD)
			.add(TTItems.OMEN_POTTERY_SHERD)
			.add(TTItems.PLUME_POTTERY_SHERD)
			.add(TTItems.PROTECTION_POTTERY_SHERD)
			.add(TTItems.SHED_POTTERY_SHERD)
			.add(TTItems.SHINE_POTTERY_SHERD)
			.add(TTItems.SHOWER_POTTERY_SHERD)
			.add(TTItems.SPADE_POTTERY_SHERD)
			.add(TTItems.SPROUT_POTTERY_SHERD)
			.add(TTItems.VESSEL_POTTERY_SHERD)
			.add(TTItems.WITHER_POTTERY_SHERD);

		this.valueLookupBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS)
			.add(TTItems.CYAN_ROSE_SEEDS)
			.add(TTItems.MANEDROP_GERM)
			.add(TTItems.DAWNTRAIL_SEEDS);

		this.valueLookupBuilder(ItemTags.CHICKEN_FOOD)
			.add(TTItems.CYAN_ROSE_SEEDS);

		this.valueLookupBuilder(ItemTags.PARROT_FOOD)
			.add(TTItems.CYAN_ROSE_SEEDS);

		this.valueLookupBuilder(ItemTags.SNIFFER_FOOD)
			.add(TTItems.CYAN_ROSE_SEEDS)
			.add(TTItems.MANEDROP_GERM)
			.add(TTItems.DAWNTRAIL_SEEDS);

		this.valueLookupBuilder(ItemTags.SMALL_FLOWERS)
			.add(TTBlocks.CYAN_ROSE.asItem())
			.add(TTBlocks.DAWNTRAIL.asItem());

		this.valueLookupBuilder(ItemTags.BEE_FOOD)
			.add(TTBlocks.MANEDROP.asItem())
			.add(TTBlocks.CYAN_ROSE.asItem());

		this.valueLookupBuilder(ItemTags.STAIRS)
			.add(TTBlocks.GRANITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.DIORITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.ANDESITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.CALCITE_STAIRS.asItem())
			.add(TTBlocks.POLISHED_CALCITE_STAIRS.asItem())
			.add(TTBlocks.CALCITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_TUFF_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_BRICK_STAIRS.asItem())
			.add(TTBlocks.PALE_MOSSY_RESIN_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.asItem())
			.add(TTBlocks.CUT_SANDSTONE_STAIRS.asItem())
			.add(TTBlocks.CUT_RED_SANDSTONE_STAIRS.asItem())
			.add(TTBlocks.CHORAL_END_STONE_STAIRS.asItem())
			.add(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS.asItem())
			.add(TTBlocks.END_STONE_STAIRS.asItem());

		this.valueLookupBuilder(ItemTags.SLABS)
			.add(TTBlocks.GRANITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_GRANITE_BRICK_SLAB.asItem())
			.add(TTBlocks.DIORITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_DIORITE_BRICK_SLAB.asItem())
			.add(TTBlocks.ANDESITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB.asItem())
			.add(TTBlocks.CALCITE_SLAB.asItem())
			.add(TTBlocks.POLISHED_CALCITE_SLAB.asItem())
			.add(TTBlocks.CALCITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_CALCITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_TUFF_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_BRICK_SLAB.asItem())
			.add(TTBlocks.PALE_MOSSY_RESIN_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB.asItem())
			.add(TTBlocks.CHORAL_END_STONE_SLAB.asItem())
			.add(TTBlocks.CHORAL_END_STONE_BRICK_SLAB.asItem())
			.add(TTBlocks.END_STONE_SLAB.asItem());

		this.valueLookupBuilder(ItemTags.WALLS)
			.add(TTBlocks.GRANITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_GRANITE_BRICK_WALL.asItem())
			.add(TTBlocks.DIORITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_DIORITE_BRICK_WALL.asItem())
			.add(TTBlocks.ANDESITE_BRICK_WALL.asItem())
			.add(TTBlocks.CALCITE_WALL.asItem())
			.add(TTBlocks.POLISHED_CALCITE_WALL.asItem())
			.add(TTBlocks.CALCITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_CALCITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_TUFF_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_BRICK_WALL.asItem())
			.add(TTBlocks.PALE_MOSSY_RESIN_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL.asItem())
			.add(TTBlocks.CHORAL_END_STONE_BRICK_WALL.asItem())
			.add(TTBlocks.SMOOTH_SANDSTONE_WALL.asItem())
			.add(TTBlocks.CUT_SANDSTONE_WALL.asItem())
			.add(TTBlocks.SMOOTH_RED_SANDSTONE_WALL.asItem())
			.add(TTBlocks.CUT_RED_SANDSTONE_WALL.asItem())
			.add(TTBlocks.PRISMARINE_BRICK_WALL.asItem())
			.add(TTBlocks.DARK_PRISMARINE_WALL.asItem())
			.add(TTBlocks.CHORAL_END_STONE_WALL.asItem())
			.add(TTBlocks.END_STONE_WALL.asItem())
			.add(TTBlocks.PURPUR_WALL.asItem());

		this.valueLookupBuilder(TTItemTags.BRUSH_ENCHANTABLE)
			.add(Items.BRUSH);

		this.valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE)
			.add(Items.BRUSH);
	}
}
