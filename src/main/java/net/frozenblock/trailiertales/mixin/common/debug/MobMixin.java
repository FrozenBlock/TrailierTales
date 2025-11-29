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

package net.frozenblock.trailiertales.mixin.common.debug;

import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.registry.TTDebugSubscriptions;
import net.frozenblock.trailiertales.util.debug.DebugCoffinInfo;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.debug.DebugValueSource;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

	@Inject(method = "registerDebugValues", at = @At("HEAD"))
	public void trailierTales$registerDebugValues(ServerLevel level, DebugValueSource.Registration registration, CallbackInfo info) {
		registration.register(
			TTDebugSubscriptions.COFFINS,
			() -> {
				if (!(Mob.class.cast(this) instanceof EntityCoffinInterface coffinInterface)) return null;

				final EntityCoffinData coffinData = coffinInterface.trailierTales$getCoffinData();
				if (coffinData == null) return null;

				if (coffinData.getSpawner(level).isEmpty()) return null;

				return new DebugCoffinInfo(coffinData.getPos(), level.getGameTime() - coffinData.lastInteraction());
			}
		);
	}

}
