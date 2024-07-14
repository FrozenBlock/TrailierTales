package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.registry.RegisterMobEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@WrapOperation(
		method = "doesMobEffectBlockSky",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z",
			ordinal = 0
		)
	)
	private boolean trailierTales$doesMobEffectBlockSky(LivingEntity instance, Holder<MobEffect> effect, Operation<Boolean> original) {
		return original.call(instance, effect) || original.call(instance, RegisterMobEffects.HAUNT);
	}

}
