package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.RegisterParticles;
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

public class ApparitionAid extends Behavior<Apparition> {

	@VisibleForTesting
	public ApparitionAid() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
				RegisterMemoryModuleTypes.AID_COOLDOWN, MemoryStatus.VALUE_ABSENT,
				RegisterMemoryModuleTypes.IS_AIDING, MemoryStatus.VALUE_ABSENT
			),
			80
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, @NotNull Apparition apparition) {
		return apparition.getPose() == Pose.STANDING;
	}

	@Override
	protected boolean canStillUse(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		Optional<LivingEntity> optional = brain.getMemory(RegisterMemoryModuleTypes.NEAREST_AIDABLE);
		return brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && (optional.isPresent());
	}

	@Override
	protected void start(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		apparition.playSound(SoundEvents.BREEZE_INHALE, 1F, 1F);
		brain.setMemoryWithExpiry(RegisterMemoryModuleTypes.IS_AIDING, Unit.INSTANCE, 60L);
	}

	@Override
	protected void stop(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		brain.setMemoryWithExpiry(RegisterMemoryModuleTypes.AID_COOLDOWN, Unit.INSTANCE, 200L);
		apparition.setAidAnimProgress(0F);
	}

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		List<LivingEntity> entities = brain.getMemory(RegisterMemoryModuleTypes.NEARBY_AIDABLES).orElse(ImmutableList.of());
		if (brain.hasMemoryValue(RegisterMemoryModuleTypes.IS_AIDING)) {
			entities.forEach(livingEntity -> spawnParticles(world, livingEntity, apparition.getRandom().nextInt(1, 2), RegisterParticles.AID_BUBBLE));
			apparition.setAidAnimProgress(1F);
		} else {
			brain.getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(attackTarget -> entities.forEach(livingEntity -> {
				if (livingEntity instanceof Mob mob) {
					mob.setTarget(attackTarget);
					spawnParticles(world, livingEntity, apparition.getRandom().nextInt(9, 18), ParticleTypes.EFFECT);
				}
			}));
			this.doStop(world, apparition, l);
		}
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
