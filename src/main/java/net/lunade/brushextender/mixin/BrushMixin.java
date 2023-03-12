package net.lunade.brushextender.mixin;

import net.lunade.brushextender.impl.Brushable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public class BrushMixin {

	@Unique
	BlockHitResult brushExtender$blockHitResult;
	@Unique
	BlockState brushExtender$blockState;

	@ModifyVariable(method = "onUseTick", at = @At("STORE"), ordinal = 0)
	public BlockHitResult brushExtender$captureBlockHitResult(BlockHitResult blockHitResult) {
		this.brushExtender$blockHitResult = blockHitResult;
		return blockHitResult;
	}

	@ModifyVariable(method = "onUseTick", at = @At("STORE"), ordinal = 0)
	public BlockState brushExtender$captureBlockHitResult(BlockState blockState) {
		this.brushExtender$blockState = blockState;
		return blockState;
	}

	@Redirect(method = "onUseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z"))
	public boolean brushExtender$cancelLogic(Level level) {
		level.isClientSide();
		return true;
	}

	@Inject(method = "onUseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z", shift = At.Shift.BEFORE))
	public void brushExtender$extendBrush(Level level, LivingEntity livingEntity2, ItemStack itemStack, int i, CallbackInfo info) {
		this.brushExtender$extendedBrush(level, livingEntity2, itemStack, i);
	}

	@Unique
	private void brushExtender$extendedBrush(Level level, LivingEntity livingEntity2, ItemStack itemStack, int i) {
		if (!level.isClientSide() && this.brushExtender$blockHitResult != null && this.brushExtender$blockState != null) {
			BlockPos blockPos = this.brushExtender$blockHitResult.getBlockPos();
			BlockEntity blockEntity = level.getBlockEntity(blockPos);
			boolean hasBrushed = false;
			if (blockEntity instanceof Brushable brushable) {
				hasBrushed = brushable.brush(level.getGameTime(), level, (Player)livingEntity2, this.brushExtender$blockHitResult.getDirection(), blockPos, this.brushExtender$blockState);
			} else if (this.brushExtender$blockState.getBlock() instanceof Brushable brushable) {
				hasBrushed = brushable.brush(level.getGameTime(), level, (Player)livingEntity2, this.brushExtender$blockHitResult.getDirection(), blockPos, this.brushExtender$blockState);
			}
			if (hasBrushed) {
				itemStack.hurtAndBreak(1, livingEntity2, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
	}

}
