/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.advancements.modification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.advancement.impl.AdvancementRequirementsInterface;
import net.frozenblock.lib.advancement.mixin.MobEffectsPredicateAccessor;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import net.frozenblock.trailiertales.data.worldgen.structure.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.CatacombsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.DeepslateRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.DesertRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.GenericRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.JungleRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.SavannaRuinsGenerator;
import net.frozenblock.trailiertales.data.worldgen.structure.SnowyRuinsGenerator;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.EffectsChangedTrigger;
import net.minecraft.advancements.triggers.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.triggers.KilledTrigger;
import net.minecraft.advancements.triggers.LootTableTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

public class TTAdvancementModifications {

	public static void init() {
		AdvancementEvents.INIT.register((holder, registries) -> {
			final HolderLookup<Block> blocks = registries.lookupOrThrow(Registries.BLOCK);
			final HolderLookup<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
			final HolderLookup<LootTable> lootTables = registries.lookupOrThrow(Registries.LOOT_TABLE);
			final Advancement advancement = holder.value();
			if (!TTMiscConfig.MODIFY_ADVANCEMENTS.get()) return;

			switch (holder.id().toString()) {
				case "minecraft:adventure/kill_a_mob" -> {
					AdvancementAPI.addCriteria(advancement, TTConstants.string("apparition"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
						KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, TTEntityTypes.APPARITION.get())).triggerInstance())
					);
					AdvancementAPI.addRequirementsToList(advancement,
						List.of(TTConstants.string("apparition"))
					);
				}
				case "minecraft:adventure/kill_all_mobs" -> {
					AdvancementAPI.addCriteria(advancement, TTConstants.string("apparition"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
						KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, TTEntityTypes.APPARITION.get())).triggerInstance())
					);
					AdvancementAPI.addRequirementsAsNewList(advancement,
						new AdvancementRequirements(List.of(
							List.of(TTConstants.string("apparition"))
						))
					);
				}
				case "minecraft:husbandry/plant_any_sniffer_seed" -> {
					AdvancementAPI.addCriteria(advancement, "trailiertales:cyan_rose", CriteriaTriggers.PLACED_BLOCK.createCriterion(
						ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(blocks, TTBlocks.CYAN_ROSE_CROP.get()).triggerInstance())
					);
					AdvancementAPI.addCriteria(advancement, "trailiertales:manedrop", CriteriaTriggers.PLACED_BLOCK.createCriterion(
						ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(blocks, TTBlocks.MANEDROP_CROP.get()).triggerInstance())
					);
					AdvancementAPI.addCriteria(advancement, "trailiertales:guzmania", CriteriaTriggers.PLACED_BLOCK.createCriterion(
						ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(blocks, TTBlocks.GUZMANIA_CROP.get()).triggerInstance())
					);
					AdvancementAPI.addCriteria(advancement, "trailiertales:dawntrail", CriteriaTriggers.PLACED_BLOCK.createCriterion(
						ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(blocks, TTBlocks.DAWNTRAIL_CROP.get()).triggerInstance())
					);
					AdvancementAPI.addCriteria(advancement, "trailiertales:lithops", CriteriaTriggers.PLACED_BLOCK.createCriterion(
						ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(blocks, TTBlocks.LITHOPS_CROP.get()).triggerInstance())
					);
					AdvancementAPI.addRequirementsToList(advancement,
						List.of(
							"trailiertales:cyan_rose",
							"trailiertales:manedrop",
							"trailiertales:guzmania",
							"trailiertales:dawntrail",
							"trailiertales:lithops"
						)
					);
				}
				case "minecraft:adventure/salvage_sherd" -> {
					addLootTableRequirement(advancement, GenericRuinsGenerator.RUINS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.RUINS_ARCHAEOLOGY));
					addLootTableRequirement(advancement, SnowyRuinsGenerator.SNOWY_RUINS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.SNOWY_RUINS_ARCHAEOLOGY));
					addLootTableRequirement(advancement, BadlandsRuinsGenerator.BADLANDS_RUINS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.BADLANDS_RUINS_ARCHAEOLOGY));
					addLootTableRequirement(advancement, DeepslateRuinsGenerator.DEEPSLATE_RUINS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.DEEPSLATE_RUINS_ARCHAEOLOGY));
					addLootTableRequirement(advancement, DesertRuinsGenerator.DESERT_RUINS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.DESERT_RUINS_ARCHAEOLOGY));
					addLootTableRequirement(advancement, JungleRuinsGenerator.JUNGLE_RUINS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.JUNGLE_RUINS_ARCHAEOLOGY));
					addLootTableRequirement(advancement, SavannaRuinsGenerator.SAVANNA_RUINS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.SAVANNA_RUINS_ARCHAEOLOGY));
					addLootTableRequirement(advancement, CatacombsGenerator.CATACOMBS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.CATACOMBS_ARCHAEOLOGY_TOMB));
					addLootTableRequirement(advancement, CatacombsGenerator.CATACOMBS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE));
					addLootTableRequirement(advancement, CatacombsGenerator.CATACOMBS_KEY.identifier().toString(), lootTables.getOrThrow(TTLootTables.CATACOMBS_ARCHAEOLOGY_CORRIDOR));
				}
				case "minecraft:nether/all_potions" -> {
					if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
						Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
						MobEffectsPredicate predicate = criterion.triggerInstance().effects().orElseThrow();
						Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap());
						map.put(TTMobEffects.TRANSFIGURING.asHolder(), new MobEffectsPredicate.MobEffectInstancePredicate());
						((MobEffectsPredicateAccessor) (Object) predicate).frozenLib$setEffectMap(map);
					}
				}
				case "minecraft:nether/all_effects" -> {
					if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
						Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
						MobEffectsPredicate predicate = criterion.triggerInstance().effects().orElseThrow();
						Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap());
						map.put(TTMobEffects.HAUNT.asHolder(), new MobEffectsPredicate.MobEffectInstancePredicate());
						map.put(TTMobEffects.TRANSFIGURING.asHolder(), new MobEffectsPredicate.MobEffectInstancePredicate());
						map.put(TTMobEffects.SIEGE_OMEN.asHolder(), new MobEffectsPredicate.MobEffectInstancePredicate());
						((MobEffectsPredicateAccessor) (Object) predicate).frozenLib$setEffectMap(map);
					}
				}
				default -> {
				}
			}
		});
	}

	private static void addLootTableRequirement(Advancement advancement, String criteriaName, Holder<LootTable> lootTable) {
		AdvancementAPI.addCriteria(
			advancement,
			criteriaName, LootTableTrigger.TriggerInstance.lootTableUsed(lootTable)
		);
		addLootRequirementToList(advancement, criteriaName);
	}

	private static void addLootRequirementToList(Advancement advancement, String requirement) {
		AdvancementAPI.setupRequirements(advancement);
		final AdvancementRequirementsInterface requirementsInterface = (AdvancementRequirementsInterface) advancement.requirements();
		final List<List<String>> requirementsList = new ArrayList<>(requirementsInterface.frozenLib$getRequirements());
		if (requirementsList.isEmpty()) {
			requirementsList.add(List.of(requirement));
		} else {
			for (List<String> requirements : requirementsList) {
				if (requirements.contains("has_sherd")) continue;

				final List<String> finalList = new ArrayList<>(requirements);
				finalList.add(requirement);
				requirementsList.add(Collections.unmodifiableList(finalList));
				requirementsList.remove(requirements);
				break;
			}
		}

		requirementsInterface.frozenLib$setRequirements(Collections.unmodifiableList(requirementsList));
	}
}
