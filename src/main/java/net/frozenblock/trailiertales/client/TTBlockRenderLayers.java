package net.frozenblock.trailiertales.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class TTBlockRenderLayers {

	public static void init() {
		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;
		renderLayerRegistry.putBlock(TTBlocks.ECTOPLASM_BLOCK, RenderType.translucent());

		renderLayerRegistry.putBlock(TTBlocks.POTTED_CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.CYAN_ROSE, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.CYAN_ROSE_CROP, RenderType.cutout());

		renderLayerRegistry.putBlock(TTBlocks.MANEDROP, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.MANEDROP_CROP, RenderType.cutout());

		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL_CROP, RenderType.cutout());
	}
}
