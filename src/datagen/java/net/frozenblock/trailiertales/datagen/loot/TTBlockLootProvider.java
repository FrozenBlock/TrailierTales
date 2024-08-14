package net.frozenblock.trailiertales.datagen.loot;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.frozenblock.trailiertales.block.ManedropCropBlock;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
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
		this.add(
			RegisterBlocks.MANEDROP_CROP,
			this.applyExplosionDecay(
				RegisterBlocks.MANEDROP_CROP,
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(RegisterItems.MANEDROP_GERM)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.MANEDROP_CROP)
										.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ManedropCropBlock.AGE, ManedropCropBlock.MAX_AGE))
											.invert()
									)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.MANEDROP_CROP)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
									)
							)
							.add(
								LootItem.lootTableItem(RegisterBlocks.MANEDROP)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.MANEDROP_CROP)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ManedropCropBlock.AGE, ManedropCropBlock.MAX_AGE))
									)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.MANEDROP_CROP)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
									)
							)
					)
			)
		);

		this.add(
			RegisterBlocks.MANEDROP,
			this.applyExplosionDecay(
				RegisterBlocks.MANEDROP,
				LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.add(
								LootItem.lootTableItem(RegisterBlocks.MANEDROP)
									.when(
										LootItemBlockStatePropertyCondition.hasBlockStateProperties(RegisterBlocks.MANEDROP)
											.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
									)
							)
					)
			)
		);

		this.dropSelf(RegisterBlocks.POLISHED_GRANITE_WALL);
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

		this.dropSelf(RegisterBlocks.POLISHED_DIORITE_WALL);
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

		this.dropSelf(RegisterBlocks.POLISHED_ANDESITE_WALL);
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

		this.dropSelf(RegisterBlocks.CRACKED_TUFF_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_TUFF_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_TUFF_BRICK_STAIRS);
		this.add(RegisterBlocks.MOSSY_TUFF_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_TUFF_BRICK_WALL);

		this.dropSelf(RegisterBlocks.CRACKED_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_BRICKS);
		this.dropSelf(RegisterBlocks.MOSSY_BRICK_STAIRS);
		this.add(RegisterBlocks.MOSSY_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.MOSSY_BRICK_WALL);

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

		this.dropSelf(RegisterBlocks.SMOOTH_SANDSTONE_WALL);
		this.dropSelf(RegisterBlocks.CUT_SANDSTONE_STAIRS);
		this.dropSelf(RegisterBlocks.CUT_SANDSTONE_WALL);

		this.dropSelf(RegisterBlocks.SMOOTH_RED_SANDSTONE_WALL);
		this.dropSelf(RegisterBlocks.CUT_RED_SANDSTONE_STAIRS);
		this.dropSelf(RegisterBlocks.CUT_RED_SANDSTONE_WALL);

		this.dropSelf(RegisterBlocks.CHORAL_END_STONE);
		this.dropSelf(RegisterBlocks.CHORAL_END_STONE_STAIRS);
		this.add(RegisterBlocks.CHORAL_END_STONE_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.CHORAL_END_STONE_WALL);

		this.dropSelf(RegisterBlocks.CRACKED_END_STONE_BRICKS);
		this.dropSelf(RegisterBlocks.CHISELED_END_STONE_BRICKS);
		this.dropSelf(RegisterBlocks.CHORAL_END_STONE_BRICKS);
		this.dropSelf(RegisterBlocks.CHORAL_END_STONE_BRICK_STAIRS);
		this.add(RegisterBlocks.CHORAL_END_STONE_BRICK_SLAB, this::createSlabItemTable);
		this.dropSelf(RegisterBlocks.CHORAL_END_STONE_BRICK_WALL);

		this.dropSelf(RegisterBlocks.CRACKED_PURPUR_BLOCK);
		this.dropSelf(RegisterBlocks.CHISELED_PURPUR_BLOCK);
		this.dropSelf(RegisterBlocks.PURPUR_WALL);

		this.add(RegisterBlocks.SUSPICIOUS_RED_SAND, noDrop());
		this.add(RegisterBlocks.SUSPICIOUS_DIRT, noDrop());
		this.add(RegisterBlocks.SUSPICIOUS_CLAY, noDrop());
		this.add(RegisterBlocks.COFFIN, noDrop());
		this.dropSelf(RegisterBlocks.SURVEYOR);
	}

}
