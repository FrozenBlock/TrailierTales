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

package net.frozenblock.trailiertales;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.frozenblock.lib.debug.client.api.DebugRendererEvents;
import net.frozenblock.lib.debug.client.impl.DebugRenderManager;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.lib.music.api.client.pitch.MusicPitchApi;
import net.frozenblock.trailiertales.client.TTBlockRenderLayers;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.TTParticleEngine;
import net.frozenblock.trailiertales.client.renderer.debug.CoffinDebugRenderer;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import net.frozenblock.trailiertales.networking.packet.CoffinDebugPacket;
import net.frozenblock.trailiertales.networking.packet.CoffinRemoveDebugPacket;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class TrailierTalesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		SplashTextAPI.addSplashLocation(TTConstants.id("texts/splashes.txt"));

		TTBlockRenderLayers.init();
		TTModelLayers.init();
		TTParticleEngine.init();

		MusicPitchApi.registerForStructureInside(CatacombsGenerator.CATACOMBS_KEY.location(), TrailierTalesClient::calculateCatacombsMusicPitch);

		DebugRendererEvents.DEBUG_RENDERERS_CREATED.register(client -> {
			CoffinDebugRenderer coffinDebugRenderer = new CoffinDebugRenderer(client);

			ClientPlayNetworking.registerGlobalReceiver(CoffinDebugPacket.PACKET_TYPE, (packet, ctx) -> {
				coffinDebugRenderer.addConnection(packet.entityId(), packet.coffinPos(), packet.lastInteractionTime(), packet.gameTime());
			});

			ClientPlayNetworking.registerGlobalReceiver(CoffinRemoveDebugPacket.PACKET_TYPE, (packet, ctx) -> {
				coffinDebugRenderer.scheduleRemoval(packet.entityId());
			});

			DebugRenderManager.addClearRunnable(coffinDebugRenderer::clear);

			DebugRenderManager.registerRenderer(TTConstants.id("coffin"), coffinDebugRenderer::render);
		});
	}

	private static float calculateCatacombsMusicPitch(long gameTime) {
		if (!TTMiscConfig.Client.DISTORTED_CATACOMBS_MUSIC) return 1F;
		float basePitch = 0.98F + Mth.sin((float) ((gameTime * Math.PI) / 1000F)) * 0.005F;
		float additionalPitchChangeA = Mth.clamp(Mth.cos((float) ((gameTime * Math.PI) / 600F)) * 0.5F, -0.00975F, 0.00975F);
		float additionalWobble = Mth.sin((float) ((gameTime * Math.PI) / 20F)) * 0.005F;
		return basePitch + additionalPitchChangeA + additionalWobble;
	}
}
