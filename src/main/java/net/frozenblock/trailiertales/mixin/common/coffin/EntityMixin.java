package net.frozenblock.trailiertales.mixin.common.coffin;

import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "changeDimension", at = @At("HEAD"))
	public void trailierTales$changeDimension(DimensionTransition dimensionTransition, CallbackInfoReturnable<Entity> info) {
		if (EntityCoffinData.entityHasCoffinData(Entity.class.cast(this))) {
			CoffinBlock.onCoffinUntrack(Entity.class.cast(this), null, true);
		}
	}

	@Inject(method = "canUsePortal", at = @At("HEAD"), cancellable = true)
	public void trailierTales$canUsePortal(CallbackInfoReturnable<Boolean> info) {
		if (EntityCoffinData.entityHasCoffinData(Entity.class.cast(this))) {
			info.setReturnValue(false);
		}
	}

}
