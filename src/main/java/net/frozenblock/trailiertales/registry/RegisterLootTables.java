package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

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
	public static final ResourceKey<LootTable> SAVANNA_RUINS_ARCHAEOLOGY_SURFACE = register("archaeology/savanna_surface");
	public static final ResourceKey<LootTable> RUINS_ARCHAEOLOGY = register("archaeology/ruins");
	public static final ResourceKey<LootTable> RUINS_ARCHAEOLOGY_SURFACE = register("archaeology/ruins_surface");
	public static final ResourceKey<LootTable> RUINS_ARCHAEOLOGY_LIBRARY = register("archaeology/ruins_library");
	public static final ResourceKey<LootTable> BADLANDS_RUINS_ARCHAEOLOGY_SURFACE = register("archaeology/badlands_ruins_surface");
	public static final ResourceKey<LootTable> BADLANDS_RUINS_ARCHAEOLOGY = register("archaeology/badlands_ruins");
	public static final ResourceKey<LootTable> BADLANDS_RUINS_ARCHAEOLOGY_FOSSIL = register("archaeology/badlands_ruins_fossil");

	public static void init() {
		//SNIFFER DIGGING - CYAN ROSE
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.SNIFFER_DIGGING.equals(key) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(RegisterItems.CYAN_ROSE_SEEDS));
				tableBuilder.withPool(pool);
			}
		});
	}

	private static ResourceKey<LootTable> register(String path) {
		return ResourceKey.create(Registries.LOOT_TABLE, TrailierConstants.id(path));
	}
}
