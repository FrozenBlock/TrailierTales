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
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(BlockConfig syncInstance) {
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
		public boolean place_items = true;
	}

	public static class BlockSounds {
		@EntrySyncData(value = "unpolished_brick_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean unpolished_bricks = true;
		@EntrySyncData(value = "polished_brick_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean polished_bricks = true;
		@EntrySyncData(value = "polished_sounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean polished = true;
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
