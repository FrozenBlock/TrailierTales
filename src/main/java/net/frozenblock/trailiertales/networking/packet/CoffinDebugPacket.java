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

package net.frozenblock.trailiertales.networking.packet;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record CoffinDebugPacket(int entityId, long lastInteractionTime, BlockPos coffinPos, long gameTime) implements CustomPacketPayload {
	public static final Type<CoffinDebugPacket> PACKET_TYPE = new Type<>(
		TTConstants.id("debug_coffin")
	);

	public static final StreamCodec<FriendlyByteBuf, CoffinDebugPacket> CODEC = StreamCodec.ofMember(CoffinDebugPacket::write, CoffinDebugPacket::new);

	public CoffinDebugPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readVarInt(), buf.readLong(), buf.readBlockPos(), buf.readLong());
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeVarInt(this.entityId);
		buf.writeLong(this.lastInteractionTime);
		buf.writeBlockPos(this.coffinPos);
		buf.writeLong(this.gameTime);
	}

	@NotNull
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
