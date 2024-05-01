/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

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

		this.dropSelf(RegisterBlocks.CHISELED_POLISHED_GRANITE);
		this.dropSelf(RegisterBlocks.POLISHED_GRANITE_BRICKS);
		this.dropSelf(RegisterBlocks.CRACKED_POLISHED_GRANITE_BRICKS);
		this.dropSelf(RegisterBlocks.POLISHED_GRANITE_BRICK_STAIRS);
		this.add(RegisterBlocks.POLISHED_GRANITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.POLISHED_GRANITE_BRICK_WALL);

		this.dropSelf(RegisterBlocks.CHISELED_POLISHED_DIORITE);
		this.dropSelf(RegisterBlocks.POLISHED_DIORITE_BRICKS);
		this.dropSelf(RegisterBlocks.CRACKED_POLISHED_DIORITE_BRICKS);
		this.dropSelf(RegisterBlocks.POLISHED_DIORITE_BRICK_STAIRS);
		this.add(RegisterBlocks.POLISHED_DIORITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.POLISHED_DIORITE_BRICK_WALL);

		this.dropSelf(RegisterBlocks.CHISELED_POLISHED_ANDESITE);
		this.dropSelf(RegisterBlocks.POLISHED_ANDESITE_BRICKS);
		this.dropSelf(RegisterBlocks.CRACKED_POLISHED_ANDESITE_BRICKS);
		this.dropSelf(RegisterBlocks.POLISHED_ANDESITE_BRICK_STAIRS);
		this.add(RegisterBlocks.POLISHED_ANDESITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.POLISHED_ANDESITE_BRICK_WALL);

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
	}

}
