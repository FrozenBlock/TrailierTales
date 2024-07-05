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
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class ApparitionPlayerSensor extends Sensor<Apparition> {

	@Override
	public @NotNull Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
	}

	@Override
	protected void doTick(@NotNull ServerLevel world, @NotNull Apparition apparition) {
		List<Player> list = world.players()
			.stream()
			.filter(EntitySelector.NO_SPECTATORS)
			.filter(player -> apparition.closerThan(player, apparition.getAttributes().getValue(Attributes.FOLLOW_RANGE)))
			.sorted(Comparator.comparingDouble(apparition::distanceToSqr))
			.collect(Collectors.toList());
		Brain<?> brain = apparition.getBrain();
		brain.setMemory(MemoryModuleType.NEAREST_PLAYERS, list);
		List<Player> list2 = list.stream().filter(player -> isEntityTargetable(apparition, player)).toList();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, list2.isEmpty() ? null : list2.getFirst());
		Optional<Player> optional = list2.stream().filter(player -> isEntityAttackable(apparition, player)).findFirst();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, optional);
	}
}
