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

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceType;
import net.frozenblock.lib.wind.disturbance.WindDisturbances;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.wind.ApparitionWindDisturbance;

public final class TTWindDisturbances {
	public static final WindDisturbanceType<ApparitionWindDisturbance> APPARITION = WindDisturbanceType.register(
		TTConstants.id("apparition"),
		ApparitionWindDisturbance.CODEC,
		ApparitionWindDisturbance.STREAM_CODEC
	);

	public static void init() {
		ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
			WindDisturbances.addIf(level, entity, WindDisturbances.isOfClassAndDoesntHaveDisturbance(Apparition.class, APPARITION), () -> ApparitionWindDisturbance.INSTANCE);
		});
	}
}
