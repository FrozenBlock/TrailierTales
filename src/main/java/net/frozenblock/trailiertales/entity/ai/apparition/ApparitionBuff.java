package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Optional;

public class ApparitionBuff extends Behavior<Apparition> {
	private int chargingTicks;

	@VisibleForTesting
	public ApparitionBuff() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
				RegisterMemoryModuleTypes.AID_COOLDOWN, MemoryStatus.VALUE_ABSENT,
				RegisterMemoryModuleTypes.IS_AIDING, MemoryStatus.VALUE_ABSENT
			)
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, @NotNull Apparition apparition) {
		return apparition.getPose() == Pose.STANDING;
	}

	@Override
	protected boolean canStillUse(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		Optional<List<LivingEntity>> optional = brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES);
		return brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && (optional.isPresent() && !optional.get().isEmpty());
	}

	@Override
	protected void start(ServerLevel world, @NotNull Apparition apparition, long l) {
		apparition.playSound(SoundEvents.BREEZE_INHALE, 1F, 1F);
		apparition.getBrain().setMemory(RegisterMemoryModuleTypes.IS_AIDING, Unit.INSTANCE);
	}

	@Override
	protected void stop(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		apparition.setAggressive(false);
		brain.eraseMemory(RegisterMemoryModuleTypes.IS_AIDING);
		brain.setMemoryWithExpiry(RegisterMemoryModuleTypes.AID_COOLDOWN, Unit.INSTANCE, 200L);

		LivingEntity attackTarget = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (attackTarget != null) {
			List<LivingEntity> entities = brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of());
			for (LivingEntity livingEntity : entities) {
				if (livingEntity instanceof Mob mob) {
					if (mob.getTarget() != attackTarget && mob.getType() != RegisterEntities.APPARITION) {
						mob.setTarget(attackTarget);
						spawnParticles(world, livingEntity, apparition.getRandom().nextInt(9, 18), ParticleTypes.EFFECT);
					}
				}
			}
		}
	}

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		brain.setMemory(RegisterMemoryModuleTypes.IS_AIDING, Unit.INSTANCE);
	}

	private static void spawnParticles(@NotNull ServerLevel level, @NotNull LivingEntity entity, int count, ParticleOptions particleOptions) {
		level.sendParticles(
			particleOptions,
			entity.getX(),
			entity.getY(0.6666666666666666D),
			entity.getZ(),
			count,
			entity.getBbWidth() / 4F,
			entity.getBbHeight() / 4F,
			entity.getBbWidth() / 4F,
			0.05D
		);
	}
}
