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

package net.frozenblock.trailiertales.datagen.loot;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public class TTArchaeologyLootProvider extends SimpleFabricLootTableProvider {

	private final CompletableFuture<HolderLookup.Provider> registryLookup;

	public TTArchaeologyLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup, LootContextParamSets.ARCHAEOLOGY);
		this.registryLookup = registryLookup;
	}

	@Override
	public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> registry) {
		HolderLookup.Provider registries = registryLookup.join();

		registry.accept(
			TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.OMEN_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.PLENTY_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.ESSENCE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.EYE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(3))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(8))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(3))
						.add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(1))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(14))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(10))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.STRING).setWeight(16))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10))
						.add(LootItem.lootTableItem(Items.PAPER).setWeight(12))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(5))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(18))
						.add(LootItem.lootTableItem(Items.RAIL).setWeight(6))
						.add(LootItem.lootTableItem(Items.MINECART).setWeight(1))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(1)
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries))
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))))
						.add(LootItem.lootTableItem(Items.STONE_SHOVEL).setWeight(1)
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries))
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))))
						.add(LootItem.lootTableItem(Items.CHAIN).setWeight(5))
						.add(LootItem.lootTableItem(Items.OAK_FENCE).setWeight(1))
						.add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1))
						.add(LootItem.lootTableItem(Items.APPLE).setWeight(1))
				)
		);

		registry.accept(
			TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.OMEN_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.PLENTY_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.ESSENCE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.EYE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.BULLSEYE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(2))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(4))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(3))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(6))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(1).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(5))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(8))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(8))
						.add(LootItem.lootTableItem(Items.PAPER).setWeight(6))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(3))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(8))
						.add(LootItem.lootTableItem(Items.RED_DYE).setWeight(3))
						.add(LootItem.lootTableItem(Items.RAIL).setWeight(7))
						.add(LootItem.lootTableItem(Items.MINECART).setWeight(1))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(1)
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries))
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))))
						.add(LootItem.lootTableItem(Items.STONE_SHOVEL).setWeight(1)
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries))
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))))
						.add(LootItem.lootTableItem(Items.CHAIN).setWeight(3))
						.add(LootItem.lootTableItem(Items.OAK_FENCE).setWeight(1))
						.add(LootItem.lootTableItem(Items.APPLE).setWeight(2))
						.add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1))
						.add(LootItem.lootTableItem(Items.SPLASH_POTION).setWeight(1)
							.apply(SetPotionFunction.setPotion(Potions.WEAKNESS)))
				)
		);

		registry.accept(
			TTLootTables.CATACOMBS_ARCHAEOLOGY_TOMB,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.OMEN_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.PLENTY_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.ESSENCE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(TTItems.EYE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(2))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(1).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(7))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(3))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(6))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(3))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(8))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(7))
						.add(LootItem.lootTableItem(Items.STRING).setWeight(7))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(9))
						.add(LootItem.lootTableItem(Items.PAPER).setWeight(12))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(4))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(8))
						.add(LootItem.lootTableItem(Items.RED_DYE).setWeight(3))
						.add(LootItem.lootTableItem(Items.RAIL).setWeight(4))
						.add(LootItem.lootTableItem(Items.CHAIN).setWeight(5))
						.add(LootItem.lootTableItem(Items.OAK_FENCE).setWeight(2))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(1)
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries))
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))))
						.add(LootItem.lootTableItem(Items.STONE_SHOVEL).setWeight(1)
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries))
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))))
						.add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(2))
						.add(LootItem.lootTableItem(Items.APPLE).setWeight(2))
						.add(LootItem.lootTableItem(Items.SPLASH_POTION).setWeight(1)
							.apply(SetPotionFunction.setPotion(Potions.WEAKNESS)))
				)
		);

		registry.accept(
			TTLootTables.DESERT_RUINS_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.BLUE_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.YELLOW_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(20))
						.add(LootItem.lootTableItem(Items.BLUE_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(10))
						.add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(10))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(10))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.CLAY).setWeight(7))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(10))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.STONE_PICKAXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(5))
						.add(LootItem.lootTableItem(Items.POTION).setWeight(10)
							.apply(SetPotionFunction.setPotion(Potions.WATER)))
						.add(LootItem.lootTableItem(Items.STONE_AXE).setWeight(10))
						.add(LootItem.lootTableItem(Items.SADDLE).setWeight(4))
						.add(LootItem.lootTableItem(Items.BUCKET).setWeight(5))
						.add(LootItem.lootTableItem(TTItems.HUMP_POTTERY_SHERD).setWeight(6))
						.add(LootItem.lootTableItem(TTItems.NEEDLES_POTTERY_SHERD).setWeight(6))
						.add(LootItem.lootTableItem(Items.DANGER_POTTERY_SHERD).setWeight(6))
						.add(LootItem.lootTableItem(TTItems.SHINE_POTTERY_SHERD).setWeight(6))
						.add(LootItem.lootTableItem(TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(4))
				)
		);

		registry.accept(
			TTLootTables.JUNGLE_RUINS_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.BROWN_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHITE_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.GRAY_DYE).setWeight(10))
						.add(LootItem.lootTableItem(Items.BROWN_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHITE_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.GREEN_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(10))
						.add(LootItem.lootTableItem(Items.COCOA_BEANS).setWeight(10))
						.add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(8))
						.add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(8))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(10))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.CLAY).setWeight(7))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(10))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.STRING).setWeight(10))
						.add(LootItem.lootTableItem(Items.JUNGLE_HANGING_SIGN).setWeight(10))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5))
						.add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.STONE_HOE).setWeight(5))
						.add(LootItem.lootTableItem(Items.BOW).setWeight(3))
						.add(LootItem.lootTableItem(Items.BAMBOO).setWeight(8))
						.add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(5))
						.add(LootItem.lootTableItem(Items.LIGHTNING_ROD).setWeight(5))
						.add(LootItem.lootTableItem(Items.CAULDRON).setWeight(5))
						.add(LootItem.lootTableItem(Items.COMPASS).setWeight(5))
						.add(LootItem.lootTableItem(TTItems.BOLT_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.NAVIGATOR_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.BLOOM_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.SHOWER_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(5))
				)
		);

		registry.accept(
			TTLootTables.SAVANNA_RUINS_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.RED_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.YELLOW_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.PURPLE_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.RED_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.YELLOW_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(10))
						.add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(10))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.CLAY).setWeight(7))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(10))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.STRING).setWeight(10))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(10))
						.add(LootItem.lootTableItem(Items.LEAD).setWeight(10))
						.add(LootItem.lootTableItem(Items.ACACIA_FENCE).setWeight(7))
						.add(LootItem.lootTableItem(Items.ACACIA_FENCE_GATE).setWeight(4))
						.add(LootItem.lootTableItem(Items.ACACIA_HANGING_SIGN).setWeight(10))
						.add(LootItem.lootTableItem(Items.OAK_HANGING_SIGN).setWeight(10))
						.add(LootItem.lootTableItem(Items.STONE_AXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.STONE_PICKAXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(5))
						.add(LootItem.lootTableItem(Items.FEATHER).setWeight(8))
						.add(LootItem.lootTableItem(Items.BOW).setWeight(3))
						.add(LootItem.lootTableItem(Items.FERMENTED_SPIDER_EYE).setWeight(5))
						.add(LootItem.lootTableItem(Items.BOWL).setWeight(5))
						.add(LootItem.lootTableItem(TTItems.CRAWL_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.CLUCK_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.PLUME_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(Items.SHELTER_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(5))
				)
		);

		registry.accept(
			TTLootTables.RUINS_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.BROWN_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHITE_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.RED_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.BROWN_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHITE_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.RED_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(10))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(7))
						.add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(7))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(10))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.CLAY).setWeight(7))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(10))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.STRING).setWeight(10))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(10))
						.add(LootItem.lootTableItem(Items.LEAD).setWeight(10))
						.add(LootItem.lootTableItem(Items.OAK_FENCE).setWeight(7))
						.add(LootItem.lootTableItem(Items.OAK_FENCE_GATE).setWeight(4))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10))
						.add(LootItem.lootTableItem(Items.OAK_HANGING_SIGN).setWeight(10))
						.add(LootItem.lootTableItem(Items.BIRCH_HANGING_SIGN).setWeight(10))
						.add(LootItem.lootTableItem(Items.WOODEN_AXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.WOODEN_PICKAXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(5))
						.add(LootItem.lootTableItem(Items.SADDLE).setWeight(5))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.LUMBER_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.CULTIVATOR_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.SPROUT_POTTERY_SHERD).setWeight(7))
				)
		);

		registry.accept(
			TTLootTables.SNOWY_RUINS_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.WHITE_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.RED_DYE).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHITE_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.RED_CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(10))
						.add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(7))
						.add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(7))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10))
						.add(LootItem.lootTableItem(Items.CHARCOAL).setWeight(15))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.CLAY).setWeight(7))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.STRING).setWeight(10))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(10))
						.add(LootItem.lootTableItem(Items.WHITE_WOOL).setWeight(8))
						.add(LootItem.lootTableItem(Items.RED_WOOL).setWeight(8))
						.add(LootItem.lootTableItem(Items.WHITE_CARPET).setWeight(20))
						.add(LootItem.lootTableItem(Items.RED_CARPET).setWeight(20))
						.add(LootItem.lootTableItem(Items.LEAD).setWeight(10))
						.add(LootItem.lootTableItem(Items.SPRUCE_FENCE).setWeight(8))
						.add(LootItem.lootTableItem(Items.SPRUCE_FENCE_GATE).setWeight(4))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10))
						.add(LootItem.lootTableItem(Items.PACKED_ICE).setWeight(10))
						.add(LootItem.lootTableItem(Items.SPRUCE_HANGING_SIGN).setWeight(10))
						.add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(8))
						.add(LootItem.lootTableItem(TTItems.AURORA_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.ENCLOSURE_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.HARE_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.FROST_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(5))
				)
		);

		registry.accept(
			TTLootTables.DEEPSLATE_RUINS_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(20))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(10))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.CLAY).setWeight(6))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(10))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.STRING).setWeight(10))
						.add(LootItem.lootTableItem(Items.LEAD).setWeight(10))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10))
						.add(LootItem.lootTableItem(Items.OAK_HANGING_SIGN).setWeight(10))
						.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3))
						.add(LootItem.lootTableItem(Items.CLOCK).setWeight(3))
						.add(LootItem.lootTableItem(Items.COMPASS).setWeight(3))
						.add(LootItem.lootTableItem(Items.NOTE_BLOCK).setWeight(3))
						.add(LootItem.lootTableItem(Items.REDSTONE).setWeight(4))
						.add(LootItem.lootTableItem(Items.GLOW_BERRIES).setWeight(4))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(4))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(4))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(1)
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries))
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))))
						.add(LootItem.lootTableItem(Items.MOURNER_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(Items.MINER_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.ILLUMINATOR_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.HEIGHT_POTTERY_SHERD).setWeight(7))
						.add(LootItem.lootTableItem(TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(5))
				)
		);

		registry.accept(
			TTLootTables.BADLANDS_RUINS_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.ORANGE_DYE).setWeight(15))
						.add(LootItem.lootTableItem(Items.RED_DYE).setWeight(15))
						.add(LootItem.lootTableItem(Items.YELLOW_DYE).setWeight(15))
						.add(LootItem.lootTableItem(Items.BROWN_DYE).setWeight(15))
						.add(LootItem.lootTableItem(Items.ORANGE_CANDLE).setWeight(15))
						.add(LootItem.lootTableItem(Items.RED_CANDLE).setWeight(15))
						.add(LootItem.lootTableItem(Items.YELLOW_CANDLE).setWeight(15))
						.add(LootItem.lootTableItem(Items.BROWN_CANDLE).setWeight(15))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(20))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(5))
						.add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(7))
						.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(8))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(13))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(10))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(10))
						.add(LootItem.lootTableItem(Items.CLAY).setWeight(6))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(13))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(5))
						.add(LootItem.lootTableItem(Items.STRING).setWeight(7))
						.add(LootItem.lootTableItem(Items.OAK_HANGING_SIGN).setWeight(5))
						.add(LootItem.lootTableItem(Items.POTION).setWeight(3)
							.apply(SetPotionFunction.setPotion(Potions.WATER)))
						.add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(5))
						.add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).setWeight(5))
						.add(LootItem.lootTableItem(Items.BOWL).setWeight(4))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(4))
						.add(LootItem.lootTableItem(TTItems.SHED_POTTERY_SHERD).setWeight(6))
						.add(LootItem.lootTableItem(Items.BURN_POTTERY_SHERD).setWeight(6))
						.add(LootItem.lootTableItem(TTItems.WITHER_POTTERY_SHERD).setWeight(6))
						.add(LootItem.lootTableItem(TTItems.DROUGHT_POTTERY_SHERD).setWeight(6))
						.add(LootItem.lootTableItem(TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(4))
				)
		);
	}
}
