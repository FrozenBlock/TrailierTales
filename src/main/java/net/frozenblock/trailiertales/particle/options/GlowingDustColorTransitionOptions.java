package net.frozenblock.trailiertales.particle.options;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.trailiertales.registry.RegisterParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ScalableParticleOptionsBase;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class GlowingDustColorTransitionOptions extends ScalableParticleOptionsBase {
	public static final MapCodec<GlowingDustColorTransitionOptions> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(effect -> effect.fromColor),
				ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(effect -> effect.toColor),
				SCALE.fieldOf("scale").forGetter(ScalableParticleOptionsBase::getScale)
			)
			.apply(instance, GlowingDustColorTransitionOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, GlowingDustColorTransitionOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.VECTOR3F,
		dustColorTransitionOptions -> dustColorTransitionOptions.fromColor,
		ByteBufCodecs.VECTOR3F,
		dustColorTransitionOptions -> dustColorTransitionOptions.toColor,
		ByteBufCodecs.FLOAT,
		ScalableParticleOptionsBase::getScale,
		GlowingDustColorTransitionOptions::new
	);
	private final Vector3f fromColor;
	private final Vector3f toColor;

	public GlowingDustColorTransitionOptions(Vector3f fromColor, Vector3f toColor, float scale) {
		super(scale);
		this.fromColor = fromColor;
		this.toColor = toColor;
	}

	public Vector3f getFromColor() {
		return this.fromColor;
	}

	public Vector3f getToColor() {
		return this.toColor;
	}

	@Override
	public @NotNull ParticleType<GlowingDustColorTransitionOptions> getType() {
		return RegisterParticles.GLOWING_DUST_COLOR_TRANSITION;
	}
}
