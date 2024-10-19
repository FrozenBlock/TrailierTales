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
	protected boolean isMatchingEntity(ServerLevel level, LivingEntity entity, LivingEntity target) {
		return this.isClose(entity, target)
			&& this.isHostileTarget(target)
			&& Sensor.isEntityAttackableIgnoringLineOfSight(level, entity, target);
	}

	private boolean isHostileTarget(@NotNull LivingEntity entity) {
		return entity.getType().is(TTEntityTags.APPARITION_TARGETABLE);
	}

	private boolean isClose(LivingEntity apparition, @NotNull LivingEntity target) {
		return target.distanceTo(apparition) <= apparition.getAttributes().getValue(Attributes.FOLLOW_RANGE);
	}

	@Override
	protected void doTick(ServerLevel level, @NotNull LivingEntity entity) {
		entity.getBrain().setMemory(this.getMemory(), this.getNearestEntityNoLineOfSight(level, entity));
	}

	private Optional<LivingEntity> getNearestEntityNoLineOfSight(ServerLevel level, @NotNull LivingEntity entity) {
		return entity.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS)
			.flatMap(livingEntities -> this.findClosest(livingEntities, livingEntity -> this.isMatchingEntity(level, entity, livingEntity)));
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
