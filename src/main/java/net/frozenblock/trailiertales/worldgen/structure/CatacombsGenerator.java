package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.frozenblock.lib.worldgen.structure.api.AppendSherds;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.frozenblock.trailiertales.worldgen.processor.CoffinProcessor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class CatacombsGenerator {
	public static final ResourceKey<StructureSet> CATACOMBS_STRUCTURE_SET_KEY =  RegisterStructures.ofSet("catacombs");
	public static final ResourceKey<Structure> CATACOMBS_KEY = RegisterStructures.createKey("catacombs");
	public static final ResourceKey<StructureTemplatePool> START = Pools.parseKey(TrailierConstants.string("catacombs/dungeon"));
	public static final ResourceKey<StructureProcessorList> CATACOMBS_CORRIDOR = createKey("catacombs_corridor");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_CORRIDOR_RARE = createKey("catacombs_corridor_rare");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_SMALL_ROOM = createKey("catacombs_small_room");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_TOMB = createKey("catacombs_tomb");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_KING = createKey("catacombs_king");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_ARCHERY = createKey("catacombs_archery");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_LAVA_TRAP = createKey("catacombs_lava_trap");

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> corridor = structureProcessorGetter.getOrThrow(CATACOMBS_CORRIDOR);
		Holder<StructureProcessorList> corridorRare = structureProcessorGetter.getOrThrow(CATACOMBS_CORRIDOR_RARE);
		Holder<StructureProcessorList> smallRoom = structureProcessorGetter.getOrThrow(CATACOMBS_SMALL_ROOM);
		Holder<StructureProcessorList> tomb = structureProcessorGetter.getOrThrow(CATACOMBS_TOMB);
		Holder<StructureProcessorList> king = structureProcessorGetter.getOrThrow(CATACOMBS_KING);
		Holder<StructureProcessorList> archery = structureProcessorGetter.getOrThrow(CATACOMBS_ARCHERY);
		Holder<StructureProcessorList> lavaTrap = structureProcessorGetter.getOrThrow(CATACOMBS_LAVA_TRAP);
		pool.register(
			START,
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("catacombs_center"), tomb), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/corridor1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor2"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor3"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor4"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor5"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_arch1"), corridor), 6),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_arch2"), corridor), 6),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried1"), corridorRare), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried2"), corridorRare), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried3"), corridorRare), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried4"), corridorRare), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_pillar1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_pillar2"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_pillar3"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_pillar4"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_buried1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_thin1"), corridor), 6),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_thin2"), corridor), 6),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_room1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_room2"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/corridor_room3"), corridor), 3)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor_connector"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/connector1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector2"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector3"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector4"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector5"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_arch1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_arch2"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_arch3"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_arch4"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried1"), corridorRare), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried2"), corridorRare), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried3"), corridorRare), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried4"), corridorRare), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/connector_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/connector_door3"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/connector_pillar1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_pillar2"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_pillar3"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_pillar4"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_buried1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_thin2"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_thin3"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_thin4"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_room1"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_room2"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/connector_room3"), corridor), 3),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/all1"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all2"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all3"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all4"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all5"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_all"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_all"), corridor), 4),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/left1"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left2"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left3"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left4"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left5"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left"), corridor), 2),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front1"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front2"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front3"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front4"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front5"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_front_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left_front"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left_front"), corridor), 3),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right1"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right2"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right3"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right4"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right5"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/left_right_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left_right"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left_right"), corridor), 3),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/right1"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right2"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right3"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right4"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right5"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_right"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_right"), corridor), 2),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front1"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front2"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front3"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front4"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front5"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/right_front_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_right_front"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_right_front"), corridor), 3),

					Pair.of(StructurePoolElement.single(string("corridor/staircase/bottom1"), corridor), 22),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/top1"), corridor), 22),

					Pair.of(StructurePoolElement.single(string("corridor/dead_end/cap1"), corridor), 3), // Total 9
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_cap"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_cap"), corridor), 3),

					Pair.of(StructurePoolElement.single(string("corridor/rail_and_poison"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/caged_chest"), tomb), 3),
					Pair.of(StructurePoolElement.single(string("tomb/two_chest_doors"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb1"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb2"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb3"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb4"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb5"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/small_split_sides"), tomb), 3),
					Pair.of(StructurePoolElement.single(string("tomb/skull_left_chest"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/skull_right_chest"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/buried1"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/buried2"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle1"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle2"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle3"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle3"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars1"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars2"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars3"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars3"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/archery"), archery), 4),
					Pair.of(StructurePoolElement.single(string("tomb/burial"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb1"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb2"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/king"), king), 2),
					Pair.of(StructurePoolElement.single(string("tomb/lava_trap"), lavaTrap), 4),
					Pair.of(StructurePoolElement.single(string("tomb/maze"), tomb), 3),
					Pair.of(StructurePoolElement.single(string("tomb/piston_puzzle"), tomb), 3),
					Pair.of(StructurePoolElement.single(string("tomb/poison_trap"), tomb), 4),
					Pair.of(StructurePoolElement.single(string("tomb/prison"), tomb), 3),
					Pair.of(StructurePoolElement.single(string("tomb/two_story"), tomb), 4),

					Pair.of(StructurePoolElement.single(string("corridor/connector_dripstone_trap"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/connector_lava_trap"), corridor), 4)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor/ladder_bottom"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_all"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left_front"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_left_right"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_right"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_right_front"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_cap"), corridor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor/ladder_top"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_all"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left_front"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_left_right"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_right"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_right_front"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_cap"), corridor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("small_room"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("room/buried"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/cabin1"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/cabin2"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/jail"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/kitchen"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/mine1"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/mine2"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/portal"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/pot_making"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/ritual1"), smallRoom), 10),
					Pair.of(StructurePoolElement.single(string("room/ritual2"), smallRoom), 10)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("corridor_decoration"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/decoration/soul_lantern")), 2),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull")), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull1")), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull2")), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull3")), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull4")), 19),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/blank")), 123)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("tomb_decoration"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("tomb/decoration/blank")), 20),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/soul_lantern")), 6),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull")), 12),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull1")), 12),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull2")), 12),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull3")), 12),
					Pair.of(StructurePoolElement.single(string("tomb/decoration/skull4")), 12)

				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank")), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2")), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3")), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_4")), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_5")), 2)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain_4"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank")), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2")), 2),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3")), 3),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_4")), 3)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain_3"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank")), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 4),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2")), 3),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3")), 3)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain_2"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank")), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 5),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2")), 5)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("decoration/chain_1"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_blank")), 150),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 9)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("tomb_decoration_wall"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull")), 50),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull1")), 18),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull2")), 18),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull3")), 6),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/skull4")), 6),
					Pair.of(StructurePoolElement.single(string("corridor/decoration/blank")), 123)

				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("staircase_up"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector1"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector2"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector3"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector4"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector5"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector6"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector7"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector8"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector9"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector10"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/top1"), corridor), 16)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		RegisterStructures.register(
			pool,
			string("staircase_down"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector1"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector2"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector3"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector4"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector5"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector6"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector7"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector8"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector9"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector10"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/bottom1"), corridor), 16)
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
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_CATACOMBS),
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
				96,
				List.of(),
				new DimensionPadding(6),
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

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		final RuleProcessor catacombsRuleProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICKS.defaultBlockState()),

				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_SLAB, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),

				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILES.defaultBlockState()),

				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),

				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.GRAVEL, 0.425F),
					AlwaysTrueTest.INSTANCE, Blocks.TUFF.defaultBlockState()
				),

				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.COBWEB, 0.65F),
					AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
				),

				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.CANDLE, 0.8F),
					AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockStateMatchTest(
						Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.15F),
					AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 3)
				),
				new ProcessorRule(
					new RandomBlockStateMatchTest(
						Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.5F),
					AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 2)
				),
				new ProcessorRule(
					new RandomBlockStateMatchTest(
						Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4), 0.7F),
					AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 1)
				),

				new ProcessorRule(
					new RandomBlockStateMatchTest(
						Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4).setValue(BlockStateProperties.LIT, true), 0.15F),
					AlwaysTrueTest.INSTANCE, Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 3).setValue(BlockStateProperties.LIT, true)
				),
				new ProcessorRule(
					new RandomBlockStateMatchTest(
						Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4).setValue(BlockStateProperties.LIT, true), 0.5F),
					AlwaysTrueTest.INSTANCE, Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 2).setValue(BlockStateProperties.LIT, true)
				),
				new ProcessorRule(
					new RandomBlockStateMatchTest(
						Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4).setValue(BlockStateProperties.LIT, true), 0.7F),
					AlwaysTrueTest.INSTANCE, Blocks.RED_CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 1).setValue(BlockStateProperties.LIT, true)
				),

				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.POTTED_DEAD_BUSH, 0.1F),
					AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.POTTED_DEAD_BUSH, 0.6F),
					AlwaysTrueTest.INSTANCE, Blocks.FLOWER_POT.defaultBlockState()
				)
			)
		);

		final RuleProcessor catacombsPotProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.25F),
					AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.25F),
					AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4)
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.25F),
					AlwaysTrueTest.INSTANCE, Blocks.POTTED_DEAD_BUSH.defaultBlockState()
				)
			)
		);

		final BlockStateRespectingRuleProcessor catacombsBlockStateRespectingRuleProcessor = new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_TILE_STAIRS.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_BRICK_WALL.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_TILE_WALL.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_BRICK_SLAB.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.DEEPSLATE_TILE_SLAB.defaultBlockState(), 0.15F), AlwaysTrueTest.INSTANCE, RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB
				)
			)
		);

		final BlockStateRespectingRuleProcessor catacombsPotLootProcessor = catacombsPotLootProcessor(RegisterLootTables.CATACOMBS_DECORATED_POT);
		final RuleProcessor tombArchy = catacombsArchy(false, RegisterLootTables.CATACOMBS_ARCHAEOLOGY_TOMB, 0.0775F);
		final RuleProcessor corridorArchy = catacombsArchy(false, RegisterLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR, 0.0775F);
		final RuleProcessor corridorRareArchy = catacombsArchy(false, RegisterLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE, 0.0775F);
		final RuleProcessor corridorRareClayArchy = catacombsArchy(true, RegisterLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE, 0.65F);

		final BlockStateRespectingRuleProcessor zombieSkeletonCoffinProcessor = coffinProcessor(EntityType.ZOMBIE, EntityType.SKELETON);
		final BlockStateRespectingRuleProcessor skeletonCoffinProcessor = coffinProcessor(EntityType.SKELETON);
		final BlockStateRespectingRuleProcessor huskCoffinProcessor = coffinProcessor(EntityType.HUSK);

		register(
			context,
			CATACOMBS_CORRIDOR,
			ImmutableList.of(
				corridorArchy,
				catacombsPotProcessor,
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				catacombsPotLootProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					Items.PRIZE_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					Items.SHEAF_POTTERY_SHERD,
					Items.HEART_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					Items.BLADE_POTTERY_SHERD,
					Items.BREWER_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_CORRIDOR_RARE,
			ImmutableList.of(
				corridorRareArchy,
				catacombsPotProcessor,
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				catacombsPotLootProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					Items.PRIZE_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					Items.SHEAF_POTTERY_SHERD,
					Items.HEART_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					Items.BLADE_POTTERY_SHERD,
					Items.BREWER_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_TOMB,
			ImmutableList.of(
				tombArchy,
				zombieSkeletonCoffinProcessor,
				catacombsPotProcessor,
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				catacombsPotLootProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					Items.PRIZE_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					Items.SHEAF_POTTERY_SHERD,
					Items.HEART_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					Items.BLADE_POTTERY_SHERD,
					Items.BREWER_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_KING,
			ImmutableList.of(
				tombArchy,
				huskCoffinProcessor,
				catacombsPotProcessor,
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				catacombsPotLootProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.PRIZE_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					Items.HEART_POTTERY_SHERD,
					Items.BREWER_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_ARCHERY,
			ImmutableList.of(
				tombArchy,
				skeletonCoffinProcessor,
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.1F),
							AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(
								Blocks.DECORATED_POT, 0.2F),
							AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4)
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.15F),
							AlwaysTrueTest.INSTANCE, Blocks.POTTED_DEAD_BUSH.defaultBlockState()
						)
					)
				),
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				catacombsPotLootProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					RegisterItems.BULLSEYE_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD,
					RegisterItems.BULLSEYE_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_LAVA_TRAP,
			ImmutableList.of(
				tombArchy,
				zombieSkeletonCoffinProcessor,
				catacombsPotProcessor,
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				catacombsPotLootProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.ARMS_UP_POTTERY_SHERD,
					Items.BURN_POTTERY_SHERD,
					Items.BREWER_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_SMALL_ROOM,
			ImmutableList.of(
				corridorRareArchy,
				corridorRareClayArchy,
				catacombsPotProcessor,
				catacombsRuleProcessor,
				catacombsBlockStateRespectingRuleProcessor,
				catacombsPotLootProcessor,
				decoratedPotSherdProcessor(
					1F,
					false,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					Items.PRIZE_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					Items.SHEAF_POTTERY_SHERD,
					Items.HEART_POTTERY_SHERD,
					Items.ARCHER_POTTERY_SHERD,
					Items.BLADE_POTTERY_SHERD,
					Items.BREWER_POTTERY_SHERD,
					RegisterItems.WITHER_POTTERY_SHERD
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);
	}

	private static @NotNull BlockStateRespectingRuleProcessor catacombsPotLootProcessor(ResourceKey<LootTable> registryKey) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new BlockMatchTest(Blocks.DECORATED_POT),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.DECORATED_POT,
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

	private static @NotNull RuleProcessor catacombsArchy(boolean clay, ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(!clay ? Blocks.GRAVEL : Blocks.CLAY, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					!clay ? Blocks.SUSPICIOUS_GRAVEL.defaultBlockState() : RegisterBlocks.SUSPICIOUS_CLAY.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	private static @NotNull BlockStateRespectingRuleProcessor coffinProcessor(EntityType<?>... entities) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new BlockMatchTest(RegisterBlocks.COFFIN),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					RegisterBlocks.COFFIN,
					new CoffinProcessor(true, entities)
				)
			)
		);
	}

	private static @NotNull String string(String name) {
		return TrailierConstants.string("catacombs/" + name);
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
