package net.frozenblock.trailiertales.mixin.client.brush;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin{

	@Inject(
		method = "renderArmWithItem",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
			shift = At.Shift.AFTER
		)
	)
	void trailierTales$injectBrushAnim(
		LivingEntity livingEntity,
		ItemStack itemStack,
		ItemDisplayContext displayContext,
		HumanoidArm arm,
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight,
		CallbackInfo info,
		@Local(ordinal = 0) boolean isLeftArm
	) {
		if (itemStack.is(Items.BRUSH) && livingEntity.getUseItem() == itemStack && livingEntity.swingTime == 0 && TTItemConfig.SMOOTH_BRUSH_ANIMATION) {
			float remainingTicks = livingEntity.getUseItemRemainingTicks() + 1F;
			float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks() - Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
			float brushProgress = remainingTicks + partialTick;
			float brushRoll = Mth.cos(
				(brushProgress * Mth.PI)
					/ 5F
			) * 1.2F;

			if (isLeftArm) {
				poseStack.mulPose(Axis.ZP.rotation(brushRoll));
			} else {
				poseStack.mulPose(Axis.ZN.rotation(brushRoll));
			}
		}
	}

}
