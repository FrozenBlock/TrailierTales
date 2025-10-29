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

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.trailiertales.impl.FallingBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityItemMixin implements FallingBlockEntityInterface {

	@Unique
	private ItemStack trailierTales$itemStack = ItemStack.EMPTY;
	@Unique
	private boolean trailierTales$overrideBreak = false;

	@Override
	public boolean trailierTales$setItem(ItemStack itemStack) {
		this.trailierTales$itemStack = itemStack;
		return true;
	}

	@Override
	public ItemStack trailierTales$getItem() {
		return this.trailierTales$itemStack;
	}

	@Override
	public void trailierTales$overrideBreak() {
		this.trailierTales$overrideBreak = true;
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"
		)
	)
	public void trailierTales$dropItem(CallbackInfo info, @Local ServerLevel level) {
		FallingBlockEntity.class.cast(this).spawnAtLocation(level, this.trailierTales$itemStack);
	}

	@Inject(method = "callOnBrokenAfterFall", at = @At("HEAD"))
	public void trailierTales$spawnCustomItemAfterBroken(Block block, BlockPos pos, CallbackInfo info) {
		final FallingBlockEntity fallingBlockEntity = FallingBlockEntity.class.cast(this);
		if (fallingBlockEntity.level() instanceof ServerLevel level && this.trailierTales$itemStack != ItemStack.EMPTY) {
			fallingBlockEntity.spawnAtLocation(level, this.trailierTales$itemStack.copy());
			this.trailierTales$itemStack = ItemStack.EMPTY;
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$addAdditionalSaveData(ValueOutput valueOutput, CallbackInfo info) {
		if (this.trailierTales$itemStack != null && !this.trailierTales$itemStack.isEmpty()) {
			valueOutput.store("TrailierTalesItem", ItemStack.CODEC, this.trailierTales$itemStack);
		}

		if (this.trailierTales$overrideBreak) {
			valueOutput.putBoolean("TrailierTalesOverrideBreak", true);
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$readAdditionalSaveData(ValueInput valueInput, CallbackInfo info) {
		this.trailierTales$itemStack = valueInput.read("TrailierTalesItem", ItemStack.CODEC).orElse(ItemStack.EMPTY);
		this.trailierTales$overrideBreak = valueInput.getBooleanOr("TrailierTalesOverrideBreak", false);
	}

}
