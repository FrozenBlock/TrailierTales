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

import net.frozenblock.trailiertales.particle.options.GlowingDustColorTransitionOptions;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DustParticleBase;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;

@ClientOnly
public class GlowingColorTransitionParticle extends DustParticleBase<GlowingDustColorTransitionOptions> {
	private final Vector3f fromColor;
	private final Vector3f toColor;

	public GlowingColorTransitionParticle(
		ClientLevel level,
		double x, double y, double z,
		double xa, double ya, double za,
		GlowingDustColorTransitionOptions options,
		SpriteSet sprites
	) {
		super(level, x, y, z, xa, ya, za, options, sprites);
		float f = this.random.nextFloat() * 0.4F + 0.6F;
		this.fromColor = this.randomizeColor(options.getFromColor(), f);
		this.toColor = this.randomizeColor(options.getToColor(), f);
	}

	private Vector3f randomizeColor(Vector3f color, float factor) {
		return new Vector3f(this.randomizeColor(color.x(), factor), this.randomizeColor(color.y(), factor), this.randomizeColor(color.z(), factor));
	}

	private void lerpColors(float partialTicks) {
		final float lerpProgress = ((float) this.age + partialTicks) / ((float) this.lifetime + 1F);
		final Vector3f vector3f = new Vector3f(this.fromColor).lerp(this.toColor, lerpProgress);
		this.rCol = vector3f.x();
		this.gCol = vector3f.y();
		this.bCol = vector3f.z();
	}

	@Override
	public void extract(QuadParticleRenderState particleTypeRenderState, Camera camera, float partialTickTime) {
		this.lerpColors(partialTickTime);
		super.extract(particleTypeRenderState, camera, partialTickTime);
	}

	@Override
	protected int getLightCoords(float a) {
		return LightCoordsUtil.MAX_SMOOTH_LIGHT_LEVEL;
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<GlowingDustColorTransitionOptions> {
		@Override
		public Particle createParticle(
			GlowingDustColorTransitionOptions options,
			ClientLevel level,
			double x, double y, double z,
			double xAux, double yAux, double zAux,
			RandomSource random
		) {
			return new GlowingColorTransitionParticle(level, x, y, z, xAux, yAux, zAux, options, this.spriteSet);
		}
	}
}
