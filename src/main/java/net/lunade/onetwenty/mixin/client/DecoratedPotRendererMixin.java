package net.lunade.onetwenty.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Objects;
import net.lunade.onetwenty.Luna120Client;
import net.lunade.onetwenty.interfaces.DecoratedPotBlockEntityInterface;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotRenderer.class)
public class DecoratedPotRendererMixin {
	@Unique
	private static final Material LUNA120$BLANK_MATERIAL = Objects.requireNonNull(Sheets.getDecoratedPotMaterial(Luna120Client.BLANK_DECORATED));

	@Unique
	private boolean luna120$isMisMatched;

	@Inject(
		method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At("HEAD")
	)
	public void luna120$render(
		DecoratedPotBlockEntity decoratedPotBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo info
	) {
		this.luna120$setupMisMatched(decoratedPotBlockEntity);
	}

	@Inject(
		method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$WobbleStyle;duration:I",
			ordinal = 0
		)
	)
	public void luna120$prepareIsFlipped(
		DecoratedPotBlockEntity decoratedPotBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo info,
		@Share("luna120$isFlipped") LocalBooleanRef isFlipped
	) {
		isFlipped.set(((DecoratedPotBlockEntityInterface)decoratedPotBlockEntity).luna120$isWobbleFlipped());
	}

	@WrapOperation(
		method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/math/Axis;rotation(F)Lorg/joml/Quaternionf;"
		)
	)
	public Quaternionf luna120$flipWobble(
		Axis instance, float v, Operation<Quaternionf> original,
		@Share("luna120$isFlipped") LocalBooleanRef isFlipped
	) {
		return original.call(instance, v * (isFlipped.get() ? -1 : 1F));
	}

	@Unique
	private void luna120$setupMisMatched(@NotNull DecoratedPotBlockEntity decoratedPotBlockEntity) {
		boolean hasBlank = false;
		boolean hasDecorated = false;
		for (Item item : decoratedPotBlockEntity.getDecorations().ordered().stream().toList()) {
			if (Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.getResourceKey(Items.BRICK)) == Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.getResourceKey(item))) {
				hasBlank = true;
			} else {
				hasDecorated = true;
			}
		}
		this.luna120$isMisMatched = hasBlank && hasDecorated;
	}

	@Inject(
		method = "renderSide",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V",
			shift = At.Shift.BEFORE
		)
	)
	private void luna120$renderSide(
		ModelPart modelPart, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, @Nullable Material material, CallbackInfo info,
		@Share("luna120$newPattern") LocalBooleanRef newPattern, @Share("luna120$blankVertexConsumer") LocalRef<VertexConsumer> blankVertexConsumer
	) {
		if (material == Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.getResourceKey(Items.BRICK)) && this.luna120$isMisMatched) {
			newPattern.set(true);
			blankVertexConsumer.set(LUNA120$BLANK_MATERIAL.buffer(multiBufferSource, RenderType::entitySolid));
		}
	}

	@WrapOperation(
		method = "renderSide",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"
		)
	)
	private void luna120$renderSideFix(
		ModelPart instance, PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, Operation<Void> original,
		@Share("luna120$newPattern") LocalBooleanRef newPattern, @Share("luna120$blankVertexConsumer") LocalRef<VertexConsumer> blankVertexConsumer
	) {
		original.call(
			instance,
			poseStack,
			newPattern.get() ? blankVertexConsumer.get() : vertexConsumer,
			i,
			j
		);
	}

}
