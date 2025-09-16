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

package net.frozenblock.trailiertales.particle.provider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlyTowardsPositionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class TTParticleProviders {

	@Environment(EnvType.CLIENT)
	public static class SuspiciousConnectionProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public SuspiciousConnectionProvider(SpriteSet spriteProvider) {
			this.sprite = spriteProvider;
		}

		@Override
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
			FlyTowardsPositionParticle flyTowardsPositionParticle = new FlyTowardsPositionParticle(
				world, d, e, f, g, h, i, true, new Particle.LifetimeAlpha(1F, 0F, 0F, 1F), this.sprite.get(random)
			);
			flyTowardsPositionParticle.scale(1.75F);
			return flyTowardsPositionParticle;
		}
	}

}
