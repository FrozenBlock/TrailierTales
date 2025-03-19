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

package net.frozenblock.trailiertales.mixin.common.apparition;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.block.EctoplasmBlock;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockCollisions.class)
public class BlockCollisionsMixin {

	@Shadow
	@Final
	private CollisionContext context;

	@ModifyExpressionValue(
		method = "computeNext",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/BlockGetter;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState trailierTales$modifyBlockStateForApparitions(BlockState original) {
		if (this.context instanceof EntityCollisionContext entityCollisionContext) {
			if (entityCollisionContext.getEntity() instanceof Apparition) {
				return original.getBlock() instanceof EctoplasmBlock ? original : Blocks.AIR.defaultBlockState();
			}
		}

		return original;
	}

}
