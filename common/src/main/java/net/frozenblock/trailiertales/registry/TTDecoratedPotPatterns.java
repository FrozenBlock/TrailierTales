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

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

public final class TTDecoratedPotPatterns {
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

	public static void bootstrap(BootstrapContext<DecoratedPotPattern> context) {
		DecoratedPotPatterns.registerWithDefaultAsset(context, AURORA);
		DecoratedPotPatterns.registerWithDefaultAsset(context, BAIT);
		DecoratedPotPatterns.registerWithDefaultAsset(context, BLOOM);
		DecoratedPotPatterns.registerWithDefaultAsset(context, BOLT);
		DecoratedPotPatterns.registerWithDefaultAsset(context, BULLSEYE);
		DecoratedPotPatterns.registerWithDefaultAsset(context, CARRIER);
		DecoratedPotPatterns.registerWithDefaultAsset(context, CLUCK);
		DecoratedPotPatterns.registerWithDefaultAsset(context, CRAWL);
		DecoratedPotPatterns.registerWithDefaultAsset(context, CRESCENT);
		DecoratedPotPatterns.registerWithDefaultAsset(context, CULTIVATOR);
		DecoratedPotPatterns.registerWithDefaultAsset(context, DROUGHT);
		DecoratedPotPatterns.registerWithDefaultAsset(context, ENCLOSURE);
		DecoratedPotPatterns.registerWithDefaultAsset(context, ESSENCE);
		DecoratedPotPatterns.registerWithDefaultAsset(context, EYE);
		DecoratedPotPatterns.registerWithDefaultAsset(context, FOCUS);
		DecoratedPotPatterns.registerWithDefaultAsset(context, FROST);
		DecoratedPotPatterns.registerWithDefaultAsset(context, HARE);
		DecoratedPotPatterns.registerWithDefaultAsset(context, HEIGHT);
		DecoratedPotPatterns.registerWithDefaultAsset(context, HUMP);
		DecoratedPotPatterns.registerWithDefaultAsset(context, ILLUMINATOR);
		DecoratedPotPatterns.registerWithDefaultAsset(context, INCIDENCE);
		DecoratedPotPatterns.registerWithDefaultAsset(context, LUMBER);
		DecoratedPotPatterns.registerWithDefaultAsset(context, NAVIGATOR);
		DecoratedPotPatterns.registerWithDefaultAsset(context, NEEDLES);
		DecoratedPotPatterns.registerWithDefaultAsset(context, OMEN);
		DecoratedPotPatterns.registerWithDefaultAsset(context, PLUME);
		DecoratedPotPatterns.registerWithDefaultAsset(context, PROTECTION);
		DecoratedPotPatterns.registerWithDefaultAsset(context, SHED);
		DecoratedPotPatterns.registerWithDefaultAsset(context, SHINE);
		DecoratedPotPatterns.registerWithDefaultAsset(context, SHOWER);
		DecoratedPotPatterns.registerWithDefaultAsset(context, SPADE);
		DecoratedPotPatterns.registerWithDefaultAsset(context, SPROUT);
		DecoratedPotPatterns.registerWithDefaultAsset(context, VESSEL);
		DecoratedPotPatterns.registerWithDefaultAsset(context, WITHER);
	}

	private static ResourceKey<DecoratedPotPattern> createKey(String name) {
		return ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(name));
	}

	private TTDecoratedPotPatterns() {}
}
