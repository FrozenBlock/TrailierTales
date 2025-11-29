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

package net.frozenblock.trailiertales.util.debug;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record DebugCoffinInfo(BlockPos coffinPos, long lastInteractionDifference) {
	public static final StreamCodec<FriendlyByteBuf, DebugCoffinInfo> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC, DebugCoffinInfo::coffinPos,
		ByteBufCodecs.LONG, DebugCoffinInfo::lastInteractionDifference,
		DebugCoffinInfo::new
	);
}
