/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.frozenblock.lib.block.api.entity.BlockEntityWithoutLevelRendererRegistry;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class TTBlockRenderLayers {

	public static void init() {
		BlockEntityWithoutLevelRendererRegistry.register(TTBlocks.COFFIN, TTBlockEntityTypes.COFFIN);

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
