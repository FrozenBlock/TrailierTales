package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.data.recipe.SherdCopyRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class RegisterRecipies {

	public static final RecipeSerializer<SherdCopyRecipe> SHERD_COPY_RECIPE = Registry.register(
		BuiltInRegistries.RECIPE_SERIALIZER,
		TrailierTalesSharedConstants.id("crafting_sherd_copy"),
		new SimpleCraftingRecipeSerializer<>(SherdCopyRecipe::new)
	);

	public static void init() {
	}


}
