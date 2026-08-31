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

import net.frozenblock.lib.renderer.RenderStateDataKey;
import net.frozenblock.trailiertales.TTConstants;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

@ClientOnly
public class TTRenderStateDataKeys {
	public static final RenderStateDataKey<Float> BOAT_WALK_ANIMATION_POS = RenderStateDataKey.create(
		TTConstants.id("boat_walk_animation_pos")
	);
	public static final RenderStateDataKey<Float> BOAT_WALK_ANIMATION_SPEED = RenderStateDataKey.create(
		TTConstants.id("boat_walk_animation_speed")
	);
	public static final RenderStateDataKey<DyeColor> BOAT_BANNER_BASE_COLOR = RenderStateDataKey.create(
		TTConstants.id("boat_banner_base_color")
	);
	public static final RenderStateDataKey<BannerPatternLayers> BOAT_BANNER_PATTERNS = RenderStateDataKey.create(
		TTConstants.id("boat_banner_patterns")
	);

	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_X_OFFSET = RenderStateDataKey.create(
		TTConstants.id("brushable_block_x_offset")
	);
	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_Y_OFFSET = RenderStateDataKey.create(
		TTConstants.id("brushable_block_y_offset")
	);
	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_Z_OFFSET = RenderStateDataKey.create(
		TTConstants.id("brushable_block_z_offset")
	);
	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_ROTATION = RenderStateDataKey.create(
		TTConstants.id("brushable_block_rotation")
	);
	public static final RenderStateDataKey<Float> BRUSHABLE_BLOCK_ITEM_SCALE = RenderStateDataKey.create(
		TTConstants.id("brushable_block_item_scale")
	);

	public static final RenderStateDataKey<Boolean> DECORATED_POT_WOBBLE_FLIPPED = RenderStateDataKey.create(
		TTConstants.id("decorated_pot_wobble_flipped")
	);

	public static void init() {
	}
}
