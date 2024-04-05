package net.frozenblock.trailiertales.mixin;

import net.frozenblock.trailiertales.TrailierTales;
import net.frozenblock.trailiertales.interfaces.FallingBlockEntityInterface;
import net.frozenblock.trailiertales.interfaces.BrushableBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BrushableBlock.class)
public abstract class BrushableBlockMixin extends BaseEntityBlock {

	@Unique
	private boolean luna120$cancelledBreakUponFall;
	@Unique
	private ItemStack luna120$itemStack = ItemStack.EMPTY;

	protected BrushableBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BrushableBlock;registerDefaultState(Lnet/minecraft/world/level/block/state/BlockState;)V", shift = At.Shift.AFTER))
	public void lunade120$init(Block block, SoundEvent soundEvent, SoundEvent soundEvent2, BlockBehaviour.Properties properties, CallbackInfo info) {
		this.registerDefaultState(this.defaultBlockState().setValue(TrailierTales.CAN_PLACE_ITEM, false));
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		ItemStack playerStack = player.getItemInHand(interactionHand);
		boolean canPlaceIntoBlock = blockState.getValue(TrailierTales.CAN_PLACE_ITEM) && playerStack != ItemStack.EMPTY && playerStack.getItem() != Items.AIR && !playerStack.is(Items.BRUSH);
		if (canPlaceIntoBlock) {
			if (level.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity) {
				((BrushableBlockEntityInterface) brushableBlockEntity).luna120$setItem(playerStack.split(1));
				return ItemInteractionResult.SUCCESS;
			}
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public void onRemove(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState2, boolean bl) {
		if (blockState.is(blockState2.getBlock())) {
			return;
		}
		if (level.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity && ((BrushableBlockEntityInterface) brushableBlockEntity).luna120$hasCustomItem()) {
			Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), brushableBlockEntity.getItem());
		}
		super.onRemove(blockState, level, blockPos, blockState2, bl);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
		return BaseEntityBlock.createTickerHelper(blockEntityType, BlockEntityType.BRUSHABLE_BLOCK, (worldx, pos, statex, blockEntity) -> ((BrushableBlockEntityInterface) blockEntity).luna120$tick());
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;fall(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/entity/item/FallingBlockEntity;", shift = At.Shift.BEFORE))
	public void luna120$setBreakCancellationValue(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo info) {
		if (serverLevel.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity && (((BrushableBlockEntityInterface) brushableBlockEntity).luna120$hasCustomItem() || (blockState.hasProperty(TrailierTales.CAN_PLACE_ITEM) && blockState.getValue(TrailierTales.CAN_PLACE_ITEM)))) {
			this.luna120$cancelledBreakUponFall = true;
			this.luna120$itemStack = brushableBlockEntity.getItem().copy();
			((BrushableBlockEntityInterface) brushableBlockEntity).luna120$setItem(ItemStack.EMPTY);
		}
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;disableDrop()V", shift = At.Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	public void luna120$tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo info, FallingBlockEntity fallingBlockEntity) {
		if (this.luna120$cancelledBreakUponFall) {
			((FallingBlockEntityInterface) fallingBlockEntity).luna120$setItem(this.luna120$itemStack);
			info.cancel();
		}
	}

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
	protected void luna120$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
		builder.add(TrailierTales.CAN_PLACE_ITEM);
	}

}
