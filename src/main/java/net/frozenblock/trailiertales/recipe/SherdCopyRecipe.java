/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
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

package net.frozenblock.trailiertales.recipe;

import net.frozenblock.trailiertales.config.TTItemConfig;
import net.frozenblock.trailiertales.registry.TTRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SherdCopyRecipe extends CustomRecipe {

	public SherdCopyRecipe(CraftingBookCategory craftingBookCategory) {
		super(craftingBookCategory);
	}

	@Override
	public boolean matches(@NotNull CraftingInput input, Level world) {
		if (!TTItemConfig.SHERD_DUPLICATION_RECIPE || !this.canCraftInDimensions(input.width(), input.height())) {
			return false;
		}
		if (input.ingredientCount() != 2) {
			return false;
		}
		return input.stackedContents().canCraft(this, null);
	}

	@Override @NotNull
	public ItemStack assemble(@NotNull CraftingInput input, HolderLookup.Provider provider) {
		if (TTItemConfig.SHERD_DUPLICATION_RECIPE) {
			int bricks = 0;
			int sherds = 0;
			ItemStack outputStack = ItemStack.EMPTY;
			for (int i = 0; i < input.size(); i++) {
				ItemStack inputStack = input.getItem(i);
				if (!inputStack.isEmpty()) {
					if (inputStack.is(ItemTags.DECORATED_POT_SHERDS)) {
						outputStack = inputStack.copy();
						outputStack.setCount(2);
						sherds += 1;
					} else if (inputStack.is(Items.BRICK)) {
						bricks += 1;
					} else {
						return ItemStack.EMPTY;
					}
				}
			}
			if (bricks == 1 && sherds == 1 && outputStack != ItemStack.EMPTY) {
				return outputStack;
			}
		}
		return ItemStack.EMPTY;
	}

	public boolean canCraftInDimensions(int i, int j) {
		return i >= 2 && j >= 2;
	}

	@Override @NotNull
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return TTRecipeTypes.SHERD_COPY_RECIPE;
	}
}

