package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.RegisterParticles;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
				RegisterMemoryModuleTypes.IS_AIDING, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED
			),
			80
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, @NotNull Apparition apparition) {
		return apparition.getBrain().hasMemoryValue(RegisterMemoryModuleTypes.NEAREST_AIDABLE);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		return brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && brain.hasMemoryValue(RegisterMemoryModuleTypes.NEAREST_AIDABLE);
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

	public static final ParticleOptions BUBBLE_PARTICLE = ColorParticleOption.create(RegisterParticles.COLORABLE_BUBBLE, 162F / 255F, 181F/ 255F, 217F / 255F);
	public static final ParticleOptions EFFECT_PARTICLE = ColorParticleOption.create(RegisterParticles.GLOWING_ENTITY_EFFECT, 162F / 255F, 181F/ 255F, 217F / 255F);

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		List<LivingEntity> entities = brain.getMemory(RegisterMemoryModuleTypes.NEARBY_AIDABLES).orElse(ImmutableList.of());
		if (brain.hasMemoryValue(RegisterMemoryModuleTypes.IS_AIDING)) {
			entities.forEach(livingEntity -> spawnParticles(world, livingEntity, apparition.getRandom().nextInt(1, 2), BUBBLE_PARTICLE));
			apparition.setAidAnimProgress(1F);
			LivingEntity nearestAidable = brain.getMemory(RegisterMemoryModuleTypes.NEAREST_AIDABLE).orElse(null);
			if (nearestAidable != null) {
				brain.eraseMemory(MemoryModuleType.WALK_TARGET);
				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				apparition.getNavigation().moveTo(nearestAidable.getX(), nearestAidable.getEyeY() + 0.5D, nearestAidable.getZ(), 0, 1.25D);
			}
		} else {
			brain.getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(attackTarget -> entities.forEach(livingEntity -> {
				if (livingEntity instanceof Mob mob) {
					mob.setTarget(attackTarget);
					spawnParticles(world, livingEntity, apparition.getRandom().nextInt(9, 18), EFFECT_PARTICLE);
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
