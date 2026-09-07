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
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.frozenblock.trailiertales.effect.TransfiguringMobEffect;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class TTMobEffects {
	private static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(
		Registries.MOB_EFFECT,
		TTConstants.MOD_ID
	);

	public static final DeferredHolder<MobEffect, MobEffect> TRANSFIGURING = register(
		"transfiguring",
		() -> new TransfiguringMobEffect(
			MobEffectCategory.BENEFICIAL,
			8687789
		).requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final DeferredHolder<MobEffect, MobEffect> HAUNT = register(
		"haunt",
		() -> new MobEffect(
			MobEffectCategory.HARMFUL,
			10663385,
			ApparitionAid.EFFECT_PARTICLE
		).setBlendDuration(40).requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final DeferredHolder<MobEffect, MobEffect> SIEGE_OMEN = register(
		"siege_omen",
		() -> new MobEffect(MobEffectCategory.NEUTRAL,
			1484454,
			TTParticleTypes.SIEGE_OMEN.get()
		).withSoundOnAdded(TTSounds.APPLY_EFFECT_SIEGE_OMEN.get()).requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <T extends MobEffect> DeferredHolder<MobEffect, T> register(String id, Supplier<T> entry) {
		return REGISTER.register(id, entry);
	}

	private TTMobEffects() {}
}
