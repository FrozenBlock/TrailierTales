package net.frozenblock.trailiertales;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterFeatures;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.TrailierLootTableModifications;
import net.frozenblock.trailiertales.registry.RegisterRecipies;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.worldgen.TrailierBiomeModifications;
import net.frozenblock.trailiertales.worldgen.impl.suspicious_handler.SuspiciousData;
import net.frozenblock.trailiertales.worldgen.impl.suspicious_handler.SuspiciousDataStorage;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class TrailierTales extends FrozenModInitializer {

	public TrailierTales() {
		super(TrailierTalesSharedConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		TrailierTalesSharedConstants.startMeasuring(this);

		RegisterBlocks.init();
		RegisterItems.init();
		RegisterRecipies.init();
		RegisterStructures.init();
		RegisterFeatures.init();
		TrailierBiomeModifications.init();
		TrailierLootTableModifications.init();

		ServerWorldEvents.LOAD.register((server, level) -> {
			DimensionDataStorage dimensionDataStorage = level.getDataStorage();
			SuspiciousData suspiciousData = SuspiciousData.getSuspiciousData(level);
			dimensionDataStorage.computeIfAbsent(suspiciousData.createData(), SuspiciousDataStorage.SUSPICIOUS_FILE_ID);
		});

		ServerTickEvents.START_WORLD_TICK.register(level -> {
			SuspiciousData.getSuspiciousData(level).tick(level);
		});

		ServerTickEvents.END_WORLD_TICK.register(level -> {
			SuspiciousData.getSuspiciousData(level).swapLists();
		});

		TrailierTalesSharedConstants.stopMeasuring(this);
	}
}
