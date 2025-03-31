/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

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
