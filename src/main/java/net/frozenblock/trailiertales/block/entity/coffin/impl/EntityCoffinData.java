package net.frozenblock.trailiertales.block.entity.coffin.impl;

import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;

public class EntityCoffinData {
	private BlockPos pos;
	private UUID coffinUUID;
	private UUID targetUUID;

	public EntityCoffinData(BlockPos pos, UUID coffinUUID, UUID targetUUID) {
		this.pos = pos;
		this.coffinUUID = coffinUUID;
		this.targetUUID = targetUUID;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public UUID getCoffinUUID() {
		return this.coffinUUID;
	}

	public UUID getTargetUUID() {
		return this.targetUUID;
	}

	public void saveCompoundTag(@NotNull CompoundTag tag) {
		CompoundTag coffinDataTag = new CompoundTag();
		tag.put("BlockPos", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, this.pos).result().orElseThrow(() -> new IllegalStateException("Invalid BlockPos")));
		coffinDataTag.putUUID("CoffinUUID", this.coffinUUID);
		coffinDataTag.putUUID("TargetUUID", this.targetUUID);
		tag.put("TrailierTales_CoffinData", coffinDataTag);
	}

	public static @Nullable EntityCoffinData loadCompoundTag(@NotNull CompoundTag tag) {
		if (tag.contains("TrailierTales_CoffinData", 10)) {
			CompoundTag coffinDataTag = tag.getCompound("TrailierTales_CoffinData");
			BlockPos pos = BlockPos.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, coffinDataTag.getCompound("BlockPos"))).result().orElseThrow(() -> new IllegalStateException("Invalid BlockPos"));
			UUID coffinUUID = coffinDataTag.getUUID("CoffinUUID");
			UUID targetUUID = coffinDataTag.getUUID("TargetUUID");
			return new EntityCoffinData(pos, coffinUUID, targetUUID);
		}
		return null;
	}
}
