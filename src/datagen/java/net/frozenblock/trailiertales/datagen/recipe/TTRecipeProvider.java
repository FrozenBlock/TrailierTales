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
import net.frozenblock.trailiertales.tag.TrailierItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
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

		// GRANITE BRICKS

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS, 4)
			.define('#', Blocks.POLISHED_GRANITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_polished_granite", has(Blocks.POLISHED_GRANITE))
			.save(recipeOutput);
		slabBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICK_SLAB, Ingredient.of(RegisterBlocks.GRANITE_BRICKS))
			.unlockedBy("has_granite_bricks", has(TrailierItemTags.GRANITE_BRICKS))
			.save(recipeOutput);
		stairBuilder(RegisterBlocks.GRANITE_BRICK_STAIRS, Ingredient.of(RegisterBlocks.GRANITE_BRICKS)).unlockedBy("has_granite_bricks", has(TrailierItemTags.GRANITE_BRICKS)).save(recipeOutput);
		wall(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.GRANITE_BRICK_WALL, RegisterBlocks.GRANITE_BRICKS);
		chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, Ingredient.of(RegisterBlocks.GRANITE_BRICK_SLAB))
			.unlockedBy("has_tag", has(TrailierItemTags.GRANITE_BRICKS))
			.save(recipeOutput);

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
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, RegisterBlocks.GRANITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, Blocks.POLISHED_GRANITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, Blocks.GRANITE);

		// DIORITE BRICKS

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS, 4)
			.define('#', Blocks.POLISHED_DIORITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_polished_diorite", has(Blocks.POLISHED_DIORITE))
			.save(recipeOutput);
		slabBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICK_SLAB, Ingredient.of(RegisterBlocks.DIORITE_BRICKS))
			.unlockedBy("has_diorite_bricks", has(TrailierItemTags.DIORITE_BRICKS))
			.save(recipeOutput);
		stairBuilder(RegisterBlocks.DIORITE_BRICK_STAIRS, Ingredient.of(RegisterBlocks.DIORITE_BRICKS)).unlockedBy("has_diorite_bricks", has(TrailierItemTags.DIORITE_BRICKS)).save(recipeOutput);
		wall(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.DIORITE_BRICK_WALL, RegisterBlocks.DIORITE_BRICKS);
		chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, Ingredient.of(RegisterBlocks.DIORITE_BRICK_SLAB))
			.unlockedBy("has_tag", has(TrailierItemTags.DIORITE_BRICKS))
			.save(recipeOutput);

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
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, RegisterBlocks.DIORITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, Blocks.POLISHED_DIORITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, Blocks.DIORITE);

		// ANDESITE BRICKS

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS, 4)
			.define('#', Blocks.POLISHED_ANDESITE)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_polished_andesite", has(Blocks.POLISHED_ANDESITE))
			.save(recipeOutput);
		slabBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICK_SLAB, Ingredient.of(RegisterBlocks.ANDESITE_BRICKS))
			.unlockedBy("has_andesite_bricks", has(TrailierItemTags.ANDESITE_BRICKS))
			.save(recipeOutput);
		stairBuilder(RegisterBlocks.ANDESITE_BRICK_STAIRS, Ingredient.of(RegisterBlocks.ANDESITE_BRICKS)).unlockedBy("has_andesite_bricks", has(TrailierItemTags.ANDESITE_BRICKS)).save(recipeOutput);
		wall(recipeOutput, RecipeCategory.DECORATIONS, RegisterBlocks.ANDESITE_BRICK_WALL, RegisterBlocks.ANDESITE_BRICKS);
		chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, Ingredient.of(RegisterBlocks.ANDESITE_BRICK_SLAB))
			.unlockedBy("has_tag", has(TrailierItemTags.ANDESITE_BRICKS))
			.save(recipeOutput);

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
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, RegisterBlocks.ANDESITE_BRICKS);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE);
		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, Blocks.ANDESITE);

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
		//RecipeProvider.generateForEnabledBlockFamilies();

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
	}

}
