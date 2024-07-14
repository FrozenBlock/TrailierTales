package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.registry.RegisterMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {

	@ModifyExpressionValue(
		method = "play",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/resources/sounds/Sound;getAttenuationDistance()I"
		)
	)
	public int modifyAttenuationDistance(int original) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.hasEffect(RegisterMobEffects.HAUNT)) {
			return (int) (original * 0.5F);
		}
		return original;
	}
}
