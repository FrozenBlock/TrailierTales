/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.block.entity.coffin;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class CoffinSpawnerData {
	public static MapCodec<CoffinSpawnerData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		SpawnData.LIST_CODEC.optionalFieldOf("spawn_potentials", WeightedList.of()).forGetter(data -> data.spawnPotentials),
		Codec.INT.listOf().lenientOptionalFieldOf("souls_to_spawn", new IntArrayList()).forGetter(data -> data.soulsToSpawn),
		UUIDUtil.CODEC_SET.lenientOptionalFieldOf("potential_players", Sets.newHashSet()).forGetter(data -> data.potentialPlayers),
		UUIDUtil.CODEC_SET.lenientOptionalFieldOf("detected_players", Sets.newHashSet()).forGetter(data -> data.detectedPlayers),
		UUIDUtil.CODEC_SET.optionalFieldOf("current_mobs", Sets.newHashSet()).forGetter(data -> data.currentMobs),
		UUIDUtil.CODEC_SET.optionalFieldOf("current_apparitions", Sets.newHashSet()).forGetter(data -> data.currentApparitions),
		Codec.LONG.optionalFieldOf("power_cooldown_ends_at", 0L).forGetter(data -> data.powerCooldownEndsAt),
		Codec.LONG.lenientOptionalFieldOf("next_mob_spawns_at", 0L).forGetter(data -> data.nextMobSpawnsAt),
		Codec.intRange(0, Integer.MAX_VALUE).lenientOptionalFieldOf("total_mobs_spawned", 0).forGetter(data -> data.totalMobsSpawned),
		Codec.LONG.lenientOptionalFieldOf("next_apparition_spawns_at", 0L).forGetter(data -> data.nextApparitionSpawnsAt),
		Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("total_apparitions_spawned", 0).forGetter(data -> data.totalApparitionsSpawned),
		Codec.LONG.lenientOptionalFieldOf("cooldown_ends_at", 0L).forGetter(data -> data.cooldownEndsAt),
		Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("power", 0).forGetter(data -> data.power),
		SpawnData.CODEC.optionalFieldOf("spawn_data").forGetter(data -> data.nextSpawnData),
		Codec.BOOL.optionalFieldOf("within_catacombs", false).forGetter(data -> data.withinCatacombs),
		Codec.intRange(0, 15).optionalFieldOf("max_active_light_level", 10).forGetter(data -> data.maxActiveLightLevel)
	).apply(instance, CoffinSpawnerData::new));
	protected final IntArrayList soulsToSpawn = new IntArrayList();
	protected final Set<UUID> potentialPlayers = new HashSet<>();
	protected final Set<UUID> detectedPlayers = new HashSet<>();
	protected final Set<UUID> currentMobs = new HashSet<>();
	protected final Set<UUID> currentApparitions = new HashSet<>();
	protected long powerCooldownEndsAt;
	protected long nextMobSpawnsAt;
	protected int totalMobsSpawned;
	protected long nextApparitionSpawnsAt;
	protected int totalApparitionsSpawned;
	protected long cooldownEndsAt;
	protected int power;
	protected Optional<SpawnData> nextSpawnData;
	protected boolean withinCatacombs;
	protected int maxActiveLightLevel;
	private WeightedList<SpawnData> spawnPotentials;

	public CoffinSpawnerData() {
		this(
			WeightedList.of(),
			new IntArrayList(),
			Collections.emptySet(),
			Collections.emptySet(),
			Collections.emptySet(),
			Collections.emptySet(),
			0L,
			0L,
			0,
			0L,
			0,
			0L,
			0,
			Optional.empty(),
			false,
			7
		);
	}

	public CoffinSpawnerData(
		WeightedList<SpawnData> spawnPotentials,
		List<Integer> soulsToSpawn,
		Set<UUID> potentialPlayers,
		Set<UUID> detectedPlayers,
		Set<UUID> currentMobs,
		Set<UUID> currentApparitions,
		long powerCooldownEndsAt,
		long nextMobSpawnsAt,
		int totalMobsSpawned,
		long nextApparitionSpawnsAt,
		int totalApparitionsSpawned,
		long cooldownEndsAt,
		int power,
		Optional<SpawnData> nextSpawnData,
		boolean withinCatacombs,
		int maxActiveLightLevel
	) {
		this.spawnPotentials = spawnPotentials;
		this.soulsToSpawn.addAll(soulsToSpawn);
		this.potentialPlayers.addAll(potentialPlayers);
		this.detectedPlayers.addAll(detectedPlayers);
		this.currentMobs.addAll(currentMobs);
		this.currentApparitions.addAll(currentApparitions);
		this.powerCooldownEndsAt = powerCooldownEndsAt;
		this.nextMobSpawnsAt = nextMobSpawnsAt;
		this.totalMobsSpawned = totalMobsSpawned;
		this.nextApparitionSpawnsAt = nextApparitionSpawnsAt;
		this.totalApparitionsSpawned = totalApparitionsSpawned;
		this.cooldownEndsAt = cooldownEndsAt;
		this.power = power;
		this.nextSpawnData = nextSpawnData;
		this.withinCatacombs = withinCatacombs;
		this.maxActiveLightLevel = maxActiveLightLevel;
	}

	public void immediatelyActivate(Level level, BlockPos pos, CoffinSpawner coffinSpawner) {
		if (!(level instanceof ServerLevel serverLevel)) return;
		if (!coffinSpawner.canSpawnApparition(serverLevel, pos, true)) return;
		this.nextApparitionSpawnsAt = 0L;
		this.nextMobSpawnsAt = 0L;
		coffinSpawner.addPower(1, serverLevel);
		coffinSpawner.spawnApparition(serverLevel, pos);
	}

	public void reset() {
		this.totalMobsSpawned = 0;
		this.power = 0;
		this.nextMobSpawnsAt = 0L;
		this.powerCooldownEndsAt = 0L;
		this.nextApparitionSpawnsAt = 0L;
		this.cooldownEndsAt = 0L;
		this.currentMobs.clear();
		this.currentApparitions.clear();
	}

	public boolean hasMobToSpawn(Level level, RandomSource random, BlockPos pos) {
		boolean hasNextSpawnData = this.getOrCreateNextSpawnData(level, random, pos).getEntityToSpawn().contains("id");
		return hasNextSpawnData || !this.spawnPotentials().isEmpty();
	}

	public boolean hasFinishedSpawningAllMobs(CoffinSpawnerConfig config, int players) {
		return this.totalMobsSpawned >= config.calculateTargetTotalMobs(players);
	}

	public boolean haveAllCurrentMobsDied() {
		return this.currentMobs.isEmpty();
	}

	public boolean haveAllCurrentApparitionsDied() {
		return this.currentApparitions.isEmpty();
	}

	public boolean trackingEntity(Entity entity) {
		return this.currentMobs.contains(entity.getUUID());
	}

	public boolean trackingApparition(Entity entity) {
		return this.currentApparitions.contains(entity.getUUID());
	}

	public boolean isOnCooldown(Level level) {
		return level.getGameTime() < this.cooldownEndsAt;
	}

	public boolean hasMobToSpawnAndIsntOnCooldown(Level level, RandomSource random, BlockPos pos) {
		return !isOnCooldown(level) && this.hasMobToSpawn(level, random, pos);
	}

	public int getPower() {
		return this.power;
	}

	public boolean isReadyToSpawnNextMob(ServerLevel level, CoffinSpawnerConfig config, int players) {
		return this.isPreparingToSpawnNextMob(level, config, players, 0);
	}

	public boolean isPreparingToSpawnNextMob(ServerLevel level, CoffinSpawnerConfig config, int players, int timeAhead) {
		return level.getGameTime() + timeAhead >= this.nextMobSpawnsAt && this.detectedAnyPlayers() && this.currentMobs.size() < config.calculateTargetSimultaneousMobs(players);
	}

	public int countAdditionalPlayers() {
		return Math.max(0, this.detectedPlayers.size() - 1);
	}

	public boolean detectedAnyPlayers() {
		return !this.detectedPlayers.isEmpty();
	}

	public boolean hasPotentialPlayers() {
		return !this.potentialPlayers.isEmpty();
	}

	public Optional<Player> getClosestDetectedPlayer(Level level, Vec3 origin) {
		return this.getClosestPlayerFromSet(this.detectedPlayers, level, origin);
	}

	public Optional<Player> getClosestPotentialPlayer(Level level, Vec3 origin) {
		return this.getClosestPlayerFromSet(this.potentialPlayers, level, origin);
	}

	private Optional<Player> getClosestPlayerFromSet(Set<UUID> players, Level level, Vec3 origin) {
		if (players.isEmpty()) return Optional.empty();
		final AtomicReference<Double> closestDistance = new AtomicReference<>(Double.MAX_VALUE);
		final AtomicReference<Optional<Player>> closestPlayer = new AtomicReference<>(Optional.empty());
		players.forEach(uuid -> {
			final Player player = level.getPlayerByUUID(uuid);
			if (player != null && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)) {
				double distanceTo = player.distanceToSqr(origin);
				if (distanceTo < closestDistance.get()) {
					closestDistance.set(distanceTo);
					closestPlayer.set(Optional.of(player));
				}
			}
		});
		return closestPlayer.get();
	}

	public List<Player> getNearbyDetectedPlayers(Level level, Vec3 origin, double distance) {
		return this.getNearbyPlayersFromSet(this.detectedPlayers, level, origin, distance);
	}

	public List<Player> getNearbyPotentialPlayers(Level level, Vec3 origin, double distance) {
		return this.getNearbyPlayersFromSet(this.potentialPlayers, level, origin, distance);
	}

	private List<Player> getNearbyPlayersFromSet(Set<UUID> players, Level level, Vec3 origin, double distance) {
		final List<Player> nearbyPlayers = new ArrayList<>();
		final double squaredDistance = distance * distance;
		if (players.isEmpty()) return nearbyPlayers;

		players.forEach(uuid -> {
			final Player player = level.getPlayerByUUID(uuid);
			if (player != null && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)) {
				if (player.distanceToSqr(origin) < squaredDistance) nearbyPlayers.add(player);
			}
		});
		return nearbyPlayers;
	}

	public void tryDetectPlayers(ServerLevel level, BlockPos pos, Direction direction, CoffinSpawner coffinSpawner) {
		final boolean isTickTime = (pos.asLong() + level.getGameTime()) % 20L == 0L;
		if (!isTickTime) return;

		final List<UUID> list = coffinSpawner.getPlayerDetector()
			.detect(level, coffinSpawner.getEntitySelector(), pos, coffinSpawner.getRequiredPlayerRange(), this.withinCatacombs);
		this.potentialPlayers.addAll(list);

		if (!coffinSpawner.isOminous() && !list.isEmpty()) {
			final Optional<Pair<Player, Holder<MobEffect>>> optional = findPlayerWithOminousEffect(level, list);
			optional.ifPresent(pair -> {
				Player player = pair.getFirst();
				if (pair.getSecond() == MobEffects.BAD_OMEN) transformBadOmenIntoSiegeOmen(player);
				coffinSpawner.applyOminous(level);
			});
		}

		final List<UUID> detectedList = new ArrayList<>(list);
		detectedList.removeIf(uuid ->
			!(level.getPlayerByUUID(uuid) instanceof Player player)
			|| !((TTEntityConfig.APPARITION_HAUNTED_COFFINS.get() && player.hasEffect(TTMobEffects.HAUNT.asHolder())) || player.hasEffect(TTMobEffects.SIEGE_OMEN.asHolder()))
		);
		for (UUID uuid : this.currentApparitions) {
			if (level.getEntity(uuid) instanceof Apparition apparition) {
				final LivingEntity target = apparition.getTarget();
				if (target instanceof Player player) detectedList.add(player.getUUID());
			}
		}

		if (this.detectedPlayers.addAll(detectedList)) {
			final RandomSource random = level.getRandom();
			CoffinBlock.spawnParticlesFrom(level, TTParticleTypes.COFFIN_SOUL.get(), 8 + Math.min(this.countAdditionalPlayers() * 3, 15), 0.015D, direction, pos, 0.45D);
			level.playSound(null, pos, TTSounds.COFFIN_DETECT_PLAYER.get(), SoundSource.BLOCKS, 2F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1F);
		}

		this.detectedPlayers.removeIf(uuid -> !detectedList.contains(uuid));
	}

	private static Optional<Pair<Player, Holder<MobEffect>>> findPlayerWithOminousEffect(ServerLevel level, List<UUID> list) {
		Player player = null;

		for (UUID uUID : list) {
			final Player player2 = level.getPlayerByUUID(uUID);
			if (player2 != null) {
				final Holder<MobEffect> holder = TTMobEffects.SIEGE_OMEN.asHolder();
				if (player2.hasEffect(holder)) return Optional.of(Pair.of(player2, holder));
				if (player2.hasEffect(MobEffects.BAD_OMEN)) player = player2;
			}
		}

		return Optional.ofNullable(player).map(playerx -> Pair.of(playerx, MobEffects.BAD_OMEN));
	}

	private static void transformBadOmenIntoSiegeOmen(Player player) {
		final MobEffectInstance mobEffectInstance = player.getEffect(MobEffects.BAD_OMEN);
		if (mobEffectInstance == null) return;
		final int i = mobEffectInstance.getAmplifier() + 1;
		final int j = 18000 * i;
		player.removeEffect(MobEffects.BAD_OMEN);
		player.addEffect(new MobEffectInstance(TTMobEffects.SIEGE_OMEN.asHolder(), j, 0));
	}

	public boolean isPowerCooldownFinished(ServerLevel level) {
		return level.getGameTime() >= this.powerCooldownEndsAt;
	}

	public void setEntityId(EntityType<?> type, Level level, RandomSource random, BlockPos pos) {
		this.getOrCreateNextSpawnData(level, random, pos).getEntityToSpawn().putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(type).toString());
	}

	public void setEntityData(TypedEntityData<EntityType<?>> entityData, @Nullable Level level, RandomSource random, BlockPos pos) {
		final SpawnData spawnData = this.getOrCreateNextSpawnData(level, random, pos);
		entityData.loadInto(spawnData, BuiltInRegistries.ENTITY_TYPE);
	}

	public WeightedList<SpawnData> spawnPotentials() {
		return this.spawnPotentials;
	}

	SpawnData getOrCreateNextSpawnData(@Nullable Level level, RandomSource random, BlockPos pos) {
		if (this.nextSpawnData.isEmpty()) this.setNextSpawnData(level, pos, this.spawnPotentials.getRandom(random).orElseGet(SpawnData::new));
		return this.nextSpawnData.get();
	}

	protected void setNextSpawnData(@Nullable Level level, BlockPos pos, @Nullable SpawnData nextSpawnData) {
		this.nextSpawnData = Optional.ofNullable(nextSpawnData);
	}
}
