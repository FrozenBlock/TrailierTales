package net.frozenblock.trailiertales;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.lib.block.api.entity.BlockEntityWithoutLevelRendererRegistry;
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
import net.frozenblock.trailiertales.registry.TTBlockEntities;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEntities;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

@Environment(EnvType.CLIENT)
public class TrailierTalesClient implements ClientModInitializer {
	public static final String BLANK_DECORATED_NAME = "decorated_pot_blank_side";
	public static final String BAIT_POTTERY_PATTERN_NAME = "bait_pottery_pattern";
	public static final String BLOOM_POTTERY_PATTERN_NAME = "bloom_pottery_pattern";
	public static final String BOLT_POTTERY_PATTERN_NAME = "bolt_pottery_pattern";
	public static final String BULLSEYE_POTTERY_PATTERN_NAME = "bullseye_pottery_pattern";
	public static final String CLUCK_POTTERY_PATTERN_NAME = "cluck_pottery_pattern";
	public static final String CRAWL_POTTERY_PATTERN_NAME = "crawl_pottery_pattern";
	public static final String CRESCENT_POTTERY_PATTERN_NAME = "crescent_pottery_pattern";
	public static final String CULTIVATOR_POTTERY_PATTERN_NAME = "cultivator_pottery_pattern";
	public static final String DROUGHT_POTTERY_PATTERN_NAME = "drought_pottery_pattern";
	public static final String ESSENCE_POTTERY_PATTERN_NAME = "essence_pottery_pattern";
	public static final String EYE_POTTERY_PATTERN_NAME = "eye_pottery_pattern";
	public static final String FOCUS_POTTERY_PATTERN_NAME = "focus_pottery_pattern";
	public static final String HEIGHT_POTTERY_PATTERN_NAME = "height_pottery_pattern";
	public static final String HUMP_POTTERY_PATTERN_NAME = "hump_pottery_pattern";
	public static final String ILLUMINATOR_POTTERY_PATTERN_NAME = "illuminator_pottery_pattern";
	public static final String INCIDENCE_POTTERY_PATTERN_NAME = "incidence_pottery_pattern";
	public static final String LUMBER_POTTERY_PATTERN_NAME = "lumber_pottery_pattern";
	public static final String NAVIGATOR_POTTERY_PATTERN_NAME = "navigator_pottery_pattern";
	public static final String NEEDLES_POTTERY_PATTERN_NAME = "needles_pottery_pattern";
	public static final String PLUME_POTTERY_PATTERN_NAME = "plume_pottery_pattern";
	public static final String PROTECTION_POTTERY_PATTERN_NAME = "protection_pottery_pattern";
	public static final String SHED_POTTERY_PATTERN_NAME = "shed_pottery_pattern";
	public static final String SHINE_POTTERY_PATTERN_NAME = "shine_pottery_pattern";
	public static final String SHOWER_POTTERY_PATTERN_NAME = "shower_pottery_pattern";
	public static final String SPADE_POTTERY_PATTERN_NAME = "spade_pottery_pattern";
	public static final String SPROUT_POTTERY_PATTERN_NAME = "sprout_pottery_pattern";
	public static final String VESSEL_POTTERY_PATTERN_NAME = "vessel_pottery_pattern";
	public static final String WITHER_POTTERY_PATTERN_NAME = "wither_pottery_pattern";

	public static final ResourceKey<DecoratedPotPattern> BLANK_DECORATED = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(BLANK_DECORATED_NAME));
	public static final ResourceKey<DecoratedPotPattern> BAIT_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(BAIT_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> BLOOM_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(BLOOM_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> BOLT_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(BOLT_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> BULLSEYE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(BULLSEYE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> CLUCK_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(CLUCK_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> CRAWL_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(CRAWL_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> CRESCENT_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(CRESCENT_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> CULTIVATOR_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(CULTIVATOR_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> DROUGHT_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(DROUGHT_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> ESSENCE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(ESSENCE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> EYE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(EYE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> FOCUS_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(FOCUS_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> HEIGHT_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(HEIGHT_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> HUMP_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(HUMP_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> ILLUMINATOR_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(ILLUMINATOR_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> INCIDENCE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(INCIDENCE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> LUMBER_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(LUMBER_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> NAVIGATOR_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(NAVIGATOR_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> NEEDLES_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(NEEDLES_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> PLUME_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(PLUME_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> PROTECTION_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(PROTECTION_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> SHED_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(SHED_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> SHINE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(SHINE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> SHOWER_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(SHOWER_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> SPADE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(SPADE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> SPROUT_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(SPROUT_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> VESSEL_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(VESSEL_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> WITHER_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TTConstants.id(WITHER_POTTERY_PATTERN_NAME));

	public static final ModelLayerLocation COFFIN_HEAD = new ModelLayerLocation(TTConstants.id("coffin_head"), "main");
	public static final ModelLayerLocation COFFIN_FOOT = new ModelLayerLocation(TTConstants.id("coffin_foot"), "main");
	public static final ModelLayerLocation APPARITION = new ModelLayerLocation(TTConstants.id("apparition"), "main");
	public static final ModelLayerLocation APPARITION_OVERLAY = new ModelLayerLocation(TTConstants.id("apparition"), "overlay");
	public static final ModelLayerLocation BOAT_BANNER = new ModelLayerLocation(TTConstants.id("boat"), "banner");

	@Override
	public void onInitializeClient() {
		SplashTextAPI.addSplashLocation(TTConstants.id("texts/splashes.txt"));
		addPanorama("catacombs");

		BlockEntityWithoutLevelRendererRegistry.register(TTBlocks.COFFIN, TTBlockEntities.COFFIN);

		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;
		renderLayerRegistry.putBlock(TTBlocks.POTTED_CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.CYAN_ROSE_CROP, RenderType.cutout());

		renderLayerRegistry.putBlock(TTBlocks.MANEDROP, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.MANEDROP_CROP, RenderType.cutout());

		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL_CROP, RenderType.cutout());

		BlockEntityRenderers.register(TTBlockEntities.COFFIN, CoffinRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_HEAD, CoffinRenderer::createHeadLayer);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_FOOT, CoffinRenderer::createFootLayer);

		EntityRendererRegistry.register(TTEntities.APPARITION, ApparitionRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(APPARITION, ApparitionModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(APPARITION_OVERLAY, ApparitionModel::createBodyLayer);

		EntityRendererRegistry.register(TTEntities.THROWN_ITEM_PROJECTILE, ThrownItemRenderer::new);

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
