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

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.frozenblock.trailiertales.block.entity.SurveyorBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.references.TTBlockEntityTypeIds;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;

public final class TTBlockEntityTypes {
	public static final BlockEntityType<CoffinBlockEntity> COFFIN = BlockEntityTypes.register(TTBlockEntityTypeIds.COFFIN,
		CoffinBlockEntity::new,
		TTBlocks.COFFIN
	);
	public static final BlockEntityType<SurveyorBlockEntity> SURVEYOR = BlockEntityTypes.register(TTBlockEntityTypeIds.SURVEYOR,
		SurveyorBlockEntity::new,
		TTBlocks.SURVEYOR
	);

	public static void init() {
		final FabricBlockEntityType brushableBlock = BlockEntityTypes.BRUSHABLE_BLOCK;
		brushableBlock.addValidBlock(TTBlocks.SUSPICIOUS_DIRT);
		brushableBlock.addValidBlock(TTBlocks.SUSPICIOUS_CLAY);
		brushableBlock.addValidBlock(TTBlocks.SUSPICIOUS_RED_SAND);
	}
}
