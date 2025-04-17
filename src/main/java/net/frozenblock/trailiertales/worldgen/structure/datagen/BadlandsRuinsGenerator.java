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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class BadlandsRuinsGenerator {
	public static final ResourceKey<StructureSet> BADLANDS_RUINS_KEY = TTStructures.ofSet("ruins_badlands");
	public static final ResourceKey<Structure> BADLANDS_RUIN_KEY = TTStructures.createKey("ruins_badlands");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			BADLANDS_RUIN_KEY,
			new RuinsStructure(
				TTStructures.structure(
					holderGetter.getOrThrow(TTBiomeTags.HAS_BADLANDS_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.BADLANDS,
				1F,
				UniformInt.of(2, 6),
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			BADLANDS_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(BADLANDS_RUIN_KEY),
				new RandomSpreadStructurePlacement(34, 18, RandomSpreadType.LINEAR, 21338252) // ancient city salt is 20083232
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.CHISELED_RED_SANDSTONE, 0.25F), AlwaysTrueTest.INSTANCE, Blocks.CUT_RED_SANDSTONE.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.RED_SAND, 0.075F), AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()),
					TTStructures.archyProcessorRule(Blocks.RED_SAND, TTBlocks.SUSPICIOUS_RED_SAND, TTLootTables.BADLANDS_RUINS_ARCHAEOLOGY, 0.1F),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.CUT_RED_SANDSTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.SMOOTH_RED_SANDSTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState())
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.CUT_RED_SANDSTONE_SLAB, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_RED_SANDSTONE_SLAB
					)
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.SMOOTH_RED_SANDSTONE_SLAB, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_RED_SANDSTONE_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.SMOOTH_RED_SANDSTONE_STAIRS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE_STAIRS
					)
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.4F),
						AlwaysTrueTest.INSTANCE, Blocks.RED_SAND.defaultBlockState()
					),
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.4F),
						AlwaysTrueTest.INSTANCE, Blocks.POTTED_DEAD_BUSH.defaultBlockState()
					),
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.ORANGE_CANDLE, 0.8F),
						AlwaysTrueTest.INSTANCE, Blocks.RED_SAND.defaultBlockState()
					),
					new ProcessorRule(
						new RandomBlockStateMatchTest(
							Blocks.ORANGE_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.15F),
						AlwaysTrueTest.INSTANCE, Blocks.ORANGE_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 3)
					),
					new ProcessorRule(
						new RandomBlockStateMatchTest(
							Blocks.ORANGE_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.5F),
						AlwaysTrueTest.INSTANCE, Blocks.ORANGE_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 2)
					),
					new ProcessorRule(
						new RandomBlockStateMatchTest(
							Blocks.ORANGE_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.7F),
						AlwaysTrueTest.INSTANCE, Blocks.ORANGE_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 1)
					)
				)
			),
			TTStructures.decoratedPotSherdProcessor(
				1F,
				TTItems.SHED_POTTERY_SHERD,
				Items.BURN_POTTERY_SHERD,
				TTItems.WITHER_POTTERY_SHERD,
				TTItems.DROUGHT_POTTERY_SHERD
			),
			new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
		)
	);
}
