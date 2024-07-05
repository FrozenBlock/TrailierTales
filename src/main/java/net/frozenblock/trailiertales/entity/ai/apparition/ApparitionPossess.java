package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class ApparitionPossess extends Behavior<Apparition> {
	private final double speedModifier;
	private int possessingTicks;

	@VisibleForTesting
	public ApparitionPossess(double speed) {
		super(
			ImmutableMap.of(
				RegisterMemoryModuleTypes.NEAREST_POSSESSABLE, MemoryStatus.VALUE_PRESENT,
				RegisterMemoryModuleTypes.POSSESSION_COOLDOWN, MemoryStatus.VALUE_ABSENT
			),
			300
		);
		this.speedModifier = speed;
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, @NotNull Apparition apparition) {
		return apparition.getPose() == Pose.STANDING && !apparition.isPassenger();
	}

	@Override
	protected boolean canStillUse(ServerLevel world, @NotNull Apparition apparition, long l) {
		return apparition.getBrain().hasMemoryValue(RegisterMemoryModuleTypes.NEAREST_POSSESSABLE) && !apparition.isPassenger();
	}

	@Override
	protected void start(ServerLevel world, @NotNull Apparition apparition, long l) {
		apparition.playSound(SoundEvents.BREEZE_INHALE, 1F, 1F);
	}

	@Override
	protected void stop(ServerLevel world, @NotNull Apparition apparition, long l) {
		this.possessingTicks = 0;
		apparition.getBrain().setMemoryWithExpiry(RegisterMemoryModuleTypes.POSSESSION_COOLDOWN, Unit.INSTANCE, 200L);
	}

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		LivingEntity possessionTarget = brain.getMemory(RegisterMemoryModuleTypes.NEAREST_POSSESSABLE).orElse(null);

		if (possessionTarget instanceof Mob mob) {
			apparition.getLookControl().setLookAt(possessionTarget, 30F, 30F);
			double AABBIncrease = Math.max(0.5D, this.possessingTicks / 10D);
			boolean isTouching = apparition.getBoundingBox().inflate(AABBIncrease).intersects(possessionTarget.getBoundingBox());
			double speed = this.speedModifier;
			if (isTouching) {
				this.possessingTicks++;
				apparition.spawnParticles(3, ParticleTypes.EFFECT);
				speed = 0.5D;
			} else {
				this.possessingTicks--;
			}
			apparition.getNavigation().moveTo(possessionTarget.getX(), possessionTarget.getEyeY(), possessionTarget.getZ(), speed);

			if (this.possessingTicks >= 10) {
				apparition.possessEntity(mob);
			}
		}
	}
}
