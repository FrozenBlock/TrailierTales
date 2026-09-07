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

import net.frozenblock.lib.renderer.entity.EntityRendererRegistry;
import net.frozenblock.lib.renderer.model.ModelLayerRegistry;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.client.model.monster.apparition.ApparitionModel;
import net.frozenblock.trailiertales.client.model.object.boat.BoatBannerModel;
import net.frozenblock.trailiertales.client.model.object.coffin.CoffinModel;
import net.frozenblock.trailiertales.client.renderer.blockentity.CoffinRenderer;
import net.frozenblock.trailiertales.client.renderer.entity.ApparitionRenderer;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

@ClientOnly
public final class TTModelLayers {
	public static final ModelLayerLocation COFFIN_HEAD = new ModelLayerLocation(TTConstants.id("coffin_head"), "main");
	public static final ModelLayerLocation COFFIN_FOOT = new ModelLayerLocation(TTConstants.id("coffin_foot"), "main");
	public static final ModelLayerLocation APPARITION = new ModelLayerLocation(TTConstants.id("apparition"), "main");
	public static final ModelLayerLocation APPARITION_OVERLAY = new ModelLayerLocation(TTConstants.id("apparition"), "overlay");
	public static final ModelLayerLocation BOAT_BANNER_FLAG = new ModelLayerLocation(TTConstants.id("boat"), "banner_flag");
	public static final ModelLayerLocation BOAT_BANNER_STAND = new ModelLayerLocation(TTConstants.id("boat"), "banner_stand");

	public static void init() {
		ModelLayerRegistry.register(COFFIN_HEAD, CoffinModel::createLayerDefinition);
		ModelLayerRegistry.register(COFFIN_FOOT, CoffinModel::createLayerDefinition);

		ModelLayerRegistry.register(APPARITION, ApparitionModel::createBodyLayer);
		ModelLayerRegistry.register(APPARITION_OVERLAY, ApparitionModel::createBodyLayer);

		ModelLayerRegistry.register(BOAT_BANNER_FLAG, BoatBannerModel::createFlagLayer);
		ModelLayerRegistry.register(BOAT_BANNER_STAND, BoatBannerModel::createStandLayer);
	}

	/**
	 * Registries MUST be populated before this. Runs during NeoForge's setup event.
	 */
	public static void setup() {
		BlockEntityRenderers.register(TTBlockEntityTypes.COFFIN.get(), CoffinRenderer::new);

		EntityRendererRegistry.register(TTEntityTypes.APPARITION.get(), ApparitionRenderer::new);

		EntityRendererRegistry.register(TTEntityTypes.THROWN_ITEM_PROJECTILE.get(), ThrownItemRenderer::new);
	}

	private TTModelLayers() {}
}
