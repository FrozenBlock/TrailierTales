package net.frozenblock.trailiertales.mixin.common.brushable_block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = BlockEntityType.class, priority = 1100)
public class BlockEntityTypeMixin {

	@WrapOperation(
		method = "<clinit>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/entity/BlockEntityType$Builder;of(Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType$Builder;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "stringValue=brushable_block"
			)
		)
	)
	private static <T extends BlockEntity> BlockEntityType.Builder<T> trailierTales$addSuspiciousSand(
		BlockEntityType.BlockEntitySupplier<? extends T> instance, Block[] blocks, Operation<BlockEntityType.Builder<T>> original
	) {
		List<Block> brushableBlocks = new ArrayList<>(Arrays.stream(blocks).toList());
		brushableBlocks.add(RegisterBlocks.SUSPICIOUS_RED_SAND);
		brushableBlocks.add(RegisterBlocks.SUSPICIOUS_DIRT);
		brushableBlocks.add(RegisterBlocks.SUSPICIOUS_CLAY);
		return new BlockEntityType.Builder<>(instance, Set.copyOf(brushableBlocks));
	}

}
