package net.frozenblock.trailiertales.worldgen.impl.suspicious_handler;

import com.mojang.serialization.Dynamic;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class SuspiciousDataStorage extends SavedData {
	public static final String SUSPICIOUS_FILE_ID = "trailiertales_generated_suspicious_blocks";
	private final SuspiciousData suspiciousLocations;

	public SuspiciousDataStorage(SuspiciousData suspiciousLocations) {
		this.suspiciousLocations = suspiciousLocations;
		this.setDirty();
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag, HolderLookup.Provider provider) {
		SuspiciousData.Pair.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.suspiciousLocations.suspiciousData)
			.resultOrPartial().ifPresent(nbt -> compoundTag.put("SuspiciousData", nbt));

		return compoundTag;
	}

	public static @NotNull SuspiciousDataStorage load(@NotNull CompoundTag compoundTag, SuspiciousData suspiciousLocations) {
		SuspiciousDataStorage suspiciousLocationStorage = new SuspiciousDataStorage(suspiciousLocations);

		if (compoundTag.contains("SuspiciousData", 10)) {
			SuspiciousData.Pair.CODEC.listOf()
				.parse(new Dynamic<>(NbtOps.INSTANCE, compoundTag.getCompound("SuspiciousData")))
				.resultOrPartial()
				.ifPresent(data -> suspiciousLocations.suspiciousData = data);
		}

		return suspiciousLocationStorage;
	}
}
