package net.frozenblock.trailiertales.entity.spawn;

import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

public class TrailierSpawnPredicates {
	public static boolean checkCamelSpawnRules(
		EntityType<? extends LivingEntity> entityType,
		@NotNull ServerLevelAccessor world,
		EntitySpawnReason reason,
		@NotNull BlockPos spawnPos,
		RandomSource random
	) {
		boolean bl = EntitySpawnReason.ignoresLightRequirements(reason) || world.getRawBrightness(spawnPos, 0) > 8;
		return world.getBlockState(spawnPos.below()).is(TTBlockTags.CAMEL_SPAWNABLE_ON) && bl && random.nextFloat() <= 0.1F;
	}
}
