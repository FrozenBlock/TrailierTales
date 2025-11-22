/*
 * Copyright 2025 FrozenBlock
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ApparitionAid extends Behavior<Apparition> {

	@VisibleForTesting
	public ApparitionAid() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
				TTMemoryModuleTypes.AID_COOLDOWN, MemoryStatus.VALUE_ABSENT,
				TTMemoryModuleTypes.AIDING_TIME, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED,
				TTMemoryModuleTypes.AIDING_ENTITIES, MemoryStatus.REGISTERED
			),
			80
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, Apparition apparition) {
		return apparition.getBrain().hasMemoryValue(TTMemoryModuleTypes.NEAREST_AIDABLE) && !apparition.isHiding();
	}

	@Override
	protected boolean canStillUse(ServerLevel level, Apparition apparition, long l) {
		final Brain<Apparition> brain = apparition.getBrain();
		return brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			&& brain.hasMemoryValue(TTMemoryModuleTypes.NEAREST_AIDABLE)
			&& brain.hasMemoryValue(TTMemoryModuleTypes.AIDING_TIME);
	}

	@Override
	protected void start(ServerLevel level, Apparition apparition, long l) {
		final Brain<Apparition> brain = apparition.getBrain();
		apparition.playSound(TTSounds.APPARITION_AID, apparition.getSoundVolume(), apparition.getVoicePitch());
		brain.setMemory(TTMemoryModuleTypes.AIDING_TIME, 61);
		final List<UUID> trackingUUIDs = new ArrayList<>();
		brain.getMemory(TTMemoryModuleTypes.NEARBY_AIDABLES).ifPresent(nearbyAidables -> {
			nearbyAidables.forEach(aidable -> trackingUUIDs.add(aidable.getUUID()));
		});
		if (!trackingUUIDs.isEmpty()) brain.setMemory(TTMemoryModuleTypes.AIDING_ENTITIES, trackingUUIDs);
	}

	@Override
	protected void stop(ServerLevel level, Apparition apparition, long l) {
		final Brain<Apparition> brain = apparition.getBrain();
		brain.setMemoryWithExpiry(TTMemoryModuleTypes.AID_COOLDOWN, Unit.INSTANCE, 200L);
		brain.eraseMemory(TTMemoryModuleTypes.AIDING_ENTITIES);
		apparition.setAidAnimProgress(0F);
	}

	public static final ParticleOptions BUBBLE_PARTICLE = ColorParticleOption.create(TTParticleTypes.GLOWING_BUBBLE, 162F / 255F, 181F/ 255F, 217F / 255F);
	public static final ParticleOptions EFFECT_PARTICLE = ColorParticleOption.create(TTParticleTypes.GLOWING_ENTITY_EFFECT, 162F / 255F, 181F/ 255F, 217F / 255F);

	@Override
	protected void tick(ServerLevel level, Apparition apparition, long l) {
		final Brain<Apparition> brain = apparition.getBrain();
		final List<LivingEntity> entities = brain.getMemory(TTMemoryModuleTypes.NEARBY_AIDABLES).orElse(ImmutableList.of());
		final List<UUID> trackingUUIDs = new ArrayList<>();
		entities.forEach(aidable -> trackingUUIDs.add(aidable.getUUID()));
		if (trackingUUIDs.isEmpty()) {
			this.doStop(level, apparition, l);
			return;
		}
		brain.setMemory(TTMemoryModuleTypes.AIDING_ENTITIES, trackingUUIDs);

		final int aidingTime = brain.getMemory(TTMemoryModuleTypes.AIDING_TIME).orElse(0);
		if (aidingTime > 1) {
			entities.forEach(livingEntity -> spawnParticles(level, livingEntity, apparition.getRandom().nextInt(1, 2), BUBBLE_PARTICLE));
			apparition.setAidAnimProgress(1F);
			final LivingEntity nearestAidable = brain.getMemory(TTMemoryModuleTypes.NEAREST_AIDABLE).orElse(null);
			if (nearestAidable != null) {
				brain.eraseMemory(MemoryModuleType.WALK_TARGET);
				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				apparition.getNavigation().moveTo(nearestAidable.getX(), nearestAidable.getEyeY() + 0.5D, nearestAidable.getZ(), 0, 1.25D);
			}
			return;
		} else if (aidingTime == 1) {
			brain.getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(attackTarget -> entities.forEach(livingEntity -> {
				if (!(livingEntity instanceof Mob mob)) return;
				mob.setTarget(attackTarget);
				spawnParticles(level, livingEntity, apparition.getRandom().nextInt(9, 18), EFFECT_PARTICLE);
			}));
		}
		this.doStop(level, apparition, l);
	}

	private static void spawnParticles(ServerLevel level, LivingEntity entity, int count, ParticleOptions options) {
		level.sendParticles(
			options,
			entity.getX(), entity.getY(0.6666666666666666D), entity.getZ(),
			count,
			entity.getBbWidth() / 4F, entity.getBbHeight() / 4F, entity.getBbWidth() / 4F,
			0.05D
		);
	}
}
