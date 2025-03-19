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
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.tag.TTEntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public final class TTEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

	public TTEntityTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(TTEntityTags.APPARITION_TARGETABLE)
			.add(EntityType.PLAYER);

		this.getOrCreateTagBuilder(TTEntityTags.SURVEYOR_IGNORES)
			.add(TTEntityTypes.APPARITION);

		this.getOrCreateTagBuilder(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
			.add(TTEntityTypes.APPARITION);

		this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
			.add(TTEntityTypes.APPARITION);

		this.getOrCreateTagBuilder(EntityTypeTags.WITHER_FRIENDS)
			.add(TTEntityTypes.APPARITION);
	}
}
