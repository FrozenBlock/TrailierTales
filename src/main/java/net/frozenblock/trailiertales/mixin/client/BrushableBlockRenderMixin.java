package net.frozenblock.trailiertales.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BrushableBlockRenderer.class)
public class BrushableBlockRenderMixin {

	@ModifyExpressionValue(
		method = "render(Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
		)
	)
	public Comparable<Integer> luna120$removeBrushRequirement(Comparable<Integer> original) {
		return 1;
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
	public void luna120$useSmoothTranslation(PoseStack instance, float f, float g, float h, Operation<Void> original, BrushableBlockEntity brushableBlockEntity, float partialTick) {
		BrushableBlockEntityInterface brushableBlockEntityInterface = (BrushableBlockEntityInterface) brushableBlockEntity;
		original.call(
			instance,
			brushableBlockEntityInterface.luna120$getXOffset(partialTick),
			brushableBlockEntityInterface.luna120$getYOffset(partialTick),
			brushableBlockEntityInterface.luna120$getZOffset(partialTick)
		);
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
	public void luna120$useSmoothXAxisRotation(PoseStack instance, Quaternionf quaternionf, Operation<Void> original, BrushableBlockEntity brushableBlockEntity, float partialTick) {
		BrushableBlockEntityInterface brushableBlockEntityInterface = (BrushableBlockEntityInterface) brushableBlockEntity;
		original.call(
			instance,
			Axis.YP.rotationDegrees(brushableBlockEntityInterface.luna120$getRotation(partialTick) + 15F)
		);
	}

}
