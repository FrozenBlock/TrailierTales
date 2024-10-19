package net.frozenblock.trailiertales.mixin.common.brushable_block;

import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	public void trailierTales$useItemOn(
		ItemStack stack,
		BlockState blockState,
		Level level,
		BlockPos blockPos,
		Player player,
		InteractionHand interactionHand,
		BlockHitResult hitResult,
		CallbackInfoReturnable<ItemInteractionResult> info
	) {
		if (blockState.getBlock() instanceof BrushableBlock) {
			if (TTBlockConfig.get().suspiciousBlocks.place_items && blockState.hasProperty(TTBlockStateProperties.CAN_PLACE_ITEM)) {
				ItemStack playerStack = player.getItemInHand(interactionHand);
				boolean canPlaceIntoBlock = blockState.getValue(TTBlockStateProperties.CAN_PLACE_ITEM) &&
					playerStack != ItemStack.EMPTY &&
					playerStack.getItem() != Items.AIR &&
					!playerStack.is(Items.BRUSH);
				if (canPlaceIntoBlock) {
					if (level.getBlockEntity(blockPos) instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) {
						brushableBlockEntityInterface.trailierTales$setItem(playerStack.split(1));
						info.setReturnValue(ItemInteractionResult.SUCCESS);
						return;
					}
				}
				info.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
			}
		}
	}

	@Inject(method = "onRemove", at = @At("HEAD"))
	public void trailierTales$onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newState, boolean moved, CallbackInfo info) {
		if (blockState.getBlock() instanceof BrushableBlock) {
			if (level.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity
				&& brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface
				&& brushableBlockEntityInterface.trailierTales$hasCustomItem()
			) {
				Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), brushableBlockEntity.getItem());
			}
		}
	}
}
