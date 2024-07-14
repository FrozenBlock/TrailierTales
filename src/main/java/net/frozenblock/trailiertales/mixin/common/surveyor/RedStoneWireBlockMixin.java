package net.frozenblock.trailiertales.mixin.common.surveyor;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(RedStoneWireBlock.class)
public class RedStoneWireBlockMixin {

	@WrapOperation(
		method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/level/block/Blocks;OBSERVER:Lnet/minecraft/world/level/block/Block;"
			)
		)
	)
	private static boolean trailierTales$shouldConnectTo(BlockState instance, Block block, Operation<Boolean> original) {
		return original.call(instance, block) || instance.is(RegisterBlocks.SURVEYOR);
	}

}
