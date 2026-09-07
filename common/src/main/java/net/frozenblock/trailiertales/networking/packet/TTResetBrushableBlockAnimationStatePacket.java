/*
 * Copyright 2026 FrozenBlock
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

import net.frozenblock.lib.networking.api.NetworkingHelper;
import net.frozenblock.lib.networking.api.PlayerLookup;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public record TTResetBrushableBlockAnimationStatePacket(BlockPos pos) implements CustomPacketPayload {
	public static final Type<TTResetBrushableBlockAnimationStatePacket> PACKET_TYPE = new Type<>(TTConstants.id("reset_brushable_block_animation_state"));
	public static final StreamCodec<RegistryFriendlyByteBuf, TTResetBrushableBlockAnimationStatePacket> CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC, TTResetBrushableBlockAnimationStatePacket::pos,
		TTResetBrushableBlockAnimationStatePacket::new
	);

	public static void sendToAll(ServerLevel level, BlockPos pos) {
		for (ServerPlayer player : PlayerLookup.tracking(level, pos)) {
			NetworkingHelper.sendToPlayer(
				player,
				new TTResetBrushableBlockAnimationStatePacket(pos)
			);
		}
	}

	@Override
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
