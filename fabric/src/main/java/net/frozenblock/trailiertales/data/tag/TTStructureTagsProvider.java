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

package net.frozenblock.trailiertales.data.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.frozenblock.trailiertales.data.worldgen.structure.CatacombsGenerator;
import net.frozenblock.trailiertales.tag.TTStructureTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class TTStructureTagsProvider extends FabricTagsProvider<Structure> {

	public TTStructureTagsProvider(FabricPackOutput output, CompletableFuture registries) {
		super(output, Registries.STRUCTURE, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.tag(TTStructureTags.ON_BURIED_CATACOMBS_MAPS)
			.add(CatacombsGenerator.CATACOMBS_KEY);

	}

}
