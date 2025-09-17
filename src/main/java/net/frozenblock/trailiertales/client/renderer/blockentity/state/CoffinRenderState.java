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

package net.frozenblock.trailiertales.client.renderer.blockentity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

@Environment(EnvType.CLIENT)
public class CoffinRenderState extends BlockEntityRenderState {
	public CoffinPart part;
	public CoffinSpawnerState spawnerState;
	public boolean ominous;
	public Direction direction;
	public float openProgress;
	public float wobbleProgress;
}
