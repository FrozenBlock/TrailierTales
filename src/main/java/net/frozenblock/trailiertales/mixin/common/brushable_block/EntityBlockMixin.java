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

package net.frozenblock.trailiertales.mixin.common.brushable_block;

import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityBlock.class)
public interface EntityBlockMixin {

	@Inject(method = "getTicker", at = @At("HEAD"), cancellable = true)
	default <T extends BlockEntity> void trailierTales$getTicker(Level level, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
		if (type != BlockEntityType.BRUSHABLE_BLOCK) return;
		info.setReturnValue(
			BaseEntityBlock.createTickerHelper(
				type,
				BlockEntityType.BRUSHABLE_BLOCK,
				(levelx, pos, statex, blockEntity) -> {
					if (blockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) brushableBlockEntityInterface.trailierTales$tick();
				})
		);
	}
}
