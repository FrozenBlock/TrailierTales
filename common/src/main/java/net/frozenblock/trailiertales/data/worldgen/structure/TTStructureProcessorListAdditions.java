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

package net.frozenblock.trailiertales.data.worldgen.structure;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.frozenblock.lib.levelgen.structure.api.processor.StructureProcessorListAdditions;
import net.frozenblock.lib.levelgen.structure.impl.processor.StructureProcessorListAddition;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;

public final class TTStructureProcessorListAdditions {

	public static void bootstrap(BootstrapContext<StructureProcessorListAddition> context) {
		final HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

		StructureProcessorListAdditions.register(
			context,
			TTConstants.id("end_city_cracked_blocks"),
			HolderSet.direct(structures.getOrThrow(BuiltinStructures.END_CITY)),
			List.of(
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.2F),
							AlwaysTrueTest.INSTANCE,
							TTBlocks.CRACKED_END_STONE_BRICKS.get().defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.2F),
							AlwaysTrueTest.INSTANCE,
							TTBlocks.CRACKED_PURPUR_BLOCK.get().defaultBlockState()
						)
					)
				)
			),
			TTWorldgenConfig.END_CITY_CRACKED_GENERATION.equalTo(true)
		);

		StructureProcessorListAdditions.register(
			context,
			TTConstants.id("end_city_choral_blocks"),
			HolderSet.direct(structures.getOrThrow(BuiltinStructures.END_CITY)),
			List.of(
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.05F),
							AlwaysTrueTest.INSTANCE,
							TTBlocks.CHORAL_END_STONE_BRICKS.get().defaultBlockState()
						)
					)
				)
			),
			TTWorldgenConfig.END_CITY_CHORAL_GENERATION.equalTo(true)
		);

		StructureProcessorListAdditions.register(
			context,
			TTConstants.id("end_city_chiseled_blocks"),
			HolderSet.direct(structures.getOrThrow(BuiltinStructures.END_CITY)),
			List.of(
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(
							new RandomBlockStateMatchTest(Blocks.PURPUR_PILLAR.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.4F),
							AlwaysTrueTest.INSTANCE,
							TTBlocks.CHISELED_PURPUR_BLOCK.get().defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockStateMatchTest(Blocks.PURPUR_PILLAR.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.4F),
							AlwaysTrueTest.INSTANCE,
							TTBlocks.CHISELED_PURPUR_BLOCK.get().defaultBlockState()
						)
					)
				)
			),
			TTWorldgenConfig.END_CITY_CHISELED_GENERATION.equalTo(true)
		);
	}

	private TTStructureProcessorListAdditions() {}
}
