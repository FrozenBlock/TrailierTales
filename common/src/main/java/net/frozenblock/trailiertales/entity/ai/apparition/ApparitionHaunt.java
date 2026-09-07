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

import com.google.common.collect.ImmutableMap;
import net.frozenblock.trailiertales.config.TTEntityConfig;
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

public class ApparitionHaunt extends Behavior<Apparition> {

	public ApparitionHaunt() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
			)
		);
	}

	@Override
	protected boolean canStillUse(ServerLevel level, Apparition body, long timestamp) {
		return body.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && TTEntityConfig.APPARITION_HAUNTS_PLAYERS.get();
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, Apparition body) {
		return super.checkExtraStartConditions(level, body) && TTEntityConfig.APPARITION_HAUNTS_PLAYERS.get();
	}

	@Override
	protected void tick(ServerLevel level, Apparition body, long timestamp) {
		final Brain<Apparition> brain = body.getBrain();
		final LivingEntity livingEntity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity == null) return;

		int hauntingTicks = brain.getMemory(TTMemoryModuleTypes.HAUNTING_TICKS.get()).orElse(0);
		if (livingEntity.getBoundingBox().intersects(body.getAttackBoundingBox(0D))) {
			hauntingTicks += 2;
			if (hauntingTicks >= 150) {
				body.playSound(TTSounds.APPARITION_HAUNT.get(), body.getSoundVolume(), body.getVoicePitch());
				hauntingTicks = 0;
				livingEntity.addEffect(
					new MobEffectInstance(
						TTMobEffects.HAUNT.asHolder(),
						600
					)
				);
			}
		} else {
			hauntingTicks = Math.max(0, hauntingTicks - 1);
		}

		brain.setMemory(TTMemoryModuleTypes.HAUNTING_TICKS.get(), hauntingTicks);
	}
}
