package net.frozenblock.trailiertales.worldgen.structure.datagen;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DeepslateRuinsGenerator {
	public static final ResourceKey<StructureSet> DEEPSLATE_RUINS_KEY = RegisterStructures.ofSet("deepslate_ruins");
	private static final ResourceKey<Structure> DEEPSLATE_RUIN_KEY = RegisterStructures.createKey("deepslate_ruins");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			DEEPSLATE_RUIN_KEY,
			new RuinsStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_DEEPSLATE_RUINS),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					TerrainAdjustment.BEARD_THIN
				),
				RuinsStructure.Type.DEEPSLATE,
				1F,
				UniformInt.of(20, 40),
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
			archyLootProcessor(RegisterLootTables.DEEPSLATE_RUINS_ARCHAEOLOGY, 0.2F),
			new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
		)
	);

	@Contract("_, _ -> new")
	private static @NotNull RuleProcessor archyLootProcessor(ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.GRAVEL, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.SUSPICIOUS_GRAVEL.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}
}
