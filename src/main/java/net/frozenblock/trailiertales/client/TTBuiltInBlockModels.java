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

package net.frozenblock.trailiertales.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.renderer.block.BuiltInBlockModelRegistry;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.client.renderer.MultiblockCoffinResources;
import net.frozenblock.trailiertales.client.renderer.blockentity.CoffinRenderer;
import net.frozenblock.trailiertales.client.renderer.special.CoffinSpecialRenderer;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public final class TTBuiltInBlockModels {

	public static void init() {
		BuiltInBlockModelRegistry.REGISTER.register(builder -> {
			builder.put(createCoffin(CoffinSpecialRenderer.INACTIVE), TTBlocks.COFFIN);
			builder.put(BuiltInBlockModels::createFlowerBedModel, TTBlocks.LITHOPS);
		});
	}

	private static BuiltInBlockModels.SpecialModelFactory createCoffin(MultiblockCoffinResources<Identifier> textures) {
		return BuiltInBlockModels.specialModelWithPropertyDispatch(
			CoffinBlock.FACING,
			CoffinBlock.PART,
			(facing, part) -> createCoffin(textures.select(part), part, facing)
		);
	}

	private static BlockModel.Unbaked createCoffin(Identifier texture, CoffinPart part, Direction facing) {
		return BuiltInBlockModels.special(new CoffinSpecialRenderer.Unbaked(texture, part), CoffinRenderer.modelTransform(facing));
	}
}
