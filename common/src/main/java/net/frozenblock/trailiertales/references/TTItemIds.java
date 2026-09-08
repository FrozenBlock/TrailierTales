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
import net.frozenblock.trailiertales.registry.TTDecoratedPotPatterns;
import net.frozenblock.trailiertales.registry.TTJukeboxSongs;
import net.frozenblock.trailiertales.registry.TTTrimPatterns;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public final class TTItemIds {
	// SPAWN EGGS
	public static final ResourceKey<Item> APPARITION_SPAWN_EGG = ItemIds.createSpawnEgg(TTEntityTypeIds.APPARITION);
	// CATACOMBS
	public static final ResourceKey<Item> ECTOPLASM = create("ectoplasm");
	// POTTERY SHERDS
	public static final ResourceKey<Item> AURORA_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.AURORA);
	public static final ResourceKey<Item> BAIT_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.BAIT);
	public static final ResourceKey<Item> BLOOM_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.BLOOM);
	public static final ResourceKey<Item> BOLT_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.BOLT);
	public static final ResourceKey<Item> BULLSEYE_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.BULLSEYE);
	public static final ResourceKey<Item> CARRIER_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.CARRIER);
	public static final ResourceKey<Item> CLUCK_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.CLUCK);
	public static final ResourceKey<Item> CRAWL_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.CRAWL);
	public static final ResourceKey<Item> CRESCENT_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.CRESCENT);
	public static final ResourceKey<Item> CULTIVATOR_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.CULTIVATOR);
	public static final ResourceKey<Item> DROUGHT_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.DROUGHT);
	public static final ResourceKey<Item> ENCLOSURE_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.ENCLOSURE);
	public static final ResourceKey<Item> ESSENCE_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.ESSENCE);
	public static final ResourceKey<Item> EYE_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.EYE);
	public static final ResourceKey<Item> FOCUS_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.FOCUS);
	public static final ResourceKey<Item> FROST_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.FROST);
	public static final ResourceKey<Item> HARE_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.HARE);
	public static final ResourceKey<Item> HEIGHT_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.HEIGHT);
	public static final ResourceKey<Item> HUMP_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.HUMP);
	public static final ResourceKey<Item> ILLUMINATOR_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.ILLUMINATOR);
	public static final ResourceKey<Item> INCIDENCE_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.INCIDENCE);
	public static final ResourceKey<Item> LUMBER_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.LUMBER);
	public static final ResourceKey<Item> NAVIGATOR_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.NAVIGATOR);
	public static final ResourceKey<Item> NEEDLES_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.NEEDLES);
	public static final ResourceKey<Item> OMEN_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.OMEN);
	public static final ResourceKey<Item> PLUME_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.PLUME);
	public static final ResourceKey<Item> PROTECTION_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.PROTECTION);
	public static final ResourceKey<Item> SHED_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.SHED);
	public static final ResourceKey<Item> SHINE_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.SHINE);
	public static final ResourceKey<Item> SHOWER_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.SHOWER);
	public static final ResourceKey<Item> SPADE_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.SPADE);
	public static final ResourceKey<Item> SPROUT_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.SPROUT);
	public static final ResourceKey<Item> VESSEL_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.VESSEL);
	public static final ResourceKey<Item> WITHER_POTTERY_SHERD = ItemIds.createPotterySherd(TTDecoratedPotPatterns.WITHER);
	// SMITHING TEMPLATES
	public static final ResourceKey<Item> UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE = ItemIds.createArmorTrimSmithingTemplate(TTTrimPatterns.UNDEAD);
	public static final ResourceKey<Item> MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE = ItemIds.createArmorTrimSmithingTemplate(TTTrimPatterns.MATRIX);
	public static final ResourceKey<Item> GEODE_ARMOR_TRIM_SMITHING_TEMPLATE = ItemIds.createArmorTrimSmithingTemplate(TTTrimPatterns.GEODE);
	public static final ResourceKey<Item> OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE = ItemIds.createArmorTrimSmithingTemplate(TTTrimPatterns.OVERGROWTH);
	public static final ResourceKey<Item> MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE = ItemIds.createArmorTrimSmithingTemplate(TTTrimPatterns.MARTYR);
	public static final ResourceKey<Item> ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE = ItemIds.createArmorTrimSmithingTemplate(TTTrimPatterns.ZEPHYR);
	public static final ResourceKey<Item> COT_ARMOR_TRIM_SMITHING_TEMPLATE = ItemIds.createArmorTrimSmithingTemplate(TTTrimPatterns.COT);
	public static final ResourceKey<Item> EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE = ItemIds.createArmorTrimSmithingTemplate(TTTrimPatterns.EMBRACE);
	// MUSIC DISCS
	public static final ResourceKey<Item> MUSIC_DISC_FAUSSE_VIE = ItemIds.createMusicDisc(TTJukeboxSongs.FAUSSE_VIE);
	public static final ResourceKey<Item> MUSIC_DISC_STASIS = ItemIds.createMusicDisc(TTJukeboxSongs.STASIS);
	public static final ResourceKey<Item> MUSIC_DISC_OSSUAIRE = ItemIds.createMusicDisc(TTJukeboxSongs.OSSUAIRE);
	// MAPS
	public static final ResourceKey<Item> BURIED_CATACOMBS_MAP = create("buried_catacombs_map");

	private static ResourceKey<Item> create(String name) {
		return ResourceKey.create(Registries.ITEM, TTConstants.id(name));
	}

	private TTItemIds() {}
}
