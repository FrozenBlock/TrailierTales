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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.levelgen.structure.RuinsPieces;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public final class TTStructurePieceTypes {
	private static final DeferredRegister<StructurePieceType> REGISTER = DeferredRegister.create(Registries.STRUCTURE_PIECE, TTConstants.MOD_ID);

	public static final DeferredHolder<StructurePieceType, StructurePieceType> RUIN = setFullContextPieceId(RuinsPieces.RuinPiece::create, "ruin");

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static DeferredHolder<StructurePieceType, StructurePieceType> setFullContextPieceId(StructurePieceType type, String name) {
		return REGISTER.register(name, () -> type);
	}

	private TTStructurePieceTypes() {}
}
