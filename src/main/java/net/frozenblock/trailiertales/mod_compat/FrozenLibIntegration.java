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

package net.frozenblock.trailiertales.mod_compat;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.FrozenLibConstants;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.wind.api.WindDisturbance;
import net.frozenblock.lib.wind.api.WindDisturbanceLogic;
import net.frozenblock.lib.worldgen.structure.api.StructureGenerationConditionApi;
import net.frozenblock.lib.worldgen.structure.api.StructurePlacementExclusionApi;
import net.frozenblock.lib.worldgen.structure.api.StructureProcessorApi;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.frozenblock.trailiertales.worldgen.structure.datagen.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DeepslateRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DesertRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.GenericRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.JungleRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SavannaRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SnowyRuinsGenerator;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LootTableTrigger;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

public class FrozenLibIntegration extends ModIntegration {
	public static final ResourceLocation APPARITION_WIND_DISTURBANCE = TTConstants.id("apparition");

	public FrozenLibIntegration() {
		super("frozenlib");
	}

	@Override
	public void initPreFreeze() {
		WindDisturbanceLogic.register(
			APPARITION_WIND_DISTURBANCE,
			(WindDisturbanceLogic.DisturbanceLogic<Apparition>) (source, level, windOrigin, affectedArea, windTarget) -> {
				if (source.isPresent()) {
					double distance = windOrigin.distanceTo(windTarget);
					if (distance <= 6D) {
						Vec3 differenceInPoses = windOrigin.subtract(windTarget);
						double scaledDistance = (6D - distance) / 6D;
						double strengthFromDistance = Mth.clamp((6D - distance) / 4.5D, 0D, 1D);
						double x = scaledDistance * differenceInPoses.x * 0.3D;
						double y = scaledDistance * differenceInPoses.y * 0.3D;
						double z = scaledDistance * differenceInPoses.z * 0.3D;
						Vec3 windVec = new Vec3(x, y, z);
						return new WindDisturbance.DisturbanceResult(strengthFromDistance, 6D - distance, windVec);
					}
				}
				return null;
			}
		);
	}

	@Override
	public void init() {
		BlockSoundTypeOverwrites.addBlockTag(TTBlockTags.SOUND_UNPOLISHED_BRICKS, TTSounds.BRICKS, () -> TTBlockConfig.get().blockSounds.unpolished_bricks);
		BlockSoundTypeOverwrites.addBlockTag(TTBlockTags.SOUND_POLISHED_BRICKS, TTSounds.POLISHED_BRICKS, () -> TTBlockConfig.get().blockSounds.polished_bricks);
		BlockSoundTypeOverwrites.addBlockTag(TTBlockTags.SOUND_POLISHED_CALCITE, TTSounds.POLISHED_CALCITE, () -> TTBlockConfig.get().blockSounds.polished_calcite);
		BlockSoundTypeOverwrites.addBlockTag(TTBlockTags.SOUND_CALCITE_BRICKS, TTSounds.CALCITE_BRICKS_ALT, () -> TTBlockConfig.get().blockSounds.calcite_bricks);
		BlockSoundTypeOverwrites.addBlockTag(TTBlockTags.SOUND_POLISHED, TTSounds.POLISHED, () -> TTBlockConfig.get().blockSounds.polished);
		BlockSoundTypeOverwrites.addBlockTag(TTBlockTags.SOUND_POLISHED_DEEPSLATE, TTSounds.POLISHED_DEEPSLATE, () -> TTBlockConfig.get().blockSounds.polished_deepslate);
		BlockSoundTypeOverwrites.addBlockTag(TTBlockTags.SOUND_POLISHED_TUFF, TTSounds.POLISHED_TUFF, () -> TTBlockConfig.get().blockSounds.polished_tuff);
		BlockSoundTypeOverwrites.addBlockTag(TTBlockTags.SOUND_POLISHED_BASALT, TTSounds.POLISHED_BASALT, () -> TTBlockConfig.get().blockSounds.polished_basalt);

		StructureGenerationConditionApi.addGenerationCondition(CatacombsGenerator.CATACOMBS_STRUCTURE_SET_KEY.location(), () -> TTWorldgenConfig.GENERATE_CATACOMBS);
		StructureGenerationConditionApi.addGenerationCondition(BadlandsRuinsGenerator.BADLANDS_RUINS_KEY.location(), () -> TTWorldgenConfig.GENERATE_BADLANDS_RUINS);
		StructureGenerationConditionApi.addGenerationCondition(DeepslateRuinsGenerator.DEEPSLATE_RUINS_KEY.location(), () -> TTWorldgenConfig.GENERATE_DEEPSLATE_RUINS);
		StructureGenerationConditionApi.addGenerationCondition(DesertRuinsGenerator.DESERT_RUINS_KEY.location(), () -> TTWorldgenConfig.GENERATE_DESERT_RUINS);
		StructureGenerationConditionApi.addGenerationCondition(GenericRuinsGenerator.RUINS_KEY.location(), () -> TTWorldgenConfig.GENERATE_GENERIC_RUINS);
		StructureGenerationConditionApi.addGenerationCondition(JungleRuinsGenerator.JUNGLE_RUINS_KEY.location(), () -> TTWorldgenConfig.GENERATE_JUNGLE_RUINS);
		StructureGenerationConditionApi.addGenerationCondition(SavannaRuinsGenerator.SAVANNA_RUINS_KEY.location(), () -> TTWorldgenConfig.GENERATE_SAVANNA_RUINS);
		StructureGenerationConditionApi.addGenerationCondition(SnowyRuinsGenerator.SNOWY_RUINS_KEY.location(), () -> TTWorldgenConfig.GENERATE_SNOWY_RUINS);

		StructurePlacementExclusionApi.addExclusion(
			BuiltinStructureSets.TRIAL_CHAMBERS.location(),
			CatacombsGenerator.CATACOMBS_STRUCTURE_SET_KEY.location(),
			8
		);

		StructurePlacementExclusionApi.addExclusion(
			DeepslateRuinsGenerator.DEEPSLATE_RUINS_KEY.location(),
			BuiltinStructureSets.ANCIENT_CITIES.location(),
			8
		);

		StructurePlacementExclusionApi.addExclusion(
			DesertRuinsGenerator.DESERT_RUINS_KEY.location(),
			BuiltinStructureSets.DESERT_PYRAMIDS.location(),
			3
		);

		if (TTWorldgenConfig.get().endCity.generateCracked) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.location(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, TTBlocks.CRACKED_END_STONE_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.2F), AlwaysTrueTest.INSTANCE, TTBlocks.CRACKED_PURPUR_BLOCK.defaultBlockState())
					)
				)
			);
		}

		if (TTWorldgenConfig.get().endCity.generateChoral) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.location(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, TTBlocks.CHORAL_END_STONE_BRICKS.defaultBlockState())
					)
				)
			);
		}

		if (TTWorldgenConfig.get().endCity.generateChiseled) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.location(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockStateMatchTest(Blocks.PURPUR_PILLAR.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.4F), AlwaysTrueTest.INSTANCE, TTBlocks.CHISELED_PURPUR_BLOCK.defaultBlockState()),
						new ProcessorRule(new RandomBlockStateMatchTest(Blocks.PURPUR_PILLAR.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.4F), AlwaysTrueTest.INSTANCE, TTBlocks.CHISELED_PURPUR_BLOCK.defaultBlockState())
					)
				)
			);
		}

		AdvancementEvents.INIT.register((holder, registries) -> {
			HolderLookup<EntityType<?>> entity = registries.lookupOrThrow(Registries.ENTITY_TYPE);
			Advancement advancement = holder.value();
			if (TTMiscConfig.get().modify_advancements) {
				switch (holder.id().toString()) {
					case "minecraft:adventure/kill_a_mob" -> {
						AdvancementAPI.addCriteria(advancement, TTConstants.string("apparition"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entity, TTEntityTypes.APPARITION)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								TTConstants.string("apparition")
							)
						);
					}
					case "minecraft:adventure/kill_all_mobs" -> {
						AdvancementAPI.addCriteria(advancement, TTConstants.string("apparition"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entity, TTEntityTypes.APPARITION)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									TTConstants.string("apparition")
								)
							))
						);
					}
					case "minecraft:husbandry/plant_any_sniffer_seed" -> {
						AdvancementAPI.addCriteria(advancement, "trailiertales:cyan_rose", CriteriaTriggers.PLACED_BLOCK.createCriterion(
							ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TTBlocks.CYAN_ROSE_CROP).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "trailiertales:manedrop", CriteriaTriggers.PLACED_BLOCK.createCriterion(
							ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TTBlocks.MANEDROP_CROP).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "trailiertales:dawntrail", CriteriaTriggers.PLACED_BLOCK.createCriterion(
							ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(TTBlocks.DAWNTRAIL_CROP).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								"trailiertales:cyan_rose",
								"trailiertales:manedrop",
								"trailiertales:dawntrail"
							)
						);
					}
					case "minecraft:adventure/salvage_sherd" -> {
						addLootTableRequirement(advancement, GenericRuinsGenerator.RUINS_KEY.location().toString(), TTLootTables.RUINS_ARCHAEOLOGY);
						addLootTableRequirement(advancement, SnowyRuinsGenerator.SNOWY_RUINS_KEY.location().toString(), TTLootTables.SNOWY_RUINS_ARCHAEOLOGY);
						addLootTableRequirement(advancement, BadlandsRuinsGenerator.BADLANDS_RUINS_KEY.location().toString(), TTLootTables.BADLANDS_RUINS_ARCHAEOLOGY);
						addLootTableRequirement(advancement, DeepslateRuinsGenerator.DEEPSLATE_RUINS_KEY.location().toString(), TTLootTables.DEEPSLATE_RUINS_ARCHAEOLOGY);
						addLootTableRequirement(advancement, DesertRuinsGenerator.DESERT_RUINS_KEY.location().toString(), TTLootTables.DESERT_RUINS_ARCHAEOLOGY);
						addLootTableRequirement(advancement, JungleRuinsGenerator.JUNGLE_RUINS_KEY.location().toString(), TTLootTables.JUNGLE_RUINS_ARCHAEOLOGY);
						addLootTableRequirement(advancement, SavannaRuinsGenerator.SAVANNA_RUINS_KEY.location().toString(), TTLootTables.SAVANNA_RUINS_ARCHAEOLOGY);
						addLootTableRequirement(advancement, CatacombsGenerator.CATACOMBS_KEY.location().toString(), TTLootTables.CATACOMBS_ARCHAEOLOGY_TOMB);
						addLootTableRequirement(advancement, CatacombsGenerator.CATACOMBS_KEY.location().toString(), TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE);
						addLootTableRequirement(advancement, CatacombsGenerator.CATACOMBS_KEY.location().toString(), TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR);
					}
					case "minecraft:nether/all_potions" -> {
						if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
							Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
							MobEffectsPredicate predicate = criterion.triggerInstance().effects.orElseThrow();
							Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap);
							map.put(TTMobEffects.TRANSFIGURING, new MobEffectsPredicate.MobEffectInstancePredicate());
							predicate.effectMap = map;
						}
					}
					case "minecraft:nether/all_effects" -> {
						if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
							Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
							MobEffectsPredicate predicate = criterion.triggerInstance().effects.orElseThrow();
							Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap);
							map.put(TTMobEffects.HAUNT, new MobEffectsPredicate.MobEffectInstancePredicate());
							map.put(TTMobEffects.TRANSFIGURING, new MobEffectsPredicate.MobEffectInstancePredicate());
							map.put(TTMobEffects.SIEGE_OMEN, new MobEffectsPredicate.MobEffectInstancePredicate());
							predicate.effectMap = map;
						}
					}
					default -> {
					}
				}
			}
		});
	}

	private static void addLootTableRequirement(Advancement advancement, String criteriaName, ResourceKey<LootTable> lootTable) {
		AdvancementAPI.addCriteria(
			advancement,
			criteriaName, LootTableTrigger.TriggerInstance.lootTableUsed(lootTable)
		);
		addLootRequirementToList(advancement, criteriaName);
	}

	private static void addLootRequirementToList(Advancement advancement, String requirement) {
		AdvancementAPI.setupRequirements(advancement);
		List<List<String>> list = new ArrayList<>(advancement.requirements().requirements);
		if (list.isEmpty()) {
			list.add(List.of(requirement));
		} else {
			for (List<String> requirements : list) {
				if (!requirements.contains("has_sherd")) {
					List<String> finalList = new ArrayList<>(requirements);
					finalList.add(requirement);
					list.add(Collections.unmodifiableList(finalList));
					list.remove(requirements);
					break;
				}
			}
		}

		advancement.requirements().requirements = Collections.unmodifiableList(list);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void clientInit() {
	}
}
