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

package net.frozenblock.trailiertales.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class GlowingColorBubbleParticle extends SingleQuadParticle {
	private final SpriteSet spriteProvider;

	public GlowingColorBubbleParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, @NotNull SpriteSet spriteProvider) {
		super(level, x, y, z, spriteProvider.first());
		this.spriteProvider = spriteProvider;
		this.setSpriteFromAge(spriteProvider);
		this.quadSize *= 1.2F;
		this.setSize(0.02F, 0.02F);
		this.xd = xSpeed * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
		this.yd = ySpeed * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
		this.zd = zSpeed * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
		this.lifetime = 5;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			this.yd += 0.002D;
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 0.85D;
			this.yd *= 0.85D;
			this.zd *= 0.85D;
		}
		this.setSpriteFromAge(this.spriteProvider);
	}

	@Override
	public int getLightColor(float tint) {
		return 240;
	}

	@Override
	@NotNull
	protected Layer getLayer() {
		return Layer.OPAQUE;
	}

	public static class Provider implements ParticleProvider<ColorParticleOption> {
		private final SpriteSet sprite;

		public Provider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		public Particle createParticle(@NotNull ColorParticleOption colorParticleOption, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			Particle particle = new GlowingColorBubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprite);
			//TODO port color?
			//particle.setColor(colorParticleOption.getRed(), colorParticleOption.getGreen(), colorParticleOption.getBlue());
			return particle;
		}
	}
}
