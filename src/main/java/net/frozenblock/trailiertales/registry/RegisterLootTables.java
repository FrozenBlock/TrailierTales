package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.EntityConfig;
import net.frozenblock.trailiertales.tag.TrailierStructureTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
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
	public static final ResourceKey<LootTable> JUNGLE_RUINS_ARCHAEOLOGY = register("archaeology/jungle_ruins");
	public static final ResourceKey<LootTable> SAVANNA_RUINS_ARCHAEOLOGY = register("archaeology/savanna_ruins");
	public static final ResourceKey<LootTable> RUINS_ARCHAEOLOGY = register("archaeology/ruins");
	public static final ResourceKey<LootTable> DEEPSLATE_RUINS_ARCHAEOLOGY = register("archaeology/deepslate_ruins");
	public static final ResourceKey<LootTable> BADLANDS_RUINS_ARCHAEOLOGY = register("archaeology/badlands_ruins");

	public static final ResourceKey<LootTable> FOSSIL_ARCHAEOLOGY = register("archaeology/fossil");

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
				// Added Vessel
				// Added Incidence
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(LootItem.lootTableItem(RegisterItems.INCIDENCE_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.EXPLORER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(RegisterItems.VESSEL_POTTERY_SHERD))
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
				// Removed Danger
				// Added Protection
				// Added Catacombs Explorer Map
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(
								LootItem.lootTableItem(Items.MAP)
									.apply(
										ExplorationMapFunction.makeExplorationMap()
											.setDestination(TrailierStructureTags.ON_CATACOMBS_EXPLORER_MAPS)
											.setMapDecoration(RegisterMapDecorationTypes.CATACOMBS)
											.setZoom((byte)1)
											.setSkipKnownStructures(false)
									)
									.apply(SetNameFunction.setName(Component.translatable("filled_map.trailiertales.catacombs"), SetNameFunction.Target.ITEM_NAME))
							)
							.add(LootItem.lootTableItem(RegisterItems.PROTECTION_POTTERY_SHERD))
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
			if (BuiltInLootTables.SNIFFER_DIGGING.equals(key) && EntityConfig.get().sniffer.cyan_rose_seeds) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(RegisterItems.CYAN_ROSE_SEEDS));
				tableBuilder.withPool(pool);
			}
		});

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

	private static @NotNull ResourceKey<LootTable> register(String path) {
		return ResourceKey.create(Registries.LOOT_TABLE, TrailierConstants.id(path));
	}
}
