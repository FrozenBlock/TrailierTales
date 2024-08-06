package net.frozenblock.trailiertales.datagen.recipe;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.TrailierFeatureFlags;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TTRecipeProvider extends FabricRecipeProvider {
	public TTRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void buildRecipes(RecipeOutput recipeOutput) {
		generateForEnabledBlockFamilies(recipeOutput, TrailierFeatureFlags.TRAILIER_TALES_FLAG_SET);
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlocks.SURVEYOR)
			.define('E', RegisterItems.ECTOPLASM)
			.define('A', Items.AMETHYST_SHARD)
			.define('R', Items.REDSTONE)
			.define('#', Blocks.COBBLESTONE)
			.pattern("###")
			.pattern("RAE")
			.pattern("###")
			.unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
			.unlockedBy("has_ectoplasm", has(RegisterItems.ECTOPLASM))
			.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SUSPICIOUS_GRAVEL, 4)
			.define('#', Items.GRAVEL)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.GRAVEL), has(Items.GRAVEL))
			.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SUSPICIOUS_SAND, 4)
			.define('#', Items.SAND)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.SAND), has(Items.SAND))
			.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.SUSPICIOUS_RED_SAND, 4)
			.define('#', Items.RED_SAND)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.RED_SAND), has(Items.RED_SAND))
			.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.SUSPICIOUS_DIRT, 4)
			.define('#', Items.DIRT)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
			.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.SUSPICIOUS_CLAY, 4)
			.define('#', Items.CLAY)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.CLAY), has(Items.CLAY))
			.save(recipeOutput);

		oneToOneConversionRecipe(recipeOutput, Items.CYAN_DYE, RegisterBlocks.CYAN_ROSE, "cyan_dye");

		// GRANITE

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS, 4)
			.define('#', Blocks.GRANITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_granite", has(Blocks.GRANITE))
			.save(recipeOutput);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, Blocks.GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, RegisterBlocks.GRANITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS, Blocks.GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_SLAB, RegisterBlocks.GRANITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_SLAB, Blocks.POLISHED_GRANITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_SLAB, Blocks.GRANITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_STAIRS, RegisterBlocks.GRANITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_STAIRS, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_STAIRS, Blocks.GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_WALL, RegisterBlocks.GRANITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_WALL, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_WALL, Blocks.GRANITE);

		// MOSSY GRANITE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_GRANITE_BRICKS)
			.requires(RegisterBlocks.GRANITE_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_granite_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_GRANITE_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_GRANITE_BRICKS)
			.requires(RegisterBlocks.GRANITE_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_granite_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_GRANITE_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_GRANITE_BRICK_SLAB, RegisterBlocks.MOSSY_GRANITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_GRANITE_BRICK_STAIRS, RegisterBlocks.MOSSY_GRANITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_GRANITE_BRICK_WALL, RegisterBlocks.MOSSY_GRANITE_BRICKS);

		// DIORITE

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS, 4)
			.define('#', Blocks.DIORITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_diorite", has(Blocks.DIORITE))
			.save(recipeOutput);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, Blocks.DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, RegisterBlocks.DIORITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS, Blocks.DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_SLAB, RegisterBlocks.DIORITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_SLAB, Blocks.POLISHED_DIORITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_SLAB, Blocks.DIORITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_STAIRS, RegisterBlocks.DIORITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_STAIRS, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_STAIRS, Blocks.DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_WALL, RegisterBlocks.DIORITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_WALL, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_WALL, Blocks.DIORITE);

		// MOSSY DIORITE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DIORITE_BRICKS)
			.requires(RegisterBlocks.DIORITE_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_diorite_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_DIORITE_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DIORITE_BRICKS)
			.requires(RegisterBlocks.DIORITE_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_diorite_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_DIORITE_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DIORITE_BRICK_SLAB, RegisterBlocks.MOSSY_DIORITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DIORITE_BRICK_STAIRS, RegisterBlocks.MOSSY_DIORITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_DIORITE_BRICK_WALL, RegisterBlocks.MOSSY_DIORITE_BRICKS);

		// ANDESITE

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS, 4)
			.define('#', Blocks.ANDESITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_andesite", has(Blocks.ANDESITE))
			.save(recipeOutput);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, Blocks.ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, RegisterBlocks.ANDESITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS, Blocks.ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_SLAB, RegisterBlocks.ANDESITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_SLAB, Blocks.POLISHED_ANDESITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_SLAB, Blocks.ANDESITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_STAIRS, RegisterBlocks.ANDESITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_STAIRS, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_STAIRS, Blocks.ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_WALL, RegisterBlocks.ANDESITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_WALL, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_WALL, Blocks.ANDESITE);

		// MOSSY ANDESITE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_ANDESITE_BRICKS)
			.requires(RegisterBlocks.ANDESITE_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_andesite_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_ANDESITE_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_ANDESITE_BRICKS)
			.requires(RegisterBlocks.ANDESITE_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_andesite_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_ANDESITE_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_ANDESITE_BRICK_SLAB, RegisterBlocks.MOSSY_ANDESITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_ANDESITE_BRICK_STAIRS, RegisterBlocks.MOSSY_ANDESITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_ANDESITE_BRICK_WALL, RegisterBlocks.MOSSY_ANDESITE_BRICKS);

		// CALCITE

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_SLAB, Blocks.CALCITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_STAIRS, Blocks.CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.CALCITE_WALL, Blocks.CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE, Blocks.CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_SLAB, Blocks.CALCITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_STAIRS, Blocks.CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_SLAB, RegisterBlocks.POLISHED_CALCITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_STAIRS, RegisterBlocks.POLISHED_CALCITE);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS, 4)
			.define('#', Blocks.CALCITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_calcite", has(Blocks.CALCITE))
			.save(recipeOutput);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE_BRICKS, Blocks.CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE_BRICKS, RegisterBlocks.POLISHED_CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE_BRICKS, RegisterBlocks.CALCITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS, Blocks.CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS, RegisterBlocks.POLISHED_CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_SLAB, RegisterBlocks.CALCITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_SLAB, RegisterBlocks.POLISHED_CALCITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_SLAB, Blocks.CALCITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_STAIRS, RegisterBlocks.CALCITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_STAIRS, RegisterBlocks.POLISHED_CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_STAIRS, Blocks.CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_WALL, RegisterBlocks.CALCITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_WALL, RegisterBlocks.POLISHED_CALCITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICK_WALL, Blocks.CALCITE);

		// MOSSY CALCITE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_CALCITE_BRICKS)
			.requires(RegisterBlocks.CALCITE_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_calcite_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_CALCITE_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_CALCITE_BRICKS)
			.requires(RegisterBlocks.CALCITE_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_calcite_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_CALCITE_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_CALCITE_BRICK_SLAB, RegisterBlocks.MOSSY_CALCITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_CALCITE_BRICK_STAIRS, RegisterBlocks.MOSSY_CALCITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_CALCITE_BRICK_WALL, RegisterBlocks.MOSSY_CALCITE_BRICKS);

		// TUFF BRICKS

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.TUFF_BRICKS), RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CRACKED_TUFF_BRICKS.asItem(), 0.1F, 200)
			.unlockedBy("has_tuff_bricks", has(Blocks.TUFF_BRICKS))
			.save(recipeOutput);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_TUFF_BRICKS)
			.requires(Blocks.TUFF_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_tuff_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_TUFF_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_TUFF_BRICKS)
			.requires(Blocks.TUFF_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_tuff_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_TUFF_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_TUFF_BRICK_SLAB, RegisterBlocks.MOSSY_TUFF_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_TUFF_BRICK_STAIRS, RegisterBlocks.MOSSY_TUFF_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_TUFF_BRICK_WALL, RegisterBlocks.MOSSY_TUFF_BRICKS);

		// BRICKS

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.BRICKS), RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CRACKED_BRICKS.asItem(), 0.1F, 200)
			.unlockedBy("has_bricks", has(Blocks.BRICKS))
			.save(recipeOutput);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_BRICKS)
			.requires(Blocks.BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_BRICKS)
			.requires(Blocks.BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_BRICK_SLAB, RegisterBlocks.MOSSY_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_BRICK_STAIRS, RegisterBlocks.MOSSY_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_BRICK_WALL, RegisterBlocks.MOSSY_BRICKS);

		// MOSSY COBBLED DEEPSLATE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE)
			.requires(Blocks.COBBLED_DEEPSLATE)
			.requires(Blocks.VINE)
			.group("mossy_cobbled_deepslate")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE)
			.requires(Blocks.COBBLED_DEEPSLATE)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_cobbled_deepslate")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_WALL, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);

		// MOSSY DEEPSLATE BRICKS

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS)
			.requires(Blocks.DEEPSLATE_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_deepslate_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS)
			.requires(Blocks.DEEPSLATE_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_deepslate_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);

		// MOSSY DEEPSLATE TILE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILES)
			.requires(Blocks.DEEPSLATE_TILES)
			.requires(Blocks.VINE)
			.group("mossy_deepslate_tiles")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_DEEPSLATE_TILES, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILES)
			.requires(Blocks.DEEPSLATE_TILES)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_deepslate_tiles")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_DEEPSLATE_TILES, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILES, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILES, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL, RegisterBlocks.MOSSY_COBBLED_DEEPSLATE);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB, RegisterBlocks.MOSSY_DEEPSLATE_TILES, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS, RegisterBlocks.MOSSY_DEEPSLATE_TILES);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL, RegisterBlocks.MOSSY_DEEPSLATE_TILES);

		// SANDSTONE

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CUT_SANDSTONE_STAIRS, Blocks.SANDSTONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CUT_SANDSTONE_STAIRS, Blocks.CUT_SANDSTONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CUT_SANDSTONE_WALL, Blocks.SANDSTONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CUT_SANDSTONE_WALL, Blocks.CUT_SANDSTONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.SMOOTH_SANDSTONE_WALL, Blocks.SMOOTH_SANDSTONE);

		// RED SANDSTONE

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CUT_RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CUT_RED_SANDSTONE_STAIRS, Blocks.CUT_RED_SANDSTONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CUT_RED_SANDSTONE_WALL, Blocks.RED_SANDSTONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CUT_RED_SANDSTONE_WALL, Blocks.CUT_RED_SANDSTONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.SMOOTH_RED_SANDSTONE_WALL, Blocks.SMOOTH_RED_SANDSTONE);

		// END STONE BRICKS

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.END_STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CRACKED_END_STONE_BRICKS.asItem(), 0.1F, 200)
			.unlockedBy("has_end_stone_bricks", has(Blocks.END_STONE_BRICKS))
			.save(recipeOutput);

		chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_END_STONE_BRICKS, Ingredient.of(Blocks.END_STONE_BRICK_SLAB))
			.unlockedBy("has_end_stone_bricks", has(Blocks.END_STONE_BRICKS))
			.unlockedBy("has_chiseled_end_stone_bricks", has(RegisterBlocks.CHISELED_END_STONE_BRICKS))
			.unlockedBy("has_end_stone_brick_slab", has(Blocks.END_STONE_BRICK_SLAB))
			.save(recipeOutput);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_BRICKS)
			.requires(Blocks.END_STONE_BRICKS)
			.requires(Items.CHORUS_FRUIT)
			.group("choral_end_stone_bricks")
			.unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.CHORAL_END_STONE_BRICKS, Items.CHORUS_FRUIT));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_END_STONE_BRICKS, Blocks.END_STONE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_END_STONE_BRICKS, Blocks.END_STONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_BRICK_SLAB, RegisterBlocks.CHORAL_END_STONE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_BRICK_SLAB, RegisterBlocks.CHORAL_END_STONE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_BRICK_STAIRS, RegisterBlocks.CHORAL_END_STONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_BRICK_STAIRS, RegisterBlocks.CHORAL_END_STONE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_BRICK_WALL, RegisterBlocks.CHORAL_END_STONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_BRICK_WALL, RegisterBlocks.CHORAL_END_STONE_BRICKS);

		// END STONE

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.END_STONE_STAIRS, Blocks.END_STONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.END_STONE_SLAB, Blocks.END_STONE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.END_STONE_WALL, Blocks.END_STONE);

		// CHORAL END STONE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE)
			.requires(Blocks.END_STONE)
			.requires(Items.CHORUS_FRUIT)
			.group("choral_end_stone")
			.unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.CHORAL_END_STONE, Items.CHORUS_FRUIT));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_STAIRS, RegisterBlocks.CHORAL_END_STONE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_SLAB, RegisterBlocks.CHORAL_END_STONE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHORAL_END_STONE_WALL, RegisterBlocks.CHORAL_END_STONE);

		// PURPUR

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.PURPUR_WALL, Blocks.PURPUR_BLOCK);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_PURPUR_BLOCK, Blocks.PURPUR_BLOCK);

		// DESOLATION SMITHING TEMPLATE

			ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
			.define('#', Items.DIAMOND)
			.define('C', Blocks.STONE_BRICKS)
			.define('S', RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE)
			.pattern("#S#")
			.pattern("#C#")
			.pattern("###")
			.unlockedBy("has_desolation_armor_trim_smithing_template", has(RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE))
			.save(recipeOutput);

		// UNDEAD SMITHING TEMPLATE

			ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
			.define('#', Items.DIAMOND)
			.define('C', RegisterBlocks.MOSSY_DEEPSLATE_BRICKS)
			.define('S', RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE)
			.pattern("#S#")
			.pattern("#C#")
			.pattern("###")
			.unlockedBy("has_undead_armor_trim_smithing_template", has(RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE))
			.save(recipeOutput);
	}

	public static void stonecutterResultFromBase(RecipeOutput exporter, RecipeCategory category, ItemLike ingredient, ItemLike result) {
		stonecutterResultFromBase(exporter, category, ingredient, result, 1);
	}

	@Contract("_, _ -> new")
	public static @NotNull String getConversionRecipeName(ItemLike from, ItemLike to) {
		return TrailierConstants.string(getItemName(from) + "_from_" + getItemName(to));
	}

	public static void stonecutterResultFromBase(RecipeOutput exporter, RecipeCategory category, ItemLike ingredient, ItemLike result, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(result), category, ingredient, count)
			.unlockedBy(getHasName(result), has(result))
			.save(exporter, getConversionRecipeName(ingredient, result) + "_stonecutting");
	}
}
