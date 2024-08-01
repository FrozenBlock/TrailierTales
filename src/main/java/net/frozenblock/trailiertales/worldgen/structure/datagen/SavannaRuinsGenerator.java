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
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SavannaRuinsGenerator {
	public static final ResourceKey<StructureSet> SAVANNA_RUINS_KEY =  RegisterStructures.ofSet("savanna_ruins");
	private static final ResourceKey<Structure> SAVANNA_RUIN_KEY = RegisterStructures.createKey("savanna_ruins");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			SAVANNA_RUIN_KEY,
			new RuinsStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_SAVANNA_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.SAVANNA,
				1F,
				UniformInt.of(2, 5)
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			SAVANNA_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(SAVANNA_RUIN_KEY),
				new RandomSpreadStructurePlacement(45, 30, RandomSpreadType.LINEAR, 78872547)
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.75F), AlwaysTrueTest.INSTANCE, Blocks.PACKED_MUD.defaultBlockState())
				)
			),
			archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY, 0.275F),
			archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY, 0.2F),
			archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY, 0.2F),
			archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.SAVANNA_RUINS_ARCHAEOLOGY, 0.4F)
		)
	);

	@Contract("_, _, _, _ -> new")
	private static @NotNull RuleProcessor archyLootProcessor(Block original, @NotNull Block suspicious, ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(original, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					suspicious.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}
}
