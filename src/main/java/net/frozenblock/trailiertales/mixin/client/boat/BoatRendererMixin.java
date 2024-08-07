package net.frozenblock.trailiertales.mixin.client.boat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.entity.render.model.BoatBannerModel;
import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BoatRenderer.class)
public abstract class BoatRendererMixin extends EntityRenderer<Boat> {
	@Unique
	private BoatBannerModel trailierTales$boatBannerModel;

	protected BoatRendererMixin(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$init(EntityRendererProvider.Context context, boolean isChest, CallbackInfo info) {
		this.trailierTales$boatBannerModel = new BoatBannerModel(context.bakeLayer(TrailierTalesClient.BOAT_BANNER));
	}

	@Inject(
		method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/ListModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V",
			shift = At.Shift.AFTER
		)
	)
	public void trailierTales$renderBoatBanner(Boat boat, float f, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int i, CallbackInfo info) {
		if (boat instanceof BoatBannerInterface bannerInterface) {
			ItemStack stack = bannerInterface.trailierTales$getBanner();
			if (!stack.isEmpty() && stack.getItem() instanceof BannerItem bannerItem) {
				matrices.pushPose();
				this.trailierTales$boatBannerModel.setupAnim(boat, tickDelta, Mth.lerp(tickDelta, boat.walkDistO, boat.walkDist), boat.tickCount + tickDelta, 0F, 0F);
				this.trailierTales$boatBannerModel.renderToBuffer(matrices, ModelBakery.BANNER_BASE.buffer(vertexConsumers, RenderType::entitySolid), i, OverlayTexture.NO_OVERLAY);
				this.trailierTales$boatBannerModel.renderFlag(matrices, vertexConsumers, i, OverlayTexture.NO_OVERLAY, bannerItem.getColor(), stack.getComponents().get(DataComponents.BANNER_PATTERNS));
				matrices.popPose();
			}
		}
	}
}
