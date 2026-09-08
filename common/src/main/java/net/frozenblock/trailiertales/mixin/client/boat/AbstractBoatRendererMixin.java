/*
 * Copyright 2025-2026 FrozenBlock
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
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.TTRenderStateDataKeys;
import net.frozenblock.trailiertales.client.model.object.boat.BoatBannerModel;
import net.frozenblock.trailiertales.client.renderer.impl.AbstractBoatRendererInterface;
import net.frozenblock.trailiertales.entity.impl.BoatBannerInterface;
import net.frozenblock.trailiertales.registry.TTAttachmentTypes;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(AbstractBoatRenderer.class)
public abstract class AbstractBoatRendererMixin extends EntityRenderer<AbstractBoat, BoatRenderState> implements AbstractBoatRendererInterface {

	@Shadow
	protected abstract EntityModel<BoatRenderState> model();

	@Unique
	private SpriteGetter trailierTales$sprites;
	@Unique
	private Identifier trailierTales$bannerTexture;
	@Unique
	private BoatBannerModel trailierTales$boatBannerFlagModel;
	@Unique
	private BoatBannerModel trailierTales$boatBannerStandModel;
	@Unique
	private boolean trailierTales$raft;

	protected AbstractBoatRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$init(EntityRendererProvider.Context context, Identifier texture, CallbackInfo info) {
		this.trailierTales$sprites = context.getSprites();
		this.trailierTales$boatBannerFlagModel = new BoatBannerModel(context.bakeLayer(TTModelLayers.BOAT_BANNER_FLAG));
		this.trailierTales$boatBannerStandModel = new BoatBannerModel(context.bakeLayer(TTModelLayers.BOAT_BANNER_STAND));
	}

	@Unique
	@Override
	public void trailierTales$setBannerBaseTexture(Identifier texture) {
		this.trailierTales$bannerTexture = texture;
	}

	@Unique
	@Override
	public Identifier trailierTales$getBannerBaseTexture() {
		return this.trailierTales$bannerTexture;
	}

	@Unique
	@Override
	public void trailierTales$setRaft(boolean raft) {
		this.trailierTales$raft = raft;
	}

	@Inject(
		method = "extractRenderState(Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat;Lnet/minecraft/client/renderer/entity/state/BoatRenderState;F)V",
		at = @At("TAIL")
	)
	public void trailierTales$extractRenderState(AbstractBoat entity, BoatRenderState state, float partialTicks, CallbackInfo info) {
		if (!(entity instanceof BoatBannerInterface bannerInterface)) return;
		final WalkAnimationState walkAnimationState = bannerInterface.trailierTales$getWalkAnimationState();

		state.frozenLib$setData(TTRenderStateDataKeys.BOAT_WALK_ANIMATION_POS, walkAnimationState.position(partialTicks));
		state.frozenLib$setData(TTRenderStateDataKeys.BOAT_WALK_ANIMATION_SPEED, walkAnimationState.speed(partialTicks));

		final ItemStack bannerStack = entity.frozenLib$getAttached(TTAttachmentTypes.BOAT_BANNER);
		if (bannerStack == null || bannerStack.isEmpty() || !(bannerStack.getItem() instanceof BannerItem bannerItem)) {
			state.frozenLib$setData(TTRenderStateDataKeys.BOAT_BANNER_BASE_COLOR, null);
			state.frozenLib$setData(TTRenderStateDataKeys.BOAT_BANNER_PATTERNS, null);
		} else {
			state.frozenLib$setData(TTRenderStateDataKeys.BOAT_BANNER_BASE_COLOR, bannerItem.getColor());
			state.frozenLib$setData(TTRenderStateDataKeys.BOAT_BANNER_PATTERNS, bannerStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
		}
	}

	@Inject(
		method = "submit(Lnet/minecraft/client/renderer/entity/state/BoatRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;III)V",
			shift = At.Shift.AFTER
		)
	)
	public void trailierTales$renderBoatBanner(
		BoatRenderState state,
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CameraRenderState camera,
		CallbackInfo info
	) {
		final DyeColor baseColor = state.frozenLib$getData(TTRenderStateDataKeys.BOAT_BANNER_BASE_COLOR);
		final BannerPatternLayers patterns = state.frozenLib$getData(TTRenderStateDataKeys.BOAT_BANNER_PATTERNS);
		if (baseColor == null || patterns == null) return;

		poseStack.pushPose();
		this.trailierTales$boatBannerFlagModel.setRaft(this.trailierTales$raft);
		this.trailierTales$boatBannerFlagModel.preparePoseStack(poseStack);
		this.trailierTales$boatBannerFlagModel.setupAnim(state);
		this.trailierTales$boatBannerFlagModel.submitFlag(
			this.trailierTales$sprites,
			poseStack,
			submitNodeCollector,
			state,
			OverlayTexture.NO_OVERLAY,
			baseColor,
			patterns
		);
		this.trailierTales$boatBannerFlagModel.popPoseStack(poseStack);
		poseStack.popPose();

		poseStack.pushPose();
		this.trailierTales$boatBannerStandModel.setRaft(this.trailierTales$raft);
		this.trailierTales$boatBannerStandModel.preparePoseStack(poseStack);
		this.trailierTales$boatBannerStandModel.setupAnim(state);
		this.trailierTales$boatBannerStandModel.submitStand(
			poseStack,
			submitNodeCollector,
			state,
			OverlayTexture.NO_OVERLAY,
			this.trailierTales$getBannerBaseTexture()
		);
		this.trailierTales$boatBannerStandModel.popPoseStack(poseStack);
		poseStack.popPose();
	}
}
