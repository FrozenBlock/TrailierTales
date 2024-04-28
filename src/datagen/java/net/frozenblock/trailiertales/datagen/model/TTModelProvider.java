package net.frozenblock.trailiertales.datagen.model;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import org.jetbrains.annotations.NotNull;

public final class TTModelProvider extends FabricModelProvider {

	public TTModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.createPlant(RegisterBlocks.CYAN_ROSE, RegisterBlocks.POTTED_CYAN_ROSE, BlockModelGenerators.TintState.NOT_TINTED);

		generator.family(RegisterBlocks.GRANITE_BRICKS).generateFor(RegisterBlocks.FAMILY_GRANITE_BRICK);
		generator.createTrivialBlock(RegisterBlocks.CHISELED_GRANITE_BRICKS, TexturedModel.CUBE);

		generator.family(RegisterBlocks.DIORITE_BRICKS).generateFor(RegisterBlocks.FAMILY_DIORITE_BRICK);
		generator.createTrivialBlock(RegisterBlocks.CHISELED_DIORITE_BRICKS, TexturedModel.CUBE);

		generator.family(RegisterBlocks.ANDESITE_BRICKS).generateFor(RegisterBlocks.FAMILY_ANDESITE_BRICK);
		generator.createTrivialBlock(RegisterBlocks.CHISELED_ANDESITE_BRICKS, TexturedModel.CUBE);

		generator.family(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE).generateFor(RegisterBlocks.FAMILY_MOSSY_COBBLED_DEEPSLATE);
		generator.family(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_DEEPSLATE_BRICKS);
		generator.family(RegisterBlocks.MOSSY_DEEPSLATE_TILES).generateFor(RegisterBlocks.FAMILY_MOSSY_DEEPSLATE_TILES);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(RegisterItems.CYAN_ROSE_SEEDS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.BULLSEYE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.WITHER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
	}
}
