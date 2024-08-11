package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class RegisterPotions {
	public static final Holder.Reference<Potion> TRANSFIGURING = register(
		"transfiguring", new Potion(new MobEffectInstance(RegisterMobEffects.TRANSFIGURING, 3600))
	);

	public static void init() {
		FabricBrewingRecipeRegistryBuilder.BUILD.register(boringBuilder -> {
			if (boringBuilder instanceof FabricBrewingRecipeRegistryBuilder builder) {
				builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(RegisterItems.ECTOPLASM), TRANSFIGURING);
			}
		});
	}

	private static @NotNull Holder.Reference<Potion> register(String key, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, TrailierConstants.id(key), potion);
	}

	private static @NotNull Holder.Reference<Potion> register(ResourceKey<Potion> key, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, key, potion);
	}
}
