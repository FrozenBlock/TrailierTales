package net.frozenblock.trailiertales.mixin.common.terrain_adjustment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.trailiertales.worldgen.TrailierTerrainAdjustment;
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
		@Share("trailierTales$terrainAdjustment") LocalRef<TerrainAdjustment> terrainAdjustment,
		@Share("trailierTales$isSmallPlatform") LocalBooleanRef isSmallPlatform,
		@Share("trailierTales$yOffset") LocalIntRef yOffset,
		@Share("trailierTales$xScale") LocalDoubleRef xScale,
		@Share("trailierTales$zScale") LocalDoubleRef zScale
	) {
		yOffset.set(0);
		xScale.set(1D);
		zScale.set(1D);

		if (original instanceof Beardifier.Rigid rigid) {
			terrainAdjustment.set(rigid.terrainAdjustment());
			isSmallPlatform.set(terrainAdjustment.get() == TrailierTerrainAdjustment.SMALL_PLATFORM);
			if (isSmallPlatform.get()) {
				yOffset.set(-4);
				xScale.set(0.5D);
				zScale.set(0.5D);
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
	public int trailierTales$adjustMinY(int original, @Share("trailierTales$yOffset") LocalIntRef yOffset) {
		return original + yOffset.get();
	}


	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(II)I",
			ordinal = 0
		)
	)
	public int trailierTales$shrinkZ(int original, @Share("trailierTales$zScale") LocalDoubleRef zScale) {
		return (int) (original * zScale.get());
	}

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(II)I",
			ordinal = 2
		)
	)
	public int trailierTales$shrinkX(int original, @Share("trailierTales$xScale") LocalDoubleRef xScale) {
		return (int) (original * xScale.get());
	}

	@ModifyExpressionValue(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;terrainAdjustment()Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;"
		)
	)
	public TerrainAdjustment trailierTales$smallPlatformToBury(
		TerrainAdjustment original,
		@Share("trailierTales$isSmallPlatform") LocalBooleanRef isSmallPlatform
	) {
		if (isSmallPlatform.get()) {
			return TerrainAdjustment.BURY;
		}
		return original;
	}

	@WrapOperation(
		method = "compute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/Beardifier;getBuryContribution(DDD)D"
		)
	)
	public double trailierTales$smallPlatformLogicInBury(
		double x, double y, double z, Operation<Double> operation,
		@Share("trailierTales$isSmallPlatform") LocalBooleanRef isSmallPlatform
	) {
		if (isSmallPlatform.get()) {
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
