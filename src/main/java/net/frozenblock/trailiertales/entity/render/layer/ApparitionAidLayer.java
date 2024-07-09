package net.frozenblock.trailiertales.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.render.model.ApparitionModel;
import net.frozenblock.trailiertales.entity.render.model.ApparitionAidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class ApparitionAidLayer extends RenderLayer<Apparition, ApparitionModel<Apparition>> {
	private static final RenderType TEXTURE = FrozenRenderType.apparitionOuter(TrailierConstants.id("textures/entity/apparition/apparition_active.png"));
	private final ApparitionModel<Apparition> model;

	public ApparitionAidLayer(EntityRendererProvider.@NotNull Context context, RenderLayerParent<Apparition, ApparitionModel<Apparition>> renderLayerParent) {
		super(renderLayerParent);
		this.model = new ApparitionAidModel<>(context.bakeLayer(TrailierTalesClient.APPARITION_OUTER));
	}

	@Override
	public void render(PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, Apparition entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		this.model.prepareMobModel(entity, limbAngle, limbDistance, tickDelta);
		this.model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(TEXTURE);
		this.model.renderToBuffer(matrices, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
	}
}
