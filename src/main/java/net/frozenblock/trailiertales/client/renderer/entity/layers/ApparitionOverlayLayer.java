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
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.render.FrozenLibRenderTypes;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.ApparitionModel;
import net.frozenblock.trailiertales.client.renderer.entity.state.ApparitionRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ApparitionOverlayLayer extends RenderLayer<ApparitionRenderState, ApparitionModel> {
	private final RenderType renderType;
	private final ApparitionModel model;

	public ApparitionOverlayLayer(
		EntityRendererProvider.@NotNull Context context,
		RenderLayerParent<ApparitionRenderState, ApparitionModel> renderLayerParent,
		ApparitionModel.AlphaFunction<ApparitionRenderState> innerAlphaFunction,
		ApparitionModel.AlphaFunction<ApparitionRenderState> outerAlphaFunction,
		ApparitionModel.DrawSelector<ApparitionRenderState, ApparitionModel> drawSelector,
		ResourceLocation texture,
		boolean outer
	) {
		super(renderLayerParent);
		this.model = new ApparitionModel(
			outer ? FrozenLibRenderTypes::apparitionOuter : RenderType::entityTranslucentEmissive,
			context.bakeLayer(TTModelLayers.APPARITION_OVERLAY),
			innerAlphaFunction,
			outerAlphaFunction,
			drawSelector
		);
		this.renderType = outer ? FrozenLibRenderTypes.apparitionOuter(texture) : RenderType.entityTranslucentEmissive(texture);
	}

	@Override
	public void submit(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int light,
		ApparitionRenderState renderState,
		float partialTick,
		float color
	) {
		this.model.setupAnim(renderState);
		submitNodeCollector.submitModel(
			this.model,
			renderState,
			poseStack,
			this.renderType,
			light,
			getOverlay(renderState),
			0,
			null
		);
	}

	private static int getOverlay(@NotNull ApparitionRenderState renderState) {
		return OverlayTexture.pack(OverlayTexture.u(0F), OverlayTexture.v(renderState.hasRedOverlay));
	}
}
