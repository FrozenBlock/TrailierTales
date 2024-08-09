package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.frozenblock.trailiertales.config.EntityConfig;
import net.frozenblock.trailiertales.tag.TrailierStructureTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

public class RegisterVillagerTrades {
	public static void init() {
		if (EntityConfig.get().villager.sell_catacombs_map) {
			TradeOfferHelper.registerVillagerOffers(
				VillagerProfession.CARTOGRAPHER,
				3,
				(factories, rebalanced) -> factories.add(
					new VillagerTrades.TreasureMapForEmeralds(
						12,
						TrailierStructureTags.ON_CATACOMBS_EXPLORER_MAPS,
						"filled_map.trailiertales.catacombs",
						RegisterMapDecorationTypes.CATACOMBS,
						12,
						10
					)
				)
			);
		}
	}
}
