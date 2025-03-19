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

package net.frozenblock.trailiertales.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.frozenblock.trailiertales.impl.client.BoatRenderStateInterface;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
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
		super(root, RenderType::entitySolid);
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
	public void setupAnim(BoatRenderState renderState) {
		super.setupAnim(renderState);
		if (renderState instanceof BoatRenderStateInterface boatRenderStateInterface) {
			this.flag.xRot = (-0.0125F + 0.01F * Mth.cos((Mth.PI * 2F) * renderState.ageInTicks / 100F)) * Mth.PI;
			this.flag.xRot -= (boatRenderStateInterface.trailierTales$getWalkAnimationSpeed() * (90F / 180F)) * Mth.PI;
			this.flag.xRot -= (Mth.cos(boatRenderStateInterface.trailierTales$getWalkAnimationPos() * 0.4F) + 1F) * 0.1F * Math.min(boatRenderStateInterface.trailierTales$getWalkAnimationSpeed() * 2F, 1F);
			this.flag.y = -32F;
		}
	}

	public void beforeRender(@NotNull PoseStack matrices) {
		matrices.pushPose();
		matrices.translate(
			this.raft ? -0.74F : -0.94F,
			this.raft ? -0.2F : -0.25F,
			0F
		);

		float h = -Direction.WEST.toYRot();
		matrices.mulPose(Axis.YN.rotationDegrees(h));
		matrices.translate(0F, -0.3125F, 0F);
		matrices.mulPose(Axis.XP.rotation(Mth.PI));

		matrices.pushPose();
		matrices.scale(0.6666667F, -0.6666667F, -0.6666667F);
	}

	public void afterRender(@NotNull PoseStack matrices) {
		matrices.popPose();
		matrices.popPose();
		this.raft = false;
	}

	public void renderFlag(
		@NotNull PoseStack poseStack,
		MultiBufferSource multiBufferSource,
		int light,
		int overlay,
		DyeColor dyeColor,
		BannerPatternLayers bannerPatternLayers
	) {
		BannerRenderer.renderPatterns(poseStack, multiBufferSource, light, overlay, this.flag, ModelBakery.BANNER_BASE, true, dyeColor, bannerPatternLayers);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack matrices, VertexConsumer vertexConsumer, int i, int j, int k) {
		this.pole.render(matrices, vertexConsumer, i, j);
		this.bar.render(matrices, vertexConsumer, i, j);
	}
}
