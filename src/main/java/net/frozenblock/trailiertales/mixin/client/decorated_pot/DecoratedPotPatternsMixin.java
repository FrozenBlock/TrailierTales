package net.frozenblock.trailiertales.mixin.client.decorated_pot;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
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
	private static Map<Item, ResourceKey<String>> trailierTales$addNewSherds(Map<Item, ResourceKey<String>> original) {
		Object2ObjectLinkedOpenHashMap<Item, ResourceKey<String>> newMap = new Object2ObjectLinkedOpenHashMap<>();
		newMap.putAll(original);
		newMap.put(RegisterItems.BULLSEYE_POTTERY_SHERD, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN);
		return Map.copyOf(newMap);
	}

	@Inject(method = "bootstrap", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
	private static void trailierTales$bootstrap(Registry<String> registry, CallbackInfoReturnable<String> info) {
		Registry.register(registry, TrailierTalesClient.BLANK_DECORATED, TrailierTalesClient.BLANK_DECORATED_NAME);
		Registry.register(registry, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN, TrailierTalesClient.BULLSEYE_POTTERY_PATTERN_NAME);
	}

}
