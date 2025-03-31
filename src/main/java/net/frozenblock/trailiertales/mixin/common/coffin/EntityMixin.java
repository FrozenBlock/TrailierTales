/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
		if (EntityCoffinData.entityHasCoffinData(Entity.class.cast(this))) {
			info.setReturnValue(false);
		}
	}

}
