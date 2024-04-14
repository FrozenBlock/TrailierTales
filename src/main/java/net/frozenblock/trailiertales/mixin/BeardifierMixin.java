package net.frozenblock.trailiertales.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Beardifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Beardifier.class)
public class BeardifierMixin {

	@Overwrite
	private static double getBuryContribution(int x, int y, int z) {
		double d = Mth.length(x, -25D, z);
		return Mth.clampedMap(d, 0.0, 6.0, 1.0, 0.0);
	}

}
