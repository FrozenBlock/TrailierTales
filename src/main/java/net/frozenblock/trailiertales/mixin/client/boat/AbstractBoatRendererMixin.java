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
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.MaterialSet;
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
	private MaterialSet materials;

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
		this.materials = context.getMaterials();
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
	public void trailierTales$extractRenderState(AbstractBoat abstractBoat, BoatRenderState renderState, float partialTick, CallbackInfo info) {
		if (!(abstractBoat instanceof BoatBannerInterface bannerInterface && renderState instanceof BoatRenderStateInterface stateInterface)) return;
		final WalkAnimationState walkAnimationState = bannerInterface.trailierTales$getWalkAnimationState();
		stateInterface.trailierTales$setWalkAnimationPos(walkAnimationState.position(partialTick));
		stateInterface.trailierTales$setWalkAnimationSpeed(walkAnimationState.speed(partialTick));
		stateInterface.trailierTales$setBanner(bannerInterface.trailierTales$getBanner());
	}

	@Inject(
		method = "submit(Lnet/minecraft/client/renderer/entity/state/BoatRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
			shift = At.Shift.AFTER
		)
	)
	public void trailierTales$renderBoatBanner(
		BoatRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CameraRenderState cameraRenderState,
		CallbackInfo info
	) {
		if (!(renderState instanceof BoatRenderStateInterface stateInterface)) return;
		final ItemStack stack = stateInterface.trailierTales$getBanner();
		if (!(!stack.isEmpty() && stack.getItem() instanceof BannerItem bannerItem)) return;

		poseStack.pushPose();
		this.trailierTales$boatBannerModel.setRaft(this.trailierTales$raft);
		this.trailierTales$boatBannerModel.preparePoseStack(poseStack);
		this.trailierTales$boatBannerModel.setupAnim(renderState);
		this.trailierTales$boatBannerModel.submitFlag(
			this.materials,
			poseStack,
			submitNodeCollector,
			renderState,
			OverlayTexture.NO_OVERLAY,
			bannerItem.getColor(),
			stack.getComponents().get(DataComponents.BANNER_PATTERNS)
		);
		this.trailierTales$boatBannerModel.popPoseStack(poseStack);
		poseStack.popPose();
	}
}
