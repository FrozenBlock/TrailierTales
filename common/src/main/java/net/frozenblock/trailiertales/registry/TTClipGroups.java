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

import net.frozenblock.lib.block.api.clipgroup.ClipGroups;
import net.frozenblock.lib.block.impl.clipgroup.ClipGroup;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.references.TTBlockItemIds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class TTClipGroups {
	public static final ResourceKey<ClipGroup> ECTOPLASM_BLOCK = createKey("ectoplasm_block");

	public static void bootstrap(BootstrapContext<ClipGroup> context) {
		ClipGroups.register(context, ECTOPLASM_BLOCK, HolderSet.direct(context.lookup(Registries.BLOCK).getOrThrow(TTBlockItemIds.ECTOPLASM_BLOCK.block())));
	}

	private static ResourceKey<ClipGroup> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.CLIP_GROUP, TTConstants.id(name));
	}

	private TTClipGroups() {}
}
