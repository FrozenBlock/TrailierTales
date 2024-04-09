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
			this.trailierTales$renderArmWithBrush(livingEntity, itemStack, displayContext, arm, poseStack, buffer, packedLight);
		}
	}

	@Unique
	private void trailierTales$renderArmWithBrush(@NotNull LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, HumanoidArm arm, @NotNull PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
		poseStack.pushPose();
		float remainingTicks = entity.getUseItemRemainingTicks() + 1F;
		float partialTick = Minecraft.getInstance().getDeltaFrameTime();
		float brushProgress = remainingTicks + partialTick;
		float brushRoll = (float) Math.cos(
			(brushProgress * Math.PI)
			/ 5D
		);
		if (arm != HumanoidArm.RIGHT) {
			poseStack.mulPose(Axis.XN.rotation(brushRoll));
		} else {
			poseStack.mulPose(Axis.XP.rotation(brushRoll));
		}
		this.itemInHandRenderer.renderItem(entity, stack, displayContext, false, poseStack, buffer, combinedLight);
		poseStack.popPose();
	}

}
