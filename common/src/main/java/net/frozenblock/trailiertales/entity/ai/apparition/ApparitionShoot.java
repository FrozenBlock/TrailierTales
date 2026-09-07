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
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ApparitionShoot extends Behavior<Apparition> {

	public ApparitionShoot() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
				TTMemoryModuleTypes.SEE_TIME.get(), MemoryStatus.REGISTERED,
				TTMemoryModuleTypes.STRAFING_CLOCKWISE.get(), MemoryStatus.REGISTERED,
				TTMemoryModuleTypes.STRAFING_BACKWARDS.get(), MemoryStatus.REGISTERED,
				TTMemoryModuleTypes.STRAFING_TIME.get(), MemoryStatus.REGISTERED,
				TTMemoryModuleTypes.CHARGING_TICKS.get(), MemoryStatus.REGISTERED
			),
			240
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, Apparition body) {
		return !body.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && !body.isHiding()
			&& body.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).map(livingEntity -> isTargetWithinRange(body, livingEntity)).orElse(false);
	}

	@Override
	protected boolean canStillUse(ServerLevel level, Apparition body, long timestamp) {
		return body.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			&& !body.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
	}

	@Override
	protected void start(ServerLevel level, Apparition body, long timestamp) {
		body.playSound(TTSounds.APPARITION_HOLDING_ITEM.get(), body.getSoundVolume(), body.getVoicePitch());
		body.setAggressive(true);
		body.setPoltergeistAnimProgress(1F);
	}

	@Override
	protected void stop(ServerLevel level, Apparition body, long timestamp) {
		final Brain<Apparition> brain = body.getBrain();
		body.setAggressive(false);
		brain.eraseMemory(TTMemoryModuleTypes.SEE_TIME.get());
		brain.eraseMemory(TTMemoryModuleTypes.STRAFING_CLOCKWISE.get());
		brain.eraseMemory(TTMemoryModuleTypes.STRAFING_BACKWARDS.get());
		brain.eraseMemory(TTMemoryModuleTypes.STRAFING_TIME.get());
		brain.eraseMemory(TTMemoryModuleTypes.CHARGING_TICKS.get());
		body.setPoltergeistAnimProgress(0F);
		body.getBrain().setMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, 500);
	}

	@Override
	protected void tick(ServerLevel level, Apparition body, long timestamp) {
		final Brain<Apparition> brain = body.getBrain();
		final LivingEntity livingEntity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity == null) return;

		final double distance = body.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
		final boolean lineOfSight = body.getSensing().hasLineOfSight(livingEntity);
		final boolean hasSeen = brain.hasMemoryValue(TTMemoryModuleTypes.SEE_TIME.get());
		if (lineOfSight != hasSeen) brain.eraseMemory(TTMemoryModuleTypes.SEE_TIME.get());

		final int seeTime = brain.getMemory(TTMemoryModuleTypes.SEE_TIME.get()).orElse(0) + (lineOfSight ? 1 : -1);
		brain.setMemory(TTMemoryModuleTypes.SEE_TIME.get(), seeTime);

		int strafeTime = brain.getMemory(TTMemoryModuleTypes.STRAFING_TIME.get()).orElse(-1);
		if (!(distance > 256D) && seeTime >= 20) {
			brain.eraseMemory(MemoryModuleType.WALK_TARGET);
			brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
			body.getNavigation().stop();
			strafeTime += 1;
			brain.setMemory(TTMemoryModuleTypes.STRAFING_TIME.get(), strafeTime);
		} else {
			body.getNavigation().moveTo(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(), 1D);
			brain.eraseMemory(MemoryModuleType.WALK_TARGET);
			brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
			brain.eraseMemory(TTMemoryModuleTypes.STRAFING_TIME.get());
		}

		if (brain.getMemory(TTMemoryModuleTypes.STRAFING_TIME.get()).orElse(0) >= 20) {
			if (body.getRandom().nextFloat() < 0.3F) {
				brain.getMemory(TTMemoryModuleTypes.STRAFING_CLOCKWISE.get())
					.ifPresentOrElse(
						unit -> brain.eraseMemory(TTMemoryModuleTypes.STRAFING_CLOCKWISE.get()),
						() -> brain.setMemory(TTMemoryModuleTypes.STRAFING_CLOCKWISE.get(), Unit.INSTANCE)
					);
				brain.eraseMemory(TTMemoryModuleTypes.CHARGING_TICKS.get());
			}

			if (body.getRandom().nextFloat() < 0.3F) {
				brain.getMemory(TTMemoryModuleTypes.STRAFING_BACKWARDS.get())
					.ifPresentOrElse(
						unit -> brain.eraseMemory(TTMemoryModuleTypes.STRAFING_BACKWARDS.get()),
						() -> brain.setMemory(TTMemoryModuleTypes.STRAFING_BACKWARDS.get(), Unit.INSTANCE)
					);
			}

			strafeTime = 0;
			brain.setMemory(TTMemoryModuleTypes.STRAFING_TIME.get(), strafeTime);
		}

		if (strafeTime > -1) {
			if (distance > 256D * 0.75F) {
				brain.eraseMemory(TTMemoryModuleTypes.STRAFING_BACKWARDS.get());
			} else if (distance < 256D * 0.25F) {
				brain.setMemory(TTMemoryModuleTypes.STRAFING_BACKWARDS.get(), Unit.INSTANCE);
			}

			body.getMoveControl().strafe(
				brain.hasMemoryValue(TTMemoryModuleTypes.STRAFING_BACKWARDS.get()) ? -0.5F : 0.5F,
				brain.hasMemoryValue(TTMemoryModuleTypes.STRAFING_CLOCKWISE.get()) ? 0.5F : -0.5F
			);
			if (body.getControlledVehicle() instanceof Mob mob) {
				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				mob.lookAt(livingEntity, 30F, 30F);
			}

			brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
			body.lookAt(livingEntity, 30F, 30F);
		} else {
			brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
			body.getLookControl().setLookAt(livingEntity, 30F, 30F);
		}

		int chargingTicks = brain.getMemory(TTMemoryModuleTypes.CHARGING_TICKS.get()).orElse(0);
		if (chargingTicks > 0) {
			if (!lineOfSight && seeTime < -60) {
				chargingTicks = 0;
			} else if (lineOfSight) {
				if (chargingTicks++ >= 20) {
					chargingTicks = 0;
					body.performRangedAttack(livingEntity, 0.3F + (body.getRandom().nextFloat() * 1.4F));
					this.doStop(level, body, timestamp);
				}
			}
		} else if (seeTime >= -60) {
			chargingTicks++;
		}
		brain.setMemory(TTMemoryModuleTypes.CHARGING_TICKS.get(), chargingTicks);
	}

	private static boolean isTargetWithinRange(Apparition body, LivingEntity target) {
		final double distance = body.position().distanceToSqr(target.position());
		return distance > 4D && distance < 256D;
	}
}
