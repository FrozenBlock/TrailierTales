package net.frozenblock.trailiertales.mod_compat;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.FrozenSharedConstants;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites;
import net.frozenblock.lib.wind.api.WindDisturbance;
import net.frozenblock.lib.wind.api.WindDisturbanceLogic;
import net.frozenblock.lib.worldgen.structure.api.StructureProcessorApi;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEntities;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.worldgen.structure.datagen.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DeepslateRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DesertRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.JungleRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.RuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SavannaRuinsGenerator;
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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

public class FrozenLibIntegration extends ModIntegration {
	public static final ResourceLocation APPARITION_WIND_DISTURBANCE = FrozenSharedConstants.id("apparition");

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
		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.BRICKS,
				Blocks.BRICK_STAIRS,
				Blocks.BRICK_SLAB,
				Blocks.BRICK_WALL,
				TTBlocks.CRACKED_BRICKS,
				TTBlocks.MOSSY_BRICKS,
				TTBlocks.MOSSY_BRICK_STAIRS,
				TTBlocks.MOSSY_BRICK_SLAB,
				TTBlocks.MOSSY_BRICK_WALL
			},
			TTSounds.BRICKS,
			() -> TTBlockConfig.get().blockSounds.unpolished_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.STONE_BRICKS,
				Blocks.STONE_BRICK_STAIRS,
				Blocks.STONE_BRICK_SLAB,
				Blocks.STONE_BRICK_WALL,
				Blocks.CHISELED_STONE_BRICKS,
				Blocks.CRACKED_STONE_BRICKS,
				Blocks.MOSSY_STONE_BRICKS,
				Blocks.MOSSY_STONE_BRICK_STAIRS,
				Blocks.MOSSY_STONE_BRICK_SLAB,
				Blocks.MOSSY_STONE_BRICK_WALL,
				Blocks.INFESTED_STONE_BRICKS,
				Blocks.INFESTED_CHISELED_STONE_BRICKS,
				Blocks.INFESTED_CRACKED_STONE_BRICKS,
				Blocks.INFESTED_MOSSY_STONE_BRICKS
			},
			TTSounds.BRICKS,
			() -> TTBlockConfig.get().blockSounds.unpolished_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				TTBlocks.GRANITE_BRICKS,
				TTBlocks.GRANITE_BRICK_STAIRS,
				TTBlocks.GRANITE_BRICK_SLAB,
				TTBlocks.GRANITE_BRICK_WALL,
				TTBlocks.CHISELED_GRANITE_BRICKS,
				TTBlocks.CRACKED_GRANITE_BRICKS,
				TTBlocks.MOSSY_GRANITE_BRICKS,
				TTBlocks.MOSSY_GRANITE_BRICK_STAIRS,
				TTBlocks.MOSSY_GRANITE_BRICK_SLAB,
				TTBlocks.MOSSY_GRANITE_BRICK_WALL
			},
			TTSounds.POLISHED_BRICKS,
			() -> TTBlockConfig.get().blockSounds.polished_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				TTBlocks.DIORITE_BRICKS,
				TTBlocks.DIORITE_BRICK_STAIRS,
				TTBlocks.DIORITE_BRICK_SLAB,
				TTBlocks.DIORITE_BRICK_WALL,
				TTBlocks.CHISELED_DIORITE_BRICKS,
				TTBlocks.CRACKED_DIORITE_BRICKS,
				TTBlocks.MOSSY_DIORITE_BRICKS,
				TTBlocks.MOSSY_DIORITE_BRICK_STAIRS,
				TTBlocks.MOSSY_DIORITE_BRICK_SLAB,
				TTBlocks.MOSSY_DIORITE_BRICK_WALL
			},
			TTSounds.POLISHED_BRICKS,
			() -> TTBlockConfig.get().blockSounds.polished_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				TTBlocks.ANDESITE_BRICKS,
				TTBlocks.ANDESITE_BRICK_STAIRS,
				TTBlocks.ANDESITE_BRICK_SLAB,
				TTBlocks.ANDESITE_BRICK_WALL,
				TTBlocks.CHISELED_ANDESITE_BRICKS,
				TTBlocks.CRACKED_ANDESITE_BRICKS,
				TTBlocks.MOSSY_ANDESITE_BRICKS,
				TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS,
				TTBlocks.MOSSY_ANDESITE_BRICK_SLAB,
				TTBlocks.MOSSY_ANDESITE_BRICK_WALL
			},
			TTSounds.POLISHED_BRICKS,
			() -> TTBlockConfig.get().blockSounds.polished_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.PRISMARINE_BRICKS,
				Blocks.PRISMARINE_BRICK_STAIRS,
				Blocks.PRISMARINE_BRICK_SLAB,
				TTBlocks.PRISMARINE_BRICK_WALL,
			},
			TTSounds.BRICKS,
			() -> TTBlockConfig.get().blockSounds.unpolished_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.POLISHED_BLACKSTONE_BRICKS,
				Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS,
				Blocks.POLISHED_BLACKSTONE_BRICK_SLAB,
				Blocks.POLISHED_BLACKSTONE_BRICK_WALL,
				Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
			},
			TTSounds.POLISHED_BRICKS,
			() -> TTBlockConfig.get().blockSounds.polished_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				TTBlocks.POLISHED_CALCITE,
				TTBlocks.POLISHED_CALCITE_SLAB,
				TTBlocks.POLISHED_CALCITE_WALL,
				TTBlocks.POLISHED_CALCITE_STAIRS,
			},
			TTSounds.POLISHED_CALCITE,
			() -> TTBlockConfig.get().blockSounds.polished_calcite
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				TTBlocks.CALCITE_BRICKS,
				TTBlocks.CALCITE_BRICK_STAIRS,
				TTBlocks.CALCITE_BRICK_SLAB,
				TTBlocks.CALCITE_BRICK_WALL,
				TTBlocks.CHISELED_CALCITE_BRICKS,
				TTBlocks.CRACKED_CALCITE_BRICKS,
				TTBlocks.MOSSY_CALCITE_BRICKS,
				TTBlocks.MOSSY_CALCITE_BRICK_STAIRS,
				TTBlocks.MOSSY_CALCITE_BRICK_SLAB,
				TTBlocks.MOSSY_CALCITE_BRICK_WALL
			},
			TTSounds.CALCITE_BRICKS_ALT,
			() -> TTBlockConfig.get().blockSounds.calcite_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.END_STONE_BRICKS,
				Blocks.END_STONE_BRICK_STAIRS,
				Blocks.END_STONE_BRICK_SLAB,
				Blocks.END_STONE_BRICK_WALL,
				TTBlocks.CHISELED_END_STONE_BRICKS,
				TTBlocks.CRACKED_END_STONE_BRICKS,
				TTBlocks.CHORAL_END_STONE_BRICKS,
				TTBlocks.CHORAL_END_STONE_BRICK_STAIRS,
				TTBlocks.CHORAL_END_STONE_BRICK_SLAB,
				TTBlocks.CHORAL_END_STONE_BRICK_WALL
			},
			TTSounds.BRICKS,
			() -> TTBlockConfig.get().blockSounds.unpolished_bricks
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.POLISHED_ANDESITE,
				Blocks.POLISHED_ANDESITE_SLAB,
				Blocks.POLISHED_ANDESITE_STAIRS,
				TTBlocks.POLISHED_ANDESITE_WALL,
				Blocks.POLISHED_GRANITE,
				Blocks.POLISHED_GRANITE_SLAB,
				Blocks.POLISHED_GRANITE_STAIRS,
				TTBlocks.POLISHED_GRANITE_WALL,
				Blocks.POLISHED_DIORITE,
				Blocks.POLISHED_DIORITE_SLAB,
				Blocks.POLISHED_DIORITE_STAIRS,
				TTBlocks.POLISHED_DIORITE_WALL,
				Blocks.POLISHED_BLACKSTONE,
				Blocks.POLISHED_BLACKSTONE_SLAB,
				Blocks.POLISHED_BLACKSTONE_STAIRS,
				Blocks.POLISHED_BLACKSTONE_WALL,
				Blocks.CHISELED_POLISHED_BLACKSTONE,
				Blocks.POLISHED_BLACKSTONE_BUTTON,
				Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE,
			},
			TTSounds.POLISHED,
			() -> TTBlockConfig.get().blockSounds.polished
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.POLISHED_DEEPSLATE,
				Blocks.POLISHED_DEEPSLATE_SLAB,
				Blocks.POLISHED_DEEPSLATE_WALL,
				Blocks.POLISHED_DEEPSLATE_STAIRS
			},
			TTSounds.POLISHED_DEEPSLATE,
			() -> TTBlockConfig.get().blockSounds.polished_deepslate
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.POLISHED_TUFF,
				Blocks.POLISHED_TUFF_SLAB,
				Blocks.POLISHED_TUFF_WALL,
				Blocks.POLISHED_TUFF_STAIRS,
			},
			TTSounds.POLISHED_TUFF,
			() -> TTBlockConfig.get().blockSounds.polished_tuff
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.POLISHED_BASALT
			},
			TTSounds.POLISHED_BASALT,
			() -> TTBlockConfig.get().blockSounds.polished_basalt
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
			Advancement advancement = holder.value();
			if (TTMiscConfig.get().modify_advancements) {
				switch (holder.id().toString()) {
					case "minecraft:adventure/kill_a_mob" -> {
						AdvancementAPI.addCriteria(advancement, TTConstants.string("apparition"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TTEntities.APPARITION)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								TTConstants.string("apparition")
							)
						);
					}
					case "minecraft:adventure/kill_all_mobs" -> {
						AdvancementAPI.addCriteria(advancement, TTConstants.string("apparition"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TTEntities.APPARITION)).triggerInstance())
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
						addLootTableRequirement(advancement, RuinsGenerator.RUINS_KEY.location().toString(), TTLootTables.RUINS_ARCHAEOLOGY);
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
