package net.frozenblock.trailiertales.datagen.loot;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
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
			RegisterLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(40).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(35).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
				)
		);

		registry.accept(
			RegisterLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(RegisterItems.WITHER_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(RegisterItems.BULLSEYE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(5))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(2).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(5)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
				)
		);

		registry.accept(
			RegisterLootTables.CATACOMBS_ARCHAEOLOGY_TOMB,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(3))
						.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(RegisterItems.WITHER_POTTERY_SHERD).setWeight(3))
						.add(LootItem.lootTableItem(RegisterItems.BULLSEYE_POTTERY_SHERD).setWeight(3))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(2))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(1).apply(EnchantRandomlyFunction.randomApplicableEnchantment(registries)))
						.add(LootItem.lootTableItem(Items.SOUL_LANTERN).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.BONE).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(18).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(5)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
						.add(LootItem.lootTableItem(Items.CANDLE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
				)
		);
	}
}
