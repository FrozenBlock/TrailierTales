/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class TTBlockTags {
	public static final TagKey<Block> SOUND_UNPOLISHED_BRICKS = bind("sound/unpolished_bricks");
	public static final TagKey<Block> SOUND_POLISHED_BRICKS = bind("sound/polished_bricks");
	public static final TagKey<Block> SOUND_POLISHED_CALCITE = bind("sound/polished_calcite");
	public static final TagKey<Block> SOUND_CALCITE_BRICKS = bind("sound/calcite_bricks");
	public static final TagKey<Block> SOUND_POLISHED = bind("sound/polished");
	public static final TagKey<Block> SOUND_POLISHED_DEEPSLATE = bind("sound/polished_deepslate");
	public static final TagKey<Block> SOUND_POLISHED_TUFF = bind("sound/polished_tuff");
	public static final TagKey<Block> SOUND_POLISHED_BASALT = bind("sound/polished_basalt");
	public static final TagKey<Block> SOUND_POLISHED_RESIN = bind("sound/polished_resin");

	public static final TagKey<Block> COFFIN_UNSPAWNABLE_ON = bind("coffin_unspawnable_on");
	public static final TagKey<Block> CAMEL_SPAWNABLE_ON = bind("camel_spawnable_on");

	public static final TagKey<Block> SURVEYOR_CAN_SEE_THROUGH = bind("surveyor_can_see_through");
	public static final TagKey<Block> SURVEYOR_CANNOT_SEE_THROUGH = bind("surveyor_cannot_see_through");

	@NotNull
	private static TagKey<Block> bind(@NotNull String path) {
		return TagKey.create(Registries.BLOCK, TTConstants.id(path));
	}

}
