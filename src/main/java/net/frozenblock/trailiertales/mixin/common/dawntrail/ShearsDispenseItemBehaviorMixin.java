package net.frozenblock.trailiertales.mixin.common.dawntrail;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.block.DawntrailBlock;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShearsDispenseItemBehavior.class)
public class ShearsDispenseItemBehaviorMixin {

	@WrapOperation(
		method = "execute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/dispenser/ShearsDispenseItemBehavior;tryShearBeehive(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean trailierTales$execute(ServerLevel world, BlockPos pos, Operation<Boolean> original) {
		return original.call(world, pos) ||
			trailierTales$tryShearDawntrail(world, pos);
	}

	@Unique
	private static boolean trailierTales$tryShearDawntrail(@NotNull ServerLevel level, BlockPos pos) {
		BlockState blockState = level.getBlockState(pos);
		if (blockState.getBlock() == RegisterBlocks.DAWNTRAIL && DawntrailBlock.isMaxAge(blockState)) {
			DawntrailBlock.shear(level, pos, blockState, null);
			return true;
		}
		return false;
	}
}
