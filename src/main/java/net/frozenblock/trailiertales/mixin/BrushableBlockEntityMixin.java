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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
	private float luna120$targetXLerp = 0.5F;
	@Unique
	private float luna120$xLerp = 0.5F;
	@Unique
	private float luna120$prevXLerp = 0.5F;
	@Unique
	private float luna120$targetYLerp = 0.0F;
	@Unique
	private float luna120$yLerp = 0.0F;
	@Unique
	private float luna120$prevYLerp = 0.0F;
	@Unique
	private float luna120$targetZLerp = 0.5F;
	@Unique
	private float luna120$zLerp = 0.5F;
	@Unique
	private float luna120$prevZLerp = 0.5F;
	@Unique
	private float luna120$rotation;
	@Unique
	private float luna120$prevRotation;
	@Unique
	private boolean luna120$hasCustomItem;
	@Unique
	@Nullable
	private Direction luna120$hitDirection;
	@Shadow
	@Nullable
	private Direction hitDirection;
	@Shadow
	private ItemStack item;

	@Unique
	private static float[] luna120$translations(@NotNull Direction direction, int i) {
		float[] fs = new float[]{0.5f, 0.0f, 0.5f};
		float f = (float) i / 9.0f;
		switch (direction) {
			case EAST -> fs[0] = 0.73f + f;
			case WEST -> fs[0] = 0.25f - f;
			case UP -> fs[1] = 0.25f + f;
			case DOWN -> fs[1] = -0.23f - f;
			case NORTH -> fs[2] = 0.25f - f;
			case SOUTH -> fs[2] = 0.73f + f;
		}
		return fs;
	}

	@Inject(method = "getUpdateTag", at = @At("RETURN"))
	public void luna120$getUpdateTag(CallbackInfoReturnable<CompoundTag> info) {
		this.luna120$saveLunaNBT(info.getReturnValue());
	}

	@Inject(method = "loadAdditional", at = @At("TAIL"))
	public void luna120$load(CompoundTag compoundTag, HolderLookup.Provider provider, CallbackInfo info) {
		this.luna120$readLunaNBT(compoundTag);
	}

	@Inject(method = "saveAdditional", at = @At("TAIL"))
	public void luna120$saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider, CallbackInfo info) {
		this.luna120$saveLunaNBT(compoundTag);
	}

	@Unique
	@Override
	public void luna120$tick() {
		BrushableBlockEntity brushableBlockEntity = BrushableBlockEntity.class.cast(this);
		Level level = brushableBlockEntity.getLevel();
		if (level != null && !level.isClientSide) {
			BlockPos blockPos = brushableBlockEntity.getBlockPos();
			BlockState blockState = level.getBlockState(blockPos);
			if (blockState.hasProperty(RegisterProperties.CAN_PLACE_ITEM)) {
				boolean canPlaceItemProperty = level.getBlockState(blockPos).getValue(RegisterProperties.CAN_PLACE_ITEM);
				boolean canPlaceItem = this.item.isEmpty() && this.lootTable == null;
				if (canPlaceItem) {
					this.luna120$hasCustomItem = false;
				}
				if (canPlaceItemProperty != canPlaceItem) {
					level.setBlock(blockPos, blockState.setValue(RegisterProperties.CAN_PLACE_ITEM, canPlaceItem), 3);
				}
			}
		}
		this.luna120$prevRotation = this.luna120$rotation;
		if (this.hitDirection != null) {
			this.luna120$hitDirection = this.hitDirection;
		}
		Direction direction = this.luna120$getHitDirection();
		if (direction != null) {
			int dusted = BrushableBlockEntity.class.cast(this).getBlockState().getValue(BlockStateProperties.DUSTED);
			float[] translation = luna120$translations(direction, dusted);
			this.luna120$targetXLerp = translation[0];
			this.luna120$targetYLerp = translation[1];
			this.luna120$targetZLerp = translation[2];
			if (direction.getAxis() == Direction.Axis.X) {
				this.luna120$rotation = 90.0f;
			} else {
				this.luna120$rotation = 0.0f;
			}
		}
		this.luna120$prevXLerp = this.luna120$xLerp;
		this.luna120$prevYLerp = this.luna120$yLerp;
		this.luna120$prevZLerp = this.luna120$zLerp;

		this.luna120$xLerp += (this.luna120$targetXLerp - this.luna120$xLerp) * 0.2F;
		this.luna120$yLerp += (this.luna120$targetYLerp - this.luna120$yLerp) * 0.2F;
		this.luna120$zLerp += (this.luna120$targetZLerp - this.luna120$zLerp) * 0.2F;
	}

	@Unique
	public void luna120$saveLunaNBT(CompoundTag compoundTag) {
		if (this.luna120$hitDirection != null) {
			compoundTag.putString("LunaHitDirection", this.luna120$hitDirection.getName());
		}
		if (this.luna120$targetXLerp != 0.5F) compoundTag.putFloat("TargetXLerp", this.luna120$targetXLerp);
		if (this.luna120$targetYLerp != 0F) compoundTag.putFloat("TargetYLerp", this.luna120$targetYLerp);
		if (this.luna120$targetZLerp != 0.5F) compoundTag.putFloat("TargetZLerp", this.luna120$targetZLerp);
		if (this.luna120$xLerp != 0.5F) compoundTag.putFloat("XLerp", this.luna120$xLerp);
		if (this.luna120$yLerp != 0F) compoundTag.putFloat("YLerp", this.luna120$yLerp);
		if (this.luna120$zLerp != 0.5F) compoundTag.putFloat("ZLerp", this.luna120$zLerp);
		if (this.luna120$prevXLerp != 0.5F) compoundTag.putFloat("PrevXLerp", this.luna120$prevXLerp);
		if (this.luna120$prevYLerp != 0F) compoundTag.putFloat("PrevYLerp", this.luna120$prevYLerp);
		if (this.luna120$prevZLerp != 0.5F) compoundTag.putFloat("PrevZLerp", this.luna120$prevZLerp);
		if (this.luna120$rotation != 0F) compoundTag.putFloat("Rotation", this.luna120$rotation);
		if (this.luna120$prevRotation != 0F) compoundTag.putFloat("PrevRotation", this.luna120$prevRotation);
		if (this.luna120$hasCustomItem) compoundTag.putBoolean("HasCustomItem", this.luna120$hasCustomItem);
	}

	@Unique
	public void luna120$readLunaNBT(@NotNull CompoundTag compoundTag) {
		if (compoundTag.contains("LunaHitDirection")) {
			this.luna120$hitDirection = Direction.byName(compoundTag.getString("LunaHitDirection"));
		}
		if (compoundTag.contains("TargetXLerp")) {
			this.luna120$targetXLerp = compoundTag.getFloat("TargetXLerp");
		}
		if (compoundTag.contains("TargetYLerp")) {
			this.luna120$targetYLerp = compoundTag.getFloat("TargetYLerp");
		}
		if (compoundTag.contains("TargetZLerp")) {
			this.luna120$targetZLerp = compoundTag.getFloat("TargetZLerp");
		}
		if (compoundTag.contains("XLerp")) {
			this.luna120$xLerp = compoundTag.getFloat("XLerp");
		}
		if (compoundTag.contains("YLerp")) {
			this.luna120$yLerp = compoundTag.getFloat("YLerp");
		}
		if (compoundTag.contains("ZLerp")) {
			this.luna120$zLerp = compoundTag.getFloat("ZLerp");
		}
		if (compoundTag.contains("PrevXLerp")) {
			this.luna120$prevXLerp = compoundTag.getFloat("PrevXLerp");
		}
		if (compoundTag.contains("PrevYLerp")) {
			this.luna120$prevYLerp = compoundTag.getFloat("PrevYLerp");
		}
		if (compoundTag.contains("PrevZLerp")) {
			this.luna120$prevZLerp = compoundTag.getFloat("PrevZLerp");
		}
		this.luna120$rotation = compoundTag.getFloat("Rotation");
		this.luna120$prevRotation = compoundTag.getFloat("PrevRotation");
		this.luna120$hasCustomItem = compoundTag.getBoolean("HasCustomItem");
	}

	@Unique
	@Override
	public boolean luna120$setItem(@NotNull ItemStack itemStack) {
		this.item = itemStack;
		this.luna120$hasCustomItem = true;
		return true;
	}

	@Override
	public boolean luna120$hasCustomItem() {
		return this.luna120$hasCustomItem;
	}

	@Unique
	@Override
	public float luna120$getXOffset(float partialTicks) {
		return Mth.lerp(partialTicks, this.luna120$prevXLerp, this.luna120$xLerp);
	}

	@Unique
	@Override
	public float luna120$getYOffset(float partialTicks) {
		return Mth.lerp(partialTicks, this.luna120$prevYLerp, this.luna120$yLerp);
	}

	@Unique
	@Override
	public float luna120$getZOffset(float partialTicks) {
		return Mth.lerp(partialTicks, this.luna120$prevZLerp, this.luna120$zLerp);
	}

	@Unique
	@Override
	public float luna120$getRotation(float partialTicks) {
		return Mth.lerp(partialTicks, this.luna120$prevRotation, this.luna120$rotation);
	}

	@Unique
	@Nullable
	@Override
	public Direction luna120$getHitDirection() {
		return this.luna120$hitDirection;
	}

}
