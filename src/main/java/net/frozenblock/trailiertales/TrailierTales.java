package net.frozenblock.trailiertales;

import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterFeatures;
import net.frozenblock.trailiertales.registry.RegisterRecipies;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.worldgen.TrailierBiomeModifications;

public class TrailierTales extends FrozenModInitializer {

	public TrailierTales() {
		super(TrailierTalesSharedConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		TrailierTalesSharedConstants.startMeasuring(this);

		RegisterBlocks.init();
		RegisterRecipies.init();
		RegisterStructures.init();
		RegisterFeatures.init();
		TrailierBiomeModifications.init();

		TrailierTalesSharedConstants.stopMeasuring(this);
	}
}
