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

package net.frozenblock.trailiertales.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.entity.api.rendering.FrozenLibRenderTypes;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.ApparitionModel;
import net.frozenblock.trailiertales.client.renderer.entity.state.ApparitionRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
		ApparitionModel.AlphaFunction<ApparitionRenderState> outlineAlphaFunction,
		ApparitionModel.AlphaFunction<ApparitionRenderState> outerAlphaFunction,
		ApparitionModel.DrawSelector<ApparitionRenderState, ApparitionModel> drawSelector,
		ResourceLocation texture,
		boolean cull
	) {
		super(renderLayerParent);
		this.model = new ApparitionModel(
			cull ? FrozenLibRenderTypes::apparitionOuterCull : FrozenLibRenderTypes::apparitionOuter,
			context.bakeLayer(TTModelLayers.APPARITION_OVERLAY),
			innerAlphaFunction,
			outlineAlphaFunction,
			outerAlphaFunction,
			drawSelector
		);
		this.renderType = cull ? FrozenLibRenderTypes.apparitionOuterCull(texture) : FrozenLibRenderTypes.apparitionOuter(texture);
	}

	@Override
	public void render(
		PoseStack matrices,
		@NotNull MultiBufferSource vertexConsumers,
		int light,
		ApparitionRenderState renderState,
		float partialTick,
		float color
	) {
		this.model.setupAnim(renderState);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.renderType);
		this.model.renderToBuffer(matrices, vertexConsumer, 15728640, getOverlay(renderState));
	}

	private static int getOverlay(@NotNull ApparitionRenderState renderState) {
		return OverlayTexture.pack(OverlayTexture.u(0F), OverlayTexture.v(renderState.hasRedOverlay));
	}
}
