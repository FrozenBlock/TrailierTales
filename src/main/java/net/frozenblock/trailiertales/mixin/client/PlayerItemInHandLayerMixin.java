package net.frozenblock.trailiertales.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
public abstract class PlayerItemInHandLayerMixin<T extends Player, M extends EntityModel<T> & ArmedModel & HeadedModel> extends ItemInHandLayer<T, M> {

	@Shadow
	@Final
	private ItemInHandRenderer itemInHandRenderer;

	public PlayerItemInHandLayerMixin(RenderLayerParent<T, M> renderer, ItemInHandRenderer itemInHandRenderer) {
		super(renderer, itemInHandRenderer);
	}

	@Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
	void trailierTales$overrideWithNewBrushAnim(
		LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight,
		CallbackInfo info
	) {
		if (itemStack.is(Items.BRUSH) && livingEntity.getUseItem() == itemStack && livingEntity.swingTime == 0) {
			info.cancel();
			this.trailierTales$renderArmWithBrush(livingEntity, itemStack, arm, poseStack, buffer, packedLight);
		}
	}

	@Unique
	private void trailierTales$renderArmWithBrush(LivingEntity entity, ItemStack stack, HumanoidArm arm, @NotNull PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
		poseStack.pushPose();
		float remainingTicks = entity.getUseItemRemainingTicks() % 10F;
		float partialTick = Minecraft.getInstance().getDeltaFrameTime();
		float g = remainingTicks - partialTick + 1F;
		float h = 1.0F - g / 10.0F;
		float n = -15.0F + 75.0F * Mth.cos(h * 2.0F * (float) Math.PI);
		if (arm != HumanoidArm.RIGHT) {
			poseStack.translate(0.1, 0.83, 0.35);
			poseStack.mulPose(Axis.XP.rotationDegrees(-80.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
			poseStack.mulPose(Axis.XP.rotationDegrees(n));
			poseStack.translate(-0.3, 0.22, 0.35);
		} else {
			poseStack.translate(-0.25, 0.22, 0.35);
			poseStack.mulPose(Axis.XP.rotationDegrees(-80.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
			poseStack.mulPose(Axis.XP.rotationDegrees(n));
		}
		this.itemInHandRenderer.renderItem(entity, stack, ItemDisplayContext.HEAD, false, poseStack, buffer, combinedLight);
		poseStack.popPose();
	}

}
