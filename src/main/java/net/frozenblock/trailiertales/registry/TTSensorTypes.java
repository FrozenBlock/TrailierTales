/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.registry;

import java.util.function.Supplier;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAidablesSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAttackablesSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionNearestItemSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionPlayerSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionSpecificSensor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;

public final class TTSensorTypes {

	private TTSensorTypes() {
		throw new UnsupportedOperationException("RegisterSensorTypes contains only static declarations.");
	}

	public static void register() {
		TTConstants.log("Registering SensorTypes for Trailier Tales", TTConstants.UNSTABLE_LOGGING);
	}

	public static final SensorType<ApparitionSpecificSensor> APPARITION_SPECIFIC_SENSOR = register("apparition_specific_sensor", ApparitionSpecificSensor::new);
	public static final SensorType<ApparitionAttackablesSensor> APPARITION_ATTACKABLES_SENSOR = register("apparition_attackables_sensor", ApparitionAttackablesSensor::new);
	public static final SensorType<ApparitionNearestItemSensor> APPARITION_NEAREST_ITEM_SENSOR = register("apparition_nearest_item_sensor", ApparitionNearestItemSensor::new);
	public static final SensorType<ApparitionPlayerSensor> APPARITION_PLAYER_SENSOR = register("apparition_player_sensor", ApparitionPlayerSensor::new);
	public static final SensorType<ApparitionAidablesSensor> APPARITION_AIDABLES_SENSOR = register("apparition_aidables_sensor", ApparitionAidablesSensor::new);

	private static <U extends Sensor<?>> SensorType<U> register(String key, Supplier<U> sensorSupplier) {
		return Registry.register(BuiltInRegistries.SENSOR_TYPE, TTConstants.id(key), new SensorType<>(sensorSupplier));
	}

}
