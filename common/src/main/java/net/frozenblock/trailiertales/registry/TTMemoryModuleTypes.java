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

import java.util.List;
import java.util.UUID;
import net.frozenblock.lib.platform.api.registry.DeferredMemoryModuleType;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;

public final class TTMemoryModuleTypes {
	private static final DeferredRegister.MemoryModuleTypes REGISTER = DeferredRegister.createMemoryModuleTypes(TTConstants.MOD_ID);

	public static final DeferredMemoryModuleType<List<Apparition>> NEARBY_APPARITIONS = REGISTER.register("nearby_apparitions");
	public static final DeferredMemoryModuleType<Unit> AID_COOLDOWN = REGISTER.register("aid_cooldown");
	public static final DeferredMemoryModuleType<Integer> AIDING_TIME = REGISTER.register("aiding_time");
	public static final DeferredMemoryModuleType<List<LivingEntity>> NEARBY_AIDABLES = REGISTER.register("nearby_aidables");
	public static final DeferredMemoryModuleType<LivingEntity> NEAREST_AIDABLE = REGISTER.register("nearest_aidable");
	public static final DeferredMemoryModuleType<Integer> SEE_TIME = REGISTER.register("see_time");
	public static final DeferredMemoryModuleType<Unit> STRAFING_CLOCKWISE = REGISTER.register("strafing_clockwise");
	public static final DeferredMemoryModuleType<Unit> STRAFING_BACKWARDS = REGISTER.register("strafing_backwards");
	public static final DeferredMemoryModuleType<Integer> STRAFING_TIME = REGISTER.register("strafing_time");
	public static final DeferredMemoryModuleType<Integer> CHARGING_TICKS = REGISTER.register("charging_ticks");
	public static final DeferredMemoryModuleType<Integer> HAUNTING_TICKS = REGISTER.register("haunting_ticks");
	public static final DeferredMemoryModuleType<List<UUID>> AIDING_ENTITIES = REGISTER.register("aiding_entities");

	static {
		REGISTER.register();
	}

	public static void init() {}

	private TTMemoryModuleTypes() {}
}
