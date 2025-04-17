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

package net.frozenblock.trailiertales.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.impl.client.rendering.ModelPartInvertInterface;
import net.frozenblock.trailiertales.client.renderer.entity.state.ApparitionRenderState;
import net.frozenblock.lib.render.FrozenLibRenderTypes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ApparitionModel extends EntityModel<ApparitionRenderState> {
	private final ModelPart root;
	public final ModelPart core;
	public final ModelPart inner;
	public final ModelPart outline;
	public final ModelPart outer;
	private final AlphaFunction<ApparitionRenderState> innerAlphaFunction;
	private final AlphaFunction<ApparitionRenderState> outerAlphaFunction;
	private final DrawSelector<ApparitionRenderState, ApparitionModel> drawSelector;
	private final List<ModelPart> innerParts;
	private final List<ModelPart> outerParts;

	private float innerTransparency;
	private float outerTransparency;
	private float flicker;

	public ApparitionModel(@NotNull ModelPart root) {
		this(
			FrozenLibRenderTypes::apparitionOuter,
			root,
			renderState -> renderState.innerTransparency,
			renderState -> renderState.outerTransparency,
			ApparitionModel::getOuterParts
		);
	}

	public ApparitionModel(
		Function<ResourceLocation, RenderType> function,
		@NotNull ModelPart root,
		AlphaFunction<ApparitionRenderState> innerAlpha,
		AlphaFunction<ApparitionRenderState> outerAlpha,
		DrawSelector<ApparitionRenderState, ApparitionModel> drawSelector
	) {
		super(root, function);
		this.root = root;
		this.core = root.getChild("core");
		this.inner = this.core.getChild("inner");
		this.outline = this.core.getChild("outline");
		this.outer = root.getChild("outer");

		this.innerAlphaFunction = innerAlpha;
		this.outerAlphaFunction = outerAlpha;
		this.drawSelector = drawSelector;
		this.innerParts = ImmutableList.of(this.inner);
		this.outerParts = ImmutableList.of(this.outline, this.outer);
	}

	public List<ModelPart> getOuterParts() {
		return this.outerParts;
	}

	public List<ModelPart> getInnerParts() {
		return this.innerParts;
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();

		PartDefinition core = meshdefinition.getRoot().addOrReplaceChild("core", CubeListBuilder.create(), PartPose.ZERO);

		core.addOrReplaceChild("inner", CubeListBuilder.create()
				.texOffs(0, 28)
				.addBox(-5F, -5F, -5F, 10F, 10F, 10F),
			PartPose.offset(0F, 17F, 0F)
		);
		PartDefinition outline = core.addOrReplaceChild("outline", CubeListBuilder.create()
				.texOffs(0, 48)
				.addBox(-5.5F, -5.5F, -5.5F, 11F, 11F, 11F)
				.mirror(),
			PartPose.offset(0F, 17F, 0F)
		);
		ModelPartInvertInterface.class.cast(outline).frozenLib$setInverted();

		meshdefinition.getRoot().addOrReplaceChild("outer", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-7F, -7F, -7F, 14F, 14F, 14F),
			PartPose.offset(0F, 17F, 0F)
		);

		return LayerDefinition.create(meshdefinition, 80, 80);
	}

	@Override
	public void setupAnim(@NotNull ApparitionRenderState renderState) {
		float limbAngle = renderState.walkAnimationPos;
		float limbDistance = renderState.walkAnimationSpeed;
		float headYaw = renderState.yRot;
		float headPitch = renderState.xRot;
		this.innerTransparency = this.innerAlphaFunction.apply(renderState);
		this.outerTransparency = this.outerAlphaFunction.apply(renderState);
		this.flicker = renderState.flicker;
		this.outer.yRot = renderState.itemYRot;
		this.outer.zRot = renderState.itemZRot;

		float animationProgress = renderState.ageInTicks + (limbAngle * 3.5F);
		this.core.yRot = headYaw * ((float) Math.PI / 180F);
		this.core.xRot = headPitch * ((float) Math.PI / 180F);

		float tighten = 1F - limbDistance * 0.75F;
		this.outer.yRot *= tighten;
		this.outer.zRot *= tighten;

		//SQUASH & STRETCH
		float sinIdle = (float) (Math.sin(animationProgress * 0.3F) * 0.1F) * tighten;
		float squash = sinIdle + 1F;

		this.outer.xScale = squash;
		this.outer.zScale = squash;
		this.outer.yScale = -sinIdle + 1;
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int colorBad) {
		poseStack.pushPose();
		this.onlyDrawSelectedParts();

		int innerTransparency = ARGB.colorFromFloat(this.innerTransparency * this.flicker, 1F, 1F, 1F);
		this.inner.render(poseStack, buffer, 15728640, packedOverlay, innerTransparency);

		int outlineTransparency = ARGB.colorFromFloat(this.innerTransparency * 0.7F * this.flicker, 1F, 1F, 1F);
		this.outline.render(poseStack, buffer, 15728640, packedOverlay, outlineTransparency);

		int outerTransparency = ARGB.colorFromFloat(this.outerTransparency * this.flicker, 1F, 1F, 1F);
		this.outer.render(poseStack, buffer, 15728640, packedOverlay, outerTransparency);

		this.resetDrawForAllParts();
		poseStack.popPose();
	}

	private void onlyDrawSelectedParts() {
		this.root().getAllParts().forEach(modelPart -> modelPart.skipDraw = true);
		this.drawSelector.getPartsToDraw(this).forEach(modelPart -> modelPart.skipDraw = false);
	}

	private void resetDrawForAllParts() {
		this.root().getAllParts().forEach(modelPart -> modelPart.skipDraw = false);
	}

	@Environment(EnvType.CLIENT)
	public interface AlphaFunction<T extends ApparitionRenderState> {
		float apply(T apparition);
	}

	@Environment(EnvType.CLIENT)
	public interface DrawSelector<T extends ApparitionRenderState, M extends EntityModel<T>> {
		List<ModelPart> getPartsToDraw(M entityModel);
	}
}
