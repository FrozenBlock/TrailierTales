/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.mixin.client.brush;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.frozenblock.trailiertales.impl.client.ArmedEntityRenderStateInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin<S extends ArmedEntityRenderState, M extends EntityModel<S> & ArmedModel> {

	@Inject(
		method = "renderArmWithItem",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
			shift = At.Shift.AFTER
		)
	)
	void trailierTales$injectBrushAnim(
		S armedEntityRenderState,
		ItemStackRenderState itemStackRenderState,
		HumanoidArm humanoidArm,
		PoseStack poseStack,
		MultiBufferSource multiBufferSource,
		int i,
		CallbackInfo info
	) {
		if (
			armedEntityRenderState instanceof HumanoidRenderState humanoidRenderState
			&& humanoidRenderState instanceof ArmedEntityRenderStateInterface armedEntityRenderStateInterface
		) {
			InteractionHand interactionHand = humanoidArm == humanoidRenderState.mainArm ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
			boolean isLeftHand = humanoidArm == HumanoidArm.LEFT;
			ItemStack item = isLeftHand ? armedEntityRenderStateInterface.trailierTales$getLeftHandItemStack() : armedEntityRenderStateInterface.trailierTales$getRightHandItemStack();
			if (
				item != null
				&& TTItemConfig.SMOOTH_BRUSH_ANIMATION
				&& humanoidRenderState.isUsingItem
				&& humanoidRenderState.useItemHand == interactionHand
				&& humanoidRenderState.attackTime < 1.0E-5F
				&& item.is(Items.BRUSH)
			) {
				float remainingTicks = humanoidRenderState.ticksUsingItem + 1F;
				float partialTick = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true);
				float brushProgress = remainingTicks + partialTick;
				float brushRoll = Mth.cos((brushProgress * Mth.PI) / 5F) * 1.2F;

				if (humanoidArm == HumanoidArm.LEFT) {
					poseStack.mulPose(Axis.ZP.rotation(brushRoll));
				} else {
					poseStack.mulPose(Axis.ZN.rotation(brushRoll));
				}
			}
		}
	}
}
