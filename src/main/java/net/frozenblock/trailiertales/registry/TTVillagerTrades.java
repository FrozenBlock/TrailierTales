/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.tag.TTStructureTags;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;

public final class TTVillagerTrades {

	public static void init() {
		if (!TTEntityConfig.get().villager.sell_catacombs_map) return;

		TradeOfferHelper.registerVillagerOffers(
			VillagerProfession.CARTOGRAPHER,
			3,
			(trades, rebalanced) -> trades.add(
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
