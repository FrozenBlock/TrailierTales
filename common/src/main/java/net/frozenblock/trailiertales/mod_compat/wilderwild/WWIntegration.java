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

package net.frozenblock.trailiertales.mod_compat.wilderwild;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.levelgen.structure.api.processor.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.levelgen.structure.api.processor.BlockStateRespectingRuleProcessor;
import net.frozenblock.lib.levelgen.structure.api.processor.StructureProcessorApi;
import net.frozenblock.trailiertales.data.worldgen.structure.CatacombsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.SavannaRuinsGenerator;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;

public class WWIntegration extends AbstractWWIntegration {

	public WWIntegration() {
		super();
	}

	@Override
	public void init() {
		StructureProcessorApi.addProcessor(
			SavannaRuinsGenerator.SAVANNA_RUINS_KEY.identifier(),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, WWBlocks.CRACKED_MUD_BRICKS.get().defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICKS.get().defaultBlockState())
				)
			)
		);
		StructureProcessorApi.addProcessor(
			SavannaRuinsGenerator.SAVANNA_RUINS_KEY.identifier(),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_STAIRS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_STAIRS.get()),
					new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_SLAB.get()),
					new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_WALL.get())
				)
			)
		);

		StructureProcessorApi.addProcessor(CatacombsGenerator.CATACOMBS_KEY.identifier(),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.CHEST), AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.get())
				)
			)
		);
	}
}
