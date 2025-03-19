/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.client.model.ApparitionModel;
import net.frozenblock.trailiertales.client.model.BoatBannerModel;
import net.frozenblock.trailiertales.client.model.CoffinModel;
import net.frozenblock.trailiertales.client.renderer.blockentity.CoffinRenderer;
import net.frozenblock.trailiertales.client.renderer.entity.ApparitionRenderer;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

@Environment(EnvType.CLIENT)
public class TTModelLayers {
	public static final ModelLayerLocation COFFIN_HEAD = new ModelLayerLocation(TTConstants.id("coffin_head"), "main");
	public static final ModelLayerLocation COFFIN_FOOT = new ModelLayerLocation(TTConstants.id("coffin_foot"), "main");
	public static final ModelLayerLocation APPARITION = new ModelLayerLocation(TTConstants.id("apparition"), "main");
	public static final ModelLayerLocation APPARITION_OVERLAY = new ModelLayerLocation(TTConstants.id("apparition"), "overlay");
	public static final ModelLayerLocation BOAT_BANNER = new ModelLayerLocation(TTConstants.id("boat"), "banner");

	public static void init() {
		BlockEntityRenderers.register(TTBlockEntityTypes.COFFIN, CoffinRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_HEAD, CoffinModel::createLayerDefinition);
		EntityModelLayerRegistry.registerModelLayer(COFFIN_FOOT, CoffinModel::createLayerDefinition);

		EntityRendererRegistry.register(TTEntityTypes.APPARITION, ApparitionRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(APPARITION, ApparitionModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(APPARITION_OVERLAY, ApparitionModel::createBodyLayer);

		EntityRendererRegistry.register(TTEntityTypes.THROWN_ITEM_PROJECTILE, ThrownItemRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(BOAT_BANNER, BoatBannerModel::createBodyLayer);
	}
}
