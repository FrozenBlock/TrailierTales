/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.mixin.common.armor_stand;

import net.frozenblock.trailiertales.config.TTEntityConfig;
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
		if (TTEntityConfig.get().armorStand.armor_stand_arms) {
			args.set(1, this.setBit((byte) 0, 4, true));
		}
	}

}
