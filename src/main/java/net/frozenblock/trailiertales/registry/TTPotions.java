package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class TTPotions {
	public static final Holder.Reference<Potion> TRANSFIGURING = register(
		"transfiguring", new Potion("transfiguring", new MobEffectInstance(TTMobEffects.TRANSFIGURING, 3600))
	);

	public static void init() {
		FabricBrewingRecipeRegistryBuilder.BUILD.register(boringBuilder -> {
			if (boringBuilder instanceof FabricBrewingRecipeRegistryBuilder builder) {
				builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(TTItems.ECTOPLASM), TRANSFIGURING);
			}
		});
	}

	private static @NotNull Holder.Reference<Potion> register(String key, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, TTConstants.id(key), potion);
	}

	private static @NotNull Holder.Reference<Potion> register(ResourceKey<Potion> key, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, key, potion);
	}
}
