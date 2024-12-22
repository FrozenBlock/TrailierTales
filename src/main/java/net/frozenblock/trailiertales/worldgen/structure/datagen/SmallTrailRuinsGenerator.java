package net.frozenblock.trailiertales.worldgen.structure.datagen;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.registry.TTBlocks;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.NotNull;

public class SmallTrailRuinsGenerator {
	public static final ResourceKey<StructureSet> SMALL_TRAIL_RUINS_KEY =  TTStructures.ofSet("ruins_trail");
	private static final ResourceKey<Structure> SMALL_TRAIL_RUIN_KEY = TTStructures.createKey("ruins_trail");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			SMALL_TRAIL_RUIN_KEY,
			new RuinsStructure(
				TTStructures.structure(
					holderGetter.getOrThrow(TTBiomeTags.HAS_SMALL_TRAIL_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.TRAIL,
				1F,
				UniformInt.of(2, 5),
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			SMALL_TRAIL_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(SMALL_TRAIL_RUIN_KEY),
				new RandomSpreadStructurePlacement(32, 18, RandomSpreadType.LINEAR, 783586428)
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
					new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.PACKED_MUD.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
					TTStructures.archyProcessorRule(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, TTLootTables.RUINS_ARCHAEOLOGY, 0.15F),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_BRICKS.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.BRICKS, 0.1F), AlwaysTrueTest.INSTANCE, TTBlocks.CRACKED_BRICKS.defaultBlockState())
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
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.BRICK_SLAB, 0.2F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_BRICK_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.BRICK_WALL, 0.2F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_BRICK_WALL
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockMatchTest(Blocks.BRICK_STAIRS, 0.2F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_BRICK_STAIRS
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
			TTStructures.archyLootProcessor(Blocks.DIRT, TTBlocks.SUSPICIOUS_DIRT, TTLootTables.RUINS_ARCHAEOLOGY, 0.05F),
			TTStructures.archyLootProcessor(Blocks.COARSE_DIRT, TTBlocks.SUSPICIOUS_DIRT, TTLootTables.RUINS_ARCHAEOLOGY, 0.05F),
			TTStructures.archyLootProcessor(Blocks.CLAY, TTBlocks.SUSPICIOUS_CLAY, TTLootTables.RUINS_ARCHAEOLOGY, 0.4F),
			new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
		)
	);
}
