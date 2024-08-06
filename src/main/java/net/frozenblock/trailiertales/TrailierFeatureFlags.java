package net.frozenblock.trailiertales;

import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;

public class TrailierFeatureFlags {
	public static final FeatureFlag TRAILIER_TALES = FrozenFeatureFlags.builder.create(TrailierConstants.id(TrailierConstants.MOD_ID));
	public static final FeatureFlagSet TRAILIER_TALES_FLAG_SET = FeatureFlagSet.of(TRAILIER_TALES);

	public static void init() {
	}
}
