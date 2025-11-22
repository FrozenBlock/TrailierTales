/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.particle.options;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ScalableParticleOptionsBase;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.Contract;
import org.joml.Vector3f;

public class GlowingDustColorTransitionOptions extends ScalableParticleOptionsBase {
	public static final MapCodec<GlowingDustColorTransitionOptions> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			ExtraCodecs.RGB_COLOR_CODEC.fieldOf("from_color").forGetter(options -> options.fromColor),
			ExtraCodecs.RGB_COLOR_CODEC.fieldOf("to_color").forGetter(options -> options.toColor),
			SCALE.fieldOf("scale").forGetter(ScalableParticleOptionsBase::getScale)
		).apply(instance, GlowingDustColorTransitionOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, GlowingDustColorTransitionOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT,
		options -> options.fromColor,
		ByteBufCodecs.INT,
		options -> options.toColor,
		ByteBufCodecs.FLOAT,
		ScalableParticleOptionsBase::getScale,
		GlowingDustColorTransitionOptions::new
	);
	private final int fromColor;
	private final int toColor;

	public GlowingDustColorTransitionOptions(int fromColor, int toColor, float scale) {
		super(scale);
		this.fromColor = fromColor;
		this.toColor = toColor;
	}

	@Contract("_, _ -> new")
	public static GlowingDustColorTransitionOptions ofSingleColor(int color, float scale) {
		return new GlowingDustColorTransitionOptions(color, color, scale);
	}

	public Vector3f getFromColor() {
		return ARGB.vector3fFromRGB24(this.fromColor);
	}

	public Vector3f getToColor() {
		return ARGB.vector3fFromRGB24(this.toColor);
	}

	@Override
	public ParticleType<GlowingDustColorTransitionOptions> getType() {
		return TTParticleTypes.GLOWING_DUST_COLOR_TRANSITION;
	}
}
