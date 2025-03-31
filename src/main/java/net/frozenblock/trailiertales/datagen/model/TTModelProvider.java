/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.datagen.model;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.function.Function;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.DawntrailBlock;
import net.frozenblock.trailiertales.block.DawntrailCropBlock;
import net.frozenblock.trailiertales.block.ManedropCropBlock;
import net.frozenblock.trailiertales.datagen.TTDataGenerator;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import net.minecraft.Util;

public final class TTModelProvider extends FabricModelProvider {
	public static final List<Pair<BooleanProperty, Function<ResourceLocation, Variant>>> MULTIFACE_GENERATOR_NO_UV_LOCK = List.of(
		Pair.of(BlockStateProperties.NORTH, model -> Variant.variant().with(VariantProperties.MODEL, model)),
		Pair.of(
			BlockStateProperties.EAST,
			model -> Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
		),
		Pair.of(
			BlockStateProperties.SOUTH,
			model -> Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
		),
		Pair.of(
			BlockStateProperties.WEST,
			model -> Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
		),
		Pair.of(
			BlockStateProperties.UP,
			model -> Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
		),
		Pair.of(
			BlockStateProperties.DOWN,
			resourceLocation -> Variant.variant()
				.with(VariantProperties.MODEL, resourceLocation)
				.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
		)
	);

	public TTModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.createPlant(TTBlocks.CYAN_ROSE, TTBlocks.POTTED_CYAN_ROSE, BlockModelGenerators.TintState.NOT_TINTED);

		createManedropCrop(generator);
		generator.createDoublePlant(TTBlocks.MANEDROP, BlockModelGenerators.TintState.NOT_TINTED);

		createDawntrailCrop(generator);
		createDawntrail(generator);

		generator.createBrushableBlock(TTBlocks.SUSPICIOUS_RED_SAND);
		generator.createBrushableBlock(TTBlocks.SUSPICIOUS_DIRT);
		generator.createBrushableBlock(TTBlocks.SUSPICIOUS_CLAY);

		createEctoplasmBlock(generator);

		generator.family(Blocks.POLISHED_GRANITE).generateFor(BlockFamilies.POLISHED_GRANITE);
		generator.family(TTBlocks.GRANITE_BRICKS).generateFor(TTBlocks.FAMILY_GRANITE_BRICK);
		generator.family(TTBlocks.MOSSY_GRANITE_BRICKS).generateFor(TTBlocks.FAMILY_MOSSY_GRANITE_BRICK);

		generator.family(Blocks.POLISHED_DIORITE).generateFor(BlockFamilies.POLISHED_DIORITE);
		generator.family(TTBlocks.DIORITE_BRICKS).generateFor(TTBlocks.FAMILY_DIORITE_BRICK);
		generator.family(TTBlocks.MOSSY_DIORITE_BRICKS).generateFor(TTBlocks.FAMILY_MOSSY_DIORITE_BRICK);

		generator.family(Blocks.POLISHED_ANDESITE).generateFor(BlockFamilies.POLISHED_ANDESITE);
		generator.family(TTBlocks.ANDESITE_BRICKS).generateFor(TTBlocks.FAMILY_ANDESITE_BRICK);
		generator.family(TTBlocks.MOSSY_ANDESITE_BRICKS).generateFor(TTBlocks.FAMILY_MOSSY_ANDESITE_BRICK);

		BlockModelGenerators.BlockFamilyProvider calciteFamily = generator.family(Blocks.CALCITE);
		calciteFamily.skipGeneratingModelsFor.add(Blocks.CALCITE);
		calciteFamily.generateFor(TTDataGenerator.FAMILY_CALCITE);
		generator.family(TTBlocks.POLISHED_CALCITE).generateFor(TTBlocks.FAMILY_POLISHED_CALCITE);
		BlockModelGenerators.BlockFamilyProvider calciteBricksFamily = generator.family(TTBlocks.CALCITE_BRICKS);
		calciteBricksFamily.skipGeneratingModelsFor.add(TTBlocks.CHISELED_CALCITE_BRICKS);
		calciteBricksFamily.generateFor(TTBlocks.FAMILY_CALCITE_BRICK);
		generator.family(TTBlocks.MOSSY_CALCITE_BRICKS).generateFor(TTBlocks.FAMILY_MOSSY_CALCITE_BRICK);
		generator.createTrivialBlock(TTBlocks.CHISELED_CALCITE_BRICKS, TexturedModel.COLUMN_WITH_WALL);

		generator.createTrivialCube(TTBlocks.CRACKED_TUFF_BRICKS);
		generator.family(TTBlocks.MOSSY_TUFF_BRICKS).generateFor(TTBlocks.FAMILY_MOSSY_TUFF_BRICKS);

		generator.createTrivialCube(TTBlocks.CRACKED_BRICKS);
		generator.family(TTBlocks.MOSSY_BRICKS).generateFor(TTBlocks.FAMILY_MOSSY_BRICKS);

		generator.family(TTBlocks.MOSSY_COBBLED_DEEPSLATE).generateFor(TTBlocks.FAMILY_MOSSY_COBBLED_DEEPSLATE);
		generator.family(TTBlocks.MOSSY_DEEPSLATE_BRICKS).generateFor(TTBlocks.FAMILY_MOSSY_DEEPSLATE_BRICKS);
		generator.family(TTBlocks.MOSSY_DEEPSLATE_TILES).generateFor(TTBlocks.FAMILY_MOSSY_DEEPSLATE_TILES);

		this.wallSmooth(generator, TTBlocks.SMOOTH_SANDSTONE_WALL, Blocks.SANDSTONE);
		this.stairsCut(generator, TTBlocks.CUT_SANDSTONE_STAIRS, Blocks.CUT_SANDSTONE, Blocks.SANDSTONE);
		this.wall(generator, TTBlocks.CUT_SANDSTONE_WALL, Blocks.CUT_SANDSTONE);

		this.wallSmooth(generator, TTBlocks.SMOOTH_RED_SANDSTONE_WALL, Blocks.RED_SANDSTONE);
		this.stairsCut(generator, TTBlocks.CUT_RED_SANDSTONE_STAIRS, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE);
		this.wall(generator, TTBlocks.CUT_RED_SANDSTONE_WALL, Blocks.CUT_RED_SANDSTONE);

		this.wall(generator, TTBlocks.PRISMARINE_BRICK_WALL, Blocks.PRISMARINE_BRICKS);

		this.wall(generator, TTBlocks.DARK_PRISMARINE_WALL, Blocks.DARK_PRISMARINE);

		BlockModelGenerators.BlockFamilyProvider endStoneFamily = generator.family(Blocks.END_STONE);
		endStoneFamily.skipGeneratingModelsFor.add(Blocks.END_STONE);
		endStoneFamily.generateFor(TTDataGenerator.FAMILY_END_STONE);
		generator.family(TTBlocks.CHORAL_END_STONE).generateFor(TTBlocks.FAMILY_CHORAL_END_STONE);
		generator.createTrivialCube(TTBlocks.CRACKED_END_STONE_BRICKS);
		generator.createTrivialCube(TTBlocks.CHISELED_END_STONE_BRICKS);
		generator.family(TTBlocks.CHORAL_END_STONE_BRICKS).generateFor(TTBlocks.FAMILY_CHORAL_END_STONE_BRICKS);

		generator.createTrivialCube(TTBlocks.CRACKED_PURPUR_BLOCK);
		generator.createTrivialCube(TTBlocks.CHISELED_PURPUR_BLOCK);
		this.wall(generator, TTBlocks.PURPUR_WALL, Blocks.PURPUR_BLOCK);

		generator.blockEntityModels(TTConstants.id("block/coffin"), Blocks.DEEPSLATE_BRICKS)
			.createWithoutBlockItem(
				TTBlocks.COFFIN
			);
	}

	public void wallSmooth(@NotNull BlockModelGenerators generator, Block wallBlock, Block originalBlock) {
		TextureMapping mapping = TexturedModel.createAllSame(TextureMapping.getBlockTexture(originalBlock, "_top")).getMapping();
		ResourceLocation resourceLocation = ModelTemplates.WALL_POST.create(wallBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation3 = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, mapping, generator.modelOutput);
		generator.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, resourceLocation, resourceLocation2, resourceLocation3));
		ResourceLocation resourceLocation4 = ModelTemplates.WALL_INVENTORY.create(wallBlock, mapping, generator.modelOutput);
		generator.delegateItemModel(wallBlock, resourceLocation4);
	}

	public void wall(@NotNull BlockModelGenerators generator, Block wallBlock, Block originalBlock) {
		TextureMapping mapping = TexturedModel.CUBE.get(originalBlock).getMapping();
		ResourceLocation resourceLocation = ModelTemplates.WALL_POST.create(wallBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation3 = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, mapping, generator.modelOutput);
		generator.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, resourceLocation, resourceLocation2, resourceLocation3));
		ResourceLocation resourceLocation4 = ModelTemplates.WALL_INVENTORY.create(wallBlock, mapping, generator.modelOutput);
		generator.delegateItemModel(wallBlock, resourceLocation4);
	}

	public void stairsCut(@NotNull BlockModelGenerators generator, Block stairsBlock, Block cutBlock, Block smoothBlock) {
		TextureMapping textureMapping = new TextureMapping();
		textureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(cutBlock));
		textureMapping.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(smoothBlock, "_top"));
		textureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(smoothBlock, "_top"));

		ResourceLocation resourceLocation = ModelTemplates.STAIRS_INNER.create(stairsBlock, textureMapping, generator.modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.STAIRS_STRAIGHT.create(stairsBlock, textureMapping, generator.modelOutput);
		ResourceLocation resourceLocation3 = ModelTemplates.STAIRS_OUTER.create(stairsBlock, textureMapping, generator.modelOutput);
		generator.blockStateOutput.accept(BlockModelGenerators.createStairs(stairsBlock, resourceLocation, resourceLocation2, resourceLocation3));
		generator.delegateItemModel(stairsBlock, resourceLocation2);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(TTItems.AURORA_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.BAIT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.BLOOM_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.BOLT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.BULLSEYE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.CARRIER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.CLUCK_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.CRAWL_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.CRESCENT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.CULTIVATOR_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.DROUGHT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.ENCLOSURE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.ESSENCE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.EYE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.FOCUS_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.FROST_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.HARE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.HEIGHT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.HUMP_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.ILLUMINATOR_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.INCIDENCE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.LUMBER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.NAVIGATOR_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.NEEDLES_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.OMEN_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.PLUME_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.PROTECTION_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.SHED_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.SHINE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.SHOWER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.SPADE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.SPROUT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.VESSEL_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.WITHER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(TTItems.ECTOPLASM, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.CYAN_ROSE_SEEDS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.DAWNTRAIL_SEEDS, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(TTItems.MUSIC_DISC_STASIS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.MUSIC_DISC_FAUSSE_VIE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.MUSIC_DISC_OSSUAIRE, ModelTemplates.FLAT_ITEM);
	}

	private static void createManedropCrop(@NotNull BlockModelGenerators generator) {
		Block block = TTBlocks.MANEDROP_CROP;
		generator.createSimpleFlatItemModel(block.asItem());
		PropertyDispatch propertyDispatch = PropertyDispatch.properties(ManedropCropBlock.AGE, BlockStateProperties.DOUBLE_BLOCK_HALF).generate((age, half) -> {
			return switch (half) {
				case UPPER -> {
					if (age < ManedropCropBlock.DOUBLE_PLANT_AGE_INTERSECTION) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							TTConstants.id("block/manedrop_crop_top_empty")
						);
					} else if (age == ManedropCropBlock.MAX_AGE) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							TTConstants.id("block/manedrop_top")
						);
					} else {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							BlockModelGenerators.TintState.NOT_TINTED.getCross().create(
								TTConstants.id("block/manedrop_crop_top_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/manedrop_crop_top_stage_" + age)),
								generator.modelOutput
							)
						);
					}
				}
				case LOWER -> {
					if (age == ManedropCropBlock.MAX_AGE) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							TTConstants.id("block/manedrop_bottom")
						);
					} else {
						yield Variant.variant().with(VariantProperties.MODEL,
							BlockModelGenerators.TintState.NOT_TINTED.getCross().create(
								TTConstants.id("block/manedrop_crop_bottom_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/manedrop_crop_bottom_stage_" + age)),
								generator.modelOutput
							)
						);
					}
				}
			};
		});
		generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(propertyDispatch));
	}

	private static void createDawntrail(@NotNull BlockModelGenerators generator) {
		Block block = TTBlocks.DAWNTRAIL;
		generator.createSimpleFlatItemModel(block);
		MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(block);

		ResourceLocation firstModel = TTConstants.id("block/dawntrail_stage_0");
		ResourceLocation secondModel = TTConstants.id("block/dawntrail_stage_1");
		ResourceLocation thirdModel = TTConstants.id("block/dawntrail_stage_2");
		Condition.TerminalCondition terminalCondition = Util.make(
			Condition.condition(), terminalConditionx -> MULTIFACE_GENERATOR_NO_UV_LOCK.stream().map(Pair::getFirst).forEach(booleanPropertyx -> {
				terminalConditionx.term(booleanPropertyx, false);
			})
		);

		for (Pair<BooleanProperty, Function<ResourceLocation, Variant>> pair : MULTIFACE_GENERATOR_NO_UV_LOCK) {
			BooleanProperty booleanProperty = pair.getFirst();
			Function<ResourceLocation, Variant> function = pair.getSecond();
			if (block.defaultBlockState().hasProperty(booleanProperty)) {
				multiPartGenerator.with(Condition.condition().term(booleanProperty, true).term(DawntrailBlock.AGE, 0), function.apply(firstModel));
				multiPartGenerator.with(Condition.condition().term(booleanProperty, true).term(DawntrailBlock.AGE, 1), function.apply(secondModel));
				multiPartGenerator.with(Condition.condition().term(booleanProperty, true).term(DawntrailBlock.AGE, 2), function.apply(thirdModel));
				multiPartGenerator.with(terminalCondition, function.apply(firstModel));
			}
		}

		generator.blockStateOutput.accept(multiPartGenerator);
	}

	private static void createDawntrailCrop(@NotNull BlockModelGenerators generator) {
		Block crop = TTBlocks.DAWNTRAIL_CROP;
		IntegerProperty ageProperty = DawntrailCropBlock.AGE;
		Int2ObjectMap<ResourceLocation> int2ObjectMap = new Int2ObjectOpenHashMap<>();
		PropertyDispatch propertyDispatch = PropertyDispatch.property(ageProperty)
			.generate(
				age -> {
					ResourceLocation resourceLocation = int2ObjectMap.computeIfAbsent(
						age, (Int2ObjectFunction<? extends ResourceLocation>)(key -> TTConstants.id("block/dawntrail_crop_stage_" + Math.min(age, 3)))
					);
					return Variant.variant().with(VariantProperties.MODEL, resourceLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90);
				}
				);
		generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(crop).with(propertyDispatch));
	}

	private static void createEctoplasmBlock(@NotNull BlockModelGenerators generator) {
		Block block = TTBlocks.ECTOPLASM_BLOCK;
		ResourceLocation model = TTConstants.id("block/ectoplasm_block");

		generator.blockStateOutput.accept( BlockModelGenerators.createSimpleBlock(block, model));
		generator.delegateItemModel(block, model);
	}
}
