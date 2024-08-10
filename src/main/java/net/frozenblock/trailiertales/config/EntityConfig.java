package net.frozenblock.trailiertales.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TrailierConstants;

public final class EntityConfig {

	public static final Config<EntityConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TrailierConstants.MOD_ID,
			EntityConfig.class,
			TrailierConstants.configPath("entity", true),
			JsonType.JSON5,
			null,
			null
		)
	);

	@CollapsibleObject
	public final Sniffer sniffer = new Sniffer();

	@CollapsibleObject
	public final Camel camel = new Camel();

	@CollapsibleObject
	public final Villager villager = new Villager();

	@CollapsibleObject
	public final ArmorStand armorStand = new ArmorStand();

	public static class Sniffer {
		@EntrySyncData(value = "dig_cyan_rose_seeds")
		public boolean cyan_rose_seeds = true;
		@EntrySyncData(value = "spawn_sniffer")
		public boolean spawn = false;
	}

	public static class Camel {
		@EntrySyncData(value = "spawn_camel")
		public boolean spawn = true;
	}

	public static class Villager {
		@EntrySyncData(value = "villagers_sell_catacombs_map")
		public boolean sell_catacombs_map = true;
	}

	public static class ArmorStand {
		@EntrySyncData(value = "armor_stand_arms")
		public boolean armor_stand_arms = true;
	}

	public static EntityConfig get() {
		return get(false);
	}

	public static EntityConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static EntityConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
