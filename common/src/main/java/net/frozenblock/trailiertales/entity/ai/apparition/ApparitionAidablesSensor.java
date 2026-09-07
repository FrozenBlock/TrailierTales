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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.frozenblock.lib.tag.api.ConventionalEntityTypeTags;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityTypes;
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
			TTMemoryModuleTypes.NEARBY_AIDABLES.get(),
			TTMemoryModuleTypes.NEAREST_AIDABLE.get()
		);
	}

	protected boolean isMatchingEntity(Apparition body, LivingEntity target, List<UUID> takenUUIDs) {
		return this.isClose(body, target)
			&& this.isAidable(body, target, takenUUIDs);
	}

	private boolean isAidable(Apparition body, LivingEntity entity, List<UUID> takenUUIDs) {
		final LivingEntity newTarget = body.getTarget();
		if (entity instanceof Mob mob
				&& newTarget != null
				&& mob.getType() != TTEntityTypes.APPARITION.get()
				&& !mob.getType().getCategory().isFriendly()
				&& !mob.is(ConventionalEntityTypeTags.BOSSES)
				&& !takenUUIDs.contains(mob.getUUID())
		) {
			final Brain<Apparition> brain = body.getBrain();
			if (brain.hasMemoryValue(TTMemoryModuleTypes.AIDING_TIME.get())) {
				final Optional<List<UUID>> trackingUUIDs = brain.getMemory(TTMemoryModuleTypes.AIDING_ENTITIES.get());
				if (trackingUUIDs.isPresent() && !trackingUUIDs.get().contains(mob.getUUID())) return false;
			}
			final LivingEntity currentTarget = mob.getTarget();
			return mob != body
				&& mob.isAlive()
				&& !mob.isSpectator()
				&& mob != currentTarget
				&& (currentTarget == null || currentTarget.getType() != EntityTypes.PLAYER);
		}
		return false;
	}

	private boolean isClose(Apparition body, LivingEntity target) {
		return target.distanceTo(body) <= body.getAttributeValue(Attributes.FOLLOW_RANGE);
	}

	@Override
	protected void doTick(ServerLevel level, Apparition body) {
		final Brain<?> brain = body.getBrain();
		final LivingEntity attackTarget = body.getTarget();
		if (attackTarget == null || !TTEntityConfig.APPARITION_HYPNOTIZES_MOBS.get()) {
			brain.setMemory(TTMemoryModuleTypes.NEARBY_AIDABLES.get(), new ArrayList<>());
			brain.eraseMemory(TTMemoryModuleTypes.NEAREST_AIDABLE.get());
			return;
		}

		final List<UUID> takenUUIDs = new ArrayList<>();
		level.getAllEntities().forEach(entity -> {
			if (!(entity instanceof Apparition otherApparition) || otherApparition == body) return;
			otherApparition.getBrain().getMemory(TTMemoryModuleTypes.AIDING_ENTITIES.get()).ifPresent(takenUUIDs::addAll);
		});

		final double range = body.getAttributeValue(Attributes.FOLLOW_RANGE);
		final AABB aABB = body.getBoundingBox().inflate(range, range, range);
		final List<LivingEntity> entities = level.getEntitiesOfClass(
			LivingEntity.class,
			aABB,
			livingEntity2 -> isMatchingEntity(body, livingEntity2, takenUUIDs)
		);
		entities.sort(Comparator.comparingDouble(body::distanceToSqr));
		brain.setMemory(TTMemoryModuleTypes.NEARBY_AIDABLES.get(), entities);
		brain.setMemory(TTMemoryModuleTypes.NEAREST_AIDABLE.get(), this.getNearestEntity(body));
	}

	private Optional<LivingEntity> getNearestEntity(Apparition body) {
		return body.getBrain().getMemory(TTMemoryModuleTypes.NEARBY_AIDABLES.get())
			.flatMap(entities -> this.findClosest(entities, livingEntity -> true));
	}

	private Optional<LivingEntity> findClosest(List<? extends LivingEntity> entities, Predicate<LivingEntity> predicate) {
		for (LivingEntity entity : entities) {
			if (predicate.test(entity)) return Optional.of(entity);
		}

		return Optional.empty();
	}
}
