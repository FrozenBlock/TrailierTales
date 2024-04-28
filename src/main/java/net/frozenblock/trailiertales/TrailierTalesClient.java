package net.frozenblock.trailiertales;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.frozenblock.trailiertales.block.render.CoffinRenderer;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TrailierTalesClient implements ClientModInitializer {

	public static final String BLANK_DECORATED_NAME = "decorated_pot_blank_side";
	public static final String BULLSEYE_POTTERY_PATTERN_NAME = "bullseye_pottery_pattern";
	public static final String WITHER_POTTERY_PATTERN_NAME = "wither_pottery_pattern";

	public static final ResourceKey<String> BLANK_DECORATED = ResourceKey.create(Registries.DECORATED_POT_PATTERNS, TrailierTalesSharedConstants.id(BLANK_DECORATED_NAME));
	public static final ResourceKey<String> BULLSEYE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERNS, TrailierTalesSharedConstants.id(BULLSEYE_POTTERY_PATTERN_NAME));
	public static final ResourceKey<String> WITHER_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERNS, TrailierTalesSharedConstants.id(WITHER_POTTERY_PATTERN_NAME));

	public static final ResourceLocation COFFIN_SHEET = TrailierTalesSharedConstants.id("textures/atlas/chest.png");
	private static final RenderType COFFIN_SHEET_TYPE = RenderType.entityCutout(COFFIN_SHEET);
	private static final RenderType COFFIN_SHEET_EYES_TYPE = RenderType.eyes(COFFIN_SHEET);

	public static final ModelLayerLocation COFFIN_HEAD = new ModelLayerLocation(TrailierTalesSharedConstants.id("coffin_head"), "main");
	public static final ModelLayerLocation COFFIN_FOOT = new ModelLayerLocation(TrailierTalesSharedConstants.id("coffin_foot"), "main");

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;

		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYAN_ROSE_CROP, RenderType.cutout());

		BlockEntityRenderers.register(RegisterBlockEntities.COFFIN, CoffinRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_HEAD, CoffinRenderer::createHeadLayer);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_FOOT, CoffinRenderer::createFootLayer);
	}
}
