package net.frozenblock.trailiertales.mixin.common;

import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CandleBlock.class)
public class candlestuff {

	@Inject(
		method = "<init>",
		at = @At("TAIL")
	)
	public void initLOL(BlockBehaviour.Properties settings, CallbackInfo ci) {
		CandleBlock.class.cast(this).registerDefaultState(CandleBlock.class.cast(this).defaultBlockState().setValue(BlockStateProperties.CANDLES, 4));
	}

}
