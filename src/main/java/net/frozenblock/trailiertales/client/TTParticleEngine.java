package net.frozenblock.trailiertales.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.frozenblock.trailiertales.particle.GlowingColorBubbleParticle;
import net.frozenblock.trailiertales.particle.GlowingColorTransitionParticle;
import net.frozenblock.trailiertales.particle.GlowingSpellParticle;
import net.frozenblock.trailiertales.particle.provider.TTParticleProviders;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.minecraft.client.particle.SoulParticle;

@Environment(EnvType.CLIENT)
public class TTParticleEngine {

	public static void init() {
		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();

		particleRegistry.register(TTParticleTypes.COFFIN_SOUL, SoulParticle.EmissiveProvider::new);
		particleRegistry.register(TTParticleTypes.COFFIN_SOUL_ENTER, SoulParticle.EmissiveProvider::new);
		particleRegistry.register(TTParticleTypes.GLOWING_BUBBLE, GlowingColorBubbleParticle.Provider::new);
		particleRegistry.register(TTParticleTypes.GLOWING_ENTITY_EFFECT, GlowingSpellParticle.MobEffectProvider::new);
		particleRegistry.register(TTParticleTypes.GLOWING_DUST_COLOR_TRANSITION, GlowingColorTransitionParticle.Provider::new);
		particleRegistry.register(TTParticleTypes.SUSPICIOUS_CONNECTION, TTParticleProviders.SuspiciousConnectionProvider::new);
		particleRegistry.register(TTParticleTypes.SIEGE_OMEN, GlowingSpellParticle.Provider::new);
		particleRegistry.register(TTParticleTypes.TRANSFIGURING, GlowingSpellParticle.Provider::new);
	}
}
