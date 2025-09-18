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
import net.frozenblock.trailiertales.particle.options.GlowingDustColorTransitionOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DustParticleBase;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class GlowingColorTransitionParticle extends DustParticleBase<GlowingDustColorTransitionOptions> {
	private final Vector3f fromColor;
	private final Vector3f toColor;

	public GlowingColorTransitionParticle(
		@NotNull ClientLevel level,
		double x, double y, double z,
		double xd, double yd, double zd,
		GlowingDustColorTransitionOptions particleEffect,
		SpriteSet spriteProvider
	) {
		super(level, x, y, z, xd, yd, zd, particleEffect, spriteProvider);
		float f = this.random.nextFloat() * 0.4F + 0.6F;
		this.fromColor = this.randomizeColor(particleEffect.getFromColor(), f);
		this.toColor = this.randomizeColor(particleEffect.getToColor(), f);
	}

	@Contract("_, _ -> new")
	private @NotNull Vector3f randomizeColor(@NotNull Vector3f color, float factor) {
		return new Vector3f(this.randomizeColor(color.x(), factor), this.randomizeColor(color.y(), factor), this.randomizeColor(color.z(), factor));
	}

	private void lerpColors(float partialTick) {
		final float lerp = ((float) this.age + partialTick) / ((float) this.lifetime + 1F);
		Vector3f vector3f = new Vector3f(this.fromColor).lerp(this.toColor, lerp);
		this.rCol = vector3f.x();
		this.gCol = vector3f.y();
		this.bCol = vector3f.z();
	}

	@Override
	public void extract(QuadParticleRenderState renderState, Camera camera, float partialTick) {
		this.lerpColors(partialTick);
		super.extract(renderState, camera, partialTick);
	}

	@Override
	protected int getLightColor(float tint) {
		return 240;
	}

	public static class Provider implements ParticleProvider<GlowingDustColorTransitionOptions> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			GlowingDustColorTransitionOptions dustColorTransitionOptions,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new GlowingColorTransitionParticle(level, x, y, z, xd, yd, zd, dustColorTransitionOptions, this.spriteSet);
		}
	}
}
