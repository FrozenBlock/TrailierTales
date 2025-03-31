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

package net.frozenblock.trailiertales;

import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.lib.gravity.api.GravityAPI;
import net.frozenblock.trailiertales.block.EctoplasmBlock;
import net.frozenblock.trailiertales.datafix.trailiertales.TTDataFixer;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.networking.TTNetworking;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEnchantments;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.registry.TTJukeboxSongs;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.frozenblock.trailiertales.registry.TTMapDecorationTypes;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTPotions;
import net.frozenblock.trailiertales.registry.TTRecipeTypes;
import net.frozenblock.trailiertales.registry.TTResources;
import net.frozenblock.trailiertales.registry.TTRuleBlockEntityModifiers;
import net.frozenblock.trailiertales.registry.TTSensorTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.registry.TTStructurePieceTypes;
import net.frozenblock.trailiertales.registry.TTStructureTypes;
import net.frozenblock.trailiertales.registry.TTTrimPatterns;
import net.frozenblock.trailiertales.registry.TTVillagerTrades;
import net.frozenblock.trailiertales.worldgen.TTBiomeModifications;

public class TrailierTales extends FrozenModInitializer {

	public TrailierTales() {
		super(TTConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		if (FrozenBools.IS_DATAGEN) {
			TTFeatureFlags.init();
			FeatureFlagApi.rebuild();
		}

		TTDataFixer.applyDataFixes(container);
		TTStructureTypes.init();
		TTStructurePieceTypes.init();
		TTMapDecorationTypes.init();
		TTBlocks.init();
		TTBlockEntityTypes.register();
		TTItems.init();
		TTTrimPatterns.init();
		TTEntityTypes.init();
		TTMemoryModuleTypes.register();
		TTSensorTypes.register();
		TTRecipeTypes.init();
		TTBiomeModifications.init();
		TTLootTables.init();
		TTSounds.init();
		TTParticleTypes.init();
		TTEnchantments.init();
		TTMobEffects.init();
		TTPotions.init();
		TTJukeboxSongs.init();
		TTRuleBlockEntityModifiers.init();
		TTVillagerTrades.init();
		TTNetworking.init();

		TTBlocks.registerBlockProperties();

		TTModIntegrations.init();

		TTResources.init(container);

		GravityAPI.MODIFICATIONS.register(gravityContext -> {
			if (gravityContext.entity != null) {
				if (gravityContext.state.getBlock() instanceof EctoplasmBlock) {
					gravityContext.gravity = gravityContext.gravity.scale(EctoplasmBlock.GRAVITY_SLOWDOWN);
				}
			}
		});
	}
}
