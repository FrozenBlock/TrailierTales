package net.lunade.onetwenty.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import net.lunade.onetwenty.Luna120Client;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(DecoratedPotRenderer.class)
public class DecoratedPotRendererMixin {

	@Unique
	private static final Material luna120$blankMaterial = Objects.requireNonNull(Sheets.getDecoratedPotMaterial(Luna120Client.BLANK_DECORATED));
	@Unique
	public boolean luna120$shouldSwitchToNewPattern;
	@Unique
	private boolean luna120$isMisMatched;
	@Unique
	private VertexConsumer luna120$blankVertexConsumer;

	@Inject(method = "render*", at = @At("HEAD"))
	public void luna120$render(DecoratedPotBlockEntity decoratedPotBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo info) {
		this.luna120$setupMisMatched(decoratedPotBlockEntity);
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

	@Inject(method = "renderSide", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.BEFORE))
	private void luna120$renderSide(ModelPart modelPart, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, @Nullable Material material, CallbackInfo info) {
		this.luna120$setupBrickValues(multiBufferSource, material);
	}

	@Unique
	private void luna120$setupBrickValues(MultiBufferSource multiBufferSource, @Nullable Material material) {
		this.luna120$shouldSwitchToNewPattern = material == Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.getResourceKey(Items.BRICK)) && this.luna120$isMisMatched;
		this.luna120$blankVertexConsumer = luna120$blankMaterial.buffer(multiBufferSource, RenderType::entitySolid);
	}

	@ModifyArgs(method = "renderSide", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
	private void luna120$renderSideFix(Args args) {
		if (this.luna120$shouldSwitchToNewPattern) {
			args.set(1, this.luna120$blankVertexConsumer);
		}
	}

}
