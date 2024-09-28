package net.frozenblock.trailiertales.mixin.common.brushable_block;

import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityBlock.class)
public interface EntityBlockMixin {

	@Inject(method = "getTicker", at = @At("HEAD"), cancellable = true)
	default <T extends BlockEntity> void trailierTales$getTicker(Level world, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
		if (type == BlockEntityType.BRUSHABLE_BLOCK) {
			info.setReturnValue(
				BaseEntityBlock.createTickerHelper(
					type,
					BlockEntityType.BRUSHABLE_BLOCK,
					(worldx, pos, statex, blockEntity) -> {
						if (blockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) {
							brushableBlockEntityInterface.trailierTales$tick();
						}
					})
			);
		}
	}
}
