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
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.frozenblock.trailiertales.impl.client.BrushableBlockRenderStateInterface;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.blockentity.state.BrushableBlockRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BrushableBlockRenderer.class)
public class BrushableBlockRendererMixin {

	@ModifyExpressionValue(
		method = "extractRenderState(Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/BrushableBlockRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
		)
	)
	public Comparable<Integer> trailierTales$removeBrushRequirementAndSetItemScale(Comparable<Integer> original) {
		return TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS ? 1 : original;
	}

	@Inject(
		method = "extractRenderState(Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/BrushableBlockRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
		at = @At("TAIL")
	)
	private void trailierTales$extractBrushableBlockRenderState(BrushableBlockEntity brushableBlockEntity, BrushableBlockRenderState brushableBlockRenderState, float partialTick, Vec3 vec3, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, CallbackInfo ci) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface && brushableBlockRenderState instanceof BrushableBlockRenderStateInterface brushableBlockRenderStateInterface) {
			brushableBlockRenderStateInterface.trailierTales$setXOffset(brushableBlockEntityInterface.trailierTales$getXOffset(partialTick));
			brushableBlockRenderStateInterface.trailierTales$setYOffset(brushableBlockEntityInterface.trailierTales$getYOffset(partialTick));
			brushableBlockRenderStateInterface.trailierTales$setZOffset(brushableBlockEntityInterface.trailierTales$getZOffset(partialTick));
			brushableBlockRenderStateInterface.trailierTales$setRotation(brushableBlockEntityInterface.trailierTales$getRotation(partialTick));
			brushableBlockRenderStateInterface.trailierTales$setItemScale(brushableBlockEntityInterface.trailierTales$getItemScale(partialTick));
		}
	}

	@Inject(
		method = "submit(Lnet/minecraft/client/renderer/blockentity/state/BrushableBlockRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	public void trailierTales$setItemScaleOrCancel(
		BrushableBlockRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CallbackInfo ci,
		@Share("trailierTales$itemScale") LocalFloatRef itemScale
	) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && renderState instanceof BrushableBlockRenderStateInterface brushableBlockRenderStateInterface) {
			itemScale.set(brushableBlockRenderStateInterface.trailierTales$getItemScale());
			if (itemScale.get() <= 0.05F)
				ci.cancel();
		}
	}

	@WrapOperation(
		method = "submit(Lnet/minecraft/client/renderer/blockentity/state/BrushableBlockRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
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
		BrushableBlockRenderState renderState
	) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && renderState instanceof BrushableBlockRenderStateInterface brushableBlockRenderStateInterface) {
			original.call(
				instance,
				brushableBlockRenderStateInterface.trailierTales$getXOffset(),
				brushableBlockRenderStateInterface.trailierTales$getYOffset(),
				brushableBlockRenderStateInterface.trailierTales$getZOffset()
			);
		} else {
			original.call(instance, f, g, h);
		}
	}

	@WrapOperation(
		method = "submit(Lnet/minecraft/client/renderer/blockentity/state/BrushableBlockRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V",
				shift = At.Shift.AFTER
			)
		)
	)
	public void trailierTales$useSmoothXAxisRotation(
		PoseStack instance,
		Quaternionfc quaternionf,
		Operation<Void> original,
		BrushableBlockRenderState renderState
	) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && renderState instanceof BrushableBlockRenderStateInterface brushableBlockRenderStateInterface) {
			original.call(
				instance,
				Axis.YP.rotationDegrees(brushableBlockRenderStateInterface.trailierTales$getRotation() + 15F)
			);
		} else {
			original.call(instance, quaternionf);
		}
	}

	@WrapOperation(
		method = "submit(Lnet/minecraft/client/renderer/blockentity/state/BrushableBlockRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
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
		BrushableBlockRenderState renderState
	) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && renderState instanceof BrushableBlockRenderStateInterface brushableBlockRenderStateInterface) {
			float itemScale = brushableBlockRenderStateInterface.trailierTales$getItemScale();
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
