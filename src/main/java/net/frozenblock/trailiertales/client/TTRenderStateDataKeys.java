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
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

@Environment(EnvType.CLIENT)
public class TTRenderStateDataKeys {
	public static final RenderStateDataKey<Float> BOAT_WALK_ANIMATION_POS = RenderStateDataKey.create();
	public static final RenderStateDataKey<Float> BOAT_WALK_ANIMATION_SPEED = RenderStateDataKey.create();
	public static final RenderStateDataKey<DyeColor> BOAT_BANNER_BASE_COLOR = RenderStateDataKey.create();
	public static final RenderStateDataKey<BannerPatternLayers> BOAT_BANNER_PATTERNS = RenderStateDataKey.create();

	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_X_OFFSET = RenderStateDataKey.create();
	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_Y_OFFSET = RenderStateDataKey.create();
	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_Z_OFFSET = RenderStateDataKey.create();
	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_ROTATION = RenderStateDataKey.create();
	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_ITEM_SCALE = RenderStateDataKey.create();

	public static final RenderStateDataKey<Boolean> DECORATED_POT_WOBBLE_FLIPPED = RenderStateDataKey.create();

	public static void init() {
	}
}
