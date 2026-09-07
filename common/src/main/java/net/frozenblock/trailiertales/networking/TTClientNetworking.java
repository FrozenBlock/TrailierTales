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

package net.frozenblock.trailiertales.networking;

import net.frozenblock.lib.networking.api.ClientNetworkingHelper;
import net.frozenblock.trailiertales.block.impl.client.BrushableBlockAnimationState;
import net.frozenblock.trailiertales.networking.packet.TTResetBrushableBlockAnimationStatePacket;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class TTClientNetworking {

	public static void setup() {
		receiveResetBrushableBlockAnimationStatePacket();
	}

	public static void receiveResetBrushableBlockAnimationStatePacket() {
		ClientNetworkingHelper.registerGlobalClientReceiver(TTResetBrushableBlockAnimationStatePacket.PACKET_TYPE, (packet, minecraft, player) -> {
			BrushableBlockAnimationState.reset(minecraft.level.getBlockEntity(packet.pos()));
		});
	}

	private TTClientNetworking() {}
}
