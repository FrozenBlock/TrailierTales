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

package net.frozenblock.trailiertales.mixin.client.brushable_block;

import net.frozenblock.trailiertales.block.impl.client.BrushableBlockAnimationState;
import net.frozenblock.trailiertales.block.impl.client.BrushableBlockEntityInterface;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@ClientOnly
@Mixin(BrushableBlockEntity.class)
public class BrushableBlockEntityMixin implements BrushableBlockEntityInterface {
	@Unique
	private BrushableBlockAnimationState trailierTales$animationState;

	@Unique
	@Override
	public BrushableBlockAnimationState trailierTales$getAnimationState() {
		if (this.trailierTales$animationState == null) this.trailierTales$animationState = BrushableBlockAnimationState.create();
		return this.trailierTales$animationState;
	}
}
