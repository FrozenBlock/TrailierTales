package net.frozenblock.trailiertales.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
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
			cull ? FrozenRenderType::apparitionOuterCull : FrozenRenderType::apparitionOuter,
			context.bakeLayer(TTModelLayers.APPARITION_OVERLAY),
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
		ApparitionRenderState renderState,
		float partialTick,
		float color
	) {
		this.model.setupAnim(renderState);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.renderType);
		this.model.renderToBuffer(matrices, vertexConsumer, 15728640, this.getOverlay(renderState, 0F));
	}

	private int getOverlay(@NotNull ApparitionRenderState renderState, float whiteOverlayProgress) {
		return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(renderState.hurtTime > 0 || renderState.deathTime > 0));
	}
}
