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

package net.frozenblock.trailiertales.mixin.common.camel;

import java.util.Map;
import net.frozenblock.trailiertales.entity.spawn.TrailierSpawnPredicates;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnPlacements.class)
public class SpawnPlacementsMixin {
	@Shadow
	@Final
	private static Map<EntityType<?>, SpawnPlacements.Data> DATA_BY_TYPE;

	@Shadow
	public static <T extends Mob> void register(EntityType<T> type, SpawnPlacementType location, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
	}

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void trailierTales$addCamelSpawnIfPossible(CallbackInfo info) {
		if (!DATA_BY_TYPE.containsKey(EntityType.CAMEL)) {
			register(EntityType.CAMEL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TrailierSpawnPredicates::checkCamelSpawnRules);
		}
	}
}
