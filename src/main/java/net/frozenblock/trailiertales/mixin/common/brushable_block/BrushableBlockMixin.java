package net.frozenblock.trailiertales.mixin.common.brushable_block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.impl.BrushableBlockEntityInterface;
import net.frozenblock.trailiertales.impl.FallingBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
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
		BlockState defaultBlockState = this.defaultBlockState();
		if (defaultBlockState.hasProperty(TTBlockStateProperties.CAN_PLACE_ITEM)) {
			this.registerDefaultState(defaultBlockState.setValue(TTBlockStateProperties.CAN_PLACE_ITEM, false));
		}
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;checkReset(Lnet/minecraft/server/level/ServerLevel;)V"
		)
	)
	public void trailierTales$setHasCustomItemForFalling(
		BrushableBlockEntity brushableBlockEntity, ServerLevel level, Operation<Void> original,
		BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random,
		@Share("trailierTales$brushableBlockEntity") LocalRef<BrushableBlockEntity> blockEntityRef,
		@Share("trailierTales$hasCustomItem") LocalBooleanRef hasCustomItem
	) {
		original.call(brushableBlockEntity, level);
		blockEntityRef.set(brushableBlockEntity);
		if (
			brushableBlockEntity instanceof BrushableBlockEntityInterface brushableBlockEntityInterface &&
				(brushableBlockEntityInterface.trailierTales$hasCustomItem() ||
					(state.hasProperty(TTBlockStateProperties.CAN_PLACE_ITEM) && state.getValue(TTBlockStateProperties.CAN_PLACE_ITEM)))
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
		if (TTBlockConfig.get().suspiciousBlocks.place_items) builder.add(TTBlockStateProperties.CAN_PLACE_ITEM);
	}

}
