package net.frozenblock.trailiertales.block.impl;

import net.mehvahdjukaar.candlelight.api.PlatformImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public final class CropGrowthHelper {

	@PlatformImpl
	public static float getGrowthSpeed(BlockState state, BlockGetter level, BlockPos pos) {
		throw new AssertionError();
	}

	private CropGrowthHelper() {}
}
