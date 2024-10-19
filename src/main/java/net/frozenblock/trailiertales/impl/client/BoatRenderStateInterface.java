package net.frozenblock.trailiertales.impl.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public interface BoatRenderStateInterface {
	void trailierTales$setWalkAnimationPos(float pos);

	float trailierTales$getWalkAnimationPos();

	void trailierTales$setWalkAnimationSpeed(float speed);

	float trailierTales$getWalkAnimationSpeed();

	void trailierTales$setBanner(ItemStack stack);

	ItemStack trailierTales$getBanner();
}
