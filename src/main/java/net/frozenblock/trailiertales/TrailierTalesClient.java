package net.frozenblock.trailiertales;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

@Environment(EnvType.CLIENT)
public class TrailierTalesClient implements ClientModInitializer {

	public static final String BLANK_DECORATED_NAME = "decorated_pot_blank_side";
	public static final String BULLSEYE_POTTERY_PATTERN_NAME = "bullseye_pottery_pattern";

	public static final ResourceKey<String> BLANK_DECORATED = ResourceKey.create(Registries.DECORATED_POT_PATTERNS, TrailierTalesSharedConstants.id(BLANK_DECORATED_NAME));
	public static final ResourceKey<String> BULLSEYE_POTTERY_PATTERN = ResourceKey.create(Registries.DECORATED_POT_PATTERNS, TrailierTalesSharedConstants.id(BULLSEYE_POTTERY_PATTERN_NAME));

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;

		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYAN_ROSE_CROP, RenderType.cutout());
	}
}
