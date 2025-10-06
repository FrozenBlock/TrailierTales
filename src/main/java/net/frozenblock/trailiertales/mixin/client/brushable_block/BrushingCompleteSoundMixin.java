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

package net.frozenblock.trailiertales.mixin.client.brushable_block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.mod_compat.wilderwild.AbstractWWIntegration;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LevelEventHandler.class)
public class BrushingCompleteSoundMixin {

	@ModifyExpressionValue(
		method = "levelEvent",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrushableBlock;getBrushCompletedSound()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent trailierTales$newBrushSounds(SoundEvent original) {
		final AbstractWWIntegration wwIntegration = TTModIntegrations.WILDER_WILD_INTEGRATION.getIntegration();
		if (original == SoundEvents.BRUSH_GRAVEL_COMPLETED && wwIntegration.newGravelSounds()) return TTSounds.BRUSH_GRAVEL_WW_COMPLETED;
		if (original == TTSounds.BRUSH_CLAY_COMPLETED && wwIntegration.newClaySounds()) return TTSounds.BRUSH_CLAY_WW_COMPLETED;
		return original;
	}

}
