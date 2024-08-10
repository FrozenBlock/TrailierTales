package net.frozenblock.trailiertales.block.entity.coffin.impl;

import java.util.Optional;
import java.util.UUID;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCoffinData {
	private final BlockPos pos;
	private final UUID coffinUUID;

	public EntityCoffinData(BlockPos pos, UUID coffinUUID) {
		this.pos = pos;
		this.coffinUUID = coffinUUID;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public UUID getCoffinUUID() {
		return this.coffinUUID;
	}

	public void tick(LivingEntity entity, @NotNull Level level) {
		if (level.isClientSide()) {
			return;
		}

		Optional<CoffinSpawner> optionalCoffinSpawner = this.getSpawner(level);
		if (optionalCoffinSpawner.isEmpty()) {
			CoffinBlock.onCoffinUntrack(entity);
		} else if (entity instanceof Mob mob) {
			if (optionalCoffinSpawner.get().isOminous()) {
				CoffinSpawner coffinSpawner = optionalCoffinSpawner.get();
				Optional<Player> optionalPlayer = coffinSpawner.getData().getClosestDetectedPlayer(level, entity.position());
				optionalPlayer.ifPresent(mob::setTarget);
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

	public void saveCompoundTag(@NotNull CompoundTag tag) {
		CompoundTag coffinDataTag = new CompoundTag();
		coffinDataTag.putInt("X", this.pos.getX());
		coffinDataTag.putInt("Y", this.pos.getY());
		coffinDataTag.putInt("Z", this.pos.getZ());
		coffinDataTag.putUUID("CoffinUUID", this.coffinUUID);
		tag.put("TrailierTales_CoffinData", coffinDataTag);
	}

	public static @Nullable EntityCoffinData loadCompoundTag(@NotNull CompoundTag tag) {
		if (tag.contains("TrailierTales_CoffinData", 10)) {
			CompoundTag coffinDataTag = tag.getCompound("TrailierTales_CoffinData");
			BlockPos pos = new BlockPos(
				coffinDataTag.getInt("X"),
				coffinDataTag.getInt("Y"),
				coffinDataTag.getInt("Z")
			);
			UUID coffinUUID = coffinDataTag.getUUID("CoffinUUID");
			return new EntityCoffinData(pos, coffinUUID);
		}
		return null;
	}
}
