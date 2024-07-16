package net.frozenblock.trailiertales.worldgen.structure;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterStructureProcessors;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class CatacombsGenerator {
	public static final ResourceKey<StructureSet> CATACOMBS_STRUCTURE_SET_KEY =  RegisterStructures.ofSet("catacombs");
	public static final ResourceKey<Structure> CATACOMBS_KEY = RegisterStructures.createKey("catacombs");
	public static final ResourceKey<StructureTemplatePool> START = Pools.parseKey(TrailierConstants.string("catacombs/dungeon"));

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> catacombsDegradation = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.CATACOMBS_DEGRADATION);
		Holder<StructureProcessorList> catacombsDegradationArchery = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.CATACOMBS_DEGRADATION_ARCHERY);
		Holder<StructureProcessorList> catacombsDegradationFire = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.CATACOMBS_DEGRADATION_FIRE);
		pool.register(
			START,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("catacombs_center"), catacombsDegradation), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/corridor1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor5"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_arch1"), catacombsDegradation), 6),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_arch2"), catacombsDegradation), 6),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_door1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_door2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_pillar1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_pillar2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_pillar3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_pillar4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_thin1"), catacombsDegradation), 6),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_thin2"), catacombsDegradation), 6),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_room1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_room2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_room3"), catacombsDegradation), 3)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor_connector"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/connector1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector5"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_arch1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_arch2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_arch3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_arch4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_door1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/connector_door2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/connector_door3"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/connector_pillar1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_pillar2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_pillar3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_pillar4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_thin2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_thin3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_thin4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_room1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_room2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_room3"), catacombsDegradation), 3),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/all1"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all2"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all3"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all4"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all5"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all_door1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all_door2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_all"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_all"), catacombsDegradation), 4),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/left1"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left2"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left3"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left4"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left5"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_door1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_door2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left"), catacombsDegradation), 2),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front1"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front2"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front3"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front4"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front5"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front_door1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front_door2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left_front"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left_front"), catacombsDegradation), 3),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right1"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right2"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right3"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right4"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right5"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right_door1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right_door2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left_right"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left_right"), catacombsDegradation), 3),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/right1"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right2"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right3"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right4"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right5"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_door1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_door2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_right"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_right"), catacombsDegradation), 2),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front1"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front2"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front3"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front4"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front5"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front_door1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front_door2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_right_front"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_right_front"), catacombsDegradation), 3),

					Pair.of(StructurePoolElement.single(string("corridor/staircase/bottom1"), catacombsDegradation), 22),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/top1"), catacombsDegradation), 22),

					Pair.of(StructurePoolElement.single(string("corridor/dead_end/cap1"), catacombsDegradation), 3), // Total 9
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_cap"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_cap"), catacombsDegradation), 3),

					Pair.of(StructurePoolElement.single(string("corridor/rail_and_poison"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/caged_chest"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/two_chest_doors"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb5"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/small_split_sides"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/skull_left_chest"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("tomb/skull_right_chest"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("tomb/buried1"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("tomb/buried2"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle3"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle3"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars1"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars2"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars3"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars3"), catacombsDegradation), 1),
					Pair.of(StructurePoolElement.single(string("tomb/archery"), catacombsDegradationArchery), 3),
					Pair.of(StructurePoolElement.single(string("tomb/burial"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/king"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("tomb/lava_trap"), catacombsDegradationFire), 3),
					Pair.of(StructurePoolElement.single(string("tomb/maze"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/piston_puzzle"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/poison_trap"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/prison"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("tomb/two_story"), catacombsDegradation), 3),

					Pair.of(StructurePoolElement.single(string("corridor/connector_dripstone_trap"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/connector_lava_trap"), catacombsDegradation), 4)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor/ladder_bottom"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_all"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left_front"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left_right"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_right"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_right_front"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_cap"), catacombsDegradation), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor/ladder_top"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_all"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left_front"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left_right"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_right"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_right_front"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_cap"), catacombsDegradation), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("small_room"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("room/buried"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/cabin1"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/cabin2"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/jail"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/kitchen"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/mine1"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/mine2"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/portal"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/pot_making"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/ritual1"), catacombsDegradation), 10),
					Pair.of(StructurePoolElement.single(string("room/ritual2"), catacombsDegradation), 10)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor_decoration"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/decoration/soul_lantern"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull"), catacombsDegradation), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull1"), catacombsDegradation), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull2"), catacombsDegradation), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull3"), catacombsDegradation), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull4"), catacombsDegradation), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/blank"), catacombsDegradation), 123)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("tomb_decoration"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("tomb/decoration/blank"), catacombsDegradation), 20),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/soul_lantern"), catacombsDegradation), 6),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull"), catacombsDegradation), 12),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull1"), catacombsDegradation), 12),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull2"), catacombsDegradation), 12),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull3"), catacombsDegradation), 12),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull4"), catacombsDegradation), 12)

				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank"), catacombsDegradation), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_4"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_5"), catacombsDegradation), 2)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain_4"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank"), catacombsDegradation), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2"), catacombsDegradation), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_4"), catacombsDegradation), 3)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain_3"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank"), catacombsDegradation), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1"), catacombsDegradation), 4),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3"), catacombsDegradation), 3)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain_2"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank"), catacombsDegradation), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1"), catacombsDegradation), 5),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2"), catacombsDegradation), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain_1"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank"), catacombsDegradation), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1"), catacombsDegradation), 9)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("tomb_decoration_wall"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull"), catacombsDegradation), 50),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull1"), catacombsDegradation), 18),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull2"), catacombsDegradation), 18),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull3"), catacombsDegradation), 6),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull4"), catacombsDegradation), 6),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/blank"), catacombsDegradation), 123)

				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("staircase_up"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector5"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector6"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector7"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector8"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector9"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector10"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/top1"), catacombsDegradation), 12)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("staircase_down"),
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector1"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector2"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector3"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector4"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector5"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector6"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector7"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector8"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector9"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector10"), catacombsDegradation), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/bottom1"), catacombsDegradation), 12)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			CATACOMBS_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(BiomeTags.HAS_MINESHAFT),
					Map.of(
						MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, MobSpawnSettings.EMPTY_MOB_LIST)
					),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					TerrainAdjustment.ENCAPSULATE
				),
				templatePool.getOrThrow(START),
				Optional.empty(),
				20,
				UniformHeight.of(VerticalAnchor.aboveBottom(20), VerticalAnchor.aboveBottom(40)),
				false,
				Optional.empty(),
				116,
				List.of(),
				JigsawStructure.DEFAULT_DIMENSION_PADDING,
				LiquidSettings.IGNORE_WATERLOGGING
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

	private static @NotNull String string(String name) {
		return TrailierConstants.string("catacombs/" + name);
	}
}
