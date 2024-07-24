package net.frozenblock.trailiertales.mixin;

import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CandleBlock.class)
public class CandleBlockMixin {

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$boostUpUgh(BlockBehaviour.Properties settings, CallbackInfo ci) {
		CandleBlock.class.cast(this).registerDefaultState(
			CandleBlock.class.cast(this).getStateDefinition().any().setValue(CandleBlock.CANDLES, 4).setValue(CandleBlock.LIT, false).setValue(CandleBlock.WATERLOGGED, false)
		);
	}
}
