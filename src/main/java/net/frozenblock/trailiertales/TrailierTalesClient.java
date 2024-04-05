package net.frozenblock.trailiertales;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.util.TrailierTalesSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

@Environment(EnvType.CLIENT)
public class TrailierTalesClient implements ClientModInitializer {

	public static final String BLANK_DECORATED_NAME = "decorated_pot_blank_side";
	public static final ResourceKey<String> BLANK_DECORATED = ResourceKey.create(Registries.DECORATED_POT_PATTERNS, TrailierTalesSharedConstants.id(BLANK_DECORATED_NAME));

	@Override
	public void onInitializeClient() {
	}
}
