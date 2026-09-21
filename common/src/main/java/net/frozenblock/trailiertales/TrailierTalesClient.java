/*
 * Copyright 2026 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales;

import net.frozenblock.lib.menu.api.SplashTextEvents;
import net.frozenblock.lib.music.api.client.pitch.MusicPitchApi;
import net.frozenblock.lib.renderer.special.SpecialModelRendererRegistry;
import net.frozenblock.trailiertales.client.TTBuiltInBlockModels;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.TTParticleEngine;
import net.frozenblock.trailiertales.client.TTRenderStateDataKeys;
import net.frozenblock.trailiertales.client.renderer.special.CoffinSpecialRenderer;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import net.frozenblock.trailiertales.data.worldgen.structure.CatacombsGenerator;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.util.Mth;

@ClientOnly
public final class TrailierTalesClient {

	public static void init() {
		SplashTextEvents.ADD_SOURCE_FILES.register(sourceFiles -> sourceFiles.add(TTConstants.id("texts/splashes.txt")));

		TTBuiltInBlockModels.init();
		TTModelLayers.init();
		TTParticleEngine.init();
		TTRenderStateDataKeys.init();

		SpecialModelRendererRegistry.register(TTConstants.id("coffin"), CoffinSpecialRenderer.Unbaked.MAP_CODEC);
		MusicPitchApi.registerForStructureInside(CatacombsGenerator.CATACOMBS_KEY.identifier(), TrailierTalesClient::calculateCatacombsMusicPitch);
	}

	public static void setup() {}

	private static float calculateCatacombsMusicPitch(long gameTime) {
		if (!TTMiscConfig.DISTORTED_CATACOMBS_MUSIC.get()) return 1F;
		final float basePitch = 0.98F + Mth.sin((float) ((gameTime * Math.PI) / 1000F)) * 0.005F;
		final float additionalPitchChangeA = Mth.clamp(Mth.cos((float) ((gameTime * Math.PI) / 600F)) * 0.5F, -0.00975F, 0.00975F);
		final float additionalWobble = Mth.sin((float) ((gameTime * Math.PI) / 20F)) * 0.005F;
		return basePitch + additionalPitchChangeA + additionalWobble;
	}

	private TrailierTalesClient() {}
}
