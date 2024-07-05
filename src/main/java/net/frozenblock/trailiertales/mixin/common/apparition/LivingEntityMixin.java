package net.frozenblock.trailiertales.mixin.common.apparition;

import net.frozenblock.trailiertales.entity.ai.apparition.impl.EntityPossessionData;
import net.frozenblock.trailiertales.entity.ai.apparition.impl.EntityPossessionInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "remove", at = @At("HEAD"))
	public void trailierTales$remove(Entity.RemovalReason reason, CallbackInfo info) {
		if (reason == Entity.RemovalReason.KILLED || reason == Entity.RemovalReason.DISCARDED) {
			LivingEntity entity = LivingEntity.class.cast(this);
			if (entity instanceof EntityPossessionInterface possessionInterface) {
				EntityPossessionData entityPossessionData = possessionInterface.trailierTales$getPossessionData();
				if (entityPossessionData != null) {
					entityPossessionData.onDeath(entity);
				}
			}
		}
	}

}
