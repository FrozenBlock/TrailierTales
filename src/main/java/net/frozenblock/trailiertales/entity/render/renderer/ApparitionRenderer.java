package net.frozenblock.trailiertales.entity.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.render.model.ApparitionModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(@NotNull Apparition entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
		ItemStack stack = entity.getVisibleItem();
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0F, 0.45F, 0F);
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
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(Apparition entity) {
		return TEXTURE;
	}
}
