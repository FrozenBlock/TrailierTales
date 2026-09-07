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

package net.frozenblock.trailiertales.entity.ai.apparition;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.frozenblock.trailiertales.tag.TTEntityTypeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class ApparitionAttackablesSensor extends Sensor<LivingEntity> {

	protected boolean isMatchingEntity(ServerLevel level, LivingEntity body, LivingEntity target) {
		return this.isClose(body, target)
			&& this.isHostileTarget(target)
			&& Sensor.isEntityAttackableIgnoringLineOfSight(level, body, target);
	}

	private boolean isHostileTarget(LivingEntity target) {
		return target.is(TTEntityTypeTags.APPARITION_TARGETABLE);
	}

	private boolean isClose(LivingEntity body, LivingEntity target) {
		return target.distanceTo(body) <= body.getAttributes().getValue(Attributes.FOLLOW_RANGE);
	}

	@Override
	protected void doTick(ServerLevel level, LivingEntity body) {
		body.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, this.getNearestEntityNoLineOfSight(level, body));
	}

	private Optional<LivingEntity> getNearestEntityNoLineOfSight(ServerLevel level, LivingEntity body) {
		return body.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS)
			.flatMap(entities -> this.findClosest(entities, entity -> this.isMatchingEntity(level, body, entity)));
	}

	private Optional<LivingEntity> findClosest(List<? extends LivingEntity> entities, Predicate<LivingEntity> predicate) {
		for (LivingEntity livingEntity : entities) {
			if (predicate.test(livingEntity)) return Optional.of(livingEntity);
		}

		return Optional.empty();
	}

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return Set.of(MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.NEAREST_PLAYERS);
	}
}
