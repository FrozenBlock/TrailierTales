package net.frozenblock.trailiertales;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.frozenblock.lib.debug.client.api.DebugRendererEvents;
import net.frozenblock.lib.debug.client.impl.DebugRenderManager;
import net.frozenblock.lib.menu.api.PanoramaApi;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.trailiertales.client.TTBlockRenderLayers;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.TTParticleEngine;
import net.frozenblock.trailiertales.client.renderer.debug.CoffinDebugRenderer;
import net.frozenblock.trailiertales.networking.packet.CoffinDebugPacket;
import net.frozenblock.trailiertales.networking.packet.CoffinRemoveDebugPacket;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TrailierTalesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		TrailierTales.init();
		SplashTextAPI.addSplashLocation(TTConstants.id("texts/splashes.txt"));
		addPanorama("catacombs");

		TTBlockRenderLayers.init();
		TTModelLayers.init();
		TTParticleEngine.init();

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

	private static void addPanorama(String panoramaName) {
		ResourceLocation panoramaLocation = TTConstants.id("textures/gui/title/" + panoramaName + "/panorama");
		PanoramaApi.addPanorama(panoramaLocation);
	}
}
