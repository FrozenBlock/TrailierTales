/*
 * Copyright 2025-2026 FrozenBlock
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

import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.gravity.api.GravityAPI;
import net.frozenblock.trailiertales.advancements.modification.TTAdvancementModifications;
import net.frozenblock.trailiertales.block.EctoplasmBlock;
import net.frozenblock.trailiertales.levelgen.biome.modification.TTBiomeModifications;
import net.frozenblock.trailiertales.levelgen.structure.modification.TTStructureModifications;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.registry.TTFabricBlocks;

public class TrailierTalesFabric extends FrozenModInitializer {

	public TrailierTalesFabric() {
		super(TTConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		TrailierTales.init(modId);
		TrailierTales.setup();
		TTFabricBlocks.registerBlockProperties();

		TTBiomeModifications.init();
		TTModIntegrations.init();
		TTStructureModifications.init();
		TTAdvancementModifications.init();

		GravityAPI.MODIFICATIONS.register(gravityContext -> {
			if (gravityContext.entity != null) {
				if (gravityContext.state.getBlock() instanceof EctoplasmBlock) {
					gravityContext.gravity = gravityContext.gravity.scale(EctoplasmBlock.GRAVITY_SLOWDOWN);
				}
			}
		});
	}
}
