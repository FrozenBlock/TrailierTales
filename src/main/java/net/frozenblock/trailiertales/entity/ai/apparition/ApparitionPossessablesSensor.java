package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.collect.ImmutableSet;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ApparitionPossessablesSensor extends Sensor<Apparition> {

	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(RegisterMemoryModuleTypes.NEAREST_POSSESSABLE, MemoryModuleType.NEAREST_LIVING_ENTITIES, RegisterMemoryModuleTypes.POSSESSION_COOLDOWN);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull Apparition apparition) {
		Brain<?> brain = apparition.getBrain();
		if (brain.hasMemoryValue(RegisterMemoryModuleTypes.POSSESSION_COOLDOWN) || !apparition.getInventory().getItems().getFirst().isEmpty()) {
			brain.eraseMemory(RegisterMemoryModuleTypes.NEAREST_POSSESSABLE);
			return;
		}

		List<LivingEntity> entities = brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(List.of());
		entities.sort(Comparator.comparingDouble(apparition::distanceToSqr));
		Optional<LivingEntity> optional = entities.stream()
			.filter(apparition::canPossessEntity)
			.findFirst();

		if (optional.isPresent()) {
			brain.setMemory(RegisterMemoryModuleTypes.NEAREST_POSSESSABLE, optional.get());
		} else {
			brain.eraseMemory(RegisterMemoryModuleTypes.NEAREST_POSSESSABLE);
		}
	}
}
