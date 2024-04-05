package net.frozenblock.trailiertales.mixin.client;

import net.frozenblock.trailiertales.TrailierTalesClient;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin {

	@Inject(method = "bootstrap", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
	private static void luna120$bootstrap(Registry<String> registry, CallbackInfoReturnable<String> info) {
		Registry.register(registry, TrailierTalesClient.BLANK_DECORATED, TrailierTalesClient.BLANK_DECORATED_NAME);
	}

}
