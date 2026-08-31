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

package net.frozenblock.trailiertales.block.entity.coffin.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.UUID;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawner;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EntityCoffinData {
	public static final Codec<EntityCoffinData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		BlockPos.CODEC.fieldOf("coffin_position").forGetter(data -> data.coffinPosition),
		UUIDUtil.CODEC.fieldOf("coffin_uuid").forGetter(data -> data.coffinUUID),
		Codec.LONG.fieldOf("last_interaction_timestamp").forGetter(data -> data.listInteractionTimestamp)
	).apply(instance, EntityCoffinData::new));
	private final BlockPos coffinPosition;
	private final UUID coffinUUID;
	private long listInteractionTimestamp;

	public EntityCoffinData(BlockPos coffinPosition, UUID coffinUUID, long listInteractionTimestamp) {
		this.coffinPosition = coffinPosition;
		this.coffinUUID = coffinUUID;
		this.listInteractionTimestamp = listInteractionTimestamp;
	}

	public BlockPos getCoffinPosition() {
		return this.coffinPosition;
	}

	public UUID getCoffinUUID() {
		return this.coffinUUID;
	}

	public static boolean entityHasCoffinData(Entity entity) {
		if (entity == null) return false;
		return entity.frozenLib$getAttached(TTAttachmentTypes.ENTITY_COFFIN_DATA) != null;
	}

	public void tick(LivingEntity entity, Level level) {
		if (!(level instanceof ServerLevel serverLevel)) return;

		final long gameTime = level.getGameTime();
		final boolean canUntrackFromTime = (gameTime - this.listInteractionTimestamp) > 1800 && !(entity instanceof Apparition);
		final Optional<CoffinSpawner> optionalCoffinSpawner = this.getSpawner(level);
		if (optionalCoffinSpawner.isEmpty() || canUntrackFromTime) {
			CoffinBlock.onCoffinUntrack(serverLevel, entity, null, true);
			return;
		}

		if (entity instanceof Mob mob && optionalCoffinSpawner.get().isOminous()) {
			final CoffinSpawner coffinSpawner = optionalCoffinSpawner.get();
			final Optional<Player> optionalPlayer = coffinSpawner.getData().getClosestDetectedPlayer(level, entity.position());
			optionalPlayer.ifPresent(mob::setTarget);
		}
	}

	public Optional<CoffinSpawner> getSpawner(Level level) {
		if (!level.isLoaded(this.getCoffinPosition())) return Optional.empty();
		if (!(level.getBlockEntity(this.getCoffinPosition()) instanceof CoffinBlockEntity coffinBlockEntity)) return Optional.empty();
		if (!(coffinBlockEntity.getCoffinSpawner().getUUID().equals(this.getCoffinUUID()))) return Optional.empty();
		return Optional.of(coffinBlockEntity.getCoffinSpawner());
	}

	@VisibleForDebug
	public Optional<CoffinSpawner> getSpawnerIgnoringUUID(Level level) {
		if (!level.isLoaded(this.getCoffinPosition())) return Optional.empty();
		if (!(level.getBlockEntity(this.getCoffinPosition()) instanceof CoffinBlockEntity coffinBlockEntity)) return Optional.empty();
		return Optional.of(coffinBlockEntity.getCoffinSpawner());
	}

	public long lastInteraction() {
		return this.listInteractionTimestamp;
	}

	public void updateLastInteraction(long newTime) {
		this.listInteractionTimestamp = newTime;
	}
}
