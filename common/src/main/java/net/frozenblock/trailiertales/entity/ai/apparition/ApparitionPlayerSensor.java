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

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class ApparitionPlayerSensor extends Sensor<Apparition> {
	private static final TargetingConditions TARGET_CONDITIONS = TargetingConditions.forNonCombat()
		.ignoreLineOfSight();
	private static final TargetingConditions TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING = TargetingConditions.forNonCombat()
		.ignoreLineOfSight()
		.ignoreInvisibilityTesting();
	private static final TargetingConditions ATTACK_TARGET_CONDITIONS = TargetingConditions.forCombat()
		.ignoreLineOfSight();
	private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY = TargetingConditions.forCombat()
		.ignoreLineOfSight()
		.ignoreInvisibilityTesting();

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
	}

	@Override
	protected void doTick(ServerLevel level, Apparition body) {
		final Brain<?> brain = body.getBrain();
		final double range = body.getAttributeValue(Attributes.FOLLOW_RANGE);

		final List<Player> nearestPlayers = level.players()
			.stream()
			.filter(EntitySelector.NO_SPECTATORS)
			.filter(player -> body.closerThan(player, range))
			.sorted(Comparator.comparingDouble(body::distanceToSqr))
			.collect(Collectors.toList());
		brain.setMemory(MemoryModuleType.NEAREST_PLAYERS, nearestPlayers);

		final List<Player> nearestVisiblePlayers = nearestPlayers
			.stream()
			.filter(player -> isEntityTargetable(level, body, player, range))
			.toList();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, nearestVisiblePlayers.isEmpty() ? null : nearestVisiblePlayers.getFirst());

		final Optional<Player> nearestVisibleAttackablePlayer = nearestVisiblePlayers
			.stream()
			.filter(player -> isEntityAttackable(level, body, player, range))
			.findFirst();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, nearestVisibleAttackablePlayer);
	}

	public static boolean isEntityTargetable(ServerLevel level, LivingEntity body, LivingEntity target, double range) {
		return body.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, target)
			? TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING.range(range).test(level, body, target)
			: TARGET_CONDITIONS.range(range).test(level, body, target);
	}

	public static boolean isEntityAttackable(ServerLevel level, LivingEntity body, LivingEntity target, double range) {
		return body.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, target)
			? ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY.range(range).test(level, body, target)
			: ATTACK_TARGET_CONDITIONS.range(range).test(level, body, target);
	}
}
