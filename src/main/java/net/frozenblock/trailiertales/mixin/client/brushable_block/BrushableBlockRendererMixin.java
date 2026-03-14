/*
 * Copyright 2025-2026 FrozenBlock
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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.client.TTRenderStateDataKeys;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.blockentity.state.BrushableBlockRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
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
		method = "extractRenderState*",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
		)
	)
	public Comparable<Integer> trailierTales$removeBrushRequirementAndSetItemScale(Comparable<Integer> original) {
		return TTBlockConfig.SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS.get() ? 1 : original;
	}

	@Inject(
		method = "extractRenderState*",
		at = @At("TAIL")
	)
	private void trailierTales$extractBrushableBlockRenderState(
		BrushableBlockEntity brushableBlock,
		BrushableBlockRenderState renderState,
		float partialTick,
		Vec3 cameraPosition,
		ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
		CallbackInfo info
	) {
		if (TTBlockConfig.SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS.get() && brushableBlock instanceof BrushableBlockEntityInterface blockInterface) {
			renderState.setData(TTRenderStateDataKeys.BRUSHABLE_BLOCK_X_OFFSET, blockInterface.trailierTales$getXOffset(partialTick));
			renderState.setData(TTRenderStateDataKeys.BRUSHABLE_BLOCK_Y_OFFSET, blockInterface.trailierTales$getYOffset(partialTick));
			renderState.setData(TTRenderStateDataKeys.BRUSHABLE_BLOCK_Z_OFFSET, blockInterface.trailierTales$getZOffset(partialTick));
			renderState.setData(TTRenderStateDataKeys.BRUSHABLE_BLOCK_ROTATION, blockInterface.trailierTales$getRotation(partialTick));
			renderState.setData(TTRenderStateDataKeys.BRUSHABLE_BLOCK_ITEM_SCALE, blockInterface.trailierTales$getItemScale(partialTick));
		}
	}

	@Inject(
		method = "submit*",
		at = @At("HEAD"),
		cancellable = true
	)
	public void trailierTales$cancelIfItemIsTooSmall(
		BrushableBlockRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		CameraRenderState cameraState,
		CallbackInfo info
	) {
		if (!TTBlockConfig.SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS.get()) return;
		if (renderState.getDataOrDefault(TTRenderStateDataKeys.BRUSHABLE_BLOCK_ITEM_SCALE, 1F) <= 0.05F) info.cancel();
	}

	@WrapOperation(
		method = "submit*",
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
		float x,
		float y,
		float z,
		Operation<Void> original,
		BrushableBlockRenderState renderState
	) {
		if (TTBlockConfig.SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS.get()) {
			original.call(
				instance,
				renderState.getDataOrDefault(TTRenderStateDataKeys.BRUSHABLE_BLOCK_X_OFFSET, x),
				renderState.getDataOrDefault(TTRenderStateDataKeys.BRUSHABLE_BLOCK_Y_OFFSET, y),
				renderState.getDataOrDefault(TTRenderStateDataKeys.BRUSHABLE_BLOCK_Z_OFFSET, z)
			);
		} else {
			original.call(instance, x, y, z);
		}
	}

	@WrapOperation(
		method = "submit*",
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
		Quaternionfc rotation,
		Operation<Void> original,
		BrushableBlockRenderState renderState
	) {
		if (TTBlockConfig.SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS.get()) {
			original.call(
				instance,
				Axis.YP.rotationDegrees(renderState.getDataOrDefault(TTRenderStateDataKeys.BRUSHABLE_BLOCK_ROTATION, 0F) + 15F)
			);
		} else {
			original.call(instance, rotation);
		}
	}

	@WrapOperation(
		method = "submit*",
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
		if (TTBlockConfig.SUSPICIOUS_BLOCK_SMOOTH_ANIMATIONS.get()) {
			final float itemScale = renderState.getDataOrDefault(TTRenderStateDataKeys.BRUSHABLE_BLOCK_ITEM_SCALE, 1F);
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
