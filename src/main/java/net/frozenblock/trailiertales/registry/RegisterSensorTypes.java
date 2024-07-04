package net.frozenblock.trailiertales.registry;

import java.util.function.Supplier;
import net.frozenblock.trailiertales.TrailierConstants;
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

	@NotNull
	private static <U extends Sensor<?>> SensorType<U> register(String key, Supplier<U> sensorSupplier) {
		return Registry.register(BuiltInRegistries.SENSOR_TYPE, TrailierConstants.id(key), new SensorType<>(sensorSupplier));
	}

}
