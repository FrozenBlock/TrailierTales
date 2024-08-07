package net.frozenblock.trailiertales.particle.provider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlyTowardsPositionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class TrailierParticleProviders {

	@Environment(EnvType.CLIENT)
	public static class SuspiciousConnectionProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public SuspiciousConnectionProvider(SpriteSet spriteProvider) {
			this.sprite = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel world, double d, double e, double f, double g, double h, double i) {
			FlyTowardsPositionParticle flyTowardsPositionParticle = new FlyTowardsPositionParticle(
				world, d, e, f, g, h, i, true, new Particle.LifetimeAlpha(1F, 0F, 0F, 1F)
			);
			flyTowardsPositionParticle.scale(1.75F);
			flyTowardsPositionParticle.pickSprite(this.sprite);
			return flyTowardsPositionParticle;
		}
	}

}
