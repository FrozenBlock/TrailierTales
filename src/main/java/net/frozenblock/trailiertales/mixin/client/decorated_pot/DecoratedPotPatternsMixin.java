package net.frozenblock.trailiertales.mixin.client.decorated_pot;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin {

	@ModifyExpressionValue(method = "<clinit>",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/Map;ofEntries([Ljava/util/Map$Entry;)Ljava/util/Map;"
		)
	)
	private static Map<Item, ResourceKey<DecoratedPotPattern>> trailierTales$addNewSherds(Map<Item, ResourceKey<DecoratedPotPattern>> original) {
		Object2ObjectLinkedOpenHashMap<Item, ResourceKey<DecoratedPotPattern>> newMap = new Object2ObjectLinkedOpenHashMap<>();
		newMap.putAll(original);
		newMap.put(RegisterItems.BAIT_POTTERY_SHERD, TrailierTalesClient.BAIT_POTTERY_PATTERN);
		newMap.put(RegisterItems.BLOOM_POTTERY_SHERD, TrailierTalesClient.BLOOM_POTTERY_PATTERN);
		newMap.put(RegisterItems.BOLT_POTTERY_SHERD, TrailierTalesClient.BOLT_POTTERY_PATTERN);
		newMap.put(RegisterItems.BULLSEYE_POTTERY_SHERD, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN);
		newMap.put(RegisterItems.CRAWL_POTTERY_SHERD, TrailierTalesClient.CRAWL_POTTERY_PATTERN);
		newMap.put(RegisterItems.CRESCENT_POTTERY_SHERD, TrailierTalesClient.CRESCENT_POTTERY_PATTERN);
		newMap.put(RegisterItems.CULTIVATOR_POTTERY_SHERD, TrailierTalesClient.CULTIVATOR_POTTERY_PATTERN);
		newMap.put(RegisterItems.DROUGHT_POTTERY_SHERD, TrailierTalesClient.DROUGHT_POTTERY_PATTERN);
		newMap.put(RegisterItems.ESSENCE_POTTERY_SHERD, TrailierTalesClient.ESSENCE_POTTERY_PATTERN);
		newMap.put(RegisterItems.EYE_POTTERY_SHERD, TrailierTalesClient.EYE_POTTERY_PATTERN);
		newMap.put(RegisterItems.FOCUS_POTTERY_SHERD, TrailierTalesClient.FOCUS_POTTERY_PATTERN);
		newMap.put(RegisterItems.HUMP_POTTERY_SHERD, TrailierTalesClient.HUMP_POTTERY_PATTERN);
		newMap.put(RegisterItems.INCIDENCE_POTTERY_SHERD, TrailierTalesClient.INCIDENCE_POTTERY_PATTERN);
		newMap.put(RegisterItems.LUMBER_POTTERY_SHERD, TrailierTalesClient.LUMBER_POTTERY_PATTERN);
		newMap.put(RegisterItems.NAVIGATOR_POTTERY_SHERD, TrailierTalesClient.NAVIGATOR_POTTERY_PATTERN);
		newMap.put(RegisterItems.NEEDLES_POTTERY_SHERD, TrailierTalesClient.NEEDLES_POTTERY_PATTERN);
		newMap.put(RegisterItems.PLUME_POTTERY_SHERD, TrailierTalesClient.PLUME_POTTERY_PATTERN);
		newMap.put(RegisterItems.PROTECTION_POTTERY_SHERD, TrailierTalesClient.PROTECTION_POTTERY_PATTERN);
		newMap.put(RegisterItems.SHED_POTTERY_SHERD, TrailierTalesClient.SHED_POTTERY_PATTERN);
		newMap.put(RegisterItems.SHINE_POTTERY_SHERD, TrailierTalesClient.SHINE_POTTERY_PATTERN);
		newMap.put(RegisterItems.SHOWER_POTTERY_SHERD, TrailierTalesClient.SHOWER_POTTERY_PATTERN);
		newMap.put(RegisterItems.SPADE_POTTERY_SHERD, TrailierTalesClient.SPADE_POTTERY_PATTERN);
		newMap.put(RegisterItems.SPROUT_POTTERY_SHERD, TrailierTalesClient.SPROUT_POTTERY_PATTERN);
		newMap.put(RegisterItems.VESSEL_POTTERY_SHERD, TrailierTalesClient.VESSEL_POTTERY_PATTERN);
		newMap.put(RegisterItems.WITHER_POTTERY_SHERD, TrailierTalesClient.WITHER_POTTERY_PATTERN);
		return Map.copyOf(newMap);
	}

	@Inject(method = "bootstrap", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
	private static void trailierTales$bootstrap(Registry<DecoratedPotPattern> registry, CallbackInfoReturnable<DecoratedPotPattern> info) {
		trailierTales$register(registry, TrailierTalesClient.BLANK_DECORATED, TrailierTalesClient.BLANK_DECORATED_NAME);
		trailierTales$register(registry, TrailierTalesClient.BAIT_POTTERY_PATTERN, TrailierTalesClient.BAIT_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.BLOOM_POTTERY_PATTERN, TrailierTalesClient.BLOOM_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.BOLT_POTTERY_PATTERN, TrailierTalesClient.BOLT_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.CRAWL_POTTERY_PATTERN, TrailierTalesClient.CRAWL_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.CRESCENT_POTTERY_PATTERN, TrailierTalesClient.CRESCENT_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.CULTIVATOR_POTTERY_PATTERN, TrailierTalesClient.CULTIVATOR_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.DROUGHT_POTTERY_PATTERN, TrailierTalesClient.DROUGHT_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.ESSENCE_POTTERY_PATTERN, TrailierTalesClient.ESSENCE_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.EYE_POTTERY_PATTERN, TrailierTalesClient.EYE_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.FOCUS_POTTERY_PATTERN, TrailierTalesClient.FOCUS_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.HUMP_POTTERY_PATTERN, TrailierTalesClient.HUMP_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.INCIDENCE_POTTERY_PATTERN, TrailierTalesClient.INCIDENCE_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.LUMBER_POTTERY_PATTERN, TrailierTalesClient.LUMBER_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.NAVIGATOR_POTTERY_PATTERN, TrailierTalesClient.NAVIGATOR_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.NEEDLES_POTTERY_PATTERN, TrailierTalesClient.NEEDLES_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.PLUME_POTTERY_PATTERN, TrailierTalesClient.PLUME_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.PROTECTION_POTTERY_PATTERN, TrailierTalesClient.PROTECTION_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.SHED_POTTERY_PATTERN, TrailierTalesClient.SHED_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.SHINE_POTTERY_PATTERN, TrailierTalesClient.SHINE_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.SHOWER_POTTERY_PATTERN, TrailierTalesClient.SHOWER_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.SPADE_POTTERY_PATTERN, TrailierTalesClient.SPADE_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.SPROUT_POTTERY_PATTERN, TrailierTalesClient.SPROUT_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.VESSEL_POTTERY_PATTERN, TrailierTalesClient.VESSEL_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.WITHER_POTTERY_PATTERN, TrailierTalesClient.WITHER_POTTERY_PATTERN_NAME);
	}

	@Unique
	private static void trailierTales$register(Registry<DecoratedPotPattern> registry, ResourceKey<DecoratedPotPattern> registryKey, String path) {
		Registry.register(registry, registryKey, new DecoratedPotPattern(TrailierConstants.id(path)));
	}

}
