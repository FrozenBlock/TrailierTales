package net.frozenblock.trailiertales;

import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.renderer.entity.ApparitionRenderer;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = TTPreLoadConstants.MOD_ID, dist = Dist.CLIENT)
public final class TrailierTalesNeoForgeClient {

	public TrailierTalesNeoForgeClient(IEventBus modBus) {
		TrailierTalesClient.init();

		// AFTER register event
		modBus.addListener(FMLClientSetupEvent.class, event -> {
			TrailierTalesClient.setup();
		});

		modBus.addListener(EntityRenderersEvent.RegisterRenderers.class, event -> {
			TTModelLayers.setup();
			event.registerEntityRenderer(TTEntityTypes.APPARITION.get(), ApparitionRenderer::new);
			event.registerEntityRenderer(TTEntityTypes.THROWN_ITEM_PROJECTILE.get(), ThrownItemRenderer::new);
		});
	}
}
