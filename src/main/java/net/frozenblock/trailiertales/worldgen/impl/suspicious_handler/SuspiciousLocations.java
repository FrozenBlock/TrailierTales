package net.frozenblock.trailiertales.worldgen.impl.suspicious_handler;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class SuspiciousLocations {
	public List<BlockPos> suspiciousLocations = new ArrayList<>();

	private final ServerLevel level;

	@SuppressWarnings("unchecked")
	public SuspiciousLocations(@NotNull ServerLevel level) {
		this.level = level;
	}

	@NotNull
	public static SuspiciousLocations getSuspiciousLocations(@NotNull ServerLevel level) {
		return ((SuspiciousLocationInterface)level).trailierTales$getSuspiciousLocations();
	}

	@NotNull
	public SavedData.Factory<SuspiciousLocationStorage> createData() {
		return new SavedData.Factory<>(
			() -> new SuspiciousLocationStorage(this),
			(tag, provider) -> SuspiciousLocationStorage.load(tag, this),
			DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES
		);
	}

	public void tick(@NotNull ServerLevel level) {

	}
}
