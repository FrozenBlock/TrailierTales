/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.mixin.client.boat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.BoatBannerModel;
import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.frozenblock.trailiertales.impl.client.AbstractBoatRendererInterface;
import net.frozenblock.trailiertales.impl.client.BoatRenderStateInterface;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AbstractBoatRenderer.class)
public abstract class AbstractBoatRendererMixin extends EntityRenderer<AbstractBoat, BoatRenderState> implements AbstractBoatRendererInterface {

	@Shadow
	protected abstract EntityModel<BoatRenderState> model();

	@Unique
	private ResourceLocation trailierTales$bannerTexture;
	@Unique
	private BoatBannerModel trailierTales$boatBannerModel;
	@Unique
	private boolean trailierTales$raft;

	protected AbstractBoatRendererMixin(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$init(EntityRendererProvider.Context context, CallbackInfo info) {
		this.trailierTales$boatBannerModel = new BoatBannerModel(context.bakeLayer(TTModelLayers.BOAT_BANNER));
	}

	@Unique
	@Override
	public void trailierTales$setBannerBaseTexture(ResourceLocation texture) {
		this.trailierTales$bannerTexture = texture;
	}

	@Unique
	@Override
	public ResourceLocation trailierTales$getBannerBaseTexture() {
		return this.trailierTales$bannerTexture;
	}

	@Unique
	@Override
	public void trailierTales$setRaft(boolean raft) {
		this.trailierTales$raft = raft;
	}

	@Inject(
		method = "extractRenderState(Lnet/minecraft/world/entity/vehicle/AbstractBoat;Lnet/minecraft/client/renderer/entity/state/BoatRenderState;F)V",
		at = @At("TAIL")
	)
	public void trailierTales$extractRenderState(AbstractBoat abstractBoat, BoatRenderState boatRenderState, float f, CallbackInfo info) {
		if (abstractBoat instanceof BoatBannerInterface bannerInterface && boatRenderState instanceof BoatRenderStateInterface boatRenderStateInterface) {
			WalkAnimationState walkAnimationState = bannerInterface.trailierTales$getWalkAnimationState();
			boatRenderStateInterface.trailierTales$setWalkAnimationPos(walkAnimationState.position(f));
			boatRenderStateInterface.trailierTales$setWalkAnimationSpeed(walkAnimationState.speed(f));
			boatRenderStateInterface.trailierTales$setBanner(bannerInterface.trailierTales$getBanner());
		}
	}

	@Inject(
		method = "render(Lnet/minecraft/client/renderer/entity/state/BoatRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V",
			shift = At.Shift.AFTER
		)
	)
	public void trailierTales$renderBoatBanner(
		BoatRenderState boatRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo info
	) {
		if (boatRenderState instanceof BoatRenderStateInterface boatRenderStateInterface) {
			ItemStack stack = boatRenderStateInterface.trailierTales$getBanner();
			if (!stack.isEmpty() && stack.getItem() instanceof BannerItem bannerItem) {
				poseStack.pushPose();
				this.trailierTales$boatBannerModel.setRaft(this.trailierTales$raft);
				this.trailierTales$boatBannerModel.beforeRender(poseStack);
				this.trailierTales$boatBannerModel.setupAnim(boatRenderState);
				this.trailierTales$boatBannerModel.renderToBuffer(
					poseStack,
					multiBufferSource.getBuffer(
						this.trailierTales$boatBannerModel.renderType(
							this.trailierTales$bannerTexture
						)
					),
					light,
					OverlayTexture.NO_OVERLAY
				);
				this.trailierTales$boatBannerModel.renderFlag(
					poseStack,
					multiBufferSource,
					light,
					OverlayTexture.NO_OVERLAY,
					bannerItem.getColor(),
					stack.getComponents().get(DataComponents.BANNER_PATTERNS)
				);
				this.trailierTales$boatBannerModel.afterRender(poseStack);
				poseStack.popPose();
			}
		}
	}
}
