package net.frozenblock.trailiertales.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.registry.RegisterStructureProcessors;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBindings;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class CatacombsGenerator {
	public static final ResourceKey<StructureSet> CATACOMBS_STRUCTURE_SET_KEY =  RegisterStructures.ofSet("catacombs");
	private static final ResourceKey<Structure> CATACOMBS_KEY = RegisterStructures.createKey("catacombs");
	public static final ResourceKey<StructureTemplatePool> START = createKey("dungeon");
	public static final List<PoolAliasBinding> ALIAS_BINDINGS = ImmutableList.<PoolAliasBinding>builder()
		.add(
			PoolAliasBinding.random(
				string("dungeon/spawner"),
				SimpleWeightedRandomList.<String>builder()
					.add(dungeonSpawner("chain/skeleton"))
					.add(dungeonSpawner("chain/zombie"))

					.add(dungeonSpawner("chain_hanging/skeleton"))
					.add(dungeonSpawner("chain_hanging/zombie"))

					.add(dungeonSpawner("pillar/skeleton"))
					.add(dungeonSpawner("pillar/zombie"))

					.add(dungeonSpawner("wall/skeleton"))
					.add(dungeonSpawner("wall/zombie"))

					.add(dungeonSpawner("wall_chain/skeleton"))
					.add(dungeonSpawner("wall_chain/zombie"))

					.add(dungeonSpawner("wall_chain_hanging/skeleton"))
					.add(dungeonSpawner("wall_chain_hanging/zombie"))

					.add(dungeonSpawner("wall_hanging/skeleton"))
					.add(dungeonSpawner("wall_hanging/zombie"))
					.build()
			)
		)
		.build();

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> catacombsDegradation = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.CATACOMBS_DEGRADATION);

		pool.register(
			START,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("dungeon/dungeon1"), catacombsDegradation), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			string("corridor"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/corridor1"), catacombsDegradation), 20)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			string("corridor_connector"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/connector1"), catacombsDegradation), 20),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all1"), catacombsDegradation), 20),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left1"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front1"), catacombsDegradation), 15),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right1"), catacombsDegradation), 15),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right1"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front1"), catacombsDegradation), 15),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/bottom1"), catacombsDegradation), 11),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/top1"), catacombsDegradation), 11),
					Pair.of(StructurePoolElement.single(string("corridor/dead_end/cap1"), catacombsDegradation), 2)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			string("corridor_decoration"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull"), catacombsDegradation), 50),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull1"), catacombsDegradation), 18),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull2"), catacombsDegradation), 18),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull3"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull4"), catacombsDegradation), 5)

				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			string("staircase_up"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/staircase/staircase1"), catacombsDegradation), 25),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/staircase2"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/top1"), catacombsDegradation), 12)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			string("staircase_down"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/staircase/staircase1"), catacombsDegradation), 25),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/staircase2"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/bottom1"), catacombsDegradation), 12)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		// DUNGEON SPAWNERS

		Pools.register(
			pool,
			dungeonSpawner("chain/skeleton"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("chain/skeleton"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("chain/zombie"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("chain/zombie"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("chain_hanging/skeleton"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("chain_hanging/skeleton"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("chain_hanging/zombie"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("chain_hanging/zombie"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("pillar/skeleton"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("pillar/skeleton"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("pillar/zombie"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("pillar/zombie"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("wall/skeleton"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("wall/skeleton"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("wall/zombie"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("wall/zombie"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("wall_chain/skeleton"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("wall_chain/skeleton"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("wall_chain/zombie"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("wall_chain/zombie"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("wall_chain_hanging/skeleton"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("wall_chain_hanging/skeleton"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("wall_chain_hanging/zombie"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("wall_chain_hanging/zombie"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("wall_hanging/skeleton"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("wall_hanging/skeleton"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		Pools.register(
			pool,
			dungeonSpawner("wall_hanging/zombie"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(dungeonSpawner("wall_hanging/zombie"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		PoolAliasBindings.registerTargetsAsPools(pool, empty, ALIAS_BINDINGS);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			CATACOMBS_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(BiomeTags.HAS_MINESHAFT),
					GenerationStep.Decoration.UNDERGROUND_DECORATION,
					TerrainAdjustment.BURY
				),
				templatePool.getOrThrow(START),
				Optional.empty(),
				20,
				UniformHeight.of(VerticalAnchor.aboveBottom(20), VerticalAnchor.aboveBottom(40)),
				false,
				Optional.empty(),
				116,
				ALIAS_BINDINGS
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			CATACOMBS_STRUCTURE_SET_KEY,
			new StructureSet(
				structure.getOrThrow(CATACOMBS_KEY),
				new RandomSpreadStructurePlacement(40, 30, RandomSpreadType.LINEAR, 1488497114) // ancient city salt is 20083232
			)
		);
	}

	private static @NotNull ResourceKey<StructureTemplatePool> createKey(String name) {
		return ResourceKey.create(Registries.TEMPLATE_POOL, TrailierTalesSharedConstants.id("catacombs/" + name));
	}

	private static @NotNull String string(String name) {
		return TrailierTalesSharedConstants.string("catacombs/" + name);
	}

	public static String dungeonSpawner(String string) {
		return string("dungeon/spawner/" + string);
	}

}
