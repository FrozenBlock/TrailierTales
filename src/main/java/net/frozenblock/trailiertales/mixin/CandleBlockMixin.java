package net.frozenblock.trailiertales.mixin;

import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static com.sk89q.worldedit.world.item.ItemCategories.CANDLES;
import static net.frozenblock.lib.block.api.MultifaceClusterBlock.WATERLOGGED;
import static net.minecraft.world.level.block.CopperBulbBlock.LIT;

@Mixin(CandleBlock.class)
public class CandleBlockMixin {

	@Inject(method = "<init>", at = @At("TAIL"))
	public void trailierTales$boostUpUgh(BlockBehaviour.Properties settings, CallbackInfo ci) {
		CandleBlock.class.cast(this).registerDefaultState(
			CandleBlock.class.cast(this).any().setValue(CANDLES, 4).setValue(LIT, false).setValue(WATERLOGGED, false)
		);
	}
}
