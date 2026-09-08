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

package net.frozenblock.trailiertales.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.monster.apparition.ApparitionModel;
import net.frozenblock.trailiertales.client.renderer.entity.layers.ApparitionLayer;
import net.frozenblock.trailiertales.client.renderer.entity.state.ApparitionRenderState;
import net.frozenblock.trailiertales.entity.Apparition;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.Nullable;

@ClientOnly
public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionRenderState, ApparitionModel> {
	private static final Identifier TEXTURE = TTConstants.id("textures/entity/apparition/apparition.png");
	private static final Identifier HYPNOTIZING_TEXTURE = TTConstants.id("textures/entity/apparition/apparition_hypnotizing.png");
	private static final Identifier SHOOTING_TEXTURE = TTConstants.id("textures/entity/apparition/apparition_shooting.png");
	private final ItemModelResolver itemModelResolver;
	private float itemYaw;

	public ApparitionRenderer(EntityRendererProvider.Context context) {
		super(context, new ApparitionModel(context.bakeLayer(TTModelLayers.APPARITION)), 0.5F);
		this.addLayer(new ApparitionLayer(
			this,
			state -> state.innerTransparency,
			state -> state.outerTransparency,
			TEXTURE,
			0
		));

		final ApparitionModel.AlphaFunction<ApparitionRenderState> aidAlpha = state -> state.aidAnimProgress * 0.8F;
		this.addLayer(new ApparitionLayer(
			this,
			aidAlpha,
			aidAlpha,
			HYPNOTIZING_TEXTURE,
			1
		));

		final ApparitionModel.AlphaFunction<ApparitionRenderState> poltergeistAlpha = state -> state.poltergeistAnimProgress * 0.8F;
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
	public void submit(ApparitionRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		super.submit(state, poseStack, submitNodeCollector, camera);

		if (state.item.isEmpty()) return;
		poseStack.pushPose();
		poseStack.translate(0F, 0.425F, 0F);
		poseStack.rotate(Axis.YP.rotationDegrees(180F - this.itemYaw));
		poseStack.rotate(Axis.YN.rotation(state.itemYRot));
		poseStack.rotate(Axis.ZN.rotation(state.itemZRot));
		state.item.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
		poseStack.popPose();
	}

	@Override
	protected void setupRotations(ApparitionRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
		super.setupRotations(state, poseStack, bodyRot, entityScale);
		this.itemYaw = bodyRot;
		this.shadowStrength = state.totalTransparency;
	}

	@Override
	@Nullable
	protected RenderType getRenderType(ApparitionRenderState state, boolean isBodyVisible, boolean forceTransparent, boolean appearGlowing) {
		return null;
	}

	@Override
	public Identifier getTextureLocation(ApparitionRenderState state) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(Apparition entity, BlockPos pos) {
		return LightEngine.MAX_LEVEL;
	}

	@Override
	public ApparitionRenderState createRenderState() {
		return new ApparitionRenderState();
	}

	@Override
	public void extractRenderState(Apparition entity, ApparitionRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.itemYRot = getItemYRot(state.ageInTicks);
		state.itemZRot = getItemZRot(state.ageInTicks);

		final Minecraft minecraft = Minecraft.getInstance();
		final Player player = minecraft.player;
		final MobEffectInstance nightVision = player != null ? player.getEffect(MobEffects.NIGHT_VISION) : null;
		final float nightVisionBlend = nightVision != null ? nightVision.getBlendFactor(player, partialTicks) : 0F;
		state.totalTransparency = entity.totalTransparency(nightVisionBlend, partialTicks);
		state.innerTransparency = entity.getInnerTransparency(nightVisionBlend, partialTicks);
		state.outerTransparency = entity.getOuterTransparency(nightVisionBlend, partialTicks);
		state.flicker = entity.getFlicker(partialTicks);

		this.itemModelResolver.updateForLiving(state.item, entity.getItemBySlot(EquipmentSlot.MAINHAND), ItemDisplayContext.GROUND, entity);
		state.aidAnimProgress = entity.getAidAnimProgress(partialTicks);
		state.poltergeistAnimProgress = entity.getPoltergeistAnimProgress(partialTicks);
	}

	private static float getItemYRot(float ageInTicks) {
		return Mth.cos((ageInTicks) / 8F) * 0.35F;
	}

	private static float getItemZRot(float ageInTicks) {
		return Mth.sin((ageInTicks) / 8F) * 0.35F;
	}
}
