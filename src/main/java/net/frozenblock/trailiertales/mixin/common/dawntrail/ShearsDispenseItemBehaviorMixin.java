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

package net.frozenblock.trailiertales.mixin.common.dawntrail;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.block.DawntrailBlock;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShearsDispenseItemBehavior.class)
public class ShearsDispenseItemBehaviorMixin {

	@WrapOperation(
		method = "execute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/dispenser/ShearsDispenseItemBehavior;tryShearBeehive(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean trailierTales$execute(ServerLevel level, ItemStack stack, BlockPos pos, Operation<Boolean> original) {
		return original.call(level, stack, pos) || trailierTales$tryShearDawntrail(level, pos);
	}

	@Unique
	private static boolean trailierTales$tryShearDawntrail(@NotNull ServerLevel level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		if (!state.is(TTBlocks.DAWNTRAIL) || !DawntrailBlock.isMaxAge(state)) return false;
		DawntrailBlock.shear(level, pos, state, null);
		return true;
	}
}
