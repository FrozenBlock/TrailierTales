package net.frozenblock.trailiertales;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.lib.block.api.entity.BlockEntityWithoutLevelRendererRegistry;
import net.frozenblock.trailiertales.block.render.CoffinRenderer;
import net.frozenblock.trailiertales.entity.render.model.ApparitionModel;
import net.frozenblock.trailiertales.entity.render.renderer.ApparitionRenderer;
import net.frozenblock.trailiertales.particle.ColorableBubbleParticle;
import net.frozenblock.trailiertales.particle.GlowingColorTransitionParticle;
import net.frozenblock.trailiertales.particle.GlowingSpellParticle;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.frozenblock.trailiertales.registry.RegisterParticles;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

@Environment(EnvType.CLIENT)
public class TrailierTalesClient implements ClientModInitializer {

	public static final String BLANK_DECORATED_NAME = "decorated_pot_blank_side";
	public static final String BULLSEYE_POTTERY_PATTERN_NAME = "bullseye_pottery_pattern";
	public static final String WITHER_POTTERY_PATTERN_NAME = "wither_pottery_pattern";
	public static final String BLOOM_POTTERY_PATTERN_NAME = "bloom_pottery_pattern.png";
	public static final String INCIDENCE_POTTERY_PATTERN_NAME = "incidence_pottery_pattern";
	public static final String CULTIVATOR_POTTERY_PATTERN_NAME = "cultivator_pottery_pattern.png";
	public static final String SPADE_POTTERY_PATTERN_NAME = "spade_pottery_pattern";

	public static final ResourceKey<DecoratedPotPattern> BLANK_DECORATED = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TrailierConstants.id(BLANK_DECORATED_NAME));
	public static final ResourceKey<DecoratedPotPattern> BULLSEYE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TrailierConstants.id(BULLSEYE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> WITHER_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TrailierConstants.id(WITHER_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> BLOOM_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TrailierConstants.id(BLOOM_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> INCIDENCE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TrailierConstants.id(INCIDENCE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> CULTIVATOR_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TrailierConstants.id(CULTIVATOR_POTTERY_PATTERN_NAME));
	public static final ResourceKey<DecoratedPotPattern> SPADE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERN, TrailierConstants.id(SPADE_POTTERY_PATTERN_NAME));

	public static final ModelLayerLocation COFFIN_HEAD = new ModelLayerLocation(TrailierConstants.id("coffin_head"), "main");
	public static final ModelLayerLocation COFFIN_FOOT = new ModelLayerLocation(TrailierConstants.id("coffin_foot"), "main");
	public static final ModelLayerLocation APPARITION = new ModelLayerLocation(TrailierConstants.id("apparition"), "main");
	public static final ModelLayerLocation APPARITION_OVERLAY = new ModelLayerLocation(TrailierConstants.id("apparition"), "overlay");

	@Override
	public void onInitializeClient() {
		BlockEntityWithoutLevelRendererRegistry.register(RegisterBlocks.COFFIN, RegisterBlockEntities.COFFIN);

		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYAN_ROSE_CROP, RenderType.cutout());

		BlockEntityRenderers.register(RegisterBlockEntities.COFFIN, CoffinRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_HEAD, CoffinRenderer::createHeadLayer);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_FOOT, CoffinRenderer::createFootLayer);

		EntityRendererRegistry.register(RegisterEntities.APPARITION, ApparitionRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(APPARITION, ApparitionModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(APPARITION_OVERLAY, ApparitionModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.DAMAGING_THROWABLE_ITEM_PROJECTILE, ThrownItemRenderer::new);

		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();
		particleRegistry.register(RegisterParticles.COFFIN_SOUL, SoulParticle.EmissiveProvider::new);
		particleRegistry.register(RegisterParticles.COFFIN_SOUL_ENTER, SoulParticle.EmissiveProvider::new);
		particleRegistry.register(RegisterParticles.COLORABLE_BUBBLE, ColorableBubbleParticle.Provider::new);
		particleRegistry.register(RegisterParticles.GLOWING_ENTITY_EFFECT, GlowingSpellParticle.MobEffectProvider::new);
		particleRegistry.register(RegisterParticles.GLOWING_DUST_COLOR_TRANSITION, GlowingColorTransitionParticle.Provider::new);
	}
}
