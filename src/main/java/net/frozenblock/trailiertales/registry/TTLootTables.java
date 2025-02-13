package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.loot.v3.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.frozenblock.lib.loot.LootTableModifier;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.tag.TTStructureTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import org.jetbrains.annotations.NotNull;

public class TTLootTables {
	public static final ResourceKey<LootTable> CATACOMBS_CORRIDOR = register("chests/catacombs/corridor");
	public static final ResourceKey<LootTable> CATACOMBS_TOMB = register("chests/catacombs/tomb");
	public static final ResourceKey<LootTable> CATACOMBS_TOMB_REWARD = register("chests/catacombs/tomb_reward");
	public static final ResourceKey<LootTable> CATACOMBS_COFFIN = register("chests/catacombs/coffin");
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
	public static final ResourceKey<LootTable> SNOWY_RUINS_ARCHAEOLOGY = register("archaeology/snowy_ruins");

	public static void init() {
		LootTableModifier.editTable(
			BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY,
			false,
			(resourceKey, mutableLootTable) -> {
				mutableLootTable.modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.SHELTER_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.SHELTER_POTTERY_SHERD, TTItems.BAIT_POTTERY_SHERD)
				);
			}
		);

		LootTableModifier.editTable(
			BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY,
			false,
			(resourceKey, mutableLootTable) -> {
				mutableLootTable.modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.BLADE_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.BLADE_POTTERY_SHERD, TTItems.INCIDENCE_POTTERY_SHERD)
				).modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.MOURNER_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.MOURNER_POTTERY_SHERD, TTItems.VESSEL_POTTERY_SHERD)
				).modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.PLENTY_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.PLENTY_POTTERY_SHERD, TTItems.FOCUS_POTTERY_SHERD)
				);
			}
		);

		LootTableModifier.editTable(
			BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE,
			false,
			(resourceKey, mutableLootTable) -> {
				mutableLootTable.modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.BURN_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.BURN_POTTERY_SHERD, TTItems.PROTECTION_POTTERY_SHERD)
				).modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.DANGER_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.DANGER_POTTERY_SHERD, TTItems.CRESCENT_POTTERY_SHERD)
				);
			}
		);

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			// Added Catacombs Explorer Map
			if (BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.equals(key)) {
				((FabricLootTableBuilder) tableBuilder)
					.modifyPools(builder -> builder.add(LootItem.lootTableItem(Items.MAP).apply(
						ExplorationMapFunction.makeExplorationMap()
							.setDestination(TTStructureTags.ON_CATACOMBS_EXPLORER_MAPS)
							.setMapDecoration(TTMapDecorationTypes.CATACOMBS)
							.setZoom((byte) 1)
							.setSkipKnownStructures(false)
							).apply(
								SetNameFunction.setName(
									Component.translatable("filled_map.trailiertales.catacombs"),
									SetNameFunction.Target.ITEM_NAME
								)
							)
						)
					);
				}
		});

		LootTableModifier.editTable(
			BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
			false,
			(resourceKey, mutableLootTable) -> {
				mutableLootTable.modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.MINER_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.MINER_POTTERY_SHERD, TTItems.SPADE_POTTERY_SHERD)
				).modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.SKULL_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.SKULL_POTTERY_SHERD, Items.ARMS_UP_POTTERY_SHERD)
				);
			}
		);

		LootTableModifier.editTable(
			BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,
			false,
			(resourceKey, mutableLootTable) -> {
				mutableLootTable.modifyPools(
					mutableLootPool -> mutableLootPool.hasItem(Items.ARMS_UP_POTTERY_SHERD),
					mutableLootPool -> mutableLootPool.replace(Items.ARMS_UP_POTTERY_SHERD, TTItems.CARRIER_POTTERY_SHERD)
				);
			}
		);

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.SNIFFER_DIGGING.equals(key)) {
				if (TTEntityConfig.get().sniffer.cyan_rose_seeds) {
					((FabricLootTableBuilder) tableBuilder)
						.modifyPools(builder -> builder.add(LootItem.lootTableItem(TTItems.CYAN_ROSE_SEEDS)));
				}
				if (TTEntityConfig.get().sniffer.manedrop_germs) {
					((FabricLootTableBuilder) tableBuilder)
						.modifyPools(builder -> builder.add(LootItem.lootTableItem(TTItems.MANEDROP_GERM)));
				}
				if (TTEntityConfig.get().sniffer.dawntrail_seeds) {
					((FabricLootTableBuilder) tableBuilder)
						.modifyPools(builder -> builder.add(LootItem.lootTableItem(TTItems.DAWNTRAIL_SEEDS)));
				}
			}
		});
	}

	private static @NotNull ResourceKey<LootTable> register(String path) {
		return ResourceKey.create(Registries.LOOT_TABLE, TTConstants.id(path));
	}
}
