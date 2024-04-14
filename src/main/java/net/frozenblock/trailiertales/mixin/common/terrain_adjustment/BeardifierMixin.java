package net.frozenblock.trailiertales.mixin.common.terrain_adjustment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.trailiertales.TrailierEnumValues;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Beardifier.class)
public class BeardifierMixin {

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lit/unimi/dsi/fastutil/objects/ObjectListIterator;next()Ljava/lang/Object;",
			ordinal = 0
		)
	)
	public Object trailierTales$captureTerrainAdjustment(
		Object original,
		@Share("trailierTales$terrainAdjustment") LocalRef<TerrainAdjustment> terrainAdjustment
	) {
		if (original instanceof Beardifier.Rigid rigid) {
			terrainAdjustment.set(rigid.terrainAdjustment());
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/structure/BoundingBox;minY()I"
		)
	)
	public int trailierTales$adjustMinY(
		int original,
		@Share("trailierTales$terrainAdjustment") LocalRef<TerrainAdjustment> terrainAdjustment
	) {
		if (terrainAdjustment.get() == TrailierEnumValues.SMALL_PLATFORM) {
			return original - 1;
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;terrainAdjustment()Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;"
		)
	)
	public TerrainAdjustment trailierTales$smallPlatformToBury(TerrainAdjustment original) {
		if (original == TrailierEnumValues.SMALL_PLATFORM) {
			return TerrainAdjustment.BURY;
		}
		return original;
	}

	@WrapOperation(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/Beardifier;getBuryContribution(III)D"
		)
	)
	public double trailierTales$smallPlatformLogicInBury(
		int x, int y, int z, Operation<Double> operation,
		@Share("trailierTales$terrainAdjustment") LocalRef<TerrainAdjustment> terrainAdjustment
	) {
		if (terrainAdjustment.get() == TrailierEnumValues.SMALL_PLATFORM) {
			return trailierTales$getSmallPlatformContribution(x, y, z);
		}
		return operation.call(x, y, z);
	}

	@Unique
	private static double trailierTales$getSmallPlatformContribution(int x, int y, int z) {
		double d = Mth.length(x * 5D, y * 3D, z * 5D);
		return Mth.clampedMap(d, 0D, 6D, 1D, 0D);
	}

}
