package net.frozenblock.trailiertales.mixin.client.decorated_pot;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.impl.client.DecoratedPotBlockEntityInterface;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(DecoratedPotRenderer.class)
public class DecoratedPotRendererMixin {

	@Inject(
		method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$WobbleStyle;duration:I",
			ordinal = 0
		)
	)
	public void trailierTales$prepareIsFlipped(
		DecoratedPotBlockEntity decoratedPotBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo info,
		@Share("trailierTales$isFlipped") LocalBooleanRef isFlipped
	) {
		isFlipped.set(decoratedPotBlockEntity instanceof DecoratedPotBlockEntityInterface decoratedPotBlockEntityInterface
			&& decoratedPotBlockEntityInterface.trailierTales$isWobbleFlipped()
		);
	}

	@WrapOperation(
		method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/math/Axis;rotation(F)Lorg/joml/Quaternionf;"
		)
	)
	public Quaternionf trailierTales$flipWobble(
		Axis instance, float v, Operation<Quaternionf> original,
		@Share("trailierTales$isFlipped") LocalBooleanRef isFlipped
	) {
		return original.call(instance, v * (isFlipped.get() ? -1 : 1F));
	}

}
