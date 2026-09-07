package net.frozenblock.trailiertales.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CropBlock.class)
public interface CropBlockAccess {

	// NeoForge patches CropBlock#getGrowthSpeed to take the BlockState instead of Block.
	@Invoker("getGrowthSpeed")
	static float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
		throw new AssertionError();
	}
}
