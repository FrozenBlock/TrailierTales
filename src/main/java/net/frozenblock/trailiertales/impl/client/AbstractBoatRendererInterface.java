package net.frozenblock.trailiertales.impl.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public interface AbstractBoatRendererInterface {
	void trailierTales$setBannerBaseTexture(ResourceLocation texture);

	ResourceLocation trailierTales$getBannerBaseTexture();

	void trailierTales$setRaft(boolean raft);
}
