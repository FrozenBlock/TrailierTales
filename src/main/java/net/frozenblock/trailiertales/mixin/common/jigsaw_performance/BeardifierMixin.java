package net.frozenblock.trailiertales.mixin.common.jigsaw_performance;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(Beardifier.class)
public class BeardifierMixin {

	@Inject(
		method = "method_42694",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/structure/Structure;terrainAdaptation()Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;",
			shift = At.Shift.AFTER
		)
	)
	private static void trailierTales$temp(
		ChunkPos chunkPos, ObjectList objectList, int i, int j, ObjectList objectList2, StructureStart structureStart, CallbackInfo info,
		@Share("trailierTales$validPieceList")LocalRef<ArrayList<StructurePiece>> validPieceList
	) {
		ArrayList<StructurePiece> validPieces = new ArrayList<>(structureStart.getPieces());
		validPieces.removeIf(structurePiece -> validPieces.stream()
			.anyMatch(
				checkingPiece -> {
					AtomicInteger insideCorners = new AtomicInteger();
					BoundingBox comparedBox = checkingPiece.getBoundingBox();
					structurePiece.getBoundingBox().forAllCorners(
						corner -> {
							if (comparedBox.isInside(corner)) {
								insideCorners.set(insideCorners.get() + 1);
							}
						});
					return insideCorners.get() >= 8;
				})
		);
		validPieceList.set(validPieces);
	}

	@WrapOperation(
		method = "method_42694",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/structure/StructureStart;getPieces()Ljava/util/List;"
		)
	)
	private static List<StructurePiece> trailierTales$modifyPieceCall(
		StructureStart instance, Operation<List<StructurePiece>> original, @Share("trailierTales$validPieceList")LocalRef<ArrayList<StructurePiece>> validPieceList
	) {
		return validPieceList.get();
	}

}
