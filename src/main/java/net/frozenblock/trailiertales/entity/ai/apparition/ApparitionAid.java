package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
				RegisterMemoryModuleTypes.AIDING_TIME, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED,
				RegisterMemoryModuleTypes.AIDING_ENTITIES, MemoryStatus.REGISTERED
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
		return brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			&& brain.hasMemoryValue(RegisterMemoryModuleTypes.NEAREST_AIDABLE)
			&& brain.hasMemoryValue(RegisterMemoryModuleTypes.AIDING_TIME);
	}

	@Override
	protected void start(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		apparition.playSound(SoundEvents.BREEZE_INHALE, 1F, 1F);
		brain.setMemory(RegisterMemoryModuleTypes.AIDING_TIME, 61);
		List<UUID> trackingUUIDs = new ArrayList<>();
		brain.getMemory(RegisterMemoryModuleTypes.NEARBY_AIDABLES).ifPresent(nearbyAidables -> {
			nearbyAidables.forEach(aidable -> trackingUUIDs.add(aidable.getUUID()));
		});
		if (!trackingUUIDs.isEmpty()) {
			brain.setMemory(RegisterMemoryModuleTypes.AIDING_ENTITIES, trackingUUIDs);
		}
	}

	@Override
	protected void stop(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		brain.setMemoryWithExpiry(RegisterMemoryModuleTypes.AID_COOLDOWN, Unit.INSTANCE, 200L);
		brain.eraseMemory(RegisterMemoryModuleTypes.AIDING_ENTITIES);
		apparition.setAidAnimProgress(0F);
	}

	public static final ParticleOptions BUBBLE_PARTICLE = ColorParticleOption.create(RegisterParticles.COLORABLE_BUBBLE, 162F / 255F, 181F/ 255F, 217F / 255F);
	public static final ParticleOptions EFFECT_PARTICLE = ColorParticleOption.create(RegisterParticles.GLOWING_ENTITY_EFFECT, 162F / 255F, 181F/ 255F, 217F / 255F);

	@Override
	protected void tick(ServerLevel world, @NotNull Apparition apparition, long l) {
		Brain<Apparition> brain = apparition.getBrain();
		List<LivingEntity> entities = brain.getMemory(RegisterMemoryModuleTypes.NEARBY_AIDABLES).orElse(ImmutableList.of());
		List<UUID> trackingUUIDs = new ArrayList<>();
		entities.forEach(aidable -> trackingUUIDs.add(aidable.getUUID()));
		if (!trackingUUIDs.isEmpty()) {
			brain.setMemory(RegisterMemoryModuleTypes.AIDING_ENTITIES, trackingUUIDs);
		} else {
			this.doStop(world, apparition, l);
			return;
		}

		int aidingTime = brain.getMemory(RegisterMemoryModuleTypes.AIDING_TIME).orElse(0);
		if (aidingTime > 1) {
			entities.forEach(livingEntity -> spawnParticles(world, livingEntity, apparition.getRandom().nextInt(1, 2), BUBBLE_PARTICLE));
			apparition.setAidAnimProgress(1F);
			LivingEntity nearestAidable = brain.getMemory(RegisterMemoryModuleTypes.NEAREST_AIDABLE).orElse(null);
			if (nearestAidable != null) {
				brain.eraseMemory(MemoryModuleType.WALK_TARGET);
				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				apparition.getNavigation().moveTo(nearestAidable.getX(), nearestAidable.getEyeY() + 0.5D, nearestAidable.getZ(), 0, 1.25D);
			}
		} else if (aidingTime == 1) {
			brain.getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(attackTarget -> entities.forEach(livingEntity -> {
				if (livingEntity instanceof Mob mob) {
					mob.setTarget(attackTarget);
					spawnParticles(world, livingEntity, apparition.getRandom().nextInt(9, 18), EFFECT_PARTICLE);
				}
			}));
			this.doStop(world, apparition, l);
		} else {
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
