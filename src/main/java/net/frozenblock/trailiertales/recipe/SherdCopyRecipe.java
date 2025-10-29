/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.recipe;

import com.mojang.datafixers.util.Pair;
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
	private static final Pair<Boolean, ItemStack> EMPTY = Pair.of(false, ItemStack.EMPTY);

	public SherdCopyRecipe(CraftingBookCategory craftingBookCategory) {
		super(craftingBookCategory);
	}

	@Override
	public boolean matches(@NotNull CraftingInput input, Level level) {
		return getCraftingOutput(input).getFirst();
	}

	@Override @NotNull
	public ItemStack assemble(@NotNull CraftingInput input, HolderLookup.Provider provider) {
		return getCraftingOutput(input).getSecond();
	}

	private static Pair<Boolean, ItemStack> getCraftingOutput(@NotNull CraftingInput input) {
		if (!TTItemConfig.SHERD_DUPLICATION_RECIPE || input.ingredientCount() != 2) return EMPTY;

		int bricks = 0;
		int sherds = 0;
		Pair<Boolean, ItemStack> result = EMPTY;

		for (int i = 0; i < input.size(); i++) {
			final ItemStack inputStack = input.getItem(i);
			if (inputStack.isEmpty()) continue;
			if (inputStack.is(ItemTags.DECORATED_POT_SHERDS)) {
				final ItemStack outputStack = inputStack.copy();
				outputStack.setCount(2);
				result = Pair.of(true, outputStack);
				sherds += 1;
			} else if (inputStack.is(Items.BRICK)) {
				bricks += 1;
			} else {
				return EMPTY;
			}
		}
		if (bricks != 1 || sherds != 1) return EMPTY;
		return result;
	}

	@Override @NotNull
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return TTRecipeTypes.SHERD_COPY_RECIPE;
	}
}

