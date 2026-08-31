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

import net.frozenblock.lib.entity.api.attribute.DefaultAttributeRegistry;
import net.frozenblock.lib.platform.api.registry.DeferredEntityType;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.ThrownItemProjectile;
import net.frozenblock.trailiertales.references.TTEntityTypeIds;
import net.minecraft.world.entity.MobCategory;

public final class TTEntityTypes {
	private static final DeferredRegister.Entities REGISTER = DeferredRegister.createEntities(
		TTConstants.MOD_ID
	);

	public static final DeferredEntityType<Apparition> APPARITION = REGISTER.register(TTEntityTypeIds.APPARITION,
		Apparition::new,
		MobCategory.MISC,
		builder -> builder
			.sized(0.98F, 0.98F)
			.eyeHeight(0.98F * 0.5F)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, Apparition.createApparitionAttributes());
		}
	);

	public static final DeferredEntityType<ThrownItemProjectile> THROWN_ITEM_PROJECTILE = REGISTER.register(TTEntityTypeIds.THROWN_ITEM_PROJECTILE,
		ThrownItemProjectile::new,
		MobCategory.MISC,
		builder -> builder
			.sized(0.25F, 0.25F)
			.clientTrackingRange(64)
			.updateInterval(10)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	static {
		REGISTER.register();
	}

	public static void init() {}
}
