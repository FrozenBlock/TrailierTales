package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.NotNull;

public class NearestItemNoLineOfSightSensor extends Sensor<Mob> {
	private static final double RADIUS = 32D;
	private static final double Y_RANGE = 16L;

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
	}

	@Override
	protected void doTick(@NotNull ServerLevel world, @NotNull Mob mob) {
		Brain<?> brain = mob.getBrain();
		List<ItemEntity> list = world.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(RADIUS, Y_RANGE, RADIUS), itemEntity -> true);
		list.sort(Comparator.comparingDouble(mob::distanceToSqr));
		Optional<ItemEntity> optional = list.stream()
			.filter(itemEntity -> mob.wantsToPickUp(itemEntity.getItem()))
			.filter(itemEntity -> itemEntity.closerThan(mob, RADIUS))
			.findFirst();
		brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, optional);
	}
}
