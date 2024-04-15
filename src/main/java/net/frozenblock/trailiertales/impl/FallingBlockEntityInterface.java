package net.frozenblock.trailiertales.impl;

import net.minecraft.world.item.ItemStack;

public interface FallingBlockEntityInterface {
	boolean trailierTales$setItem(ItemStack itemStack);

	ItemStack trailierTales$getItem();

	void trailierTales$overrideBreak();

}
