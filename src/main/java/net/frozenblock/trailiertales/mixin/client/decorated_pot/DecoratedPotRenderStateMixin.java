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

package net.frozenblock.trailiertales.mixin.client.decorated_pot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.impl.client.DecoratedPotRenderStateInterface;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(DecoratedPotRenderState.class)
public class DecoratedPotRenderStateMixin implements DecoratedPotRenderStateInterface {

	@Unique
	private boolean trailierTales$isWobbleFlipped = false;

	@Unique
	@Override
	public void trailierTales$setWobbleFlipped(boolean flipped) {
		this.trailierTales$isWobbleFlipped = flipped;
	}

	@Unique
	@Override
	public boolean trailierTales$isWobbleFlipped() {
		return this.trailierTales$isWobbleFlipped;
	}
}
