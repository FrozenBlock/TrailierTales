package net.frozenblock.trailiertales.block.entity.coffin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.SpawnData;
import org.jetbrains.annotations.NotNull;

public record CoffinSpawnerConfig(
	int spawnRange,
	float totalMobs,
	float simultaneousMobs,
	float totalMobsAddedPerPlayer,
	float simultaneousMobsAddedPerPlayer,
	int ticksBetweenSpawn,
	int powerForNextLevel,
	SimpleWeightedRandomList<SpawnData> spawnPotentials
) {
	public static final CoffinSpawnerConfig DEFAULT = new CoffinSpawnerConfig(
		4,
		4F,
		2F,
		2F,
		2F,
		400,
		4,
		SimpleWeightedRandomList.<SpawnData>builder()
			.add(new SpawnData(getSpawnCompound(EntityType.ZOMBIE), Optional.empty(), Optional.empty()), 1)
			.add(new SpawnData(getSpawnCompound(EntityType.SKELETON), Optional.empty(), Optional.empty()), 2)
			.build()
	);
	public static final CoffinSpawnerConfig IRRITATED = new CoffinSpawnerConfig(
		4,
		5F,
		2F,
		2F,
		2F,
		240,
		8,
		SimpleWeightedRandomList.<SpawnData>builder()
			.add(new SpawnData(getSpawnCompound(EntityType.ZOMBIE), Optional.empty(), Optional.empty()), 2)
			.add(new SpawnData(getSpawnCompound(EntityType.SKELETON), Optional.empty(), Optional.empty()), 5)
			.add(new SpawnData(getSpawnCompoundBaby(EntityType.ZOMBIE), Optional.empty(), Optional.empty()), 2)
			.add(new SpawnData(getSpawnCompound(EntityType.STRAY), Optional.empty(), Optional.empty()), 1)
			.build()
	);
	public static final CoffinSpawnerConfig AGGRESSIVE = new CoffinSpawnerConfig(
		4,
		6F,
		3F,
		3F,
		2F,
		120,
		8,
		SimpleWeightedRandomList.<SpawnData>builder()
			.add(new SpawnData(getSpawnCompound(EntityType.ZOMBIE), Optional.empty(), Optional.empty()), 3)
			.add(new SpawnData(getSpawnCompound(EntityType.SKELETON), Optional.empty(), Optional.empty()), 9)
			.add(new SpawnData(getSpawnCompoundBaby(EntityType.ZOMBIE), Optional.empty(), Optional.empty()), 3)
			.add(new SpawnData(getSpawnCompound(EntityType.STRAY), Optional.empty(), Optional.empty()), 6)
			.add(new SpawnData(getSpawnCompound(EntityType.BOGGED), Optional.empty(), Optional.empty()), 4)
			.add(new SpawnData(getSpawnCompound(EntityType.HUSK), Optional.empty(), Optional.empty()), 4)
			.build()
	);

	private static @NotNull CompoundTag getSpawnCompound(EntityType<?> entityType) {
		CompoundTag tag = new CompoundTag();
		tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
		return tag;
	}

	private static @NotNull CompoundTag getSpawnCompoundBaby(EntityType<?> entityType) {
		CompoundTag tag = getSpawnCompound(entityType);
		tag.putBoolean("IsBaby", true);
		return tag;
	}

	public static final Codec<CoffinSpawnerConfig> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codec.intRange(1, 128).lenientOptionalFieldOf("spawn_range", DEFAULT.spawnRange).forGetter(CoffinSpawnerConfig::spawnRange),
				Codec.floatRange(0F, Float.MAX_VALUE).lenientOptionalFieldOf("total_mobs", DEFAULT.totalMobs).forGetter(CoffinSpawnerConfig::totalMobs),
				Codec.floatRange(0F, Float.MAX_VALUE)
					.lenientOptionalFieldOf("simultaneous_mobs", DEFAULT.simultaneousMobs)
					.forGetter(CoffinSpawnerConfig::simultaneousMobs),
				Codec.floatRange(0F, Float.MAX_VALUE)
					.lenientOptionalFieldOf("total_mobs_added_per_player", DEFAULT.totalMobsAddedPerPlayer)
					.forGetter(CoffinSpawnerConfig::totalMobsAddedPerPlayer),
				Codec.floatRange(0F, Float.MAX_VALUE)
					.lenientOptionalFieldOf("simultaneous_mobs_added_per_player", DEFAULT.simultaneousMobsAddedPerPlayer)
					.forGetter(CoffinSpawnerConfig::simultaneousMobsAddedPerPlayer),
				Codec.intRange(80, Integer.MAX_VALUE)
					.lenientOptionalFieldOf("ticks_between_spawn", DEFAULT.ticksBetweenSpawn)
					.forGetter(CoffinSpawnerConfig::ticksBetweenSpawn),
				Codec.intRange(0, Integer.MAX_VALUE)
					.lenientOptionalFieldOf("power_for_next_level", DEFAULT.powerForNextLevel)
					.forGetter(CoffinSpawnerConfig::powerForNextLevel),
				SpawnData.LIST_CODEC.lenientOptionalFieldOf("spawn_potentials", SimpleWeightedRandomList.empty()).forGetter(CoffinSpawnerConfig::spawnPotentials)
			)
			.apply(instance, CoffinSpawnerConfig::new)
	);

	public int calculateTargetTotalMobs(int players) {
		return (int)Math.floor(this.totalMobs + this.totalMobsAddedPerPlayer * (float)players);
	}

	public int calculateTargetSimultaneousMobs(int players) {
		return (int)Math.floor(this.simultaneousMobs + this.simultaneousMobsAddedPerPlayer * (float)players);
	}
}
