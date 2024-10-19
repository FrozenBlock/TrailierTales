package net.frozenblock.trailiertales.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.render.model.ApparitionModel;
import net.frozenblock.trailiertales.entity.render.renderer.state.ApparitionRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ApparitionOverlayLayer<T extends ApparitionRenderState> extends RenderLayer<T, ApparitionModel<T>> {
	private final RenderType renderType;
	private final ApparitionModel<T> model;

	public ApparitionOverlayLayer(
		EntityRendererProvider.@NotNull Context context,
		RenderLayerParent<T, ApparitionModel<T>> renderLayerParent,
		ApparitionModel.AlphaFunction<T> innerAlphaFunction,
		ApparitionModel.AlphaFunction<T> outlineAlphaFunction,
		ApparitionModel.AlphaFunction<T> outerAlphaFunction,
		ApparitionModel.DrawSelector<T, ApparitionModel<T>> drawSelector,
		ResourceLocation texture,
		boolean cull
	) {
		super(renderLayerParent);
		this.model = new ApparitionModel<>(
			cull ? FrozenRenderType::apparitionOuterCull : FrozenRenderType::apparitionOuter,
			context.bakeLayer(TrailierTalesClient.APPARITION_OVERLAY),
			innerAlphaFunction,
			outlineAlphaFunction,
			outerAlphaFunction,
			drawSelector
		);
		this.renderType = cull ? FrozenRenderType.apparitionOuterCull(texture) : FrozenRenderType.apparitionOuter(texture);
	}

	@Override
	public void render(
		PoseStack matrices,
		@NotNull MultiBufferSource vertexConsumers,
		int light,
		T renderState,
		float partialTick,
		float color
	) {
		this.model.prepareMobModel(renderState, renderState.walkAnimationPos, renderState.walkAnimationSpeed, partialTick);
		this.model.setupAnim(renderState);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.renderType);
		this.model.renderToBuffer(matrices, vertexConsumer, 15728640, this.getOverlay(renderState, 0F));
	}

	private int getOverlay(@NotNull T renderState, float whiteOverlayProgress) {
		return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(renderState.hurtTime > 0 || renderState.deathTime > 0));
	}
}
