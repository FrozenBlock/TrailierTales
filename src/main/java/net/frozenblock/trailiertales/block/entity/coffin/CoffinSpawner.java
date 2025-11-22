/*
 * Copyright 2025 FrozenBlock
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

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.Optional;
import java.util.UUID;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAi;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTResources;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;

public final class CoffinSpawner {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final int PLAYER_TRACKING_DISTANCE = 48;
	private static final int MAX_MOB_TRACKING_DISTANCE = 64;
	private static final int MAX_MOB_TRACKING_DISTANCE_SQR = Mth.square(MAX_MOB_TRACKING_DISTANCE);
	private static final PlayerDetector.EntitySelector ENTITY_SELECTOR = PlayerDetector.EntitySelector.SELECT_FROM_LEVEL;
	public static final PlayerDetector IN_CATACOMBS_NO_CREATIVE_PLAYERS = (world, entitySelector, pos, d, bl) -> entitySelector.getPlayers(
			world, player -> player.blockPosition().closerThan(pos, d) && !player.isCreative() && !player.isSpectator()
		)
		.stream()
		.filter(player -> !bl || isInCatacombsBounds(player.blockPosition(), world.structureManager()))
		.map(Entity::getUUID)
		.toList();
	private final CoffinSpawnerConfig normalConfig;
	private final CoffinSpawnerConfig irritatedConfig;
	private final CoffinSpawnerConfig aggressiveConfig;
	private final CoffinSpawnerConfig ominousConfig;
	private final CoffinSpawnerData data;
	private final int requiredPlayerRange;
	private final int powerCooldownLength;
	private final CoffinSpawner.StateAccessor stateAccessor;
	private final UUID uuid;
	private boolean attemptingToSpawnMob;

	@Contract(" -> new")
	public MapCodec<CoffinSpawner> mapCodec() {
		return RecordCodecBuilder.mapCodec(
			instance -> instance.group(
				CoffinSpawnerConfig.CODEC.optionalFieldOf("normal_config", CoffinSpawnerConfig.DEFAULT).forGetter(CoffinSpawner::getNormalConfig),
				CoffinSpawnerConfig.CODEC.optionalFieldOf("irritated_config", CoffinSpawnerConfig.IRRITATED).forGetter(CoffinSpawner::getIrritatedConfig),
				CoffinSpawnerConfig.CODEC.optionalFieldOf("aggressive_config", CoffinSpawnerConfig.AGGRESSIVE).forGetter(CoffinSpawner::getAggressiveConfig),
				CoffinSpawnerConfig.CODEC.optionalFieldOf("ominous_config", CoffinSpawnerConfig.AGGRESSIVE).forGetter(CoffinSpawner::getOminousConfig),
				CoffinSpawnerData.MAP_CODEC.forGetter(CoffinSpawner::getData),
				Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("power_cooldown_length", 12000).forGetter(CoffinSpawner::getPowerCooldownLength),
				Codec.intRange(1, PLAYER_TRACKING_DISTANCE).optionalFieldOf("required_player_range", PLAYER_TRACKING_DISTANCE).forGetter(CoffinSpawner::getRequiredPlayerRange),
				Codec.STRING.optionalFieldOf("uuid", UUID.randomUUID().toString()).forGetter(CoffinSpawner::getStringUUID),
				Codec.BOOL.optionalFieldOf("attempting_to_spawn_mob", false).forGetter(CoffinSpawner::isAttemptingToSpawnMob)
			).apply(
				instance,
				(config, config2, config3, config4, data, powerCooldownLength, integer, uuid, attemptingSpawn) -> new CoffinSpawner(
					config, config2, config3, config4, data, powerCooldownLength, integer, uuid, attemptingSpawn, this.stateAccessor
				)
			)
		);
	}

	public CoffinSpawner(CoffinSpawner.StateAccessor coffin) {
		this(
			CoffinSpawnerConfig.DEFAULT,
			CoffinSpawnerConfig.IRRITATED,
			CoffinSpawnerConfig.AGGRESSIVE,
			CoffinSpawnerConfig.OMINOUS,
			new CoffinSpawnerData(),
			12000,
			PLAYER_TRACKING_DISTANCE,
			UUID.randomUUID().toString(),
			false,
			coffin
		);
	}

	public CoffinSpawner(
		CoffinSpawnerConfig normalConfig,
		CoffinSpawnerConfig irritatedConfig,
		CoffinSpawnerConfig aggressiveConfig,
		CoffinSpawnerConfig ominousConfig,
		CoffinSpawnerData data,
		int powerCooldownLength,
		int requiredPlayerRange,
		String uuid,
		boolean attemptingToSpawnMob,
		CoffinSpawner.StateAccessor coffin
	) {
		this.normalConfig = normalConfig;
		this.irritatedConfig = irritatedConfig;
		this.aggressiveConfig = aggressiveConfig;
		this.ominousConfig = ominousConfig;
		this.data = data;
		this.powerCooldownLength = powerCooldownLength;
		this.requiredPlayerRange = requiredPlayerRange;
		this.uuid = UUID.fromString(uuid);
		this.attemptingToSpawnMob = attemptingToSpawnMob;
		this.stateAccessor = coffin;
	}

	public CoffinSpawnerConfig getConfig() {
		return switch (this.getState()) {
			case OMINOUS -> this.ominousConfig;
			case AGGRESSIVE -> this.aggressiveConfig;
			case IRRITATED -> this.irritatedConfig;
			default -> this.normalConfig;
		};
	}

	@VisibleForTesting
	public CoffinSpawnerConfig getNormalConfig() {
		return this.normalConfig;
	}

	@VisibleForTesting
	public CoffinSpawnerConfig getIrritatedConfig() {
		return this.irritatedConfig;
	}

	@VisibleForTesting
	public CoffinSpawnerConfig getAggressiveConfig() {
		return this.aggressiveConfig;
	}

	@VisibleForTesting
	public CoffinSpawnerConfig getOminousConfig() {
		return this.ominousConfig;
	}

	public void applyOminous(ServerLevel level) {
		this.setState(level, CoffinSpawnerState.OMINOUS);
	}

	public void removeOminous(ServerLevel level) {
		this.setState(level, CoffinSpawnerState.getStateForPower(level, this));
	}

	public boolean isOminous() {
		return this.getState() == CoffinSpawnerState.OMINOUS;
	}

	public CoffinSpawnerData getData() {
		return this.data;
	}

	public int getPowerCooldownLength() {
		return this.powerCooldownLength;
	}

	public int getRequiredPlayerRange() {
		return this.requiredPlayerRange;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public String getStringUUID() {
		return this.uuid.toString();
	}

	public boolean isAttemptingToSpawnMob() {
		return this.attemptingToSpawnMob;
	}

	public void addPower(int i, Level level) {
		this.data.power += i;
		this.data.powerCooldownEndsAt = level.getGameTime() + this.powerCooldownLength;
	}

	public void addSoulParticle(int delayUntilSpawn) {
		this.data.soulsToSpawn.add(delayUntilSpawn);
	}

	public CoffinSpawnerState getState() {
		return this.stateAccessor.getState();
	}

	public void setState(Level level, CoffinSpawnerState state) {
		this.stateAccessor.setState(level, state);
	}

	public void markUpdated() {
		this.stateAccessor.markUpdated();
	}

	public PlayerDetector getPlayerDetector() {
		return this.data.withinCatacombs ? IN_CATACOMBS_NO_CREATIVE_PLAYERS : PlayerDetector.NO_CREATIVE_PLAYERS;
	}

	public PlayerDetector.EntitySelector getEntitySelector() {
		return ENTITY_SELECTOR;
	}

	public boolean canSpawnInLevel(ServerLevel level) {
		return level.getDifficulty() != Difficulty.PEACEFUL
			&& (TTBlockConfig.COFFIN_IGNORE_DOMOBSPAWNING || level.getGameRules().get(GameRules.SPAWN_MOBS));
	}

	public Optional<UUID> spawnMob(ServerLevel level, BlockPos pos) {
		final RandomSource random = level.getRandom();
		final SpawnData spawnData = this.data.getOrCreateNextSpawnData(level.getRandom());

		try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(() -> "spawner@" + pos, LOGGER)) {
			final ValueInput valueInput = TagValueInput.create(scopedCollector, level.registryAccess(), spawnData.entityToSpawn());
			final Optional<EntityType<?>> optional = EntityType.by(valueInput);
			if (optional.isEmpty()) return Optional.empty();

			final Vec3 spawnVec3 = valueInput.read("Pos", Vec3.CODEC).orElseGet(() -> {
				CoffinSpawnerConfig config = this.getConfig();
				return new Vec3(
					pos.getX() + (random.nextDouble() - random.nextDouble()) * (double)config.spawnRange() + (double)0.5F,
					pos.getY() + random.nextInt(3) - 1,
					pos.getZ() + (random.nextDouble() - random.nextDouble()) * (double)config.spawnRange() + (double)0.5F
				);
			});
			if (!level.noCollision(optional.get().getSpawnAABB(spawnVec3.x, spawnVec3.y, spawnVec3.z))) return Optional.empty();

			if (!inLineOfSight(level, pos.getCenter(), spawnVec3)) return Optional.empty();

			final BlockPos spawnBlockPos = BlockPos.containing(spawnVec3);
			if (!SpawnPlacements.checkSpawnRules(optional.get(), level, EntitySpawnReason.TRIAL_SPAWNER, spawnBlockPos, level.getRandom())) return Optional.empty();

			if (spawnData.getCustomSpawnRules().isPresent()) {
				SpawnData.CustomSpawnRules customSpawnRules = spawnData.getCustomSpawnRules().get();
				if (!customSpawnRules.isValidPosition(spawnBlockPos, level)) return Optional.empty();
			}

			final int lightAtPos = level.getRawBrightness(spawnBlockPos, 0);
			final int lightToleranceDifference = Math.max(this.data.maxActiveLightLevel, lightAtPos) - this.data.maxActiveLightLevel;
			if (lightToleranceDifference > 0 && random.nextInt(lightToleranceDifference * 25) > 0) return Optional.empty();

			if (level.getBlockState(spawnBlockPos).is(TTBlockTags.COFFIN_UNSPAWNABLE_ON)) return Optional.empty();

			final Entity entity = EntityType.loadEntityRecursive(valueInput, level, EntitySpawnReason.TRIAL_SPAWNER, entityx -> {
				entityx.snapTo(spawnVec3, random.nextFloat() * 360F, 0F);
				return entityx;
			});
			if (entity == null) return Optional.empty();

			if (entity instanceof Mob mob) {
				if (!mob.checkSpawnObstruction(level)) return Optional.empty();
				boolean bl = spawnData.getEntityToSpawn().size() == 1 && spawnData.getEntityToSpawn().contains("id");
				if (bl) mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), EntitySpawnReason.TRIAL_SPAWNER, null);
				spawnData.getEquipment().ifPresent(mob::equip);
			}

			if (!level.tryAddFreshEntityWithPassengers(entity)) return Optional.empty();

			level.playSound(
				null,
				entity,
				TTSounds.COFFIN_SPAWN_MOB,
				SoundSource.BLOCKS,
				1F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1F
			);
			if (entity instanceof Mob mob) mob.spawnAnim();
			level.gameEvent(entity, GameEvent.ENTITY_PLACE, spawnBlockPos);
			this.appendCoffinSpawnAttributes(entity, level, pos, false);
			return Optional.of(entity.getUUID());
		}
	}

	public boolean canSpawnApparition(Level level, BlockPos pos, boolean ignoreChance) {
		final CoffinSpawnerData data = this.getData();
		if (!data.isOnCooldown(level)
			&& data.hasPotentialPlayers()
			&& level.getGameTime() >= data.nextApparitionSpawnsAt
			&& data.currentApparitions.size() < this.getConfig().maxApparitions()
		) {
			final Vec3 vec3 = Vec3.atCenterOf(pos);
			final Optional<Player> optionalPlayer = data.getClosestPotentialPlayer(level, vec3);
			if (ignoreChance) return true;
			if (optionalPlayer.isEmpty()) return false;
			final double distance = Math.sqrt(optionalPlayer.get().distanceToSqr(vec3));
			final double playerRange = this.getRequiredPlayerRange();
			double chance = playerRange - distance;
			chance = (0.000425D / playerRange) * chance;
			return level.getRandom().nextDouble() < chance;
		}
		return false;
	}

	public void spawnApparition(ServerLevel level, BlockPos pos) {
		final Apparition apparition = TTEntityTypes.APPARITION.create(level, null, pos, EntitySpawnReason.TRIAL_SPAWNER, true, false);
		if (apparition == null || !level.addFreshEntity(apparition)) return;
		apparition.hiddenTicks = 100;
		this.appendCoffinSpawnAttributes(apparition, level, pos, true);
		this.data.nextApparitionSpawnsAt = level.getGameTime() + 1000L;
		this.data.currentApparitions.add(apparition.getUUID());
	}

	public void appendCoffinSpawnAttributes(Entity entity, Level level, BlockPos pos, boolean usePotentialPlayers) {
		if (entity instanceof Mob mob) {
			mob.getAttributes().getInstance(Attributes.FOLLOW_RANGE)
				.addPermanentModifier(new AttributeModifier(CoffinBlock.ATTRIBUTE_COFFIN_FOLLOW_RANGE, 24D, AttributeModifier.Operation.ADD_VALUE));
			final Optional<Player> closestDetectedPlayer = usePotentialPlayers
				? this.data.getClosestPotentialPlayer(level, entity.position())
				: this.data.getClosestDetectedPlayer(level, entity.position());
			closestDetectedPlayer.ifPresent(mob::setTarget);
		}
		if (entity instanceof EntityCoffinInterface entityInterface) entityInterface.trailierTales$setCoffinData(new EntityCoffinData(pos, this.uuid, level.getGameTime()));
		if (entity instanceof Apparition apparition) ApparitionAi.rememberHome(apparition, level, pos);
	}

	public void updateAttemptingToSpawn(ServerLevel level) {
		final boolean isAttempting = this.isAttemptingToSpawnMob(level);
		if (isAttempting == this.attemptingToSpawnMob) return;
		this.attemptingToSpawnMob = isAttempting;
		this.markUpdated();
	}

	public boolean isAttemptingToSpawnMob(ServerLevel level) {
		final int additionalPlayers = this.data.countAdditionalPlayers();
		final boolean isPreparing = this.data.isPreparingToSpawnNextMob(level, this.getConfig(), additionalPlayers, 45);
		final boolean finishedSpawningMobs = this.data.hasFinishedSpawningAllMobs(this.getConfig(), additionalPlayers);
		final boolean canSpawnInLevel = this.canSpawnInLevel(level) && this.getState().isCapableOfSpawning();
		return isPreparing && !finishedSpawningMobs && canSpawnInLevel;
	}

	public void tickServer(ServerLevel level, BlockPos pos, BlockState state, CoffinPart part, boolean ominous) {
		if (part == CoffinPart.HEAD) return;

		final Direction orientation = CoffinBlock.getCoffinOrientation(level, pos);

		handleSouls: {
			if (orientation == null) break handleSouls;

			this.getState().emitParticles(level, pos, orientation);
			if (this.data.soulsToSpawn.isEmpty()) break handleSouls;

			final IntArrayList newList = new IntArrayList();
			this.data.soulsToSpawn.forEach(spawnTime -> {
				if (spawnTime <= 0) {
					CoffinBlock.spawnParticlesFrom(level, TTParticleTypes.COFFIN_SOUL_ENTER, 4, 0D, orientation, pos, 0.35D);
					this.addPower(1, level);
				} else {
					newList.add(spawnTime - 1);
				}
			});
			this.data.soulsToSpawn.clear();
			this.data.soulsToSpawn.addAll(newList);
		}

		cleanupOtherHalfBreaking: {
			final Direction connectedDirection = CoffinBlock.getConnectedDirection(state);
			if (connectedDirection == null) break cleanupOtherHalfBreaking;
			final BlockPos connectedPos = pos.relative(connectedDirection);
			if (!level.isLoaded(connectedPos)) break cleanupOtherHalfBreaking;
			if (orientation != null && level.getBlockState(connectedPos).is(TTBlocks.COFFIN)) break cleanupOtherHalfBreaking;

			level.destroyBlock(pos, false);
			return;
		}

		this.data.currentMobs.removeIf(uiid -> {
			final Entity entity = level.getEntity(uiid);
			final boolean shouldUntrack = shouldMobBeUntracked(level, pos, entity);
			if (shouldUntrack) CoffinBlock.onCoffinUntrack(level, entity, this, false);
			return shouldUntrack;
		});

		this.data.currentApparitions.removeIf(uiid -> {
			final Entity entity = level.getEntity(uiid);
			final boolean shouldUntrack = shouldMobBeUntracked(level, pos, entity);
			if (shouldUntrack) CoffinBlock.onCoffinUntrack(level, entity, this, true);
			return shouldUntrack;
		});

		final CoffinSpawnerState currentState = this.getState();

		if (!this.canSpawnInLevel(level)) {
			if (this.getState() != CoffinSpawnerState.COOLDOWN) this.setState(level, CoffinSpawnerState.COOLDOWN);
		} else {
			CoffinSpawnerState nextState = currentState.tickAndGetNext(pos, this, state, level);
			if (nextState != currentState) this.setState(level, nextState);
		}
		this.updateAttemptingToSpawn(level);
	}

	private static boolean shouldMobBeUntracked(ServerLevel level, BlockPos pos, UUID uuid) {
		return shouldMobBeUntracked(level, pos, level.getEntity(uuid));
	}

	private static boolean shouldMobBeUntracked(ServerLevel level, BlockPos pos, Entity entity) {
		return entity == null
			|| !entity.level().dimension().equals(level.dimension())
			|| entity.blockPosition().distSqr(pos) > (double)MAX_MOB_TRACKING_DISTANCE_SQR
			|| entity.isRemoved();
	}

	private static boolean inLineOfSight(Level level, Vec3 spawnerPos, Vec3 mobPos) {
		final BlockHitResult hitResult = level.clip(new ClipContext(mobPos, spawnerPos, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, CollisionContext.empty()));
		return !(hitResult.getBlockPos().equals(BlockPos.containing(spawnerPos)) || hitResult.getType() == HitResult.Type.MISS);
	}

	public static boolean isInCatacombsBounds(BlockPos pos, StructureManager structureManager) {
		final ResourceKey<Structure> structureKey = TTResources.HAS_STRONGHOLD_OVERRIDE_PACK ? BuiltinStructures.STRONGHOLD : CatacombsGenerator.CATACOMBS_KEY;
		final Structure structure = structureManager.registryAccess().lookupOrThrow(Registries.STRUCTURE).getValue(structureKey);
		return structure != null && structureManager.structureHasPieceAt(pos, structureManager.getStructureAt(pos, structure));
	}

	public void onApparitionRemovedOrKilled(Level level) {
		if (!(level instanceof ServerLevel serverLevel)) return;
		this.data.nextApparitionSpawnsAt = serverLevel.getGameTime() + this.getConfig().ticksBetweenApparitionSpawn();
	}

	public void immediatelyActivate(Level level, BlockPos pos) {
		this.data.immediatelyActivate(level, pos, this);
	}

	public interface StateAccessor {
		void setState(Level level, CoffinSpawnerState state);

		CoffinSpawnerState getState();

		void markUpdated();
	}
}
