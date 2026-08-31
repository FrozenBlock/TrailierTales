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

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.entity.SurveyorBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.references.TTBlockEntityTypeIds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;

public final class TTBlockEntityTypes {
	private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(
		Registries.BLOCK_ENTITY_TYPE,
		TTConstants.MOD_ID
	);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CoffinBlockEntity>> COFFIN = register(TTBlockEntityTypeIds.COFFIN,
		CoffinBlockEntity::new,
		TTBlocks.COFFIN
	);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SurveyorBlockEntity>> SURVEYOR = register(TTBlockEntityTypeIds.SURVEYOR,
		SurveyorBlockEntity::new,
		TTBlocks.SURVEYOR
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	public static void registerValidBlocks() {
		var brushableBlock = BlockEntityTypes.BRUSHABLE_BLOCK;
		brushableBlock.frozenLib$addValidBlock(TTBlocks.SUSPICIOUS_DIRT.get());
		brushableBlock.frozenLib$addValidBlock(TTBlocks.SUSPICIOUS_CLAY.get());
		brushableBlock.frozenLib$addValidBlock(TTBlocks.SUSPICIOUS_RED_SAND.get());
	}

	@SafeVarargs
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(
		ResourceKey<BlockEntityType<?>> id,
		BlockEntityType.BlockEntitySupplier<T> builder,
		Supplier<? extends Block>... blocks
	) {
		return REGISTER.register(id, () -> new BlockEntityType<>(builder, Arrays.stream(blocks).map(Supplier::get).collect(Collectors.toSet())));
	}

	private TTBlockEntityTypes() {}
}
