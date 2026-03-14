package net.frozenblock.trailiertales.mixin.client.boat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.client.model.object.boat.BoatBannerModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(BannerRenderer.class)
public class BannerRendererMixin {

	@WrapOperation(
		method = "submitPatternLayer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"
		)
	)
	private static void trailierTales$fixBannerBoatRenderType(
		SubmitNodeCollector instance,
		Model model,
		Object renderState,
		PoseStack poseStack,
		RenderType renderType,
		int lightCoords,
		int overlayCoords,
		int tintedColor,
		TextureAtlasSprite atlasSprite,
		int outlineColor,
		ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
		Operation<Void> original,
		@Local(argsOnly = true) SpriteId sprite
	) {
		if (model instanceof BoatBannerModel) renderType = sprite.renderType(RenderTypes::entitySolid);
		original.call(instance, model, renderState, poseStack, renderType, lightCoords, overlayCoords, tintedColor, atlasSprite, outlineColor, crumblingOverlay);
	}

}
