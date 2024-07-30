package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class ApparitionShoot extends Behavior<Apparition> {

	@VisibleForTesting
	public ApparitionShoot() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
				RegisterMemoryModuleTypes.SEE_TIME, MemoryStatus.REGISTERED,
				RegisterMemoryModuleTypes.STRAFING_CLOCKWISE, MemoryStatus.REGISTERED,
				RegisterMemoryModuleTypes.STRAFING_BACKWARDS, MemoryStatus.REGISTERED,
				RegisterMemoryModuleTypes.STRAFING_TIME, MemoryStatus.REGISTERED,
				RegisterMemoryModuleTypes.CHARGING_TICKS, MemoryStatus.REGISTERED
			),
			240
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, @NotNull Apparition apparition) {
		return !apparition.getInventory().getItems().getFirst().isEmpty() && !apparition.isHiding()
			&& apparition.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).map(livingEntity -> isTargetWithinRange(apparition, livingEntity)).orElse(false);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, @NotNull Apparition apparition, long l) {
		return apparition.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			&& !apparition.getInventory().getItems().getFirst().isEmpty();
	}

	@Override
	protected void start(ServerLevel world, @NotNull Apparition apparition, long l) {
		apparition.playSound(RegisterSounds.APPARITION_AID, 1F, apparition.getVoicePitch());
		apparition.setAggressive(true);
		apparition.setPoltergeistAnimProgress(1F);
	}

	@Override
	protected void stop(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		apparition.setAggressive(false);
		brain.eraseMemory(RegisterMemoryModuleTypes.SEE_TIME);
		brain.eraseMemory(RegisterMemoryModuleTypes.STRAFING_CLOCKWISE);
		brain.eraseMemory(RegisterMemoryModuleTypes.STRAFING_BACKWARDS);
		brain.eraseMemory(RegisterMemoryModuleTypes.STRAFING_TIME);
		brain.eraseMemory(RegisterMemoryModuleTypes.CHARGING_TICKS);
		apparition.setPoltergeistAnimProgress(0F);
		apparition.getBrain().setMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, 300);
	}

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		LivingEntity livingEntity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (livingEntity != null) {
			double distance = apparition.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
			boolean lineOfSight = apparition.getSensing().hasLineOfSight(livingEntity);
			boolean hasSeen = brain.hasMemoryValue(RegisterMemoryModuleTypes.SEE_TIME);
			if (lineOfSight != hasSeen) {
				brain.eraseMemory(RegisterMemoryModuleTypes.SEE_TIME);
			}

			int seeTime = brain.getMemory(RegisterMemoryModuleTypes.SEE_TIME).orElse(0);
			if (lineOfSight) {
				seeTime += 1;
			} else {
				seeTime -= 1;
			}
			brain.setMemory(RegisterMemoryModuleTypes.SEE_TIME, seeTime);

			int strafeTime = brain.getMemory(RegisterMemoryModuleTypes.STRAFING_TIME).orElse(-1);
			if (!(distance > 256D) && seeTime >= 20) {
				brain.eraseMemory(MemoryModuleType.WALK_TARGET);
				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				apparition.getNavigation().stop();
				strafeTime += 1;
				brain.setMemory(RegisterMemoryModuleTypes.STRAFING_TIME, strafeTime);
			} else {
				apparition.getNavigation().moveTo(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(), 1D);
				brain.eraseMemory(MemoryModuleType.WALK_TARGET);
				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				brain.eraseMemory(RegisterMemoryModuleTypes.STRAFING_TIME);
			}

			if (brain.getMemory(RegisterMemoryModuleTypes.STRAFING_TIME).orElse(0) >= 20) {
				if (apparition.getRandom().nextFloat() < 0.3F) {
					brain.getMemory(RegisterMemoryModuleTypes.STRAFING_CLOCKWISE)
						.ifPresentOrElse(
							unit -> brain.eraseMemory(RegisterMemoryModuleTypes.STRAFING_CLOCKWISE),
							() -> brain.setMemory(RegisterMemoryModuleTypes.STRAFING_CLOCKWISE, Unit.INSTANCE)
						);
					brain.eraseMemory(RegisterMemoryModuleTypes.CHARGING_TICKS);
				}

				if (apparition.getRandom().nextFloat() < 0.3F) {
					brain.getMemory(RegisterMemoryModuleTypes.STRAFING_BACKWARDS)
						.ifPresentOrElse(
							unit -> brain.eraseMemory(RegisterMemoryModuleTypes.STRAFING_BACKWARDS),
							() -> brain.setMemory(RegisterMemoryModuleTypes.STRAFING_BACKWARDS, Unit.INSTANCE)
						);
				}

				strafeTime = 0;
				brain.setMemory(RegisterMemoryModuleTypes.STRAFING_TIME, strafeTime);
			}

			if (strafeTime > -1) {
				if (distance > 256D * 0.75F) {
					brain.eraseMemory(RegisterMemoryModuleTypes.STRAFING_BACKWARDS);
				} else if (distance < 256D * 0.25F) {
					brain.setMemory(RegisterMemoryModuleTypes.STRAFING_BACKWARDS, Unit.INSTANCE);
				}

				apparition.getMoveControl().strafe(
					brain.hasMemoryValue(RegisterMemoryModuleTypes.STRAFING_BACKWARDS) ? -0.5F : 0.5F,
					brain.hasMemoryValue(RegisterMemoryModuleTypes.STRAFING_CLOCKWISE) ? 0.5F : -0.5F
				);
				if (apparition.getControlledVehicle() instanceof Mob mob) {
					brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
					mob.lookAt(livingEntity, 30F, 30F);
				}

				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				apparition.lookAt(livingEntity, 30F, 30F);
			} else {
				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				apparition.getLookControl().setLookAt(livingEntity, 30F, 30F);
			}

			int chargingTicks = brain.getMemory(RegisterMemoryModuleTypes.CHARGING_TICKS).orElse(0);
			if (chargingTicks > 0) {
				if (!lineOfSight && seeTime < -60) {
					chargingTicks = 0;
				} else if (lineOfSight) {
					if (chargingTicks++ >= 20) {
						chargingTicks = 0;
						apparition.performRangedAttack(livingEntity, 0.3F + (apparition.getRandom().nextFloat() * 1.4F));
						this.doStop(world, apparition, l);
					}
				}
			} else if (seeTime >= -60) {
				chargingTicks++;
			}
			brain.setMemory(RegisterMemoryModuleTypes.CHARGING_TICKS, chargingTicks);
		}
	}

	private static boolean isTargetWithinRange(@NotNull Apparition apparition, @NotNull LivingEntity target) {
		double d = apparition.position().distanceToSqr(target.position());
		return d > 4D && d < 256D;
	}
}
