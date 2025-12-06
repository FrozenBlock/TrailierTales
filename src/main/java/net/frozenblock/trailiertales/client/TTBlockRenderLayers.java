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

		renderLayerRegistry.putBlock(TTBlocks.GUZMANIA, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.GUZMANIA_CROP, RenderType.cutout());

		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL_CROP, RenderType.cutout());

		renderLayerRegistry.putBlock(TTBlocks.POTTED_LITHOPS, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.LITHOPS, RenderType.cutout());
		renderLayerRegistry.putBlock(TTBlocks.LITHOPS_CROP, RenderType.cutout());

	}
}
