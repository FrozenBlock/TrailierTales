package net.lunade.onetwenty.mixin;

import net.lunade.onetwenty.interfaces.DecoratedPotBlockEntityInterface;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotBlockEntity.class)
public class DecoratedPotBlockEntityMixin implements DecoratedPotBlockEntityInterface {

	@Unique
	private boolean luna120$flipWobble = false;

	@Inject(
		method = "triggerEvent",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getGameTime()J"
		)
	)
	public void luna120$flipWobble(int i, int j, CallbackInfoReturnable<Boolean> cir) {
		this.luna120$flipWobble = !this.luna120$flipWobble;
	}

	@Unique
	@Override
	public boolean luna120$isWobbleFlipped() {
		return this.luna120$flipWobble;
	}
}
