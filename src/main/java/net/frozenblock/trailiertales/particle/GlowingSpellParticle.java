package net.frozenblock.trailiertales.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class GlowingSpellParticle extends TextureSheetParticle {
	private static final RandomSource RANDOM = RandomSource.create();
	private final SpriteSet sprites;
	private float originalAlpha = 1.0F;

	GlowingSpellParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
		super(world, x, y, z, 0.5 - RANDOM.nextDouble(), velocityY, 0.5 - RANDOM.nextDouble());
		this.friction = 0.96F;
		this.gravity = -0.1F;
		this.speedUpWhenYMotionIsBlocked = true;
		this.sprites = spriteProvider;
		this.yd *= 0.2F;
		if (velocityX == 0.0 && velocityZ == 0.0) {
			this.xd *= 0.1F;
			this.zd *= 0.1F;
		}

		this.quadSize *= 0.75F;
		this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2));
		this.hasPhysics = false;
		this.setSpriteFromAge(spriteProvider);
		if (this.isCloseToScopingPlayer()) {
			this.setAlpha(0.0F);
		}
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.sprites);
		if (this.isCloseToScopingPlayer()) {
			this.alpha = 0.0F;
		} else {
			this.alpha = Mth.lerp(0.05F, this.alpha, this.originalAlpha);
		}
	}

	@Override
	protected void setAlpha(float alpha) {
		super.setAlpha(alpha);
		this.originalAlpha = alpha;
	}

	private boolean isCloseToScopingPlayer() {
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer localPlayer = minecraft.player;
		return localPlayer != null
			&& localPlayer.getEyePosition().distanceToSqr(this.x, this.y, this.z) <= 9.0
			&& minecraft.options.getCameraType().isFirstPerson()
			&& localPlayer.isScoping();
	}

	@Override
	protected int getLightColor(float tint) {
		return 240;
	}

	@Environment(EnvType.CLIENT)
	public static class MobEffectProvider implements ParticleProvider<ColorParticleOption> {
		private final SpriteSet sprite;

		public MobEffectProvider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		public Particle createParticle(@NotNull ColorParticleOption colorParticleOption, ClientLevel world, double d, double e, double f, double g, double h, double i) {
			GlowingSpellParticle particle = new GlowingSpellParticle(world, d, e, f, g, h, i, this.sprite);
			particle.setColor(colorParticleOption.getRed(), colorParticleOption.getGreen(), colorParticleOption.getBlue());
			particle.setAlpha(colorParticleOption.getAlpha());
			return particle;
		}
	}
}
