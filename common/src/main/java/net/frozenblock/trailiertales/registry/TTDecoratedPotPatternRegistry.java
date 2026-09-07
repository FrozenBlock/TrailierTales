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

import java.util.function.BiConsumer;
import net.frozenblock.lib.item.impl.sherd.DecoratedPotPatternRegistryEntrypoint;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.references.TTItemIds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

public final class TTDecoratedPotPatternRegistry implements DecoratedPotPatternRegistryEntrypoint {
	public static final ResourceKey<DecoratedPotPattern> AURORA = createKey("aurora");
	public static final ResourceKey<DecoratedPotPattern> BAIT = createKey("bait");
	public static final ResourceKey<DecoratedPotPattern> BLOOM = createKey("bloom");
	public static final ResourceKey<DecoratedPotPattern> BOLT = createKey("bolt");
	public static final ResourceKey<DecoratedPotPattern> BULLSEYE = createKey("bullseye");
	public static final ResourceKey<DecoratedPotPattern> CARRIER = createKey("carrier");
	public static final ResourceKey<DecoratedPotPattern> CLUCK = createKey("cluck");
	public static final ResourceKey<DecoratedPotPattern> CRAWL = createKey("crawl");
	public static final ResourceKey<DecoratedPotPattern> CRESCENT = createKey("crescent");
	public static final ResourceKey<DecoratedPotPattern> CULTIVATOR = createKey("cultivator");
	public static final ResourceKey<DecoratedPotPattern> DROUGHT = createKey("drought");
	public static final ResourceKey<DecoratedPotPattern> ENCLOSURE = createKey("enclosure");
	public static final ResourceKey<DecoratedPotPattern> ESSENCE = createKey("essence");
	public static final ResourceKey<DecoratedPotPattern> EYE = createKey("eye");
	public static final ResourceKey<DecoratedPotPattern> FOCUS = createKey("focus");
	public static final ResourceKey<DecoratedPotPattern> FROST = createKey("frost");
	public static final ResourceKey<DecoratedPotPattern> HARE = createKey("hare");
	public static final ResourceKey<DecoratedPotPattern> HEIGHT = createKey("height");
	public static final ResourceKey<DecoratedPotPattern> HUMP = createKey("hump");
	public static final ResourceKey<DecoratedPotPattern> ILLUMINATOR = createKey("illuminator");
	public static final ResourceKey<DecoratedPotPattern> INCIDENCE = createKey("incidence");
	public static final ResourceKey<DecoratedPotPattern> LUMBER = createKey("lumber");
	public static final ResourceKey<DecoratedPotPattern> NAVIGATOR = createKey("navigator");
	public static final ResourceKey<DecoratedPotPattern> NEEDLES = createKey("needles");
	public static final ResourceKey<DecoratedPotPattern> OMEN = createKey("omen");
	public static final ResourceKey<DecoratedPotPattern> PLUME = createKey("plume");
	public static final ResourceKey<DecoratedPotPattern> PROTECTION = createKey("protection");
	public static final ResourceKey<DecoratedPotPattern> SHED = createKey("shed");
	public static final ResourceKey<DecoratedPotPattern> SHINE = createKey("shine");
	public static final ResourceKey<DecoratedPotPattern> SHOWER = createKey("shower");
	public static final ResourceKey<DecoratedPotPattern> SPADE = createKey("spade");
	public static final ResourceKey<DecoratedPotPattern> SPROUT = createKey("sprout");
	public static final ResourceKey<DecoratedPotPattern> VESSEL = createKey("vessel");
	public static final ResourceKey<DecoratedPotPattern> WITHER = createKey("wither");

	@Override
	public void itemToPatternMappings(BiConsumer<ResourceKey<Item>, ResourceKey<DecoratedPotPattern>> itemToPattern) {
		itemToPattern.accept(TTItemIds.AURORA_POTTERY_SHERD, AURORA);
		itemToPattern.accept(TTItemIds.BAIT_POTTERY_SHERD, BAIT);
		itemToPattern.accept(TTItemIds.BLOOM_POTTERY_SHERD, BLOOM);
		itemToPattern.accept(TTItemIds.BOLT_POTTERY_SHERD, BOLT);
		itemToPattern.accept(TTItemIds.BULLSEYE_POTTERY_SHERD, BULLSEYE);
		itemToPattern.accept(TTItemIds.CARRIER_POTTERY_SHERD, CARRIER);
		itemToPattern.accept(TTItemIds.CLUCK_POTTERY_SHERD, CLUCK);
		itemToPattern.accept(TTItemIds.CRAWL_POTTERY_SHERD, CRAWL);
		itemToPattern.accept(TTItemIds.CRESCENT_POTTERY_SHERD, CRESCENT);
		itemToPattern.accept(TTItemIds.CULTIVATOR_POTTERY_SHERD, CULTIVATOR);
		itemToPattern.accept(TTItemIds.DROUGHT_POTTERY_SHERD, DROUGHT);
		itemToPattern.accept(TTItemIds.ENCLOSURE_POTTERY_SHERD, ENCLOSURE);
		itemToPattern.accept(TTItemIds.ESSENCE_POTTERY_SHERD, ESSENCE);
		itemToPattern.accept(TTItemIds.EYE_POTTERY_SHERD, EYE);
		itemToPattern.accept(TTItemIds.FOCUS_POTTERY_SHERD, FOCUS);
		itemToPattern.accept(TTItemIds.FROST_POTTERY_SHERD, FROST);
		itemToPattern.accept(TTItemIds.HARE_POTTERY_SHERD, HARE);
		itemToPattern.accept(TTItemIds.HEIGHT_POTTERY_SHERD, HEIGHT);
		itemToPattern.accept(TTItemIds.HUMP_POTTERY_SHERD, HUMP);
		itemToPattern.accept(TTItemIds.ILLUMINATOR_POTTERY_SHERD, ILLUMINATOR);
		itemToPattern.accept(TTItemIds.INCIDENCE_POTTERY_SHERD, INCIDENCE);
		itemToPattern.accept(TTItemIds.LUMBER_POTTERY_SHERD, LUMBER);
		itemToPattern.accept(TTItemIds.NAVIGATOR_POTTERY_SHERD, NAVIGATOR);
		itemToPattern.accept(TTItemIds.NEEDLES_POTTERY_SHERD, NEEDLES);
		itemToPattern.accept(TTItemIds.OMEN_POTTERY_SHERD, OMEN);
		itemToPattern.accept(TTItemIds.PLUME_POTTERY_SHERD, PLUME);
		itemToPattern.accept(TTItemIds.PROTECTION_POTTERY_SHERD, PROTECTION);
		itemToPattern.accept(TTItemIds.SHED_POTTERY_SHERD, SHED);
		itemToPattern.accept(TTItemIds.SHINE_POTTERY_SHERD, SHINE);
		itemToPattern.accept(TTItemIds.SHOWER_POTTERY_SHERD, SHOWER);
		itemToPattern.accept(TTItemIds.SPADE_POTTERY_SHERD, SPADE);
		itemToPattern.accept(TTItemIds.SPROUT_POTTERY_SHERD, SPROUT);
		itemToPattern.accept(TTItemIds.VESSEL_POTTERY_SHERD, VESSEL);
		itemToPattern.accept(TTItemIds.WITHER_POTTERY_SHERD, WITHER);
	}

	@Override
	public void bootstrap(Registry<DecoratedPotPattern> registry) {
		register(registry, AURORA);
		register(registry, BAIT);
		register(registry, BLOOM);
		register(registry, BOLT);
		register(registry, BULLSEYE);
		register(registry, CARRIER);
		register(registry, CLUCK);
		register(registry, CRAWL);
		register(registry, CRESCENT);
		register(registry, CULTIVATOR);
		register(registry, DROUGHT);
		register(registry, ENCLOSURE);
		register(registry, ESSENCE);
		register(registry, EYE);
		register(registry, FOCUS);
		register(registry, FROST);
		register(registry, HARE);
		register(registry, HEIGHT);
		register(registry, HUMP);
		register(registry, ILLUMINATOR);
		register(registry, INCIDENCE);
		register(registry, LUMBER);
		register(registry, NAVIGATOR);
		register(registry, NEEDLES);
		register(registry, OMEN);
		register(registry, PLUME);
		register(registry, PROTECTION);
		register(registry, SHED);
		register(registry, SHINE);
		register(registry, SHOWER);
		register(registry, SPADE);
		register(registry, SPROUT);
		register(registry, VESSEL);
		register(registry, WITHER);
	}

	private static void register(Registry<DecoratedPotPattern> registry, ResourceKey<DecoratedPotPattern> pattern) {
		DecoratedPotPatternRegistryEntrypoint.register(
			registry,
			pattern,
			TTConstants.id(pattern.identifier().getPath() + "_pottery_pattern")
		);
	}

	private static ResourceKey<DecoratedPotPattern> createKey(String name) {
		return ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(name));
	}
}
