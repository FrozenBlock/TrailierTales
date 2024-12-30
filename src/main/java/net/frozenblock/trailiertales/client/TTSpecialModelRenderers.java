package net.frozenblock.trailiertales.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.block.client.entity.SpecialModelRenderersEntrypoint;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.client.renderer.special.CoffinSpecialRenderer;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TTSpecialModelRenderers implements SpecialModelRenderersEntrypoint {

	@Override
	public void registerSpecialModelRenderers(ExtraCodecs.@NotNull LateBoundIdMapper<ResourceLocation, MapCodec<? extends SpecialModelRenderer.Unbaked>> mapper) {
		mapper.put(TTConstants.id("coffin"), CoffinSpecialRenderer.Unbaked.MAP_CODEC);
	}

	@Override
	public void onMapInit(ImmutableMap.@NotNull Builder map) {
		map.put(TTBlocks.COFFIN, new CoffinSpecialRenderer.Unbaked(CoffinSpawnerState.INACTIVE.getHeadTexture(), CoffinSpawnerState.INACTIVE.getFootTexture()));
	}
}
