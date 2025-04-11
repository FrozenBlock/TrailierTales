/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

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

public final class TTPotions {
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
