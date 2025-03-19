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
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class DesertRuinsGenerator {
	public static final ResourceKey<StructureSet> DESERT_RUINS_KEY = TTStructures.ofSet("ruins_desert");
	public static final ResourceKey<Structure> DESERT_RUIN_KEY = TTStructures.createKey("ruins_desert");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			DESERT_RUIN_KEY,
			new RuinsStructure(
				TTStructures.structure(
					holderGetter.getOrThrow(TTBiomeTags.HAS_DESERT_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.DESERT,
				1F,
				UniformInt.of(2, 5),
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			DESERT_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(DESERT_RUIN_KEY),
				new RandomSpreadStructurePlacement(34, 18, RandomSpreadType.LINEAR, 9432401)
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.CHISELED_SANDSTONE, 0.25F), AlwaysTrueTest.INSTANCE, Blocks.CUT_SANDSTONE.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.SAND, 0.075F), AlwaysTrueTest.INSTANCE, Blocks.SANDSTONE.defaultBlockState()),
					TTStructures.archyProcessorRule(Blocks.SAND, Blocks.SUSPICIOUS_SAND, TTLootTables.DESERT_RUINS_ARCHAEOLOGY, 0.1F),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.CUT_SANDSTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_SANDSTONE.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.SMOOTH_SANDSTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.SANDSTONE.defaultBlockState())
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.CUT_SANDSTONE_SLAB, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_SANDSTONE_SLAB
					)
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.SMOOTH_SANDSTONE_SLAB, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_SANDSTONE_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.SMOOTH_SANDSTONE_STAIRS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.SANDSTONE_STAIRS
					)
				)
			),
			TTStructures.archyLootProcessor(Blocks.CLAY, TTBlocks.SUSPICIOUS_CLAY, TTLootTables.DESERT_RUINS_ARCHAEOLOGY, 0.3F),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.5F),
						AlwaysTrueTest.INSTANCE, Blocks.WATER.defaultBlockState()
					)
				)
			),
			TTStructures.decoratedPotSherdProcessor(
				1F,
				TTItems.HUMP_POTTERY_SHERD,
				TTItems.NEEDLES_POTTERY_SHERD,
				Items.DANGER_POTTERY_SHERD,
				TTItems.SHINE_POTTERY_SHERD
			),
			new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
		)
	);
}
