package net.frozenblock.trailiertales;

import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;

@Mod("trailiertales")
public final class TrailierTalesNeoForge {

	public TrailierTalesNeoForge(IEventBus modBus) {
		TrailierTales.init(TTConstants.MOD_ID);

		// AFTER register event
		modBus.addListener(FMLCommonSetupEvent.class, event -> {
			TrailierTales.setup();
		});

		NeoForge.EVENT_BUS.addListener(
			EntityMobGriefingEvent.class,
			(event) -> {
				if (event.getEntity() instanceof Apparition && TTEntityConfig.APPARITION_IGNORES_MOB_GRIEFING.get()) event.setCanGrief(true);
			}
		);
	}
}
