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

package net.frozenblock.trailiertales.levelgen.structure.modification;

import net.frozenblock.lib.levelgen.structure.api.placement.StructureGenerationConditionApi;
import net.frozenblock.lib.levelgen.structure.api.placement.StructurePlacementExclusionApi;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.data.worldgen.structure.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.CatacombsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.DeepslateRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.DesertRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.GenericRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.JungleRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.SavannaRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.SnowyRuinsGenerator;
import net.frozenblock.trailiertales.registry.TTResources;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;

public final class TTStructureModifications {

	public static void setup() {
		StructureGenerationConditionApi.ADD_GENERATION_CONDITIONS.register((structureSet, context) -> {
			if (structureSet.is(CatacombsGenerator.CATACOMBS_STRUCTURE_SET_KEY)) {
				context.add(TTWorldgenConfig.CATACOMBS_GENERATION);
				context.add(() -> !TTResources.HAS_STRONGHOLD_OVERRIDE_PACK);
			}
			if (structureSet.is(BadlandsRuinsGenerator.BADLANDS_RUINS_KEY)) context.add(TTWorldgenConfig.BADLANDS_RUINS_GENERATION);
			if (structureSet.is(DeepslateRuinsGenerator.DEEPSLATE_RUINS_KEY)) context.add(TTWorldgenConfig.DEEPSLATE_RUINS_GENERATION);
			if (structureSet.is(DesertRuinsGenerator.DESERT_RUINS_KEY)) context.add(TTWorldgenConfig.DESERT_RUINS_GENERATION);
			if (structureSet.is(GenericRuinsGenerator.RUINS_KEY)) context.add(TTWorldgenConfig.GENERIC_RUINS_GENERATION);
			if (structureSet.is(JungleRuinsGenerator.JUNGLE_RUINS_KEY)) context.add(TTWorldgenConfig.JUNGLE_RUINS_GENERATION);
			if (structureSet.is(SavannaRuinsGenerator.SAVANNA_RUINS_KEY)) context.add(TTWorldgenConfig.SAVANNA_RUINS_GENERATION);
			if (structureSet.is(SnowyRuinsGenerator.SNOWY_RUINS_KEY)) context.add(TTWorldgenConfig.SNOWY_RUINS_GENERATION);
		});

		StructurePlacementExclusionApi.ADD_PLACEMENT_EXCLUSIONS.register((structureSet, context) -> {
			if (structureSet.is(BuiltinStructureSets.TRIAL_CHAMBERS)) context.add(CatacombsGenerator.CATACOMBS_STRUCTURE_SET_KEY, 8);
			if (structureSet.is(DeepslateRuinsGenerator.DEEPSLATE_RUINS_KEY)) context.add(BuiltinStructureSets.ANCIENT_CITIES, 8);
			if (structureSet.is(DesertRuinsGenerator.DESERT_RUINS_KEY)) context.add(BuiltinStructureSets.DESERT_PYRAMIDS, 3);
		});
	}

	private TTStructureModifications() {}
}
