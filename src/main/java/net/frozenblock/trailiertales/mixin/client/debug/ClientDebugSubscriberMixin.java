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

package net.frozenblock.trailiertales.mixin.client.debug;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTDebugSubscriptions;
import net.minecraft.client.multiplayer.ClientDebugSubscriber;
import net.minecraft.util.debug.DebugSubscription;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Set;

@Environment(EnvType.CLIENT)
@Mixin(ClientDebugSubscriber.class)
public abstract class ClientDebugSubscriberMixin {

	@Shadow
	private static void addFlag(Set<DebugSubscription<?>> set, DebugSubscription<?> debugSubscription, boolean bl) {
	}

	@Inject(
		method = "requestedSubscriptions",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/multiplayer/ClientDebugSubscriber;addFlag(Ljava/util/Set;Lnet/minecraft/util/debug/DebugSubscription;Z)V",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/util/debug/DebugSubscriptions;BREEZES:Lnet/minecraft/util/debug/DebugSubscription;"
			)
		)
	)
	private void trailierTales$addDebugSubscriptions(
		CallbackInfoReturnable<Set<DebugSubscription<?>>> info,
		@Local Set set
	) {
		addFlag(set, TTDebugSubscriptions.COFFINS, TTConstants.DEBUG_COFFINS);
	}

}
