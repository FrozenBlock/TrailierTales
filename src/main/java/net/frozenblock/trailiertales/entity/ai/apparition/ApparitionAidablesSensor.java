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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.phys.AABB;

public class ApparitionAidablesSensor extends Sensor<Apparition> {

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return Set.of(
			TTMemoryModuleTypes.NEARBY_AIDABLES,
			TTMemoryModuleTypes.NEAREST_AIDABLE
		);
	}

	protected boolean isMatchingEntity(Apparition apparition, LivingEntity target, List<UUID> takenUUIDs) {
		return this.isClose(apparition, target)
			&& this.isAidable(apparition, target, takenUUIDs);
	}

	private boolean isAidable(Apparition apparition, LivingEntity entity, List<UUID> takenUUIDs) {
		final LivingEntity newTarget = apparition.getTarget();
		if (entity instanceof Mob mob
				&& newTarget != null
				&& mob.getType() != TTEntityTypes.APPARITION
				&& !mob.getType().getCategory().isFriendly()
				&& !mob.getType().is(ConventionalEntityTypeTags.BOSSES)
				&& !takenUUIDs.contains(mob.getUUID())
		) {
			final Brain<Apparition> brain = apparition.getBrain();
			if (brain.hasMemoryValue(TTMemoryModuleTypes.AIDING_TIME)) {
				final Optional<List<UUID>> trackingUUIDs = brain.getMemory(TTMemoryModuleTypes.AIDING_ENTITIES);
				if (trackingUUIDs.isPresent() && !trackingUUIDs.get().contains(mob.getUUID())) return false;
			}
			final LivingEntity currentTarget = mob.getTarget();
			return mob != apparition
				&& mob.isAlive()
				&& !mob.isSpectator()
				&& mob != currentTarget
				&& (currentTarget == null || currentTarget.getType() != EntityType.PLAYER);
		}
		return false;
	}

	private boolean isClose(Apparition apparition, LivingEntity target) {
		return target.distanceTo(apparition) <= apparition.getAttributeValue(Attributes.FOLLOW_RANGE);
	}

	@Override
	protected void doTick(ServerLevel level, Apparition apparition) {
		final Brain<?> brain = apparition.getBrain();
		final LivingEntity attackTarget = apparition.getTarget();
		if (attackTarget == null || !TTEntityConfig.get().apparition.hypnotizes_mobs) {
			brain.setMemory(TTMemoryModuleTypes.NEARBY_AIDABLES, new ArrayList<>());
			brain.eraseMemory(TTMemoryModuleTypes.NEAREST_AIDABLE);
			return;
		}

		final List<UUID> takenUUIDs = new ArrayList<>();
		level.getAllEntities().forEach(entity -> {
			if (!(entity instanceof Apparition otherApparition) || otherApparition == apparition) return;
			otherApparition.getBrain().getMemory(TTMemoryModuleTypes.AIDING_ENTITIES).ifPresent(takenUUIDs::addAll);
		});

		final double range = apparition.getAttributeValue(Attributes.FOLLOW_RANGE);
		final AABB aABB = apparition.getBoundingBox().inflate(range, range, range);
		final List<LivingEntity> entities = level.getEntitiesOfClass(
			LivingEntity.class,
			aABB,
			livingEntity2 -> isMatchingEntity(apparition, livingEntity2, takenUUIDs)
		);
		entities.sort(Comparator.comparingDouble(apparition::distanceToSqr));
		brain.setMemory(TTMemoryModuleTypes.NEARBY_AIDABLES, entities);
		brain.setMemory(TTMemoryModuleTypes.NEAREST_AIDABLE, this.getNearestEntity(apparition));
	}

	private Optional<LivingEntity> getNearestEntity(Apparition apparition) {
		return apparition.getBrain().getMemory(TTMemoryModuleTypes.NEARBY_AIDABLES)
			.flatMap(entities -> this.findClosest(entities, livingEntity -> true));
	}

	private Optional<LivingEntity> findClosest(List<? extends LivingEntity> livingEntities, Predicate<LivingEntity> predicate) {
		for (LivingEntity entity : livingEntities) {
			if (predicate.test(entity)) return Optional.of(entity);
		}

		return Optional.empty();
	}

}
