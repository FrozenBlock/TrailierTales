/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.mixin.common.brushable_block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.frozenblock.trailiertales.registry.TTEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin implements BrushableBlockEntityInterface {

	@Unique
	private float trailierTales$targetXLerp = 0.5F;
	@Unique
	private float trailierTales$xLerp = 0.5F;
	@Unique
	private float trailierTales$prevXLerp = 0.5F;
	@Unique
	private float trailierTales$targetYLerp = 0.0F;
	@Unique
	private float trailierTales$yLerp = 0.0F;
	@Unique
	private float trailierTales$prevYLerp = 0.0F;
	@Unique
	private float trailierTales$targetZLerp = 0.5F;
	@Unique
	private float trailierTales$zLerp = 0.5F;
	@Unique
	private float trailierTales$prevZLerp = 0.5F;
	@Unique
	private float trailierTales$rotation;
	@Unique
	private float trailierTales$prevRotation;
	@Unique
	private boolean trailierTales$hasCustomItem = false;
	@Unique
	private float trailierTales$targetItemScale = 0F;
	@Unique
	private float trailierTales$itemScale = 0F;
	@Unique
	private float trailierTales$prevItemScale = 0F;
	@Unique
	@Nullable
	private Direction trailierTales$hitDirection;
	@Unique
	private boolean trailierTales$rebrushed = false;
	@Unique
	private ResourceKey<LootTable> trailierTales$storedLootTable;

	@Shadow
	@Nullable
	private Direction hitDirection;
	@Shadow
	private ItemStack item;
	@Shadow
	@Nullable
	public ResourceKey<LootTable> lootTable;
	@Shadow
	private long lootTableSeed;
	@Shadow
	private int brushCount;

	@Contract(pure = true)
	@Unique
	private static float @NotNull [] trailierTales$translations(@NotNull Direction direction, int i) {
		float[] fs = new float[]{0.5F, 0F, 0.5F};
		float f = (float) i / 9F;
		switch (direction) {
			case EAST -> fs[0] = 0.73F + f;
			case WEST -> fs[0] = 0.25F - f;
			case UP -> fs[1] = 0.25F + f;
			case DOWN -> fs[1] = -0.23F - f;
			case NORTH -> fs[2] = 0.25F - f;
			case SOUTH -> fs[2] = 0.73F + f;
		}
		return fs;
	}

	@Inject(method = "getUpdateTag", at = @At("RETURN"))
	public void trailierTales$getUpdateTag(CallbackInfoReturnable<CompoundTag> info) {
		this.trailierTales$saveNBT(info.getReturnValue());
	}

	@Inject(method = "loadAdditional", at = @At("TAIL"))
	public void trailierTales$loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider, CallbackInfo info) {
		this.trailierTales$readNBT(compoundTag);
		if (compoundTag.contains("Rebrushed")) this.trailierTales$rebrushed = compoundTag.getBoolean("Rebrushed");
		if (compoundTag.contains("TrailierTalesStoredLootTable", 8)) {
			this.trailierTales$storedLootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(compoundTag.getString("TrailierTalesStoredLootTable")));
		}
	}

	@Inject(method = "saveAdditional", at = @At("TAIL"))
	public void trailierTales$saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider, CallbackInfo info) {
		this.trailierTales$saveNBT(compoundTag);
		if (this.trailierTales$rebrushed) compoundTag.putBoolean("Rebrushed", this.trailierTales$rebrushed);
		if (this.trailierTales$storedLootTable != null && this.trailierTales$storedLootTable != this.lootTable) {
			compoundTag.putString("TrailierTalesStoredLootTable", this.trailierTales$storedLootTable.location().toString());
		}
	}

	@Unique
	private boolean trailierTales$runRebrush;

	@Inject(
		method = "brush",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;brushingCompleted(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V",
			shift = At.Shift.BEFORE
		)
	)
	public void trailierTales$rebrushA(long ticks, ServerLevel level, Player player, Direction direction, ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if (!this.trailierTales$hasCustomItem) {
			int rebrushLevel = stack.getEnchantments().getLevel(player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(TTEnchantments.REBRUSH));
			if (rebrushLevel > 0) {
				float rebrushChance = this.trailierTales$rebrushed ? 0.05F * rebrushLevel : 0.1F * rebrushLevel;
				if (player.getRandom().nextFloat() < rebrushChance) {
					this.trailierTales$runRebrush = true;
				}
			}
		}
	}

	@WrapOperation(
		method = "brushingCompleted",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrushableBlock;getTurnsInto()Lnet/minecraft/world/level/block/Block;"
		)
	)
	private Block trailierTales$runRebrush(BrushableBlock instance, Operation<Block> original) {
		if (this.trailierTales$runRebrush) {
			this.trailierTales$rebrushed = true;
			this.lootTable = this.trailierTales$storedLootTable;
			this.brushCount = 0;
			this.hitDirection = null;
			this.trailierTales$targetXLerp = 0.5F;
			this.trailierTales$xLerp = 0.5F;
			this.trailierTales$prevXLerp = 0.5F;
			this.trailierTales$targetYLerp = 0F;
			this.trailierTales$yLerp = 0F;
			this.trailierTales$prevYLerp = 0F;
			this.trailierTales$targetZLerp = 0.5F;
			this.trailierTales$zLerp = 0.5F;
			this.trailierTales$prevZLerp = 0.5F;
			this.trailierTales$rotation = 0F;
			this.trailierTales$prevRotation = 0F;
			this.trailierTales$targetItemScale = 0F;
			this.trailierTales$itemScale = 0F;
			this.trailierTales$prevItemScale = 0F;
			this.trailierTales$runRebrush = false;
			this.item = ItemStack.EMPTY;
			this.lootTableSeed = 0L;
			return instance;
		}
		return original.call(instance);
	}

	@Inject(
		method = "tryLoadLootTable",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;lootTable:Lnet/minecraft/resources/ResourceKey;",
			opcode = Opcodes.PUTFIELD,
			shift = At.Shift.AFTER
		)
	)
	private void trailierTales$storeLootTable(CompoundTag nbt, CallbackInfoReturnable<Boolean> info) {
		this.trailierTales$storedLootTable = this.lootTable;
	}

	@Inject(method = "setLootTable", at = @At("HEAD"))
	public void trailierTales$setLootTable(ResourceKey<LootTable> lootTable, long lootTableSeed, CallbackInfo info) {
		if (lootTable != null) {
			this.trailierTales$storedLootTable = lootTable;;
		}
	}

	@Unique
	@Override
	public void trailierTales$tick() {
		BrushableBlockEntity brushableBlockEntity = BrushableBlockEntity.class.cast(this);
		Level level = brushableBlockEntity.getLevel();
		if (level != null && !level.isClientSide) {
			BlockPos blockPos = brushableBlockEntity.getBlockPos();
			BlockState blockState = level.getBlockState(blockPos);
			if (blockState.hasProperty(TTBlockStateProperties.CAN_PLACE_ITEM)) {
				BlockState worldState = level.getBlockState(blockPos);
				boolean canPlaceItemProperty = worldState.hasProperty(TTBlockStateProperties.CAN_PLACE_ITEM) && worldState.getValue(TTBlockStateProperties.CAN_PLACE_ITEM);
				boolean canPlaceItem = this.item.isEmpty() && this.lootTable == null;
				if (canPlaceItemProperty != canPlaceItem) {
					level.setBlock(blockPos, blockState.setValue(TTBlockStateProperties.CAN_PLACE_ITEM, canPlaceItem), Block.UPDATE_CLIENTS);
				}
			}
			if (this.trailierTales$storedLootTable == null && this.lootTable != null) {
				this.trailierTales$storedLootTable = this.lootTable;
			}
		}
			this.trailierTales$prevRotation = this.trailierTales$rotation;
			if (this.hitDirection != null) {
				this.trailierTales$hitDirection = this.hitDirection;
			}
			Direction direction = this.trailierTales$getHitDirection();
			if (direction != null) {
				float[] translation = trailierTales$translations(direction, this.getCompletionState());
				this.trailierTales$targetXLerp = translation[0];
				this.trailierTales$targetYLerp = translation[1];
				this.trailierTales$targetZLerp = translation[2];
				if (direction.getAxis() == Direction.Axis.X) {
					this.trailierTales$rotation = 90F;
				} else {
					this.trailierTales$rotation = 0F;
				}
			}
			this.trailierTales$targetItemScale = Math.min(1F, this.brushCount * 0.5F);
			this.trailierTales$prevItemScale = this.trailierTales$itemScale;
			this.trailierTales$itemScale += (this.trailierTales$targetItemScale - this.trailierTales$itemScale) * 0.25F;

			this.trailierTales$prevXLerp = this.trailierTales$xLerp;
			this.trailierTales$prevYLerp = this.trailierTales$yLerp;
			this.trailierTales$prevZLerp = this.trailierTales$zLerp;

			this.trailierTales$xLerp += (this.trailierTales$targetXLerp - this.trailierTales$xLerp) * 0.2F;
			this.trailierTales$yLerp += (this.trailierTales$targetYLerp - this.trailierTales$yLerp) * 0.2F;
			this.trailierTales$zLerp += (this.trailierTales$targetZLerp - this.trailierTales$zLerp) * 0.2F;
	}

	@Unique
	public void trailierTales$saveNBT(CompoundTag compoundTag) {
		if (this.trailierTales$hitDirection != null) {
			compoundTag.putString("TTHitDirection", this.trailierTales$hitDirection.getName());
		}
		if (this.trailierTales$targetXLerp != 0.5F)
			compoundTag.putFloat("TargetXLerp", this.trailierTales$targetXLerp);
		if (this.trailierTales$targetYLerp != 0F)
			compoundTag.putFloat("TargetYLerp", this.trailierTales$targetYLerp);
		if (this.trailierTales$targetZLerp != 0.5F)
			compoundTag.putFloat("TargetZLerp", this.trailierTales$targetZLerp);
		if (this.trailierTales$xLerp != 0.5F)
			compoundTag.putFloat("XLerp", this.trailierTales$xLerp);
		if (this.trailierTales$yLerp != 0F)
			compoundTag.putFloat("YLerp", this.trailierTales$yLerp);
		if (this.trailierTales$zLerp != 0.5F)
			compoundTag.putFloat("ZLerp", this.trailierTales$zLerp);
		if (this.trailierTales$prevXLerp != 0.5F)
			compoundTag.putFloat("PrevXLerp", this.trailierTales$prevXLerp);
		if (this.trailierTales$prevYLerp != 0F)
			compoundTag.putFloat("PrevYLerp", this.trailierTales$prevYLerp);
		if (this.trailierTales$prevZLerp != 0.5F)
			compoundTag.putFloat("PrevZLerp", this.trailierTales$prevZLerp);

		if (Math.abs(this.trailierTales$rotation) > 0.1F)
			compoundTag.putFloat("Rotation", this.trailierTales$rotation);
		if (Math.abs(this.trailierTales$prevRotation) > 0.1F)
			compoundTag.putFloat("PrevRotation", this.trailierTales$prevRotation);
		if (Math.abs(this.trailierTales$targetItemScale) > 0.1F)
			compoundTag.putFloat("TargetItemScale", this.trailierTales$targetItemScale);
		if (Math.abs(this.trailierTales$itemScale) > 0.1F)
			compoundTag.putFloat("ItemScale", this.trailierTales$itemScale);
		if (Math.abs(this.trailierTales$prevItemScale) > 0.1F)
			compoundTag.putFloat("PrevItemScale", this.trailierTales$prevItemScale);
		if (this.trailierTales$hasCustomItem)
			compoundTag.putBoolean("HasCustomItem", this.trailierTales$hasCustomItem);
		if (this.brushCount != 0)
			compoundTag.putInt("BrushCount", this.brushCount);
	}

	@Unique
	public void trailierTales$readNBT(@NotNull CompoundTag compoundTag) {
		if (compoundTag.contains("TTHitDirection")) this.trailierTales$hitDirection = Direction.byName(compoundTag.getString("TTHitDirection"));
		if (compoundTag.contains("TargetXLerp")) this.trailierTales$targetXLerp = compoundTag.getFloat("TargetXLerp");
		if (compoundTag.contains("TargetYLerp")) this.trailierTales$targetYLerp = compoundTag.getFloat("TargetYLerp");
		if (compoundTag.contains("TargetZLerp")) this.trailierTales$targetZLerp = compoundTag.getFloat("TargetZLerp");
		if (compoundTag.contains("XLerp")) this.trailierTales$xLerp = compoundTag.getFloat("XLerp");
		if (compoundTag.contains("YLerp")) this.trailierTales$yLerp = compoundTag.getFloat("YLerp");
		if (compoundTag.contains("ZLerp")) this.trailierTales$zLerp = compoundTag.getFloat("ZLerp");
		if (compoundTag.contains("PrevXLerp")) this.trailierTales$prevXLerp = compoundTag.getFloat("PrevXLerp");
		if (compoundTag.contains("PrevYLerp")) this.trailierTales$prevYLerp = compoundTag.getFloat("PrevYLerp");
		if (compoundTag.contains("PrevZLerp")) this.trailierTales$prevZLerp = compoundTag.getFloat("PrevZLerp");
		if (compoundTag.contains("TargetItemScale")) this.trailierTales$targetItemScale = compoundTag.getFloat("TargetItemScale");
		if (compoundTag.contains("ItemScale")) this.trailierTales$itemScale = compoundTag.getFloat("ItemScale");
		if (compoundTag.contains("PrevItemScale")) this.trailierTales$prevItemScale = compoundTag.getFloat("PrevItemScale");
		this.trailierTales$rotation = compoundTag.getFloat("Rotation");
		this.trailierTales$prevRotation = compoundTag.getFloat("PrevRotation");
		this.trailierTales$hasCustomItem = compoundTag.getBoolean("HasCustomItem");
		this.brushCount = compoundTag.getInt("BrushCount");
	}

	@Unique
	@Override
	public boolean trailierTales$setItem(@NotNull ItemStack itemStack) {
		this.item = itemStack;
		this.trailierTales$hasCustomItem = true;
		this.trailierTales$storedLootTable = null;
		return true;
	}

	@Override
	public boolean trailierTales$hasCustomItem() {
		return this.trailierTales$hasCustomItem;
	}

	@Unique
	@Override
	public float trailierTales$getXOffset(float partialTicks) {
		return Mth.lerp(partialTicks, this.trailierTales$prevXLerp, this.trailierTales$xLerp);
	}

	@Unique
	@Override
	public float trailierTales$getYOffset(float partialTicks) {
		return Mth.lerp(partialTicks, this.trailierTales$prevYLerp, this.trailierTales$yLerp);
	}

	@Unique
	@Override
	public float trailierTales$getZOffset(float partialTicks) {
		return Mth.lerp(partialTicks, this.trailierTales$prevZLerp, this.trailierTales$zLerp);
	}

	@Unique
	@Override
	public float trailierTales$getRotation(float partialTicks) {
		return Mth.lerp(partialTicks, this.trailierTales$prevRotation, this.trailierTales$rotation);
	}

	@Unique
	@Override
	public float trailierTales$getItemScale(float partialTicks) {
		return Mth.lerp(partialTicks, this.trailierTales$prevItemScale, this.trailierTales$itemScale);
	}

	@Unique
	@Nullable
	@Override
	public Direction trailierTales$getHitDirection() {
		return this.trailierTales$hitDirection;
	}

	@Shadow
	private int getCompletionState() {
		throw new AssertionError("Mixin injection failed - Trailier Tales BrushableBlockEntityMixin.");
	}

}
