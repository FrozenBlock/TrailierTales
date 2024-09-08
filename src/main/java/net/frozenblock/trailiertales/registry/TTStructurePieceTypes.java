package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.worldgen.structure.RuinsPieces;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.jetbrains.annotations.NotNull;

public class TTStructurePieceTypes {
	public static final StructurePieceType RUIN = setTemplatePieceId(RuinsPieces.RuinPiece::create, "ruin");

	public static void init() {
	}

	private static @NotNull StructurePieceType setFullContextPieceId(StructurePieceType type, String id) {
		return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, TTConstants.id(id), type);
	}

	private static @NotNull StructurePieceType setPieceId(StructurePieceType.ContextlessType simplePieceType, String id) {
		return setFullContextPieceId(simplePieceType, id);
	}

	private static @NotNull StructurePieceType setTemplatePieceId(StructurePieceType.StructureTemplateType managerAwarePieceType, String id) {
		return setFullContextPieceId(managerAwarePieceType, id);
	}
}
