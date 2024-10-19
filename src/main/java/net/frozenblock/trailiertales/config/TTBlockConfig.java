package net.frozenblock.trailiertales.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTPreLoadConstants;

public final class TTBlockConfig {

	public static final Config<TTBlockConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TTConstants.MOD_ID,
			TTBlockConfig.class,
			TTPreLoadConstants.configPath("block", true),
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
			public void onSync(TTBlockConfig syncInstance) {
				var config = this.config();
				SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS = config.suspiciousBlocks.smooth_animations;
				SUSPICIOUS_BLOCK_PARTICLES = config.suspiciousBlocks.particle;
			}
		}
	);

	public static volatile boolean SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS = true;
	public static volatile boolean SUSPICIOUS_BLOCK_PARTICLES = false;

	@CollapsibleObject
	public final SuspiciousBlocks suspiciousBlocks = new SuspiciousBlocks();

	@CollapsibleObject
	public final BlockSounds blockSounds = new BlockSounds();

	public static class SuspiciousBlocks {
		@EntrySyncData(value = "smooth_animations", behavior = SyncBehavior.UNSYNCABLE)
		public boolean smooth_animations = true;
		@EntrySyncData(value = "particle", behavior = SyncBehavior.UNSYNCABLE)
		public boolean particle = false;
		@EntrySyncData(value = "place_items")
		public boolean place_items = false;
	}

	public static class BlockSounds {
		@EntrySyncData(value = "unpolished_brick_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean unpolished_bricks = true;
		@EntrySyncData(value = "polished_brick_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean polished_bricks = true;
		@EntrySyncData(value = "polished_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean polished = true;
		@EntrySyncData(value = "polished_basalt_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean polished_basalt = true;
		@EntrySyncData(value = "polished_deepslate_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean polished_deepslate = true;
		@EntrySyncData(value = "polished_tuff_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean polished_tuff = true;
		@EntrySyncData(value = "polished_calcite_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean polished_calcite = true;
		@EntrySyncData(value = "calcite_bricks_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean calcite_bricks = true;
	}

	public static TTBlockConfig get() {
		return get(false);
	}

	public static TTBlockConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static TTBlockConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
