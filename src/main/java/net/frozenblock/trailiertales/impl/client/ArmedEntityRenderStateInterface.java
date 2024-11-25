package net.frozenblock.trailiertales.impl.client;

import net.minecraft.world.item.ItemStack;

public interface ArmedEntityRenderStateInterface {
	void trailierTales$setLeftHandItemStack(ItemStack itemStack);
	void trailierTales$setRightHandItemStack(ItemStack itemStack);

	ItemStack trailierTales$getLeftHandItemStack();
	ItemStack trailierTales$getRightHandItemStack();
}
