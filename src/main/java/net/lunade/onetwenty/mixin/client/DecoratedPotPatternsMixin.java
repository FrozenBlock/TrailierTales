package net.lunade.onetwenty.mixin.client;

import net.lunade.onetwenty.Luna120Client;
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
		Registry.register(registry, Luna120Client.BLANK_DECORATED, Luna120Client.BLANK_DECORATED_NAME);
	}

}
