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

import java.util.Arrays;
import java.util.stream.Collectors;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.entity.SurveyorBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class TTBlockEntityTypes {

	public static final BlockEntityType<CoffinBlockEntity> COFFIN = register("coffin", CoffinBlockEntity::new, TTBlocks.COFFIN);
	public static final BlockEntityType<SurveyorBlockEntity> SURVEYOR = register("surveyor", SurveyorBlockEntity::new, TTBlocks.SURVEYOR);

	public static void register() {
		TTConstants.log("Registering BlockEntities for Trailier Tales.", TTConstants.UNSTABLE_LOGGING);

		final FabricBlockEntityType brushableBlock = BlockEntityType.BRUSHABLE_BLOCK;
		brushableBlock.addSupportedBlock(TTBlocks.SUSPICIOUS_DIRT);
		brushableBlock.addSupportedBlock(TTBlocks.SUSPICIOUS_CLAY);
		brushableBlock.addSupportedBlock(TTBlocks.SUSPICIOUS_RED_SAND);
	}

	private static <T extends BlockEntity> BlockEntityType<T> register(String path, BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, TTConstants.id(path), new BlockEntityType<>(factory, Arrays.stream(blocks).collect(Collectors.toSet())));
	}
}
