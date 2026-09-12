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

import java.util.List;
import java.util.Optional;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.structure.api.processor.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.levelgen.structure.api.processor.BlockStateRespectingRuleProcessor;
import net.frozenblock.lib.levelgen.structure.api.processor.StructureProcessorListAdditions;
import net.frozenblock.lib.levelgen.structure.impl.processor.StructureProcessorListAddition;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTConfigPredicates;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;

public final class TTStructureProcessorListAdditions {

	public static void bootstrap(BootstrapContext<StructureProcessorListAddition> context) {
		final HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);

		StructureProcessorListAdditions.register(
			context,
			TTConstants.id("end_city_cracked_blocks"),
			HolderSet.direct(structures.getOrThrow(BuiltinStructures.END_CITY)),
			List.of(
				new RuleProcessor(
					List.of(
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
					List.of(
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
					List.of(
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

		// WILDER WILD
		StructureProcessorListAdditions.register(
			context,
			TTConstants.id("savanna_ruins_wilder_wild"),
			HolderSet.direct(structures.getOrThrow(SavannaRuinsGenerator.SAVANNA_RUIN_KEY)),
			List.of(
				new RuleProcessor(
					List.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, WWBlocks.CRACKED_MUD_BRICKS.get().defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICKS.get().defaultBlockState())
					)
				),
				new BlockStateRespectingRuleProcessor(
					List.of(
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_STAIRS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_STAIRS.get()),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_SLAB.get()),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_WALL.get())
					)
				)
			),
			Optional.empty(),
			configPredicates.getOrThrow(TTConfigPredicates.HAS_WILDER_WILD)
		);

		StructureProcessorListAdditions.register(
			context,
			TTConstants.id("catacombs_wilder_wild"),
			HolderSet.direct(structures.getOrThrow(CatacombsGenerator.CATACOMBS_KEY)),
			List.of(
				new BlockStateRespectingRuleProcessor(
					List.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.CHEST), AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.get())
					)
				)
			),
			ConfigPredicate.withFallback(
				WWBlockConfig.ADD_STONE_CHESTS,
				WWBlockConfig.ADD_STONE_CHESTS.equalTo(true).asHolder(),
				ConfigPredicate.not(ConfigPredicate.alwaysTrue()).asHolder()
			).asHolder(),
			configPredicates.getOrThrow(TTConfigPredicates.HAS_WILDER_WILD)
		);
	}

	private TTStructureProcessorListAdditions() {}
}
