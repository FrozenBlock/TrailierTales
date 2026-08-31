package net.frozenblock.trailiertales;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("trailiertales")
public final class TrailierTalesNeoForge {

	public TrailierTalesNeoForge(IEventBus modBus) {
		TrailierTales.init(TTConstants.MOD_ID);

		// AFTER register event
		modBus.addListener(FMLCommonSetupEvent.class, event -> {
			TrailierTales.setup();
		});
	}
}
