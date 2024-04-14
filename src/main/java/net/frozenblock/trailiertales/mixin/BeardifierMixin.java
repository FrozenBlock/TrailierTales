package net.frozenblock.trailiertales.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Beardifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Beardifier.class)
public class BeardifierMixin {

	@Overwrite
	private static double getBuryContribution(int x, int y, int z) {
		double d = Mth.length(x * 5D, y * 3D, z * 5D);
		return Mth.clampedMap(d, 0D, 6D, 1D, 0D);
	}

}
