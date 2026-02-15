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

package net.frozenblock.trailiertales.recipe;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.frozenblock.trailiertales.registry.TTRecipeTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class SherdCopyRecipe extends CustomRecipe {
	public static final MapCodec<SherdCopyRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			Ingredient.CODEC.fieldOf("sherd").forGetter(recipe -> recipe.sherd),
			Ingredient.CODEC.fieldOf("material").forGetter(recipe -> recipe.material)
		).apply(instance, SherdCopyRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, SherdCopyRecipe> STREAM_CODEC = StreamCodec.composite(
		Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.sherd,
		Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.material,
		SherdCopyRecipe::new
	);
	public static final RecipeSerializer<SherdCopyRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
	private static final Pair<Boolean, ItemStack> EMPTY = Pair.of(false, ItemStack.EMPTY);
	private final Ingredient sherd;
	private final Ingredient material;

	public SherdCopyRecipe(Ingredient sherd, Ingredient material) {
		super();
		this.sherd = sherd;
		this.material = material;
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		return this.getCraftingOutput(input).getFirst();
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		return this.getCraftingOutput(input).getSecond();
	}

	private Pair<Boolean, ItemStack> getCraftingOutput(CraftingInput input) {
		if (!TTItemConfig.SHERD_DUPLICATION_RECIPE.get() || input.ingredientCount() != 2) return EMPTY;

		int bricks = 0;
		int sherds = 0;
		Pair<Boolean, ItemStack> result = EMPTY;

		for (int i = 0; i < input.size(); i++) {
			final ItemStack inputStack = input.getItem(i);
			if (inputStack.isEmpty()) continue;
			if (this.sherd.test(inputStack)) {
				final ItemStack outputStack = inputStack.copy();
				outputStack.setCount(2);
				result = Pair.of(true, outputStack);
				sherds += 1;
			} else if (this.material.test(inputStack)) {
				bricks += 1;
			} else {
				return EMPTY;
			}
		}
		if (bricks != 1 || sherds != 1) return EMPTY;
		return result;
	}

	@Override
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return TTRecipeTypes.SHERD_COPY_RECIPE;
	}
}

