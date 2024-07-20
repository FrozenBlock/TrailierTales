package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.RegisterMobEffects;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class ApparitionHaunt extends Behavior<Apparition> {

	@VisibleForTesting
	public ApparitionHaunt() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
			)
		);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, @NotNull Apparition apparition, long l) {
		return apparition.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
	}

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		LivingEntity livingEntity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (livingEntity != null) {
			int hauntingTicks = brain.getMemory(RegisterMemoryModuleTypes.HAUNTING_TICKS).orElse(0);

			if (livingEntity.getBoundingBox().intersects(apparition.getAttackBoundingBox())) {
				hauntingTicks += 2;
				if (hauntingTicks >= 150) {
					apparition.playSound(RegisterSounds.APPARITION_AID, 1F, apparition.getVoicePitch());
					hauntingTicks = 0;
					livingEntity.addEffect(
						new MobEffectInstance(
							RegisterMobEffects.HAUNT,
							600
						)
					);
				}
			} else {
				hauntingTicks = Math.max(0, hauntingTicks - 1);
			}

			brain.setMemory(RegisterMemoryModuleTypes.HAUNTING_TICKS, hauntingTicks);
		}
	}
}
