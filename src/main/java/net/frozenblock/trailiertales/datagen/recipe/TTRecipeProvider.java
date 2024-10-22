package net.frozenblock.trailiertales.datagen.recipe;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceKey;
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
	@NotNull
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
		return new RecipeProvider(registryLookup, exporter) {
			@Override
			public void buildRecipes() {
				HolderLookup.Provider registries = this.registries;
				RecipeOutput output = this.output;

				generateForEnabledBlockFamilies(TTFeatureFlags.TRAILIER_TALES_FLAG_SET);
				this.shaped(RecipeCategory.REDSTONE, TTBlocks.SURVEYOR)
					.define('E', TTItems.ECTOPLASM)
					.define('A', Items.AMETHYST_SHARD)
					.define('R', Items.REDSTONE)
					.define('#', Blocks.COBBLESTONE)
					.pattern("###")
					.pattern("RAE")
					.pattern("###")
					.unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
					.unlockedBy("has_ectoplasm", has(TTItems.ECTOPLASM))
					.save(output);

				this.shaped(RecipeCategory.MISC, Blocks.SUSPICIOUS_GRAVEL, 4)
					.define('#', Items.GRAVEL)
					.pattern(" # ")
					.pattern("# #")
					.pattern(" # ")
					.unlockedBy(getHasName(Items.GRAVEL), has(Items.GRAVEL))
					.save(output);

				this.shaped(RecipeCategory.MISC, Blocks.SUSPICIOUS_SAND, 4)
					.define('#', Items.SAND)
					.pattern(" # ")
					.pattern("# #")
					.pattern(" # ")
					.unlockedBy(getHasName(Items.SAND), has(Items.SAND))
					.save(output);

				this.shaped(RecipeCategory.MISC, TTBlocks.SUSPICIOUS_RED_SAND, 4)
					.define('#', Items.RED_SAND)
					.pattern(" # ")
					.pattern("# #")
					.pattern(" # ")
					.unlockedBy(getHasName(Items.RED_SAND), has(Items.RED_SAND))
					.save(output);

				this.shaped(RecipeCategory.MISC, TTBlocks.SUSPICIOUS_DIRT, 4)
					.define('#', Items.DIRT)
					.pattern(" # ")
					.pattern("# #")
					.pattern(" # ")
					.unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
					.save(output);

				this.shaped(RecipeCategory.MISC, TTBlocks.SUSPICIOUS_CLAY, 4)
					.define('#', Items.CLAY)
					.pattern(" # ")
					.pattern("# #")
					.pattern(" # ")
					.unlockedBy(getHasName(Items.CLAY), has(Items.CLAY))
					.save(output);

				oneToOneConversionRecipe(Items.CYAN_DYE, TTBlocks.CYAN_ROSE, "cyan_dye");
				oneToOneConversionRecipe(Items.PURPLE_DYE, TTBlocks.MANEDROP, "purple_dye", 2);
				oneToOneConversionRecipe(Items.PURPLE_DYE, TTItems.DAWNTRAIL_SEEDS, "purple_dye");
				// GRANITE

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_GRANITE_WALL, Blocks.GRANITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_GRANITE_WALL, Blocks.POLISHED_GRANITE);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICKS, 4)
					.define('#', Blocks.POLISHED_GRANITE)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_polished_granite", has(Blocks.POLISHED_GRANITE))
					.save(output);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_GRANITE_BRICKS, Blocks.GRANITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_GRANITE_BRICKS, Blocks.POLISHED_GRANITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_GRANITE_BRICKS, TTBlocks.GRANITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICKS, Blocks.GRANITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICKS, Blocks.POLISHED_GRANITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_SLAB, TTBlocks.GRANITE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_SLAB, Blocks.POLISHED_GRANITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_SLAB, Blocks.GRANITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_STAIRS, TTBlocks.GRANITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_STAIRS, Blocks.POLISHED_GRANITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_STAIRS, Blocks.GRANITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_WALL, TTBlocks.GRANITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_WALL, Blocks.POLISHED_GRANITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.GRANITE_BRICK_WALL, Blocks.GRANITE);

				// MOSSY GRANITE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_GRANITE_BRICKS)
					.requires(TTBlocks.GRANITE_BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_granite_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_GRANITE_BRICKS, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_GRANITE_BRICKS)
					.requires(TTBlocks.GRANITE_BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_granite_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_GRANITE_BRICKS, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_GRANITE_BRICK_SLAB, TTBlocks.MOSSY_GRANITE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_GRANITE_BRICK_STAIRS, TTBlocks.MOSSY_GRANITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_GRANITE_BRICK_WALL, TTBlocks.MOSSY_GRANITE_BRICKS);

				// DIORITE

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_DIORITE_WALL, Blocks.DIORITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_DIORITE_WALL, Blocks.POLISHED_DIORITE);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICKS, 4)
					.define('#', Blocks.POLISHED_DIORITE)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_polished_diorite", has(Blocks.POLISHED_DIORITE))
					.save(output);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_DIORITE_BRICKS, Blocks.DIORITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_DIORITE_BRICKS, Blocks.POLISHED_DIORITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_DIORITE_BRICKS, TTBlocks.DIORITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICKS, Blocks.DIORITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICKS, Blocks.POLISHED_DIORITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_SLAB, TTBlocks.DIORITE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_SLAB, Blocks.POLISHED_DIORITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_SLAB, Blocks.DIORITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_STAIRS, TTBlocks.DIORITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_STAIRS, Blocks.POLISHED_DIORITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_STAIRS, Blocks.DIORITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_WALL, TTBlocks.DIORITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_WALL, Blocks.POLISHED_DIORITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DIORITE_BRICK_WALL, Blocks.DIORITE);

				// MOSSY DIORITE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DIORITE_BRICKS)
					.requires(TTBlocks.DIORITE_BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_diorite_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DIORITE_BRICKS, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DIORITE_BRICKS)
					.requires(TTBlocks.DIORITE_BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_diorite_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DIORITE_BRICKS, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DIORITE_BRICK_SLAB, TTBlocks.MOSSY_DIORITE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DIORITE_BRICK_STAIRS, TTBlocks.MOSSY_DIORITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_DIORITE_BRICK_WALL, TTBlocks.MOSSY_DIORITE_BRICKS);

				// ANDESITE

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_ANDESITE_WALL, Blocks.ANDESITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_ANDESITE_WALL, Blocks.POLISHED_ANDESITE);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICKS, 4)
					.define('#', Blocks.POLISHED_ANDESITE)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_polished_andesite", has(Blocks.POLISHED_ANDESITE))
					.save(output);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_ANDESITE_BRICKS, Blocks.ANDESITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_ANDESITE_BRICKS, TTBlocks.ANDESITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICKS, Blocks.ANDESITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_SLAB, TTBlocks.ANDESITE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_SLAB, Blocks.POLISHED_ANDESITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_SLAB, Blocks.ANDESITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_STAIRS, TTBlocks.ANDESITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_STAIRS, Blocks.POLISHED_ANDESITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_STAIRS, Blocks.ANDESITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_WALL, TTBlocks.ANDESITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_WALL, Blocks.POLISHED_ANDESITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.ANDESITE_BRICK_WALL, Blocks.ANDESITE);

				// MOSSY ANDESITE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_ANDESITE_BRICKS)
					.requires(TTBlocks.ANDESITE_BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_andesite_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_ANDESITE_BRICKS, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_ANDESITE_BRICKS)
					.requires(TTBlocks.ANDESITE_BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_andesite_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_ANDESITE_BRICKS, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_ANDESITE_BRICK_SLAB, TTBlocks.MOSSY_ANDESITE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS, TTBlocks.MOSSY_ANDESITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_ANDESITE_BRICK_WALL, TTBlocks.MOSSY_ANDESITE_BRICKS);

				// CALCITE

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_SLAB, Blocks.CALCITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_STAIRS, Blocks.CALCITE);
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.CALCITE_WALL, Blocks.CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE, Blocks.CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_SLAB, Blocks.CALCITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_STAIRS, Blocks.CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_SLAB, TTBlocks.POLISHED_CALCITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.POLISHED_CALCITE_STAIRS, TTBlocks.POLISHED_CALCITE);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICKS, 4)
					.define('#', Blocks.CALCITE)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_calcite", has(Blocks.CALCITE))
					.save(output);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_CALCITE_BRICKS, Blocks.CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_CALCITE_BRICKS, TTBlocks.POLISHED_CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_CALCITE_BRICKS, TTBlocks.CALCITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICKS, Blocks.CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICKS, TTBlocks.POLISHED_CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_SLAB, TTBlocks.CALCITE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_SLAB, TTBlocks.POLISHED_CALCITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_SLAB, Blocks.CALCITE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_STAIRS, TTBlocks.CALCITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_STAIRS, TTBlocks.POLISHED_CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_STAIRS, Blocks.CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_WALL, TTBlocks.CALCITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_WALL, TTBlocks.POLISHED_CALCITE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CALCITE_BRICK_WALL, Blocks.CALCITE);

				// MOSSY CALCITE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_CALCITE_BRICKS)
					.requires(TTBlocks.CALCITE_BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_calcite_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_CALCITE_BRICKS, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_CALCITE_BRICKS)
					.requires(TTBlocks.CALCITE_BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_calcite_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_CALCITE_BRICKS, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_CALCITE_BRICK_SLAB, TTBlocks.MOSSY_CALCITE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_CALCITE_BRICK_STAIRS, TTBlocks.MOSSY_CALCITE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_CALCITE_BRICK_WALL, TTBlocks.MOSSY_CALCITE_BRICKS);

				// TUFF BRICKS

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.TUFF_BRICKS), RecipeCategory.BUILDING_BLOCKS, TTBlocks.CRACKED_TUFF_BRICKS.asItem(), 0.1F, 200)
					.unlockedBy("has_tuff_bricks", has(Blocks.TUFF_BRICKS))
					.save(output);

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICKS)
					.requires(Blocks.TUFF_BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_tuff_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_TUFF_BRICKS, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICKS)
					.requires(Blocks.TUFF_BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_tuff_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_TUFF_BRICKS, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICK_SLAB, TTBlocks.MOSSY_TUFF_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICK_STAIRS, TTBlocks.MOSSY_TUFF_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_TUFF_BRICK_WALL, TTBlocks.MOSSY_TUFF_BRICKS);

				// BRICKS

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.BRICKS), RecipeCategory.BUILDING_BLOCKS, TTBlocks.CRACKED_BRICKS.asItem(), 0.1F, 200)
					.unlockedBy("has_bricks", has(Blocks.BRICKS))
					.save(output);

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICKS)
					.requires(Blocks.BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_BRICKS, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICKS)
					.requires(Blocks.BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_BRICKS, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICK_SLAB, TTBlocks.MOSSY_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICK_STAIRS, TTBlocks.MOSSY_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_BRICK_WALL, TTBlocks.MOSSY_BRICKS);

				// MOSSY COBBLED DEEPSLATE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE)
					.requires(Blocks.COBBLED_DEEPSLATE)
					.requires(Blocks.VINE)
					.group("mossy_cobbled_deepslate")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_COBBLED_DEEPSLATE, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE)
					.requires(Blocks.COBBLED_DEEPSLATE)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_cobbled_deepslate")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_COBBLED_DEEPSLATE, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB, TTBlocks.MOSSY_COBBLED_DEEPSLATE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL, TTBlocks.MOSSY_COBBLED_DEEPSLATE);

				// MOSSY DEEPSLATE BRICKS

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICKS)
					.requires(Blocks.DEEPSLATE_BRICKS)
					.requires(Blocks.VINE)
					.group("mossy_deepslate_bricks")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DEEPSLATE_BRICKS, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICKS)
					.requires(Blocks.DEEPSLATE_BRICKS)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_deepslate_bricks")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DEEPSLATE_BRICKS, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB, TTBlocks.MOSSY_DEEPSLATE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS, TTBlocks.MOSSY_DEEPSLATE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL, TTBlocks.MOSSY_DEEPSLATE_BRICKS);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICKS, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB, TTBlocks.MOSSY_COBBLED_DEEPSLATE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL, TTBlocks.MOSSY_COBBLED_DEEPSLATE);

				// MOSSY DEEPSLATE TILE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILES)
					.requires(Blocks.DEEPSLATE_TILES)
					.requires(Blocks.VINE)
					.group("mossy_deepslate_tiles")
					.unlockedBy("has_vine", has(Blocks.VINE))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DEEPSLATE_TILES, Blocks.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILES)
					.requires(Blocks.DEEPSLATE_TILES)
					.requires(Blocks.MOSS_BLOCK)
					.group("mossy_deepslate_tiles")
					.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
					.save(output, getConversionRecipeName(TTBlocks.MOSSY_DEEPSLATE_TILES, Blocks.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILES, TTBlocks.MOSSY_DEEPSLATE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB, TTBlocks.MOSSY_DEEPSLATE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS, TTBlocks.MOSSY_DEEPSLATE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL, TTBlocks.MOSSY_DEEPSLATE_BRICKS);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILES, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB, TTBlocks.MOSSY_COBBLED_DEEPSLATE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS, TTBlocks.MOSSY_COBBLED_DEEPSLATE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL, TTBlocks.MOSSY_COBBLED_DEEPSLATE);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB, TTBlocks.MOSSY_DEEPSLATE_TILES, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS, TTBlocks.MOSSY_DEEPSLATE_TILES);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL, TTBlocks.MOSSY_DEEPSLATE_TILES);

				// SANDSTONE

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_SANDSTONE_STAIRS, Blocks.SANDSTONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_SANDSTONE_STAIRS, Blocks.CUT_SANDSTONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_SANDSTONE_WALL, Blocks.SANDSTONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_SANDSTONE_WALL, Blocks.CUT_SANDSTONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.SMOOTH_SANDSTONE_WALL, Blocks.SMOOTH_SANDSTONE);

				// RED SANDSTONE

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_RED_SANDSTONE_STAIRS, Blocks.CUT_RED_SANDSTONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_RED_SANDSTONE_WALL, Blocks.RED_SANDSTONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CUT_RED_SANDSTONE_WALL, Blocks.CUT_RED_SANDSTONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.SMOOTH_RED_SANDSTONE_WALL, Blocks.SMOOTH_RED_SANDSTONE);

				// PRISMARINE

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.PRISMARINE_BRICK_WALL, Blocks.PRISMARINE_BRICKS);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.DARK_PRISMARINE_WALL, Blocks.DARK_PRISMARINE);

				// END STONE BRICKS

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.END_STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, TTBlocks.CRACKED_END_STONE_BRICKS.asItem(), 0.1F, 200)
					.unlockedBy("has_end_stone_bricks", has(Blocks.END_STONE_BRICKS))
					.save(output);

				chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_END_STONE_BRICKS, Ingredient.of(Blocks.END_STONE_BRICK_SLAB))
					.unlockedBy("has_end_stone_bricks", has(Blocks.END_STONE_BRICKS))
					.unlockedBy("has_chiseled_end_stone_bricks", has(TTBlocks.CHISELED_END_STONE_BRICKS))
					.unlockedBy("has_end_stone_brick_slab", has(Blocks.END_STONE_BRICK_SLAB))
					.save(output);

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICKS)
					.requires(Blocks.END_STONE_BRICKS)
					.requires(Items.CHORUS_FRUIT)
					.group("choral_end_stone_bricks")
					.unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
					.save(output, getConversionRecipeName(TTBlocks.CHORAL_END_STONE_BRICKS, Items.CHORUS_FRUIT));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_END_STONE_BRICKS, Blocks.END_STONE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_END_STONE_BRICKS, Blocks.END_STONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_SLAB, TTBlocks.CHORAL_END_STONE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_SLAB, TTBlocks.CHORAL_END_STONE_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_STAIRS, TTBlocks.CHORAL_END_STONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_STAIRS, TTBlocks.CHORAL_END_STONE_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_WALL, TTBlocks.CHORAL_END_STONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_BRICK_WALL, TTBlocks.CHORAL_END_STONE_BRICKS);

				// END STONE

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.END_STONE_STAIRS, Blocks.END_STONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.END_STONE_SLAB, Blocks.END_STONE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.END_STONE_WALL, Blocks.END_STONE);

				// CHORAL END STONE

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE)
					.requires(Blocks.END_STONE)
					.requires(Items.CHORUS_FRUIT)
					.group("choral_end_stone")
					.unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
					.save(output, getConversionRecipeName(TTBlocks.CHORAL_END_STONE, Items.CHORUS_FRUIT));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_STAIRS, TTBlocks.CHORAL_END_STONE);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_SLAB, TTBlocks.CHORAL_END_STONE, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHORAL_END_STONE_WALL, TTBlocks.CHORAL_END_STONE);

				// PURPUR

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.PURPUR_WALL, Blocks.PURPUR_BLOCK);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, TTBlocks.CHISELED_PURPUR_BLOCK, Blocks.PURPUR_BLOCK);

				// ARMOR TRIMS

				smithingTrims().forEach(trimTemplate -> trimSmithing(trimTemplate.template(), trimTemplate.id()));

				// UNDEAD SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
					.define('#', Items.DIAMOND)
					.define('C', TTBlocks.MOSSY_DEEPSLATE_BRICKS)
					.define('S', TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE)
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_undead_armor_trim_smithing_template", has(TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE))
					.save(output);

				// MATRIX SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.DEEPSLATE_BRICKS)
					.define('S', TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE)
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_matrix_armor_trim_smithing_template", has(TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE))
					.save(output);

				// GEODE SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.SMOOTH_BASALT)
					.define('S', TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE)
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_geode_armor_trim_smithing_template", has(TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE))
					.save(output);

				// OVERGROWTH SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.MOSSY_COBBLESTONE)
					.define('S', TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE)
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_overgrowth_armor_trim_smithing_template", has(TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE))
					.save(output);

				// MARTYR SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.RED_SANDSTONE)
					.define('S', TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE)
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_martyr_armor_trim_smithing_template", has(TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE))
					.save(output);

				// ZEPHYR SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.SMOOTH_SANDSTONE)
					.define('S', TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE)
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_zephyr_armor_trim_smithing_template", has(TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE))
					.save(output);

				// COT SMITHING TEMPLATE

				this.shaped(RecipeCategory.MISC, TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE, 2)
					.define('#', Items.DIAMOND)
					.define('C', Blocks.MUD_BRICKS)
					.define('S', TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE)
					.pattern("#S#")
					.pattern("#C#")
					.pattern("###")
					.unlockedBy("has_cot_armor_trim_smithing_template", has(TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE))
					.save(output);
			}
		};
	}

	public static void stonecutterResultFromBase(RecipeProvider provider, RecipeOutput output, RecipeCategory category, ItemLike ingredient, ItemLike result) {
		stonecutterResultFromBase(provider, output, category, ingredient, result, 1);
	}

	@Contract("_, _ -> new")
	public static @NotNull String getConversionRecipeName(ItemLike from, ItemLike to) {
		return TTConstants.string(RecipeProvider.getItemName(from) + "_from_" + RecipeProvider.getItemName(to));
	}

	public static void stonecutterResultFromBase(RecipeProvider provider, RecipeOutput output, RecipeCategory category, ItemLike ingredient, ItemLike result, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(result), category, ingredient, count)
			.unlockedBy(provider.getHasName(result), provider.has(result))
			.save(output, getConversionRecipeName(ingredient, result) + "_stonecutting");
	}

	private static Stream<VanillaRecipeProvider.TrimTemplate> smithingTrims() {
		return Stream.of(
				TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE,
				TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE,
				TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE,
				TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE,
				TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE,
				TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE,
				TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE
			)
			.map(item -> new VanillaRecipeProvider.TrimTemplate(item, ResourceKey.create(Registries.RECIPE, TTConstants.id(RecipeProvider.getItemName(item) + "_smithing_trim"))));
	}

	@Override
	@NotNull
	public String getName() {
		return "Trailier Tales Recipes";
	}
}
