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

package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import net.minecraft.Util;

public class RuinsPieceHandler {
	private static final boolean LOG_RUINS_PIECE_LOADING = false;
	private static final Map<String, Integer> DIRECTORY_TO_PIECE_OFFSET_MAP = ImmutableMap.<String, Integer>builder()
		.put("buried", 0)
		.put("one_from_top", 1)
		.put("two_from_top", 2)
		.put("three_from_top", 3)
		.put("four_from_top", 4)
		.put("five_from_top", 5)
		.put("six_from_top", 6)
		.put("seven_from_top", 7)
		.put("eight_from_top", 8)
		.put("nine_from_top", 9)
		.put("ten_from_top", 10)
		.build();

	private final List<ResourceLocation> ruinsPieces = new ArrayList<>();
	private final RuinsStructure.Type type;

	public RuinsPieceHandler(RuinsStructure.Type type) {
		this.type = type;
	}

	public ResourceLocation getRandomPiece(@NotNull RandomSource randomSource) {
		return Util.getRandom(this.ruinsPieces, randomSource);
	}

	public void onDataReload(@NotNull ResourceManager resourceManager) {
		this.ruinsPieces.clear();
		this.ruinsPieces.addAll(this.getLoadedPieces(resourceManager));
	}

	private @NotNull List<ResourceLocation> getLoadedPieces(@NotNull ResourceManager resourceManager) {
		Set<ResourceLocation> foundPieces = resourceManager.listResources(
			"structure/ruins/" + this.type.getName(),
			resourceLocation -> resourceLocation.getPath().endsWith(".nbt") && resourceLocation.getNamespace().equals(TTConstants.MOD_ID)
		).keySet();

		ArrayList<ResourceLocation> convertedLocations = new ArrayList<>();
		foundPieces.forEach(resourceLocation -> {
			String newPath = resourceLocation.getPath();
			newPath = newPath.replace(".nbt", "");
			newPath = newPath.replace("structure/", "");
			convertedLocations.add(ResourceLocation.tryBuild(resourceLocation.getNamespace(), newPath));
		});

		if (LOG_RUINS_PIECE_LOADING) convertedLocations.forEach(convertedLocation -> TTConstants.log(convertedLocation.toString(), true));

		return convertedLocations;
	}

	public static OptionalInt getPieceOffset(RuinsPieces.@NotNull RuinPiece piece) {
		ResourceLocation pieceLocation = piece.makeTemplateLocation();
		String piecePath = pieceLocation.getPath();

		for (Map.Entry<String, Integer> offsetMapEntry : DIRECTORY_TO_PIECE_OFFSET_MAP.entrySet()) {
			String pieceDirectory = offsetMapEntry.getKey();
			if (piecePath.contains(pieceDirectory + "/")) {
				return OptionalInt.of(offsetMapEntry.getValue());
			}
		}

		return OptionalInt.empty();
	}

}
