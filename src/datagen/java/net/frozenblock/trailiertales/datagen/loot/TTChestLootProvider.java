package net.frozenblock.trailiertales.datagen.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.frozenblock.trailiertales.registry.RegisterEnchantments;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class TTChestLootProvider extends SimpleFabricLootTableProvider {

	private final CompletableFuture<HolderLookup.Provider> registryLookup;

	public TTChestLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup, LootContextParamSets.CHEST);
		this.registryLookup = registryLookup;
	}

	@Override
	public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> registry) {
		HolderLookup.Provider registries = registryLookup.join();

		registry.accept(
			RegisterLootTables.CATACOMBS_CORRIDOR,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(0F, 2F))
						.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
						.add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(20))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.SADDLE).setWeight(5))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(EmptyLootItem.emptyItem().setWeight(3))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(4F, 9F))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(4)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
				)
		);

		registry.accept(
			RegisterLootTables.CATACOMBS_TOMB,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(5))
						.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(
							LootItem.lootTableItem(Items.BOOK)
								.setWeight(3).setQuality(Rarity.EPIC.ordinal())
								.apply(new SetEnchantmentsFunction.Builder().withEnchantment(
									registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(RegisterEnchantments.REBRUSH),
									ConstantValue.exactly(1.0F)
								))
						)
						.add(EmptyLootItem.emptyItem().setWeight(3))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(1F, 3F))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(9).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2))
						.add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(8))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(EmptyLootItem.emptyItem().setWeight(2))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(0F, 2F))
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.PRIZE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.PLENTY_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.SHEAF_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BREWER_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(RegisterItems.WITHER_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(RegisterItems.BULLSEYE_POTTERY_SHERD).setWeight(2))
						.add(EmptyLootItem.emptyItem().setWeight(7))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(0F, 2F))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BREAD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(EmptyLootItem.emptyItem().setWeight(2))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(4F, 9F))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(4)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
				)
		);

		registry.accept(
			RegisterLootTables.CATACOMBS_TOMB_REWARD,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(5))
						.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(9).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(
							LootItem.lootTableItem(Items.BOOK)
								.setWeight(3)
								.apply(new SetEnchantmentsFunction.Builder().withEnchantment(
									registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(RegisterEnchantments.REBRUSH),
									ConstantValue.exactly(1.0F)
								))
						)
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(1F, 4F))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(9).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2))
						.add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(5))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(EmptyLootItem.emptyItem().setWeight(2))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(0F, 2F))
						.add(LootItem.lootTableItem(Items.BOW).setWeight(3)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(3)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(3)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(3)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(3)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(1)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(1)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.DIAMOND_HELMET).setWeight(1)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(1)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(EmptyLootItem.emptyItem().setWeight(10))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(0F, 2F))
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.PRIZE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.PLENTY_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.SHEAF_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BREWER_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(RegisterItems.WITHER_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(RegisterItems.BULLSEYE_POTTERY_SHERD).setWeight(2))
						.add(EmptyLootItem.emptyItem().setWeight(3))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
						.add(LootItem.lootTableItem(RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
						.add(EmptyLootItem.emptyItem().setWeight(18))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(1F, 4F))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BREAD).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.ARROW).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F))))
						.add(EmptyLootItem.emptyItem().setWeight(5))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(2F, 4F))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(5)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
						.add(LootItem.lootTableItem(Items.BRICK).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
				)
		);

		registry.accept(
			RegisterLootTables.CATACOMBS_DECORATED_POT,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(5))
						.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(
							LootItem.lootTableItem(Items.BOOK)
								.setWeight(3).setQuality(Rarity.EPIC.ordinal())
								.apply(new SetEnchantmentsFunction.Builder().withEnchantment(
									registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(RegisterEnchantments.REBRUSH),
									ConstantValue.exactly(1.0F)
								))
						)
						.add(EmptyLootItem.emptyItem().setWeight(3))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(1F, 3F))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
						.add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(6))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(EmptyLootItem.emptyItem().setWeight(2))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(0F, 2F))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BREAD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(EmptyLootItem.emptyItem().setWeight(2))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(4F, 9F))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(4)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
				)
		);
	}
}
