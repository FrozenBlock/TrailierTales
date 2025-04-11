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

package net.frozenblock.trailiertales.mixin.client.brush;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.impl.client.ArmedEntityRenderStateInterface;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ArmedEntityRenderState.class)
public class ArmedEntityRenderStateMixin implements ArmedEntityRenderStateInterface {

	@Unique
	private ItemStack trailierTales$leftHandItemStack;

	@Unique
	private ItemStack trailierTales$rightHandItemStack;

	@Inject(method = "extractArmedEntityRenderState", at = @At("HEAD"))
	private static void trailierTales$extractArmedEntityRenderState(
		LivingEntity livingEntity, ArmedEntityRenderState armedEntityRenderState, ItemModelResolver itemModelResolver, CallbackInfo info
	) {
		if (armedEntityRenderState instanceof ArmedEntityRenderStateInterface armedEntityRenderStateInterface) {
			armedEntityRenderStateInterface.trailierTales$setLeftHandItemStack(livingEntity.getItemHeldByArm(HumanoidArm.LEFT));
			armedEntityRenderStateInterface.trailierTales$setRightHandItemStack(livingEntity.getItemHeldByArm(HumanoidArm.RIGHT));
		}
	}

	@Unique
	@Override
	public void trailierTales$setLeftHandItemStack(ItemStack itemStack) {
		this.trailierTales$leftHandItemStack = itemStack;
	}

	@Unique
	@Override
	public void trailierTales$setRightHandItemStack(ItemStack itemStack) {
		this.trailierTales$rightHandItemStack = itemStack;
	}

	@Unique
	@Override
	public ItemStack trailierTales$getLeftHandItemStack() {
		return this.trailierTales$leftHandItemStack;
	}

	@Unique
	@Override
	public ItemStack trailierTales$getRightHandItemStack() {
		return this.trailierTales$rightHandItemStack;
	}
}
