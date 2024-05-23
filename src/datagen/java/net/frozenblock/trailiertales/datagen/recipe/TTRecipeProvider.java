/*
 * Copyright 2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.datagen.recipe;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class TTRecipeProvider extends FabricRecipeProvider {
	public TTRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void buildRecipes(RecipeOutput recipeOutput) {
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

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegisterBlocks.POLISHED_GRANITE_BRICKS), RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CRACKED_POLISHED_GRANITE_BRICKS.asItem(), 0.1F, 200)
			.unlockedBy("has_polished_granite_bricks", has(RegisterBlocks.POLISHED_GRANITE_BRICKS))
			.save(recipeOutput);

		chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_GRANITE, Ingredient.of(Blocks.POLISHED_GRANITE_SLAB))
			.unlockedBy("has_polished_granite", has(Blocks.POLISHED_GRANITE))
			.unlockedBy("has_chiseled_polished_granite", has(RegisterBlocks.CHISELED_POLISHED_GRANITE))
			.unlockedBy("has_polished_granite_slab", has(Blocks.POLISHED_GRANITE_SLAB))
			.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICKS, 4)
			.define('#', Blocks.POLISHED_GRANITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_polished_granite", has(Blocks.POLISHED_GRANITE))
			.save(recipeOutput);
		slabBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_SLAB, Ingredient.of(RegisterBlocks.POLISHED_GRANITE_BRICKS))
			.unlockedBy("has_granite_bricks", has(RegisterBlocks.POLISHED_GRANITE_BRICKS))
			.save(recipeOutput);
		stairBuilder(RegisterBlocks.POLISHED_GRANITE_BRICK_STAIRS, Ingredient.of(RegisterBlocks.POLISHED_GRANITE_BRICKS))
			.unlockedBy("has_granite_bricks", has(RegisterBlocks.POLISHED_GRANITE_BRICKS))
			.save(recipeOutput);
		wall(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_GRANITE_BRICK_WALL, RegisterBlocks.POLISHED_GRANITE_BRICKS);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_GRANITE, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_GRANITE, Blocks.GRANITE);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICKS, Blocks.GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICKS, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_SLAB, RegisterBlocks.POLISHED_GRANITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_SLAB, Blocks.POLISHED_GRANITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_SLAB, Blocks.GRANITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_STAIRS, RegisterBlocks.POLISHED_GRANITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_STAIRS, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_STAIRS, Blocks.GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_WALL, RegisterBlocks.POLISHED_GRANITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_WALL, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_GRANITE_BRICK_WALL, Blocks.GRANITE);

		// MOSSY GRANITE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICKS)
			.requires(RegisterBlocks.POLISHED_GRANITE_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_polished_granite_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICKS)
			.requires(RegisterBlocks.POLISHED_GRANITE_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_polished_granite_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICK_SLAB, RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICK_STAIRS, RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICK_WALL, RegisterBlocks.MOSSY_POLISHED_GRANITE_BRICKS);

		// DIORITE

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegisterBlocks.POLISHED_DIORITE_BRICKS), RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CRACKED_POLISHED_DIORITE_BRICKS.asItem(), 0.1F, 200)
			.unlockedBy("has_polished_diorite_bricks", has(RegisterBlocks.POLISHED_DIORITE_BRICKS))
			.save(recipeOutput);

		chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_DIORITE, Ingredient.of(Blocks.POLISHED_DIORITE_SLAB))
			.unlockedBy("has_polished_diorite", has(Blocks.POLISHED_DIORITE))
			.unlockedBy("has_chiseled_polished_diorite", has(RegisterBlocks.CHISELED_POLISHED_DIORITE))
			.unlockedBy("has_polished_diorite_slab", has(Blocks.POLISHED_DIORITE_SLAB))
			.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICKS, 4)
			.define('#', Blocks.POLISHED_DIORITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_polished_diorite", has(Blocks.POLISHED_DIORITE))
			.save(recipeOutput);
		slabBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_SLAB, Ingredient.of(RegisterBlocks.POLISHED_DIORITE_BRICKS))
			.unlockedBy("has_diorite_bricks", has(RegisterBlocks.POLISHED_DIORITE_BRICKS))
			.save(recipeOutput);
		stairBuilder(RegisterBlocks.POLISHED_DIORITE_BRICK_STAIRS, Ingredient.of(RegisterBlocks.POLISHED_DIORITE_BRICKS))
			.unlockedBy("has_diorite_bricks", has(RegisterBlocks.POLISHED_DIORITE_BRICKS))
			.save(recipeOutput);
		wall(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_DIORITE_BRICK_WALL, RegisterBlocks.POLISHED_DIORITE_BRICKS);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_DIORITE, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_DIORITE, Blocks.DIORITE);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICKS, Blocks.DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICKS, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_SLAB, RegisterBlocks.POLISHED_DIORITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_SLAB, Blocks.POLISHED_DIORITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_SLAB, Blocks.DIORITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_STAIRS, RegisterBlocks.POLISHED_DIORITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_STAIRS, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_STAIRS, Blocks.DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_WALL, RegisterBlocks.POLISHED_DIORITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_WALL, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DIORITE_BRICK_WALL, Blocks.DIORITE);

		// MOSSY DIORITE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICKS)
			.requires(RegisterBlocks.POLISHED_DIORITE_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_polished_diorite_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICKS)
			.requires(RegisterBlocks.POLISHED_DIORITE_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_polished_diorite_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICK_SLAB, RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICK_STAIRS, RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICK_WALL, RegisterBlocks.MOSSY_POLISHED_DIORITE_BRICKS);

		// ANDESITE

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegisterBlocks.POLISHED_ANDESITE_BRICKS), RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CRACKED_POLISHED_ANDESITE_BRICKS.asItem(), 0.1F, 200)
			.unlockedBy("has_polished_andesite_bricks", has(RegisterBlocks.POLISHED_ANDESITE_BRICKS))
			.save(recipeOutput);

		chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_ANDESITE, Ingredient.of(Blocks.POLISHED_ANDESITE_SLAB))
			.unlockedBy("has_polished_andesite", has(Blocks.POLISHED_ANDESITE))
			.unlockedBy("has_chiseled_polished_andesite", has(RegisterBlocks.CHISELED_POLISHED_ANDESITE))
			.unlockedBy("has_polished_andesite_slab", has(Blocks.POLISHED_ANDESITE_SLAB))
			.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICKS, 4)
			.define('#', Blocks.POLISHED_ANDESITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_polished_andesite", has(Blocks.POLISHED_ANDESITE))
			.save(recipeOutput);
		slabBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_SLAB, Ingredient.of(RegisterBlocks.POLISHED_ANDESITE_BRICKS))
			.unlockedBy("has_andesite_bricks", has(RegisterBlocks.POLISHED_ANDESITE_BRICKS))
			.save(recipeOutput);
		stairBuilder(RegisterBlocks.POLISHED_ANDESITE_BRICK_STAIRS, Ingredient.of(RegisterBlocks.POLISHED_ANDESITE_BRICKS))
			.unlockedBy("has_andesite_bricks", has(RegisterBlocks.POLISHED_ANDESITE_BRICKS))
			.save(recipeOutput);
		wall(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_ANDESITE_BRICK_WALL, RegisterBlocks.POLISHED_ANDESITE_BRICKS);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_POLISHED_ANDESITE, Blocks.ANDESITE);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICKS, Blocks.ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_SLAB, RegisterBlocks.POLISHED_ANDESITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_SLAB, Blocks.POLISHED_ANDESITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_SLAB, Blocks.ANDESITE, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_STAIRS, RegisterBlocks.POLISHED_ANDESITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_STAIRS, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_STAIRS, Blocks.ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_WALL, RegisterBlocks.POLISHED_ANDESITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_WALL, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_ANDESITE_BRICK_WALL, Blocks.ANDESITE);

		// MOSSY ANDESITE

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICKS)
			.requires(RegisterBlocks.POLISHED_ANDESITE_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_polished_andesite_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICKS)
			.requires(RegisterBlocks.POLISHED_ANDESITE_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_polished_andesite_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(recipeOutput, getConversionRecipeName(RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICK_SLAB, RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICKS, 2);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICK_STAIRS, RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICK_WALL, RegisterBlocks.MOSSY_POLISHED_ANDESITE_BRICKS);

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

		// DESOLATION SMITHING TEMPLATE

			ShapedRecipeBuilder.shaped(RecipeCategory.INGREDIENTS, RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
			.define('#', Items.DIAMOND)
			.define('C', Blocks.STONE_BRICKS)
			.define('S', RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE)
			.pattern("#S#")
			.pattern("#C#")
			.pattern("###")
			.unlockedBy("has_desolation_armor_trim_smithing_template", has(RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE))
			.save(recipeOutput);

		// UNDEAD SMITHING TEMPLATE

			ShapedRecipeBuilder.shaped(RecipeCategory.INGREDIENTS, RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
			.define('#', Items.DIAMOND)
			.define('C', RegisterBlocks.MOSSY_DEEPSLATE_BRICKS)
			.define('S', RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE)
			.pattern("#S#")
			.pattern("#C#")
			.pattern("###")
			.unlockedBy("has_undead_armor_trim_smithing_template", has(RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE))
			.save(recipeOutput);
	}
}
