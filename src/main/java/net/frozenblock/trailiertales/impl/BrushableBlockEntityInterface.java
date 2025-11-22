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

package net.frozenblock.trailiertales.impl;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public interface BrushableBlockEntityInterface {
	void trailierTales$tick();
	boolean trailierTales$setItem(ItemStack stack);
	boolean trailierTales$hasCustomItem();
	float trailierTales$getXOffset(float partialTicks);
	float trailierTales$getYOffset(float partialTicks);
	float trailierTales$getZOffset(float partialTicks);
	float trailierTales$getRotation(float partialTicks);
	float trailierTales$getItemScale(float partialTicks);
	Direction trailierTales$getHitDirection();
}
