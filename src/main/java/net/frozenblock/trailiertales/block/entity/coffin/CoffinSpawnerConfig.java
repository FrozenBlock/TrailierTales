package net.frozenblock.trailiertales.block.entity.coffin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CoffinSpawnerConfig(
	int spawnRange,
	float totalMobs,
	float simultaneousMobs,
	float totalMobsAddedPerPlayer,
	float simultaneousMobsAddedPerPlayer,
	int ticksBetweenSpawn,
	int powerForNextLevel,
	boolean spawnsApparitions
) {
	public static final CoffinSpawnerConfig DEFAULT = new CoffinSpawnerConfig(
		4,
		6F,
		1F,
		2F,
		1F,
		400,
		4,
		false
	);
	public static final CoffinSpawnerConfig IRRITATED = new CoffinSpawnerConfig(
		5,
		12F,
		2F,
		2F,
		1F,
		240,
		12,
		true
	);
	public static final CoffinSpawnerConfig AGGRESSIVE = new CoffinSpawnerConfig(
		6,
		20F,
		3F,
		3F,
		2F,
		120,
		20,
		true
	);

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
				Codec.BOOL.lenientOptionalFieldOf("spawns_apparitions", DEFAULT.spawnsApparitions)
					.forGetter(CoffinSpawnerConfig::spawnsApparitions)
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
