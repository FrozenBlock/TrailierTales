package net.frozenblock.trailiertales.worldgen.structure.datagen;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.structure.api.AppendSherds;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.frozenblock.trailiertales.worldgen.structure.RuinsStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DesertRuinsGenerator {
	public static final ResourceKey<StructureSet> DESERT_RUINS_KEY =  RegisterStructures.ofSet("desert_ruins");
	private static final ResourceKey<Structure> DESERT_RUIN_KEY = RegisterStructures.createKey("desert_ruins");

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			DESERT_RUIN_KEY,
			new RuinsStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_DESERT_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.DESERT,
				0.9F,
				UniformInt.of(1, 4)
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			DESERT_RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(DESERT_RUIN_KEY),
				new RandomSpreadStructurePlacement(45, 30, RandomSpreadType.LINEAR, 9432401)
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.SAND, 0.075F), AlwaysTrueTest.INSTANCE, Blocks.SANDSTONE.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.CUT_SANDSTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.SMOOTH_SANDSTONE.defaultBlockState())
				)
			),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.SMOOTH_SANDSTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.SANDSTONE.defaultBlockState())
				)
			),
			desertArchyLootProcessor(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY, 0.35F),
			desertArchyLootProcessorClay(RegisterLootTables.DESERT_RUINS_ARCHAEOLOGY, 0.3F),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(
						new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.5F),
						AlwaysTrueTest.INSTANCE, Blocks.WATER.defaultBlockState()
					)
				)
			),
			decoratedPotSherdProcessor(
				1F,
				false,
				RegisterItems.NEEDLES_POTTERY_SHERD,
				RegisterItems.SHINE_POTTERY_SHERD,
				Items.DANGER_POTTERY_SHERD
			)
		)
	);

	@Contract("_, _ -> new")
	private static @NotNull RuleProcessor desertArchyLootProcessor(ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.SAND, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.SUSPICIOUS_SAND.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	@Contract("_, _ -> new")
	private static @NotNull RuleProcessor desertArchyLootProcessorClay(ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.CLAY, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					RegisterBlocks.SUSPICIOUS_CLAY.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	@Contract("_, _, _ -> new")
	private static @NotNull BlockStateRespectingRuleProcessor decoratedPotSherdProcessor(float chance, boolean defaultToBricks, Item... sherds) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.DECORATED_POT,
					new AppendSherds(chance, defaultToBricks, sherds)
				)
			)
		);
	}
}
