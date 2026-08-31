package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.storage.api.NoInteractionStorage;

public final class TTFabricBlocks {

	public static void registerBlockProperties() {
		registerInventories();
	}

	private static void registerInventories() {
		ItemStorage.SIDED.registerForBlocks(
			(level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(),
			TTBlocks.COFFIN.get()
		);
	}

	private TTFabricBlocks() {}
}
