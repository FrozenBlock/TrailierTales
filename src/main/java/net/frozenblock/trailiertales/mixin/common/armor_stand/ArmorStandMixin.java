package net.frozenblock.trailiertales.mixin.common.armor_stand;

import net.frozenblock.trailiertales.config.EntityConfig;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ArmorStand.class)
public class ArmorStandMixin {

	@Shadow
	private byte setBit(byte value, int bitField, boolean set) {
		throw new AssertionError("Mixin injection failed - Trailier Tales ArmorStandMixin.");
	}

	@ModifyArgs(
		method = "defineSynchedData",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/network/syncher/SynchedEntityData$Builder;define(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)Lnet/minecraft/network/syncher/SynchedEntityData$Builder;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/entity/decoration/ArmorStand;DATA_CLIENT_FLAGS:Lnet/minecraft/network/syncher/EntityDataAccessor;"
			)
		)
	)
	public void trailierTales$enableArms(Args args) {
		if (EntityConfig.get().armorStand.armor_stand_arms) {
			args.set(1, this.setBit((byte) 0, 4, true));
		}
	}

}
