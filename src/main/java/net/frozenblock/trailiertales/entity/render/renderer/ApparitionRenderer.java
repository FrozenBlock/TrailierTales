package net.frozenblock.trailiertales.entity.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.render.layer.ApparitionOverlayLayer;
import net.frozenblock.trailiertales.entity.render.model.ApparitionModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionModel<Apparition>> {
	private final ItemRenderer itemRenderer;
	private float itemYaw;

	private static final ResourceLocation TEXTURE = TrailierConstants.id("textures/entity/apparition/apparition.png");

	public ApparitionRenderer(EntityRendererProvider.Context context) {
		super(context, new ApparitionModel<>(context.bakeLayer(TrailierTalesClient.APPARITION)), 0.5F);
		this.addLayer(new ApparitionOverlayLayer<>(
			context,
			this,
			(apparition, tickDelta) -> apparition.getAidAnimProgress(tickDelta) * 0.8F,
			(apparition, tickDelta) -> apparition.getAidAnimProgress(tickDelta) * 0.8F,
			(apparition, tickDelta) -> apparition.getAidAnimProgress(tickDelta) * 0.8F,
			ApparitionModel::getParts,
			TrailierConstants.id("textures/entity/apparition/apparition_hypnotizing.png"),
			true
		));
		this.addLayer(new ApparitionOverlayLayer<>(
			context,
			this,
			(apparition, tickDelta) -> apparition.getPoltergeistAnimProgress(tickDelta) * 0.8F,
			(apparition, tickDelta) -> apparition.getPoltergeistAnimProgress(tickDelta) * 0.8F,
			(apparition, tickDelta) -> apparition.getPoltergeistAnimProgress(tickDelta) * 0.8F,
			ApparitionModel::getParts,
			TrailierConstants.id("textures/entity/apparition/apparition_shooting.png"),
			true
		));
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(@NotNull Apparition entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
		ItemStack stack = entity.getVisibleItem();
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0F, 0.425F, 0F);
			poseStack.mulPose(Axis.YP.rotationDegrees(180F - this.itemYaw));
			poseStack.mulPose(Axis.YN.rotation(entity.getItemYRot(partialTick)));
			poseStack.mulPose(Axis.ZN.rotation(entity.getItemZRot(partialTick)));
			poseStack.pushPose();
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), 1);
			poseStack.popPose();
			poseStack.popPose();
		}
	}

	@Override
	protected void setupRotations(Apparition entity, PoseStack matrices, float animationProgress, float bodyYaw, float tickDelta, float f) {
		super.setupRotations(entity, matrices, animationProgress, bodyYaw, tickDelta, f);
		this.itemYaw = bodyYaw;
		this.shadowStrength = entity.totalTransparency(tickDelta);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(Apparition entity) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(Apparition entity, BlockPos pos) {
		return 15;
	}
}
