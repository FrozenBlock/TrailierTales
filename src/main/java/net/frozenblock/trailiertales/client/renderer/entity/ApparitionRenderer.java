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
import net.frozenblock.trailiertales.client.renderer.entity.layers.ApparitionLayer;
import net.frozenblock.trailiertales.client.renderer.entity.state.ApparitionRenderState;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionRenderState, ApparitionModel> {
	private static final ResourceLocation TEXTURE = TTConstants.id("textures/entity/apparition/apparition.png");
	private static final ResourceLocation HYPNOTIZING_TEXTURE = TTConstants.id("textures/entity/apparition/apparition_hypnotizing.png");
	private static final ResourceLocation SHOOTING_TEXTURE = TTConstants.id("textures/entity/apparition/apparition_shooting.png");
	private final ItemModelResolver itemModelResolver;
	private float itemYaw;

	public ApparitionRenderer(EntityRendererProvider.Context context) {
		super(context, new ApparitionModel(context.bakeLayer(TTModelLayers.APPARITION)), 0.5F);
		this.addLayer(new ApparitionLayer(
			this,
			renderState -> renderState.innerTransparency,
			renderState -> renderState.outerTransparency,
			TEXTURE,
			0
		));

		final ApparitionModel.AlphaFunction<ApparitionRenderState> aidAlpha = renderState -> renderState.aidAnimProgress * 0.8F;
		this.addLayer(new ApparitionLayer(
			this,
			aidAlpha,
			aidAlpha,
			HYPNOTIZING_TEXTURE,
			1
		));

		final ApparitionModel.AlphaFunction<ApparitionRenderState> poltergeistAlpha = renderState -> renderState.poltergeistAnimProgress * 0.8F;
		this.addLayer(new ApparitionLayer(
			this,
			poltergeistAlpha,
			poltergeistAlpha,
			SHOOTING_TEXTURE,
			2
		));

		this.itemModelResolver = context.getItemModelResolver();
	}

	@Override
	public void submit(ApparitionRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
		super.submit(renderState, poseStack, submitNodeCollector);
		if (!renderState.item.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0F, 0.425F, 0F);
			poseStack.mulPose(Axis.YP.rotationDegrees(180F - this.itemYaw));
			poseStack.mulPose(Axis.YN.rotation(renderState.itemYRot));
			poseStack.mulPose(Axis.ZN.rotation(renderState.itemZRot));
			renderState.item.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
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
	protected @Nullable RenderType getRenderType(ApparitionRenderState renderState, boolean bl, boolean bl2, boolean bl3) {
		return null;
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
		renderState.lightCoords = 15728640;
		renderState.itemYRot = apparition.getItemYRot(partialTick);
		renderState.itemZRot = apparition.getItemZRot(partialTick);
		renderState.totalTransparency = apparition.totalTransparency(partialTick);
		renderState.innerTransparency = apparition.getInnerTransparency(partialTick);
		renderState.outerTransparency = apparition.getOuterTransparency(partialTick);
		renderState.flicker = apparition.getFlicker(partialTick);

		this.itemModelResolver.updateForLiving(renderState.item, apparition.getVisibleItem(), ItemDisplayContext.GROUND, apparition);
		renderState.aidAnimProgress = apparition.getAidAnimProgress(partialTick);
		renderState.poltergeistAnimProgress = apparition.getPoltergeistAnimProgress(partialTick);
	}
}
