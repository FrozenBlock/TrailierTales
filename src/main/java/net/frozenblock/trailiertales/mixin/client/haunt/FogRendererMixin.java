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

package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.effect.client.HauntFogFunction;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(FogRenderer.class)
public class FogRendererMixin {

	@Shadow
	@Final
	private static List<FogRenderer.MobEffectFogFunction> MOB_EFFECT_FOG;

	@WrapOperation(
		method = "computeFogColor",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/effect/MobEffects;DARKNESS:Lnet/minecraft/core/Holder;"
			)
		)
	)
	private static boolean trailierTales$setupColor(LivingEntity instance, Holder<MobEffect> effect, Operation<Boolean> original) {
		return original.call(instance, effect) || (TTEntityConfig.HAUNTED_FOG && original.call(instance, TTMobEffects.HAUNT));
	}

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void trailierTales$injectHauntFunction(CallbackInfo info) {
		MOB_EFFECT_FOG.add(new HauntFogFunction());
	}

}
