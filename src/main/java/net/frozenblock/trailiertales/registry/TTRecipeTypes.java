package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.recipe.SherdCopyRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class TTRecipeTypes {
	public static final RecipeSerializer<SherdCopyRecipe> SHERD_COPY_RECIPE = Registry.register(
		BuiltInRegistries.RECIPE_SERIALIZER,
		TTConstants.id("crafting_sherd_copy"),
		new CustomRecipe.Serializer<>(SherdCopyRecipe::new)
	);

	public static void init() {
	}
}
