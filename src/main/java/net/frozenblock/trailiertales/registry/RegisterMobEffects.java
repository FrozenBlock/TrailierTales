package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.TrailierFeatureFlags;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAid;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.jetbrains.annotations.NotNull;

public class RegisterMobEffects {
	public static final Holder<MobEffect> HAUNT = register("haunt",
		new MobEffect(
			MobEffectCategory.HARMFUL,
			10663385,
			ApparitionAid.EFFECT_PARTICLE
		).setBlendDuration(40).requiredFeatures(TrailierFeatureFlags.FEATURE_FLAG)
	);

	public static void init() {
	}

	private static @NotNull Holder<MobEffect> register(String id, MobEffect entry) {
		return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, TrailierConstants.id(id), entry);
	}
}
