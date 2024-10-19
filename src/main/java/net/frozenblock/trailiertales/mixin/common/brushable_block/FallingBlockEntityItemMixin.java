package net.frozenblock.trailiertales.mixin.common.brushable_block;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.trailiertales.impl.FallingBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
		FallingBlockEntity fallingBlockEntity = FallingBlockEntity.class.cast(this);
		if (fallingBlockEntity.level() instanceof ServerLevel level && this.trailierTales$itemStack != ItemStack.EMPTY) {
			fallingBlockEntity.spawnAtLocation(level, this.trailierTales$itemStack.copy());
			this.trailierTales$itemStack = ItemStack.EMPTY;
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo info) {
		if (this.trailierTales$itemStack != null && !this.trailierTales$itemStack.isEmpty()) {
			FallingBlockEntity fallingBlockEntity = FallingBlockEntity.class.cast(this);
			compoundTag.put("TrailierTalesItem", this.trailierTales$itemStack.save(fallingBlockEntity.level().registryAccess(), new CompoundTag()));
		}

		if (this.trailierTales$overrideBreak) {
			compoundTag.putBoolean("TrailierTalesOverrideBreak", true);
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo info) {
		if (compoundTag.contains("TrailierTalesItem")) {
			FallingBlockEntity fallingBlockEntity = FallingBlockEntity.class.cast(this);
			this.trailierTales$itemStack = ItemStack.parseOptional(fallingBlockEntity.level().registryAccess(), compoundTag.getCompound("TrailierTalesItem"));
		}

		if (compoundTag.contains("TrailierTalesOverrideBreak")) {
			this.trailierTales$overrideBreak = compoundTag.getBoolean("TrailierTalesOverrideBreak");
		}
	}

}
