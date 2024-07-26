package net.frozenblock.trailiertales.datagen.model;

import java.util.Optional;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
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

		generator.family(RegisterBlocks.GRANITE_BRICKS).generateFor(RegisterBlocks.FAMILY_GRANITE_BRICK);
		generator.family(RegisterBlocks.MOSSY_GRANITE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_GRANITE_BRICK);

		generator.family(RegisterBlocks.DIORITE_BRICKS).generateFor(RegisterBlocks.FAMILY_DIORITE_BRICK);
		generator.family(RegisterBlocks.MOSSY_DIORITE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_DIORITE_BRICK);

		generator.family(RegisterBlocks.ANDESITE_BRICKS).generateFor(RegisterBlocks.FAMILY_ANDESITE_BRICK);
		generator.family(RegisterBlocks.MOSSY_ANDESITE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_ANDESITE_BRICK);

		generator.family(Blocks.CALCITE).generateFor(RegisterBlocks.FAMILY_CALCITE);
		generator.family(RegisterBlocks.POLISHED_CALCITE).generateFor(RegisterBlocks.FAMILY_POLISHED_CALCITE);
		generator.family(RegisterBlocks.CALCITE_BRICKS).generateFor(RegisterBlocks.FAMILY_CALCITE_BRICK);
		generator.family(RegisterBlocks.MOSSY_CALCITE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_CALCITE_BRICK);

		generator.createTrivialCube(RegisterBlocks.CRACKED_TUFF_BRICKS);
		generator.family(RegisterBlocks.MOSSY_TUFF_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_TUFF_BRICKS);

		generator.family(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE).generateFor(RegisterBlocks.FAMILY_MOSSY_COBBLED_DEEPSLATE);
		generator.family(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS).generateFor(RegisterBlocks.FAMILY_MOSSY_DEEPSLATE_BRICKS);
		generator.family(RegisterBlocks.MOSSY_DEEPSLATE_TILES).generateFor(RegisterBlocks.FAMILY_MOSSY_DEEPSLATE_TILES);

		generator.blockEntityModels(TrailierConstants.id("block/coffin"), Blocks.DEEPSLATE_BRICKS)
			.createWithoutBlockItem(
				RegisterBlocks.COFFIN
			);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(RegisterItems.CYAN_ROSE_SEEDS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.BULLSEYE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.WITHER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.BLOOM_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.INCIDENCE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.CULTIVATOR_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.SPADE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.MUSIC_DISC_FAUSSE_VIE, ModelTemplates.FLAT_ITEM);
		COFFIN_INVENTORY.create(ModelLocationUtils.getModelLocation(RegisterBlocks.COFFIN.asItem()), TextureMapping.particle(Blocks.DEEPSLATE_BRICKS), generator.output);
	}

	@Contract("_, _ -> new")
	private static @NotNull ModelTemplate createItem(String itemModelLocation, TextureSlot... requiredSlots) {
		return new ModelTemplate(Optional.of(TrailierConstants.id("item/" + itemModelLocation)), Optional.empty(), requiredSlots);
	}
}
