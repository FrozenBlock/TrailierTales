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

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.levelgen.structure.api.placement.StructureGenerationConditionApi;
import net.frozenblock.lib.levelgen.structure.api.placement.StructurePlacementExclusionApi;
import net.frozenblock.lib.levelgen.structure.api.processor.StructureProcessorApi;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.data.worldgen.structure.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.CatacombsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.DeepslateRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.DesertRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.GenericRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.JungleRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.SavannaRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.SnowyRuinsGenerator;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;

public class TTStructureModifications {

	public static void init() {
		StructureGenerationConditionApi.addGenerationCondition(CatacombsGenerator.CATACOMBS_STRUCTURE_SET_KEY.identifier(), TTWorldgenConfig.CATACOMBS_GENERATION);
		StructureGenerationConditionApi.addGenerationCondition(BadlandsRuinsGenerator.BADLANDS_RUINS_KEY.identifier(), TTWorldgenConfig.BADLANDS_RUINS_GENERATION);
		StructureGenerationConditionApi.addGenerationCondition(DeepslateRuinsGenerator.DEEPSLATE_RUINS_KEY.identifier(), TTWorldgenConfig.DEEPSLATE_RUINS_GENERATION);
		StructureGenerationConditionApi.addGenerationCondition(DesertRuinsGenerator.DESERT_RUINS_KEY.identifier(), TTWorldgenConfig.DESERT_RUINS_GENERATION);
		StructureGenerationConditionApi.addGenerationCondition(GenericRuinsGenerator.RUINS_KEY.identifier(), TTWorldgenConfig.GENERIC_RUINS_GENERATION);
		StructureGenerationConditionApi.addGenerationCondition(JungleRuinsGenerator.JUNGLE_RUINS_KEY.identifier(), TTWorldgenConfig.JUNGLE_RUINS_GENERATION);
		StructureGenerationConditionApi.addGenerationCondition(SavannaRuinsGenerator.SAVANNA_RUINS_KEY.identifier(), TTWorldgenConfig.SAVANNA_RUINS_GENERATION);
		StructureGenerationConditionApi.addGenerationCondition(SnowyRuinsGenerator.SNOWY_RUINS_KEY.identifier(), TTWorldgenConfig.SNOWY_RUINS_GENERATION);

		StructurePlacementExclusionApi.addExclusion(
			BuiltinStructureSets.TRIAL_CHAMBERS.identifier(),
			CatacombsGenerator.CATACOMBS_STRUCTURE_SET_KEY.identifier(),
			8
		);

		StructurePlacementExclusionApi.addExclusion(
			DeepslateRuinsGenerator.DEEPSLATE_RUINS_KEY.identifier(),
			BuiltinStructureSets.ANCIENT_CITIES.identifier(),
			8
		);

		StructurePlacementExclusionApi.addExclusion(
			DesertRuinsGenerator.DESERT_RUINS_KEY.identifier(),
			BuiltinStructureSets.DESERT_PYRAMIDS.identifier(),
			3
		);

		if (TTWorldgenConfig.END_CITY_CRACKED_GENERATION.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.identifier(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, TTBlocks.CRACKED_END_STONE_BRICKS.get().defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.2F), AlwaysTrueTest.INSTANCE, TTBlocks.CRACKED_PURPUR_BLOCK.get().defaultBlockState())
					)
				)
			);
		}

		if (TTWorldgenConfig.END_CITY_CHORAL_GENERATION.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.identifier(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, TTBlocks.CHORAL_END_STONE_BRICKS.get().defaultBlockState())
					)
				)
			);
		}

		if (TTWorldgenConfig.END_CITY_CHISELED_GENERATION.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.identifier(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockStateMatchTest(Blocks.PURPUR_PILLAR.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.4F), AlwaysTrueTest.INSTANCE, TTBlocks.CHISELED_PURPUR_BLOCK.get().defaultBlockState()),
						new ProcessorRule(new RandomBlockStateMatchTest(Blocks.PURPUR_PILLAR.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.4F), AlwaysTrueTest.INSTANCE, TTBlocks.CHISELED_PURPUR_BLOCK.get().defaultBlockState())
					)
				)
			);
		}
	}
}
