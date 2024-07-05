package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

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
		return apparition.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
	}

	@Override
	protected void start(ServerLevel world, @NotNull Apparition apparition, long l) {
		apparition.playSound(SoundEvents.BREEZE_INHALE, 1F, 1F);
		apparition.getBrain().setMemory(RegisterMemoryModuleTypes.IS_AIDING, Unit.INSTANCE);
	}

	@Override
	protected void stop(ServerLevel world, @NotNull Apparition apparition, long l) {
		apparition.setAggressive(false);
		apparition.getBrain().eraseMemory(RegisterMemoryModuleTypes.IS_AIDING);
	}

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		LivingEntity livingEntity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (livingEntity != null) {

		}
	}
}
