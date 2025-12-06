/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.datagen.model;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.DawntrailBlock;
import net.frozenblock.trailiertales.block.DawntrailCropBlock;
import net.frozenblock.trailiertales.block.GuzmaniaCropBlock;
import net.frozenblock.trailiertales.block.LithopsBlock;
import net.frozenblock.trailiertales.block.LithopsCropBlock;
import net.frozenblock.trailiertales.block.ManedropCropBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.client.renderer.special.CoffinSpecialRenderer;
import net.frozenblock.trailiertales.datagen.TTDataGenerator;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.minecraft.Util;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamilies;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
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
	private static final ModelTemplate COFFIN_INVENTORY = createItem("template_coffin", TextureSlot.PARTICLE);
	public static final ModelTemplate CROP_CROSS = create("template_crop_cross", TextureSlot.CROSS);

	public TTModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.createPlantWithDefaultItem(TTBlocks.CYAN_ROSE, TTBlocks.POTTED_CYAN_ROSE, BlockModelGenerators.PlantType.NOT_TINTED);

		createManedropCrop(generator);
		generator.createDoublePlantWithDefaultItem(TTBlocks.MANEDROP, BlockModelGenerators.PlantType.NOT_TINTED);

		createGuzmaniaCrop(generator);
		generator.createDoublePlantWithDefaultItem(TTBlocks.GUZMANIA, BlockModelGenerators.PlantType.NOT_TINTED);

		createDawntrailCrop(generator);
		createDawntrail(generator);

		createLithopsCrop(generator);
		createLithops(generator);

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

		generator.createTrivialCube(TTBlocks.POLISHED_RESIN_BLOCK);
		generator.createTrivialCube(TTBlocks.CRACKED_RESIN_BRICKS);
		generator.family(TTBlocks.PALE_MOSSY_RESIN_BRICKS).generateFor(TTBlocks.FAMILY_PALE_MOSSY_RESIN_BRICKS);

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

		this.createCoffin(generator, TTBlocks.COFFIN, Blocks.DEEPSLATE, CoffinSpawnerState.INACTIVE.getHeadTexture(), CoffinSpawnerState.INACTIVE.getFootTexture());
	}

	public void wallSmooth(@NotNull BlockModelGenerators generator, Block wallBlock, Block originalBlock) {
		TextureMapping mapping = TexturedModel.createAllSame(TextureMapping.getBlockTexture(originalBlock, "_top")).getMapping();
		ResourceLocation resourceLocation = ModelTemplates.WALL_POST.create(wallBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation3 = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, mapping, generator.modelOutput);
		generator.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, resourceLocation, resourceLocation2, resourceLocation3));
		ResourceLocation resourceLocation4 = ModelTemplates.WALL_INVENTORY.create(wallBlock, mapping, generator.modelOutput);
		generator.registerSimpleItemModel(wallBlock, resourceLocation4);
	}

	public void wall(@NotNull BlockModelGenerators generator, Block wallBlock, Block originalBlock) {
		TextureMapping mapping = TexturedModel.CUBE.get(originalBlock).getMapping();
		ResourceLocation resourceLocation = ModelTemplates.WALL_POST.create(wallBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation3 = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, mapping, generator.modelOutput);
		generator.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, resourceLocation, resourceLocation2, resourceLocation3));
		ResourceLocation resourceLocation4 = ModelTemplates.WALL_INVENTORY.create(wallBlock, mapping, generator.modelOutput);
		generator.registerSimpleItemModel(wallBlock, resourceLocation4);
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
		generator.registerSimpleItemModel(stairsBlock, resourceLocation2);
	}

	public void createCoffin(@NotNull BlockModelGenerators generator, Block coffin, Block particleTexture, ResourceLocation headTexture, ResourceLocation footTexture) {
		generator.createParticleOnlyBlock(coffin, particleTexture);
		Item item = coffin.asItem();
		ResourceLocation coffinModel = COFFIN_INVENTORY.create(item, TextureMapping.particle(particleTexture), generator.modelOutput);
		ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(coffinModel, new CoffinSpecialRenderer.Unbaked(headTexture, footTexture));
		generator.itemModelOutput.accept(item, unbaked);
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
		generator.generateFlatItem(TTItems.MANEDROP_GERM, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.GUZMANIA_SEEDS, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(TTItems.MUSIC_DISC_STASIS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.MUSIC_DISC_FAUSSE_VIE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(TTItems.MUSIC_DISC_OSSUAIRE, ModelTemplates.FLAT_ITEM);

		generator.generateSpawnEgg(TTItems.APPARITION_SPAWN_EGG, 11712721, 10663385);
	}

	private static void createManedropCrop(@NotNull BlockModelGenerators generator) {
		final Block block = TTBlocks.MANEDROP_CROP;
		final PropertyDispatch propertyDispatch = PropertyDispatch.properties(ManedropCropBlock.AGE, BlockStateProperties.DOUBLE_BLOCK_HALF).generate((age, half) -> {
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
							CROP_CROSS.create(
								TTConstants.id("block/manedrop_crop_top_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/manedrop_top")),
								generator.modelOutput
							)
						);
					} else {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							CROP_CROSS.create(
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
							CROP_CROSS.create(
								TTConstants.id("block/manedrop_crop_bottom_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/manedrop_bottom")),
								generator.modelOutput
							)
						);
					} else {
						yield Variant.variant().with(VariantProperties.MODEL,
							CROP_CROSS.create(
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

	private static void createGuzmaniaCrop(BlockModelGenerators generator) {
		final Block block = TTBlocks.GUZMANIA_CROP;
		PropertyDispatch propertyDispatch = PropertyDispatch.properties(GuzmaniaCropBlock.AGE, BlockStateProperties.DOUBLE_BLOCK_HALF).generate((age, half) -> {
			return switch (half) {
				case UPPER -> {
					if (age < GuzmaniaCropBlock.DOUBLE_PLANT_AGE_INTERSECTION) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							TTConstants.id("block/guzmania_crop_top_empty")
						);
					} else if (age == GuzmaniaCropBlock.MAX_AGE) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							CROP_CROSS.create(
								TTConstants.id("block/guzmania_crop_top_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/guzmania_top")),
								generator.modelOutput
							)
						);
					} else {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							CROP_CROSS.create(
								TTConstants.id("block/guzmania_crop_top_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/guzmania_crop_top_stage_" + age)),
								generator.modelOutput
							)
						);
					}
				}
				case LOWER -> {
					if (age == GuzmaniaCropBlock.MAX_AGE) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							CROP_CROSS.create(
								TTConstants.id("block/guzmania_crop_bottom_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/guzmania_bottom")),
								generator.modelOutput
							)
						);
					} else {
						yield Variant.variant().with(VariantProperties.MODEL,
							CROP_CROSS.create(
								TTConstants.id("block/guzmania_crop_bottom_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/guzmania_crop_bottom_stage_" + age)),
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
		generator.registerSimpleFlatItemModel(block);
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

	private static void createLithopsCrop(BlockModelGenerators generator) {
		final Block crop = TTBlocks.LITHOPS_CROP;
		generator.registerSimpleFlatItemModel(crop.asItem());

		final ResourceLocation cropModel = generator.createSuffixedVariant(crop, "_stage_0", ModelTemplates.CROP, TextureMapping::crop);
		final ResourceLocation model1 = ModelLocationUtils.getModelLocation(crop, "_1_stage_1");
		final ResourceLocation model2 = ModelLocationUtils.getModelLocation(crop, "_2_stage_1");
		final ResourceLocation model3 = ModelLocationUtils.getModelLocation(crop, "_3_stage_1");
		final ResourceLocation model4 = ModelLocationUtils.getModelLocation(crop, "_4_stage_1");
		final ResourceLocation model12 = ModelLocationUtils.getModelLocation(crop, "_1_stage_2");
		final ResourceLocation model22 = ModelLocationUtils.getModelLocation(crop, "_2_stage_2");
		final ResourceLocation model32 = ModelLocationUtils.getModelLocation(crop, "_3_stage_2");
		final ResourceLocation model42 = ModelLocationUtils.getModelLocation(crop, "_4_stage_2");
		generator.blockStateOutput
			.accept(
				MultiPartGenerator.multiPart(crop)
					.with(Condition.condition().term(LithopsCropBlock.AGE, 0), Variant.variant().with(VariantProperties.MODEL, cropModel))

					.with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model1)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model2)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model3)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model4)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 1).term(LithopsCropBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					)

					.with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model12)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model12).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model12).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model12).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model22)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model22).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model22).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model22).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model32)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model32).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model32).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model32).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model42)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model42).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model42).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					).with(Condition.condition().term(LithopsCropBlock.AGE, 2).term(LithopsCropBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model42).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					)
			);
	}

	private static void createLithops(BlockModelGenerators generator) {
		final Block block = TTBlocks.LITHOPS;
		generator.registerSimpleFlatItemModel(block.asItem());

		final ResourceLocation model1 = ModelLocationUtils.getModelLocation(block, "_1");
		final ResourceLocation model2 = ModelLocationUtils.getModelLocation(block, "_2");
		final ResourceLocation model3 = ModelLocationUtils.getModelLocation(block, "_3");
		final ResourceLocation model4 = ModelLocationUtils.getModelLocation(block, "_4");
		generator.blockStateOutput
			.accept(
				MultiPartGenerator.multiPart(block)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 1, 2, 3, 4).term(LithopsBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model1)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 1, 2, 3, 4).term(LithopsBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 1, 2, 3, 4).term(LithopsBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 1, 2, 3, 4).term(LithopsBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 2, 3, 4).term(LithopsBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model2)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 2, 3, 4).term(LithopsBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 2, 3, 4).term(LithopsBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 2, 3, 4).term(LithopsBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 3, 4).term(LithopsBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model3)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 3, 4).term(LithopsBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 3, 4).term(LithopsBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 3, 4).term(LithopsBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 4).term(LithopsBlock.FACING, Direction.NORTH),
						Variant.variant().with(VariantProperties.MODEL, model4)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 4).term(LithopsBlock.FACING, Direction.EAST),
						Variant.variant().with(VariantProperties.MODEL, model4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 4).term(LithopsBlock.FACING, Direction.SOUTH),
						Variant.variant().with(VariantProperties.MODEL, model4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
					)
					.with(
						Condition.condition().term(LithopsBlock.AMOUNT, 4).term(LithopsBlock.FACING, Direction.WEST),
						Variant.variant().with(VariantProperties.MODEL, model4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
					)
			);

		final Block pottedBlock = TTBlocks.POTTED_LITHOPS;
		final ResourceLocation pottedModel = ModelLocationUtils.getModelLocation(pottedBlock);
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, pottedModel));
	}

	private static void createEctoplasmBlock(@NotNull BlockModelGenerators generator) {
		Block block = TTBlocks.ECTOPLASM_BLOCK;
		ResourceLocation model = TTConstants.id("block/ectoplasm_block");

		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model));
		generator.registerSimpleItemModel(block, model);
	}

	@Contract("_, _ -> new")
	private static @NotNull ModelTemplate createItem(String string, TextureSlot... textureSlots) {
		return new ModelTemplate(Optional.of(TTConstants.id("item/" + string)), Optional.empty(), textureSlots);
	}

	private static ModelTemplate create(String parent, TextureSlot... requiredTextures) {
		return new ModelTemplate(Optional.of(TTConstants.id("block/" + parent)), Optional.empty(), requiredTextures);
	}
}
