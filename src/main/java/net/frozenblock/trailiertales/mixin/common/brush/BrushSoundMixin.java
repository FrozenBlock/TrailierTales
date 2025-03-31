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

package net.frozenblock.trailiertales.mixin.common.brush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.mod_compat.wilderwild.AbstractWWIntegration;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BrushItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrushItem.class)
public class BrushSoundMixin {

	@ModifyExpressionValue(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrushableBlock;getBrushSound()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent trailierTales$newBrushSounds(SoundEvent original) {
		AbstractWWIntegration wwIntegration = TTModIntegrations.WILDER_WILD_INTEGRATION.getIntegration();
		if (original == SoundEvents.BRUSH_GRAVEL && wwIntegration.newGravelSounds()) {
			return TTSounds.BRUSH_GRAVEL_WW;
		} else if (original == TTSounds.BRUSH_CLAY && wwIntegration.newClaySounds()) {
			return TTSounds.BRUSH_CLAY_WW;
		}
		return original;
	}

}
