package net.frozenblock.trailiertales.registry;

import java.util.function.Supplier;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAttackablesSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionPossessablesSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionSpecificSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.NearestItemNoLineOfSightSensor;
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
	public static final SensorType<NearestItemNoLineOfSightSensor> NEAREST_ITEM_NO_LINE_OF_SIGHT = register("nearest_item_no_line_of_sight", NearestItemNoLineOfSightSensor::new);
	public static final SensorType<ApparitionPossessablesSensor> APPARITION_POSSESSABLES_SENSOR = register("possessables_sensor", ApparitionPossessablesSensor::new);

	@NotNull
	private static <U extends Sensor<?>> SensorType<U> register(String key, Supplier<U> sensorSupplier) {
		return Registry.register(BuiltInRegistries.SENSOR_TYPE, TrailierConstants.id(key), new SensorType<>(sensorSupplier));
	}

}
