package net.frozenblock.trailiertales.block.impl.platform;

import net.frozenblock.trailiertales.mixin.common.CropBlockAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public final class CropGrowthHelperImpl {

	public static float getGrowthSpeed(BlockState state, BlockGetter level, BlockPos pos) {
		return CropBlockAccess.getGrowthSpeed(state.getBlock(), level, pos);
	}

	private CropGrowthHelperImpl() {}
}
