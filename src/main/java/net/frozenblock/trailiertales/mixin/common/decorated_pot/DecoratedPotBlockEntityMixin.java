package net.frozenblock.trailiertales.mixin.common.decorated_pot;

import net.frozenblock.trailiertales.impl.client.DecoratedPotBlockEntityInterface;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotBlockEntity.class)
public class DecoratedPotBlockEntityMixin implements DecoratedPotBlockEntityInterface {

	@Unique
	private boolean trailierTales$flipWobble = false;

	@Inject(
		method = "triggerEvent",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getGameTime()J"
		)
	)
	public void trailierTales$flipWobble(int i, int j, CallbackInfoReturnable<Boolean> cir) {
		this.trailierTales$flipWobble = !this.trailierTales$flipWobble;
	}

	@Unique
	@Override
	public boolean trailierTales$isWobbleFlipped() {
		return this.trailierTales$flipWobble;
	}
}
