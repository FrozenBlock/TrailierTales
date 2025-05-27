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
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

@Environment(EnvType.CLIENT)
public class TTBlockRenderLayers {

	public static void init() {
		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;
		renderLayerRegistry.putBlock(TTBlocks.ECTOPLASM_BLOCK, ChunkSectionLayer.TRANSLUCENT);

		renderLayerRegistry.putBlock(TTBlocks.POTTED_CYAN_ROSE, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(TTBlocks.CYAN_ROSE, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(TTBlocks.CYAN_ROSE_CROP, ChunkSectionLayer.CUTOUT);

		renderLayerRegistry.putBlock(TTBlocks.MANEDROP, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(TTBlocks.MANEDROP_CROP, ChunkSectionLayer.CUTOUT);

		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(TTBlocks.DAWNTRAIL_CROP, ChunkSectionLayer.CUTOUT);
	}
}
