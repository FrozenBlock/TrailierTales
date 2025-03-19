/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.frozenblock.trailiertales.tag.TTStructureTags;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.StructureTagsProvider;
import org.jetbrains.annotations.NotNull;

public final class TTStructureTagProvider extends StructureTagsProvider {

	public TTStructureTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.tag(TTStructureTags.ON_CATACOMBS_EXPLORER_MAPS)
			.add(CatacombsGenerator.CATACOMBS_KEY);

	}

}
