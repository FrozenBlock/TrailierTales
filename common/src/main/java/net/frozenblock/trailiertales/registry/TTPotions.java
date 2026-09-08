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

package net.frozenblock.trailiertales.registry;

import java.util.function.Supplier;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public final class TTPotions {
	private static final DeferredRegister<Potion> REGISTER = DeferredRegister.create(
		Registries.POTION,
		TTConstants.MOD_ID
	);

	public static final DeferredHolder<Potion, Potion> TRANSFIGURING = register(
		"transfiguring",
		() -> new Potion("transfiguring", new MobEffectInstance(TTMobEffects.TRANSFIGURING.asHolder(), 3600))
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static DeferredHolder<Potion, Potion> register(String name, Supplier<Potion> potion) {
		return REGISTER.register(name, potion);
	}

	private static DeferredHolder<Potion, Potion> register(ResourceKey<Potion> key, Supplier<Potion> potion) {
		return REGISTER.register(key, potion);
	}

	private TTPotions() {}
}
