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

package net.frozenblock.trailiertales.client.model.object.boat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.frozenblock.trailiertales.client.TTRenderStateDataKeys;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public class BoatBannerModel extends EntityModel<BoatRenderState> {
	private final ModelPart flag;
	private final ModelPart pole;
	private final ModelPart bar;

	private boolean raft = false;

	public BoatBannerModel(ModelPart root) {
		super(root, RenderTypes::entityCutout);
		this.flag = root.getChild("flag");
		this.pole = root.getChild("pole");
		this.bar = root.getChild("bar");
	}

	public static LayerDefinition createFlagLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("flag", CubeListBuilder.create().texOffs(0, 0).addBox(-10F, 0F, -2F, 20F, 40F, 1F), PartPose.ZERO);
		root.addOrReplaceChild("pole", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("bar", CubeListBuilder.create(), PartPose.ZERO);
		return LayerDefinition.create(mesh, 64, 64);
	}

	public static LayerDefinition createStandLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("flag", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(44, 0).addBox(-1F, -30F, -1F, 2F, 42F, 2F), PartPose.ZERO);
		root.addOrReplaceChild("bar", CubeListBuilder.create().texOffs(0, 42).addBox(-10F, -32F, -1F, 20F, 2F, 2F), PartPose.ZERO);
		return LayerDefinition.create(mesh, 64, 64);
	}

	public void setRaft(boolean raft) {
		this.raft = raft;
	}

	@Override
	public void setupAnim(BoatRenderState renderState) {
		super.setupAnim(renderState);
		final float walkAnimationSpeed = renderState.getDataOrDefault(TTRenderStateDataKeys.BOAT_WALK_ANIMATION_SPEED, 0F);
		final float walkAnimationPos = renderState.getDataOrDefault(TTRenderStateDataKeys.BOAT_WALK_ANIMATION_POS, 0F);
		this.flag.xRot = (-0.0125F + 0.01F * Mth.cos(Mth.TWO_PI * renderState.ageInTicks / 100F)) * Mth.PI;
		this.flag.xRot -= (walkAnimationSpeed * (90F / 180F)) * Mth.PI;
		this.flag.xRot -= (Mth.cos(walkAnimationPos * 0.4F) + 1F) * 0.1F * Math.min(walkAnimationSpeed * 2F, 1F);
		this.flag.y = -32F;
	}

	public void preparePoseStack(PoseStack poseStack) {
		poseStack.pushPose();
		poseStack.translate(
			this.raft ? -0.74F : -0.94F,
			this.raft ? -0.2F : -0.25F,
			0F
		);

		poseStack.mulPose(Axis.YN.rotationDegrees(-Direction.WEST.toYRot()));
		poseStack.translate(0F, -0.3125F, 0F);
		poseStack.mulPose(Axis.XP.rotation(Mth.PI));

		poseStack.pushPose();
		poseStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
	}

	public void popPoseStack(PoseStack matrices) {
		matrices.popPose();
		matrices.popPose();
		this.raft = false;
	}

	public void submitFlag(
		SpriteGetter sprites,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		BoatRenderState renderState,
		int overlayCoords,
		DyeColor dyeColor,
		BannerPatternLayers bannerPatternLayers
	) {
		BannerRenderer.submitPatterns(
			sprites,
			poseStack,
			collector,
			renderState.lightCoords,
			overlayCoords,
			this,
			renderState,
			true,
			dyeColor,
			bannerPatternLayers,
			null
		);
	}

	public void submitStand(
		PoseStack poseStack,
		SubmitNodeCollector collector,
		BoatRenderState renderState,
		int overlayCoords,
		Identifier standTexture
	) {
		collector.submitModel(this, renderState, poseStack, RenderTypes.entityCutout(standTexture), renderState.lightCoords, overlayCoords, 0, null);
	}
}
