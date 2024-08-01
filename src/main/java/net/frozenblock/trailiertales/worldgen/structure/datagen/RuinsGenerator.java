package net.frozenblock.trailiertales.worldgen.structure.datagen;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.structure.api.AppendSherds;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class RuinsGenerator {
	public static final ResourceKey<StructureSet> RUINS_KEY = RegisterStructures.ofSet("ruins");
	private static final ResourceKey<Structure> RUIN_KEY = RegisterStructures.createKey("ruins");

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

		context.register(
			RUIN_KEY,
			new RuinsStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TerrainAdjustment.NONE
				),
				RuinsStructure.Type.GENERIC,
				0.75F,
				UniformInt.of(1, 4)
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			RUINS_KEY,
			new StructureSet(
				structure.getOrThrow(RUIN_KEY),
				new RandomSpreadStructurePlacement(45, 30, RandomSpreadType.LINEAR, 68415318)
			)
		);
	}

	public static final StructureProcessorList PROCESSORS = new StructureProcessorList(
		ImmutableList.of(
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.6F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.defaultBlockState())
				)
			),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(
						new RandomBlockStateMatchTest(Blocks.COBBLESTONE_SLAB.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_SLAB
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockStateMatchTest(Blocks.COBBLESTONE_WALL.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_WALL
					),
					new BlockStateRespectingProcessorRule(
						new RandomBlockStateMatchTest(Blocks.COBBLESTONE_STAIRS.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_STAIRS
					)
				)
			),
			archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.275F),
			archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.2F),
			archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.2F),
			archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.4F),
			decoratedPotSherdProcessor(
				1F,
				Items.MINER_POTTERY_SHERD,
				Items.ARCHER_POTTERY_SHERD,
				Items.BREWER_POTTERY_SHERD,
				Items.FRIEND_POTTERY_SHERD
			)
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

	@Contract("_, _ -> new")
	private static @NotNull BlockStateRespectingRuleProcessor decoratedPotSherdProcessor(float chance, Item... sherds) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.DECORATED_POT,
					new AppendSherds(chance, false, sherds)
				)
			)
		);
	}
}
