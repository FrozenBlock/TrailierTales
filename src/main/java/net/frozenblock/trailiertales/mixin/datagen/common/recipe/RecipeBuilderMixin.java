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
