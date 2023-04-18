package net.lunade.onetwenty.mixin;

import net.lunade.onetwenty.interfaces.BrushableBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BrushableBlock.class)
public abstract class BrushableBlockMixin extends BaseEntityBlock {

	protected BrushableBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (level.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity) {
			if (brushableBlockEntity.getItem() == ItemStack.EMPTY && brushableBlockEntity.lootTable == null) {
				ItemStack playerStack = player.getItemInHand(interactionHand);
				if (playerStack != ItemStack.EMPTY && playerStack.getItem() != Items.AIR && !playerStack.is(Items.BRUSH)) {
					ItemStack splitStack = playerStack.split(1);
					if (splitStack.getCount() > 0) {
						((BrushableBlockEntityInterface) brushableBlockEntity).luna120$setItem(splitStack);
						return InteractionResult.SUCCESS;
					}
				}
			}
		}
		return InteractionResult.PASS;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return BaseEntityBlock.createTickerHelper(blockEntityType, BlockEntityType.BRUSHABLE_BLOCK, (worldx, pos, statex, blockEntity) -> ((BrushableBlockEntityInterface) blockEntity).luna120$tick());
	}
}
