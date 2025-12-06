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

import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map;
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
import net.frozenblock.trailiertales.block.LithopsCropBlock;
import net.frozenblock.trailiertales.block.ManedropCropBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.client.renderer.special.CoffinSpecialRenderer;
import net.frozenblock.trailiertales.datagen.TTDataGenerator;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamilies;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Contract;

@Environment(EnvType.CLIENT)
public final class TTModelProvider extends FabricModelProvider {
	public static final Map<BooleanProperty, VariantMutator> MULTIFACE_GENERATOR_NO_UV_LOCK = Map.of(
		BlockStateProperties.NORTH, BlockModelGenerators.NOP,
		BlockStateProperties.EAST, BlockModelGenerators.Y_ROT_90,
		BlockStateProperties.SOUTH, BlockModelGenerators.Y_ROT_180,
		BlockStateProperties.WEST, BlockModelGenerators.Y_ROT_270,
		BlockStateProperties.UP, BlockModelGenerators.X_ROT_270,
		BlockStateProperties.DOWN, BlockModelGenerators.X_ROT_90
	);
	private static final ModelTemplate COFFIN_INVENTORY = createItem("template_coffin", TextureSlot.PARTICLE);
	public static final ModelTemplate CROP_CROSS = create("template_crop_cross", TextureSlot.CROSS);

	public TTModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generator) {
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

		generator.registerSimpleItemModel(TTBlocks.SURVEYOR, ModelLocationUtils.getModelLocation(TTBlocks.SURVEYOR));
	}

	public void wallSmooth(BlockModelGenerators generator, Block wallBlock, Block originalBlock) {
		final TextureMapping mapping = TexturedModel.createAllSame(TextureMapping.getBlockTexture(originalBlock, "_top")).getMapping();
		final Identifier post = ModelTemplates.WALL_POST.create(wallBlock, mapping, generator.modelOutput);
		final Identifier lowSide = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, mapping, generator.modelOutput);
		final Identifier tallSide = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, mapping, generator.modelOutput);

		MultiVariant postVariant = BlockModelGenerators.plainVariant(post);
		MultiVariant lowSideVariant = BlockModelGenerators.plainVariant(lowSide);
		MultiVariant tallSideVariant = BlockModelGenerators.plainVariant(tallSide);
		generator.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, postVariant, lowSideVariant, tallSideVariant));

		final Identifier inventory = ModelTemplates.WALL_INVENTORY.create(wallBlock, mapping, generator.modelOutput);
		generator.registerSimpleItemModel(wallBlock, inventory);
	}

	public void wall(BlockModelGenerators generator, Block wallBlock, Block originalBlock) {
		final TextureMapping mapping = TexturedModel.CUBE.get(originalBlock).getMapping();
		final Identifier post = ModelTemplates.WALL_POST.create(wallBlock, mapping, generator.modelOutput);
		final Identifier lowSide = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, mapping, generator.modelOutput);
		final Identifier tallSide = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, mapping, generator.modelOutput);

		final MultiVariant postVariant = BlockModelGenerators.plainVariant(post);
		final MultiVariant lowSideVariant = BlockModelGenerators.plainVariant(lowSide);
		final MultiVariant tallSideVariant = BlockModelGenerators.plainVariant(tallSide);
		generator.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, postVariant, lowSideVariant, tallSideVariant));

		final Identifier inventory = ModelTemplates.WALL_INVENTORY.create(wallBlock, mapping, generator.modelOutput);
		generator.registerSimpleItemModel(wallBlock, inventory);
	}

	public void stairsCut(BlockModelGenerators generator, Block stairsBlock, Block cutBlock, Block smoothBlock) {
		final TextureMapping mapping = new TextureMapping();
		mapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(cutBlock));
		mapping.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(smoothBlock, "_top"));
		mapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(smoothBlock, "_top"));

		final Identifier inner = ModelTemplates.STAIRS_INNER.create(stairsBlock, mapping, generator.modelOutput);
		final Identifier straight = ModelTemplates.STAIRS_STRAIGHT.create(stairsBlock, mapping, generator.modelOutput);
		final Identifier outer = ModelTemplates.STAIRS_OUTER.create(stairsBlock, mapping, generator.modelOutput);
		final MultiVariant innerVariant = BlockModelGenerators.plainVariant(inner);
		final MultiVariant straightVariant = BlockModelGenerators.plainVariant(straight);
		final MultiVariant outerVariant = BlockModelGenerators.plainVariant(outer);
		generator.blockStateOutput.accept(BlockModelGenerators.createStairs(stairsBlock, innerVariant, straightVariant, outerVariant));
		generator.registerSimpleItemModel(stairsBlock, straight);
	}

	public void createCoffin(BlockModelGenerators generator, Block coffin, Block particleTexture, Identifier headTexture, Identifier footTexture) {
		generator.createParticleOnlyBlock(coffin, particleTexture);
		final Item item = coffin.asItem();
		final Identifier coffinModel = COFFIN_INVENTORY.create(item, TextureMapping.particle(particleTexture), generator.modelOutput);
		final ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(coffinModel, new CoffinSpecialRenderer.Unbaked(headTexture, footTexture));
		generator.itemModelOutput.accept(item, unbaked);
	}

	@Override
	public void generateItemModels(ItemModelGenerators generator) {
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

		generator.generateFlatItem(TTItems.APPARITION_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
	}

	private static void createManedropCrop(BlockModelGenerators generator) {
		final Block block = TTBlocks.MANEDROP_CROP;
		final PropertyDispatch<MultiVariant> propertyDispatch = PropertyDispatch.initial(ManedropCropBlock.AGE, BlockStateProperties.DOUBLE_BLOCK_HALF).generate((age, half) -> {
			return switch (half) {
				case UPPER -> {
					if (age < ManedropCropBlock.DOUBLE_PLANT_AGE_INTERSECTION) {
						yield BlockModelGenerators.plainVariant(
							TTConstants.id("block/manedrop_crop_top_empty")
						);
					} else if (age == ManedropCropBlock.MAX_AGE) {
						yield BlockModelGenerators.plainVariant(
							CROP_CROSS.create(
								TTConstants.id("block/manedrop_crop_top_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/manedrop_top")),
								generator.modelOutput
							)
						);
					} else {
						yield BlockModelGenerators.plainVariant(
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
						yield BlockModelGenerators.plainVariant(
							CROP_CROSS.create(
								TTConstants.id("block/manedrop_crop_bottom_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/manedrop_bottom")),
								generator.modelOutput
							)
						);
					} else {
						yield BlockModelGenerators.plainVariant(
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
		generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(propertyDispatch));
	}

	private static void createGuzmaniaCrop(BlockModelGenerators generator) {
		final Block block = TTBlocks.GUZMANIA_CROP;
		final PropertyDispatch<MultiVariant> propertyDispatch = PropertyDispatch.initial(GuzmaniaCropBlock.AGE, BlockStateProperties.DOUBLE_BLOCK_HALF).generate((age, half) -> {
			return switch (half) {
				case UPPER -> {
					if (age < GuzmaniaCropBlock.DOUBLE_PLANT_AGE_INTERSECTION) {
						yield BlockModelGenerators.plainVariant(
							TTConstants.id("block/guzmania_crop_top_empty")
						);
					} else if (age == GuzmaniaCropBlock.MAX_AGE) {
						yield BlockModelGenerators.plainVariant(
							CROP_CROSS.create(
								TTConstants.id("block/guzmania_crop_top_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/guzmania_top")),
								generator.modelOutput
							)
						);
					} else {
						yield BlockModelGenerators.plainVariant(
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
						yield BlockModelGenerators.plainVariant(
							CROP_CROSS.create(
								TTConstants.id("block/guzmania_crop_bottom_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TTConstants.id("block/guzmania_bottom")),
								generator.modelOutput
							)
						);
					} else {
						yield BlockModelGenerators.plainVariant(
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
		generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(propertyDispatch));
	}

	private static void createDawntrail(BlockModelGenerators generator) {
		final Block block = TTBlocks.DAWNTRAIL;
		generator.registerSimpleFlatItemModel(block);
		final MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(block);

		final Identifier firstModel = TTConstants.id("block/dawntrail_stage_0");
		final Identifier secondModel = TTConstants.id("block/dawntrail_stage_1");
		final Identifier thirdModel = TTConstants.id("block/dawntrail_stage_2");
		final ConditionBuilder terminalCondition = Util.make(
			BlockModelGenerators.condition(), terminalConditionx -> MULTIFACE_GENERATOR_NO_UV_LOCK.keySet().forEach(booleanPropertyx -> {
				terminalConditionx.term(booleanPropertyx, false);
			})
		);

		final MultiVariant multiVariant = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block));
		for (Map.Entry<BooleanProperty, VariantMutator> pair : MULTIFACE_GENERATOR_NO_UV_LOCK.entrySet()) {
			final BooleanProperty booleanProperty = pair.getKey();
			final VariantMutator function = pair.getValue();
			if (!block.defaultBlockState().hasProperty(booleanProperty)) continue;
			multiPartGenerator.with(BlockModelGenerators.condition().term(booleanProperty, true).term(DawntrailBlock.AGE, 0), BlockModelGenerators.plainVariant(firstModel).with(function));
			multiPartGenerator.with(BlockModelGenerators.condition().term(booleanProperty, true).term(DawntrailBlock.AGE, 1), BlockModelGenerators.plainVariant(secondModel).with(function));
			multiPartGenerator.with(BlockModelGenerators.condition().term(booleanProperty, true).term(DawntrailBlock.AGE, 2), BlockModelGenerators.plainVariant(thirdModel).with(function));
			multiPartGenerator.with(terminalCondition, multiVariant.with(function));
		}

		generator.blockStateOutput.accept(multiPartGenerator);
	}

	private static void createDawntrailCrop(BlockModelGenerators generator) {
		final Block crop = TTBlocks.DAWNTRAIL_CROP;
		final IntegerProperty ageProperty = DawntrailCropBlock.AGE;
		final Int2ObjectMap<Identifier> int2ObjectMap = new Int2ObjectOpenHashMap<>();
		final PropertyDispatch<MultiVariant> propertyDispatch = PropertyDispatch.initial(ageProperty)
			.generate(
				age -> {
					Identifier identifier = int2ObjectMap.computeIfAbsent(
						age, (Int2ObjectFunction<? extends Identifier>)(key -> TTConstants.id("block/dawntrail_crop_stage_" + Math.min(age, 3)))
					);
					return BlockModelGenerators.plainVariant(identifier).with(BlockModelGenerators.X_ROT_90);
				}
				);
		generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(crop).with(propertyDispatch));
	}

	private static void createLithopsCrop(BlockModelGenerators generator) {
		final Block crop = TTBlocks.LITHOPS_CROP;
		generator.registerSimpleFlatItemModel(crop.asItem());

		final MultiVariant cropModel = BlockModelGenerators.plainVariant(generator.createSuffixedVariant(crop, "_stage_0", ModelTemplates.CROP, TextureMapping::crop));
		final MultiVariant model1 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(crop, "_1_stage_1"));
		final MultiVariant model2 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(crop, "_2_stage_1"));
		final MultiVariant model3 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(crop, "_3_stage_1"));
		final MultiVariant model4 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(crop, "_4_stage_1"));
		final MultiVariant model12 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(crop, "_1_stage_2"));
		final MultiVariant model22 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(crop, "_2_stage_2"));
		final MultiVariant model32 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(crop, "_3_stage_2"));
		final MultiVariant model42 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(crop, "_4_stage_2"));
		final Function<ConditionBuilder, ConditionBuilder> isStage1 = conditionBuilder -> conditionBuilder.term(LithopsCropBlock.AGE, 1);
		final Function<ConditionBuilder, ConditionBuilder> isStage2 = conditionBuilder -> conditionBuilder.term(LithopsCropBlock.AGE, 2);

		generator.blockStateOutput.accept(
			MultiPartGenerator.multiPart(crop)
				.with(BlockModelGenerators.condition().term(LithopsCropBlock.AGE, 0), cropModel)
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), model1)
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), model1.with(BlockModelGenerators.Y_ROT_90))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), model1.with(BlockModelGenerators.Y_ROT_180))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), model1.with(BlockModelGenerators.Y_ROT_270))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), model2)
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), model2.with(BlockModelGenerators.Y_ROT_90))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), model2.with(BlockModelGenerators.Y_ROT_180))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), model2.with(BlockModelGenerators.Y_ROT_270))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), model3)
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), model3.with(BlockModelGenerators.Y_ROT_90))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), model3.with(BlockModelGenerators.Y_ROT_180))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), model3.with(BlockModelGenerators.Y_ROT_270))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), model4)
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), model4.with(BlockModelGenerators.Y_ROT_90))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), model4.with(BlockModelGenerators.Y_ROT_180))
				.with(isStage1.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), model4.with(BlockModelGenerators.Y_ROT_270))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), model12)
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), model12.with(BlockModelGenerators.Y_ROT_90))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), model12.with(BlockModelGenerators.Y_ROT_180))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), model12.with(BlockModelGenerators.Y_ROT_270))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), model22)
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), model22.with(BlockModelGenerators.Y_ROT_90))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), model22.with(BlockModelGenerators.Y_ROT_180))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), model22.with(BlockModelGenerators.Y_ROT_270))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), model32)
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), model32.with(BlockModelGenerators.Y_ROT_90))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), model32.with(BlockModelGenerators.Y_ROT_180))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), model32.with(BlockModelGenerators.Y_ROT_270))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), model42)
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), model42.with(BlockModelGenerators.Y_ROT_90))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), model42.with(BlockModelGenerators.Y_ROT_180))
				.with(isStage2.apply(BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), model42.with(BlockModelGenerators.Y_ROT_270))
		);
	}

	private static void createLithops(BlockModelGenerators generator) {
		final Block block = TTBlocks.LITHOPS;
		generator.registerSimpleFlatItemModel(block.asItem());

		final MultiVariant model1 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block, "_1"));
		final MultiVariant model2 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block, "_2"));
		final MultiVariant model3 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block, "_3"));
		final MultiVariant model4 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block, "_4"));
		generator.createSegmentedBlock(
			block,
			model1, BlockModelGenerators.FLOWER_BED_MODEL_1_SEGMENT_CONDITION,
			model2, BlockModelGenerators.FLOWER_BED_MODEL_2_SEGMENT_CONDITION,
			model3, BlockModelGenerators.FLOWER_BED_MODEL_3_SEGMENT_CONDITION,
			model4, BlockModelGenerators.FLOWER_BED_MODEL_4_SEGMENT_CONDITION
		);

		final Block pottedBlock = TTBlocks.POTTED_LITHOPS;
		final MultiVariant pottedModel = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(pottedBlock));
		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, pottedModel));
	}

	private static void createEctoplasmBlock(BlockModelGenerators generator) {
		final Block block = TTBlocks.ECTOPLASM_BLOCK;
		final Identifier model = TTConstants.id("block/ectoplasm_block");
		final MultiVariant variant = BlockModelGenerators.plainVariant(model);

		generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, variant));
		generator.registerSimpleItemModel(block, model);
	}

	@Contract("_, _ -> new")
	private static ModelTemplate createItem(String string, TextureSlot... textureSlots) {
		return new ModelTemplate(Optional.of(TTConstants.id("item/" + string)), Optional.empty(), textureSlots);
	}

	private static ModelTemplate create(String parent, TextureSlot... requiredTextures) {
		return new ModelTemplate(Optional.of(TTConstants.id("block/" + parent)), Optional.empty(), requiredTextures);
	}
}
