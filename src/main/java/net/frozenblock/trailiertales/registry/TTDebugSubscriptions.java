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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.util.debug.DebugCoffinInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.debug.DebugSubscription;

public final class TTDebugSubscriptions {
	public static final DebugSubscription<DebugCoffinInfo> COFFINS = registerWithValue("coffins", DebugCoffinInfo.STREAM_CODEC);

	public static void init() {
	}

	private static DebugSubscription<?> registerSimple(String path) {
		return Registry.register(BuiltInRegistries.DEBUG_SUBSCRIPTION, TTConstants.id(path), new DebugSubscription(null));
	}

	private static <T> DebugSubscription<T> registerWithValue(String path, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
		return Registry.register(BuiltInRegistries.DEBUG_SUBSCRIPTION, TTConstants.id(path), new DebugSubscription<>(streamCodec));
	}

	private static <T> DebugSubscription<T> registerTemporaryValue(String path, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, int expireAfterTicks) {
		return Registry.register(BuiltInRegistries.DEBUG_SUBSCRIPTION, TTConstants.id(path), new DebugSubscription<>(streamCodec, expireAfterTicks));
	}
}
