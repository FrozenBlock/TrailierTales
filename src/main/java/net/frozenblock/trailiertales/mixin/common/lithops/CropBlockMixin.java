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

package net.frozenblock.trailiertales.mixin.common.lithops;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.block.LithopsCropBlock;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CropBlock.class)
public class CropBlockMixin {

	@ModifyExpressionValue(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/CropBlock;getStateForAge(I)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState trailierTales$fixLithopsRandomTickGrowthState(
		BlockState original,
		BlockState state
	) {
		if (state.is(TTBlocks.LITHOPS_CROP)) return original.setValue(LithopsCropBlock.FACING, state.getValue(LithopsCropBlock.FACING));
		return original;
	}

	@ModifyExpressionValue(
		method = "growCrops",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/CropBlock;getStateForAge(I)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState trailierTales$fixLithopsBoneMealGrowthState(
		BlockState original,
		Level level, BlockPos pos, BlockState state
	) {
		if (state.is(TTBlocks.LITHOPS_CROP)) return original.setValue(LithopsCropBlock.FACING, state.getValue(LithopsCropBlock.FACING));
		return original;
	}

}
