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
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ApparitionOverlayLayer<T extends Apparition> extends RenderLayer<T, ApparitionModel<T>> {
	private final RenderType renderType;
	private final ApparitionModel<T> model;

	public ApparitionOverlayLayer(
		EntityRendererProvider.@NotNull Context context,
		RenderLayerParent<T, ApparitionModel<T>> renderLayerParent,
		ApparitionModel.AlphaFunction<T> innerAlphaFunction,
		ApparitionModel.AlphaFunction<T> outerAlphaFunction,
		ApparitionModel.DrawSelector<T, ApparitionModel<T>> drawSelector,
		ResourceLocation texture,
		boolean outer
	) {
		super(renderLayerParent);
		this.model = new ApparitionModel<>(
			outer ? FrozenLibRenderTypes::apparitionOuter : RenderType::entityTranslucentEmissive,
			context.bakeLayer(TTModelLayers.APPARITION_OVERLAY),
			innerAlphaFunction,
			outerAlphaFunction,
			drawSelector
		);
		this.renderType = outer ? FrozenLibRenderTypes.apparitionOuter(texture) : RenderType.entityTranslucentEmissive(texture);
	}

	@Override
	public void render(
		PoseStack matrices,
		@NotNull MultiBufferSource vertexConsumers,
		int light,
		T entity,
		float limbAngle,
		float limbDistance,
		float tickDelta,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.model.prepareMobModel(entity, limbAngle, limbDistance, tickDelta);
		this.model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.renderType);
		this.model.renderToBuffer(matrices, vertexConsumer, 15728640, this.getOverlay(entity, 0F));
	}

	private int getOverlay(@NotNull T entity, float whiteOverlayProgress) {
		return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
	}
}
