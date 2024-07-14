package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.List;
import net.frozenblock.trailiertales.effect.client.HauntFogFunction;
import net.frozenblock.trailiertales.registry.RegisterMobEffects;
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

@Mixin(FogRenderer.class)
public class FogRendererMixin {

	@Shadow
	@Final
	private static List<FogRenderer.MobEffectFogFunction> MOB_EFFECT_FOG;

	@WrapOperation(
		method = "setupColor",
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
		return original.call(instance, effect) || original.call(instance, RegisterMobEffects.HAUNT);
	}

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void trailierTales$injectHauntFunction(CallbackInfo info) {
		MOB_EFFECT_FOG.add(new HauntFogFunction());
	}

}
