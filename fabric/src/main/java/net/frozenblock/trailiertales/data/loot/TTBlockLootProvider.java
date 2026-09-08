/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.data.loot;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.frozenblock.trailiertales.block.GuzmaniaCropBlock;
import net.frozenblock.trailiertales.block.LithopsCropBlock;
import net.frozenblock.trailiertales.block.ManedropCropBlock;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public final class TTBlockLootProvider extends FabricBlockLootSubProvider {

	public TTBlockLootProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(dataOutput, registries);
	}

	@Override
	public void generate() {
		this.dropSelf(TTBlocks.CYAN_ROSE.get());
		this.dropPottedContents(TTBlocks.POTTED_CYAN_ROSE.get());
		this.add(
			TTBlocks.CYAN_ROSE_CROP.get(),
			this.applyExplosionDecay(TTBlocks.CYAN_ROSE_CROP.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(TTItems.CYAN_ROSE_SEEDS.get()))))
		);

		this.add(
			TTBlocks.MANEDROP.get(),
			this.applyExplosionDecay(
				TTBlocks.MANEDROP.get(),
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(TTBlocks.MANEDROP.get())
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.MANEDROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
										)
									)
							)
					)
			)
		);
		this.add(
			TTBlocks.MANEDROP_CROP.get(),
			this.applyExplosionDecay(
				TTBlocks.MANEDROP_CROP.get(),
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(TTItems.MANEDROP_GERM.get())
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.MANEDROP_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(ManedropCropBlock.AGE, ManedropCropBlock.MAX_AGE)
										).invert()
									)
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.MANEDROP_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
										)
									)
							)
							.add(
								LootItem.lootTableItem(TTBlocks.MANEDROP.get())
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.MANEDROP_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(ManedropCropBlock.AGE, ManedropCropBlock.MAX_AGE)
										)
									)
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.MANEDROP_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
										)
									)
							)
					)
			)
		);

		this.add(
			TTBlocks.GUZMANIA.get(),
			this.applyExplosionDecay(
				TTBlocks.GUZMANIA.get(),
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(TTBlocks.GUZMANIA.get())
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.GUZMANIA.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
										)
									)
							)
					)
			)
		);
		this.add(
			TTBlocks.GUZMANIA_CROP.get(),
			this.applyExplosionDecay(
				TTBlocks.GUZMANIA_CROP.get(),
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(TTItems.GUZMANIA_SEEDS.get())
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.GUZMANIA_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(GuzmaniaCropBlock.AGE, GuzmaniaCropBlock.MAX_AGE)
										).invert()
									)
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.GUZMANIA_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
										)
									)
							)
							.add(
								LootItem.lootTableItem(TTBlocks.GUZMANIA.get())
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.GUZMANIA_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(GuzmaniaCropBlock.AGE, GuzmaniaCropBlock.MAX_AGE)
										)
									)
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.GUZMANIA_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
										)
									)
							)
					)
			)
		);

		this.add(TTBlocks.DAWNTRAIL.get(), this::createMultifaceBlockDrops);
		this.add(
			TTBlocks.DAWNTRAIL_CROP.get(),
			this.applyExplosionDecay(TTBlocks.DAWNTRAIL_CROP.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(TTItems.DAWNTRAIL_SEEDS.get()))))
		);

		this.add(TTBlocks.LITHOPS.get(), this.createSegmentedBlockDrops(TTBlocks.LITHOPS.get()));
		this.dropPottedContents(TTBlocks.POTTED_LITHOPS.get());
		this.add(
			TTBlocks.LITHOPS_CROP.get(),
			this.applyExplosionDecay(
				TTBlocks.LITHOPS_CROP.get(),
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(TTItems.LITHOPS_SEEDS.get())
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.LITHOPS_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(LithopsCropBlock.AGE, LithopsCropBlock.MAX_AGE)
										).invert()
									)
							)
							.add(
								LootItem.lootTableItem(TTBlocks.LITHOPS.get())
									.when(
										MatchBlock.blockMatches(
											this.blocks,
											TTBlocks.LITHOPS_CROP.get(),
											StatePropertiesPredicate.Builder.properties().hasProperty(LithopsCropBlock.AGE, LithopsCropBlock.MAX_AGE)
										)
									)
									.apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(4), false))
							)
					)
			)
		);

		this.dropSelf(TTBlocks.STONE_WALL.get());

		this.dropSelf(TTBlocks.POLISHED_GRANITE_WALL.get());
		this.dropSelf(TTBlocks.CHISELED_GRANITE_BRICKS.get());
		this.dropSelf(TTBlocks.GRANITE_BRICKS.get());
		this.dropSelf(TTBlocks.CRACKED_GRANITE_BRICKS.get());
		this.dropSelf(TTBlocks.GRANITE_BRICK_STAIRS.get());
		this.add(TTBlocks.GRANITE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.GRANITE_BRICK_WALL.get());
		this.dropSelf(TTBlocks.MOSSY_GRANITE_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS.get());
		this.add(TTBlocks.MOSSY_GRANITE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_GRANITE_BRICK_WALL.get());

		this.dropSelf(TTBlocks.POLISHED_DIORITE_WALL.get());
		this.dropSelf(TTBlocks.CHISELED_DIORITE_BRICKS.get());
		this.dropSelf(TTBlocks.DIORITE_BRICKS.get());
		this.dropSelf(TTBlocks.CRACKED_DIORITE_BRICKS.get());
		this.dropSelf(TTBlocks.DIORITE_BRICK_STAIRS.get());
		this.add(TTBlocks.DIORITE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.DIORITE_BRICK_WALL.get());
		this.dropSelf(TTBlocks.MOSSY_DIORITE_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS.get());
		this.add(TTBlocks.MOSSY_DIORITE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_DIORITE_BRICK_WALL.get());

		this.dropSelf(TTBlocks.POLISHED_ANDESITE_WALL.get());
		this.dropSelf(TTBlocks.CHISELED_ANDESITE_BRICKS.get());
		this.dropSelf(TTBlocks.ANDESITE_BRICKS.get());
		this.dropSelf(TTBlocks.CRACKED_ANDESITE_BRICKS.get());
		this.dropSelf(TTBlocks.ANDESITE_BRICK_STAIRS.get());
		this.add(TTBlocks.ANDESITE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.ANDESITE_BRICK_WALL.get());
		this.dropSelf(TTBlocks.MOSSY_ANDESITE_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS.get());
		this.add(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_ANDESITE_BRICK_WALL.get());

		this.dropSelf(TTBlocks.CALCITE_STAIRS.get());
		this.add(TTBlocks.CALCITE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.CALCITE_WALL.get());
		this.dropSelf(TTBlocks.POLISHED_CALCITE.get());
		this.dropSelf(TTBlocks.POLISHED_CALCITE_STAIRS.get());
		this.add(TTBlocks.POLISHED_CALCITE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.POLISHED_CALCITE_WALL.get());
		this.dropSelf(TTBlocks.CHISELED_CALCITE_BRICKS.get());
		this.dropSelf(TTBlocks.CALCITE_BRICKS.get());
		this.dropSelf(TTBlocks.CRACKED_CALCITE_BRICKS.get());
		this.dropSelf(TTBlocks.CALCITE_BRICK_STAIRS.get());
		this.add(TTBlocks.CALCITE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.CALCITE_BRICK_WALL.get());
		this.dropSelf(TTBlocks.MOSSY_CALCITE_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS.get());
		this.add(TTBlocks.MOSSY_CALCITE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_CALCITE_BRICK_WALL.get());

		this.dropSelf(TTBlocks.CRACKED_TUFF_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_TUFF_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_TUFF_BRICK_STAIRS.get());
		this.add(TTBlocks.MOSSY_TUFF_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_TUFF_BRICK_WALL.get());

		this.dropSelf(TTBlocks.CRACKED_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_BRICK_STAIRS.get());
		this.add(TTBlocks.MOSSY_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_BRICK_WALL.get());

		this.dropSelf(TTBlocks.POLISHED_RESIN_BLOCK.get());
		this.dropSelf(TTBlocks.POLISHED_RESIN_STAIRS.get());
		this.add(TTBlocks.POLISHED_RESIN_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.POLISHED_RESIN_WALL.get());
		this.dropSelf(TTBlocks.CRACKED_RESIN_BRICKS.get());
		this.dropSelf(TTBlocks.PALE_MOSSY_RESIN_BRICKS.get());
		this.dropSelf(TTBlocks.PALE_MOSSY_RESIN_BRICK_STAIRS.get());
		this.add(TTBlocks.PALE_MOSSY_RESIN_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.PALE_MOSSY_RESIN_BRICK_WALL.get());

		this.dropSelf(TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());
		this.dropSelf(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS.get());
		this.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL.get());

		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_TILES.get());
		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.get());
		this.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL.get());

		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_BRICKS.get());
		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.get());
		this.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL.get());

		this.dropSelf(TTBlocks.SMOOTH_SANDSTONE_WALL.get());
		this.dropSelf(TTBlocks.CUT_SANDSTONE_STAIRS.get());
		this.dropSelf(TTBlocks.CUT_SANDSTONE_WALL.get());

		this.dropSelf(TTBlocks.SMOOTH_RED_SANDSTONE_WALL.get());
		this.dropSelf(TTBlocks.CUT_RED_SANDSTONE_STAIRS.get());
		this.dropSelf(TTBlocks.CUT_RED_SANDSTONE_WALL.get());

		this.dropSelf(TTBlocks.PRISMARINE_BRICK_WALL.get());

		this.dropSelf(TTBlocks.DARK_PRISMARINE_WALL.get());

		this.dropSelf(TTBlocks.CHORAL_END_STONE.get());
		this.dropSelf(TTBlocks.CHORAL_END_STONE_STAIRS.get());
		this.add(TTBlocks.CHORAL_END_STONE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.CHORAL_END_STONE_WALL.get());

		this.dropSelf(TTBlocks.CRACKED_END_STONE_BRICKS.get());
		this.dropSelf(TTBlocks.CHISELED_END_STONE_BRICKS.get());
		this.dropSelf(TTBlocks.CHORAL_END_STONE_BRICKS.get());
		this.dropSelf(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS.get());
		this.add(TTBlocks.CHORAL_END_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(TTBlocks.CHORAL_END_STONE_BRICK_WALL.get());

		this.dropSelf(TTBlocks.CRACKED_PURPUR_BLOCK.get());
		this.dropSelf(TTBlocks.CHISELED_PURPUR_BLOCK.get());
		this.dropSelf(TTBlocks.PURPUR_WALL.get());

		this.add(TTBlocks.SUSPICIOUS_RED_SAND.get(), noDrop());
		this.add(TTBlocks.SUSPICIOUS_DIRT.get(), noDrop());
		this.add(TTBlocks.SUSPICIOUS_CLAY.get(), noDrop());
		this.add(TTBlocks.COFFIN.get(), noDrop());
		this.dropSelf(TTBlocks.SURVEYOR.get());
		this.dropSelf(TTBlocks.ECTOPLASM_BLOCK.get());
	}
}
