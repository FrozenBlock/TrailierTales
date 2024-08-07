package net.frozenblock.trailiertales.impl;

import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.item.ItemStack;

public interface BoatBannerInterface {
	ItemStack trailierTales$getBanner();
	void trailierTales$setBanner(ItemStack stack);
	WalkAnimationState trailierTales$getWalkAnimationState();
}
