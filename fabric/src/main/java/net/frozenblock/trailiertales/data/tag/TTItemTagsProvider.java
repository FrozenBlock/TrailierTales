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

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.frozenblock.trailiertales.references.TTBlockItemIds;
import net.frozenblock.trailiertales.references.TTItemIds;
import net.frozenblock.trailiertales.tag.TTItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TTItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {

	public TTItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	private TagKey<Item> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		new TTBlockItemTagsProvider(tagId -> BlockItemTagsProvider.wrapForItems(this.tag(tagId.item()))).run();

		this.builder(ConventionalItemTags.MUSIC_DISCS)
			.add(TTItemIds.MUSIC_DISC_STASIS)
			.add(TTItemIds.MUSIC_DISC_FAUSSE_VIE)
			.add(TTItemIds.MUSIC_DISC_OSSUAIRE);

		this.builder(ItemTags.DECORATED_POT_SHERDS)
			.add(TTItemIds.AURORA_POTTERY_SHERD)
			.add(TTItemIds.BAIT_POTTERY_SHERD)
			.add(TTItemIds.BLOOM_POTTERY_SHERD)
			.add(TTItemIds.BOLT_POTTERY_SHERD)
			.add(TTItemIds.BULLSEYE_POTTERY_SHERD)
			.add(TTItemIds.CARRIER_POTTERY_SHERD)
			.add(TTItemIds.CLUCK_POTTERY_SHERD)
			.add(TTItemIds.CRAWL_POTTERY_SHERD)
			.add(TTItemIds.CRESCENT_POTTERY_SHERD)
			.add(TTItemIds.CULTIVATOR_POTTERY_SHERD)
			.add(TTItemIds.DROUGHT_POTTERY_SHERD)
			.add(TTItemIds.ENCLOSURE_POTTERY_SHERD)
			.add(TTItemIds.ESSENCE_POTTERY_SHERD)
			.add(TTItemIds.EYE_POTTERY_SHERD)
			.add(TTItemIds.FOCUS_POTTERY_SHERD)
			.add(TTItemIds.FROST_POTTERY_SHERD)
			.add(TTItemIds.HARE_POTTERY_SHERD)
			.add(TTItemIds.HEIGHT_POTTERY_SHERD)
			.add(TTItemIds.HUMP_POTTERY_SHERD)
			.add(TTItemIds.ILLUMINATOR_POTTERY_SHERD)
			.add(TTItemIds.INCIDENCE_POTTERY_SHERD)
			.add(TTItemIds.LUMBER_POTTERY_SHERD)
			.add(TTItemIds.NAVIGATOR_POTTERY_SHERD)
			.add(TTItemIds.NEEDLES_POTTERY_SHERD)
			.add(TTItemIds.OMEN_POTTERY_SHERD)
			.add(TTItemIds.PLUME_POTTERY_SHERD)
			.add(TTItemIds.PROTECTION_POTTERY_SHERD)
			.add(TTItemIds.SHED_POTTERY_SHERD)
			.add(TTItemIds.SHINE_POTTERY_SHERD)
			.add(TTItemIds.SHOWER_POTTERY_SHERD)
			.add(TTItemIds.SPADE_POTTERY_SHERD)
			.add(TTItemIds.SPROUT_POTTERY_SHERD)
			.add(TTItemIds.VESSEL_POTTERY_SHERD)
			.add(TTItemIds.WITHER_POTTERY_SHERD);

		this.builder(ItemTags.VILLAGER_PLANTABLE_SEEDS)
			.add(TTBlockItemIds.CYAN_ROSE_CROP)
			.add(TTBlockItemIds.MANEDROP_CROP)
			.add(TTBlockItemIds.GUZMANIA_CROP)
			.add(TTBlockItemIds.DAWNTRAIL_CROP)
			.add(TTBlockItemIds.LITHOPS_CROP);

		this.builder(ItemTags.CHICKEN_FOOD)
			.add(TTBlockItemIds.CYAN_ROSE_CROP)
			.add(TTBlockItemIds.GUZMANIA_CROP)
			.add(TTBlockItemIds.LITHOPS_CROP);

		this.builder(ItemTags.PARROT_FOOD)
			.add(TTBlockItemIds.CYAN_ROSE_CROP)
			.add(TTBlockItemIds.GUZMANIA_CROP)
			.add(TTBlockItemIds.LITHOPS_CROP);

		this.builder(ItemTags.SNIFFER_FOOD)
			.add(TTBlockItemIds.CYAN_ROSE_CROP)
			.add(TTBlockItemIds.MANEDROP_CROP)
			.add(TTBlockItemIds.GUZMANIA_CROP)
			.add(TTBlockItemIds.DAWNTRAIL_CROP)
			.add(TTBlockItemIds.LITHOPS_CROP);

		this.builder(TTItemTags.BRUSH_ENCHANTABLE)
			.add(ItemIds.BRUSH);

		this.builder(ItemTags.DURABILITY_ENCHANTABLE)
			.add(ItemIds.BRUSH);
	}
}
