package net.frozenblock.trailiertales.effect.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.registry.RegisterMobEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HauntFogFunction implements FogRenderer.MobEffectFogFunction {

	@Override
	public @NotNull Holder<MobEffect> getMobEffect() {
		return RegisterMobEffects.HAUNT;
	}

	@Override
	public void setupFog(FogRenderer.@NotNull FogData parameters, @NotNull LivingEntity entity, @NotNull MobEffectInstance effect, float viewDistance, float tickDelta) {
		float entityProgress = entity.tickCount + tickDelta;
		float fogCos = (Mth.cos((entityProgress * Mth.PI) / 48F) * 4F) + 12F;
		float fogDistance = Mth.lerp(effect.getBlendFactor(entity, tickDelta), viewDistance, fogCos);
		if (parameters.mode == FogRenderer.FogMode.FOG_SKY) {
			parameters.start = 0F;
			parameters.end = fogDistance * 0.8F;
		} else {
			parameters.start = fogDistance * 0.65F;
			parameters.end = fogDistance;
		}
	}

	@Override
	public float getModifiedVoidDarkness(LivingEntity entity, @NotNull MobEffectInstance effect, float horizonShading, float tickDelta) {
		return 1F - effect.getBlendFactor(entity, tickDelta);
	}
}
