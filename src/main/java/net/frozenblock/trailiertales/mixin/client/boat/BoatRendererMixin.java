/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.mixin.client.boat;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.BoatBannerModel;
import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BoatRenderer.class)
public abstract class BoatRendererMixin extends EntityRenderer<Boat> {
	@Unique
	private Map<Boat.Type, ResourceLocation> trailierTales$boatBannerResources;
	@Unique
	private BoatBannerModel trailierTales$boatBannerModel;

	protected BoatRendererMixin(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$init(EntityRendererProvider.Context context, boolean isChest, CallbackInfo info) {
		this.trailierTales$boatBannerModel = new BoatBannerModel(context.bakeLayer(TTModelLayers.BOAT_BANNER));
		this.trailierTales$boatBannerResources = Stream.of(Boat.Type.values())
			.collect(ImmutableMap.toImmutableMap(boatVariant -> boatVariant, BoatRendererMixin::trailierTales$getBannerBaseTextureLocation));
	}

	@Contract("_ -> new")
	@Unique
	private static @NotNull ResourceLocation trailierTales$getBannerBaseTextureLocation(Boat.@NotNull Type type) {
		return TTConstants.idOrDefault(
			"textures/entity/boat_banner_base/" + type.getName() + ".png",
			"textures/entity/boat_banner_base/oak.png"
		);
	}

	@ModifyExpressionValue(
		method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/vehicle/Boat;getVariant()Lnet/minecraft/world/entity/vehicle/Boat$Type;",
			ordinal = 0
		)
	)
	public Boat.Type trailierTales$captureBoatType(
		Boat.Type original,
		@Share("trailierTales$boatType") LocalRef<Boat.Type> boatType
	) {
		boatType.set(original);
		return original;
	}

	@Inject(
		method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/ListModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V",
			shift = At.Shift.AFTER
		)
	)
	public void trailierTales$renderBoatBanner(
		Boat boat, float f, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int i, CallbackInfo info,
		@Share("trailierTales$boatType") LocalRef<Boat.Type> boatType
	) {
		if (boat instanceof BoatBannerInterface bannerInterface) {
			ItemStack stack = bannerInterface.trailierTales$getBanner();
			if (!stack.isEmpty() && stack.getItem() instanceof BannerItem bannerItem) {
				Boat.Type type = boatType.get();
				matrices.pushPose();
				float o;
				float p;
				o = bannerInterface.trailierTales$getWalkAnimationState().speed(tickDelta);
				p = bannerInterface.trailierTales$getWalkAnimationState().position(tickDelta);
				if (o > 1F) {
					o = 1F;
				}
				this.trailierTales$boatBannerModel.setRaft(type == Boat.Type.BAMBOO);
				this.trailierTales$boatBannerModel.beforeRender(matrices);
				this.trailierTales$boatBannerModel.setupAnim(boat, p, o, boat.tickCount + tickDelta, 0F, 0F);
				this.trailierTales$boatBannerModel.renderToBuffer(
					matrices,
					vertexConsumers.getBuffer(
						this.trailierTales$boatBannerModel.renderType(
							this.trailierTales$boatBannerResources.get(type)
						)
					),
					i,
					OverlayTexture.NO_OVERLAY
				);
				this.trailierTales$boatBannerModel.renderFlag(
					matrices,
					vertexConsumers,
					i,
					OverlayTexture.NO_OVERLAY,
					bannerItem.getColor(),
					stack.getComponents().get(DataComponents.BANNER_PATTERNS)
				);
				this.trailierTales$boatBannerModel.afterRender(matrices);
				matrices.popPose();
			}
		}
	}
}
