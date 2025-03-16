package net.frozenblock.trailiertales.block.entity.coffin.impl;

import java.util.Optional;
import java.util.UUID;
import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfig;
import net.frozenblock.lib.networking.FrozenNetworking;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawner;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.networking.packet.CoffinDebugPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCoffinData {
	private final BlockPos pos;
	private final UUID coffinUUID;
	private long lastInteractionAt;

	public EntityCoffinData(BlockPos pos, UUID coffinUUID, long lastInteractionAt) {
		this.pos = pos;
		this.coffinUUID = coffinUUID;
		this.lastInteractionAt = lastInteractionAt;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public UUID getCoffinUUID() {
		return this.coffinUUID;
	}

	public static boolean entityHasCoffinData(@NotNull Entity entity) {
		return entity instanceof EntityCoffinInterface entityCoffinInterface &&  entityCoffinInterface.trailierTales$getCoffinData() != null;
	}

	public void tick(LivingEntity entity, @NotNull Level level) {
		if (level instanceof ServerLevel serverLevel) {
			long gameTime = level.getGameTime();
			boolean canUntrackFromTime = (gameTime - this.lastInteractionAt) > 1800 && !(entity instanceof Apparition);
			Optional<CoffinSpawner> optionalCoffinSpawner = this.getSpawner(level);
			if (optionalCoffinSpawner.isEmpty() || canUntrackFromTime) {
				CoffinBlock.onCoffinUntrack(serverLevel, entity, null, true);
			} else {
				if (FrozenLibConfig.IS_DEBUG) {
					FrozenNetworking.sendPacketToAllPlayers(
						serverLevel,
						new CoffinDebugPacket(entity.getId(), this.lastInteractionAt, this.pos, gameTime)
					);
				}
				if (entity instanceof Mob mob) {
					if (optionalCoffinSpawner.get().isOminous()) {
						CoffinSpawner coffinSpawner = optionalCoffinSpawner.get();
						Optional<Player> optionalPlayer = coffinSpawner.getData().getClosestDetectedPlayer(level, entity.position());
						optionalPlayer.ifPresent(mob::setTarget);
					}
				}
			}
		}
	}

	public Optional<CoffinSpawner> getSpawner(@NotNull Level level) {
		if (level.isLoaded(this.getPos())) {
			if (level.getBlockEntity(this.getPos()) instanceof CoffinBlockEntity coffinBlockEntity) {
				if (coffinBlockEntity.getCoffinSpawner().getUUID().equals(this.getCoffinUUID())) {
					return Optional.of(coffinBlockEntity.getCoffinSpawner());
				}
			}
		}
		return Optional.empty();
	}

	@VisibleForDebug
	public Optional<CoffinSpawner> getSpawnerIgnoringUUID(@NotNull Level level) {
		if (level.isLoaded(this.getPos())) {
			if (level.getBlockEntity(this.getPos()) instanceof CoffinBlockEntity coffinBlockEntity) {
				return Optional.of(coffinBlockEntity.getCoffinSpawner());
			}
		}
		return Optional.empty();
	}

	public long lastInteraction() {
		return this.lastInteractionAt;
	}

	public void updateLastInteraction(long newTime) {
		this.lastInteractionAt = newTime;
	}

	public void saveCompoundTag(@NotNull CompoundTag tag) {
		CompoundTag coffinDataTag = new CompoundTag();
		coffinDataTag.putInt("X", this.pos.getX());
		coffinDataTag.putInt("Y", this.pos.getY());
		coffinDataTag.putInt("Z", this.pos.getZ());
		coffinDataTag.store("CoffinUUID", UUIDUtil.CODEC, this.coffinUUID);
		coffinDataTag.putLong("LastInteractionAt", this.lastInteractionAt);
		tag.put("TrailierTales_CoffinData", coffinDataTag);
	}

	public static @Nullable EntityCoffinData loadCompoundTag(@NotNull CompoundTag tag) {
		if (tag.contains("TrailierTales_CoffinData")) {
			CompoundTag coffinDataTag = tag.getCompoundOrEmpty("TrailierTales_CoffinData");
			BlockPos pos = new BlockPos(
				coffinDataTag.getIntOr("X", 0),
				coffinDataTag.getIntOr("Y", 0),
				coffinDataTag.getIntOr("Z", 0)
			);
			UUID coffinUUID = coffinDataTag.read("CoffinUUID", UUIDUtil.CODEC).orElse(null);
			long lastInteractionAt = coffinDataTag.getLong("LastInteractionAt").orElse(0L);
			return new EntityCoffinData(pos, coffinUUID, lastInteractionAt);
		}
		return null;
	}
}
