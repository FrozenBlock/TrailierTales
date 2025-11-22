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

package net.frozenblock.trailiertales.networking.packet;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CoffinRemoveDebugPacket(Integer entityId) implements CustomPacketPayload {
	public static final Type<CoffinRemoveDebugPacket> PACKET_TYPE = new Type<>(TTConstants.id("debug_coffin_remove"));
	public static final StreamCodec<FriendlyByteBuf, CoffinRemoveDebugPacket> CODEC = StreamCodec.ofMember(CoffinRemoveDebugPacket::write, CoffinRemoveDebugPacket::new);

	public CoffinRemoveDebugPacket(FriendlyByteBuf buf) {
		this(buf.readVarInt());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeVarInt(this.entityId);
	}

	public Type<?> type() {
		return PACKET_TYPE;
	}
}
