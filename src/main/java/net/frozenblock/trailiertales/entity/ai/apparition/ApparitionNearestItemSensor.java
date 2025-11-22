/*
 * Copyright 2025 FrozenBlock
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

public class ApparitionNearestItemSensor extends Sensor<Apparition> {
	private static final double RADIUS = 24D;
	private static final double Y_RANGE = 24D;

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
	}

	@Override
	protected void doTick(ServerLevel level, Apparition apparition) {
		final Brain<?> brain = apparition.getBrain();
		final List<ItemEntity> list = level.getEntitiesOfClass(ItemEntity.class, apparition.getBoundingBox().inflate(RADIUS, Y_RANGE, RADIUS), itemEntity -> true);
		list.sort(Comparator.comparingDouble(apparition::distanceToSqr));
		final Optional<ItemEntity> optionalItemEntity = list.stream()
			.filter(item -> apparition.wantsToPickUp(level, item))
			.filter(itemEntity -> itemEntity.closerThan(apparition, RADIUS))
			.findFirst();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, optionalItemEntity);
	}
}
