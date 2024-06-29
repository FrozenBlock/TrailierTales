package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
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

	public static void init() {
		//SNIFFER DIGGING - CYAN ROSE
		LootTableEvents.MODIFY.register((id, tableBuilder, source) -> {
			if (BuiltInLootTables.SNIFFER_DIGGING.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(RegisterItems.CYAN_ROSE_SEEDS));
				tableBuilder.withPool(pool);
			}
		});
	}

	private static ResourceKey<LootTable> register(String path) {
		return ResourceKey.create(Registries.LOOT_TABLE, TrailierTalesSharedConstants.id(path));
	}
}
