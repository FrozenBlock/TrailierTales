/*
 * Copyright 2025-2026 FrozenBlock
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

import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.api.registry.DeferredSensorType;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAidablesSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAttackablesSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionNearestItemSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionPlayerSensor;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionSpecificSensor;

public final class TTSensorTypes {
	private static final DeferredRegister.SensorTypes REGISTER = DeferredRegister.createSensorTypes(TTConstants.MOD_ID);

	public static final DeferredSensorType<ApparitionSpecificSensor> APPARITION_SPECIFIC_SENSOR = REGISTER.registerSensorType("apparition_specific_sensor", ApparitionSpecificSensor::new);
	public static final DeferredSensorType<ApparitionAttackablesSensor> APPARITION_ATTACKABLES_SENSOR = REGISTER.registerSensorType("apparition_attackables_sensor", ApparitionAttackablesSensor::new);
	public static final DeferredSensorType<ApparitionNearestItemSensor> APPARITION_NEAREST_ITEM_SENSOR = REGISTER.registerSensorType("apparition_nearest_item_sensor", ApparitionNearestItemSensor::new);
	public static final DeferredSensorType<ApparitionPlayerSensor> APPARITION_PLAYER_SENSOR = REGISTER.registerSensorType("apparition_player_sensor", ApparitionPlayerSensor::new);
	public static final DeferredSensorType<ApparitionAidablesSensor> APPARITION_AIDABLES_SENSOR = REGISTER.registerSensorType("apparition_aidables_sensor", ApparitionAidablesSensor::new);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private TTSensorTypes() {}
}
