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

package net.frozenblock.trailiertales.client.renderer.fog.environment;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.MobEffectFogEnvironment;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HauntFogEnvironment extends MobEffectFogEnvironment {

	@Override
	public @NotNull Holder<MobEffect> getMobEffect() {
		return TTMobEffects.HAUNT;
	}

	@Override
	public void setupFog(FogData fogData, @NotNull Camera camera, ClientLevel level, float viewDistance, DeltaTracker deltaTracker) {
		if (!(camera.entity() instanceof LivingEntity livingEntity)) return;

		final MobEffectInstance mobEffectInstance = livingEntity.getEffect(this.getMobEffect());
		if (mobEffectInstance == null) return;
		final float tickDelta = deltaTracker.getGameTimeDeltaPartialTick(false);
		final float entityProgress = livingEntity.tickCount + tickDelta;
		final float fogCos = (Mth.cos((entityProgress * Mth.PI) / 48F) * 4F) + 12F;
		final float fogDistance = Mth.lerp(mobEffectInstance.getBlendFactor(livingEntity, tickDelta), viewDistance, fogCos);
		fogData.environmentalStart = fogDistance * 0.75F;
		fogData.environmentalEnd = fogDistance;
		fogData.skyEnd = fogDistance;
		fogData.cloudEnd = fogDistance;
	}

	@Override
	public float getModifiedDarkness(@NotNull LivingEntity entity, float f, float g) {
		final MobEffectInstance mobEffectInstance = entity.getEffect(this.getMobEffect());
		return mobEffectInstance != null ? Math.max(mobEffectInstance.getBlendFactor(entity, g), f) : f;
	}
}
