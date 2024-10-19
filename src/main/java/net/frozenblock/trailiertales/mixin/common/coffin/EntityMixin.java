package net.frozenblock.trailiertales.mixin.common.coffin;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "changeDimension", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/portal/DimensionTransition;newLevel()Lnet/minecraft/server/level/ServerLevel;"))
	public void trailierTales$changeDimension(DimensionTransition dimensionTransition, CallbackInfoReturnable<Entity> info, @Local ServerLevel level) {
		CoffinBlock.onCoffinUntrack(level, Entity.class.cast(this), null, true);
	}

}
