/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.data.recipe;

import com.mojang.datafixers.util.Pair;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.lib.item.api.recipe.RecipeExportNamespaceFix;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.frozenblock.trailiertales.recipe.SherdCopyRecipe;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.registry.TTTrimPatterns;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;

public class TTRecipeProvider extends FabricRecipeProvider {

	public TTRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {
			@Override
			public void buildRecipes() {
				RecipeExportNamespaceFix.setCurrentGeneratingModId(TTConstants.MOD_ID);

				SpecialRecipeBuilder.special(
					() -> new SherdCopyRecipe(this.tag(ItemTags.DECORATED_POT_SHERDS), Ingredient.of(Items.BRICKS))
				).unlockedBy("has_brick", this.has(ItemTags.DECORATED_POT_SHERDS))
				.save(this.output, "sherd_copy");

				this.generateForEnabledBlockFamilies(TTFeatureFlags.TRAILIER_TALES_FLAG_SET);

				this.shaped(RecipeCategory.REDSTONE, TTBlocks.SURVEYOR.get())
					.define('E', TTItems.ECTOPLASM.get())
					.define('Q', Items.QUARTZ)
					.define('R', Items.REDSTONE)
					.define('#', Blocks.COBBLESTONE)
					.pattern("###")
					.pattern("RQE")
					.pattern("###")
					.unlockedBy("has_quartz", has(Items.QUARTZ))
					.unlockedBy("has_ectoplasm", has(TTItems.ECTOPLASM.get()))
					.save(output);

				this.shaped(RecipeCategory.MISC, TTItems.ECTOPLASM_BLOCK.get())
					.define('#', TTItems.ECTOPLASM.get())
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_ectoplasm", has(TTItems.ECTOPLASM.get()))
					.save(output);

				this.shapeless(RecipeCategory.MISC, TTItems.ECTOPLASM.get(), 4)
					.requires(TTItems.ECTOPLASM_BLOCK.get())
					.unlockedBy("has_ectoplasm_block", has(TTItems.ECTOPLASM_BLOCK.get()))
					.save(output);

				this.oneToOneConversionRecipe(Items.DYE.cyan(), TTBlocks.CYAN_ROSE.get(), "cyan_dye");
				this.oneToOneConversionRecipe(Items.DYE.purple(), TTBlocks.MANEDROP.get(), "purple_dye", 2);
				this.oneToOneConversionRecipe(Items.DYE.purple(), TTItems.DAWNTRAIL_SEEDS.get(), "purple_dye");
				this.oneToOneConversionRecipe(Items.DYE.magenta(), TTBlocks.GUZMANIA.get(), "magenta_dye", 2);

				// GRANITE

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_GRANITE_WALL.get(), Blocks.GRANITE);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICKS.get(), 4)
					.define('#', Blocks.POLISHED_GRANITE)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_polished_granite", has(Blocks.POLISHED_GRANITE))
					.save(output);

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_GRANITE_BRICKS.get(), Blocks.GRANITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_GRANITE_BRICKS.get(), Blocks.POLISHED_GRANITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_GRANITE_BRICKS.get(), TTBlocks.GRANITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICKS.get(), Blocks.GRANITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICKS.get(), Blocks.POLISHED_GRANITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_SLAB.get(), TTBlocks.GRANITE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_SLAB.get(), Blocks.POLISHED_GRANITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_SLAB.get(), Blocks.GRANITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_STAIRS.get(), TTBlocks.GRANITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_STAIRS.get(), Blocks.POLISHED_GRANITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_STAIRS.get(), Blocks.GRANITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_WALL.get(), TTBlocks.GRANITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_WALL.get(), Blocks.POLISHED_GRANITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_WALL.get(), Blocks.GRANITE);

				// MOSSY GRANITE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_GRANITE_BRICKS.get())
					.requires(TTBlocks.GRANITE_BRICKS.get())
					.requires(Blocks.VINE)
					.group("mossy_granite_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_GRANITE_BRICKS.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_GRANITE_BRICKS.get())
					.requires(TTBlocks.GRANITE_BRICKS.get())
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_granite_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_GRANITE_BRICKS.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_GRANITE_BRICK_SLAB.get(), TTBlocks.MOSSY_GRANITE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_GRANITE_BRICK_STAIRS.get(), TTBlocks.MOSSY_GRANITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_GRANITE_BRICK_WALL.get(), TTBlocks.MOSSY_GRANITE_BRICKS.get());

				// DIORITE

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_DIORITE_WALL.get(), Blocks.DIORITE);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICKS.get(), 4)
					.define('#', Blocks.POLISHED_DIORITE)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_polished_diorite", has(Blocks.POLISHED_DIORITE))
					.save(output);

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_DIORITE_BRICKS.get(), Blocks.DIORITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_DIORITE_BRICKS.get(), Blocks.POLISHED_DIORITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_DIORITE_BRICKS.get(), TTBlocks.DIORITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICKS.get(), Blocks.DIORITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICKS.get(), Blocks.POLISHED_DIORITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_SLAB.get(), TTBlocks.DIORITE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_SLAB.get(), Blocks.POLISHED_DIORITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_SLAB.get(), Blocks.DIORITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_STAIRS.get(), TTBlocks.DIORITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_STAIRS.get(), Blocks.POLISHED_DIORITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_STAIRS.get(), Blocks.DIORITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_WALL.get(), TTBlocks.DIORITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_WALL.get(), Blocks.POLISHED_DIORITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_WALL.get(), Blocks.DIORITE);

				// MOSSY DIORITE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DIORITE_BRICKS.get())
					.requires(TTBlocks.DIORITE_BRICKS.get())
					.requires(Blocks.VINE)
					.group("mossy_diorite_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DIORITE_BRICKS.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DIORITE_BRICKS.get())
					.requires(TTBlocks.DIORITE_BRICKS.get())
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_diorite_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DIORITE_BRICKS.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DIORITE_BRICK_SLAB.get(), TTBlocks.MOSSY_DIORITE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DIORITE_BRICK_STAIRS.get(), TTBlocks.MOSSY_DIORITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_DIORITE_BRICK_WALL.get(), TTBlocks.MOSSY_DIORITE_BRICKS.get());

				// ANDESITE

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_ANDESITE_WALL.get(), Blocks.ANDESITE);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICKS.get(), 4)
					.define('#', Blocks.POLISHED_ANDESITE)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_polished_andesite", has(Blocks.POLISHED_ANDESITE))
					.save(output);

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_ANDESITE_BRICKS.get(), Blocks.ANDESITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_ANDESITE_BRICKS.get(), Blocks.POLISHED_ANDESITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_ANDESITE_BRICKS.get(), TTBlocks.ANDESITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICKS.get(), Blocks.ANDESITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICKS.get(), Blocks.POLISHED_ANDESITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_SLAB.get(), TTBlocks.ANDESITE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_SLAB.get(), Blocks.POLISHED_ANDESITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_SLAB.get(), Blocks.ANDESITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_STAIRS.get(), TTBlocks.ANDESITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_STAIRS.get(), Blocks.POLISHED_ANDESITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_STAIRS.get(), Blocks.ANDESITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_WALL.get(), TTBlocks.ANDESITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_WALL.get(), Blocks.POLISHED_ANDESITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_WALL.get(), Blocks.ANDESITE);

				// MOSSY ANDESITE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_ANDESITE_BRICKS.get())
					.requires(TTBlocks.ANDESITE_BRICKS.get())
					.requires(Blocks.VINE)
					.group("mossy_andesite_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_ANDESITE_BRICKS.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_ANDESITE_BRICKS.get())
					.requires(TTBlocks.ANDESITE_BRICKS.get())
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_andesite_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_ANDESITE_BRICKS.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_ANDESITE_BRICK_SLAB.get(), TTBlocks.MOSSY_ANDESITE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS.get(), TTBlocks.MOSSY_ANDESITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_ANDESITE_BRICK_WALL.get(), TTBlocks.MOSSY_ANDESITE_BRICKS.get());

				// CALCITE

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_SLAB.get(), Blocks.CALCITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_STAIRS.get(), Blocks.CALCITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_WALL.get(), Blocks.CALCITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE.get(), Blocks.CALCITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_SLAB.get(), Blocks.CALCITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_STAIRS.get(), Blocks.CALCITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_WALL.get(), Blocks.CALCITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_SLAB.get(), TTBlocks.POLISHED_CALCITE.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_STAIRS.get(), TTBlocks.POLISHED_CALCITE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_WALL.get(), TTBlocks.POLISHED_CALCITE.get());

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICKS.get(), 4)
					.define('#', TTBlocks.POLISHED_CALCITE.get())
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_polished_calcite", has(TTBlocks.POLISHED_CALCITE.get()))
					.save(output);

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_CALCITE_BRICKS.get(), Blocks.CALCITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_CALCITE_BRICKS.get(), TTBlocks.POLISHED_CALCITE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_CALCITE_BRICKS.get(), TTBlocks.CALCITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICKS.get(), Blocks.CALCITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICKS.get(), TTBlocks.POLISHED_CALCITE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_SLAB.get(), TTBlocks.CALCITE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_SLAB.get(), TTBlocks.POLISHED_CALCITE.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_SLAB.get(), Blocks.CALCITE, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_STAIRS.get(), TTBlocks.CALCITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_STAIRS.get(), TTBlocks.POLISHED_CALCITE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_STAIRS.get(), Blocks.CALCITE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_WALL.get(), TTBlocks.CALCITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_WALL.get(), TTBlocks.POLISHED_CALCITE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_WALL.get(), Blocks.CALCITE);

				// MOSSY CALCITE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_CALCITE_BRICKS.get())
					.requires(TTBlocks.CALCITE_BRICKS.get())
					.requires(Blocks.VINE)
					.group("mossy_calcite_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_CALCITE_BRICKS.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_CALCITE_BRICKS.get())
					.requires(TTBlocks.CALCITE_BRICKS.get())
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_calcite_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_CALCITE_BRICKS.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_CALCITE_BRICK_SLAB.get(), TTBlocks.MOSSY_CALCITE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_CALCITE_BRICK_STAIRS.get(), TTBlocks.MOSSY_CALCITE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_CALCITE_BRICK_WALL.get(), TTBlocks.MOSSY_CALCITE_BRICKS.get());

				// TUFF BRICKS

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.TUFF_BRICKS), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, TTBlocks.CRACKED_TUFF_BRICKS.get().asItem(), 0.1F, 200)
					.unlockedBy("has_tuff_bricks", has(Blocks.TUFF_BRICKS))
					.save(output);

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICKS.get())
					.requires(Blocks.TUFF_BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_tuff_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_TUFF_BRICKS.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICKS.get())
					.requires(Blocks.TUFF_BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_tuff_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_TUFF_BRICKS.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICK_SLAB.get(), TTBlocks.MOSSY_TUFF_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICK_STAIRS.get(), TTBlocks.MOSSY_TUFF_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICK_WALL.get(), TTBlocks.MOSSY_TUFF_BRICKS.get());

				// BRICKS

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.BRICKS), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, TTBlocks.CRACKED_BRICKS.get().asItem(), 0.1F, 200)
					.unlockedBy("has_bricks", has(Blocks.BRICKS))
					.save(output);

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICKS.get())
					.requires(Blocks.BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_BRICKS.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICKS.get())
					.requires(Blocks.BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_BRICKS.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICK_SLAB.get(), TTBlocks.MOSSY_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICK_STAIRS.get(), TTBlocks.MOSSY_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICK_WALL.get(), TTBlocks.MOSSY_BRICKS.get());

				// RESIN

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_RESIN_BLOCK.get(), Blocks.RESIN_BLOCK);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_RESIN_STAIRS.get(), TTBlocks.POLISHED_RESIN_BLOCK.get());
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_RESIN_STAIRS.get(), Blocks.RESIN_BLOCK);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_RESIN_SLAB.get(), TTBlocks.POLISHED_RESIN_BLOCK.get(), 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_RESIN_SLAB.get(), Blocks.RESIN_BLOCK, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_RESIN_WALL.get(), TTBlocks.POLISHED_RESIN_BLOCK.get());
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_RESIN_WALL.get(), Blocks.RESIN_BLOCK);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_RESIN_BLOCK.get(), 4)
					.define('#', Blocks.RESIN_BLOCK)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_resin_block", has(Blocks.RESIN_BLOCK))
					.save(output);

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.RESIN_BRICKS), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, TTBlocks.CRACKED_RESIN_BRICKS.get().asItem(), 0.1F, 200)
					.unlockedBy("has_resin_bricks", has(Blocks.RESIN_BRICKS))
					.save(this.output);

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.PALE_MOSSY_RESIN_BRICKS.get())
					.requires(Blocks.RESIN_BRICKS)
					.requires(Blocks.PALE_HANGING_MOSS)
					.group("pale_mossy_resin_bricks")
					.unlockedBy("has_pale_hanging_moss", has(Blocks.PALE_HANGING_MOSS))
					.save(this.output, getConversionRecipeName(TTBlocks.PALE_MOSSY_RESIN_BRICKS.get(), Blocks.PALE_HANGING_MOSS));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.PALE_MOSSY_RESIN_BRICKS.get())
					.requires(Blocks.RESIN_BRICKS)
					.requires(Blocks.PALE_MOSS_BLOCK)
					.group("pale_mossy_resin_bricks")
					.unlockedBy("has_pale_moss_block", has(Blocks.PALE_MOSS_BLOCK))
					.save(this.output, getConversionRecipeName(TTBlocks.PALE_MOSSY_RESIN_BRICKS.get(), Blocks.PALE_MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.PALE_MOSSY_RESIN_BRICK_SLAB.get(), TTBlocks.PALE_MOSSY_RESIN_BRICKS.get(), 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.PALE_MOSSY_RESIN_BRICK_STAIRS.get(), TTBlocks.PALE_MOSSY_RESIN_BRICKS.get());
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.PALE_MOSSY_RESIN_BRICK_WALL.get(), TTBlocks.PALE_MOSSY_RESIN_BRICKS.get());

				// MOSSY COBBLED DEEPSLATE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE.get())
					.requires(Blocks.COBBLED_DEEPSLATE)
					.requires(Blocks.VINE)
					.group("mossy_cobbled_deepslate")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_COBBLED_DEEPSLATE.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE.get())
					.requires(Blocks.COBBLED_DEEPSLATE)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_cobbled_deepslate")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_COBBLED_DEEPSLATE.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());
				this.stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());

				// MOSSY DEEPSLATE BRICKS

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICKS.get())
					.requires(Blocks.DEEPSLATE_BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_deepslate_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DEEPSLATE_BRICKS.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICKS.get())
					.requires(Blocks.DEEPSLATE_BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_deepslate_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DEEPSLATE_BRICKS.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB.get(), TTBlocks.MOSSY_DEEPSLATE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.get(), TTBlocks.MOSSY_DEEPSLATE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL.get(), TTBlocks.MOSSY_DEEPSLATE_BRICKS.get());

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICKS.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());

				// MOSSY DEEPSLATE TILE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILES.get())
					.requires(Blocks.DEEPSLATE_TILES)
					.requires(Blocks.VINE)
					.group("mossy_deepslate_tiles")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DEEPSLATE_TILES.get(), Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILES.get())
					.requires(Blocks.DEEPSLATE_TILES)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_deepslate_tiles")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DEEPSLATE_TILES.get(), Blocks.MOSS_BLOCK));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILES.get(), TTBlocks.MOSSY_DEEPSLATE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB.get(), TTBlocks.MOSSY_DEEPSLATE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.get(), TTBlocks.MOSSY_DEEPSLATE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL.get(), TTBlocks.MOSSY_DEEPSLATE_BRICKS.get());

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILES.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL.get(), TTBlocks.MOSSY_COBBLED_DEEPSLATE.get());

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB.get(), TTBlocks.MOSSY_DEEPSLATE_TILES.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.get(), TTBlocks.MOSSY_DEEPSLATE_TILES.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL.get(), TTBlocks.MOSSY_DEEPSLATE_TILES.get());

				// SANDSTONE

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_SANDSTONE_STAIRS.get(), Blocks.SANDSTONE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_SANDSTONE_WALL.get(), Blocks.SANDSTONE);

				// RED SANDSTONE

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_RED_SANDSTONE_STAIRS.get(), Blocks.RED_SANDSTONE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_RED_SANDSTONE_WALL.get(), Blocks.RED_SANDSTONE);

				// END STONE BRICKS

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.END_STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, TTBlocks.CRACKED_END_STONE_BRICKS.get().asItem(), 0.1F, 200)
					.unlockedBy("has_end_stone_bricks", has(Blocks.END_STONE_BRICKS))
					.save(output);

				this.chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_END_STONE_BRICKS.get(), Ingredient.of(Blocks.END_STONE_BRICK_SLAB))
					.unlockedBy("has_end_stone_bricks", has(Blocks.END_STONE_BRICKS))
					.unlockedBy("has_chiseled_end_stone_bricks", has(TTBlocks.CHISELED_END_STONE_BRICKS.get()))
					.unlockedBy("has_end_stone_brick_slab", has(Blocks.END_STONE_BRICK_SLAB))
					.save(output);

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICKS.get())
					.requires(Blocks.END_STONE_BRICKS)
					.requires(Items.CHORUS_FRUIT)
					.group("choral_end_stone_bricks")
					.unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
					.save(output, getConversionRecipeName(TTBlocks.CHORAL_END_STONE_BRICKS.get(), Items.CHORUS_FRUIT));

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICKS.get())
					.define('#', TTBlocks.CHORAL_END_STONE.get())
					.pattern("##")
					.pattern("##").unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
					.save(output);

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_END_STONE_BRICKS.get(), Blocks.END_STONE_BRICKS);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_END_STONE_BRICKS.get(), Blocks.END_STONE);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICKS.get(), TTBlocks.CHORAL_END_STONE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_SLAB.get(), TTBlocks.CHORAL_END_STONE.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_SLAB.get(), TTBlocks.CHORAL_END_STONE_BRICKS.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_STAIRS.get(), TTBlocks.CHORAL_END_STONE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_STAIRS.get(), TTBlocks.CHORAL_END_STONE_BRICKS.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_WALL.get(), TTBlocks.CHORAL_END_STONE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_WALL.get(), TTBlocks.CHORAL_END_STONE_BRICKS.get());

				// END STONE

				// CHORAL END STONE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE.get())
					.requires(Blocks.END_STONE)
					.requires(Items.CHORUS_FRUIT)
					.group("choral_end_stone")
					.unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
					.save(output, getConversionRecipeName(TTBlocks.CHORAL_END_STONE.get(), Items.CHORUS_FRUIT));

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_STAIRS.get(), TTBlocks.CHORAL_END_STONE.get());
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_SLAB.get(), TTBlocks.CHORAL_END_STONE.get(), 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_WALL.get(), TTBlocks.CHORAL_END_STONE.get());

				// ARMOR TRIMS

				smithingTrims().forEach(trimTemplate -> this.trimSmithing(trimTemplate.template(), trimTemplate.patternId(), trimTemplate.recipeId()));

				// UNDEAD SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
					.define('#', Items.DIAMOND)
					.define('C', TTBlocks.MOSSY_DEEPSLATE_BRICKS.get())
					.define('S', TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE.get())
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_undead_armor_trim_smithing_template", has(TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
					.save(output);

				// MATRIX SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.DEEPSLATE_BRICKS)
					.define('S', TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE.get())
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_matrix_armor_trim_smithing_template", has(TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
					.save(output);

				// GEODE SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.SMOOTH_BASALT)
					.define('S', TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE.get())
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_geode_armor_trim_smithing_template", has(TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
					.save(output);

				// OVERGROWTH SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.MOSSY_COBBLESTONE)
					.define('S', TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE.get())
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_overgrowth_armor_trim_smithing_template", has(TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
					.save(output);

				// MARTYR SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.RED_SANDSTONE)
					.define('S', TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE.get())
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_martyr_armor_trim_smithing_template", has(TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
					.save(output);

				// ZEPHYR SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.SMOOTH_SANDSTONE)
					.define('S', TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE.get())
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_zephyr_armor_trim_smithing_template", has(TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
					.save(output);

				// COT SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.MUD_BRICKS)
					.define('S', TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE.get())
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_cot_armor_trim_smithing_template", has(TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
					.save(output);

				// EMBRACE SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.BLUE_ICE)
					.define('S', TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE.get())
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_embrace_armor_trim_smithing_template", has(TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
					.save(output);

				RecipeExportNamespaceFix.clearCurrentGeneratingModId();
			}
		};
	}

	private static Stream<VanillaRecipeProvider.TrimTemplate> smithingTrims() {
		return Stream.of(
				Pair.of(TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TTTrimPatterns.UNDEAD),
				Pair.of(TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TTTrimPatterns.MATRIX),
				Pair.of(TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TTTrimPatterns.GEODE),
				Pair.of(TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TTTrimPatterns.OVERGROWTH),
				Pair.of(TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TTTrimPatterns.MARTYR),
				Pair.of(TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TTTrimPatterns.ZEPHYR),
				Pair.of(TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TTTrimPatterns.COT),
				Pair.of(TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TTTrimPatterns.EMBRACE)
			)
			.map(pair -> new VanillaRecipeProvider.TrimTemplate(pair.getFirst(), pair.getSecond(), ResourceKey.create(Registries.RECIPE, TTConstants.id(RecipeProvider.getItemName(pair.getFirst()) + "_smithing_trim"))));
	}

	@Override
	public String getName() {
		return "Trailier Tales Recipes";
	}
}
