package net.frozenblock.trailiertales.mixin.client.decorated_pot;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
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
		newMap.put(RegisterItems.BULLSEYE_POTTERY_SHERD, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN);
		newMap.put(RegisterItems.WITHER_POTTERY_SHERD, TrailierTalesClient.WITHER_POTTERY_PATTERN);
		newMap.put(RegisterItems.BLOOM_POTTERY_SHERD, TrailierTalesClient.BLOOM_POTTERY_PATTERN);
		newMap.put(RegisterItems.INCIDENCE_POTTERY_SHERD, TrailierTalesClient.INCIDENCE_POTTERY_PATTERN);
		newMap.put(RegisterItems.CULTIVATOR_POTTERY_SHERD, TrailierTalesClient.CULTIVATOR_POTTERY_PATTERN);
		newMap.put(RegisterItems.SPADE_POTTERY_SHERD, TrailierTalesClient.SPADE_POTTERY_PATTERN);
		return Map.copyOf(newMap);
	}

	@Inject(method = "bootstrap", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
	private static void trailierTales$bootstrap(Registry<DecoratedPotPattern> registry, CallbackInfoReturnable<DecoratedPotPattern> info) {
		trailierTales$register(registry, TrailierTalesClient.BLANK_DECORATED, TrailierTalesClient.BLANK_DECORATED_NAME);
		trailierTales$register(registry, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.WITHER_POTTERY_PATTERN, TrailierTalesClient.WITHER_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.BLOOM_POTTERY_PATTERN, TrailierTalesClient.BLOOM_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.INCIDENCE_POTTERY_PATTERN, TrailierTalesClient.INCIDENCE_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.CULTIVATOR_POTTERY_PATTERN, TrailierTalesClient.CULTIVATOR_POTTERY_PATTERN_NAME);
		trailierTales$register(registry, TrailierTalesClient.SPADE_POTTERY_PATTERN, TrailierTalesClient.SPADE_POTTERY_PATTERN_NAME);
	}

	@Unique
	private static void trailierTales$register(Registry<DecoratedPotPattern> registry, ResourceKey<DecoratedPotPattern> registryKey, String path) {
		Registry.register(registry, registryKey, new DecoratedPotPattern(TrailierConstants.id(path)));
	}

}
