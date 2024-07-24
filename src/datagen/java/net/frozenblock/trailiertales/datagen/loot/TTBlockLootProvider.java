package net.frozenblock.trailiertales.datagen.loot;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.jetbrains.annotations.NotNull;

public final class TTBlockLootProvider extends FabricBlockLootTableProvider {

	public TTBlockLootProvider(@NotNull FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(dataOutput, registries);
	}

	@Override
	public void generate() {
		this.dropSelf(RegisterBlocks.CYAN_ROSE);
		this.dropPottedContents(RegisterBlocks.POTTED_CYAN_ROSE);

		this.add(
			RegisterBlocks.CYAN_ROSE_CROP,
			this.applyExplosionDecay(RegisterBlocks.CYAN_ROSE_CROP, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(RegisterItems.CYAN_ROSE_SEEDS))))
		);

		this.dropSelf(RegisterBlocks.CHISELED_GRANITE_BRICKS);
		this.dropSelf(RegisterBlocks.GRANITE_BRICKS);
		this.dropSelf(RegisterBlocks.CRACKED_GRANITE_BRICKS);
		this.dropSelf(RegisterBlocks.GRANITE_BRICK_STAIRS);
		this.add(RegisterBlocks.GRANITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.GRANITE_BRICK_WALL);
		this.dropSelf(RegisterBlocks.MOSSY_GRANITE_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_GRANITE_BRICK_STAIRS);
		this.add(RegisterBlocks.MOSSY_GRANITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_GRANITE_BRICK_WALL);

		this.dropSelf(RegisterBlocks.CHISELED_DIORITE_BRICKS);
		this.dropSelf(RegisterBlocks.DIORITE_BRICKS);
		this.dropSelf(RegisterBlocks.CRACKED_DIORITE_BRICKS);
		this.dropSelf(RegisterBlocks.DIORITE_BRICK_STAIRS);
		this.add(RegisterBlocks.DIORITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.DIORITE_BRICK_WALL);
		this.dropSelf(RegisterBlocks.MOSSY_DIORITE_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_DIORITE_BRICK_STAIRS);
		this.add(RegisterBlocks.MOSSY_DIORITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_DIORITE_BRICK_WALL);

		this.dropSelf(RegisterBlocks.CHISELED_ANDESITE_BRICKS);
		this.dropSelf(RegisterBlocks.ANDESITE_BRICKS);
		this.dropSelf(RegisterBlocks.CRACKED_ANDESITE_BRICKS);
		this.dropSelf(RegisterBlocks.ANDESITE_BRICK_STAIRS);
		this.add(RegisterBlocks.ANDESITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.ANDESITE_BRICK_WALL);
		this.dropSelf(RegisterBlocks.MOSSY_ANDESITE_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_ANDESITE_BRICK_STAIRS);
		this.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_ANDESITE_BRICK_WALL);

		this.dropSelf(RegisterBlocks.CALCITE_STAIRS);
		this.add(RegisterBlocks.CALCITE_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.CALCITE_WALL);
		this.dropSelf(RegisterBlocks.POLISHED_CALCITE);
		this.dropSelf(RegisterBlocks.POLISHED_CALCITE_STAIRS);
		this.add(RegisterBlocks.POLISHED_CALCITE_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.POLISHED_CALCITE_WALL);
		this.dropSelf(RegisterBlocks.CHISELED_CALCITE_BRICKS);
		this.dropSelf(RegisterBlocks.CALCITE_BRICKS);
		this.dropSelf(RegisterBlocks.CRACKED_CALCITE_BRICKS);
		this.dropSelf(RegisterBlocks.CALCITE_BRICK_STAIRS);
		this.add(RegisterBlocks.CALCITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.CALCITE_BRICK_WALL);
		this.dropSelf(RegisterBlocks.MOSSY_CALCITE_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_CALCITE_BRICK_STAIRS);
		this.add(RegisterBlocks.MOSSY_CALCITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_CALCITE_BRICK_WALL);

		this.dropSelf(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);
		this.dropSelf(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS);
		this.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_WALL);

		this.dropSelf(RegisterBlocks.MOSSY_DEEPSLATE_TILES);
		this.dropSelf(RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);
		this.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL);

		this.dropSelf(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS);
		this.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL);

		this.add(RegisterBlocks.COFFIN, noDrop());
		this.dropSelf(RegisterBlocks.SURVEYOR);
	}

}
