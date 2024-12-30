package net.frozenblock.trailiertales.datagen.loot;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.frozenblock.trailiertales.block.ManedropCropBlock;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

public final class TTBlockLootProvider extends FabricBlockLootTableProvider {

	public TTBlockLootProvider(@NotNull FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(dataOutput, registries);
	}

	@Override
	public void generate() {
		this.dropSelf(TTBlocks.CYAN_ROSE);
		this.dropPottedContents(TTBlocks.POTTED_CYAN_ROSE);
		this.add(
			TTBlocks.CYAN_ROSE_CROP,
			this.applyExplosionDecay(TTBlocks.CYAN_ROSE_CROP, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(TTItems.CYAN_ROSE_SEEDS))))
		);

		this.add(
			TTBlocks.MANEDROP,
			this.applyExplosionDecay(
				TTBlocks.MANEDROP,
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(TTBlocks.MANEDROP)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(TTBlocks.MANEDROP)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
									)
							)
					)
			)
		);
		this.add(
			TTBlocks.MANEDROP_CROP,
			this.applyExplosionDecay(
				TTBlocks.MANEDROP_CROP,
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(TTItems.MANEDROP_GERM)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(TTBlocks.MANEDROP_CROP)
										.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ManedropCropBlock.AGE, ManedropCropBlock.MAX_AGE))
											.invert()
									)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(TTBlocks.MANEDROP_CROP)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
									)
							)
							.add(
								LootItem.lootTableItem(TTBlocks.MANEDROP)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(TTBlocks.MANEDROP_CROP)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ManedropCropBlock.AGE, ManedropCropBlock.MAX_AGE))
									)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(TTBlocks.MANEDROP_CROP)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
									)
							)
					)
			)
		);

		this.add(TTBlocks.DAWNTRAIL, this::createMultifaceBlockDrops);
		this.add(
			TTBlocks.DAWNTRAIL_CROP,
			this.applyExplosionDecay(TTBlocks.DAWNTRAIL_CROP, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(TTItems.DAWNTRAIL_SEEDS))))
		);

		this.dropSelf(TTBlocks.POLISHED_GRANITE_WALL);
		this.dropSelf(TTBlocks.CHISELED_GRANITE_BRICKS);
		this.dropSelf(TTBlocks.GRANITE_BRICKS);
		this.dropSelf(TTBlocks.CRACKED_GRANITE_BRICKS);
		this.dropSelf(TTBlocks.GRANITE_BRICK_STAIRS);
		this.add(TTBlocks.GRANITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.GRANITE_BRICK_WALL);
		this.dropSelf(TTBlocks.MOSSY_GRANITE_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS);
		this.add(TTBlocks.MOSSY_GRANITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_GRANITE_BRICK_WALL);

		this.dropSelf(TTBlocks.POLISHED_DIORITE_WALL);
		this.dropSelf(TTBlocks.CHISELED_DIORITE_BRICKS);
		this.dropSelf(TTBlocks.DIORITE_BRICKS);
		this.dropSelf(TTBlocks.CRACKED_DIORITE_BRICKS);
		this.dropSelf(TTBlocks.DIORITE_BRICK_STAIRS);
		this.add(TTBlocks.DIORITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.DIORITE_BRICK_WALL);
		this.dropSelf(TTBlocks.MOSSY_DIORITE_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS);
		this.add(TTBlocks.MOSSY_DIORITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_DIORITE_BRICK_WALL);

		this.dropSelf(TTBlocks.POLISHED_ANDESITE_WALL);
		this.dropSelf(TTBlocks.CHISELED_ANDESITE_BRICKS);
		this.dropSelf(TTBlocks.ANDESITE_BRICKS);
		this.dropSelf(TTBlocks.CRACKED_ANDESITE_BRICKS);
		this.dropSelf(TTBlocks.ANDESITE_BRICK_STAIRS);
		this.add(TTBlocks.ANDESITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.ANDESITE_BRICK_WALL);
		this.dropSelf(TTBlocks.MOSSY_ANDESITE_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS);
		this.add(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_ANDESITE_BRICK_WALL);

		this.dropSelf(TTBlocks.CALCITE_STAIRS);
		this.add(TTBlocks.CALCITE_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.CALCITE_WALL);
		this.dropSelf(TTBlocks.POLISHED_CALCITE);
		this.dropSelf(TTBlocks.POLISHED_CALCITE_STAIRS);
		this.add(TTBlocks.POLISHED_CALCITE_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.POLISHED_CALCITE_WALL);
		this.dropSelf(TTBlocks.CHISELED_CALCITE_BRICKS);
		this.dropSelf(TTBlocks.CALCITE_BRICKS);
		this.dropSelf(TTBlocks.CRACKED_CALCITE_BRICKS);
		this.dropSelf(TTBlocks.CALCITE_BRICK_STAIRS);
		this.add(TTBlocks.CALCITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.CALCITE_BRICK_WALL);
		this.dropSelf(TTBlocks.MOSSY_CALCITE_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS);
		this.add(TTBlocks.MOSSY_CALCITE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_CALCITE_BRICK_WALL);

		this.dropSelf(TTBlocks.CRACKED_TUFF_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_TUFF_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_TUFF_BRICK_STAIRS);
		this.add(TTBlocks.MOSSY_TUFF_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_TUFF_BRICK_WALL);

		this.dropSelf(TTBlocks.CRACKED_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_BRICK_STAIRS);
		this.add(TTBlocks.MOSSY_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_BRICK_WALL);

		this.dropSelf(TTBlocks.MOSSY_COBBLED_DEEPSLATE);
		this.dropSelf(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS);
		this.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL);

		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_TILES);
		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);
		this.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);

		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_BRICKS);
		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS);
		this.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL);

		this.dropSelf(TTBlocks.SMOOTH_SANDSTONE_WALL);
		this.dropSelf(TTBlocks.CUT_SANDSTONE_STAIRS);
		this.dropSelf(TTBlocks.CUT_SANDSTONE_WALL);

		this.dropSelf(TTBlocks.SMOOTH_RED_SANDSTONE_WALL);
		this.dropSelf(TTBlocks.CUT_RED_SANDSTONE_STAIRS);
		this.dropSelf(TTBlocks.CUT_RED_SANDSTONE_WALL);

		this.dropSelf(TTBlocks.PRISMARINE_BRICK_WALL);

		this.dropSelf(TTBlocks.DARK_PRISMARINE_WALL);

		this.dropSelf(TTBlocks.CHORAL_END_STONE);
		this.dropSelf(TTBlocks.CHORAL_END_STONE_STAIRS);
		this.add(TTBlocks.CHORAL_END_STONE_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.CHORAL_END_STONE_WALL);

		this.dropSelf(TTBlocks.CRACKED_END_STONE_BRICKS);
		this.dropSelf(TTBlocks.CHISELED_END_STONE_BRICKS);
		this.dropSelf(TTBlocks.CHORAL_END_STONE_BRICKS);
		this.dropSelf(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS);
		this.add(TTBlocks.CHORAL_END_STONE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(TTBlocks.CHORAL_END_STONE_BRICK_WALL);

		this.dropSelf(TTBlocks.CRACKED_PURPUR_BLOCK);
		this.dropSelf(TTBlocks.CHISELED_PURPUR_BLOCK);
		this.dropSelf(TTBlocks.PURPUR_WALL);

		this.add(TTBlocks.SUSPICIOUS_RED_SAND, noDrop());
		this.add(TTBlocks.SUSPICIOUS_DIRT, noDrop());
		this.add(TTBlocks.SUSPICIOUS_CLAY, noDrop());
		this.add(TTBlocks.COFFIN, noDrop());
		this.dropSelf(TTBlocks.SURVEYOR);
		this.dropSelf(TTBlocks.ECTOPLASM_BLOCK);
	}

	public LootTable.@NotNull Builder createMultifaceBlockDrops(Block drop) {
		return LootTable.lootTable()
			.withPool(
				LootPool.lootPool()
					.add(
						this.applyExplosionDecay(
							drop,
							LootItem.lootTableItem(drop)
								.apply(
									Direction.values(),
									direction -> SetItemCountFunction.setCount(ConstantValue.exactly(1F), true)
										.when(
											LootItemBlockStatePropertyCondition.hasBlockStateProperties(drop)
												.setProperties(
													StatePropertiesPredicate.Builder.properties().hasProperty(MultifaceBlock.getFaceProperty(direction), true)
												)
										)
								)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1F), true))
						)
					)
			);
	}
}
