package net.frozenblock.trailiertales.entity.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.render.layer.ApparitionOverlayLayer;
import net.frozenblock.trailiertales.entity.render.model.ApparitionModel;
import net.frozenblock.trailiertales.entity.render.renderer.state.ApparitionRenderState;
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
public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionRenderState, ApparitionModel> {
	private final ItemRenderer itemRenderer;
	private float itemYaw;

	private static final ResourceLocation TEXTURE = TTConstants.id("textures/entity/apparition/apparition.png");

	public ApparitionRenderer(EntityRendererProvider.Context context) {
		super(context, new ApparitionModel(context.bakeLayer(TrailierTalesClient.APPARITION)), 0.5F);
		this.addLayer(new ApparitionOverlayLayer(
			context,
			this,
			renderState -> renderState.aidAnimProgress * 0.8F,
			renderState -> renderState.aidAnimProgress * 0.8F,
			renderState -> renderState.aidAnimProgress * 0.8F,
			ApparitionModel::getParts,
			TTConstants.id("textures/entity/apparition/apparition_hypnotizing.png"),
			true
		));
		this.addLayer(new ApparitionOverlayLayer(
			context,
			this,
			renderState -> renderState.poltergeistAnimProgress * 0.8F,
			renderState -> renderState.poltergeistAnimProgress * 0.8F,
			renderState -> renderState.poltergeistAnimProgress * 0.8F,
			ApparitionModel::getParts,
			TTConstants.id("textures/entity/apparition/apparition_shooting.png"),
			true
		));
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(@NotNull ApparitionRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
		super.render(renderState, poseStack, buffer, packedLight);
		ItemStack stack = renderState.visibleItem;
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0F, 0.425F, 0F);
			poseStack.mulPose(Axis.YP.rotationDegrees(180F - this.itemYaw));
			poseStack.mulPose(Axis.YN.rotation(renderState.itemYRot));
			poseStack.mulPose(Axis.ZN.rotation(renderState.itemZRot));
			poseStack.pushPose();
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, 1);
			poseStack.popPose();
			poseStack.popPose();
		}
	}

	@Override
	protected void setupRotations(ApparitionRenderState renderState, PoseStack matrices, float bodyYaw, float scale) {
		super.setupRotations(renderState, matrices, bodyYaw, scale);
		this.itemYaw = bodyYaw;
		this.shadowStrength = renderState.totalTransparency;
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(ApparitionRenderState renderState) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(Apparition entity, BlockPos pos) {
		return 15;
	}

	@Override
	@NotNull
	public ApparitionRenderState createRenderState() {
		return new ApparitionRenderState();
	}

	@Override
	public void extractRenderState(Apparition apparition, ApparitionRenderState renderState, float partialTick) {
		super.extractRenderState(apparition, renderState, partialTick);

		renderState.hurtTime = apparition.hurtTime;
		renderState.itemYRot = apparition.getItemYRot(partialTick);
		renderState.itemZRot = apparition.getItemZRot(partialTick);
		renderState.totalTransparency = apparition.totalTransparency(partialTick);
		renderState.innerTransparency = apparition.getInnerTransparency(partialTick);
		renderState.outlineTransparency = apparition.getOutlineTransparency(partialTick);
		renderState.outerTransparency = apparition.getOuterTransparency(partialTick);
		renderState.flicker = apparition.getFlicker(partialTick);

		renderState.visibleItem = apparition.getVisibleItem();
		renderState.aidAnimProgress = apparition.getAidAnimProgress(partialTick);
		renderState.poltergeistAnimProgress = apparition.getPoltergeistAnimProgress(partialTick);
	}
}
