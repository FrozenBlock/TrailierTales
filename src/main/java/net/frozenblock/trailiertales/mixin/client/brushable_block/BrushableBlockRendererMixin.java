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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.frozenblock.trailiertales.impl.client.BrushableBlockRenderStateInterface;
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
	private void trailierTales$extractBrushableBlockRenderState(
		BrushableBlockEntity brushableBlockEntity,
		BrushableBlockRenderState brushableBlockRenderState,
		float partialTick,
		Vec3 vec3,
		ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
		CallbackInfo info
	) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS
			&& brushableBlockEntity instanceof BrushableBlockEntityInterface blockInterface
			&& brushableBlockRenderState instanceof BrushableBlockRenderStateInterface stateInterface
		) {
			stateInterface.trailierTales$setXOffset(blockInterface.trailierTales$getXOffset(partialTick));
			stateInterface.trailierTales$setYOffset(blockInterface.trailierTales$getYOffset(partialTick));
			stateInterface.trailierTales$setZOffset(blockInterface.trailierTales$getZOffset(partialTick));
			stateInterface.trailierTales$setRotation(blockInterface.trailierTales$getRotation(partialTick));
			stateInterface.trailierTales$setItemScale(blockInterface.trailierTales$getItemScale(partialTick));
		}
	}

	@Inject(
		method = "submit(Lnet/minecraft/client/renderer/blockentity/state/BrushableBlockRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	public void trailierTales$cancelIfItemIsTooSmall(
		BrushableBlockRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CallbackInfo info
	) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && renderState instanceof BrushableBlockRenderStateInterface stateInterface) {
			if (stateInterface.trailierTales$getItemScale() <= 0.05F) info.cancel();
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
		float x,
		float y,
		float z,
		Operation<Void> original,
		BrushableBlockRenderState renderState
	) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && renderState instanceof BrushableBlockRenderStateInterface stateInterface) {
			original.call(
				instance,
				stateInterface.trailierTales$getXOffset(),
				stateInterface.trailierTales$getYOffset(),
				stateInterface.trailierTales$getZOffset()
			);
		} else {
			original.call(instance, x, y, z);
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
		Quaternionfc rotation,
		Operation<Void> original,
		BrushableBlockRenderState renderState
	) {
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && renderState instanceof BrushableBlockRenderStateInterface stateInterface) {
			original.call(
				instance,
				Axis.YP.rotationDegrees(stateInterface.trailierTales$getRotation() + 15F)
			);
		} else {
			original.call(instance, rotation);
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
		if (TTBlockConfig.SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS && renderState instanceof BrushableBlockRenderStateInterface stateInterface) {
			final float itemScale = stateInterface.trailierTales$getItemScale();
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
