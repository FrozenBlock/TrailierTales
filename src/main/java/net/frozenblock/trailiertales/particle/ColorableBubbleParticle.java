package net.frozenblock.trailiertales.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ColorParticleOption;
import org.jetbrains.annotations.NotNull;

public class ColorableBubbleParticle extends TextureSheetParticle {
	private final SpriteSet spriteProvider;

	ColorableBubbleParticle(ClientLevel world, double d, double e, double f, double g, double h, double i, @NotNull SpriteSet spriteProvider) {
		super(world, d, e, f);
		this.spriteProvider = spriteProvider;
		this.setSpriteFromAge(spriteProvider);
		this.quadSize *= 1.2F;
		this.setSize(0.02F, 0.02F);
		this.xd = g * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
		this.yd = h * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
		this.zd = i * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
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
			this.yd += 0.002;
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 0.85F;
			this.yd *= 0.85F;
			this.zd *= 0.85F;
		}
		this.setSpriteFromAge(this.spriteProvider);
	}

	@Override
	public int getLightColor(float tint) {
		return 240;
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Environment(EnvType.CLIENT)
	public static class Provider implements ParticleProvider<ColorParticleOption> {
		private final SpriteSet sprite;

		public Provider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		public Particle createParticle(ColorParticleOption colorParticleOption, ClientLevel world, double d, double e, double f, double g, double h, double i) {
			Particle particle = new ColorableBubbleParticle(world, d, e, f, g, h, i, this.sprite);
			particle.setColor(colorParticleOption.getRed(), colorParticleOption.getGreen(), colorParticleOption.getBlue());
			return particle;
		}
	}
}
