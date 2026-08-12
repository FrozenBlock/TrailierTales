/*
 * Copyright 2026 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.mixin.client.boat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.client.model.object.boat.BoatBannerModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(BannerRenderer.class)
public class BannerRendererMixin {

	@WrapOperation(
		method = "submitPatternLayer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/resources/model/sprite/SpriteId;renderType(Ljava/util/function/Function;)Lnet/minecraft/client/renderer/rendertype/RenderType;"
		)
	)
	private static RenderType trailierTales$fixBannerBoatRenderType(
		SpriteId instance, Function<Identifier, RenderType> renderType, Operation<RenderType> original,
		@Local(argsOnly = true) Model model
	) {
		if (model instanceof BoatBannerModel) return instance.renderType(RenderTypes::entitySolid);
		return original.call(instance, renderType);
	}

}
