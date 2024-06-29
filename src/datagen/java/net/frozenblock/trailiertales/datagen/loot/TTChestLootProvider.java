package net.frozenblock.trailiertales.datagen.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
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
						.setRolls(UniformGenerator.between(0F, 1F))
						.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
						.add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(30))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(5).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))))
						.add(LootItem.lootTableItem(Items.SADDLE).setWeight(5))
						.add(EmptyLootItem.emptyItem().setWeight(5))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(5F, 10F))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(18).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(4)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(RegisterItems.WITHER_POTTERY_SHERD).setWeight(1))
				)
		);

		registry.accept(
			RegisterLootTables.CATACOMBS_TOMB,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(1F, 3F))
						.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
						.add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(30))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(5)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
						.add(EmptyLootItem.emptyItem().setWeight(5))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(5F, 12F))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(18).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(4)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(RegisterItems.WITHER_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(RegisterItems.BULLSEYE_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BREAD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6))))
				)
		);

		registry.accept(
			RegisterLootTables.CATACOMBS_TOMB_REWARD,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(4F, 9F))
						.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20))
						.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
						.add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(10))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(2)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F))).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(2)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(2)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(2)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
							.apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries).when(LootItemRandomChanceCondition.randomChance(0.6F))))
						.add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(2)
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
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
						.add(LootItem.lootTableItem(RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
						.add(EmptyLootItem.emptyItem().setWeight(5))
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(UniformGenerator.between(7F, 13F))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(2)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(RegisterItems.WITHER_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(RegisterItems.BULLSEYE_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD).setWeight(1))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BREAD).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.STICK).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6))))
				)
		);
	}
}
