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

package net.frozenblock.trailiertales.mixin.client.brush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(RangeSelectItemModel.class)
public class RangeSelectItemModelMixin {

	@ModifyExpressionValue(
		method = "update",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Float;isNaN(F)Z"
		)
	)
	private boolean trailierTales$useSmoothBrushingAnim(
		boolean original,
		ItemStackRenderState itemStackRenderState,
		ItemStack itemStack
	) {
		if (TTItemConfig.SMOOTH_BRUSH_ANIMATION && itemStack.is(Items.BRUSH)) {
			return true;
		}
		return original;
	}
}
