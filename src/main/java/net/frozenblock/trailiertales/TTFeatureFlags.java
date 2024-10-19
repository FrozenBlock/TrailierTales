package net.frozenblock.trailiertales;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

public class TTFeatureFlags {
	public static final FeatureFlag TRAILIER_TALES = FrozenFeatureFlags.builder.create(TTConstants.id(TTConstants.MOD_ID));
	public static final FeatureFlagSet TRAILIER_TALES_FLAG_SET = FeatureFlagSet.of(TRAILIER_TALES);

	public static final FeatureFlag FEATURE_FLAG = FrozenBools.IS_DATAGEN ? TRAILIER_TALES : FeatureFlags.VANILLA;

	public static void init() {
	}
}
