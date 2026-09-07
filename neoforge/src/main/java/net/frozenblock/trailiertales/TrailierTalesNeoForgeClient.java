package net.frozenblock.trailiertales;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = TTPreLoadConstants.MOD_ID, dist = Dist.CLIENT)
public final class TrailierTalesNeoForgeClient {

	public TrailierTalesNeoForgeClient(IEventBus modBus) {
		TrailierTalesClient.init();

		// AFTER register event
		modBus.addListener(FMLClientSetupEvent.class, event -> {
			TrailierTalesClient.setup();
		});
	}
}
