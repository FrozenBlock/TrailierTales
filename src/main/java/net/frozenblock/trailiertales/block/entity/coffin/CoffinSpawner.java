package net.frozenblock.trailiertales.block.entity.coffin;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.Optional;
import java.util.UUID;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAi;
import net.frozenblock.trailiertales.registry.TTEntities;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

public final class CoffinSpawner {
	private static final int PLAYER_TRACKING_DISTANCE = 48;
	private static final int MAX_MOB_TRACKING_DISTANCE = 64;
	private static final int MAX_MOB_TRACKING_DISTANCE_SQR = Mth.square(MAX_MOB_TRACKING_DISTANCE);
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
	private final PlayerDetector.EntitySelector entitySelector;
	private final UUID uuid;
	private boolean attemptingToSpawnMob;

	public Codec<CoffinSpawner> codec() {
		return RecordCodecBuilder.create(
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
				)
				.apply(
					instance,
					(config, config2, config3, config4, data, powerCooldownLength, integer, uuid, attemptingSpawn) -> new CoffinSpawner(
						config, config2, config3, config4, data, powerCooldownLength, integer, uuid, attemptingSpawn, this.stateAccessor, this.entitySelector
					)
				)
		);
	}

	public @NotNull CompoundTag getUpdateTag() {
		CompoundTag compoundTag = new CompoundTag();
		compoundTag.putBoolean("attempting_to_spawn_mob", this.attemptingToSpawnMob);
		return compoundTag;
	}

	public CoffinSpawner(CoffinSpawner.StateAccessor coffin, PlayerDetector.EntitySelector playerDetectionSelector) {
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
			coffin,
			playerDetectionSelector
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
		CoffinSpawner.StateAccessor coffin,
		PlayerDetector.EntitySelector playerDetectionSelector
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
		this.entitySelector = playerDetectionSelector;
	}

	public CoffinSpawnerConfig getConfig() {
		return switch (this.getState()) {
			case OMINOUS -> this.ominousConfig;
			case AGGRESSIVE -> this.aggressiveConfig;
			case INACTIVE -> this.irritatedConfig;
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

	public void applyOminous(ServerLevel world) {
		this.setState(world, CoffinSpawnerState.OMINOUS);
	}

	public void removeOminous(ServerLevel world) {
		this.setState(world, CoffinSpawnerState.getStateForPower(world, this));
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

	public void addPower(int i, @NotNull Level level) {
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
		return this.entitySelector;
	}

	public boolean canSpawnInLevel(@NotNull ServerLevel level) {
		return level.getDifficulty() != Difficulty.PEACEFUL && level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING);
	}

	public Optional<UUID> spawnMob(@NotNull ServerLevel level, BlockPos pos) {
		RandomSource randomSource = level.getRandom();
		SpawnData spawnData = this.data.getOrCreateNextSpawnData(level, level.getRandom(), pos);
		CompoundTag compoundTag = spawnData.entityToSpawn();
		ListTag listTag = compoundTag.getList("Pos", 6);
		Optional<EntityType<?>> optional = EntityType.by(compoundTag);
		if (optional.isEmpty()) {
			return Optional.empty();
		} else {
			int i = listTag.size();
			double d = i >= 1
				? listTag.getDouble(0)
				: (double)pos.getX() + (randomSource.nextDouble() - randomSource.nextDouble()) * (double)this.getConfig().spawnRange() + 0.5;
			double e = i >= 2 ? listTag.getDouble(1) : (double)(pos.getY() + randomSource.nextInt(3) - 1);
			double f = i >= 3
				? listTag.getDouble(2)
				: (double)pos.getZ() + (randomSource.nextDouble() - randomSource.nextDouble()) * (double)this.getConfig().spawnRange() + 0.5;
			if (!level.noCollision(optional.get().getSpawnAABB(d, e, f))) {
				return Optional.empty();
			} else {
				Vec3 vec3 = new Vec3(d, e, f);
				if (!inLineOfSight(level, pos.getCenter(), vec3)) {
					return Optional.empty();
				} else {
					BlockPos blockPos = BlockPos.containing(vec3);
					if (!SpawnPlacements.checkSpawnRules(optional.get(), level, EntitySpawnReason.TRIAL_SPAWNER, blockPos, level.getRandom())) {
						return Optional.empty();
					} else {
						if (spawnData.getCustomSpawnRules().isPresent()) {
							SpawnData.CustomSpawnRules customSpawnRules = spawnData.getCustomSpawnRules().get();
							if (!customSpawnRules.isValidPosition(blockPos, level)) {
								return Optional.empty();
							}
						}

						int lightAtPos = level.getRawBrightness(blockPos, 0);
						int lightToleranceDifference = Math.max(this.data.maxActiveLightLevel, lightAtPos) - this.data.maxActiveLightLevel;
						if (lightToleranceDifference > 0 && randomSource.nextInt(lightToleranceDifference * 25) > 0) {
							return Optional.empty();
						}

						if (level.getBlockState(blockPos).is(TTBlockTags.COFFIN_UNSPAWNABLE_ON)) {
							return Optional.empty();
						}

						Entity entity = EntityType.loadEntityRecursive(compoundTag, level, EntitySpawnReason.TRIAL_SPAWNER, entityx -> {
							entityx.moveTo(d, e, f, randomSource.nextFloat() * 360F, 0F);
							return entityx;
						});
						if (entity == null) {
							return Optional.empty();
						} else {
							if (entity instanceof Mob mob) {
								if (!mob.checkSpawnObstruction(level)) {
									return Optional.empty();
								}

								boolean bl = spawnData.getEntityToSpawn().size() == 1 && spawnData.getEntityToSpawn().contains("id", 8);
								if (bl) {
									mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), EntitySpawnReason.TRIAL_SPAWNER, null);
								}

								spawnData.getEquipment().ifPresent(mob::equip);
							}

							if (!level.tryAddFreshEntityWithPassengers(entity)) {
								return Optional.empty();
							} else {
								level.playSound(
									null,
									entity,
									TTSounds.COFFIN_SPAWN_MOB,
									SoundSource.BLOCKS,
									1F,
									(randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F + 1F
								);
								if (entity instanceof Mob mob) {
									mob.spawnAnim();
								}
								level.gameEvent(entity, GameEvent.ENTITY_PLACE, blockPos);
								this.appendCoffinSpawnAttributes(entity, level, pos, false);
								return Optional.of(entity.getUUID());
							}
						}
					}
				}
			}
		}
	}

	public boolean canSpawnApparition(Level level, BlockPos pos) {
		CoffinSpawnerData data = this.getData();
		if (data.hasPotentialPlayers() && level.getGameTime() >= data.nextApparitionSpawnsAt && data.currentApparitions.size() < this.getConfig().maxApparitions()) {
			Vec3 vec3 = Vec3.atCenterOf(pos);
			Optional<Player> optionalPlayer = data.getClosestPotentialPlayer(level, vec3);
			if (optionalPlayer.isPresent()) {
				double distance = Math.sqrt(optionalPlayer.get().distanceToSqr(vec3));
				double playerRange = this.getRequiredPlayerRange();
				double chance = playerRange - distance;
				chance = (0.000475D / playerRange) * chance;
				return level.getRandom().nextDouble() < chance;
			}
		}
		return false;
	}

	public void spawnApparition(@NotNull ServerLevel level, @NotNull BlockPos pos) {
		Apparition apparition = TTEntities.APPARITION.create(level, null, pos, EntitySpawnReason.TRIAL_SPAWNER, true, false);
		if (apparition != null) {
			if (level.addFreshEntity(apparition)) {
				apparition.hiddenTicks = 500;
				this.appendCoffinSpawnAttributes(apparition, level, pos, true);
				this.data.nextApparitionSpawnsAt = level.getGameTime() + 1000L;
				this.data.currentApparitions.add(apparition.getUUID());
			}
		}
	}

	public void appendCoffinSpawnAttributes(Entity entity, Level level, BlockPos pos, boolean usePotentialPlayers) {
		if (entity instanceof Mob mob) {
			mob.getAttributes().getInstance(Attributes.FOLLOW_RANGE)
				.addPermanentModifier(new AttributeModifier(CoffinBlock.ATTRIBUTE_COFFIN_FOLLOW_RANGE, 24D, AttributeModifier.Operation.ADD_VALUE));
			Optional<Player> closestDetectedPlayer = usePotentialPlayers ? this.data.getClosestPotentialPlayer(level, entity.position()) :  this.data.getClosestDetectedPlayer(level, entity.position());
			closestDetectedPlayer.ifPresent(mob::setTarget);
		}
		if (entity instanceof EntityCoffinInterface entityInterface) {
			entityInterface.trailierTales$setCoffinData(
				new EntityCoffinData(pos, this.uuid, level.getGameTime())
			);
		}
		if (entity instanceof Apparition apparition) {
			ApparitionAi.rememberHome(apparition, level, pos);
		}
	}

	public void updateAttemptingToSpawn(@NotNull ServerLevel level) {
		boolean isAttempting = this.isAttemptingToSpawnMob(level);
		if (isAttempting != this.attemptingToSpawnMob) {
			this.attemptingToSpawnMob = isAttempting;
			this.markUpdated();
		}
	}

	public boolean isAttemptingToSpawnMob(@NotNull ServerLevel level) {
		int additionalPlayers = this.data.countAdditionalPlayers();
		boolean isPreparing = this.data.isPreparingToSpawnNextMob(level, this.getConfig(), additionalPlayers, 45);
		boolean finishedSpawningMobs = this.data.hasFinishedSpawningAllMobs(this.getConfig(), additionalPlayers);
		boolean canSpawnInLevel = this.canSpawnInLevel(level) && this.getState().isCapableOfSpawning();
		return isPreparing && !finishedSpawningMobs && canSpawnInLevel;
	}

	public void tickServer(ServerLevel world, BlockPos pos, BlockState state, CoffinPart part, boolean ominous) {
		if (part == CoffinPart.HEAD || world.isClientSide) {
			return;
		}

		Direction direction = CoffinBlock.getCoffinOrientation(world, pos);
		if (direction != null) {
			if (!this.data.soulsToSpawn.isEmpty()) {
				IntArrayList newList = new IntArrayList();
				this.data.soulsToSpawn.forEach(spawnTime -> {
					if (spawnTime <= 0) {
						CoffinBlock.spawnParticlesFrom(world, TTParticleTypes.COFFIN_SOUL_ENTER, 4, 0D, direction, pos, 0.35D);
						this.addPower(1, world);
					} else {
						newList.add(spawnTime - 1);
					}
				});
				this.data.soulsToSpawn.clear();
				this.data.soulsToSpawn.addAll(newList);
			}
		}

		this.data.currentMobs.removeIf(uiid -> {
			Entity entity = world.getEntity(uiid);
			boolean shouldUntrack = shouldMobBeUntracked(world, pos, entity);
			if (shouldUntrack) {
				CoffinBlock.onCoffinUntrack(entity, false);
			}
			return shouldUntrack;
		});

		this.data.currentApparitions.removeIf(uiid -> {
			Entity entity = world.getEntity(uiid);
			boolean shouldUntrack = shouldMobBeUntracked(world, pos, entity);
			if (shouldUntrack) {
				CoffinBlock.onCoffinUntrack(entity, true);
			}
			return shouldUntrack;
		});

		CoffinSpawnerState currentState = this.getState();
		if (!this.canSpawnInLevel(world)) {
			if (currentState.isCapableOfSpawning()) {
				this.setState(world, CoffinSpawnerState.INACTIVE);
			}
		} else {
			CoffinSpawnerState nextState = currentState.tickAndGetNext(pos, this, state, world);
			if (nextState != currentState) {
				this.setState(world, nextState);
			}
		}
		this.updateAttemptingToSpawn(world);
	}

	private static boolean shouldMobBeUntracked(@NotNull ServerLevel level, BlockPos pos, UUID uuid) {
		return shouldMobBeUntracked(level, pos, level.getEntity(uuid));
	}

	private static boolean shouldMobBeUntracked(@NotNull ServerLevel level, BlockPos pos, Entity entity) {
		return entity == null
			|| !entity.level().dimension().equals(level.dimension())
			|| entity.blockPosition().distSqr(pos) > (double)MAX_MOB_TRACKING_DISTANCE_SQR
			|| entity.isRemoved();
	}

	private static boolean inLineOfSight(@NotNull Level level, Vec3 spawnerPos, Vec3 mobPos) {
		BlockHitResult blockHitResult = level.clip(new ClipContext(mobPos, spawnerPos, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, CollisionContext.empty()));
		return !(blockHitResult.getBlockPos().equals(BlockPos.containing(spawnerPos)) || blockHitResult.getType() == HitResult.Type.MISS);
	}

	public static boolean isInCatacombsBounds(BlockPos pos, @NotNull StructureManager structureManager) {
		Structure structure = structureManager.registryAccess().lookupOrThrow(Registries.STRUCTURE).getValue(CatacombsGenerator.CATACOMBS_KEY);
		return structure != null && structureManager.structureHasPieceAt(pos, structureManager.getStructureAt(pos, structure));
	}

	public interface StateAccessor {
		void setState(Level level, CoffinSpawnerState state);

		CoffinSpawnerState getState();

		void markUpdated();
	}
}
