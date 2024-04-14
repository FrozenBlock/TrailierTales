package net.frozenblock.trailiertales.mixin.common.terrain_adjustment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.TrailierEnumValues;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JigsawStructure.class)
public class JigsawStructureMixin {

	@ModifyExpressionValue(
		method = "verifyRange",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/structure/structures/JigsawStructure;terrainAdaptation()Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;"
		)
	)
	private static TerrainAdjustment trailierTales$smallPlatformToBury(
		TerrainAdjustment original
	) {
		if (original == TrailierEnumValues.SMALL_PLATFORM) {
			return TerrainAdjustment.BURY;
		}
		return original;
	}

}
