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

package net.frozenblock.trailiertales.mixin.datagen.common.recipe;

import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.datagen.recipe.TTRecipeProvider;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RecipeBuilder.class)
public interface RecipeBuilderMixin {

	@ModifyVariable(
		method = "save(Lnet/minecraft/data/recipes/RecipeOutput;Ljava/lang/String;)V",
		at = @At("HEAD"),
		argsOnly = true,
		ordinal = 0
	)
	default String trailierTales$save(String original) {
		if (TTRecipeProvider.GENERATING_TT_RECIPES) {
			ResourceLocation originalLocation = ResourceLocation.tryParse(original);
			ResourceLocation trailierLocation = TTConstants.id(originalLocation.getPath());
			return trailierLocation.toString();
		}
		return original;
	}

}
