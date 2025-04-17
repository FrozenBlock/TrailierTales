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

package net.frozenblock.trailiertales.mixin.common.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {

	@ModifyExpressionValue(
		method = "getBoundingBoxForCulling",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"
		)
	)
	public AABB trailierTales$extendBannerBoatRenderBox(AABB original) {
		if (Entity.class.cast(this) instanceof BoatBannerInterface bannerInterface) {
			if (!bannerInterface.trailierTales$getBanner().isEmpty()) {
				return original.inflate(1D, 0D, 1D).expandTowards(0D, 2D, 0D);
			}
		}
		return original;
	}

}
