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

package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.frozenblock.lib.datagen.api.FrozenBiomeTagProvider;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.tag.TTBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public final class TTBiomeTagProvider extends FrozenBiomeTagProvider {

	public TTBiomeTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.builder(TTBiomeTags.HAS_BADLANDS_RUINS)
			.add(Biomes.BADLANDS)
			.add(Biomes.WOODED_BADLANDS)
			.add(Biomes.ERODED_BADLANDS);

		this.builder(TTBiomeTags.HAS_CATACOMBS)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(BiomeTags.IS_MOUNTAIN)
			.addOptionalTag(BiomeTags.IS_HILL)
			.addOptionalTag(BiomeTags.IS_TAIGA)
			.addOptionalTag(BiomeTags.IS_JUNGLE)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptionalTag(ConventionalBiomeTags.IS_DESERT)
			.addOptionalTag(ConventionalBiomeTags.IS_SWAMP)
			.addOptionalTag(ConventionalBiomeTags.IS_SAVANNA)
			.addOptionalTag(ConventionalBiomeTags.IS_PLAINS)
			.addOptionalTag(ConventionalBiomeTags.IS_SNOWY_PLAINS)
			.add(Biomes.DRIPSTONE_CAVES)
			.add(Biomes.LUSH_CAVES)
			.addOptional(TTModIntegrations.WILDER_WILD_INTEGRATION.getIntegration().getBiomeKey("magmatic_caves"))
			.addOptional(TTModIntegrations.WILDER_WILD_INTEGRATION.getIntegration().getBiomeKey("frozen_caves"));

		this.builder(TTBiomeTags.HAS_DESERT_RUINS)
			.addOptionalTag(ConventionalBiomeTags.IS_DESERT);

		this.builder(TTBiomeTags.HAS_SAVANNA_RUINS)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU);

		this.builder(TTBiomeTags.HAS_JUNGLE_RUINS)
			.addOptionalTag(ConventionalBiomeTags.IS_JUNGLE);

		this.builder(TTBiomeTags.HAS_DEEPSLATE_RUINS)
			.add(Biomes.DEEP_DARK);

		this.builder(TTBiomeTags.HAS_RUINS)
			.addOptionalTag(ConventionalBiomeTags.IS_FOREST)
			.addOptionalTag(ConventionalBiomeTags.IS_BIRCH_FOREST);

		this.builder(TTBiomeTags.HAS_SNOWY_RUINS)
			.addOptionalTag(ConventionalBiomeTags.IS_SNOWY_PLAINS)
			.add(Biomes.SNOWY_TAIGA);
	}

	@NotNull
	private TagKey<Biome> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

}
