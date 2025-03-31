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

package net.frozenblock.trailiertales.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.frozenblock.trailiertales.networking.packet.CoffinDebugPacket;
import net.frozenblock.trailiertales.networking.packet.CoffinRemoveDebugPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class TTNetworking {

	public static void init() {
		PayloadTypeRegistry<RegistryFriendlyByteBuf> registry = PayloadTypeRegistry.playS2C();
		registry.register(CoffinDebugPacket.PACKET_TYPE, CoffinDebugPacket.CODEC);
		registry.register(CoffinRemoveDebugPacket.PACKET_TYPE, CoffinRemoveDebugPacket.CODEC);
	}
}
