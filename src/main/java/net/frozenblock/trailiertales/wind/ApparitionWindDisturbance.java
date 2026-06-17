/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.trailiertales.wind;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.wind.disturbance.EntityWindDisturbance;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceResult;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceType;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.TTWindDisturbances;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ApparitionWindDisturbance extends EntityWindDisturbance<Apparition> {
	public static final ApparitionWindDisturbance INSTANCE = new ApparitionWindDisturbance();
	public static final MapCodec<ApparitionWindDisturbance> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, ApparitionWindDisturbance> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	private static final double AREA_WIDTH = 12D;
	private static final double AREA_HEIGHT = 12D;
	private static final double AREA_Y_OFFSET = 0D;
	private static final double WIND_RANGE = 6D;

	@Override
	public AABB area(Apparition source, Level level, Vec3 origin, Vec3 target, double scale) {
		return AABB.ofSize(
			origin,
			AREA_WIDTH * scale,
			AREA_HEIGHT * scale,
			AREA_WIDTH * scale
		).move(
			0D,
			AREA_Y_OFFSET * scale,
			0D
		);
	}

	@Override
	public WindDisturbanceResult get(Apparition source, Level level, Vec3 origin, AABB area, Vec3 target, double scale) {
		final double scaledRange = WIND_RANGE * scale;
		final double distance = origin.distanceTo(target);
		if (distance > scaledRange) return WindDisturbanceResult.PASS;

		final Vec3 differenceInPoses = origin.subtract(target);
		final double scaledDistance = (scaledRange - distance) / scaledRange;
		final double strengthFromDistance = Mth.clamp((scaledRange - distance) / (4.5D * scale), 0D, 1D);
		final double x = scaledDistance * differenceInPoses.x * 0.3D;
		final double y = scaledDistance * differenceInPoses.y * 0.3D;
		final double z = scaledDistance * differenceInPoses.z * 0.3D;
		final Vec3 windVec = new Vec3(x, y, z);
		return WindDisturbanceResult.success(
			strengthFromDistance * scale,
			(scaledRange - distance) * scale,
			windVec);
	}

	@Override
	public WindDisturbanceType<?> type() {
		return TTWindDisturbances.APPARITION;
	}
}
