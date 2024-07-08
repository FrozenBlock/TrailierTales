package net.frozenblock.trailiertales.mixin.common.terrain_adjustment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.frozenblock.trailiertales.worldgen.TrailierTerrainAdjustment;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Beardifier.class)
public class BeardifierMixin {

	@Shadow
	@Final
	private ObjectListIterator<Beardifier.Rigid> pieceIterator;
	@Unique
	@Nullable
	private TerrainAdjustment trailierTales$terrainAdjustment = null;

	@Unique
	private int trailierTales$yOffset = 0;

	@Unique
	private double trailierTales$xScale = 1D;

	@Unique
	private double trailierTales$zScale = 1D;

	@Unique
	private double trailierTales$xContributionScale = 1D;

	@Unique
	private double trailierTales$yContributionScale = 1D;

	@Unique
	private double trailierTales$zContributionScale = 1D;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$setTerrainAdjustmentValues(ObjectListIterator<Beardifier.Rigid> pieceIterator, ObjectListIterator<JigsawJunction> junctionIterator, CallbackInfo info) {
		if (this.pieceIterator.hasNext()) {
			TerrainAdjustment adjustment = this.pieceIterator.next().terrainAdjustment();
			this.trailierTales$terrainAdjustment = adjustment;
			if (adjustment == TrailierTerrainAdjustment.SMALL_PLATFORM) {
				this.trailierTales$yOffset = -4;
				this.trailierTales$xScale = 0.5D;
				this.trailierTales$zScale = 0.5D;
				this.trailierTales$xContributionScale = 4D;
				this.trailierTales$yContributionScale = 3D;
				this.trailierTales$zContributionScale = 4D;
			}
			this.pieceIterator.previous();
		}
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
		if (original == TrailierTerrainAdjustment.SMALL_PLATFORM) {
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
	public double trailierTales$smallPlatformLogicInBury(double x, double y, double z, Operation<Double> operation) {
		return operation.call(x * this.trailierTales$xContributionScale, y * this.trailierTales$yContributionScale, z * this.trailierTales$zContributionScale);
	}

}
