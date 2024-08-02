package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

public class RegisterLootTables {
	public static final ResourceKey<LootTable> CATACOMBS_CORRIDOR = register("chests/catacombs/corridor");
	public static final ResourceKey<LootTable> CATACOMBS_TOMB = register("chests/catacombs/tomb");
	public static final ResourceKey<LootTable> CATACOMBS_TOMB_REWARD = register("chests/catacombs/tomb_reward");
	public static final ResourceKey<LootTable> CATACOMBS_DECORATED_POT = register("pots/catacombs/decorated_pot");
	public static final ResourceKey<LootTable> CATACOMBS_ARCHAEOLOGY_CORRIDOR = register("archaeology/catacombs/corridor");
	public static final ResourceKey<LootTable> CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE = register("archaeology/catacombs/corridor_rare");
	public static final ResourceKey<LootTable> CATACOMBS_ARCHAEOLOGY_TOMB = register("archaeology/catacombs/tomb");
	public static final ResourceKey<LootTable> DESERT_RUINS_ARCHAEOLOGY = register("archaeology/desert_ruins");
	public static final ResourceKey<LootTable> DESERT_RUINS_ARCHAEOLOGY_SURFACE = register("archaeology/desert_ruins_surface");
	public static final ResourceKey<LootTable> DESERT_RUINS_ARCHAEOLOGY_FOSSIl = register("archaeology/desert_ruins_fossil");
	public static final ResourceKey<LootTable> DESERT_RUINS_ARCHAEOLOGY_POTS = register("archaeology/desert_ruins_pots");
	public static final ResourceKey<LootTable> JUNGLE_RUINS_ARCHAEOLOGY = register("archaeology/jungle_ruins");
	public static final ResourceKey<LootTable> JUNGLE_RUINS_ARCHAEOLOGY_SURFACE = register("archaeology/jungle_ruins_surface");
	public static final ResourceKey<LootTable> SAVANNA_RUINS_ARCHAEOLOGY = register("archaeology/savanna_ruins");
	public static final ResourceKey<LootTable> RUINS_ARCHAEOLOGY = register("archaeology/ruins");
	public static final ResourceKey<LootTable> BADLANDS_RUINS_ARCHAEOLOGY_SURFACE = register("archaeology/badlands_ruins_surface");
	public static final ResourceKey<LootTable> BADLANDS_RUINS_ARCHAEOLOGY = register("archaeology/badlands_ruins");
	public static final ResourceKey<LootTable> BADLANDS_RUINS_ARCHAEOLOGY_FOSSIL = register("archaeology/badlands_ruins_fossil");

	public static void init() {
		LootTableEvents.REPLACE.register((key, lootTable, source, registries) -> {
			if (BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.equals(key)) {
				// Removed Shelter
				// Added Bait
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(LootItem.lootTableItem(Items.ANGLER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(RegisterItems.BAIT_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.SNORT_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.SNIFFER_EGG))
							.add(LootItem.lootTableItem(Items.IRON_AXE))
							.add(LootItem.lootTableItem(Items.EMERALD).setWeight(2))
							.add(LootItem.lootTableItem(Items.WHEAT).setWeight(2))
							.add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(2))
							.add(LootItem.lootTableItem(Items.COAL).setWeight(2))
							.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(2))
					).build();
			} else if (BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.equals(key)) {
				// Removed Blade
				// Removed Mourner
				// Removed Plenty
				// Added Bait
				// Added Angler
				// Added Incidence
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(LootItem.lootTableItem(RegisterItems.INCIDENCE_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.EXPLORER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.ANGLER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(RegisterItems.BAIT_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.IRON_AXE))
							.add(LootItem.lootTableItem(Items.EMERALD).setWeight(2))
							.add(LootItem.lootTableItem(Items.WHEAT).setWeight(2))
							.add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(2))
							.add(LootItem.lootTableItem(Items.COAL).setWeight(2))
							.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(2))
					).build();
			} else if (BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.equals(key)) {
				// Removed Burn
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(LootItem.lootTableItem(Items.DANGER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.FRIEND_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.HEARTBREAK_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.HOWL_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.SHEAF_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE))
							.add(LootItem.lootTableItem(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE))
							.add(LootItem.lootTableItem(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE))
							.add(LootItem.lootTableItem(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE))
							.add(LootItem.lootTableItem(Items.MUSIC_DISC_RELIC))
					).build();
			} else if (BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY.equals(key)) {
				// Removed Miner
				// Added Spade
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(RegisterItems.SPADE_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.PRIZE_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.DIAMOND))
							.add(LootItem.lootTableItem(Items.TNT))
							.add(LootItem.lootTableItem(Items.GUNPOWDER))
							.add(LootItem.lootTableItem(Items.EMERALD))
					).build();
			}

			return null;
		});

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.SNIFFER_DIGGING.equals(key)) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(RegisterItems.CYAN_ROSE_SEEDS));
				tableBuilder.withPool(pool);
			}
		});
	}

	private static @NotNull ResourceKey<LootTable> register(String path) {
		return ResourceKey.create(Registries.LOOT_TABLE, TrailierConstants.id(path));
	}
}
