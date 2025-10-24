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

package net.frozenblock.trailiertales.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.frozenblock.trailiertales.impl.client.BoatRenderStateInterface;
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
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;

public class BoatBannerModel extends EntityModel<BoatRenderState> {
	private final ModelPart flag;
	private final ModelPart pole;
	private final ModelPart bar;

	private boolean raft = false;

	public BoatBannerModel(@NotNull ModelPart root) {
		super(root, RenderTypes::entitySolid);
		this.flag = root.getChild("flag");
		this.pole = root.getChild("pole");
		this.bar = root.getChild("bar");
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild("flag", CubeListBuilder.create().texOffs(0, 0).addBox(-10F, 0F, -2F, 20F, 40F, 1F), PartPose.ZERO);
		partDefinition.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(44, 0).addBox(-1F, -30F, -1F, 2F, 42F, 2F), PartPose.ZERO);
		partDefinition.addOrReplaceChild("bar", CubeListBuilder.create().texOffs(0, 42).addBox(-10F, -32F, -1F, 20F, 2F, 2F), PartPose.ZERO);
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	public void setRaft(boolean raft) {
		this.raft = raft;
	}

	@Override
	public void setupAnim(@NotNull BoatRenderState renderState) {
		super.setupAnim(renderState);
		if (!(renderState instanceof BoatRenderStateInterface stateInterface)) return;
		this.flag.xRot = (-0.0125F + 0.01F * Mth.cos(Mth.TWO_PI * renderState.ageInTicks / 100F)) * Mth.PI;
		this.flag.xRot -= (stateInterface.trailierTales$getWalkAnimationSpeed() * (90F / 180F)) * Mth.PI;
		this.flag.xRot -= (Mth.cos(stateInterface.trailierTales$getWalkAnimationPos() * 0.4F) + 1F) * 0.1F * Math.min(stateInterface.trailierTales$getWalkAnimationSpeed() * 2F, 1F);
		this.flag.y = -32F;
	}

	public void preparePoseStack(@NotNull PoseStack poseStack) {
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

	public void popPoseStack(@NotNull PoseStack matrices) {
		matrices.popPose();
		matrices.popPose();
		this.raft = false;
	}

	public void submitFlag(
		MaterialSet materials,
		@NotNull PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		BoatRenderState renderState,
		int overlay,
		DyeColor dyeColor,
		BannerPatternLayers bannerPatternLayers
	) {
		BannerRenderer.submitPatterns(
			materials,
			poseStack,
			submitNodeCollector,
			renderState.lightCoords,
			overlay,
			this,
			renderState,
			ModelBakery.BANNER_BASE,
			true,
			dyeColor,
			bannerPatternLayers,
			false,
			null,
			renderState.outlineColor
		);
	}
}
