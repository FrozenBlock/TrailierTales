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
import org.jetbrains.annotations.NotNull;

public class ApparitionAidablesSensor extends Sensor<Apparition> {

	@Override
	public @NotNull Set<MemoryModuleType<?>> requires() {
		return Set.of(
			TTMemoryModuleTypes.NEARBY_AIDABLES,
			TTMemoryModuleTypes.NEAREST_AIDABLE
		);
	}

	protected boolean isMatchingEntity(Apparition apparition, LivingEntity target, List<UUID> takenUUIDs) {
		return this.isClose(apparition, target)
			&& this.isAidable(apparition, target, takenUUIDs);
	}

	private boolean isAidable(@NotNull Apparition apparition, @NotNull LivingEntity entity, List<UUID> takenUUIDs) {
		LivingEntity newTarget = apparition.getTarget();
		if (
			entity instanceof Mob mob
				&& newTarget != null
				&& mob.getType() != TTEntityTypes.APPARITION
				&& !mob.getType().getCategory().isFriendly()
				&& !mob.getType().is(ConventionalEntityTypeTags.BOSSES)
				&& !takenUUIDs.contains(mob.getUUID())
		) {
			Brain<Apparition> brain = apparition.getBrain();
			if (brain.hasMemoryValue(TTMemoryModuleTypes.AIDING_TIME)) {
				Optional<List<UUID>> trackingUUIDs = brain.getMemory(TTMemoryModuleTypes.AIDING_ENTITIES);
				if (trackingUUIDs.isPresent() && !trackingUUIDs.get().contains(mob.getUUID())) {
					return false;
				}
			}
			LivingEntity currentTarget = mob.getTarget();
			return mob != apparition
				&& mob.isAlive()
				&& !mob.isSpectator()
				&& mob != currentTarget
				&& (currentTarget == null || currentTarget.getType() != EntityType.PLAYER);
		}
		return false;
	}

	private boolean isClose(Apparition apparition, @NotNull LivingEntity target) {
		return target.distanceTo(apparition) <= apparition.getAttributeValue(Attributes.FOLLOW_RANGE);
	}

	@Override
	protected void doTick(@NotNull ServerLevel world, @NotNull Apparition apparition) {
		Brain<?> brain = apparition.getBrain();
		LivingEntity attackTarget = apparition.getTarget();
		if (attackTarget != null) {
			List<UUID> takenUUIDs = new ArrayList<>();
			world.getAllEntities().forEach(
				entity -> {
					if (entity instanceof Apparition otherApparition && otherApparition != apparition) {
						otherApparition.getBrain().getMemory(TTMemoryModuleTypes.AIDING_ENTITIES).ifPresent(takenUUIDs::addAll);
					}
				}
			);

			double range = apparition.getAttributeValue(Attributes.FOLLOW_RANGE);
			AABB aABB = apparition.getBoundingBox().inflate(range, range, range);
			List<LivingEntity> list = world.getEntitiesOfClass(
				LivingEntity.class,
				aABB,
				livingEntity2 -> isMatchingEntity(apparition, livingEntity2, takenUUIDs)
			);
			list.sort(Comparator.comparingDouble(apparition::distanceToSqr));
			brain.setMemory(TTMemoryModuleTypes.NEARBY_AIDABLES, list);
			brain.setMemory(TTMemoryModuleTypes.NEAREST_AIDABLE, this.getNearestEntity(apparition));
		} else {
			brain.setMemory(TTMemoryModuleTypes.NEARBY_AIDABLES, new ArrayList<>());
			brain.eraseMemory(TTMemoryModuleTypes.NEAREST_AIDABLE);
		}
	}

	private Optional<LivingEntity> getNearestEntity(@NotNull Apparition apparition) {
		return apparition.getBrain().getMemory(TTMemoryModuleTypes.NEARBY_AIDABLES)
			.flatMap(livingEntities -> this.findClosest(livingEntities, livingEntity -> true));
	}

	private Optional<LivingEntity> findClosest(@NotNull List<? extends LivingEntity> livingEntities, Predicate<LivingEntity> predicate) {
		for (LivingEntity livingEntity : livingEntities) {
			if (predicate.test(livingEntity)) {
				return Optional.of(livingEntity);
			}
		}

		return Optional.empty();
	}

}
