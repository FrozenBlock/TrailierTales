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
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(WorldgenConfig syncInstance) {
				var config = this.config();
				GENERATE_GENERIC_RUINS = config.ruins.generic;
				GENERATE_JUNGLE_RUINS = config.ruins.jungle;
				GENERATE_SAVANNA_RUINS = config.ruins.savanna;
				GENERATE_DESERT_RUINS = config.ruins.desert;
				GENERATE_BADLANDS_RUINS = config.ruins.badlands;
				GENERATE_DEEPSLATE_RUINS = config.ruins.deepslate;
			}
		}
	);

	public static volatile boolean GENERATE_GENERIC_RUINS = true;
	public static volatile boolean GENERATE_JUNGLE_RUINS = true;
	public static volatile boolean GENERATE_SAVANNA_RUINS = true;
	public static volatile boolean GENERATE_DESERT_RUINS = true;
	public static volatile boolean GENERATE_BADLANDS_RUINS = true;
	public static volatile boolean GENERATE_DEEPSLATE_RUINS = true;

	@CollapsibleObject
	public final Ruins ruins = new Ruins();

	@CollapsibleObject
	public final Vegetation vegetation = new Vegetation();

	@CollapsibleObject
	public final EndCity endCity = new EndCity();

	public static class Ruins {
		@EntrySyncData("generic")
		public boolean generic = true;

		@EntrySyncData("jungle")
		public boolean jungle = true;

		@EntrySyncData("savanna")
		public boolean savanna = true;

		@EntrySyncData("desert")
		public boolean desert = true;

		@EntrySyncData("badlands")
		public boolean badlands = true;

		@EntrySyncData("deepslate")
		public boolean deepslate = true;
	}

	public static class Vegetation {
		@EntrySyncData("generateTorchflower")
		public boolean generateTorchflower = false;

		@EntrySyncData("generatePitcher")
		public boolean generatePitcher = false;

		@EntrySyncData("generateCyanRose")
		public boolean generateCyanRose = false;

		@EntrySyncData("generateManedrop")
		public boolean generateManedrop = false;
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
