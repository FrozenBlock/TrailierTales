/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.datagen.trading;

import java.util.List;
import java.util.Optional;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTMapDecorationTypes;
import net.frozenblock.trailiertales.tag.TTStructureTags;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.PotionTags;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.functions.DiscardItem;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.FilteredFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;

public final class TTVillagerTrades {
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_3_EMERALD_AND_COMPASS_CATACOMBS_MAP = resourceKey("cartographer/3/emerald_and_compass_catacombs_map");

	public static void bootstrap(BootstrapContext<VillagerTrade> context) {
		final HolderGetter<Item> items = context.lookup(Registries.ITEM);
		final Optional<HolderSet<Enchantment>> enchantmentsForTradedEquipment = context.lookup(Registries.ENCHANTMENT)
			.get(EnchantmentTags.ON_TRADED_EQUIPMENT)
			.map(named -> named);
		final Optional<HolderSet<Enchantment>> enchantmentsForBooks = context.lookup(Registries.ENCHANTMENT).get(EnchantmentTags.TRADEABLE).map(named -> named);
		final Optional<HolderSet<Enchantment>> doubleTradePrice = context.lookup(Registries.ENCHANTMENT).get(EnchantmentTags.DOUBLE_TRADE_PRICE).map(named -> named);
		final Optional<HolderSet<Potion>> potionsForTippedArrows = context.lookup(Registries.POTION).get(PotionTags.TRADEABLE).map(named -> named);
		final HolderGetter<VillagerType> villagerVariants = context.lookup(Registries.VILLAGER_TYPE);

		context.register(
			CARTOGRAPHER_3_EMERALD_AND_COMPASS_CATACOMBS_MAP,
			new VillagerTrade(
				new TradeCost(Items.EMERALD, 12),
				Optional.of(new TradeCost(Items.COMPASS, 1)),
				new ItemStackTemplate(Items.MAP),
				12,
				10,
				0.2F,
				Optional.empty(),
				List.of(
					ExplorationMapFunction.makeExplorationMap()
						.setDestination(TTStructureTags.ON_CATACOMBS_MAPS)
						.setMapDecoration(TTMapDecorationTypes.CATACOMBS)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.trailiertales.catacombs"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
						new ItemPredicate.Builder().of(items, Items.FILLED_MAP)
							.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build()).build()
					).onFail(Optional.of(DiscardItem.discardItem().build())).build()
				)
			)
		);
	}

	public static ResourceKey<VillagerTrade> resourceKey(String path) {
		return ResourceKey.create(Registries.VILLAGER_TRADE, TTConstants.id(path));
	}
}
