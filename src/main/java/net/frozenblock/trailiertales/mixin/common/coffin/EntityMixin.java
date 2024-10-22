package net.frozenblock.trailiertales.mixin.common.coffin;

import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
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
		CoffinBlock.onCoffinUntrack(Entity.class.cast(this), null, true);
	}

	@Inject(method = "canChangeDimensions", at = @At("HEAD"), cancellable = true)
	public void trailierTales$canChangeDimensions(CallbackInfoReturnable<Boolean> info) {
		if (Entity.class.cast(this) instanceof EntityCoffinInterface entityCoffinInterface) {
			if (entityCoffinInterface.trailierTales$getCoffinData() != null) {
				info.setReturnValue(false);
			}
		}
	}

}
