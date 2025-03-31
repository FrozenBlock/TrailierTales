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

package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.NotNull;

public class ApparitionNearestItemSensor extends Sensor<Apparition> {
	private static final double RADIUS = 24D;
	private static final double Y_RANGE = 24D;

	@Override
	public @NotNull Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull Apparition apparition) {
		Brain<?> brain = apparition.getBrain();
		List<ItemEntity> list = level.getEntitiesOfClass(ItemEntity.class, apparition.getBoundingBox().inflate(RADIUS, Y_RANGE, RADIUS), itemEntity -> true);
		list.sort(Comparator.comparingDouble(apparition::distanceToSqr));
		Optional<ItemEntity> optional = list.stream()
			.filter(item -> apparition.wantsToPickUp(level, item))
			.filter(itemEntity -> itemEntity.closerThan(apparition, RADIUS))
			.findFirst();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, optional);
	}
}
