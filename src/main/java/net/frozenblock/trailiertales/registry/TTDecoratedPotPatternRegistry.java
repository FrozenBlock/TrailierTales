package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.impl.sherd.DecoratedPotPatternRegistryEntrypoint;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import org.jetbrains.annotations.NotNull;

public class TTDecoratedPotPatternRegistry implements DecoratedPotPatternRegistryEntrypoint {

	@Override
	public void bootstrap(Registry<DecoratedPotPattern> registry) {
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

	public static void register(@NotNull Registry<DecoratedPotPattern> registry, String sherdName) {
		ResourceLocation location = TTConstants.id(sherdName + "_pottery_pattern");
		DecoratedPotPatternRegistryEntrypoint.register(
			registry,
			ResourceKey.create(Registries.DECORATED_POT_PATTERN, location),
			location
		);
	}

}
