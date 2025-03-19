/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.Contract;
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

	@Contract("_, _ -> new")
	public static @NotNull GlowingDustColorTransitionOptions ofSingleColor(Vector3f color, float scale) {
		return new GlowingDustColorTransitionOptions(color, color, scale);
	}

	public Vector3f getFromColor() {
		return this.fromColor;
	}

	public Vector3f getToColor() {
		return this.toColor;
	}

	@Override
	public @NotNull ParticleType<GlowingDustColorTransitionOptions> getType() {
		return TTParticleTypes.GLOWING_DUST_COLOR_TRANSITION;
	}
}
