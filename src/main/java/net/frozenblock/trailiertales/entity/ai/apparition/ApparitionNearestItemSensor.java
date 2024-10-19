package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.NotNull;

public class ApparitionNearestItemSensor extends Sensor<Apparition> {
	private static final double RADIUS = 24D;
	private static final double Y_RANGE = 24D;

	@Override
	public @NotNull Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull Apparition apparition) {
		Brain<?> brain = apparition.getBrain();
		List<ItemEntity> list = level.getEntitiesOfClass(ItemEntity.class, apparition.getBoundingBox().inflate(RADIUS, Y_RANGE, RADIUS), itemEntity -> true);
		list.sort(Comparator.comparingDouble(apparition::distanceToSqr));
		Optional<ItemEntity> optional = list.stream()
			.filter(item -> apparition.wantsToPickUp(level, item))
			.filter(itemEntity -> itemEntity.closerThan(apparition, RADIUS))
			.findFirst();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, optional);
	}
}
