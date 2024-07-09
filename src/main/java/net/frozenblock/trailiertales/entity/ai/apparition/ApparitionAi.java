package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.RegisterSensorTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ApparitionAi {
	public static final List<SensorType<? extends Sensor<? super Apparition>>> SENSOR_TYPES = List.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.HURT_BY,
		RegisterSensorTypes.APPARITION_PLAYER_SENSOR,
		RegisterSensorTypes.APPARITION_ATTACKABLES_SENSOR,
		RegisterSensorTypes.APPARITION_SPECIFIC_SENSOR,
		RegisterSensorTypes.APPARITION_NEAREST_ITEM_SENSOR,
		RegisterSensorTypes.APPARITION_AIDABLES_SENSOR
	);

	public static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.NEAREST_LIVING_ENTITIES,
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
		MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
		RegisterMemoryModuleTypes.NEARBY_APPARITIONS,
		MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
		MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
		RegisterMemoryModuleTypes.AID_COOLDOWN,
		RegisterMemoryModuleTypes.IS_AIDING,
		RegisterMemoryModuleTypes.NEARBY_AIDABLES,
		RegisterMemoryModuleTypes.NEAREST_AIDABLE,
		RegisterMemoryModuleTypes.SEE_TIME,
		RegisterMemoryModuleTypes.STRAFING_CLOCKWISE,
		RegisterMemoryModuleTypes.STRAFING_BACKWARDS,
		RegisterMemoryModuleTypes.STRAFING_TIME,
		RegisterMemoryModuleTypes.CHARGING_TICKS
	);

	@Contract("_, _ -> param2")
	public static @NotNull Brain<Apparition> makeBrain(Apparition apparition, Brain<Apparition> brain) {
		initCoreActivity(brain);
		initIdleActivity(brain);
		initFightActivity(apparition, brain);
		brain.setCoreActivities(Set.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void initCoreActivity(@NotNull Brain<Apparition> brain) {
		brain.addActivity(
			Activity.CORE,
			0,
			ImmutableList.of(
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new CountDownCooldownTicks(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS),
				GoToWantedItem.create(
					apparition -> apparition.getInventory().getItems().getFirst().isEmpty(),
					1.5F, true, 32)
			)
		);
	}

	private static void initIdleActivity(@NotNull Brain<Apparition> brain) {
		brain.addActivity(
			Activity.IDLE,
			10,
			ImmutableList.of(
				StartAttacking.create(apparition -> true, ApparitionAi::findNearestValidAttackTarget),
				new RunOne<>( // idle look
					ImmutableList.of(
						Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8F), 1),
						Pair.of(new DoNothing(30, 60), 1)
					)
				),
				new RunOne<>( // idle movement
					ImmutableList.of(
						Pair.of(RandomStroll.fly(0.6F), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 2),
						Pair.of(new DoNothing(30, 60), 1)
					)
				)
			)
		);
	}

	private static void initFightActivity(Apparition apparition, @NotNull Brain<Apparition> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				StopAttackingIfTargetInvalid.create(entity -> !apparition.canTargetEntity(entity), ApparitionAi::onTargetInvalid, true),
				new ApparitionShoot(),
				new ApparitionAid()
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static boolean isTarget(@NotNull Apparition apparition, LivingEntity target) {
		return apparition.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(entity -> entity == target).isPresent();
	}

	private static void onTargetInvalid(@NotNull Apparition apparition, @NotNull LivingEntity target) {
		if (apparition.getTarget() == target) {
			apparition.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		}
	}

	public static void updateActivity(@NotNull Apparition apparition) {
		apparition.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	@NotNull
	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(@NotNull Apparition apparition) {
		return apparition.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}

	@NotNull
	private static Optional<? extends LivingEntity> findNearestValidPossessionTarget(@NotNull Apparition apparition) {
		return apparition.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}

	public static void wasHurtBy(@NotNull Apparition apparition, LivingEntity target) {
		if (apparition.canTargetEntity(target)) {
			if (!Sensor.isEntityAttackableIgnoringLineOfSight(apparition, target)) {
				return;
			}
			if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(apparition, target, 4D)) {
				return;
			}

			setAngerTarget(apparition, target);
			broadcastAngerTarget(apparition, target);
		}
	}

	public static void setAngerTarget(@NotNull Apparition apparition, LivingEntity target) {
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(apparition, target)) {
			return;
		}
		apparition.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		apparition.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
	}

	public static void broadcastAngerTarget(@NotNull Apparition apparition, LivingEntity target) {
	//	Optional<List<Apparition>> nearbyApparitions = getNearbyApparitions(apparition);
	//	nearbyApparitions.ifPresent(apparitions -> apparitions.forEach(listedApparition -> setAngerTargetIfCloserThanCurrent(listedApparition, target)));
	}

	private static void setAngerTargetIfCloserThanCurrent(@NotNull Apparition apparition, LivingEntity currentTarget) {
		Optional<LivingEntity> optional = getAngerTarget(apparition);
		LivingEntity livingEntity = BehaviorUtils.getNearestTarget(apparition, optional, currentTarget);
		if (optional.isPresent() && optional.get() == livingEntity) {
			return;
		}
		setAngerTarget(apparition, livingEntity);
	}

	private static void setAngerTargetToNearestTargetablePlayerIfFound(Apparition apparition, LivingEntity currentTarget) {
		Optional<Player> optional = getNearestVisibleTargetablePlayer(apparition);
		if (optional.isPresent()) {
			setAngerTarget(apparition, optional.get());
		} else {
			setAngerTarget(apparition, currentTarget);
		}

	}

	@NotNull
	private static Optional<LivingEntity> getAngerTarget(@NotNull Apparition apparition) {
		return apparition.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
	}

	@NotNull
	private static Optional<List<Apparition>> getNearbyApparitions(@NotNull Apparition apparition) {
		return apparition.getBrain().getMemory(RegisterMemoryModuleTypes.NEARBY_APPARITIONS);
	}

	public static Optional<Player> getNearestVisibleTargetablePlayer(@NotNull Apparition apparition) {
		return apparition.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ? apparition.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
	}
}
