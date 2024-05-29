package net.frozenblock.trailiertales.block.entity.coffin;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.LevelEvent;
import org.jetbrains.annotations.NotNull;

public class CoffinSpawnerData {
	public static MapCodec<CoffinSpawnerData> MAP_CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				Codec.LONG.listOf().lenientOptionalFieldOf("souls_to_spawn", new LongArrayList()).forGetter(data -> data.soulsToSpawn),
				UUIDUtil.CODEC_SET.lenientOptionalFieldOf("detected_players", Sets.newHashSet()).forGetter(data -> data.detectedPlayers),
				UUIDUtil.CODEC_SET.lenientOptionalFieldOf("current_mobs", Sets.newHashSet()).forGetter(data -> data.currentMobs),
				Codec.LONG.lenientOptionalFieldOf("power_cooldown_ends_at", 0L).forGetter(data -> data.powerCooldownEndsAt),
				Codec.LONG.lenientOptionalFieldOf("next_mob_spawns_at", 0L).forGetter(data -> data.nextMobSpawnsAt),
				Codec.intRange(0, Integer.MAX_VALUE).lenientOptionalFieldOf("total_mobs_spawned", 0).forGetter(data -> data.totalMobsSpawned),
				Codec.intRange(0, Integer.MAX_VALUE).lenientOptionalFieldOf("power", 0).forGetter(data -> data.power),
				SpawnData.CODEC.lenientOptionalFieldOf("spawn_data").forGetter(data -> data.nextSpawnData),
				Codec.BOOL.lenientOptionalFieldOf("within_catacombs", false).forGetter(data -> data.withinCatacombs)
			)
			.apply(instance, CoffinSpawnerData::new)
	);

	protected final LongArrayList soulsToSpawn = new LongArrayList();
	protected final Set<UUID> detectedPlayers = new HashSet<>();
	protected final Set<UUID> currentMobs = new HashSet<>();
	protected long powerCooldownEndsAt;
	protected long nextMobSpawnsAt;
	protected int totalMobsSpawned;
	protected int power;
	protected Optional<SpawnData> nextSpawnData;
	protected boolean withinCatacombs;

	public CoffinSpawnerData() {
		this(new LongArrayList(), Collections.emptySet(), Collections.emptySet(), 0L, 0L, 0, 0, Optional.empty(), false);
	}

	public CoffinSpawnerData(
		List<Long> soulsToSpawn,
		Set<UUID> detectedPlayers,
		Set<UUID> currentMobs,
		long powerCooldownEndsAt,
		long nextMobSpawnsAt,
		int totalMobsSpawned,
		int power,
		Optional<SpawnData> nextSpawnData,
		boolean withinCatacombs
	) {
		this.soulsToSpawn.addAll(soulsToSpawn);
		this.detectedPlayers.addAll(detectedPlayers);
		this.currentMobs.addAll(currentMobs);
		this.powerCooldownEndsAt = powerCooldownEndsAt;
		this.nextMobSpawnsAt = nextMobSpawnsAt;
		this.totalMobsSpawned = totalMobsSpawned;
		this.power = power;
		this.nextSpawnData = nextSpawnData;
		this.withinCatacombs = withinCatacombs;
	}

	public void reset() {
		this.totalMobsSpawned = 0;
		this.power = 0;
		this.nextMobSpawnsAt = 0L;
		this.powerCooldownEndsAt = 0L;
		this.currentMobs.clear();
	}

	public boolean hasMobToSpawn(CoffinSpawner spawnerLogic, RandomSource random, boolean blocked) {
		if (blocked) {
			return false;
		}
		boolean hasNextSpawnData = this.getOrCreateNextSpawnData(spawnerLogic, random).getEntityToSpawn().contains("id", 8);
		return hasNextSpawnData || !spawnerLogic.getConfig().spawnPotentials().isEmpty();
	}

	public boolean hasFinishedSpawningAllMobs(@NotNull CoffinSpawnerConfig config, int players) {
		return this.totalMobsSpawned >= config.calculateTargetTotalMobs(players);
	}

	public boolean haveAllCurrentMobsDied() {
		return this.currentMobs.isEmpty();
	}

	public boolean trackingEntity(@NotNull Entity entity) {
		return this.currentMobs.contains(entity.getUUID());
	}

	public int getPower() {
		return this.power;
	}

	public boolean isReadyToSpawnNextMob(@NotNull ServerLevel level, CoffinSpawnerConfig config, int players) {
		return this.isPreparingToSpawnNextMob(level, config, players, 0);
	}

	public boolean isPreparingToSpawnNextMob(@NotNull ServerLevel level, CoffinSpawnerConfig config, int players, int timeAhead) {
		return level.getGameTime() + timeAhead >= this.nextMobSpawnsAt && this.detectedAnyPlayers() && this.currentMobs.size() < config.calculateTargetSimultaneousMobs(players);
	}

	public int countAdditionalPlayers() {
		return Math.max(0, this.detectedPlayers.size() - 1);
	}

	public boolean detectedAnyPlayers() {
		return !this.detectedPlayers.isEmpty();
	}

	public UUID randomPlayerUUID(RandomSource random) {
		return Util.getRandom(this.detectedPlayers.stream().toList(), random);
	}

	public void tryDetectPlayers(@NotNull ServerLevel world, @NotNull BlockPos pos, CoffinSpawner trialSpawner) {
		boolean isSecondForPos = (pos.asLong() + world.getGameTime()) % 20L == 0L;
		if (isSecondForPos) {
			List<UUID> list = trialSpawner.getPlayerDetector()
				.detect(world, trialSpawner.getEntitySelector(), pos, trialSpawner.getRequiredPlayerRange(), this.withinCatacombs);

			if (this.detectedPlayers.addAll(list)) {
				world.levelEvent(LevelEvent.PARTICLES_TRIAL_SPAWNER_DETECT_PLAYER, pos, this.detectedPlayers.size());
			}

			this.detectedPlayers.removeIf(uuid -> !list.contains(uuid));
		}
	}

	public boolean isPowerCooldownFinished(@NotNull ServerLevel level) {
		return level.getGameTime() >= this.powerCooldownEndsAt;
	}

	public void setEntityId(CoffinSpawner spawner, RandomSource random, EntityType<?> entityType) {
		this.getOrCreateNextSpawnData(spawner, random).getEntityToSpawn().putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
	}

	protected SpawnData getOrCreateNextSpawnData(CoffinSpawner spawner, RandomSource random) {
		if (this.nextSpawnData.isEmpty()) {
			SimpleWeightedRandomList<SpawnData> simpleWeightedRandomList = spawner.getConfig().spawnPotentials();
			Optional<SpawnData> optional = simpleWeightedRandomList.isEmpty() ? this.nextSpawnData : simpleWeightedRandomList.getRandom(random).map(Wrapper::data);
			this.nextSpawnData = Optional.of(optional.orElseGet(SpawnData::new));
			spawner.markUpdated();
		}
		return this.nextSpawnData.get();
	}

	private static long lowResolutionPosition(@NotNull ServerLevel world, @NotNull BlockPos pos) {
		BlockPos blockPos = new BlockPos(Mth.floor((float)pos.getX() / 30F), Mth.floor((float)pos.getY() / 20F), Mth.floor((float)pos.getZ() / 30F));
		return world.getSeed() + blockPos.asLong();
	}
}
