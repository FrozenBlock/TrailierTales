/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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

public class JungleRuinsGenerator {
	public static final ResourceKey<StructureSet> JUNGLE_RUINS_KEY =  TTStructures.ofSet("ruins_jungle");
	public static final ResourceKey<Structure> JUNGLE_RUIN_KEY = TTStructures.createKey("ruins_jungle");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			JUNGLE_RUIN_KEY,
			new RuinsStructure(
				TTStructures.structure(
					holderGetter.getOrThrow(TTBiomeTags.HAS_JUNGLE_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.JUNGLE,
				1F,
				UniformInt.of(2, 5),
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			JUNGLE_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(JUNGLE_RUIN_KEY),
				new RandomSpreadStructurePlacement(34, 18, RandomSpreadType.LINEAR, 343577861)
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new BlockMatchTest(Blocks.COBBLESTONE_SLAB), AlwaysTrueTest.INSTANCE, Blocks.STONE_BRICK_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new BlockMatchTest(Blocks.COBBLESTONE_WALL), AlwaysTrueTest.INSTANCE, Blocks.STONE_BRICK_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new BlockMatchTest(Blocks.COBBLESTONE_STAIRS), AlwaysTrueTest.INSTANCE, Blocks.STONE_BRICK_STAIRS
					)
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new BlockMatchTest(Blocks.COBBLESTONE), AlwaysTrueTest.INSTANCE, Blocks.STONE_BRICKS.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
					TTStructures.archyProcessorRule(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, TTLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.15F),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.defaultBlockState())
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.STONE_BRICK_SLAB, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.STONE_BRICK_WALL, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE_WALL
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.STONE_BRICK_STAIRS, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE_STAIRS
					)
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.COBBLESTONE_SLAB, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.COBBLESTONE_WALL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_WALL
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.COBBLESTONE_STAIRS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_STAIRS
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.STONE_BRICK_SLAB, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICK_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.STONE_BRICK_WALL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICK_WALL
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.STONE_BRICK_STAIRS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICK_STAIRS
					)
				)
			),
			TTStructures.archyLootProcessor(Blocks.DIRT, TTBlocks.SUSPICIOUS_DIRT, TTLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.05F),
			TTStructures.archyLootProcessor(Blocks.COARSE_DIRT, TTBlocks.SUSPICIOUS_DIRT, TTLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.05F),
			TTStructures.archyLootProcessor(Blocks.CLAY, TTBlocks.SUSPICIOUS_CLAY, TTLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.4F),
			TTStructures.decoratedPotSherdProcessor(
				1F,
				TTItems.BOLT_POTTERY_SHERD,
				TTItems.NAVIGATOR_POTTERY_SHERD,
				TTItems.BLOOM_POTTERY_SHERD,
				TTItems.SHOWER_POTTERY_SHERD
			),
			new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
		)
	);
}
