package net.frozenblock.trailiertales.worldgen.impl.suspicious_handler;

import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SuspiciousLocationStorage extends SavedData {
	public static final String SUSPICIOUS_FILE_ID = "trailiertales_generated_suspicious_blocks";
	private final SuspiciousLocations suspiciousLocations;

	public SuspiciousLocationStorage(SuspiciousLocations suspiciousLocations) {
		this.suspiciousLocations = suspiciousLocations;
		this.setDirty();
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag, HolderLookup.Provider provider) {
		BlockPos.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.suspiciousLocations.suspiciousLocations)
			.resultOrPartial().ifPresent(nbt -> compoundTag.put("SuspiciousLocations", nbt));

		return compoundTag;
	}

	public static @NotNull SuspiciousLocationStorage load(@NotNull CompoundTag compoundTag, SuspiciousLocations suspiciousLocations) {
		SuspiciousLocationStorage suspiciousLocationStorage = new SuspiciousLocationStorage(suspiciousLocations);
		if (compoundTag.contains("SuspiciousLocations", 10)) {
			BlockPos.CODEC.listOf()
				.parse(new Dynamic<>(NbtOps.INSTANCE, compoundTag.getCompound("SuspiciousLocations")))
				.resultOrPartial()
				.ifPresent(data -> suspiciousLocations.suspiciousLocations = data);
		}

		return suspiciousLocationStorage;
	}
}
