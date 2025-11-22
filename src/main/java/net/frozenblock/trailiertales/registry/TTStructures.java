/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.registry;

import com.google.common.collect.ImmutableList;
import java.util.Map;
import net.frozenblock.lib.worldgen.structure.api.AppendSherds;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.worldgen.structure.datagen.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DeepslateRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DesertRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.GenericRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.JungleRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SavannaRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SnowyRuinsGenerator;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;

public final class TTStructures {

	private TTStructures() {
		throw new UnsupportedOperationException("TTStructures contains only static declarations.");
	}

	public static ResourceKey<StructureSet> ofSet(String id) {
		return ResourceKey.create(Registries.STRUCTURE_SET, TTConstants.id(id));
	}

	public static void bootstrapTemplatePool(BootstrapContext<StructureTemplatePool> context) {
		CatacombsGenerator.bootstrapTemplatePool(context);
	}

	public static void bootstrap(BootstrapContext<Structure> context) {
		BadlandsRuinsGenerator.bootstrap(context);
		CatacombsGenerator.bootstrap(context);
		DesertRuinsGenerator.bootstrap(context);
		JungleRuinsGenerator.bootstrap(context);
		SavannaRuinsGenerator.bootstrap(context);
		GenericRuinsGenerator.bootstrap(context);
		SnowyRuinsGenerator.bootstrap(context);
		DeepslateRuinsGenerator.bootstrap(context);
	}

	public static void bootstrapStructureSet(BootstrapContext<StructureSet> context) {
		BadlandsRuinsGenerator.bootstrapStructureSet(context);
		CatacombsGenerator.bootstrapStructureSet(context);
		DesertRuinsGenerator.bootstrapStructureSet(context);
		JungleRuinsGenerator.bootstrapStructureSet(context);
		SavannaRuinsGenerator.bootstrapStructureSet(context);
		GenericRuinsGenerator.bootstrapStructureSet(context);
		SnowyRuinsGenerator.bootstrapStructureSet(context);
		DeepslateRuinsGenerator.bootstrapStructureSet(context);
	}

	public static void bootstrapProcessor(BootstrapContext<StructureProcessorList> context) {
		CatacombsGenerator.bootstrapProcessor(context);
	}

	@Contract("_, _, _, _ -> new")
	public static RuleProcessor archyLootProcessor(Block original, Block suspicious, ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				archyProcessorRule(original, suspicious, registryKey, chance)
			)
		);
	}

	@Contract("_, _, _, _ -> new")
	public static ProcessorRule archyProcessorRule(Block original, Block suspicious, ResourceKey<LootTable> registryKey, float chance) {
		return new ProcessorRule(
			new RandomBlockMatchTest(original, chance),
			AlwaysTrueTest.INSTANCE,
			PosAlwaysTrueTest.INSTANCE,
			suspicious.defaultBlockState(),
			new AppendLoot(registryKey)
		);
	}

	@Contract("_, _ -> new")
	public static BlockStateRespectingRuleProcessor decoratedPotSherdProcessor(float chance, Item... sherds) {
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

	public static ResourceKey<Structure> createKey(String id) {
		return ResourceKey.create(Registries.STRUCTURE, TTConstants.id(id));
	}

	public static Structure.StructureSettings structure(
		HolderSet<Biome> holderSet,
		Map<MobCategory, StructureSpawnOverride> spawns,
		GenerationStep.Decoration featureStep,
		TerrainAdjustment terrainAdaptation
	) {
		return new Structure.StructureSettings(holderSet, spawns, featureStep, terrainAdaptation);
	}

	public static Structure.StructureSettings structure(
		HolderSet<Biome> holderSet,
		GenerationStep.Decoration featureStep,
		TerrainAdjustment terrainAdaptation
	) {
		return structure(holderSet, Map.of(), featureStep, terrainAdaptation);
	}

	public static void register(BootstrapContext<StructureTemplatePool> pool, String location, StructureTemplatePool templatePool) {
		pool.register(Pools.parseKey(location), templatePool);
	}

}
