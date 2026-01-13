/*
 * Copyright 2025-2026 FrozenBlock
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
import net.fabricmc.fabric.api.client.rendering.v1.ChunkSectionLayerMap;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

@Environment(EnvType.CLIENT)
public class TTBlockRenderLayers {

	public static void init() {
		ChunkSectionLayerMap.putBlock(TTBlocks.ECTOPLASM_BLOCK, ChunkSectionLayer.TRANSLUCENT);

		ChunkSectionLayerMap.putBlock(TTBlocks.SURVEYOR, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(TTBlocks.POTTED_CYAN_ROSE, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(TTBlocks.CYAN_ROSE, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(TTBlocks.CYAN_ROSE_CROP, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(TTBlocks.MANEDROP, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(TTBlocks.MANEDROP_CROP, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(TTBlocks.GUZMANIA, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(TTBlocks.GUZMANIA_CROP, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(TTBlocks.DAWNTRAIL, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(TTBlocks.DAWNTRAIL_CROP, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(TTBlocks.POTTED_LITHOPS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(TTBlocks.LITHOPS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(TTBlocks.LITHOPS_CROP, ChunkSectionLayer.CUTOUT);
	}
}
