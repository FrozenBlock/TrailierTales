package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.frozenblock.trailiertales.effect.TransfiguringMobEffect;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAid;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.jetbrains.annotations.NotNull;

public final class TTMobEffects {
	public static final Holder<MobEffect> TRANSFIGURING = register(
		"transfiguring",
		new TransfiguringMobEffect(
			MobEffectCategory.BENEFICIAL,
			8687789
		).requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Holder<MobEffect> HAUNT = register(
		"haunt",
		new MobEffect(
			MobEffectCategory.HARMFUL,
			10663385,
			ApparitionAid.EFFECT_PARTICLE
		).setBlendDuration(40).requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Holder<MobEffect> SIEGE_OMEN = register(
		"siege_omen",
		new MobEffect(MobEffectCategory.NEUTRAL,
			1484454,
			TTParticleTypes.SIEGE_OMEN
		).withSoundOnAdded(TTSounds.APPLY_EFFECT_SIEGE_OMEN).requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static void init() {
	}

	private static @NotNull Holder<MobEffect> register(String id, MobEffect entry) {
		return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, TTConstants.id(id), entry);
	}
}
