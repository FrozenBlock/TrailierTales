/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.client.renderer.fog.environment;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.MobEffectFogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HauntFogEnvironment extends MobEffectFogEnvironment {

	@Override
	public Holder<MobEffect> getMobEffect() {
		return TTMobEffects.HAUNT;
	}

	@Override
	public void setupFog(FogData fogData, Entity entity, BlockPos blockPos, ClientLevel clientLevel, float viewDistance, DeltaTracker deltaTracker) {
		if (entity instanceof LivingEntity livingEntity) {
			MobEffectInstance mobEffectInstance = livingEntity.getEffect(this.getMobEffect());
			if (mobEffectInstance != null) {
				float tickDelta = deltaTracker.getGameTimeDeltaPartialTick(false);
				float entityProgress = entity.tickCount + tickDelta;
				float fogCos = (Mth.cos((entityProgress * Mth.PI) / 48F) * 4F) + 12F;
				float fogDistance = Mth.lerp(mobEffectInstance.getBlendFactor(livingEntity, tickDelta), viewDistance, fogCos);
				fogData.environmentalStart = fogDistance * 0.75F;
				fogData.environmentalEnd = fogDistance;
				fogData.skyEnd = fogDistance;
				fogData.cloudEnd = fogDistance;
			}
		}
	}

	@Override
	public float getModifiedDarkness(@NotNull LivingEntity livingEntity, float f, float g) {
		MobEffectInstance mobEffectInstance = livingEntity.getEffect(this.getMobEffect());
		return mobEffectInstance != null ? Math.max(mobEffectInstance.getBlendFactor(livingEntity, g), f) : f;
	}
}
