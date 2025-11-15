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

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.frozenblock.trailiertales.registry.TTEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
public abstract class BrushableBlockEntityMixin extends BlockEntity implements BrushableBlockEntityInterface {

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

	public BrushableBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

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

	@ModifyReturnValue(method = "getUpdateTag", at = @At("RETURN"))
	public CompoundTag trailierTales$getUpdateTag(CompoundTag original) {
		this.trailierTales$saveTTToCompoundTag(original);
		return original;
	}

	@Inject(method = "loadAdditional", at = @At("TAIL"))
	public void trailierTales$loadAdditional(ValueInput valueInput, CallbackInfo info) {
		this.trailierTales$readTT(valueInput);
		this.trailierTales$rebrushed = valueInput.getBooleanOr("Rebrushed", false);
		this.trailierTales$storedLootTable = ResourceKey.create(Registries.LOOT_TABLE, Identifier.parse(valueInput.getStringOr("TrailierTalesStoredLootTable", "")));
	}

	@Inject(method = "saveAdditional", at = @At("TAIL"))
	public void trailierTales$saveAdditional(ValueOutput valueOutput, CallbackInfo info) {
		this.trailierTales$saveTT(valueOutput);
		if (this.trailierTales$rebrushed) valueOutput.putBoolean("Rebrushed", this.trailierTales$rebrushed);
		if (this.trailierTales$storedLootTable != null && this.trailierTales$storedLootTable != this.lootTable) {
			valueOutput.putString("TrailierTalesStoredLootTable", this.trailierTales$storedLootTable.identifier().toString());
		}
	}

	@Unique
	private boolean trailierTales$runRebrush;

	@Inject(
		method = "brush",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;brushingCompleted(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V",
			shift = At.Shift.BEFORE
		)
	)
	public void trailierTales$rebrushA(long ticks, ServerLevel level, LivingEntity entity, Direction direction, ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if (this.trailierTales$hasCustomItem) return;

		final int rebrushLevel = stack.getEnchantments().getLevel(entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(TTEnchantments.REBRUSH));
		if (rebrushLevel <= 0) return;

		final float rebrushChance = this.trailierTales$rebrushed ? 0.05F * rebrushLevel : 0.1F * rebrushLevel;
		if (entity.getRandom().nextFloat() < rebrushChance) this.trailierTales$runRebrush = true;
	}

	@WrapOperation(
		method = "brushingCompleted",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrushableBlock;getTurnsInto()Lnet/minecraft/world/level/block/Block;"
		)
	)
	private Block trailierTales$runRebrush(BrushableBlock instance, Operation<Block> original) {
		if (!this.trailierTales$runRebrush) return original.call(instance);
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

	@Inject(
		method = "tryLoadLootTable",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;lootTable:Lnet/minecraft/resources/ResourceKey;",
			opcode = Opcodes.PUTFIELD,
			shift = At.Shift.AFTER
		)
	)
	private void trailierTales$storeLootTable(ValueInput valueInput, CallbackInfoReturnable<Boolean> info) {
		this.trailierTales$storedLootTable = this.lootTable;
	}

	@Inject(method = "setLootTable", at = @At("HEAD"))
	public void trailierTales$setLootTable(ResourceKey<LootTable> lootTable, long lootTableSeed, CallbackInfo info) {
		if (lootTable != null) this.trailierTales$storedLootTable = lootTable;;
	}

	@Unique
	@Override
	public void trailierTales$tick() {
		final BrushableBlockEntity brushableBlockEntity = BrushableBlockEntity.class.cast(this);
		final Level level = brushableBlockEntity.getLevel();
		if (level != null && !level.isClientSide()) {
			final BlockPos pos = brushableBlockEntity.getBlockPos();
			final BlockState state = level.getBlockState(pos);
			if (state.hasProperty(TTBlockStateProperties.CAN_PLACE_ITEM)) {
				final BlockState worldState = level.getBlockState(pos);
				final boolean canPlaceItemProperty = worldState.hasProperty(TTBlockStateProperties.CAN_PLACE_ITEM) && worldState.getValue(TTBlockStateProperties.CAN_PLACE_ITEM);
				final boolean canPlaceItem = this.item.isEmpty() && this.lootTable == null;
				if (canPlaceItemProperty != canPlaceItem) level.setBlock(pos, state.setValue(TTBlockStateProperties.CAN_PLACE_ITEM, canPlaceItem), Block.UPDATE_CLIENTS);
			}
			if (this.trailierTales$storedLootTable == null && this.lootTable != null) this.trailierTales$storedLootTable = this.lootTable;
		}

		this.trailierTales$prevRotation = this.trailierTales$rotation;
		if (this.hitDirection != null) this.trailierTales$hitDirection = this.hitDirection;
		final Direction direction = this.trailierTales$getHitDirection();
		if (direction != null) {
			float[] translation = trailierTales$translations(direction, this.getCompletionState());
			this.trailierTales$targetXLerp = translation[0];
			this.trailierTales$targetYLerp = translation[1];
			this.trailierTales$targetZLerp = translation[2];
			this.trailierTales$rotation = direction.getAxis() == Direction.Axis.X ? 90F : 0F;
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
	public void trailierTales$saveTTToCompoundTag(CompoundTag tag) {
		if (this.trailierTales$hitDirection != null) tag.putString("TTHitDirection", this.trailierTales$hitDirection.getName());
		if (this.trailierTales$targetXLerp != 0.5F) tag.putFloat("TargetXLerp", this.trailierTales$targetXLerp);
		if (this.trailierTales$targetYLerp != 0F) tag.putFloat("TargetYLerp", this.trailierTales$targetYLerp);
		if (this.trailierTales$targetZLerp != 0.5F) tag.putFloat("TargetZLerp", this.trailierTales$targetZLerp);
		if (this.trailierTales$xLerp != 0.5F) tag.putFloat("XLerp", this.trailierTales$xLerp);
		if (this.trailierTales$yLerp != 0F) tag.putFloat("YLerp", this.trailierTales$yLerp);
		if (this.trailierTales$zLerp != 0.5F) tag.putFloat("ZLerp", this.trailierTales$zLerp);
		if (this.trailierTales$prevXLerp != 0.5F) tag.putFloat("PrevXLerp", this.trailierTales$prevXLerp);
		if (this.trailierTales$prevYLerp != 0F) tag.putFloat("PrevYLerp", this.trailierTales$prevYLerp);
		if (this.trailierTales$prevZLerp != 0.5F) tag.putFloat("PrevZLerp", this.trailierTales$prevZLerp);

		if (Math.abs(this.trailierTales$rotation) > 0.1F) tag.putFloat("Rotation", this.trailierTales$rotation);
		if (Math.abs(this.trailierTales$prevRotation) > 0.1F) tag.putFloat("PrevRotation", this.trailierTales$prevRotation);
		if (Math.abs(this.trailierTales$targetItemScale) > 0.1F) tag.putFloat("TargetItemScale", this.trailierTales$targetItemScale);
		if (Math.abs(this.trailierTales$itemScale) > 0.1F) tag.putFloat("ItemScale", this.trailierTales$itemScale);
		if (Math.abs(this.trailierTales$prevItemScale) > 0.1F) tag.putFloat("PrevItemScale", this.trailierTales$prevItemScale);
		if (this.trailierTales$hasCustomItem) tag.putBoolean("HasCustomItem", this.trailierTales$hasCustomItem);
		if (this.brushCount != 0) tag.putInt("BrushCount", this.brushCount);
	}

	@Unique
	public void trailierTales$saveTT(ValueOutput valueOutput) {
		if (this.trailierTales$hitDirection != null) valueOutput.putString("TTHitDirection", this.trailierTales$hitDirection.getName());
		if (this.trailierTales$targetXLerp != 0.5F) valueOutput.putFloat("TargetXLerp", this.trailierTales$targetXLerp);
		if (this.trailierTales$targetYLerp != 0F) valueOutput.putFloat("TargetYLerp", this.trailierTales$targetYLerp);
		if (this.trailierTales$targetZLerp != 0.5F) valueOutput.putFloat("TargetZLerp", this.trailierTales$targetZLerp);
		if (this.trailierTales$xLerp != 0.5F) valueOutput.putFloat("XLerp", this.trailierTales$xLerp);
		if (this.trailierTales$yLerp != 0F) valueOutput.putFloat("YLerp", this.trailierTales$yLerp);
		if (this.trailierTales$zLerp != 0.5F) valueOutput.putFloat("ZLerp", this.trailierTales$zLerp);
		if (this.trailierTales$prevXLerp != 0.5F) valueOutput.putFloat("PrevXLerp", this.trailierTales$prevXLerp);
		if (this.trailierTales$prevYLerp != 0F) valueOutput.putFloat("PrevYLerp", this.trailierTales$prevYLerp);
		if (this.trailierTales$prevZLerp != 0.5F) valueOutput.putFloat("PrevZLerp", this.trailierTales$prevZLerp);

		if (Math.abs(this.trailierTales$rotation) > 0.1F) valueOutput.putFloat("Rotation", this.trailierTales$rotation);
		if (Math.abs(this.trailierTales$prevRotation) > 0.1F) valueOutput.putFloat("PrevRotation", this.trailierTales$prevRotation);
		if (Math.abs(this.trailierTales$targetItemScale) > 0.1F) valueOutput.putFloat("TargetItemScale", this.trailierTales$targetItemScale);
		if (Math.abs(this.trailierTales$itemScale) > 0.1F) valueOutput.putFloat("ItemScale", this.trailierTales$itemScale);
		if (Math.abs(this.trailierTales$prevItemScale) > 0.1F) valueOutput.putFloat("PrevItemScale", this.trailierTales$prevItemScale);
		if (this.trailierTales$hasCustomItem) valueOutput.putBoolean("HasCustomItem", this.trailierTales$hasCustomItem);
		if (this.brushCount != 0) valueOutput.putInt("BrushCount", this.brushCount);
	}

	@Unique
	public void trailierTales$readTT(@NotNull ValueInput valueInput) {
		this.trailierTales$hitDirection = Direction.byName(valueInput.getStringOr("TTHitDirection", ""));
		this.trailierTales$targetXLerp = valueInput.getFloatOr("TargetXLerp", 0);
		this.trailierTales$targetYLerp = valueInput.getFloatOr("TargetYLerp", 0);
		this.trailierTales$targetZLerp = valueInput.getFloatOr("TargetZLerp", 0);
		this.trailierTales$xLerp = valueInput.getFloatOr("XLerp", 0);
		this.trailierTales$yLerp = valueInput.getFloatOr("YLerp", 0);
		this.trailierTales$zLerp = valueInput.getFloatOr("ZLerp", 0);
		this.trailierTales$prevXLerp = valueInput.getFloatOr("PrevXLerp", 0);
		this.trailierTales$prevYLerp = valueInput.getFloatOr("PrevYLerp", 0);
		this.trailierTales$prevZLerp = valueInput.getFloatOr("PrevZLerp", 0);
		this.trailierTales$targetItemScale = valueInput.getFloatOr("TargetItemScale", 0);
		this.trailierTales$itemScale = valueInput.getFloatOr("ItemScale", 0);
		this.trailierTales$prevItemScale = valueInput.getFloatOr("PrevItemScale", 0);
		this.trailierTales$rotation = valueInput.getFloatOr("Rotation", 0);
		this.trailierTales$prevRotation = valueInput.getFloatOr("PrevRotation", 0);
		this.trailierTales$hasCustomItem = valueInput.getBooleanOr("HasCustomItem", false);
		this.brushCount = valueInput.getIntOr("BrushCount", 0);
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
