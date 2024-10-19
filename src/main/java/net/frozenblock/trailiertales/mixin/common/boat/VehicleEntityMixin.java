package net.frozenblock.trailiertales.mixin.common.boat;

import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VehicleEntity.class)
public abstract class VehicleEntityMixin extends Entity  {

	public VehicleEntityMixin(EntityType<?> variant, Level world) {
		super(variant, world);
	}

	@Inject(method = "destroy(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("HEAD"))
	public void trailierTales$destroy(ServerLevel level, DamageSource damageSource, CallbackInfo info) {
		if (VehicleEntity.class.cast(this) instanceof BoatBannerInterface bannerInterface) {
			if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
				ItemStack itemStack = bannerInterface.trailierTales$getBanner();
				bannerInterface.trailierTales$setBanner(ItemStack.EMPTY);
				this.spawnAtLocation(level, itemStack);
			}
		}
	}
}
