package net.frozenblock.trailiertales.entity.ai.apparition;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.TTSensorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApparitionAi {
	public static final List<SensorType<? extends Sensor<? super Apparition>>> SENSOR_TYPES = List.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.HURT_BY,
		TTSensorTypes.APPARITION_PLAYER_SENSOR,
		TTSensorTypes.APPARITION_ATTACKABLES_SENSOR,
		TTSensorTypes.APPARITION_SPECIFIC_SENSOR,
		TTSensorTypes.APPARITION_NEAREST_ITEM_SENSOR,
		TTSensorTypes.APPARITION_AIDABLES_SENSOR
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
		TTMemoryModuleTypes.NEARBY_APPARITIONS,
		MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
		MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
		TTMemoryModuleTypes.AID_COOLDOWN,
		TTMemoryModuleTypes.AIDING_TIME,
		TTMemoryModuleTypes.NEARBY_AIDABLES,
		TTMemoryModuleTypes.NEAREST_AIDABLE,
		TTMemoryModuleTypes.SEE_TIME,
		TTMemoryModuleTypes.STRAFING_CLOCKWISE,
		TTMemoryModuleTypes.STRAFING_BACKWARDS,
		TTMemoryModuleTypes.STRAFING_TIME,
		TTMemoryModuleTypes.CHARGING_TICKS,
		MemoryModuleType.HOME,
		TTMemoryModuleTypes.HAUNTING_TICKS,
		TTMemoryModuleTypes.AIDING_ENTITIES
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
				new CountDownCooldownTicks(TTMemoryModuleTypes.AIDING_TIME),
				new ApparitionHaunt()
			)
		);
	}

	private static void initIdleActivity(@NotNull Brain<Apparition> brain) {
		brain.addActivity(
			Activity.IDLE,
			10,
			ImmutableList.of(
				StartAttacking.create((level, apparition) -> true, ApparitionAi::findNearestValidAttackTarget),
				StayCloseToTarget.create(ApparitionAi::getLookTarget, entity -> true, 7, 16, 1F),
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
				StopAttackingIfTargetInvalid.create((level, entity) -> !apparition.canTargetEntity(entity, level), ApparitionAi::onTargetInvalid, true),
				new RunOne<>(
					ImmutableList.of(
						Pair.of(SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1F), 1),
						Pair.of(new ApparitionAid(), 1),
						Pair.of(new ApparitionShoot(), 1),
						Pair.of(GoToWantedItem.create(
							apparition1 -> apparition1.getInventory().getItems().getFirst().isEmpty(),
							1.25F, true, 28
						), 1)
					)
				)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static boolean shouldGoTowardsHome(@NotNull LivingEntity apparition, @NotNull GlobalPos pos) {
		return ((Apparition)apparition).shouldReturnToHome(pos);
	}

	public static @Nullable BlockPos getHome(@NotNull Apparition apparition) {
		Optional<GlobalPos> optional = apparition.getBrain().getMemory(MemoryModuleType.HOME);
		return optional.map(GlobalPos::pos).orElse(null);
	}

	public static boolean isInHomeDimension(@NotNull Apparition apparition) {
		Optional<GlobalPos> optional = apparition.getBrain().getMemory(MemoryModuleType.HOME);
		return optional.filter((globalPos) -> globalPos.dimension() == apparition.level().dimension()).isPresent();
	}

	public static void rememberHome(@NotNull Apparition apparition, @NotNull BlockPos pos) {
		rememberHome(apparition, apparition.level(), pos);
	}

	public static void rememberHome(@NotNull Apparition apparition, @NotNull Level level, @NotNull BlockPos pos) {
		Brain<?> brain = apparition.getBrain();
		GlobalPos globalPos = GlobalPos.of(level.dimension(), pos);
		brain.setMemory(MemoryModuleType.HOME, globalPos);
	}

	@NotNull
	private static Optional<PositionTracker> getLookTarget(@NotNull LivingEntity apparition) {
		Brain<?> brain = apparition.getBrain();
		Optional<GlobalPos> home = brain.getMemory(MemoryModuleType.HOME);
		if (home.isPresent()) {
			GlobalPos globalPos = home.get();
			if (shouldGoTowardsHome(apparition, globalPos)) {
				return Optional.of(new BlockPosTracker(randomPosAround(globalPos.pos(), apparition.level())));
			}
		}

		return Optional.empty();
	}

	@NotNull
	private static BlockPos randomPosAround(@NotNull BlockPos pos, @NotNull Level level) {
		return pos.offset(level.random.nextIntBetweenInclusive(-7, 7), level.random.nextIntBetweenInclusive(-7, 7), level.random.nextIntBetweenInclusive(-7, 7));
	}

	private static void onTargetInvalid(ServerLevel level, @NotNull Apparition apparition, @NotNull LivingEntity target) {
		if (apparition.getTarget() == target) {
			apparition.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		}
	}

	public static void updateActivity(@NotNull Apparition apparition) {
		apparition.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	@NotNull
	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, @NotNull Apparition apparition) {
		Brain<Apparition> brain = apparition.getBrain();
		if (brain.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)) {
			return brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
		} else {
			return brain.getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
		}
	}

	public static void wasHurtBy(ServerLevel level, @NotNull Apparition apparition, LivingEntity target) {
		if (apparition.canTargetEntity(target, level)) {
			if (!Sensor.isEntityAttackableIgnoringLineOfSight(level, apparition, target)) {
				return;
			}
			if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(apparition, target, 4D)) {
				return;
			}

			setAngerTarget(level, apparition, target);
		}
	}

	public static void setAngerTarget(ServerLevel level, @NotNull Apparition apparition, LivingEntity target) {
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(level, apparition, target)) {
			return;
		}
		apparition.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		apparition.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
	}
}
