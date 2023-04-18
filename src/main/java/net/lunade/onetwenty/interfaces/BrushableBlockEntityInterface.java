package net.lunade.onetwenty.interfaces;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public interface BrushableBlockEntityInterface {

	void luna120$tick();

	boolean luna120$setItem(ItemStack itemStack);

	float luna120$getXOffset(float partialTicks);
	float luna120$getYOffset(float partialTicks);
	float luna120$getZOffset(float partialTicks);
	float luna120$getRotation(float partialTicks);

	Direction luna120$getHitDirection();

}
