/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.mixin.client.brush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ItemProperties.class)
public class ItemPropertiesMixin {

	@ModifyExpressionValue(
		method = "method_49351(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/entity/LivingEntity;I)F",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;getUseItem()Lnet/minecraft/world/item/ItemStack;"
		)
	)
	private static ItemStack trailierTales$useSmoothBrushingAnim(ItemStack original) {
		if (TTItemConfig.SMOOTH_BRUSH_ANIMATION) return ItemStack.EMPTY;
		return original;
	}
}
