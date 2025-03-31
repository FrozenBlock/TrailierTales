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

package net.frozenblock.trailiertales.entity.spawn;

import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

public class TrailierSpawnPredicates {
	public static boolean checkCamelSpawnRules(
		EntityType<? extends LivingEntity> entityType,
		@NotNull ServerLevelAccessor world,
		MobSpawnType reason,
		@NotNull BlockPos spawnPos,
		RandomSource random
	) {
		boolean bl = MobSpawnType.ignoresLightRequirements(reason) || world.getRawBrightness(spawnPos, 0) > 8;
		return world.getBlockState(spawnPos.below()).is(TTBlockTags.CAMEL_SPAWNABLE_ON) && bl && random.nextFloat() <= 0.1F;
	}
}
