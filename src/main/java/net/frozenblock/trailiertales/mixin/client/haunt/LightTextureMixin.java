package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LightTexture.class)
public class LightTextureMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyReturnValue(
		method = "getDarknessGamma",
		at = @At("RETURN")
	)
	private float trailierTales$modifyDarknessGamma(float darknessGamma, float tickDelta) {
		MobEffectInstance hauntInstance = this.minecraft.player.getEffect(TTMobEffects.HAUNT);
		if (hauntInstance != null) {
			return Math.max(hauntInstance.getBlendFactor(this.minecraft.player, tickDelta) * 0.67F, darknessGamma);
		}
		return darknessGamma;
	}


}
