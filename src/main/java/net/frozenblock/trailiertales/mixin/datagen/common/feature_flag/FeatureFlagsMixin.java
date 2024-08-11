package net.frozenblock.trailiertales.mixin.datagen.common.feature_flag;

import net.frozenblock.trailiertales.TrailierFeatureFlags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FeatureFlags.class, priority = 2000)
public class FeatureFlagsMixin {
	@Final
	@Shadow
	@Mutable
	public static FeatureFlagSet DEFAULT_FLAGS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void trailierTales$modifyDefaultSet(CallbackInfo info) {
		DEFAULT_FLAGS = DEFAULT_FLAGS.join(TrailierFeatureFlags.TRAILIER_TALES_FLAG_SET);
	}
}
