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
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class TTRecipeProvider extends FabricRecipeProvider {
	public TTRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void buildRecipes(RecipeOutput exporter) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SUSPICIOUS_GRAVEL, 4)
			.define('#', Items.GRAVEL)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.GRAVEL), has(Items.GRAVEL))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SUSPICIOUS_SAND, 4)
			.define('#', Items.SAND)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.SAND), has(Items.SAND))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.SUSPICIOUS_RED_SAND, 4)
			.define('#', Items.RED_SAND)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.RED_SAND), has(Items.RED_SAND))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.SUSPICIOUS_DIRT, 4)
			.define('#', Items.DIRT)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.DIRT), has(Items.DIRT))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.SUSPICIOUS_CLAY, 4)
			.define('#', Items.CLAY)
			.pattern(" # ")
			.pattern("# #")
			.pattern(" # ")
			.unlockedBy(getHasName(Items.CLAY), has(Items.CLAY))
			.save(exporter);

		oneToOneConversionRecipe(exporter, Items.CYAN_DYE, RegisterBlocks.CYAN_ROSE, "cyan_dye");
	}

}
