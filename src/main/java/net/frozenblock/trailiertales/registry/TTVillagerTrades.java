package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.tag.TTStructureTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

public class TTVillagerTrades {
	public static void init() {
		if (TTEntityConfig.get().villager.sell_catacombs_map) {
			TradeOfferHelper.registerVillagerOffers(
				VillagerProfession.CARTOGRAPHER,
				3,
				(factories, rebalanced) -> factories.add(
					new VillagerTrades.TreasureMapForEmeralds(
						12,
						TTStructureTags.ON_CATACOMBS_EXPLORER_MAPS,
						"filled_map.trailiertales.catacombs",
						TTMapDecorationTypes.CATACOMBS,
						12,
						10
					)
				)
			);
		}
	}
}
