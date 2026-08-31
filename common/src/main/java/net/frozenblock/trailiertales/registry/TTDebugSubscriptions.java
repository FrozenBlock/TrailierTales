/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.util.debug.DebugCoffinInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.debug.DebugSubscription;

public final class TTDebugSubscriptions {
	private static final DeferredRegister<DebugSubscription<?>> REGISTER = DeferredRegister.create(Registries.DEBUG_SUBSCRIPTION, TTConstants.MOD_ID);

	public static final DeferredHolder<DebugSubscription<?>, DebugSubscription<DebugCoffinInfo>> COFFINS = registerWithValue("coffins", DebugCoffinInfo.STREAM_CODEC);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static DeferredHolder<DebugSubscription<?>, DebugSubscription<?>> registerSimple(String path) {
		return REGISTER.register(path, () -> new DebugSubscription<>(null));
	}

	private static <T> DeferredHolder<DebugSubscription<?>, DebugSubscription<T>> registerWithValue(String path, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
		return REGISTER.register(path, () -> new DebugSubscription<>(streamCodec));
	}

	private static <T> DeferredHolder<DebugSubscription<?>, DebugSubscription<T>> registerTemporaryValue(String path, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, int expireAfterTicks) {
		return REGISTER.register(path, () -> new DebugSubscription<>(streamCodec, expireAfterTicks));
	}

	private TTDebugSubscriptions() {}
}
