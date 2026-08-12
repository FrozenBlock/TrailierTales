/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.mixin.common.brushable_block;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.trailiertales.registry.TTAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityItemMixin {

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"
		)
	)
	public void trailierTales$dropItem(CallbackInfo info, @Local(name = "serverLevel") ServerLevel serverLevel) {
		trailierTales$dropItem(FallingBlockEntity.class.cast(this), serverLevel);
	}

	@Inject(method = "callOnBrokenAfterFall", at = @At("HEAD"))
	public void trailierTales$spawnCustomItemAfterBroken(Block block, BlockPos pos, CallbackInfo info) {
		final FallingBlockEntity fallingBlock = FallingBlockEntity.class.cast(this);
		if (!(fallingBlock.level() instanceof ServerLevel level)) return;
		trailierTales$dropItem(fallingBlock, level);
	}

	@Unique
	private static void trailierTales$dropItem(FallingBlockEntity fallingBlock, ServerLevel level) {
		final ItemStack itemStack = fallingBlock.getAttachedOrElse(TTAttachmentTypes.FALLING_BLOCK_ITEM, ItemStack.EMPTY);
		fallingBlock.spawnAtLocation(level, itemStack);
		fallingBlock.removeAttached(TTAttachmentTypes.FALLING_BLOCK_ITEM);
	}
}
