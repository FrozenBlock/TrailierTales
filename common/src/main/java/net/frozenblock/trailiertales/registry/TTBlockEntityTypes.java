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

import net.frozenblock.lib.block.api.blockentity.BlockEntityTypeExtension;
import net.frozenblock.lib.platform.api.registry.DeferredBlockEntityType;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.entity.SurveyorBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.references.TTBlockEntityTypeIds;
import net.minecraft.world.level.block.entity.BlockEntityTypes;

public final class TTBlockEntityTypes {
	private static final DeferredRegister.BlockEntities REGISTER = DeferredRegister.createBlockEntities(TTConstants.MOD_ID);

	public static final DeferredBlockEntityType<CoffinBlockEntity> COFFIN = REGISTER.register(TTBlockEntityTypeIds.COFFIN,
		CoffinBlockEntity::new,
		TTBlocks.COFFIN,
		BlockEntityTypeExtension::frozenLib$setOpOnlyCustomData
	);
	public static final DeferredBlockEntityType<SurveyorBlockEntity> SURVEYOR = REGISTER.register(TTBlockEntityTypeIds.SURVEYOR,
		SurveyorBlockEntity::new,
		TTBlocks.SURVEYOR
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	public static void setup() {
		BlockEntityTypes.BRUSHABLE_BLOCK.frozenLib$addValidBlock(TTBlocks.SUSPICIOUS_DIRT, TTBlocks.SUSPICIOUS_CLAY, TTBlocks.SUSPICIOUS_RED_SAND);
	}

	private TTBlockEntityTypes() {}
}
