package net.frozenblock.trailiertales.mixin;

import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.frozenblock.trailiertales.impl.FallingBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityItemMixin implements FallingBlockEntityInterface {

	@Shadow
	private BlockState blockState;

	@Unique
	private ItemStack luna120$itemStack = ItemStack.EMPTY;

	@Override
	public boolean luna120$setItem(ItemStack itemStack) {
		this.luna120$itemStack = itemStack;
		return true;
	}

	@Override
	public ItemStack luna120$getItem() {
		return this.luna120$itemStack;
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
	public void luna120$dropItem(CallbackInfo info) {
		FallingBlockEntity.class.cast(this).spawnAtLocation(this.luna120$itemStack);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;discard()V", shift = At.Shift.BEFORE))
	public void luna120$tick(CallbackInfo info) {
		FallingBlockEntity fallingBlockEntity = FallingBlockEntity.class.cast(this);
		if (!fallingBlockEntity.level().isClientSide) {
			BlockPos blockPos = fallingBlockEntity.blockPosition();

			if (this.luna120$itemStack != ItemStack.EMPTY && this.blockState.getBlock() instanceof BrushableBlock) {
				BrushableBlockEntity brushableBlockEntity = new BrushableBlockEntity(blockPos, this.blockState);
				fallingBlockEntity.level().setBlockEntity(brushableBlockEntity);
				((BrushableBlockEntityInterface) brushableBlockEntity).luna120$setItem(this.luna120$itemStack);
			}

		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void luna120$addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo info) {
		if (this.luna120$itemStack != null && !this.luna120$itemStack.isEmpty()) {
			FallingBlockEntity fallingBlockEntity = FallingBlockEntity.class.cast(this);
			compoundTag.put("LunaOneTwentyItem", this.luna120$itemStack.save(fallingBlockEntity.level().registryAccess(), new CompoundTag()));
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void luna120$readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo info) {
		if (compoundTag.contains("LunaOneTwentyItem")) {
			FallingBlockEntity fallingBlockEntity = FallingBlockEntity.class.cast(this);
			this.luna120$itemStack = ItemStack.parseOptional(fallingBlockEntity.level().registryAccess(), compoundTag.getCompound("LunaOneTwentyItem"));
		}
	}

}
