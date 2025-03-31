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

package net.frozenblock.trailiertales;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

public class TTFeatureFlags {
	public static final FeatureFlag TRAILIER_TALES = FeatureFlagApi.builder.create(TTConstants.id(TTConstants.MOD_ID));
	public static final FeatureFlagSet TRAILIER_TALES_FLAG_SET = FeatureFlagSet.of(TRAILIER_TALES);

	public static final FeatureFlag FEATURE_FLAG = FrozenBools.IS_DATAGEN ? TRAILIER_TALES : FeatureFlags.VANILLA;

	public static void init() {
	}
}
