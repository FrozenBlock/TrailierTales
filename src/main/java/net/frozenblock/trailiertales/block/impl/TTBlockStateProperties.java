/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.block.impl;

import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class TTBlockStateProperties {
	public static final EnumProperty<CoffinPart> COFFIN_PART = EnumProperty.create("part", CoffinPart.class);
	public static final EnumProperty<CoffinSpawnerState> COFFIN_STATE = EnumProperty.create("state", CoffinSpawnerState.class);
	public static final BooleanProperty CAN_PLACE_ITEM = BooleanProperty.create("can_place_item");
}
