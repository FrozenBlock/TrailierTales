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

package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import java.util.ArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.client.renderer.fog.environment.HauntFogEnvironment;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.DarknessFogEnvironment;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(FogRenderer.class)
public class FogRendererMixin {

	@ModifyExpressionValue(
		method = "<clinit>",
		at = @At(
			value = "INVOKE",
			target = "Lcom/google/common/collect/Lists;newArrayList([Ljava/lang/Object;)Ljava/util/ArrayList;"
		)
	)
	private static ArrayList<FogEnvironment> trailierTales$injectHauntEnvironment(ArrayList<FogEnvironment> original) {
		final ArrayList<FogEnvironment> finalEnvironments = new ArrayList<>();

		for (FogEnvironment environment : original) {
			finalEnvironments.add(environment);
			if (environment instanceof DarknessFogEnvironment) finalEnvironments.add(new HauntFogEnvironment());
		}

		return finalEnvironments;
	}

}
