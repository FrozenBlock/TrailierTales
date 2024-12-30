package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.impl.sherd.DecoratedPotPatternRegistryEntrypoint;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

public class TTDecoratedPotPatternRegistry implements DecoratedPotPatternRegistryEntrypoint {

	@Override
	public void registerForItems(Map<Item, ResourceKey<DecoratedPotPattern>> map) {
		put(map, TTItems.AURORA_POTTERY_SHERD, "aurora");
		put(map, TTItems.BAIT_POTTERY_SHERD, "bait");
		put(map, TTItems.BLOOM_POTTERY_SHERD, "bloom");
		put(map, TTItems.BOLT_POTTERY_SHERD, "bolt");
		put(map, TTItems.BULLSEYE_POTTERY_SHERD, "bullseye");
		put(map, TTItems.CARRIER_POTTERY_SHERD, "carrier");
		put(map, TTItems.CLUCK_POTTERY_SHERD, "cluck");
		put(map, TTItems.CRAWL_POTTERY_SHERD, "crawl");
		put(map, TTItems.CRESCENT_POTTERY_SHERD, "crescent");
		put(map, TTItems.CULTIVATOR_POTTERY_SHERD, "cultivator");
		put(map, TTItems.DROUGHT_POTTERY_SHERD, "drought");
		put(map, TTItems.ENCLOSURE_POTTERY_SHERD, "enclosure");
		put(map, TTItems.ESSENCE_POTTERY_SHERD, "essence");
		put(map, TTItems.EYE_POTTERY_SHERD, "eye");
		put(map, TTItems.FOCUS_POTTERY_SHERD, "focus");
		put(map, TTItems.FROST_POTTERY_SHERD, "frost");
		put(map, TTItems.HARE_POTTERY_SHERD, "hare");
		put(map, TTItems.HEIGHT_POTTERY_SHERD, "height");
		put(map, TTItems.HUMP_POTTERY_SHERD, "hump");
		put(map, TTItems.ILLUMINATOR_POTTERY_SHERD, "illuminator");
		put(map, TTItems.INCIDENCE_POTTERY_SHERD, "incidence");
		put(map, TTItems.LUMBER_POTTERY_SHERD, "lumber");
		put(map, TTItems.NAVIGATOR_POTTERY_SHERD, "navigator");
		put(map, TTItems.NEEDLES_POTTERY_SHERD, "needles");
		put(map, TTItems.OMEN_POTTERY_SHERD, "omen");
		put(map, TTItems.PLUME_POTTERY_SHERD, "plume");
		put(map, TTItems.PROTECTION_POTTERY_SHERD, "protection");
		put(map, TTItems.SHED_POTTERY_SHERD, "shed");
		put(map, TTItems.SHINE_POTTERY_SHERD, "shine");
		put(map, TTItems.SHOWER_POTTERY_SHERD, "shower");
		put(map, TTItems.SPADE_POTTERY_SHERD, "spade");
		put(map, TTItems.SPROUT_POTTERY_SHERD, "sprout");
		put(map, TTItems.VESSEL_POTTERY_SHERD, "vessel");
		put(map, TTItems.WITHER_POTTERY_SHERD, "wither");
	}

	@Override
	public void bootstrap(Registry<DecoratedPotPattern> registry) {
		register(registry, "aurora");
		register(registry, "bait");
		register(registry, "bloom");
		register(registry, "bolt");
		register(registry, "bullseye");
		register(registry, "carrier");
		register(registry, "cluck");
		register(registry, "crawl");
		register(registry, "crescent");
		register(registry, "cultivator");
		register(registry, "drought");
		register(registry, "enclosure");
		register(registry, "essence");
		register(registry, "eye");
		register(registry, "focus");
		register(registry, "frost");
		register(registry, "hare");
		register(registry, "height");
		register(registry, "hump");
		register(registry, "illuminator");
		register(registry, "incidence");
		register(registry, "lumber");
		register(registry, "navigator");
		register(registry, "needles");
		register(registry, "omen");
		register(registry, "plume");
		register(registry, "protection");
		register(registry, "shed");
		register(registry, "shine");
		register(registry, "shower");
		register(registry, "spade");
		register(registry, "sprout");
		register(registry, "vessel");
		register(registry, "wither");
	}

	private static void put(@NotNull Map<Item, ResourceKey<DecoratedPotPattern>> map, @NotNull Item sherd, String sherdName) {
		map.put(sherd, ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(sherdName)));
	}

	private static void register(@NotNull Registry<DecoratedPotPattern> registry, String sherdName) {
		DecoratedPotPatternRegistryEntrypoint.register(
			registry,
			ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(sherdName)),
			TTConstants.id(sherdName + "_pottery_pattern")
		);
	}

}
