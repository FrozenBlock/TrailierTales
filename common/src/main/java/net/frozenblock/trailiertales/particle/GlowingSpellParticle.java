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

package net.frozenblock.trailiertales.particle;

import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.RandomSource;

@ClientOnly
public class GlowingSpellParticle extends SpellParticle {

	public GlowingSpellParticle(
		ClientLevel level,
		double x, double y, double z,
		double xa, double ya, double za,
		SpriteSet sprites
	) {
		super(level, x, y, z, xa, ya, za, sprites);
	}

	@Override
	protected int getLightCoords(float a) {
		return LightCoordsUtil.MAX_SMOOTH_LIGHT_LEVEL;
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			SimpleParticleType options,
			ClientLevel level,
			double x, double y, double z,
			double xAux, double yAux, double zAux,
			RandomSource random
		) {
			return new GlowingSpellParticle(level, x, y, z, xAux, yAux, zAux, this.spriteSet);
		}
	}

	public record MobEffectProvider(SpriteSet spriteSet) implements ParticleProvider<ColorParticleOption> {
		@Override
		public Particle createParticle(
			ColorParticleOption options,
			ClientLevel level,
			double x, double y, double z,
			double xAux, double yAux, double zAux,
			RandomSource random
		) {
			final GlowingSpellParticle particle = new GlowingSpellParticle(level, x, y, z, xAux, yAux, zAux, this.spriteSet);
			particle.setColor(options.getRed(), options.getGreen(), options.getBlue());
			particle.setAlpha(options.getAlpha());
			return particle;
		}
	}
}
