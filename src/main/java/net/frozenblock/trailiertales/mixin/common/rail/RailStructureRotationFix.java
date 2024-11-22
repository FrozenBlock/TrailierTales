package net.frozenblock.trailiertales.mixin.common.rail;

import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PoweredRailBlock.class, DetectorRailBlock.class})
public class RailStructureRotationFix {

	@Inject(method = "rotate", at = @At("HEAD"), cancellable = true)
	public void trailierTales$fixMissingRailRotationStatements(BlockState state, Rotation rotation, CallbackInfoReturnable<BlockState> info) {
		if (rotation == Rotation.CLOCKWISE_180) {
			RailShape railShape = state.getValue(PoweredRailBlock.SHAPE);
			if (railShape == RailShape.NORTH_SOUTH) info.setReturnValue(state.setValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT, RailShape.NORTH_SOUTH));
			if (railShape == RailShape.EAST_WEST) info.setReturnValue(state.setValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT, RailShape.EAST_WEST));
		}
	}
}
