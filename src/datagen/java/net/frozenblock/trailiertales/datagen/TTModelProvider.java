package net.frozenblock.trailiertales.datagen;

import java.util.function.Function;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.block.LumibloomBlock;
import net.frozenblock.trailiertales.block.ManedropCropBlock;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.Util;
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
import org.jetbrains.annotations.NotNull;

public final class TTModelProvider extends FabricModelProvider {

	public TTModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.createPlant(RegisterBlocks.CYAN_ROSE, RegisterBlocks.POTTED_CYAN_ROSE, BlockModelGenerators.TintState.NOT_TINTED);

		createManedropCrop(generator);
		generator.createDoublePlant(RegisterBlocks.MANEDROP, BlockModelGenerators.TintState.NOT_TINTED);

		createLumibloom(generator);

		generator.family(Blocks.POLISHED_GRANITE).generateFor(BlockFamilies.POLISHED_GRANITE);
		generator.family(RegisterBlocks.GRANITE_BRICKS).generateFor(RegisterBlocks.FAMILY_GRANITE_BRICK);
		generator.family(RegisterBlocks.MOSSY_GRANITE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_GRANITE_BRICK);

		generator.family(Blocks.POLISHED_DIORITE).generateFor(BlockFamilies.POLISHED_DIORITE);
		generator.family(RegisterBlocks.DIORITE_BRICKS).generateFor(RegisterBlocks.FAMILY_DIORITE_BRICK);
		generator.family(RegisterBlocks.MOSSY_DIORITE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_DIORITE_BRICK);

		generator.family(Blocks.POLISHED_ANDESITE).generateFor(BlockFamilies.POLISHED_ANDESITE);
		generator.family(RegisterBlocks.ANDESITE_BRICKS).generateFor(RegisterBlocks.FAMILY_ANDESITE_BRICK);
		generator.family(RegisterBlocks.MOSSY_ANDESITE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_ANDESITE_BRICK);

		BlockModelGenerators.BlockFamilyProvider calciteFamily = generator.family(Blocks.CALCITE);
		calciteFamily.skipGeneratingModelsFor.add(Blocks.CALCITE);
		calciteFamily.generateFor(TTDataGenerator.FAMILY_CALCITE);
		generator.family(RegisterBlocks.POLISHED_CALCITE).generateFor(RegisterBlocks.FAMILY_POLISHED_CALCITE);
		BlockModelGenerators.BlockFamilyProvider calciteBricksFamily = generator.family(RegisterBlocks.CALCITE_BRICKS);
		calciteBricksFamily.skipGeneratingModelsFor.add(RegisterBlocks.CHISELED_CALCITE_BRICKS);
		calciteBricksFamily.generateFor(RegisterBlocks.FAMILY_CALCITE_BRICK);
		generator.family(RegisterBlocks.MOSSY_CALCITE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_CALCITE_BRICK);
		generator.createTrivialBlock(RegisterBlocks.CHISELED_CALCITE_BRICKS, TexturedModel.COLUMN_WITH_WALL);

		generator.createTrivialCube(RegisterBlocks.CRACKED_TUFF_BRICKS);
		generator.family(RegisterBlocks.MOSSY_TUFF_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_TUFF_BRICKS);

		generator.createTrivialCube(RegisterBlocks.CRACKED_BRICKS);
		generator.family(RegisterBlocks.MOSSY_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_BRICKS);

		generator.family(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE).generateFor(RegisterBlocks.FAMILY_MOSSY_COBBLED_DEEPSLATE);
		generator.family(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_DEEPSLATE_BRICKS);
		generator.family(RegisterBlocks.MOSSY_DEEPSLATE_TILES).generateFor(RegisterBlocks.FAMILY_MOSSY_DEEPSLATE_TILES);

		this.wallSmooth(generator, RegisterBlocks.SMOOTH_SANDSTONE_WALL, Blocks.SANDSTONE);
		this.stairs(generator, RegisterBlocks.CUT_SANDSTONE_STAIRS, Blocks.CUT_SANDSTONE);
		this.wall(generator, RegisterBlocks.CUT_SANDSTONE_WALL, Blocks.CUT_SANDSTONE);

		this.wallSmooth(generator, RegisterBlocks.SMOOTH_RED_SANDSTONE_WALL, Blocks.RED_SANDSTONE);
		this.stairs(generator, RegisterBlocks.CUT_RED_SANDSTONE_STAIRS, Blocks.CUT_RED_SANDSTONE);
		this.wall(generator, RegisterBlocks.CUT_RED_SANDSTONE_WALL, Blocks.CUT_RED_SANDSTONE);

		BlockModelGenerators.BlockFamilyProvider endStoneFamily = generator.family(Blocks.END_STONE);
		endStoneFamily.skipGeneratingModelsFor.add(Blocks.END_STONE);
		endStoneFamily.generateFor(TTDataGenerator.FAMILY_END_STONE);
		generator.family(RegisterBlocks.CHORAL_END_STONE).generateFor(RegisterBlocks.FAMILY_CHORAL_END_STONE);
		generator.createTrivialCube(RegisterBlocks.CRACKED_END_STONE_BRICKS);
		generator.createTrivialCube(RegisterBlocks.CHISELED_END_STONE_BRICKS);
		generator.family(RegisterBlocks.CHORAL_END_STONE_BRICKS).generateFor(RegisterBlocks.FAMILY_CHORAL_END_STONE_BRICKS);

		generator.createTrivialCube(RegisterBlocks.CRACKED_PURPUR_BLOCK);
		generator.createTrivialCube(RegisterBlocks.CHISELED_PURPUR_BLOCK);
		this.wall(generator, RegisterBlocks.PURPUR_WALL, Blocks.PURPUR_BLOCK);

		generator.blockEntityModels(TrailierConstants.id("block/coffin"), Blocks.DEEPSLATE_BRICKS)
			.createWithoutBlockItem(
				RegisterBlocks.COFFIN
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

	public void stairs(@NotNull BlockModelGenerators generator, Block stairsBlock, Block originalBlock) {
		TextureMapping mapping = TexturedModel.CUBE.get(originalBlock).getMapping();
		ResourceLocation resourceLocation = ModelTemplates.STAIRS_INNER.create(stairsBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.STAIRS_STRAIGHT.create(stairsBlock, mapping, generator.modelOutput);
		ResourceLocation resourceLocation3 = ModelTemplates.STAIRS_OUTER.create(stairsBlock, mapping, generator.modelOutput);
		generator.blockStateOutput.accept(BlockModelGenerators.createStairs(stairsBlock, resourceLocation, resourceLocation2, resourceLocation3));
		generator.delegateItemModel(stairsBlock, resourceLocation2);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(RegisterItems.BAIT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.BLOOM_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.BOLT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.BULLSEYE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CRAWL_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CRESCENT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CULTIVATOR_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.DROUGHT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.ESSENCE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.EYE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.FOCUS_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.HUMP_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.INCIDENCE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.LUMBER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.NAVIGATOR_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.NEEDLES_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.PLUME_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.PROTECTION_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SHED_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SHINE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SHOWER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SPADE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SPROUT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.VESSEL_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.WITHER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(RegisterItems.ECTOPLASM, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CYAN_ROSE_SEEDS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.MUSIC_DISC_FAUSSE_VIE, ModelTemplates.FLAT_ITEM);
	}

	private static void createManedropCrop(@NotNull BlockModelGenerators generator) {
		Block block = RegisterBlocks.MANEDROP_CROP;
		generator.createSimpleFlatItemModel(block.asItem());
		PropertyDispatch propertyDispatch = PropertyDispatch.properties(ManedropCropBlock.AGE, BlockStateProperties.DOUBLE_BLOCK_HALF).generate((age, half) -> {
			return switch (half) {
				case UPPER -> {
					if (age < ManedropCropBlock.DOUBLE_PLANT_AGE_INTERSECTION) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							TrailierConstants.id("block/manedrop_crop_top_empty")
						);
					} else if (age == ManedropCropBlock.MAX_AGE) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							TrailierConstants.id("block/manedrop_top")
						);
					} else {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							BlockModelGenerators.TintState.NOT_TINTED.getCross().create(
								TrailierConstants.id("block/manedrop_crop_top_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TrailierConstants.id("block/manedrop_crop_top_stage_" + age)),
								generator.modelOutput
							)
						);
					}
				}
				case LOWER -> {
					if (age == ManedropCropBlock.MAX_AGE) {
						yield Variant.variant().with(
							VariantProperties.MODEL,
							TrailierConstants.id("block/manedrop_bottom")
						);
					} else {
						yield Variant.variant().with(VariantProperties.MODEL,
							BlockModelGenerators.TintState.NOT_TINTED.getCross().create(
								TrailierConstants.id("block/manedrop_crop_bottom_stage_" + age),
								TextureMapping.singleSlot(TextureSlot.CROSS, TrailierConstants.id("block/manedrop_crop_bottom_stage_" + age)),
								generator.modelOutput
							)
						);
					}
				}
			};
		});
		generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(propertyDispatch));
	}

	private static void createLumibloom(@NotNull BlockModelGenerators generator) {
		Block block = RegisterBlocks.LUMIBLOOM;
		generator.createSimpleFlatItemModel(block);
		MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(block);

		ResourceLocation firstModel = TrailierConstants.id("block/lumibloom_stage_0");
		ResourceLocation secondModel = TrailierConstants.id("block/lumibloom_stage_1");
		ResourceLocation thirdModel = TrailierConstants.id("block/lumibloom_stage_2");
		Condition.TerminalCondition terminalCondition = Util.make(
			Condition.condition(), terminalConditionx -> BlockModelGenerators.MULTIFACE_GENERATOR.stream().map(Pair::getFirst).forEach(booleanPropertyx -> {
				terminalConditionx.term(booleanPropertyx, false);
			})
		);

		for (Pair<BooleanProperty, Function<ResourceLocation, Variant>> pair : BlockModelGenerators.MULTIFACE_GENERATOR) {
			BooleanProperty booleanProperty = pair.getFirst();
			Function<ResourceLocation, Variant> function = pair.getSecond();
			if (block.defaultBlockState().hasProperty(booleanProperty)) {
				multiPartGenerator.with(Condition.condition().term(booleanProperty, true).term(LumibloomBlock.AGE, 0), function.apply(firstModel));
				multiPartGenerator.with(Condition.condition().term(booleanProperty, true).term(LumibloomBlock.AGE, 1), function.apply(secondModel));
				multiPartGenerator.with(Condition.condition().term(booleanProperty, true).term(LumibloomBlock.AGE, 2), function.apply(thirdModel));
				multiPartGenerator.with(terminalCondition, function.apply(firstModel));
			}
		}

		generator.blockStateOutput.accept(multiPartGenerator);
	}
}
