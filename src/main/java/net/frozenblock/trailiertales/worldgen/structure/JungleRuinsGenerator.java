package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.frozenblock.trailiertales.worldgen.TrailierTerrainAdjustment;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class JungleRuinsGenerator {
	public static final ResourceKey<StructureSet> JUNGLE_RUINS_KEY =  RegisterStructures.ofSet("jungle_ruins");
	private static final ResourceKey<Structure> JUNGLE_RUIN_KEY = RegisterStructures.createKey("jungle_ruins");
	public static final ResourceKey<StructureTemplatePool> JUNGLE_RUINS = Pools.parseKey(TrailierConstants.string("jungle_ruins"));
	public static final ResourceKey<StructureProcessorList> JUNGLE_RUINS_ARCHAEOLOGY = createKey("jungle_ruins_archaeology");
	public static final ResourceKey<StructureProcessorList> JUNGLE_RUINS_ARCHAEOLOGY_SURFACE = createKey("jungle_ruins_archaeology_surface");

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> processor = structureProcessorGetter.getOrThrow(JUNGLE_RUINS_ARCHAEOLOGY);
		Holder<StructureProcessorList> surfaceProcessor = structureProcessorGetter.getOrThrow(JUNGLE_RUINS_ARCHAEOLOGY_SURFACE);

		pool.register(
			JUNGLE_RUINS,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("abandoned_treehouse"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("bunker"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("monastery"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("pillar"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine3"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine4"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine5"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine6"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("shrine7"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("statue"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("tower_trap"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("town1"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("town2"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("town3"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("town4"), surfaceProcessor), 1),
					Pair.of(StructurePoolElement.single(string("twin_shrines"), surfaceProcessor), 1)
				),
			StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("bunker"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("bunker_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("monastery"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("monastery_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("pillar"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("pillar_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine3"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine3_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine4"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine4_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine5"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine5_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine6"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine6_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("shrine7"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("shrine7_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("tower_trap"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("tower_trap_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("town1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("town1_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("town2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("town2_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("town3"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("town3_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("town4"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("town4_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("twin_shrines"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("twin_shrines_bottom"), processor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			JUNGLE_RUIN_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_JUNGLE_RUINS),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TrailierTerrainAdjustment.SMALL_PLATFORM
				),
				templatePool.getOrThrow(JungleRuinsGenerator.JUNGLE_RUINS),
				2,
				UniformHeight.of(VerticalAnchor.absolute(-1), VerticalAnchor.absolute(0)),
				false,
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
				new RandomSpreadStructurePlacement(22, 17, RandomSpreadType.LINEAR, 343577861)
			)
		);
	}

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		RuleProcessor jungleStairGravelProcessor = new RuleProcessor(
			ImmutableList.of(new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE_STAIRS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()))
		);
		RuleProcessor jungleDegradationProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.STONE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.defaultBlockState())
			)
		);
		BlockStateRespectingRuleProcessor jungleSlabWallStairsMossyProcessor = new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_SLAB.defaultBlockState(), 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_SLAB
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_WALL.defaultBlockState(), 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_STAIRS.defaultBlockState(), 0.4F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_STAIRS
				)
			)
		);

		register(
			context,
			JUNGLE_RUINS_ARCHAEOLOGY,
			ImmutableList.of(
				jungleStairGravelProcessor,
				jungleDegradationProcessor,
				jungleSlabWallStairsMossyProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.275F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.2F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.2F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY, 0.4F)
			)
		);

		register(
			context,
			JUNGLE_RUINS_ARCHAEOLOGY_SURFACE,
			ImmutableList.of(
				jungleStairGravelProcessor,
				jungleDegradationProcessor,
				jungleSlabWallStairsMossyProcessor,
				archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_SURFACE, 0.2F),
				archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_SURFACE, 0.15F),
				archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_SURFACE, 0.15F),
				archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.JUNGLE_RUINS_ARCHAEOLOGY_SURFACE, 0.4F)
			)
		);
	}

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

	private static @NotNull String string(String name) {
		return TrailierConstants.string("ruins/jungle/" + name);
	}

	@NotNull
	private static ResourceKey<StructureProcessorList> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, TrailierConstants.id(string));
	}

	@NotNull
	private static Holder<StructureProcessorList> register(
		@NotNull BootstrapContext<StructureProcessorList> entries, @NotNull ResourceKey<StructureProcessorList> key, @NotNull List<StructureProcessor> list
	) {
		return entries.register(key, new StructureProcessorList(list));
	}
}
