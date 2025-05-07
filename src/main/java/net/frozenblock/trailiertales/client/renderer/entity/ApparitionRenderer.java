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

package net.frozenblock.trailiertales.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.ApparitionModel;
import net.frozenblock.trailiertales.client.renderer.entity.layers.ApparitionOverlayLayer;
import net.frozenblock.trailiertales.client.renderer.entity.state.ApparitionRenderState;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionRenderState, ApparitionModel> {
	private final ItemRenderer itemRenderer;
	private float itemYaw;

	private static final ResourceLocation TEXTURE = TTConstants.id("textures/entity/apparition/apparition.png");
	private static final ResourceLocation HYPNOTIZING_TEXTURE = TTConstants.id("textures/entity/apparition/apparition_hypnotizing.png");
	private static final ResourceLocation SHOOTING_TEXTURE = TTConstants.id("textures/entity/apparition/apparition_shooting.png");

	public ApparitionRenderer(EntityRendererProvider.Context context) {
		super(context, new ApparitionModel(context.bakeLayer(TTModelLayers.APPARITION)), 0.5F);
		this.addLayer(new ApparitionOverlayLayer(
			context,
			this,
			renderState -> renderState.innerTransparency,
			renderState -> renderState.outerTransparency,
			ApparitionModel::getInnerParts,
			TEXTURE,
			false
		));
		this.addLayers(
			context,
			renderState -> renderState.aidAnimProgress * 0.8F,
			HYPNOTIZING_TEXTURE
		);
		this.addLayers(
			context,
			renderState -> renderState.poltergeistAnimProgress * 0.8F,
			SHOOTING_TEXTURE
		);
		this.itemRenderer = Minecraft.getInstance().getItemRenderer();
	}

	private void addLayers(
		EntityRendererProvider.Context context,
		ApparitionModel.AlphaFunction<ApparitionRenderState> innerFunction,
		ApparitionModel.AlphaFunction<ApparitionRenderState> outerFunction,
		ResourceLocation textureLocation
	) {
		this.addLayer(new ApparitionOverlayLayer(
			context,
			this,
			innerFunction,
			outerFunction,
			ApparitionModel::getOuterParts,
			textureLocation,
			true
		));
		this.addLayer(new ApparitionOverlayLayer(
			context,
			this,
			innerFunction,
			outerFunction,
			ApparitionModel::getInnerParts,
			textureLocation,
			false
		));
	}

	private void addLayers(
		EntityRendererProvider.Context context,
		ApparitionModel.AlphaFunction<ApparitionRenderState> alphaFunction,
		ResourceLocation textureLocation
	) {
		this.addLayers(context, alphaFunction, alphaFunction, textureLocation);
	}

	@Override
	public void render(@NotNull ApparitionRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
		super.render(renderState, poseStack, buffer, packedLight);
		ItemStack stack = renderState.visibleItem;
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0F, 0.425F, 0F);
			poseStack.mulPose(Axis.YP.rotationDegrees(180F - this.itemYaw));
			poseStack.mulPose(Axis.YN.rotation(renderState.itemYRot));
			poseStack.mulPose(Axis.ZN.rotation(renderState.itemZRot));
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, 1);
			poseStack.popPose();
		}
	}

	@Override
	protected void setupRotations(ApparitionRenderState renderState, PoseStack matrices, float bodyYaw, float scale) {
		super.setupRotations(renderState, matrices, bodyYaw, scale);
		this.itemYaw = bodyYaw;
		this.shadowStrength = renderState.totalTransparency;
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(ApparitionRenderState renderState) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(Apparition entity, BlockPos pos) {
		return LightEngine.MAX_LEVEL;
	}

	@Override
	@NotNull
	public ApparitionRenderState createRenderState() {
		return new ApparitionRenderState();
	}

	@Override
	public void extractRenderState(Apparition apparition, ApparitionRenderState renderState, float partialTick) {
		super.extractRenderState(apparition, renderState, partialTick);
		renderState.itemYRot = apparition.getItemYRot(partialTick);
		renderState.itemZRot = apparition.getItemZRot(partialTick);
		renderState.totalTransparency = apparition.totalTransparency(partialTick);
		renderState.innerTransparency = apparition.getInnerTransparency(partialTick);
		renderState.outerTransparency = apparition.getOuterTransparency(partialTick);
		renderState.flicker = apparition.getFlicker(partialTick);

		renderState.visibleItem = apparition.getVisibleItem();
		renderState.aidAnimProgress = apparition.getAidAnimProgress(partialTick);
		renderState.poltergeistAnimProgress = apparition.getPoltergeistAnimProgress(partialTick);
	}
}
