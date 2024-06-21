package net.frozenblock.trailiertales.block.entity.coffin;

import java.util.Optional;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum CoffinSpawnerState implements StringRepresentable {
	INACTIVE("inactive", 0, false),
	ACTIVE("active", 3, true),
	IRRITATED("irritated", 5, true),
	AGGRESSIVE("aggressive", 7, true);

	private final String name;
	private final int lightLevel;
	private final boolean isCapableOfSpawning;
	private final ResourceLocation headTexture;
	private final ResourceLocation footTexture;

	CoffinSpawnerState(final String name, int lightLevel, final boolean isCapableOfSpawning) {
		this.name = name;
		this.lightLevel = lightLevel;
		this.isCapableOfSpawning = isCapableOfSpawning;
		this.headTexture = getTexture(name, false);
		this.footTexture = getTexture(name, true);
	}

	CoffinSpawnerState tickAndGetNext(BlockPos pos, @NotNull CoffinSpawner spawner, ServerLevel level, boolean blocked) {
		return switch (this) {
			case INACTIVE -> getActiveState(this, pos, spawner, level);
			case ACTIVE -> activeTickAndGetNext(this, pos, spawner, level, blocked);
			case IRRITATED -> activeTickAndGetNext(this, pos, spawner, level, blocked);
			case AGGRESSIVE -> activeTickAndGetNext(this, pos, spawner, level, blocked);
			default -> throw new MatchException(null, null);
		};
	}

	private static CoffinSpawnerState getActiveState(
		CoffinSpawnerState coffinSpawnerState,
		BlockPos pos,
		@NotNull CoffinSpawner spawner,
		@NotNull ServerLevel level
	) {
		CoffinSpawnerData coffinSpawnerData = spawner.getData();
		CoffinSpawnerConfig coffinSpawnerConfig = spawner.getConfig();
		if (
			!coffinSpawnerData.hasMobToSpawn(level, level.random, pos)
				|| CoffinBlock.getLightLevelSurroundingCoffin(level, level.getBlockState(pos), pos) > coffinSpawnerData.maxActiveLightLevel
		) {
			return INACTIVE;
		} else {
			coffinSpawnerData.tryDetectPlayers(level, pos, spawner);
			if (!coffinSpawnerData.isPowerCooldownFinished(level) && coffinSpawnerData.power >= coffinSpawnerConfig.powerForNextLevel()) {
				coffinSpawnerData.powerCooldownEndsAt = level.getGameTime() + (long)spawner.getPowerCooldownLength();
				coffinSpawnerData.power = 0;
				return coffinSpawnerState.getNextPowerState();
			}
		}
		return ACTIVE;
	}

	private static CoffinSpawnerState activeTickAndGetNext(
		CoffinSpawnerState coffinSpawnerState,
		BlockPos pos,
		@NotNull CoffinSpawner spawner,
		@NotNull ServerLevel level,
		boolean blocked
	) {
		CoffinSpawnerData coffinSpawnerData = spawner.getData();
		CoffinSpawnerConfig coffinSpawnerConfig = spawner.getConfig();
		if (
			!coffinSpawnerData.hasMobToSpawn(level, level.random, pos)
				|| CoffinBlock.getLightLevelSurroundingCoffin(level, level.getBlockState(pos), pos) > coffinSpawnerData.maxActiveLightLevel
		) {
			return INACTIVE;
		} else {
			coffinSpawnerData.tryDetectPlayers(level, pos, spawner);
			int additionalPlayers = coffinSpawnerData.countAdditionalPlayers();

			if (!coffinSpawnerData.isPowerCooldownFinished(level) && coffinSpawnerData.power >= coffinSpawnerConfig.powerForNextLevel()) {
				coffinSpawnerData.powerCooldownEndsAt = level.getGameTime() + (long)spawner.getPowerCooldownLength();
				coffinSpawnerData.power = 0;
				return coffinSpawnerState.getNextPowerState();
			}

			if (coffinSpawnerData.hasFinishedSpawningAllMobs(coffinSpawnerConfig, additionalPlayers)) {
				if (coffinSpawnerData.haveAllCurrentMobsDied()) {
					coffinSpawnerData.totalMobsSpawned = 0;
					coffinSpawnerData.nextMobSpawnsAt = 0L;
					coffinSpawnerData.power = 0;
				}
			} else if (coffinSpawnerData.isReadyToSpawnNextMob(level, coffinSpawnerConfig, additionalPlayers, blocked)) {
				spawner.spawnMob(level, pos).ifPresent(uuid -> {
					coffinSpawnerData.currentMobs.add(uuid);
					++coffinSpawnerData.totalMobsSpawned;
					coffinSpawnerData.nextMobSpawnsAt = level.getGameTime() + (long)coffinSpawnerConfig.ticksBetweenSpawn();
					coffinSpawnerData.spawnPotentials().getRandom(level.getRandom()).ifPresent(spawnData -> {
						coffinSpawnerData.nextSpawnData = Optional.of(spawnData.data());
						spawner.markUpdated();
					});
				});
			}
		}
		return coffinSpawnerState;
	}

	public int getLightLevel() {
		return lightLevel;
	}

	public boolean isCapableOfSpawning() {
		return this.isCapableOfSpawning;
	}

	public ResourceLocation getHeadTexture() {
		return this.headTexture;
	}

	public ResourceLocation getFootTexture() {
		return this.footTexture;
	}

	public CoffinSpawnerState getNextPowerState() {
		return switch (this) {
			case INACTIVE -> CoffinSpawnerState.ACTIVE;
			case ACTIVE -> CoffinSpawnerState.IRRITATED;
			case IRRITATED -> CoffinSpawnerState.AGGRESSIVE;
			case AGGRESSIVE -> CoffinSpawnerState.AGGRESSIVE;
		};
	}

	@Contract("_, _ -> new")
	private static @NotNull ResourceLocation getTexture(String stateName, boolean foot) {
		return TrailierTalesSharedConstants.id("textures/entity/coffin/coffin_" + (foot ? "foot_" : "head_") + stateName +".png");
	}

	@Override
	public @NotNull String getSerializedName() {
		return this.name;
	}
}
