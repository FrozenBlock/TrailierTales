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

package net.frozenblock.trailiertales.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.ApparitionModel;
import net.frozenblock.trailiertales.client.renderer.entity.layers.ApparitionOverlayLayer;
import net.frozenblock.trailiertales.entity.Apparition;
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
public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionModel<Apparition>> {
	private final ItemRenderer itemRenderer;
	private float itemYaw;

	private static final ResourceLocation TEXTURE = TTConstants.id("textures/entity/apparition/apparition.png");
	private static final ResourceLocation HYPNOTIZING_TEXTURE = TTConstants.id("textures/entity/apparition/apparition_hypnotizing.png");
	private static final ResourceLocation SHOOTING_TEXTURE = TTConstants.id("textures/entity/apparition/apparition_shooting.png");

	public ApparitionRenderer(EntityRendererProvider.Context context) {
		super(context, new ApparitionModel<>(context.bakeLayer(TTModelLayers.APPARITION)), 0.5F);
		this.addLayer(new ApparitionOverlayLayer<>(
			context,
			this,
			Apparition::getInnerTransparency,
			Apparition::getOuterTransparency,
			ApparitionModel::getInnerParts,
			TEXTURE,
			false
		));
		this.addLayers(
			context,
			(apparition, tickDelta) -> apparition.getAidAnimProgress(tickDelta) * 0.8F,
			HYPNOTIZING_TEXTURE
		);
		this.addLayers(
			context,
			(apparition, tickDelta) -> apparition.getPoltergeistAnimProgress(tickDelta) * 0.8F,
			SHOOTING_TEXTURE
		);
		this.itemRenderer = context.getItemRenderer();
	}

	private void addLayers(
		EntityRendererProvider.Context context,
		ApparitionModel.AlphaFunction<Apparition> innerFunction,
		ApparitionModel.AlphaFunction<Apparition> outerFunction,
		ResourceLocation textureLocation
	) {
		this.addLayer(new ApparitionOverlayLayer<>(
			context,
			this,
			innerFunction,
			outerFunction,
			ApparitionModel::getOuterParts,
			textureLocation,
			true
		));
		this.addLayer(new ApparitionOverlayLayer<>(
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
		ApparitionModel.AlphaFunction<Apparition> alphaFunction,
		ResourceLocation textureLocation
	) {
		this.addLayers(context, alphaFunction, alphaFunction, textureLocation);
	}

	@Override
	public void render(@NotNull Apparition entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
		ItemStack stack = entity.getVisibleItem();
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0F, 0.425F, 0F);
			poseStack.mulPose(Axis.YP.rotationDegrees(180F - this.itemYaw));
			poseStack.mulPose(Axis.YN.rotation(entity.getItemYRot(partialTick)));
			poseStack.mulPose(Axis.ZN.rotation(entity.getItemZRot(partialTick)));
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, 1);
			poseStack.popPose();
		}
	}

	@Override
	protected void setupRotations(Apparition entity, PoseStack matrices, float animationProgress, float bodyYaw, float tickDelta, float f) {
		super.setupRotations(entity, matrices, animationProgress, bodyYaw, tickDelta, f);
		this.itemYaw = bodyYaw;
		this.shadowStrength = entity.totalTransparency(tickDelta);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(Apparition entity) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(Apparition entity, BlockPos pos) {
		return LightEngine.MAX_LEVEL;
	}
}
