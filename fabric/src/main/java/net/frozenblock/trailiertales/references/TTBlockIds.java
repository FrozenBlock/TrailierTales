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

package net.frozenblock.trailiertales.references;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public final class TTBlockIds {
	// PLANTS
	public static final ResourceKey<Block> POTTED_CYAN_ROSE = create("potted_cyan_rose");
	public static final ResourceKey<Block> POTTED_LITHOPS = create("potted_lithops");

	private static ResourceKey<Block> create(String name) {
		return ResourceKey.create(Registries.BLOCK, TTConstants.id(name));
	}
}
