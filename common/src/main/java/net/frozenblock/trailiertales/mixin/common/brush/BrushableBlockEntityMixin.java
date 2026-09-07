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

package net.frozenblock.trailiertales.mixin.common.brush;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.block.impl.BrushableBlockAnimationState;
import net.frozenblock.trailiertales.registry.TTAttachmentTypes;
import net.frozenblock.trailiertales.registry.TTEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin extends BlockEntity {
	@Shadow
	@Nullable
	private Direction hitDirection;
	@Shadow
	@Nullable
	public ResourceKey<LootTable> lootTable;
	@Shadow
	private long lootTableSeed;
	@Shadow
	private int brushCount;

	public BrushableBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@WrapOperation(
		method = "brushingCompleted",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrushableBlock;getTurnsInto()Lnet/minecraft/world/level/block/Block;"
		)
	)
	private Block trailierTales$runRebrush(
		BrushableBlock instance, Operation<Block> original,
		ServerLevel level, LivingEntity user, ItemStack brush
	) {
		final int rebrushLevel = brush.getEnchantments().getLevel(user.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(TTEnchantments.REBRUSH));
		if (rebrushLevel <= 0) return original.call(instance);

		final float rebrushChance = TTAttachmentTypes.BRUSHABLE_BLOCK_REBRUSHED.getAttachedOrElse(this, false) ? 0.05F * rebrushLevel : 0.1F * rebrushLevel;
		final boolean rebrush = user.getRandom().nextFloat() < rebrushChance;
		if (!rebrush) return original.call(instance);

		TTAttachmentTypes.BRUSHABLE_BLOCK_REBRUSHED.set(this, true);
		final ResourceKey<LootTable> storedLootTable = TTAttachmentTypes.BRUSHABLE_BLOCK_STORED_LOOT_TABLE.get(this);
		if (storedLootTable != null) this.lootTable = storedLootTable;
		this.brushCount = 0;
		this.hitDirection = null;
		this.lootTableSeed = 0L;
		TTAttachmentTypes.BRUSHABLE_BLOCK_ANIMATION_STATE.set(this, BrushableBlockAnimationState.create());
		this.setChanged();
		return instance;
	}

	@Inject(method = "unpackLootTable", at = @At(value = "HEAD"))
	private void trailierTales$unpackLootTable(ServerLevel level, LivingEntity user, ItemInstance brush, CallbackInfo info) {
		if (this.lootTable != null) TTAttachmentTypes.BRUSHABLE_BLOCK_STORED_LOOT_TABLE.set(this, this.lootTable);
	}

	@Inject(method = "tryLoadLootTable", at = @At(value = "RETURN"))
	private void trailierTales$tryLoadLootTable(ValueInput input, CallbackInfoReturnable<Boolean> info) {
		TTAttachmentTypes.BRUSHABLE_BLOCK_STORED_LOOT_TABLE.set(this, this.lootTable);
	}

	@Inject(method = "setLootTable", at = @At("HEAD"))
	public void trailierTales$setLootTable(ResourceKey<LootTable> lootTable, long seed, CallbackInfo info) {
		if (lootTable != null) TTAttachmentTypes.BRUSHABLE_BLOCK_STORED_LOOT_TABLE.set(this, lootTable);
	}
}
