package net.lunade.onetwenty.mixin;

import net.lunade.onetwenty.interfaces.BrushableBlockEntityInterface;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public class BrushableBlockEntityMixin implements BrushableBlockEntityInterface {

	@Unique
	private float luna120$targetXLerp;
	@Unique
	private float luna120$xLerp;
	@Unique
	private float luna120$prevXLerp;
	@Unique
	private float luna120$targetYLerp;
	@Unique
	private float luna120$yLerp;
	@Unique
	private float luna120$prevYLerp;
	@Unique
	private float luna120$targetZLerp;
	@Unique
	private float luna120$zLerp;
	@Unique
	private float luna120$prevZLerp;

	@Unique
	private float luna120$rotation;
	@Unique
	private float luna120$prevRotation;

	@Unique
	@Nullable
	private Direction luna120$hitDirection;

	@Shadow
	@Nullable
	private Direction hitDirection;

	@Shadow
	private ItemStack item;

	@Inject(method = "getUpdateTag", at = @At("RETURN"))
	public void luna120$getUpdateTag(CallbackInfoReturnable<CompoundTag> info) {
		this.luna120$saveLunaNBT(info.getReturnValue());
	}

	@Inject(method = "load", at = @At("TAIL"))
	public void luna120$load(CompoundTag compoundTag, CallbackInfo info) {
		this.luna120$readLunaNBT(compoundTag);
	}

	@Inject(method = "saveAdditional", at = @At("TAIL"))
	public void luna120$saveAdditional(CompoundTag compoundTag, CallbackInfo info) {
		this.luna120$saveLunaNBT(compoundTag);
	}

	@Unique
	public void luna120$saveLunaNBT(CompoundTag compoundTag) {
		if (this.luna120$hitDirection != null) {
			compoundTag.putString("LunaHitDirection", this.luna120$hitDirection.getName());
		}
		compoundTag.putFloat("TargetXLerp", this.luna120$targetXLerp);
		compoundTag.putFloat("TargetYLerp", this.luna120$targetYLerp);
		compoundTag.putFloat("TargetZLerp", this.luna120$targetZLerp);
		compoundTag.putFloat("XLerp", this.luna120$xLerp);
		compoundTag.putFloat("YLerp", this.luna120$yLerp);
		compoundTag.putFloat("ZLerp", this.luna120$zLerp);
		compoundTag.putFloat("PrevXLerp", this.luna120$prevXLerp);
		compoundTag.putFloat("PrevYLerp", this.luna120$prevYLerp);
		compoundTag.putFloat("PrevZLerp", this.luna120$prevZLerp);
		compoundTag.putFloat("Rotation", this.luna120$rotation);
		compoundTag.putFloat("PrevRotation", this.luna120$prevRotation);
	}

	@Unique
	public void luna120$readLunaNBT(CompoundTag compoundTag) {
		if (compoundTag.contains("LunaHitDirection")) {
			this.luna120$hitDirection = Direction.byName(compoundTag.getString("LunaHitDirection"));
		}
		this.luna120$targetXLerp = compoundTag.getFloat("TargetXLerp");
		this.luna120$targetYLerp = compoundTag.getFloat("TargetYLerp");
		this.luna120$targetZLerp = compoundTag.getFloat("TargetZLerp");
		this.luna120$xLerp = compoundTag.getFloat("XLerp");
		this.luna120$yLerp = compoundTag.getFloat("YLerp");
		this.luna120$zLerp = compoundTag.getFloat("ZLerp");
		this.luna120$prevXLerp = compoundTag.getFloat("PrevXLerp");
		this.luna120$prevYLerp = compoundTag.getFloat("PrevYLerp");
		this.luna120$prevZLerp = compoundTag.getFloat("PrevZLerp");
		this.luna120$rotation = compoundTag.getFloat("Rotation");
		this.luna120$prevRotation = compoundTag.getFloat("PrevRotation");
	}

	@Unique
	@Override
	public void luna120$tick() {
		this.luna120$prevRotation = this.luna120$rotation;
		if (this.hitDirection != null) {
			this.luna120$hitDirection = this.hitDirection;
		}
		Direction direction = this.luna120$getHitDirection();
		if (direction != null) {
			int dusted = BrushableBlockEntity.class.cast(this).getBlockState().getValue(BlockStateProperties.DUSTED);
			float[] translation = luna120$translations(direction, dusted);
			this.luna120$targetXLerp =  translation[0];
			this.luna120$targetYLerp = translation[1];
			this.luna120$targetZLerp = translation[2];
			if (direction.getAxis().equals(Direction.Axis.X)) {
				this.luna120$rotation = 90F;
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
	@Override
	public boolean luna120$setItem(ItemStack itemStack) {
		if (this.item == null || this.item.isEmpty()) {
			this.item = itemStack.split(1);
			return true;
		}
		return false;
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

	@Unique
	private static float[] luna120$translations(Direction direction, int i) {
		float[] fs = new float[]{0.5f, 0.0f, 0.5f};
		float f = (float)i / 9.0f;
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

	@Nullable
	@Shadow
	public Direction getHitDirection() {
		throw new AssertionError("Mixin Injection Failed - Luna 1.20 BrushableBlockEntityMixin.");
	}
}
