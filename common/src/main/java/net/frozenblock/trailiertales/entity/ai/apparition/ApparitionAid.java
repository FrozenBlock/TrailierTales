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

	public ApparitionAid() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
				TTMemoryModuleTypes.AID_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT,
				TTMemoryModuleTypes.AIDING_TIME.get(), MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED,
				TTMemoryModuleTypes.AIDING_ENTITIES.get(), MemoryStatus.REGISTERED
			),
			80
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, Apparition body) {
		return body.getBrain().hasMemoryValue(TTMemoryModuleTypes.NEAREST_AIDABLE.get()) && !body.isHiding();
	}

	@Override
	protected boolean canStillUse(ServerLevel level, Apparition body, long timestamp) {
		final Brain<Apparition> brain = body.getBrain();
		return brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			&& brain.hasMemoryValue(TTMemoryModuleTypes.NEAREST_AIDABLE.get())
			&& brain.hasMemoryValue(TTMemoryModuleTypes.AIDING_TIME.get());
	}

	@Override
	protected void start(ServerLevel level, Apparition body, long timestamp) {
		final Brain<Apparition> brain = body.getBrain();
		body.playSound(TTSounds.APPARITION_AID.get(), body.getSoundVolume(), body.getVoicePitch());
		brain.setMemory(TTMemoryModuleTypes.AIDING_TIME.get(), 61);
		final List<UUID> trackingUUIDs = new ArrayList<>();
		brain.getMemory(TTMemoryModuleTypes.NEARBY_AIDABLES.get()).ifPresent(nearbyAidables -> {
			nearbyAidables.forEach(aidable -> trackingUUIDs.add(aidable.getUUID()));
		});
		if (!trackingUUIDs.isEmpty()) brain.setMemory(TTMemoryModuleTypes.AIDING_ENTITIES.get(), trackingUUIDs);
	}

	@Override
	protected void stop(ServerLevel level, Apparition body, long timestamp) {
		final Brain<Apparition> brain = body.getBrain();
		brain.setMemoryWithExpiry(TTMemoryModuleTypes.AID_COOLDOWN.get(), Unit.INSTANCE, 200L);
		brain.eraseMemory(TTMemoryModuleTypes.AIDING_ENTITIES.get());
		body.setAidAnimProgress(0F);
	}

	public static final ParticleOptions BUBBLE_PARTICLE = ColorParticleOption.create(TTParticleTypes.GLOWING_BUBBLE.get(), 162F / 255F, 181F/ 255F, 217F / 255F);
	public static final ParticleOptions EFFECT_PARTICLE = ColorParticleOption.create(TTParticleTypes.GLOWING_ENTITY_EFFECT.get(), 162F / 255F, 181F/ 255F, 217F / 255F);

	@Override
	protected void tick(ServerLevel level, Apparition body, long timestamp) {
		final Brain<Apparition> brain = body.getBrain();
		final List<LivingEntity> entities = brain.getMemory(TTMemoryModuleTypes.NEARBY_AIDABLES.get()).orElse(ImmutableList.of());
		final List<UUID> trackingUUIDs = new ArrayList<>();
		entities.forEach(aidable -> trackingUUIDs.add(aidable.getUUID()));
		if (trackingUUIDs.isEmpty()) {
			this.doStop(level, body, timestamp);
			return;
		}
		brain.setMemory(TTMemoryModuleTypes.AIDING_ENTITIES.get(), trackingUUIDs);

		final int aidingTime = brain.getMemory(TTMemoryModuleTypes.AIDING_TIME.get()).orElse(0);
		if (aidingTime > 1) {
			entities.forEach(livingEntity -> spawnParticles(level, livingEntity, body.getRandom().nextInt(1, 2), BUBBLE_PARTICLE));
			body.setAidAnimProgress(1F);
			final LivingEntity nearestAidable = brain.getMemory(TTMemoryModuleTypes.NEAREST_AIDABLE.get()).orElse(null);
			if (nearestAidable != null) {
				brain.eraseMemory(MemoryModuleType.WALK_TARGET);
				brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
				body.getNavigation().moveTo(nearestAidable.getX(), nearestAidable.getEyeY() + 0.5D, nearestAidable.getZ(), 0, 1.25D);
			}
			return;
		} else if (aidingTime == 1) {
			brain.getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(attackTarget -> entities.forEach(entity -> {
				if (!(entity instanceof Mob mob)) return;
				mob.setTarget(attackTarget);
				spawnParticles(level, entity, body.getRandom().nextInt(9, 18), EFFECT_PARTICLE);
			}));
		}
		this.doStop(level, body, timestamp);
	}

	private static void spawnParticles(ServerLevel level, LivingEntity body, int count, ParticleOptions options) {
		level.sendParticles(
			options,
			body.getX(), body.getY(0.6666666666666666D), body.getZ(),
			count,
			body.getBbWidth() / 4F, body.getBbHeight() / 4F, body.getBbWidth() / 4F,
			0.05D
		);
	}
}
