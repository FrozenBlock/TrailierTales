package net.frozenblock.trailiertales.impl;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public interface BrushableBlockEntityInterface {

	void trailierTales$tick();

	boolean trailierTales$setItem(ItemStack itemStack);

	boolean trailierTales$hasCustomItem();

	float trailierTales$getXOffset(float partialTicks);

	float trailierTales$getYOffset(float partialTicks);

	float trailierTales$getZOffset(float partialTicks);

	float trailierTales$getRotation(float partialTicks);

	float trailierTales$getItemScale(float partialTicks);

	Direction trailierTales$getHitDirection();

}
