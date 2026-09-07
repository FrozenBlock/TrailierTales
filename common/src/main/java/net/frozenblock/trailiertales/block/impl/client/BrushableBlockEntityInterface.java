package net.frozenblock.trailiertales.block.impl.client;

import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public interface BrushableBlockEntityInterface {
	default BrushableBlockAnimationState trailierTales$getAnimationState() {
		throw new AssertionError();
	}
}
