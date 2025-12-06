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
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

@Environment(EnvType.CLIENT)
public class TTBlockRenderLayers {

	public static void init() {
		BlockRenderLayerMap.putBlock(TTBlocks.ECTOPLASM_BLOCK, ChunkSectionLayer.TRANSLUCENT);

		BlockRenderLayerMap.putBlock(TTBlocks.SURVEYOR, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(TTBlocks.POTTED_CYAN_ROSE, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(TTBlocks.CYAN_ROSE, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(TTBlocks.CYAN_ROSE_CROP, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(TTBlocks.MANEDROP, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(TTBlocks.MANEDROP_CROP, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(TTBlocks.GUZMANIA, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(TTBlocks.GUZMANIA_CROP, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(TTBlocks.DAWNTRAIL, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(TTBlocks.DAWNTRAIL_CROP, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(TTBlocks.POTTED_LITHOPS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(TTBlocks.LITHOPS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(TTBlocks.LITHOPS_CROP, ChunkSectionLayer.CUTOUT);
	}
}
