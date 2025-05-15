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

package net.frozenblock.trailiertales.mixin.common.coffin;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.minecraft.server.level.ServerLevel;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.TeleportTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(
		method = "teleport",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;teleportCrossDimension(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/portal/TeleportTransition;)Lnet/minecraft/world/entity/Entity;"
		)
	)
	public void trailierTales$changeDimension(
		TeleportTransition dimensionTransition,
		CallbackInfoReturnable<Entity> info,
		@Local(ordinal = 0) ServerLevel level
	) {
		if (EntityCoffinData.entityHasCoffinData(Entity.class.cast(this))) {
			CoffinBlock.onCoffinUntrack(level, Entity.class.cast(this), null, true);
		}
	}

	@Inject(method = "canUsePortal", at = @At("HEAD"), cancellable = true)
	public void trailierTales$canUsePortal(CallbackInfoReturnable<Boolean> info) {
		if (EntityCoffinData.entityHasCoffinData(Entity.class.cast(this))) info.setReturnValue(false);
	}

}
