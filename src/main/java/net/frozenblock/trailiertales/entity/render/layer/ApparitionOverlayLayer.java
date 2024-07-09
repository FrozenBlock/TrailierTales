package net.frozenblock.trailiertales.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.render.model.ApparitionModel;
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
		ApparitionModel.AlphaFunction<T> coreAlphaFunciton,
		ApparitionModel.AlphaFunction<T> outerAlphaFunction,
		ApparitionModel.DrawSelector<T, ApparitionModel<T>> drawSelector,
		ResourceLocation texture
	) {
		super(renderLayerParent);
		this.model = new ApparitionModel<>(
			FrozenRenderType::apparitionOuter,
			context.bakeLayer(TrailierTalesClient.APPARITION_OVERLAY),
			coreAlphaFunciton,
			outerAlphaFunction,
			drawSelector
		);
		this.renderType = FrozenRenderType.apparitionOuter(texture);
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
