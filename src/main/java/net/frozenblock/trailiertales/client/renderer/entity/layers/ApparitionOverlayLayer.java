package net.frozenblock.trailiertales.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.entity.api.rendering.FrozenLibRenderTypes;
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
		ApparitionModel.AlphaFunction<T> outlineAlphaFunction,
		ApparitionModel.AlphaFunction<T> outerAlphaFunction,
		ApparitionModel.DrawSelector<T, ApparitionModel<T>> drawSelector,
		ResourceLocation texture,
		boolean cull
	) {
		super(renderLayerParent);
		this.model = new ApparitionModel<>(
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
