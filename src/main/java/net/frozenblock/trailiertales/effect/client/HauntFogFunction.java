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

package net.frozenblock.trailiertales.effect.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.registry.TTMobEffects;
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
		return TTMobEffects.HAUNT;
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
