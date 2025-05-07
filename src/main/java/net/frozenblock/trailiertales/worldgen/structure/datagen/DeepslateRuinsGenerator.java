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
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class DeepslateRuinsGenerator {
	public static final ResourceKey<StructureSet> DEEPSLATE_RUINS_KEY = TTStructures.ofSet("ruins_deepslate");
	public static final ResourceKey<Structure> DEEPSLATE_RUIN_KEY = TTStructures.createKey("ruins_deepslate");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			DEEPSLATE_RUIN_KEY,
			new RuinsStructure(
				TTStructures.structure(
					holderGetter.getOrThrow(TTBiomeTags.HAS_DEEPSLATE_RUINS),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					TerrainAdjustment.BEARD_THIN
				),
				RuinsStructure.Type.DEEPSLATE,
				1F,
				UniformInt.of(3, 6),
				UniformHeight.of(VerticalAnchor.aboveBottom(10), VerticalAnchor.aboveBottom(48))
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			DEEPSLATE_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(DEEPSLATE_RUIN_KEY),
				new RandomSpreadStructurePlacement(15, 10, RandomSpreadType.LINEAR, 4684896)
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COBBLED_DEEPSLATE.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COBBLED_DEEPSLATE.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState())
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.COBBLED_DEEPSLATE_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_SLAB, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.COBBLED_DEEPSLATE_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_STAIRS, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.COBBLED_DEEPSLATE_STAIRS
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_STAIRS, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.COBBLED_DEEPSLATE_STAIRS
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_WALL, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.COBBLED_DEEPSLATE_WALL
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_WALL, 0.175F), AlwaysTrueTest.INSTANCE, Blocks.COBBLED_DEEPSLATE_WALL
					)
				)
			),
			TTStructures.archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, TTLootTables.DEEPSLATE_RUINS_ARCHAEOLOGY, 0.2F),
			new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
		)
	);
}
