package net.frozenblock.trailiertales;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.lib.debug.client.api.DebugRendererEvents;
import net.frozenblock.lib.debug.client.impl.DebugRenderManager;
import net.frozenblock.lib.menu.api.Panoramas;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.trailiertales.block.render.CoffinRenderer;
import net.frozenblock.trailiertales.debug.client.renderer.CoffinDebugRenderer;
import net.frozenblock.trailiertales.entity.render.model.ApparitionModel;
import net.frozenblock.trailiertales.entity.render.model.BoatBannerModel;
import net.frozenblock.trailiertales.entity.render.renderer.ApparitionRenderer;
import net.frozenblock.trailiertales.networking.packet.CoffinDebugPacket;
import net.frozenblock.trailiertales.networking.packet.CoffinRemoveDebugPacket;
import net.frozenblock.trailiertales.particle.GlowingColorBubbleParticle;
import net.frozenblock.trailiertales.particle.GlowingColorTransitionParticle;
import net.frozenblock.trailiertales.particle.GlowingSpellParticle;
import net.frozenblock.trailiertales.particle.provider.TrailierParticleProviders;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TrailierTalesClient implements ClientModInitializer {
	public static final ModelLayerLocation COFFIN_HEAD = new ModelLayerLocation(TTConstants.id("coffin_head"), "main");
	public static final ModelLayerLocation COFFIN_FOOT = new ModelLayerLocation(TTConstants.id("coffin_foot"), "main");
	public static final ModelLayerLocation APPARITION = new ModelLayerLocation(TTConstants.id("apparition"), "main");
	public static final ModelLayerLocation APPARITION_OVERLAY = new ModelLayerLocation(TTConstants.id("apparition"), "overlay");
	public static final ModelLayerLocation BOAT_BANNER = new ModelLayerLocation(TTConstants.id("boat"), "banner");

	@Override
	public void onInitializeClient() {
		SplashTextAPI.addSplashLocation(TTConstants.id("texts/splashes.txt"));
		addPanorama("catacombs");

		// TODO
		//BlockEntityWithoutLevelRendererRegistry.register(TTBlocks.COFFIN, TTBlockEntityTypes.COFFIN);

		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;
		renderLayerRegistry.putBlock(TTBlocks.POTTED_CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.CYAN_ROSE_CROP, RenderType.cutout());

		renderLayerRegistry.putBlock(TTBlocks.MANEDROP, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.MANEDROP_CROP, RenderType.cutout());

		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL_CROP, RenderType.cutout());

		BlockEntityRenderers.register(TTBlockEntityTypes.COFFIN, CoffinRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_HEAD, CoffinRenderer::createHeadLayer);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_FOOT, CoffinRenderer::createFootLayer);

		EntityRendererRegistry.register(TTEntityTypes.APPARITION, ApparitionRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(APPARITION, ApparitionModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(APPARITION_OVERLAY, ApparitionModel::createBodyLayer);

		EntityRendererRegistry.register(TTEntityTypes.THROWN_ITEM_PROJECTILE, ThrownItemRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(BOAT_BANNER, BoatBannerModel::createBodyLayer);

		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();
		particleRegistry.register(TTParticleTypes.COFFIN_SOUL, SoulParticle.EmissiveProvider::new);
		particleRegistry.register(TTParticleTypes.COFFIN_SOUL_ENTER, SoulParticle.EmissiveProvider::new);
		particleRegistry.register(TTParticleTypes.GLOWING_BUBBLE, GlowingColorBubbleParticle.Provider::new);
		particleRegistry.register(TTParticleTypes.GLOWING_ENTITY_EFFECT, GlowingSpellParticle.MobEffectProvider::new);
		particleRegistry.register(TTParticleTypes.GLOWING_DUST_COLOR_TRANSITION, GlowingColorTransitionParticle.Provider::new);
		particleRegistry.register(TTParticleTypes.SUSPICIOUS_CONNECTION, TrailierParticleProviders.SuspiciousConnectionProvider::new);
		particleRegistry.register(TTParticleTypes.SIEGE_OMEN, GlowingSpellParticle.Provider::new);
		particleRegistry.register(TTParticleTypes.TRANSFIGURING, GlowingSpellParticle.Provider::new);

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
		Panoramas.addPanorama(panoramaLocation);
	}
}
