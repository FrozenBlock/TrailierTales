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

import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	public void trailierTales$useItemOn(
		ItemStack stack,
		BlockState state,
		Level level,
		BlockPos pos,
		Player player,
		InteractionHand hand,
		BlockHitResult hitResult,
		CallbackInfoReturnable<InteractionResult> info
	) {
		if (!(state.getBlock() instanceof BrushableBlock)) return;
		if (!TTBlockConfig.get().suspiciousBlocks.place_items || !state.hasProperty(TTBlockStateProperties.CAN_PLACE_ITEM)) return;

		final ItemStack playerStack = player.getItemInHand(hand);
		final boolean canPlaceIntoBlock = state.getValue(TTBlockStateProperties.CAN_PLACE_ITEM) &&
			playerStack != ItemStack.EMPTY &&
			playerStack.getItem() != Items.AIR &&
			!playerStack.is(Items.BRUSH);
		if (canPlaceIntoBlock && level.getBlockEntity(pos) instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) {
			brushableBlockEntityInterface.trailierTales$setItem(playerStack.split(1));
			info.setReturnValue(InteractionResult.SUCCESS);
			return;
		}
		info.setReturnValue(InteractionResult.TRY_WITH_EMPTY_HAND);
	}

	@Inject(method = "affectNeighborsAfterRemoval", at = @At("HEAD"))
	public void trailierTales$onRemove(BlockState state, ServerLevel level, BlockPos pos, boolean moved, CallbackInfo info) {
		if (!(state.getBlock() instanceof BrushableBlock)) return;
		if (level.getBlockEntity(pos) instanceof BrushableBlockEntity brushableBlockEntity
			&& brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface
			&& brushableBlockEntityInterface.trailierTales$hasCustomItem()
		) {
			Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), brushableBlockEntity.getItem());
		}
	}
}
