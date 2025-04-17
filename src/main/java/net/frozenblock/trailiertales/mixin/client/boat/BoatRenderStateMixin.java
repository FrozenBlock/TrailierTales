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

package net.frozenblock.trailiertales.mixin.client.boat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.impl.client.BoatRenderStateInterface;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(BoatRenderState.class)
public class BoatRenderStateMixin implements BoatRenderStateInterface {

	@Unique
	private float trailierTales$walkAnimationPos;

	@Unique
	private float trailierTales$walkAnimationSpeed;

	@Unique
	private ItemStack trailierTales$banner;

	@Unique
	@Override
	public void trailierTales$setWalkAnimationPos(float pos) {
		this.trailierTales$walkAnimationPos = pos;
	}

	@Unique
	@Override
	public float trailierTales$getWalkAnimationPos() {
		return this.trailierTales$walkAnimationPos;
	}

	@Unique
	@Override
	public void trailierTales$setWalkAnimationSpeed(float speed) {
		this.trailierTales$walkAnimationSpeed = speed;
	}

	@Unique
	@Override
	public float trailierTales$getWalkAnimationSpeed() {
		return this.trailierTales$walkAnimationSpeed;
	}

	@Unique
	@Override
	public void trailierTales$setBanner(ItemStack stack) {
		this.trailierTales$banner = stack;
	}

	@Unique
	@Override
	public ItemStack trailierTales$getBanner() {
		return this.trailierTales$banner;
	}
}
