/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.worldgen.structure.datagen;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.frozenblock.trailiertales.registry.TTStructures;
import net.frozenblock.trailiertales.tag.TTBiomeTags;
import net.frozenblock.trailiertales.worldgen.structure.RuinsStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class SnowyRuinsGenerator {
	public static final ResourceKey<StructureSet> SNOWY_RUINS_KEY = TTStructures.ofSet("ruins_snowy");
	public static final ResourceKey<Structure> SNOWY_RUIN_KEY = TTStructures.createKey("ruins_snowy");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			SNOWY_RUIN_KEY,
			new RuinsStructure(
				TTStructures.structure(
					holderGetter.getOrThrow(TTBiomeTags.HAS_SNOWY_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.SNOWY,
				1F,
				UniformInt.of(2, 6),
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			SNOWY_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(SNOWY_RUIN_KEY),
				new RandomSpreadStructurePlacement(34, 18, RandomSpreadType.LINEAR, 9457163)
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new BlockMatchTest(TTBlocks.CALCITE_SLAB), AlwaysTrueTest.INSTANCE, TTBlocks.CALCITE_BRICK_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new BlockMatchTest(TTBlocks.CALCITE_WALL), AlwaysTrueTest.INSTANCE, TTBlocks.CALCITE_BRICK_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new BlockMatchTest(TTBlocks.CALCITE_STAIRS), AlwaysTrueTest.INSTANCE, TTBlocks.CALCITE_BRICK_STAIRS
					)
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new BlockMatchTest(Blocks.CALCITE), AlwaysTrueTest.INSTANCE, TTBlocks.CALCITE_BRICKS.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
					TTStructures.archyProcessorRule(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, TTLootTables.SNOWY_RUINS_ARCHAEOLOGY, 0.15F),
					new ProcessorRule(new RandomBlockMatchTest(TTBlocks.CALCITE_BRICKS, 0.35F), AlwaysTrueTest.INSTANCE, Blocks.CALCITE.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(TTBlocks.CALCITE_BRICKS, 0.1F), AlwaysTrueTest.INSTANCE, TTBlocks.CRACKED_CALCITE_BRICKS.defaultBlockState())
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(TTBlocks.CALCITE_BRICK_SLAB, 0.25F), AlwaysTrueTest.INSTANCE, TTBlocks.CALCITE_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(TTBlocks.CALCITE_BRICK_WALL, 0.25F), AlwaysTrueTest.INSTANCE, TTBlocks.CALCITE_WALL
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(TTBlocks.CALCITE_BRICK_STAIRS, 0.25F), AlwaysTrueTest.INSTANCE, TTBlocks.CALCITE_STAIRS
					)
				)
			),
			TTStructures.archyLootProcessor(Blocks.DIRT, TTBlocks.SUSPICIOUS_DIRT, TTLootTables.SNOWY_RUINS_ARCHAEOLOGY, 0.05F),
			TTStructures.archyLootProcessor(Blocks.COARSE_DIRT, TTBlocks.SUSPICIOUS_DIRT, TTLootTables.SNOWY_RUINS_ARCHAEOLOGY, 0.05F),
			TTStructures.archyLootProcessor(Blocks.CLAY, TTBlocks.SUSPICIOUS_CLAY, TTLootTables.SNOWY_RUINS_ARCHAEOLOGY, 0.4F),
			TTStructures.decoratedPotSherdProcessor(
				1F,
				TTItems.AURORA_POTTERY_SHERD,
				TTItems.ENCLOSURE_POTTERY_SHERD,
				TTItems.FROST_POTTERY_SHERD,
				TTItems.HARE_POTTERY_SHERD
			),
			new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
		)
	);
}
