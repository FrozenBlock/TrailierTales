package net.lunade.onetwenty.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.lunade.onetwenty.Luna120BlocksAndItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

	@Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityType$Builder;of(Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType$Builder;", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=brushable_block")))
	private static <T extends BlockEntity> BlockEntityType.Builder<T> luna120$addSuspiciousSand(BlockEntityType.BlockEntitySupplier<? extends T> blockEntitySupplier, Block[] blocks) {
		List<Block> brushableBlocks = new ArrayList<>(Arrays.stream(blocks).toList());
		brushableBlocks.add(Luna120BlocksAndItems.SUSPICIOUS_RED_SAND);
		brushableBlocks.add(Luna120BlocksAndItems.SUSPICIOUS_DIRT);
		brushableBlocks.add(Luna120BlocksAndItems.SUSPICIOUS_CLAY);
		return new BlockEntityType.Builder<>(blockEntitySupplier, Set.copyOf(brushableBlocks));
	}

}
