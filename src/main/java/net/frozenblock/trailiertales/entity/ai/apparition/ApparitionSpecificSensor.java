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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class ApparitionSpecificSensor extends Sensor<LivingEntity> {

	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(TTMemoryModuleTypes.NEARBY_APPARITIONS, MemoryModuleType.NEAREST_LIVING_ENTITIES);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull LivingEntity entity) {
		Brain<?> brain = entity.getBrain();
		ArrayList<Apparition> apparitions = Lists.newArrayList();
		List<LivingEntity> entities = brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of());
		for (LivingEntity livingEntity : entities) {
			if (livingEntity instanceof Apparition apparition) apparitions.add(apparition);
		}
		brain.setMemory(TTMemoryModuleTypes.NEARBY_APPARITIONS, apparitions);
	}
}
