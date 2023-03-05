package net.lunade.onetwenty.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.lunade.onetwenty.interfaces.SuspiciousSandBlockEntityInterface;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SuspiciousSandRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.SuspiciousSandBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SuspiciousSandRenderer.class)
public class SuspiciousSandItemLerpMixin {

	@Shadow
	@Final
	private ItemRenderer itemRenderer;

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void luna120$render(SuspiciousSandBlockEntity suspiciousSandBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo info) {
		info.cancel();
		this.luna120$smoothItemRender(suspiciousSandBlockEntity, partialTick, poseStack, multiBufferSource, i, j);
	}

	@Unique
	public void luna120$smoothItemRender(SuspiciousSandBlockEntity suspiciousSandBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
		if (suspiciousSandBlockEntity.getLevel() == null) {
			return;
		}
		SuspiciousSandBlockEntityInterface susInterface = (SuspiciousSandBlockEntityInterface)suspiciousSandBlockEntity;
		Direction direction = susInterface.luna120$getHitDirection();
		if (direction == null) {
			return;
		}
		ItemStack itemStack = suspiciousSandBlockEntity.getItem();
		if (itemStack.isEmpty()) {
			return;
		}
		poseStack.pushPose();
		poseStack.translate(0.0f, 0.5f, 0.0f);
		poseStack.translate(susInterface.luna120$getXOffset(partialTick), susInterface.luna120$getYOffset(partialTick), susInterface.luna120$getZOffset(partialTick));
		poseStack.mulPose(Axis.YP.rotationDegrees(75.0f));
		poseStack.mulPose(Axis.YP.rotationDegrees(susInterface.luna120$getRotation(partialTick) + 11));
		poseStack.scale(0.5f, 0.5f, 0.5f);
		int l = LevelRenderer.getLightColor(suspiciousSandBlockEntity.getLevel(), suspiciousSandBlockEntity.getBlockState(), suspiciousSandBlockEntity.getBlockPos().relative(direction));
		this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, l, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, suspiciousSandBlockEntity.getLevel(), 0);
		poseStack.popPose();
	}

}
