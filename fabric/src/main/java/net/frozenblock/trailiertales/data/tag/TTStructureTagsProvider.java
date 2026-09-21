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
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class TTStructureTagsProvider extends FabricTagsProvider<Structure> {

	public TTStructureTagsProvider(FabricPackOutput output, CompletableFuture registries) {
		super(output, Registries.STRUCTURE, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.tag(TTStructureTags.ON_BURIED_CATACOMBS_MAPS)
			.add(CatacombsGenerator.CATACOMBS_KEY);

		this.tag(TTStructureTags.HAS_END_CITY_PROCESSORS)
			.add(BuiltinStructures.END_CITY)
			.addOptional(getKey("enderscape", "end_city"))
			.addOptional(getKey("nova_structures", "end_castle"))
			.addOptional(getKey("nova_structures", "end_lighthouse"))
			.addOptional(getKey("nova_structures", "end_ship"));
	}

	private TagKey<Structure> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	private TagKey<Structure> getTag(String namespace, String path) {
		return TagKey.create(this.registryKey, Identifier.fromNamespaceAndPath(namespace, path));
	}

	private ResourceKey<Structure> getKey(String namespace, String path) {
		return ResourceKey.create(this.registryKey, Identifier.fromNamespaceAndPath(namespace, path));
	}
}
