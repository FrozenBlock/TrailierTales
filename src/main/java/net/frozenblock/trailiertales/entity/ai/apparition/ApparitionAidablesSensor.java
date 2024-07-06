package net.frozenblock.trailiertales.entity.ai.apparition;

import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class ApparitionAidablesSensor extends Sensor<Apparition> {

	@Override
	public @NotNull Set<MemoryModuleType<?>> requires() {
		return Set.of(
			RegisterMemoryModuleTypes.NEARBY_AIDABLES,
			RegisterMemoryModuleTypes.NEAREST_AIDABLE
		);
	}

	protected boolean isMatchingEntity(Apparition apparition, LivingEntity target) {
		return this.isClose(apparition, target)
			&& this.isAidable(apparition, target);
	}

	private boolean isAidable(@NotNull Apparition apparition, @NotNull LivingEntity entity) {
		LivingEntity newTarget = apparition.getTarget();
		if (entity instanceof Mob mob && newTarget != null && mob.getType() != RegisterEntities.APPARITION) {
			LivingEntity currentTarget = mob.getTarget();
			return currentTarget == null || currentTarget.getType() != EntityType.PLAYER;
		}
		return false;
	}

	private boolean isClose(Apparition apparition, @NotNull LivingEntity target) {
		return target.distanceTo(apparition) <= apparition.getAttributeValue(Attributes.FOLLOW_RANGE);
	}

	@Override
	protected void doTick(@NotNull ServerLevel world, @NotNull Apparition apparition) {
		double range = apparition.getAttributeValue(Attributes.FOLLOW_RANGE);
		AABB aABB = apparition.getBoundingBox().inflate(range, range, range);
		List<LivingEntity> list = world.getEntitiesOfClass(
			LivingEntity.class,
			aABB,
			livingEntity2 -> livingEntity2 != apparition
				&& livingEntity2.isAlive()
				&& !livingEntity2.isSpectator()
				&& !(livingEntity2 instanceof Player player && player.isCreative())
		);
		list.sort(Comparator.comparingDouble(apparition::distanceToSqr));
		Brain<?> brain = apparition.getBrain();
		brain.setMemory(RegisterMemoryModuleTypes.NEARBY_AIDABLES, list);
		apparition.getBrain().setMemory(RegisterMemoryModuleTypes.NEAREST_AIDABLE, this.getNearestEntity(apparition));
	}

	private Optional<LivingEntity> getNearestEntity(@NotNull Apparition apparition) {
		return apparition.getBrain().getMemory(RegisterMemoryModuleTypes.NEARBY_AIDABLES)
			.flatMap(livingEntities -> this.findClosest(livingEntities, livingEntity -> this.isMatchingEntity(apparition, livingEntity)));
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
