package net.frozenblock.trailiertales.interfaces;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public interface BrushableBlockEntityInterface {

	void luna120$tick();

	boolean luna120$setItem(ItemStack itemStack);

	boolean luna120$hasCustomItem();

	float luna120$getXOffset(float partialTicks);

	float luna120$getYOffset(float partialTicks);

	float luna120$getZOffset(float partialTicks);

	float luna120$getRotation(float partialTicks);

	Direction luna120$getHitDirection();

}
