package net.frozenblock.trailiertales.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TrailierConstants;

public final class BlockConfig {

	public static final Config<BlockConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TrailierConstants.MOD_ID,
			BlockConfig.class,
			TrailierConstants.configPath("block", true),
			JsonType.JSON5,
			null,
			null
		)
	);

	@CollapsibleObject
	public final BlockSounds blockSounds = new BlockSounds();

	public static class BlockSounds {
		@EntrySyncData(value = "brick_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean bricks = true;
		@EntrySyncData(value = "stone_brick_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean stone_bricks = true;
	}

	public static BlockConfig get() {
		return get(false);
	}

	public static BlockConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static BlockConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
