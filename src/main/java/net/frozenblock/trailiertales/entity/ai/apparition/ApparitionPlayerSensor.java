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
import org.jetbrains.annotations.NotNull;

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
	public @NotNull Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull Apparition apparition) {
		double range = apparition.getAttributeValue(Attributes.FOLLOW_RANGE);
		List<Player> list = level.players()
			.stream()
			.filter(EntitySelector.NO_SPECTATORS)
			.filter(player -> apparition.closerThan(player, range))
			.sorted(Comparator.comparingDouble(apparition::distanceToSqr))
			.collect(Collectors.toList());

		Brain<?> brain = apparition.getBrain();
		brain.setMemory(MemoryModuleType.NEAREST_PLAYERS, list);
		List<Player> list2 = list.stream().filter(player -> isEntityTargetable(level, apparition, player, range)).toList();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, list2.isEmpty() ? null : list2.getFirst());
		Optional<Player> optional = list2.stream().filter(player -> isEntityAttackable(level, apparition, player, range)).findFirst();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, optional);
	}

	public static boolean isEntityTargetable(ServerLevel level, @NotNull LivingEntity entity, LivingEntity target, double range) {
		return entity.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, target)
			? TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING.range(range).test(level, entity, target)
			: TARGET_CONDITIONS.range(range).test(level, entity, target);
	}

	public static boolean isEntityAttackable(ServerLevel level, @NotNull LivingEntity entity, LivingEntity target, double range) {
		return entity.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, target)
			? ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY.range(range).test(level, entity, target)
			: ATTACK_TARGET_CONDITIONS.range(range).test(level, entity, target);
	}
}
