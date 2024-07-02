package net.frozenblock.trailiertales.mixin.common.terrain_adjustment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.worldgen.TrailierTerrainAdjustment;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Beardifier.class)
public class BeardifierMixin {

	@Unique
	@Nullable
	private TerrainAdjustment trailierTales$terrainAdjustment = null;

	@Unique
	private int trailierTales$yOffset = 0;

	@Unique
	private double trailierTales$xScale = 1D;

	@Unique
	private double trailierTales$zScale = 1D;

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lit/unimi/dsi/fastutil/objects/ObjectListIterator;next()Ljava/lang/Object;",
			ordinal = 0
		)
	)
	public Object trailierTales$setTerrainAdjustmentValues(Object original) {
		if (this.trailierTales$terrainAdjustment == null) {
			System.out.println("NULL LMFAO WHAT AN IDIOT");
			if (original instanceof Beardifier.Rigid rigid) {
				TerrainAdjustment adjustment = rigid.terrainAdjustment();
				this.trailierTales$terrainAdjustment = adjustment;
				if (adjustment == TrailierTerrainAdjustment.SMALL_PLATFORM) {
					this.trailierTales$yOffset = -4;
					this.trailierTales$xScale = 0.5D;
					this.trailierTales$zScale = 0.5D;
				}
			}
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
	public int trailierTales$adjustMinY(int original) {
		return original + this.trailierTales$yOffset;
	}

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/structure/BoundingBox;maxY()I"
		)
	)
	public int trailierTales$adjustMaxY(int original) {
		return original + this.trailierTales$yOffset;
	}

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(II)I",
			ordinal = 0
		)
	)
	public int trailierTales$shrinkZ(int original) {
		return (int) (original * this.trailierTales$zScale);
	}

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(II)I",
			ordinal = 2
		)
	)
	public int trailierTales$shrinkX(int original) {
		return (int) (original * this.trailierTales$xScale);
	}

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;terrainAdjustment()Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;"
		)
	)
	public TerrainAdjustment trailierTales$smallPlatformToBury(TerrainAdjustment original) {
		if (this.trailierTales$terrainAdjustment == TrailierTerrainAdjustment.SMALL_PLATFORM) {
			return TerrainAdjustment.BURY;
		}
		return original;
	}

	@WrapOperation(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/Beardifier;getBuryContribution(DDD)D",
			ordinal = 0
		)
	)
	public double trailierTales$smallPlatformLogicInBury(double x, double y, double z, Operation<Double> operation) {
		if (this.trailierTales$terrainAdjustment == TrailierTerrainAdjustment.SMALL_PLATFORM) {
			return trailierTales$getSmallPlatformContribution(x, y, z);
		}
		return operation.call(x, y, z);
	}

	@Unique
	private static double trailierTales$getSmallPlatformContribution(double x, double y, double z) {
		double d = Mth.length(x * 4D, y * 3D, z * 4D);
		return Mth.clampedMap(d, 0D, 6D, 1D, 0D);
	}

}
