package net.frozenblock.trailiertales.worldgen.structure.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.lib.worldgen.structure.api.WeightedProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.WeightedRuleProcessor;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.frozenblock.trailiertales.registry.TTStructures;
import net.frozenblock.trailiertales.tag.TTBiomeTags;
import net.frozenblock.trailiertales.worldgen.processor.CoffinProcessor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
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
import org.jetbrains.annotations.NotNull;

public class CatacombsGenerator {
	public static final ResourceKey<StructureSet> CATACOMBS_STRUCTURE_SET_KEY =  TTStructures.ofSet("catacombs");
	public static final ResourceKey<Structure> CATACOMBS_KEY = TTStructures.createKey("catacombs");
	// POOLS
	public static final ResourceKey<StructureTemplatePool> START = Pools.parseKey(TTConstants.string("catacombs/center"));
	public static final ResourceKey<StructureTemplatePool> CONNECTOR_FALLBACK = Pools.parseKey(TTConstants.string("catacombs/corridor_connector_fallback"));
	public static final ResourceKey<StructureTemplatePool> CORRIDOR_FALLBACK = Pools.parseKey(TTConstants.string("catacombs/corridor_fallback"));
	// PROCESSORS
	public static final ResourceKey<StructureProcessorList> CATACOMBS_CORRIDOR = createKey("catacombs_corridor");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_CORRIDOR_RARE = createKey("catacombs_corridor_rare");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_SMALL_ROOM = createKey("catacombs_small_room");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_TOMB = createKey("catacombs_tomb");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_KING = createKey("catacombs_king");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_ARCHERY = createKey("catacombs_archery");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_LAVA_TRAP = createKey("catacombs_lava_trap");
	public static final ResourceKey<StructureProcessorList> CATACOMBS_MINESHAFT = createKey("catacombs_mineshaft");

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		Holder<StructureTemplatePool> connectorFallback = holderGetter.getOrThrow(CONNECTOR_FALLBACK);
		Holder<StructureTemplatePool> corridorFallback = holderGetter.getOrThrow(CORRIDOR_FALLBACK);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> corridor = structureProcessorGetter.getOrThrow(CATACOMBS_CORRIDOR);
		Holder<StructureProcessorList> corridorRare = structureProcessorGetter.getOrThrow(CATACOMBS_CORRIDOR_RARE);
		Holder<StructureProcessorList> smallRoom = structureProcessorGetter.getOrThrow(CATACOMBS_SMALL_ROOM);
		Holder<StructureProcessorList> tomb = structureProcessorGetter.getOrThrow(CATACOMBS_TOMB);
		Holder<StructureProcessorList> king = structureProcessorGetter.getOrThrow(CATACOMBS_KING);
		Holder<StructureProcessorList> archery = structureProcessorGetter.getOrThrow(CATACOMBS_ARCHERY);
		Holder<StructureProcessorList> lavaTrap = structureProcessorGetter.getOrThrow(CATACOMBS_LAVA_TRAP);
		Holder<StructureProcessorList> mineshaft = structureProcessorGetter.getOrThrow(CATACOMBS_MINESHAFT);

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

		pool.register(
			CORRIDOR_FALLBACK,
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/corridor_fallback"), corridor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("corridor"),
			new StructureTemplatePool(
				corridorFallback,
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
					Pair.of(StructurePoolElement.single(string("corridor/corridor_room3"), corridor), 3),

					Pair.of(StructurePoolElement.single(string("corridor/surveyors"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/tall"), corridor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		pool.register(
			CONNECTOR_FALLBACK,
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/connector_fallback"), corridor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("corridor_connector"),
			new StructureTemplatePool(
				connectorFallback,
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

					Pair.of(StructurePoolElement.single(string("corridor/intersection/all1"), corridor), 9),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all2"), corridor), 9),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all3"), corridor), 9),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all4"), corridor), 9),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all5"), corridor), 9),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all_large"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all_skulls"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/all_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_all"), corridor), 9),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_all"), corridor), 9),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/turn1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/turn2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/turn3"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/turn4"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/turn5"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/turn_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/turn_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_turn"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_turn"), corridor), 1),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/branch1"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/branch2"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/branch3"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/branch4"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/branch5"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/branch_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/branch_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_branch"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_branch"), corridor), 2),

					Pair.of(StructurePoolElement.single(string("corridor/intersection/sides1"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/sides2"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/sides3"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/sides4"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/sides5"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/sides_door1"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/intersection/sides_door2"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_sides"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_sides"), corridor), 2),

					Pair.of(StructurePoolElement.single(string("corridor/staircase/bottom1"), corridor), 22),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/top1"), corridor), 22),

					Pair.of(StructurePoolElement.single(string("corridor/dead_end/cap1"), corridor), 3), // Total 9
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_cap"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_cap"), corridor), 3),

					Pair.of(StructurePoolElement.single(string("corridor/rail_and_poison"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("corridor/lever_waterfall"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("corridor/free_chest"), tomb), 2),

					Pair.of(StructurePoolElement.single(string("tomb/archery"), archery), 2),
					Pair.of(StructurePoolElement.single(string("tomb/basement_hallway"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/bridge1"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/bridge2"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/bridge3"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/bridge4"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/burial"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/buried1"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/buried2"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/buried_alive"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/caged_chest"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/chapel"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/collapsed"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/collapsed_mob_drop"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/construction"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/courtyard1"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/courtyard2"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/cradle"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/cramped"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/crypt"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle1"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle2"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle3"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/door_puzzle4"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/dripstone_trap"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb1"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb2"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb3"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb4"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb5"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/fancy_tomb6"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/flood"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/flooded_maze"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/gallery"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/gravel_trap1"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/gravel_trap2"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/gravel_trap3"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/hall_drop"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/hidden_lever"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/hidden_room"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/hidden_tomb"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/huge_fall"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/jailhouse"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/jailhouse_mob_drop"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/king"), king), 2),
					Pair.of(StructurePoolElement.single(string("tomb/large_corridor"), king), 2),
					Pair.of(StructurePoolElement.single(string("tomb/lava_trap"), lavaTrap), 2),
					Pair.of(StructurePoolElement.single(string("tomb/lava_trapped_chests"), lavaTrap), 2),
					Pair.of(StructurePoolElement.single(string("tomb/layered1"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/layered2"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/layered3"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/layered_mob_drop"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/maze"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/maze_mob_drop"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/maze_rooms"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/mineshaft"), mineshaft), 2),
					Pair.of(StructurePoolElement.single(string("tomb/mob_delivery"), mineshaft), 2),
					Pair.of(StructurePoolElement.single(string("tomb/mob_drop"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/pick_a_lever"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/pick_a_pot"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/poison_trap"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/prison"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/puzzle1"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/puzzle2"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/puzzle3"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/puzzle4"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/ramp"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/ramp_mob_drop"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/ritual"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/ruined_path_dripstone"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/ruins"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/secret_room"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/sewers"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/shoot_the_target"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/skeleton_pot"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/skeleton_range"), archery), 2),
					Pair.of(StructurePoolElement.single(string("tomb/skull_chest"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_branching_sides"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb1"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb2"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb3"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb4"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb5"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb6"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb7"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb8"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb9"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_fancy_tomb10"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/small_split_sides"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/statue"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/statue_waterfalls"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/surveyor_blockade"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars1"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars2"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars3"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/tall_pillars4"), tomb), 1),
					Pair.of(StructurePoolElement.single(string("tomb/three_entrances"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/three_story"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/tower_drop"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/treasure"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/two_chest_doors"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/two_story"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/two_story_gravel_trap"), tomb), 2),
					Pair.of(StructurePoolElement.single(string("tomb/winding_stairs"), tomb), 2),

					Pair.of(StructurePoolElement.single(string("corridor/small_gallery"), corridor), 1),

					Pair.of(StructurePoolElement.single(string("corridor/connector_dripstone_trap"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/connector_lava_trap"), corridor), 4)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("corridor/ladder_bottom"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_all"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_turn"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_branch"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_sides"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/bottom_cap"), corridor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("corridor/ladder_top"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_all"), corridor), 4),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_turn"), corridor), 2),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_branch"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_sides"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/ladder/top_cap"), corridor), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("small_room"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("room/art_studio"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/buried1"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/buried2"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/buried3"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/cabin1"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/cabin2"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/chest_button"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic1"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic2"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic3"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic4"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic5"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic6"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic7"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic8"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic9"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic10"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic11"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic12"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic13"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic14"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic15"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic16"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic17"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic18"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/generic19"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/jail"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/kitchen"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/mine1"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/mine2"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/pillars"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/portal"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/pot_making"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/ritual1"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/ritual2"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/ritual3"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/ritual4"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/ritual5"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/ritual6"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/ritual7"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/storage"), smallRoom), 1),
					Pair.of(StructurePoolElement.single(string("room/wither_ritual"), smallRoom), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("decoration/chain"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.empty(), 60),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_4")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_5")), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("decoration/chain_4"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.empty(), 60),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_4")), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("decoration/chain_3"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.empty(), 60),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_3")), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("decoration/chain_2"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.empty(), 60),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 1),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_2")), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("decoration/chain_1"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.empty(), 60),
					Pair.of(StructurePoolElement.single(string("decoration/chain/chain_1")), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("staircase_up"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector1"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector2"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector3"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector4"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector5"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector6"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector7"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector8"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector9"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector10"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/top1"), corridor), 75)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);

		TTStructures.register(
			pool,
			string("staircase_down"),
			new StructureTemplatePool(
				empty,
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector1"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector2"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector3"), corridor), 5),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector4"), corridor), 3),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector5"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector6"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector7"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector8"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector9"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/connector10"), corridor), 1),
					Pair.of(StructurePoolElement.single(string("corridor/staircase/bottom1"), corridor), 75)
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
				TTStructures.structure(
					holderGetter.getOrThrow(TTBiomeTags.HAS_CATACOMBS),
					Map.of(
						MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, MobSpawnSettings.EMPTY_MOB_LIST)
					),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					TerrainAdjustment.ENCAPSULATE
				),
				templatePool.getOrThrow(START),
				Optional.empty(),
				16,
				UniformHeight.of(VerticalAnchor.aboveBottom(20), VerticalAnchor.aboveBottom(40)),
				false,
				Optional.empty(),
				100,
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
				new RandomSpreadStructurePlacement(65, 35, RandomSpreadType.LINEAR, 1886497114)
			)
		);
	}

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		final RuleProcessor baseProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLED_DEEPSLATE, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_COBBLED_DEEPSLATE.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_DEEPSLATE_BRICKS.defaultBlockState()),

				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_DEEPSLATE_TILES.defaultBlockState()),

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
				),

				new ProcessorRule(
					new BlockMatchTest(Blocks.SAND),
					AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()
				)
			)
		);

		final RuleProcessor potProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.35F),
					AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.275F),
					AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4)
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.275F),
					AlwaysTrueTest.INSTANCE, Blocks.POTTED_DEAD_BUSH.defaultBlockState()
				),
				new ProcessorRule(
					new BlockMatchTest(Blocks.BARREL),
					AlwaysTrueTest.INSTANCE, Blocks.DECORATED_POT.defaultBlockState()
				)
			)
		);

		final RuleProcessor barrelRespectingPotProcessor = new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.35F),
					AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.275F),
					AlwaysTrueTest.INSTANCE, Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4)
				),
				new ProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, 0.275F),
					AlwaysTrueTest.INSTANCE, Blocks.POTTED_DEAD_BUSH.defaultBlockState()
				)
			)
		);

		final BlockStateRespectingRuleProcessor blockStateRespectingRuleProcessor = new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_STAIRS, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_STAIRS, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_WALL, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_WALL, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_DEEPSLATE_TILE_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_SLAB, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.15F), AlwaysTrueTest.INSTANCE, TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB
				)
			)
		);

		final WeightedRuleProcessor corridorDecorationProcessor = new WeightedRuleProcessor(
			ImmutableList.of(
				new WeightedProcessorRule(
					new BlockMatchTest(Blocks.SKELETON_SKULL),
					AlwaysTrueTest.INSTANCE,
					SimpleWeightedRandomList.<BlockState>builder()
						.add(Blocks.CAVE_AIR.defaultBlockState(), 125)
						.add(Blocks.SOUL_LANTERN.defaultBlockState(), 4)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 0), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 1), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 2), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 3), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 4), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 5), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 6), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 7), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 8), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 9), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 10), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 11), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 12), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 13), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 14), 5)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 15), 5)
						.build()
				)
			)
		);

		final WeightedRuleProcessor tombDecorationProcessor = new WeightedRuleProcessor(
			ImmutableList.of(
				new WeightedProcessorRule(
					new BlockMatchTest(Blocks.SKELETON_SKULL),
					AlwaysTrueTest.INSTANCE,
					SimpleWeightedRandomList.<BlockState>builder()
						.add(Blocks.CAVE_AIR.defaultBlockState(), 200)
						.add(Blocks.SOUL_LANTERN.defaultBlockState(), 6)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 0), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 1), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 2), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 3), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 4), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 5), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 6), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 7), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 8), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 9), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 10), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 11), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 12), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 13), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 14), 12)
						.add(Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 15), 12)
						.build()
				)
			)
		);

		final BlockStateRespectingRuleProcessor permanentSkullProcessor = new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new BlockMatchTest(Blocks.WITHER_SKELETON_SKULL),
					AlwaysTrueTest.INSTANCE,
					Blocks.SKELETON_SKULL
				)
			)
		);

		final BlockStateRespectingRuleProcessor potLootProcessor = catacombsPotLootProcessor(TTLootTables.CATACOMBS_DECORATED_POT);
		final RuleProcessor tombArchy = catacombsArchy(false, TTLootTables.CATACOMBS_ARCHAEOLOGY_TOMB, 0.0775F);
		final RuleProcessor corridorArchy = catacombsArchy(false, TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR, 0.0775F);
		final RuleProcessor corridorRareArchy = catacombsArchy(false, TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE, 0.0775F);
		final RuleProcessor corridorRareClayArchy = catacombsArchy(true, TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE, 0.65F);
		final BlockStateRespectingRuleProcessor corridorChests = guaranteedChestProcessor(TTLootTables.CATACOMBS_CORRIDOR);
		final BlockStateRespectingRuleProcessor tombChests = guaranteedChestProcessor(TTLootTables.CATACOMBS_TOMB);
		final BlockStateRespectingRuleProcessor rewardChests = chestProcessor(TTLootTables.CATACOMBS_TOMB_REWARD, 0.375F);
		final BlockStateRespectingRuleProcessor guaranteedRewardChests = guaranteedChestProcessor(TTLootTables.CATACOMBS_TOMB_REWARD);

		final BlockStateRespectingRuleProcessor zombieSkeletonCoffinProcessor = coffinProcessor(EntityType.ZOMBIE, EntityType.SKELETON);
		final BlockStateRespectingRuleProcessor skeletonCoffinProcessor = coffinProcessor(EntityType.SKELETON);
		final BlockStateRespectingRuleProcessor huskCoffinProcessor = coffinProcessor(EntityType.HUSK);

		register(
			context,
			CATACOMBS_CORRIDOR,
			ImmutableList.of(
				corridorArchy,
				corridorDecorationProcessor,
				potProcessor,
				baseProcessor,
				blockStateRespectingRuleProcessor,
				corridorChests,
				potLootProcessor,
				TTStructures.decoratedPotSherdProcessor(
					0.5F,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					TTItems.OMEN_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					TTItems.ESSENCE_POTTERY_SHERD,
					TTItems.EYE_POTTERY_SHERD
				),
				permanentSkullProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_CORRIDOR_RARE,
			ImmutableList.of(
				corridorRareArchy,
				corridorDecorationProcessor,
				potProcessor,
				baseProcessor,
				blockStateRespectingRuleProcessor,
				corridorChests,
				potLootProcessor,
				TTStructures.decoratedPotSherdProcessor(
					0.5F,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					TTItems.OMEN_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					TTItems.ESSENCE_POTTERY_SHERD,
					TTItems.EYE_POTTERY_SHERD
				),
				permanentSkullProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_TOMB,
			ImmutableList.of(
				tombArchy,
				zombieSkeletonCoffinProcessor,
				tombDecorationProcessor,
				potProcessor,
				baseProcessor,
				blockStateRespectingRuleProcessor,
				tombChests,
				rewardChests,
				potLootProcessor,
				TTStructures.decoratedPotSherdProcessor(
					0.5F,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					TTItems.OMEN_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					TTItems.ESSENCE_POTTERY_SHERD,
					TTItems.EYE_POTTERY_SHERD
				),
				permanentSkullProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_KING,
			ImmutableList.of(
				tombArchy,
				huskCoffinProcessor,
				potProcessor,
				tombDecorationProcessor,
				baseProcessor,
				blockStateRespectingRuleProcessor,
				guaranteedRewardChests,
				potLootProcessor,
				TTStructures.decoratedPotSherdProcessor(
					0.5F,
					Items.SKULL_POTTERY_SHERD,
					TTItems.OMEN_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					TTItems.ESSENCE_POTTERY_SHERD,
					TTItems.EYE_POTTERY_SHERD
				),
				permanentSkullProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_ARCHERY,
			ImmutableList.of(
				tombArchy,
				skeletonCoffinProcessor,
				tombDecorationProcessor,
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
				baseProcessor,
				blockStateRespectingRuleProcessor,
				tombChests,
				rewardChests,
				potLootProcessor,
				TTStructures.decoratedPotSherdProcessor(
					0.5F,
					Items.SKULL_POTTERY_SHERD,
					TTItems.BULLSEYE_POTTERY_SHERD,
					TTItems.BULLSEYE_POTTERY_SHERD,
					TTItems.ESSENCE_POTTERY_SHERD,
					TTItems.EYE_POTTERY_SHERD
				),
				permanentSkullProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_LAVA_TRAP,
			ImmutableList.of(
				tombArchy,
				zombieSkeletonCoffinProcessor,
				tombDecorationProcessor,
				potProcessor,
				baseProcessor,
				blockStateRespectingRuleProcessor,
				tombChests,
				rewardChests,
				potLootProcessor,
				TTStructures.decoratedPotSherdProcessor(
					0.5F,
					Items.SKULL_POTTERY_SHERD,
					TTItems.ESSENCE_POTTERY_SHERD,
					TTItems.EYE_POTTERY_SHERD
				),
				permanentSkullProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_MINESHAFT,
			ImmutableList.of(
				tombArchy,
				zombieSkeletonCoffinProcessor,
				tombDecorationProcessor,
				barrelRespectingPotProcessor,
				baseProcessor,
				blockStateRespectingRuleProcessor,
				tombChests,
				rewardChests,
				potLootProcessor,
				TTStructures.decoratedPotSherdProcessor(
					0.5F,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					TTItems.OMEN_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					TTItems.ESSENCE_POTTERY_SHERD,
					TTItems.EYE_POTTERY_SHERD
				),
				permanentSkullProcessor,
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_SMALL_ROOM,
			ImmutableList.of(
				corridorRareArchy,
				corridorRareClayArchy,
				corridorDecorationProcessor,
				potProcessor,
				baseProcessor,
				blockStateRespectingRuleProcessor,
				corridorChests,
				potLootProcessor,
				TTStructures.decoratedPotSherdProcessor(
					0.5F,
					Items.SKULL_POTTERY_SHERD,
					Items.SKULL_POTTERY_SHERD,
					TTItems.OMEN_POTTERY_SHERD,
					Items.PLENTY_POTTERY_SHERD,
					TTItems.ESSENCE_POTTERY_SHERD,
					TTItems.EYE_POTTERY_SHERD
				),
				permanentSkullProcessor,
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

	private static @NotNull RuleProcessor catacombsArchy(boolean clay, ResourceKey<LootTable> lootTable, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(!clay ? Blocks.GRAVEL : Blocks.CLAY, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					!clay ? Blocks.SUSPICIOUS_GRAVEL.defaultBlockState() : TTBlocks.SUSPICIOUS_CLAY.defaultBlockState(),
					new AppendLoot(lootTable)
				)
			)
		);
	}

	private static @NotNull BlockStateRespectingRuleProcessor coffinProcessor(EntityType<?>... entities) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new BlockMatchTest(TTBlocks.COFFIN),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					TTBlocks.COFFIN,
					new CoffinProcessor(true, entities)
				)
			)
		);
	}

	private static @NotNull BlockStateRespectingRuleProcessor guaranteedChestProcessor(ResourceKey<LootTable> lootTable) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new BlockMatchTest(Blocks.CHEST),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.CHEST,
					new AppendLoot(lootTable)
				)
			)
		);
	}

	private static @NotNull BlockStateRespectingRuleProcessor chestProcessor(ResourceKey<LootTable> lootTable, float chance) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.CHEST, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.CHEST,
					new AppendLoot(lootTable)
				)
			)
		);
	}

	private static @NotNull String string(String name) {
		return TTConstants.string("catacombs/" + name);
	}

	@NotNull
	private static ResourceKey<StructureProcessorList> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, TTConstants.id(string));
	}

	@NotNull
	private static Holder<StructureProcessorList> register(
		@NotNull BootstrapContext<StructureProcessorList> entries, @NotNull ResourceKey<StructureProcessorList> key, @NotNull List<StructureProcessor> list
	) {
		return entries.register(key, new StructureProcessorList(list));
	}
}
