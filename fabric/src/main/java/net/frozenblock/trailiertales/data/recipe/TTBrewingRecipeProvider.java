/*
 * Copyright 2026 FrozenBlock
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

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.lib.item.api.recipe.RecipeExportNamespaceFix;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.registry.TTPotions;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.BrewingRecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Recipe;

public final class TTBrewingRecipeProvider extends FabricRecipeProvider {

	public TTBrewingRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {
			@Override
			public void buildRecipes() {
				RecipeExportNamespaceFix.setCurrentGeneratingModId(TTConstants.MOD_ID);

				this.buildStartMix(TTItems.ECTOPLASM.get(), TTPotions.TRANSFIGURING.asHolder());

				RecipeExportNamespaceFix.clearCurrentGeneratingModId();
			}

			void buildStartMix(Item reagent, Holder<Potion> output) {
				this.buildMix(Potions.WATER, reagent, Potions.MUNDANE);
				this.buildMix(Potions.AWKWARD, reagent, output);
			}

			void buildMix(Holder<Potion> input, Item reagent, Holder<Potion> result) {
				BrewingRecipeBuilder.brewingMix(Items.POTION, input, reagent, result).save(this.output);
				BrewingRecipeBuilder.brewingMix(Items.SPLASH_POTION, input, reagent, result).save(this.output);
				BrewingRecipeBuilder.brewingMix(Items.LINGERING_POTION, input, reagent, result).save(this.output);
			}
		};
	}

	@Override
	public String getName() {
		return "Trailier Tales Brewing Recipes";
	}
}
