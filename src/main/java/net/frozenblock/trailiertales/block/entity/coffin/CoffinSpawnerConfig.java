/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

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
	int maxApparitions,
	int ticksBetweenApparitionSpawn
) {
	public static final CoffinSpawnerConfig DEFAULT = new CoffinSpawnerConfig(
		4,
		6F,
		1F,
		2F,
		0F,
		400,
		4,
		1,
		1600
	);
	public static final CoffinSpawnerConfig IRRITATED = new CoffinSpawnerConfig(
		5,
		12F,
		1F,
		2F,
		1F,
		300,
		6,
		1,
		1200
	);
	public static final CoffinSpawnerConfig AGGRESSIVE = new CoffinSpawnerConfig(
		6,
		10F,
		1F,
		3F,
		1F,
		200,
		10,
		1,
		1000
	);
	public static final CoffinSpawnerConfig OMINOUS = new CoffinSpawnerConfig(
		6,
		15F,
		2F,
		3F,
		1F,
		150,
		15,
		2,
		800
	);

	public static final Codec<CoffinSpawnerConfig> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codec.intRange(1, 128)
					.lenientOptionalFieldOf("spawn_range", DEFAULT.spawnRange)
					.forGetter(CoffinSpawnerConfig::spawnRange),
				Codec.floatRange(0F, Float.MAX_VALUE)
					.lenientOptionalFieldOf("total_mobs", DEFAULT.totalMobs)
					.forGetter(CoffinSpawnerConfig::totalMobs),
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
				Codec.intRange(0, Integer.MAX_VALUE)
					.lenientOptionalFieldOf("max_apparitions", DEFAULT.maxApparitions)
					.forGetter(CoffinSpawnerConfig::maxApparitions),
				Codec.intRange(800, Integer.MAX_VALUE)
					.lenientOptionalFieldOf("ticks_between_apparition_spawn", DEFAULT.ticksBetweenApparitionSpawn)
					.forGetter(CoffinSpawnerConfig::ticksBetweenApparitionSpawn)
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
