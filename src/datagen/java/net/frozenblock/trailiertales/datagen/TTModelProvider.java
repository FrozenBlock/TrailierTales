package net.frozenblock.trailiertales.datagen;

import java.util.Optional;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class TTModelProvider extends FabricModelProvider {
	private static final ModelTemplate COFFIN_INVENTORY = createItem("template_coffin", TextureSlot.PARTICLE);

	public TTModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.createPlant(RegisterBlocks.CYAN_ROSE, RegisterBlocks.POTTED_CYAN_ROSE, BlockModelGenerators.TintState.NOT_TINTED);

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
		generator.generateFlatItem(RegisterItems.BULLSEYE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CRESCENT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CULTIVATOR_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.ESSENCE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.EYE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.INCIDENCE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.LUMBER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.NEEDLES_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.PROTECTION_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SHINE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SHOWER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SPADE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.VESSEL_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.WITHER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);

		generator.generateFlatItem(RegisterItems.ECTOPLASM, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CYAN_ROSE_SEEDS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.MUSIC_DISC_FAUSSE_VIE, ModelTemplates.FLAT_ITEM);
		COFFIN_INVENTORY.create(ModelLocationUtils.getModelLocation(RegisterBlocks.COFFIN.asItem()), TextureMapping.particle(Blocks.DEEPSLATE_BRICKS), generator.output);
	}

	@Contract("_, _ -> new")
	private static @NotNull ModelTemplate createItem(String itemModelLocation, TextureSlot... requiredSlots) {
		return new ModelTemplate(Optional.of(TrailierConstants.id("item/" + itemModelLocation)), Optional.empty(), requiredSlots);
	}
}
