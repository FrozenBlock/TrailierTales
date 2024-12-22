package net.frozenblock.trailiertales.mixin.common.ectoplasm_block;

import net.frozenblock.trailiertales.impl.InEctoplasmBlockInterface;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Entity.class)
public class EntityMixin implements InEctoplasmBlockInterface {

	@Unique
	private boolean trailierTales$clipInEctoplasm;

	@Unique
	@Override
	public void trailierTales$setClipInEctoplasm(boolean clipInEctoplasm) {
		this.trailierTales$clipInEctoplasm = clipInEctoplasm;
	}

	@Unique
	@Override
	public boolean trailierTales$wasClipInEctoplasm() {
		return this.trailierTales$clipInEctoplasm;
	}
}
