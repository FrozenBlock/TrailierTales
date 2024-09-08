package net.frozenblock.trailiertales.registry;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.particle.options.GlowingDustColorTransitionOptions;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public final class TTParticleTypes {
	public static final SimpleParticleType COFFIN_SOUL = register("coffin_soul");
	public static final SimpleParticleType COFFIN_SOUL_ENTER = register("coffin_soul_enter");
	public static final ParticleType<ColorParticleOption> GLOWING_BUBBLE = register(
		"glowing_bubble", false, ColorParticleOption::codec, ColorParticleOption::streamCodec
	);
	public static final ParticleType<ColorParticleOption> GLOWING_ENTITY_EFFECT = register(
		"glowing_entity_effect", false, ColorParticleOption::codec, ColorParticleOption::streamCodec
	);
	public static final ParticleType<GlowingDustColorTransitionOptions> GLOWING_DUST_COLOR_TRANSITION = register(
		"glowing_dust_color_transition", false, particleType -> GlowingDustColorTransitionOptions.CODEC, particleType -> GlowingDustColorTransitionOptions.STREAM_CODEC
	);
	public static final SimpleParticleType SUSPICIOUS_CONNECTION = register("suspicious_connection");
	public static final SimpleParticleType SIEGE_OMEN = register("siege_omen");
	public static final SimpleParticleType TRANSFIGURING = register("transfiguring");

	public static void init() {
		TTConstants.log("Registering Particles for Trailier Tales.", TTConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static SimpleParticleType register(@NotNull String name, boolean alwaysShow) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, TTConstants.id(name), FabricParticleTypes.simple(alwaysShow));
	}

	@NotNull
	private static SimpleParticleType register(@NotNull String name) {
		return register(name, false);
	}

	@NotNull
	private static <T extends ParticleOptions> ParticleType<T> register(
		String string,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> function,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> function2
	) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, TTConstants.id(string), new ParticleType<T>(alwaysShow) {
			@Override
			public MapCodec<T> codec() {
				return function.apply(this);
			}

			@NotNull
			@Override
			public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
				return function2.apply(this);
			}
		});
	}
}
