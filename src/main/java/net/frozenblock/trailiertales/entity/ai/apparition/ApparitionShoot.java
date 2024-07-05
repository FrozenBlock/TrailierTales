package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class ApparitionShoot extends Behavior<Apparition> {
	private final double speedModifier;
	private int attackIntervalMin;
	private final double attackRadiusSqr;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;
	private int chargingTicks;

	@VisibleForTesting
	public ApparitionShoot(double speed, int attackInterval, double range) {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
			),
			240
		);
		this.speedModifier = speed;
		this.attackIntervalMin = attackInterval;
		this.attackRadiusSqr = range * range;
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, @NotNull Apparition apparition) {
		return apparition.getPose() == Pose.STANDING
			&& apparition.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).map(livingEntity -> isTargetWithinRange(apparition, livingEntity)).orElse(false);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, @NotNull Apparition apparition, long l) {
		return apparition.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			&& !apparition.getInventory().getItems().getFirst().isEmpty();
	}

	@Override
	protected void start(ServerLevel world, @NotNull Apparition apparition, long l) {
		apparition.playSound(SoundEvents.BREEZE_INHALE, 1F, 1F);
		apparition.setAggressive(true);
	}

	@Override
	protected void stop(ServerLevel world, @NotNull Apparition apparition, long l) {
		apparition.setAggressive(false);
		this.seeTime = 0;
		this.attackTime = -1;
		apparition.getBrain().setMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, 80);
	}

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		LivingEntity livingEntity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (livingEntity != null) {
			double distance = apparition.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
			boolean lineOfSight = apparition.getSensing().hasLineOfSight(livingEntity);
			boolean hasSeen = this.seeTime > 0;
			if (lineOfSight != hasSeen) {
				this.seeTime = 0;
			}

			if (lineOfSight) {
				this.seeTime++;
			} else {
				this.seeTime--;
			}

			if (!(distance > this.attackRadiusSqr) && this.seeTime >= 20) {
				apparition.getNavigation().stop();
				this.strafingTime++;
			} else {
				apparition.getNavigation().moveTo(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(), this.speedModifier);
				this.strafingTime = -1;
			}

			if (this.strafingTime >= 20) {
				if (apparition.getRandom().nextFloat() < 0.3F) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if (apparition.getRandom().nextFloat() < 0.3F) {
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (distance > this.attackRadiusSqr * 0.75F) {
					this.strafingBackwards = false;
				} else if (distance < this.attackRadiusSqr * 0.25F) {
					this.strafingBackwards = true;
				}

				apparition.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				if (apparition.getControlledVehicle() instanceof Mob mob) {
					mob.lookAt(livingEntity, 30F, 30F);
				}

				apparition.lookAt(livingEntity, 30F, 30F);
			} else {
				apparition.getLookControl().setLookAt(livingEntity, 30F, 30F);
			}

			if (this.chargingTicks > 0) {
				if (!lineOfSight && this.seeTime < -60) {
					this.chargingTicks = 0;
				} else if (lineOfSight) {
					if (this.chargingTicks++ >= 20) {
						this.chargingTicks = 0;
						apparition.performRangedAttack(livingEntity, 0.3F + (apparition.getRandom().nextFloat() * 1.4F));
						this.attackTime = this.attackIntervalMin;
					}
				}
			} else if (--this.attackTime <= 0 && this.seeTime >= -60) {
				this.chargingTicks++;
			}
		}
	}

	private static boolean isTargetWithinRange(@NotNull Apparition apparition, @NotNull LivingEntity target) {
		double d = apparition.position().distanceToSqr(target.position());
		return d > 4D && d < 256D;
	}
}
