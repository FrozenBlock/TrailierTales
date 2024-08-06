package net.frozenblock.trailiertales.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TrailierConstants;

public final class ItemConfig {

	public static final Config<ItemConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TrailierConstants.MOD_ID,
			ItemConfig.class,
			TrailierConstants.configPath("item", true),
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
			public void onSync(ItemConfig syncInstance) {
				var config = this.config();
				SMOOTH_BRUSH_ANIMATION = config.brush.smooth_animations;
			}
		}
	);

	public static volatile boolean SMOOTH_BRUSH_ANIMATION = true;

	@CollapsibleObject
	public final Brush brush = new Brush();

	public static class Brush {
		@EntrySyncData(value = "smooth_animations", behavior = SyncBehavior.UNSYNCABLE)
		public boolean smooth_animations = true;
	}

	public static ItemConfig get() {
		return get(false);
	}

	public static ItemConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static ItemConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
