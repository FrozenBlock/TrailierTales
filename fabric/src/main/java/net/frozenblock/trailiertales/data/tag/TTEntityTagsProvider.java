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
import net.frozenblock.lib.tag.api.FrozenLibEntityTypeTags;
import net.frozenblock.trailiertales.references.TTEntityTypeIds;
import net.frozenblock.trailiertales.tag.TTEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityTypeIds;

public final class TTEntityTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider {

	public TTEntityTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.builder(TTEntityTypeTags.APPARITION_TARGETABLE)
			.add(EntityTypeIds.PLAYER);

		this.builder(TTEntityTypeTags.SURVEYOR_IGNORES)
			.add(TTEntityTypeIds.APPARITION);

		this.builder(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
			.add(TTEntityTypeIds.APPARITION);

		this.builder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
			.add(TTEntityTypeIds.APPARITION);

		this.builder(EntityTypeTags.WITHER_FRIENDS)
			.add(TTEntityTypeIds.APPARITION);

		this.builder(FrozenLibEntityTypeTags.GHOST_LIKE)
			.add(TTEntityTypeIds.APPARITION);
	}
}
