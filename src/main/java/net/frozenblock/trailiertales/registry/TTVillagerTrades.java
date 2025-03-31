/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.tag.TTStructureTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

public final class TTVillagerTrades {
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
