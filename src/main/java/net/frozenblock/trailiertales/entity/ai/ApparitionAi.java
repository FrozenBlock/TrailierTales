package net.frozenblock.trailiertales.entity.ai;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ApparitionAi {

	public static final List<SensorType<? extends Sensor<? super Apparition>>> SENSOR_TYPES = List.of(
		SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS
	);

	public static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
		MemoryModuleType.NEAREST_ATTACKABLE,
		MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
		MemoryModuleType.ATTACK_TARGET,
		MemoryModuleType.WALK_TARGET,
		MemoryModuleType.HURT_BY,
		MemoryModuleType.HURT_BY_ENTITY,
		MemoryModuleType.PATH,
		MemoryModuleType.NEAREST_PLAYERS,
		MemoryModuleType.NEAREST_VISIBLE_PLAYER,
		MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER
	);

	public static Brain<Apparition> makeBrain(Apparition apparition, Brain<Apparition> brain) {
		initCoreActivity(brain);
		initIdleActivity(brain);
		initFightActivity(apparition, brain);
		brain.setCoreActivities(Set.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void initCoreActivity(Brain<Apparition> brain) {
		brain.addActivity(
			Activity.CORE,
			0,
			ImmutableList.of(
				new Swim(0.8F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink()
			)
		);
	}

	private static void initIdleActivity(Brain<Apparition> brain) {
		brain.addActivity(
			Activity.IDLE,
			10,
			ImmutableList.of(
				StartAttacking.create(ghost -> true, ApparitionAi::findNearestValidAttackTarget),
				new RunOne<>( // idle look
					ImmutableList.of(
						Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 1),
						Pair.of(new DoNothing(30, 60), 1)
					)
				),
				new RunOne<>( // idle movement
					ImmutableList.of(
						Pair.of(RandomStroll.fly(0.6F), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
						Pair.of(new DoNothing(30, 60), 1)
					)
				)
			)
		);
	}

	private static void initFightActivity(Apparition apparition, Brain<Apparition> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				StopAttackingIfTargetInvalid.create(entity -> !apparition.canTargetEntity(entity), ApparitionAi::onTargetInvalid, true),
				SetEntityLookTarget.create(entity -> isTarget(apparition, entity), (float) apparition.getAttributeValue(Attributes.FOLLOW_RANGE)),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.2F),
				MeleeAttack.create(20)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static boolean isTarget(Apparition apparition, LivingEntity target) {
		return apparition.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(entity -> entity == target).isPresent();
	}

	private static void onTargetInvalid(@NotNull Apparition apparition, @NotNull LivingEntity target) {
		if (apparition.getTarget() == target) {
			apparition.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		}
		apparition.getNavigation().stop();
	}

	public static void updateActivity(Apparition apparition) {
		apparition.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	@NotNull
	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(@NotNull Apparition ghost) {
		return ghost.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
	}
}
