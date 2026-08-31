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

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.api.registry.DeferredSimpleParticleType;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.particle.options.GlowingDustColorTransitionOptions;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public final class TTParticleTypes {
	private static final DeferredRegister.ParticleTypes REGISTER = DeferredRegister.createParticleTypes(
		TTConstants.MOD_ID
	);

	public static final DeferredSimpleParticleType COFFIN_SOUL = register("coffin_soul");
	public static final DeferredSimpleParticleType COFFIN_SOUL_ENTER = register("coffin_soul_enter");
	public static final DeferredHolder<ParticleType<?>, ParticleType<ColorParticleOption>> GLOWING_BUBBLE = register("glowing_bubble", false, ColorParticleOption::codec, ColorParticleOption::streamCodec);
	public static final DeferredHolder<ParticleType<?>, ParticleType<ColorParticleOption>> GLOWING_ENTITY_EFFECT = register("glowing_entity_effect", false, ColorParticleOption::codec, ColorParticleOption::streamCodec);
	public static final DeferredHolder<ParticleType<?>, ParticleType<GlowingDustColorTransitionOptions>> GLOWING_DUST_COLOR_TRANSITION = register("glowing_dust_color_transition", false, particleType -> GlowingDustColorTransitionOptions.CODEC, particleType -> GlowingDustColorTransitionOptions.STREAM_CODEC);
	public static final DeferredSimpleParticleType SUSPICIOUS_CONNECTION = register("suspicious_connection");
	public static final DeferredSimpleParticleType SIEGE_OMEN = register("siege_omen");
	public static final DeferredSimpleParticleType TRANSFIGURING = register("transfiguring");

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static DeferredSimpleParticleType register(String name, boolean alwaysShow) {
		return REGISTER.register(name, alwaysShow);
	}

	private static DeferredSimpleParticleType register(String name) {
		return register(name, false);
	}

	private static <T extends ParticleOptions> DeferredHolder<ParticleType<?>, ParticleType<T>> register(
		String name,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> codec,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec
	) {
		return REGISTER.register(name, () -> new ParticleType<>(alwaysShow) {
			@Override
			public MapCodec<T> codec() {
				return codec.apply(this);
			}

			@Override
			public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
				return streamCodec.apply(this);
			}
		});
	}
}
