package net.frozenblock.trailiertales.config;

import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTPreLoadConstants;

public final class TTMiscConfig {

	public static final Config<TTMiscConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TTConstants.MOD_ID,
			TTMiscConfig.class,
			TTPreLoadConstants.configPath("misc", true),
			JsonType.JSON5
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(TTMiscConfig syncInstance) {
				TTMiscConfig miscConfig = this.config();
				Client.DISTORTED_CATACOMBS_MUSIC = miscConfig.distortedCatacombsMusic;
			}
		}
	);

	public static class Client {
		public static volatile boolean DISTORTED_CATACOMBS_MUSIC = true;
	}

	@EntrySyncData(value = "modify_advancements")
	public boolean modify_advancements = true;

	@EntrySyncData(value = "distortedCatacombsMusic", behavior = SyncBehavior.UNSYNCABLE)
	public boolean distortedCatacombsMusic = true;

	public static TTMiscConfig get() {
		return get(false);
	}

	public static TTMiscConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static TTMiscConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
