package net.frozenblock.trailiertales.entity.ai;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.trailiertales.entity.Ghost;
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

public class GhostAi {

	public static final List<SensorType<? extends Sensor<? super Ghost>>> SENSOR_TYPES = List.of(
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
		MemoryModuleType.PATH
	);

	public static Brain<Ghost> makeBrain(Ghost ghost, Brain<Ghost> brain) {
		initCoreActivity(brain);
		initIdleActivity(brain);
		initFightActivity(ghost, brain);
		brain.setCoreActivities(Set.of(Activity.CORE));
		brain.setDefaultActivity(Activity.FIGHT);
		brain.useDefaultActivity();
		return brain;
	}

	private static void initCoreActivity(Brain<Ghost> brain) {
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

	private static void initIdleActivity(Brain<Ghost> brain) {
		brain.addActivity(
			Activity.IDLE,
			10,
			ImmutableList.of(
				StartAttacking.create(ghost -> true, GhostAi::findNearestValidAttackTarget),
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

	private static void initFightActivity(Ghost ghost, Brain<Ghost> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				StopAttackingIfTargetInvalid.create(entity -> !ghost.canTargetEntity(entity), GhostAi::onTargetInvalid, true),
				SetEntityLookTarget.create(entity -> isTarget(ghost, entity), (float) ghost.getAttributeValue(Attributes.FOLLOW_RANGE)),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.2F),
				MeleeAttack.create(20)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static boolean isTarget(Ghost ghost, LivingEntity target) {
		return ghost.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(entity -> entity == target).isPresent();
	}

	private static void onTargetInvalid(@NotNull Ghost ghost, @NotNull LivingEntity target) {
		if (ghost.getTarget() == target) {
			ghost.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		}
		ghost.getNavigation().stop();
	}

	public static void updateActivity(Ghost ghost) {
		ghost.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	@NotNull
	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(@NotNull Ghost ghost) {
		return ghost.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}
}
