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

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.particle.options.GlowingDustColorTransitionOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DustParticleBase;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class GlowingColorTransitionParticle extends DustParticleBase<GlowingDustColorTransitionOptions> {
	private final Vector3f fromColor;
	private final Vector3f toColor;

	public GlowingColorTransitionParticle(
		ClientLevel world,
		double x,
		double y,
		double z,
		double velocityX,
		double velocityY,
		double velocityZ,
		GlowingDustColorTransitionOptions particleEffect,
		SpriteSet spriteProvider
	) {
		super(world, x, y, z, velocityX, velocityY, velocityZ, particleEffect, spriteProvider);
		float f = this.random.nextFloat() * 0.4F + 0.6F;
		this.fromColor = this.randomizeColor(particleEffect.getFromColor(), f);
		this.toColor = this.randomizeColor(particleEffect.getToColor(), f);
	}

	@Contract("_, _ -> new")
	private @NotNull Vector3f randomizeColor(@NotNull Vector3f color, float factor) {
		return new Vector3f(this.randomizeColor(color.x(), factor), this.randomizeColor(color.y(), factor), this.randomizeColor(color.z(), factor));
	}

	private void lerpColors(float tickDelta) {
		float f = ((float) this.age + tickDelta) / ((float) this.lifetime + 1.0F);
		Vector3f vector3f = new Vector3f(this.fromColor).lerp(this.toColor, f);
		this.rCol = vector3f.x();
		this.gCol = vector3f.y();
		this.bCol = vector3f.z();
	}

	// TODO port
	/*@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
		this.lerpColors(tickDelta);
		super.render(vertexConsumer, camera, tickDelta);
	}*/

	@Override
	protected int getLightColor(float tint) {
		return 240;
	}

	public static class Provider implements ParticleProvider<GlowingDustColorTransitionOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteProvider) {
			this.sprites = spriteProvider;
		}

		@Override
		public Particle createParticle(
			GlowingDustColorTransitionOptions dustColorTransitionOptions, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random
		) {
			return new GlowingColorTransitionParticle(world, d, e, f, g, h, i, dustColorTransitionOptions, this.sprites);
		}
	}
}
