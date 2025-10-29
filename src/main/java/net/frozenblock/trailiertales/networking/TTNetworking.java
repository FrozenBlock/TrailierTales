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

package net.frozenblock.trailiertales.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.frozenblock.trailiertales.networking.packet.CoffinDebugPacket;
import net.frozenblock.trailiertales.networking.packet.CoffinRemoveDebugPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class TTNetworking {

	public static void init() {
		final PayloadTypeRegistry<RegistryFriendlyByteBuf> registry = PayloadTypeRegistry.playS2C();
		registry.register(CoffinDebugPacket.PACKET_TYPE, CoffinDebugPacket.CODEC);
		registry.register(CoffinRemoveDebugPacket.PACKET_TYPE, CoffinRemoveDebugPacket.CODEC);
	}

}
