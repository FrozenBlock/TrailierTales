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

package net.frozenblock.trailiertales.mixin.client.brushable_block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.mod_compat.wilderwild.AbstractWWIntegration;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class BrushingCompleteSoundMixin {

	@ModifyExpressionValue(
		method = "levelEvent",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrushableBlock;getBrushCompletedSound()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent trailierTales$newBrushSounds(SoundEvent original) {
		AbstractWWIntegration wwIntegration = TTModIntegrations.WILDER_WILD_INTEGRATION.getIntegration();
		if (original == SoundEvents.BRUSH_GRAVEL_COMPLETED && wwIntegration.newGravelSounds()) {
			return TTSounds.BRUSH_GRAVEL_WW_COMPLETED;
		} else if (original == TTSounds.BRUSH_CLAY_COMPLETED && wwIntegration.newClaySounds()) {
			return TTSounds.BRUSH_CLAY_WW_COMPLETED;
		}
		return original;
	}

}
