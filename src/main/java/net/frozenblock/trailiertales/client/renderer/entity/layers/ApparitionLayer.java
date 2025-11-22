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

package net.frozenblock.trailiertales.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.lib.render.FrozenLibRenderTypes;
import net.frozenblock.trailiertales.client.model.ApparitionModel;
import net.frozenblock.trailiertales.client.renderer.entity.state.ApparitionRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class ApparitionLayer extends RenderLayer<ApparitionRenderState, ApparitionModel> {
	private final RenderType innerRenderType;
	private final RenderType outerRenderType;
	ApparitionModel.AlphaFunction<ApparitionRenderState> innerAlphaFunction;
	ApparitionModel.AlphaFunction<ApparitionRenderState> outerAlphaFunction;
	private final ApparitionModel model;
	private final int minOrder;

	public ApparitionLayer(
		RenderLayerParent<ApparitionRenderState, ApparitionModel> renderLayerParent,
		ApparitionModel.AlphaFunction<ApparitionRenderState> innerAlphaFunction,
		ApparitionModel.AlphaFunction<ApparitionRenderState> outerAlphaFunction,
		Identifier texture,
		int minOrder
	) {
		super(renderLayerParent);
		this.innerRenderType = RenderTypes.entityTranslucentEmissive(texture);
		this.outerRenderType = FrozenLibRenderTypes.apparitionOuter(texture);
		this.innerAlphaFunction = innerAlphaFunction;
		this.outerAlphaFunction = outerAlphaFunction;
		this.model = renderLayerParent.getModel();

		this.minOrder = minOrder * 2;
	}

	@Override
	public void submit(
		PoseStack poseStack,
		SubmitNodeCollector collector,
		int light,
		ApparitionRenderState renderState,
		float partialTick,
		float color
	) {
		final int overlay = LivingEntityRenderer.getOverlayCoords(renderState, 0F);
		final float innerTransparency = this.innerAlphaFunction.apply(renderState) * renderState.flicker;
		final float outerTransparency = this.outerAlphaFunction.apply(renderState) * renderState.flicker;

		if (renderState.innerTransparency > 0F) {
			collector.order(this.minOrder).submitModelPart(
				this.model.outline,
				poseStack,
				this.innerRenderType,
				light,
				overlay,
				null,
				ARGB.colorFromFloat(innerTransparency * 0.7F, 1F, 1F, 1F),
				null
			);

			collector.order(this.minOrder + 1).submitModelPart(
				this.model.inner,
				poseStack,
				this.innerRenderType,
				light,
				overlay,
				null,
				ARGB.colorFromFloat(innerTransparency, 1F, 1F, 1F),
				null
			);
		}

		if (outerTransparency > 0F) {
			collector.order(this.minOrder).submitModelPart(
				this.model.outer,
				poseStack,
				this.outerRenderType,
				light,
				overlay,
				null,
				ARGB.colorFromFloat(outerTransparency * renderState.flicker, 1F, 1F, 1F),
				null
			);
		}
	}
}
