package net.frozenblock.trailiertales.mod_compat;

import com.google.common.collect.ImmutableList;
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
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.config.WorldgenConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
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
						Vec3 windVec = (new Vec3(x, y, z)).scale(1.0);
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
			RegisterSounds.BRICKS,
			() -> true
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				Blocks.BRICKS,
				Blocks.BRICK_STAIRS,
				Blocks.BRICK_SLAB,
				Blocks.BRICK_WALL,
				RegisterBlocks.CRACKED_BRICKS,
				RegisterBlocks.MOSSY_BRICKS,
				RegisterBlocks.MOSSY_BRICK_STAIRS,
				RegisterBlocks.MOSSY_BRICK_SLAB,
				RegisterBlocks.MOSSY_BRICK_WALL
			},
			RegisterSounds.BRICKS,
			() -> true
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				RegisterBlocks.GRANITE_BRICKS,
				RegisterBlocks.GRANITE_BRICK_STAIRS,
				RegisterBlocks.GRANITE_BRICK_SLAB,
				RegisterBlocks.GRANITE_BRICK_WALL,
				RegisterBlocks.CHISELED_GRANITE_BRICKS,
				RegisterBlocks.CRACKED_GRANITE_BRICKS,
				RegisterBlocks.MOSSY_GRANITE_BRICKS,
				RegisterBlocks.MOSSY_GRANITE_BRICK_STAIRS,
				RegisterBlocks.MOSSY_GRANITE_BRICK_SLAB,
				RegisterBlocks.MOSSY_GRANITE_BRICK_WALL
			},
			RegisterSounds.BRICKS,
			() -> true
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				RegisterBlocks.DIORITE_BRICKS,
				RegisterBlocks.DIORITE_BRICK_STAIRS,
				RegisterBlocks.DIORITE_BRICK_SLAB,
				RegisterBlocks.DIORITE_BRICK_WALL,
				RegisterBlocks.CHISELED_DIORITE_BRICKS,
				RegisterBlocks.CRACKED_DIORITE_BRICKS,
				RegisterBlocks.MOSSY_DIORITE_BRICKS,
				RegisterBlocks.MOSSY_DIORITE_BRICK_STAIRS,
				RegisterBlocks.MOSSY_DIORITE_BRICK_SLAB,
				RegisterBlocks.MOSSY_DIORITE_BRICK_WALL
			},
			RegisterSounds.BRICKS,
			() -> true
		);

		BlockSoundGroupOverwrites.addBlocks(
			new Block[]{
				RegisterBlocks.ANDESITE_BRICKS,
				RegisterBlocks.ANDESITE_BRICK_STAIRS,
				RegisterBlocks.ANDESITE_BRICK_SLAB,
				RegisterBlocks.ANDESITE_BRICK_WALL,
				RegisterBlocks.CHISELED_ANDESITE_BRICKS,
				RegisterBlocks.CRACKED_ANDESITE_BRICKS,
				RegisterBlocks.MOSSY_ANDESITE_BRICKS,
				RegisterBlocks.MOSSY_ANDESITE_BRICK_STAIRS,
				RegisterBlocks.MOSSY_ANDESITE_BRICK_SLAB,
				RegisterBlocks.MOSSY_ANDESITE_BRICK_WALL
			},
			RegisterSounds.BRICKS,
			() -> true
		);

		if (WorldgenConfig.get().endCity.generateCracked) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.location(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, RegisterBlocks.CRACKED_END_STONE_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.2F), AlwaysTrueTest.INSTANCE, RegisterBlocks.CRACKED_PURPUR_BLOCK.defaultBlockState())
					)
				)
			);
		}

		if (WorldgenConfig.get().endCity.generateChoral) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.location(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, RegisterBlocks.CHORAL_END_STONE_BRICKS.defaultBlockState())
					)
				)
			);
		}

		if (WorldgenConfig.get().endCity.generateChiseled) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.END_CITY.location(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockStateMatchTest(Blocks.PURPUR_PILLAR.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.4F), AlwaysTrueTest.INSTANCE, RegisterBlocks.CHISELED_PURPUR_BLOCK.defaultBlockState()),
						new ProcessorRule(new RandomBlockStateMatchTest(Blocks.PURPUR_PILLAR.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.4F), AlwaysTrueTest.INSTANCE, RegisterBlocks.CHISELED_PURPUR_BLOCK.defaultBlockState())
					)
				)
			);
		}

		AdvancementEvents.INIT.register((holder, registries) -> {
			Advancement advancement = holder.value();
			//if (AmbienceAndMiscConfig.get().modifyAdvancements) {
				switch (holder.id().toString()) {
					case "minecraft:adventure/kill_a_mob" -> {
						AdvancementAPI.addCriteria(advancement, TrailierConstants.string("apparition"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(RegisterEntities.APPARITION)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								TrailierConstants.string("apparition")
							)
						);
					}
					case "minecraft:adventure/kill_all_mobs" -> {
						AdvancementAPI.addCriteria(advancement, TrailierConstants.string("apparition"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(RegisterEntities.APPARITION)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									TrailierConstants.string("apparition")
								)
							))
						);
					}
					case "minecraft:adventure/plant_any_sniffer_seed" -> {
						AdvancementAPI.addCriteria(advancement, "trailiertales:cyan_rose", CriteriaTriggers.PLACED_BLOCK.createCriterion(
							ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(RegisterBlocks.CYAN_ROSE_CROP).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								"trailiertales:cyan_rose"
							)
						);
					}
					case "minecraft:nether/all_effects" -> {
						if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
							Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
							MobEffectsPredicate predicate = criterion.triggerInstance().effects.orElseThrow();
							Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap);
							map.put(net.frozenblock.trailiertales.registry.RegisterMobEffects.HAUNT, new MobEffectsPredicate.MobEffectInstancePredicate());
							predicate.effectMap = map;
						}
					}
					default -> {
					}
				}

		//	}
		});
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void clientInit() {
	}
}
