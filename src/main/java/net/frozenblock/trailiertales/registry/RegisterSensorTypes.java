package net.frozenblock.trailiertales.registry;

import java.util.function.Supplier;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAidablesSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAttackablesSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionPlayerSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionSpecificSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionNearestItemSensor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.jetbrains.annotations.NotNull;

public final class RegisterSensorTypes {
	private RegisterSensorTypes() {
		throw new UnsupportedOperationException("RegisterSensorTypes contains only static declarations.");
	}

	public static void register() {
		TrailierConstants.log("Registering SensorTypes for Trailier Tales", TrailierConstants.UNSTABLE_LOGGING);
	}

	public static final SensorType<ApparitionSpecificSensor> APPARITION_SPECIFIC_SENSOR = register("apparition_specific_sensor", ApparitionSpecificSensor::new);
	public static final SensorType<ApparitionAttackablesSensor> APPARITION_ATTACKABLES_SENSOR = register("apparition_attackables_sensor", ApparitionAttackablesSensor::new);
	public static final SensorType<ApparitionNearestItemSensor> APPARITION_NEAREST_ITEM_SENSOR = register("apparition_nearest_item_sensor", ApparitionNearestItemSensor::new);
	public static final SensorType<ApparitionPlayerSensor> APPARITION_PLAYER_SENSOR = register("apparition_player_sensor", ApparitionPlayerSensor::new);
	public static final SensorType<ApparitionAidablesSensor> APPARITION_AIDABLES_SENSOR = register("apparition_aidables_sensor", ApparitionAidablesSensor::new);

	@NotNull
	private static <U extends Sensor<?>> SensorType<U> register(String key, Supplier<U> sensorSupplier) {
		return Registry.register(BuiltInRegistries.SENSOR_TYPE, TrailierConstants.id(key), new SensorType<>(sensorSupplier));
	}

}
