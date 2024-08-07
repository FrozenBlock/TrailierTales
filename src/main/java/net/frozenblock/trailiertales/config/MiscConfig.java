package net.frozenblock.trailiertales.config;

import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TrailierConstants;

public final class MiscConfig {

	public static final Config<MiscConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TrailierConstants.MOD_ID,
			MiscConfig.class,
			TrailierConstants.configPath("misc", true),
			JsonType.JSON5,
			null,
			null
		)
	);

	@EntrySyncData(value = "modify_advancements")
	public boolean modify_advancements = true;

	public static MiscConfig get() {
		return get(false);
	}

	public static MiscConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static MiscConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
