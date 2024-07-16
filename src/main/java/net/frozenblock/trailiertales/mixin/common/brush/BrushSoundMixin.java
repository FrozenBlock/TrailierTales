package net.frozenblock.trailiertales.mixin.common.brush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.mod_compat.TrailierModIntegrations;
import net.frozenblock.trailiertales.mod_compat.wilderwild.AbstractWWIntegration;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BrushItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrushItem.class)
public class BrushSoundMixin {

	@ModifyExpressionValue(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrushableBlock;getBrushSound()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent trailierTales$newBrushSounds(SoundEvent original) {
		AbstractWWIntegration wwIntegration = TrailierModIntegrations.WILDER_WILD_INTEGRATION.getIntegration();
		if (original == SoundEvents.BRUSH_GRAVEL && wwIntegration.newGravelSounds()) {
			return RegisterSounds.BRUSH_GRAVEL_WW;
		} else if (original == RegisterSounds.BRUSH_CLAY && wwIntegration.newClaySounds()) {
			return RegisterSounds.BRUSH_CLAY_WW;
		}
		return original;
	}

}
