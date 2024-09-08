package net.frozenblock.trailiertales.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TTConstants;

public final class TTItemConfig {

	public static final Config<TTItemConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TTConstants.MOD_ID,
			TTItemConfig.class,
			TTConstants.configPath("item", true),
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
			public void onSync(TTItemConfig syncInstance) {
				var config = this.config();
				SHERD_DUPLICATION_RECIPE = config.sherd_duplication_recipe;
				SMOOTH_BRUSH_ANIMATION = config.brush.smooth_animations;
				EXTRA_BRUSH_PARTICLES = config.brush.half_brush_effects;
			}
		}
	);

	public static volatile boolean SHERD_DUPLICATION_RECIPE = false;
	public static volatile boolean SMOOTH_BRUSH_ANIMATION = true;
	public static volatile boolean EXTRA_BRUSH_PARTICLES = true;

	@EntrySyncData(value = "sherd_duplication_recipe")
	public boolean sherd_duplication_recipe = false;

	@CollapsibleObject
	public final Brush brush = new Brush();

	public static class Brush {
		@EntrySyncData(value = "smooth_animations", behavior = SyncBehavior.UNSYNCABLE)
		public boolean smooth_animations = true;
		@EntrySyncData(value = "half_brush_effects")
		public boolean half_brush_effects = true;
	}

	public static TTItemConfig get() {
		return get(false);
	}

	public static TTItemConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static TTItemConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
