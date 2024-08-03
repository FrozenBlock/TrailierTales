package net.frozenblock.trailiertales.worldgen.structure.datagen;

import com.google.common.collect.ImmutableList;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
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
import org.jetbrains.annotations.NotNull;

public class BadlandsRuinsGenerator {
	public static final ResourceKey<StructureSet> BADLANDS_RUINS_KEY = RegisterStructures.ofSet("badlands_ruins");
	private static final ResourceKey<Structure> BADLANDS_RUIN_KEY = RegisterStructures.createKey("badlands_ruins");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			BADLANDS_RUIN_KEY,
			new RuinsStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_BADLANDS_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.BADLANDS,
				0.9F,
				UniformInt.of(2, 6)
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			BADLANDS_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(BADLANDS_RUIN_KEY),
				new RandomSpreadStructurePlacement(40, 30, RandomSpreadType.LINEAR, 21338252) // ancient city salt is 20083232
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.RED_SAND, 0.075F),
						AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()
					),
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.CHISELED_RED_SANDSTONE, 0.15F),
						AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()
					),
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.CUT_RED_SANDSTONE, 0.15F),
						AlwaysTrueTest.INSTANCE, Blocks.RED_SANDSTONE.defaultBlockState()
					),
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.CUT_RED_SANDSTONE, 0.75F),
						AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState()
					)
				)
			),
			badlandsArchy(RegisterLootTables.BADLANDS_RUINS_ARCHAEOLOGY, 0.3F),
			new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
		)
	);

	private static @NotNull RuleProcessor badlandsArchy(ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.RED_SAND, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					RegisterBlocks.SUSPICIOUS_RED_SAND.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}
}
