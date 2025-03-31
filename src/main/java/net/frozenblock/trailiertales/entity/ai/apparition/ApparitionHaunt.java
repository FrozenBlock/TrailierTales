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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTSounds;
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
			int hauntingTicks = brain.getMemory(TTMemoryModuleTypes.HAUNTING_TICKS).orElse(0);

			if (livingEntity.getBoundingBox().intersects(apparition.getAttackBoundingBox())) {
				hauntingTicks += 2;
				if (hauntingTicks >= 150) {
					apparition.playSound(TTSounds.APPARITION_HAUNT, apparition.getSoundVolume(), apparition.getVoicePitch());
					hauntingTicks = 0;
					livingEntity.addEffect(
						new MobEffectInstance(
							TTMobEffects.HAUNT,
							600
						)
					);
				}
			} else {
				hauntingTicks = Math.max(0, hauntingTicks - 1);
			}

			brain.setMemory(TTMemoryModuleTypes.HAUNTING_TICKS, hauntingTicks);
		}
	}
}
