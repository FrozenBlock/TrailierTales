package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class RegisterLootTables {

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

}
