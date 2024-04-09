package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.worldgen.impl.SuspiciousBlockConfiguration;
import net.frozenblock.trailiertales.worldgen.impl.SuspiciousBlockFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class RegisterFeatures {
	public static final SuspiciousBlockFeature SUSPICIOUS_BLOCK_FEATURE = new SuspiciousBlockFeature(SuspiciousBlockConfiguration.CODEC);

	public static void init() {
		Registry.register(BuiltInRegistries.FEATURE, TrailierTalesSharedConstants.id("suspicious_block_feature"), SUSPICIOUS_BLOCK_FEATURE);
	}
}
