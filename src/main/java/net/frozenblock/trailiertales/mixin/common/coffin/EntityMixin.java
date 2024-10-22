package net.frozenblock.trailiertales.mixin.common.coffin;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.minecraft.server.level.ServerLevel;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.TeleportTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "teleport", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/portal/TeleportTransition;newLevel()Lnet/minecraft/server/level/ServerLevel;"))
	public void trailierTales$changeDimension(TeleportTransition dimensionTransition, CallbackInfoReturnable<Entity> info, @Local ServerLevel level) {
		CoffinBlock.onCoffinUntrack(level, Entity.class.cast(this), null, true);
	}

	@Inject(method = "canUsePortal", at = @At("HEAD"), cancellable = true)
	public void trailierTales$canUsePortal(CallbackInfoReturnable<Boolean> info) {
		if (Entity.class.cast(this) instanceof EntityCoffinInterface entityCoffinInterface) {
			if (entityCoffinInterface.trailierTales$getCoffinData() != null) {
				info.setReturnValue(false);
			}
		}
	}

}
