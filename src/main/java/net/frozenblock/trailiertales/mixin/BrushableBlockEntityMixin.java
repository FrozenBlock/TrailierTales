package net.frozenblock.trailiertales.mixin;

import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.frozenblock.trailiertales.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin implements BrushableBlockEntityInterface {

	@Shadow
	@Nullable
	public ResourceKey<LootTable> lootTable;
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
	private boolean trailierTales$hasCustomItem;
	@Unique
	private float trailierTales$targetItemScale = 0F;
	@Unique
	private float trailierTales$itemScale = 0F;
	@Unique
	private float trailierTales$prevItemScale = 0F;
	@Unique
	@Nullable
	private Direction trailierTales$hitDirection;
	@Shadow
	@Nullable
	private Direction hitDirection;
	@Shadow
	private ItemStack item;

	@Shadow
	private int brushCount;

	@Unique
	private static float[] trailierTales$translations(@NotNull Direction direction, int i) {
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
		this.trailierTales$saveLunaNBT(info.getReturnValue());
	}

	@Inject(method = "loadAdditional", at = @At("TAIL"))
	public void trailierTales$load(CompoundTag compoundTag, HolderLookup.Provider provider, CallbackInfo info) {
		this.trailierTales$readLunaNBT(compoundTag);
	}

	@Inject(method = "saveAdditional", at = @At("TAIL"))
	public void trailierTales$saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider, CallbackInfo info) {
		this.trailierTales$saveLunaNBT(compoundTag);
	}

	@Unique
	@Override
	public void trailierTales$tick() {
		BrushableBlockEntity brushableBlockEntity = BrushableBlockEntity.class.cast(this);
		Level level = brushableBlockEntity.getLevel();
		if (level != null && !level.isClientSide) {
			BlockPos blockPos = brushableBlockEntity.getBlockPos();
			BlockState blockState = level.getBlockState(blockPos);
			if (blockState.hasProperty(RegisterProperties.CAN_PLACE_ITEM)) {
				boolean canPlaceItemProperty = level.getBlockState(blockPos).getValue(RegisterProperties.CAN_PLACE_ITEM);
				boolean canPlaceItem = this.item.isEmpty() && this.lootTable == null;
				if (canPlaceItem) {
					this.trailierTales$hasCustomItem = false;
				}
				if (canPlaceItemProperty != canPlaceItem) {
					level.setBlock(blockPos, blockState.setValue(RegisterProperties.CAN_PLACE_ITEM, canPlaceItem), BrushableBlockMixin.UPDATE_ALL);
				}
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
	public void trailierTales$saveLunaNBT(CompoundTag compoundTag) {
		if (this.trailierTales$hitDirection != null) {
			compoundTag.putString("TTHitDirection", this.trailierTales$hitDirection.getName());
		}
		compoundTag.putFloat("TargetXLerp", this.trailierTales$targetXLerp);
		compoundTag.putFloat("TargetYLerp", this.trailierTales$targetYLerp);
		compoundTag.putFloat("TargetZLerp", this.trailierTales$targetZLerp);
		compoundTag.putFloat("XLerp", this.trailierTales$xLerp);
		compoundTag.putFloat("YLerp", this.trailierTales$yLerp);
		compoundTag.putFloat("ZLerp", this.trailierTales$zLerp);
		compoundTag.putFloat("PrevXLerp", this.trailierTales$prevXLerp);
		compoundTag.putFloat("PrevYLerp", this.trailierTales$prevYLerp);
		compoundTag.putFloat("PrevZLerp", this.trailierTales$prevZLerp);
		compoundTag.putFloat("Rotation", this.trailierTales$rotation);
		compoundTag.putFloat("PrevRotation", this.trailierTales$prevRotation);
		compoundTag.putFloat("TargetItemScale", this.trailierTales$targetItemScale);
		compoundTag.putFloat("ItemScale", this.trailierTales$itemScale);
		compoundTag.putFloat("PrevItemScale", this.trailierTales$prevItemScale);
		compoundTag.putBoolean("HasCustomItem", true);
		compoundTag.putInt("BrushCount", this.brushCount);
	}

	@Unique
	public void trailierTales$readLunaNBT(@NotNull CompoundTag compoundTag) {
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
