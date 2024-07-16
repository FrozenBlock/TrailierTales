package net.frozenblock.trailiertales.mixin.client.brushable_block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.mod_compat.TrailierModIntegrations;
import net.frozenblock.trailiertales.mod_compat.wilderwild.AbstractWWIntegration;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class BrushingCompleteSoundMixin {

	@ModifyExpressionValue(
		method = "levelEvent",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrushableBlock;getBrushCompletedSound()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent trailierTales$newBrushSounds(SoundEvent original) {
		AbstractWWIntegration wwIntegration = TrailierModIntegrations.WILDER_WILD_INTEGRATION.getIntegration();
		if (original == SoundEvents.BRUSH_GRAVEL_COMPLETED && wwIntegration.newGravelSounds()) {
			return RegisterSounds.BRUSH_GRAVEL_WW_COMPLETED;
		} else if (original == RegisterSounds.BRUSH_CLAY_COMPLETED && wwIntegration.newClaySounds()) {
			return RegisterSounds.BRUSH_CLAY_WW_COMPLETED;
		}
		return original;
	}

}
