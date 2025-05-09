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

package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class TTBiomeTags {
	public static final TagKey<Biome> HAS_BADLANDS_RUINS = bind("has_structure/badlands_ruins");
	public static final TagKey<Biome> HAS_CATACOMBS = bind("has_structure/catacombs");
	public static final TagKey<Biome> HAS_DESERT_RUINS = bind("has_structure/desert_ruins");
	public static final TagKey<Biome> HAS_JUNGLE_RUINS = bind("has_structure/jungle_ruins");
	public static final TagKey<Biome> HAS_SAVANNA_RUINS = bind("has_structure/savanna_ruins");
	public static final TagKey<Biome> HAS_DEEPSLATE_RUINS = bind("has_structure/deepslate_ruins");
	public static final TagKey<Biome> HAS_RUINS = bind("has_structure/ruins");
	public static final TagKey<Biome> HAS_SNOWY_RUINS = bind("has_structure/snowy_ruins");

	@NotNull
	private static TagKey<Biome> bind(@NotNull String path) {
		return TagKey.create(Registries.BIOME, TTConstants.id(path));
	}

}
