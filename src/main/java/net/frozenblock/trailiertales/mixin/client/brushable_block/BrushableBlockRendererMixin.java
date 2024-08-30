package net.frozenblock.trailiertales.mixin.client.brushable_block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.BlockConfig;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BrushableBlockRenderer.class)
public class BrushableBlockRendererMixin {

	@ModifyExpressionValue(
		method = "render(Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
		)
	)
	public Comparable<Integer> trailierTales$removeBrushRequirementAndSetItemScale(Comparable<Integer> original) {
		return BlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS ? 1 : original;
	}

	@Inject(
		method = "render(Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	public void trailierTales$setItemScaleOrCancel(
		BrushableBlockEntity brushableBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, CallbackInfo info,
		@Share("trailierTales$itemScale") LocalFloatRef itemScale
	) {
		if (BlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) {
			itemScale.set(brushableBlockEntityInterface.trailierTales$getItemScale(partialTick));
			if (itemScale.get() <= 0.05F) {
				info.cancel();
			}
		}
	}

	@WrapOperation(
		method = "render(Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/renderer/blockentity/BrushableBlockRenderer;translations(Lnet/minecraft/core/Direction;I)[F"
			)
		)
	)
	public void trailierTales$useSmoothTranslation(
		PoseStack instance,
		float f,
		float g,
		float h,
		Operation<Void> original,
		BrushableBlockEntity brushableBlockEntity,
		float partialTick
	) {
		if (BlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) {
			original.call(
				instance,
				brushableBlockEntityInterface.trailierTales$getXOffset(partialTick),
				brushableBlockEntityInterface.trailierTales$getYOffset(partialTick),
				brushableBlockEntityInterface.trailierTales$getZOffset(partialTick)
			);
		} else {
			original.call(instance, f, g, h);
		}
	}

	@WrapOperation(
		method = "render(Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V",
				shift = At.Shift.AFTER
			)
		)
	)
	public void trailierTales$useSmoothXAxisRotation(
		PoseStack instance,
		Quaternionf quaternionf,
		Operation<Void> original,
		BrushableBlockEntity brushableBlockEntity,
		float partialTick
	) {
		if (BlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) {
			original.call(
				instance,
				Axis.YP.rotationDegrees(brushableBlockEntityInterface.trailierTales$getRotation(partialTick) + 15F)
			);
		} else {
			original.call(instance, quaternionf);
		}
	}

	@WrapOperation(
		method = "render(Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"
		)
	)
	public void trailierTales$useSmoothScale(
		PoseStack instance,
		float x,
		float y,
		float z,
		Operation<Void> original,
		BrushableBlockEntity brushableBlockEntity,
		float partialTick
	) {
		if (BlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) {
			float itemScale = brushableBlockEntityInterface.trailierTales$getItemScale(partialTick);
			original.call(
				instance,
				x * itemScale,
				y * itemScale,
				z * itemScale
			);
		} else {
			original.call(instance, x, y, z);
		}
	}

}
