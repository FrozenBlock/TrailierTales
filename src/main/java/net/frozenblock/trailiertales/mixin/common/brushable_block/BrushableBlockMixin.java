package net.frozenblock.trailiertales.mixin.common.brushable_block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.trailiertales.config.BlockConfig;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.frozenblock.trailiertales.impl.FallingBlockEntityInterface;
import net.frozenblock.trailiertales.registry.RegisterProperties;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushableBlock.class)
public abstract class BrushableBlockMixin extends BaseEntityBlock {

	protected BrushableBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$init(Block block, SoundEvent soundEvent, SoundEvent soundEvent2, BlockBehaviour.Properties properties, CallbackInfo info) {
		this.registerDefaultState(this.defaultBlockState().setValue(RegisterProperties.CAN_PLACE_ITEM, false));
	}

	@Override
	protected ItemInteractionResult useItemOn(
		@NotNull ItemStack itemStack,
		@NotNull BlockState blockState,
		@NotNull Level level,
		@NotNull BlockPos blockPos,
		@NotNull Player player,
		@NotNull InteractionHand interactionHand,
		@NotNull BlockHitResult blockHitResult
	) {
		if (BlockConfig.get().suspiciousBlocks.place_items) {
			ItemStack playerStack = player.getItemInHand(interactionHand);
			boolean canPlaceIntoBlock = blockState.getValue(RegisterProperties.CAN_PLACE_ITEM) &&
				playerStack != ItemStack.EMPTY &&
				playerStack.getItem() != Items.AIR &&
				!playerStack.is(Items.BRUSH);
			if (canPlaceIntoBlock) {
				if (level.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity) {
					((BrushableBlockEntityInterface) brushableBlockEntity).trailierTales$setItem(playerStack.split(1));
					return ItemInteractionResult.SUCCESS;
				}
			}
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}
		return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
	}

	@Override
	public void onRemove(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState2, boolean bl) {
		if (blockState.is(blockState2.getBlock())) {
			return;
		}
		if (
			level.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity
			&& brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface
			&& brushableBlockEntityInterface.trailierTales$hasCustomItem()
		) {
			Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), brushableBlockEntity.getItem());
		}
		super.onRemove(blockState, level, blockPos, blockState2, bl);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
		return BaseEntityBlock.createTickerHelper(
			blockEntityType,
			BlockEntityType.BRUSHABLE_BLOCK,
			(worldx, pos, statex, blockEntity) -> {
				if (blockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface) {
					brushableBlockEntityInterface.trailierTales$tick();
				}
			}
		);
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;checkReset()V"
		)
	)
	public void trailierTales$setHasCustomItemForFalling(
		BrushableBlockEntity brushableBlockEntity, Operation<Void> original,
		BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random,
		@Share("trailierTales$brushableBlockEntity") LocalRef<BrushableBlockEntity> blockEntityRef,
		@Share("trailierTales$hasCustomItem") LocalBooleanRef hasCustomItem
	) {
		original.call(brushableBlockEntity);
		blockEntityRef.set(brushableBlockEntity);
		if (
			brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface &&
				(brushableBlockEntityInterface.trailierTales$hasCustomItem() ||
					(state.hasProperty(RegisterProperties.CAN_PLACE_ITEM) && state.getValue(RegisterProperties.CAN_PLACE_ITEM)))
		) {
			hasCustomItem.set(true);
		}
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;fall(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/entity/item/FallingBlockEntity;",
			shift = At.Shift.BEFORE
		)
	)
	public void trailierTales$getFallingBlockItem(
		BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo info,
		@Share("trailierTales$brushableBlockEntity") LocalRef<BrushableBlockEntity> blockEntityRef,
		@Share("trailierTales$hasCustomItem") LocalBooleanRef hasCustomItem,
		@Share("trailierTales$itemStack") LocalRef<ItemStack> itemStack
	) {
		BrushableBlockEntity brushableBlockEntity = blockEntityRef.get();
		if (brushableBlockEntity != null && hasCustomItem.get()) {
			itemStack.set(brushableBlockEntity.getItem().copy());
			((BrushableBlockEntityInterface) brushableBlockEntity).trailierTales$setItem(ItemStack.EMPTY);
		}
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;fall(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/entity/item/FallingBlockEntity;"
		)
	)
	public FallingBlockEntity trailierTales$setFallingBlockItem(
		FallingBlockEntity original,
		@Share("trailierTales$hasCustomItem") LocalBooleanRef hasCustomItem,
		@Share("trailierTales$itemStack") LocalRef<ItemStack> itemStack
	) {
		if (hasCustomItem.get() && original instanceof FallingBlockEntityInterface fallingBlockEntityInterface) {
			fallingBlockEntityInterface.trailierTales$setItem(itemStack.get());
		}
		return original;
	}

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
	protected void trailierTales$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		builder.add(RegisterProperties.CAN_PLACE_ITEM);
	}

}
