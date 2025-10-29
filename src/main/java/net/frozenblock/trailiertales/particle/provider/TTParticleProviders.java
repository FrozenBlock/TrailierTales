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
import org.jetbrains.annotations.NotNull;

public class TTParticleProviders {

	@Environment(EnvType.CLIENT)
	public record SuspiciousConnectionProvider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			FlyTowardsPositionParticle flyTowardsPositionParticle = new FlyTowardsPositionParticle(
				level, x, y, z, xd, yd, zd, true, new Particle.LifetimeAlpha(1F, 0F, 0F, 1F), this.sprite.get(random)
			);
			flyTowardsPositionParticle.scale(1.75F);
			return flyTowardsPositionParticle;
		}
	}

}
