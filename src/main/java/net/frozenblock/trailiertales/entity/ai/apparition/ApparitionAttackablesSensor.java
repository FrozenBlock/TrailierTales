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

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.frozenblock.trailiertales.tag.TTEntityTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class ApparitionAttackablesSensor extends NearestVisibleLivingEntitySensor {

	@Override
	protected boolean isMatchingEntity(LivingEntity entity, LivingEntity target) {
		return this.isClose(entity, target)
			&& this.isHostileTarget(target)
			&& Sensor.isEntityAttackableIgnoringLineOfSight(entity, target);
	}

	private boolean isHostileTarget(@NotNull LivingEntity entity) {
		return entity.getType().is(TTEntityTags.APPARITION_TARGETABLE);
	}

	private boolean isClose(LivingEntity apparition, @NotNull LivingEntity target) {
		return target.distanceTo(apparition) <= apparition.getAttributes().getValue(Attributes.FOLLOW_RANGE);
	}

	@Override
	protected void doTick(ServerLevel world, @NotNull LivingEntity entity) {
		entity.getBrain().setMemory(this.getMemory(), this.getNearestEntityNoLineOfSight(entity));
	}

	private Optional<LivingEntity> getNearestEntityNoLineOfSight(@NotNull LivingEntity entity) {
		return entity.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS)
			.flatMap(livingEntities -> this.findClosest(livingEntities, livingEntity -> this.isMatchingEntity(entity, livingEntity)));
	}

	private Optional<LivingEntity> findClosest(@NotNull List<? extends LivingEntity> livingEntities, Predicate<LivingEntity> predicate) {
		for (LivingEntity livingEntity : livingEntities) {
			if (predicate.test(livingEntity)) {
				return Optional.of(livingEntity);
			}
		}

		return Optional.empty();
	}

	@Override
	protected @NotNull MemoryModuleType<LivingEntity> getMemory() {
		return MemoryModuleType.NEAREST_ATTACKABLE;
	}
}
