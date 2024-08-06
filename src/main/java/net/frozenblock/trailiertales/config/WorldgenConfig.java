package net.frozenblock.trailiertales.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TrailierConstants;

public final class WorldgenConfig {

	public static final Config<WorldgenConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TrailierConstants.MOD_ID,
			WorldgenConfig.class,
			TrailierConstants.configPath("worldgen", true),
			JsonType.JSON5,
			null,
			null
		)
	);

	@CollapsibleObject
	public final Vegetation vegetation = new Vegetation();

	@CollapsibleObject
	public final EndCity endCity = new EndCity();

	public static class Vegetation {
		@EntrySyncData("generateTorchflower")
		public boolean generateTorchflower = false;

		@EntrySyncData("generatePitcher")
		public boolean generatePitcher = false;

		@EntrySyncData("generateCyanRose")
		public boolean generateCyanRose = false;
	}

	public static class EndCity {
		@EntrySyncData("generateCracked")
		public boolean generateCracked = true;

		@EntrySyncData("generateChoral")
		public boolean generateChoral = true;

		@EntrySyncData("generateChiseled")
		public boolean generateChiseled = true;
	}

	public static WorldgenConfig get() {
		return get(false);
	}

	public static WorldgenConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static WorldgenConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
