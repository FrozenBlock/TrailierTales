package net.frozenblock.trailiertales.mixin.structure_building.common;

import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CandleBlock.class)
public class CandleBlockMixin {

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$init(BlockBehaviour.Properties settings, CallbackInfo info) {
		CandleBlock candleBlock = CandleBlock.class.cast(this);
		candleBlock.registerDefaultState(candleBlock.defaultBlockState().setValue(CandleBlock.CANDLES, CandleBlock.MAX_CANDLES));
	}

}
