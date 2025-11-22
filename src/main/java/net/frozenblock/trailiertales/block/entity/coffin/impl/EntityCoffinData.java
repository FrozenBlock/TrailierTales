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

package net.frozenblock.trailiertales.block.entity.coffin.impl;

import java.util.Optional;
import java.util.UUID;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawner;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

	public static boolean entityHasCoffinData(Entity entity) {
		return entity instanceof EntityCoffinInterface entityCoffinInterface &&  entityCoffinInterface.trailierTales$getCoffinData() != null;
	}

	public void tick(LivingEntity entity, Level level) {
		if (!(level instanceof ServerLevel serverLevel)) return;

		final long gameTime = level.getGameTime();
		final boolean canUntrackFromTime = (gameTime - this.lastInteractionAt) > 1800 && !(entity instanceof Apparition);
		final Optional<CoffinSpawner> optionalCoffinSpawner = this.getSpawner(level);
		if (optionalCoffinSpawner.isEmpty() || canUntrackFromTime) {
			CoffinBlock.onCoffinUntrack(serverLevel, entity, null, true);
			return;
		}

		// TODO port
		/*if (FrozenLibConfig.IS_DEBUG) {
			FrozenNetworking.sendPacketToAllPlayers(
				serverLevel,
				new CoffinDebugPacket(entity.getId(), this.lastInteractionAt, this.pos, gameTime)
			);
		}*/
		if (entity instanceof Mob mob) {
			if (optionalCoffinSpawner.get().isOminous()) {
				final CoffinSpawner coffinSpawner = optionalCoffinSpawner.get();
				final Optional<Player> optionalPlayer = coffinSpawner.getData().getClosestDetectedPlayer(level, entity.position());
				optionalPlayer.ifPresent(mob::setTarget);
			}
		}
	}

	public Optional<CoffinSpawner> getSpawner(Level level) {
		if (!level.isLoaded(this.getPos())) return Optional.empty();
		if (!(level.getBlockEntity(this.getPos()) instanceof CoffinBlockEntity coffinBlockEntity)) return Optional.empty();
		if (!(coffinBlockEntity.getCoffinSpawner().getUUID().equals(this.getCoffinUUID()))) return Optional.empty();
		return Optional.of(coffinBlockEntity.getCoffinSpawner());
	}

	@VisibleForDebug
	public Optional<CoffinSpawner> getSpawnerIgnoringUUID(Level level) {
		if (!level.isLoaded(this.getPos())) return Optional.empty();
		if (!(level.getBlockEntity(this.getPos()) instanceof CoffinBlockEntity coffinBlockEntity)) return Optional.empty();
		return Optional.of(coffinBlockEntity.getCoffinSpawner());
	}

	public long lastInteraction() {
		return this.lastInteractionAt;
	}

	public void updateLastInteraction(long newTime) {
		this.lastInteractionAt = newTime;
	}

	public void save(ValueOutput valueOutput) {
		final ValueOutput coffinData = valueOutput.child("TrailierTales_CoffinData");
		coffinData.putInt("X", this.pos.getX());
		coffinData.putInt("Y", this.pos.getY());
		coffinData.putInt("Z", this.pos.getZ());
		coffinData.store("CoffinUUID", UUIDUtil.CODEC, this.coffinUUID);
		coffinData.putLong("LastInteractionAt", this.lastInteractionAt);
	}

	public static @Nullable EntityCoffinData load(ValueInput valueInput) {
		final Optional<ValueInput> optional = valueInput.child("TrailierTales_CoffinData");
		if (optional.isEmpty()) return null;

		final ValueInput coffinData = optional.get();
		final BlockPos pos = new BlockPos(
			coffinData.getIntOr("X", 0),
			coffinData.getIntOr("Y", 0),
			coffinData.getIntOr("Z", 0)
		);
		final UUID coffinUUID = coffinData.read("CoffinUUID", UUIDUtil.CODEC).orElse(null);
		final long lastInteractionAt = coffinData.getLong("LastInteractionAt").orElse(0L);
		return new EntityCoffinData(pos, coffinUUID, lastInteractionAt);
	}
}
