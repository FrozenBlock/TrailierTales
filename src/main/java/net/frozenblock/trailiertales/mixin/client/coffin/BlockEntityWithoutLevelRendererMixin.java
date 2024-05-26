package net.frozenblock.trailiertales.mixin.client.coffin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BlockEntityWithoutLevelRendererMixin {

	@Unique
	private final CoffinBlockEntity trailierTales$coffin = new CoffinBlockEntity(BlockPos.ZERO, RegisterBlocks.COFFIN.defaultBlockState());

	@WrapOperation(
		method = "renderByItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/level/block/Blocks;CONDUIT:Lnet/minecraft/world/level/block/Block;"
			)
		)
	)
	public boolean trailierTales$selectCoffin(
		BlockState instance, Block block, Operation<Boolean> original,
		@Share("trailierTales$isCoffin") LocalBooleanRef isCoffin
	) {
		boolean stateIsCoffin = instance.is(RegisterBlocks.COFFIN);
		isCoffin.set(stateIsCoffin);
		return original.call(instance, block) || stateIsCoffin;
	}

	@WrapOperation(
		method = "renderByItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderDispatcher;renderItem(Lnet/minecraft/world/level/block/entity/BlockEntity;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)Z",
			ordinal = 0
		)
	)
	public boolean trailierTales$replaceWithCoffin(
		BlockEntityRenderDispatcher instance, BlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Operation<Boolean> original,
		@Share("trailierTales$isCoffin") LocalBooleanRef isCoffin
	) {
		blockEntity = isCoffin.get() ? this.trailierTales$coffin : blockEntity;
		return original.call(instance, blockEntity, poseStack, bufferSource, packedLight, packedOverlay);
	}

}
