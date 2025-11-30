/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTPreLoadConstants;

public final class TTEntityConfig {

	public static final Config<TTEntityConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TTConstants.MOD_ID,
			TTEntityConfig.class,
			TTPreLoadConstants.configPath("entity", true),
			JsonType.JSON5
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(TTEntityConfig syncInstance) {
				final TTEntityConfig entityConfig = this.config();
				TTEntityConfig.HAUNTED_FOG = entityConfig.apparition.haunted_fog;
				TTEntityConfig.HAUNTED_LIGHTMAP = entityConfig.apparition.haunted_lightmap;
				TTEntityConfig.HAUNTED_SOUNDS = entityConfig.apparition.haunted_sounds;
				TTEntityConfig.HAUNTED_HUD = entityConfig.apparition.haunted_hud;
			}
		}
	);

	public static volatile boolean HAUNTED_FOG = true;
	public static volatile boolean HAUNTED_LIGHTMAP = true;
	public static volatile boolean HAUNTED_SOUNDS = true;
	public static volatile boolean HAUNTED_HUD = true;

	@CollapsibleObject
	public final Apparition apparition = new Apparition();

	@CollapsibleObject
	public final Sniffer sniffer = new Sniffer();

	@CollapsibleObject
	public final Camel camel = new Camel();

	@CollapsibleObject
	public final Villager villager = new Villager();

	@CollapsibleObject
	public final ArmorStand armorStand = new ArmorStand();

	public static class Apparition {
		@EntrySyncData(value = "picks_up_items")
		public boolean picks_up_items = true;
		@EntrySyncData(value = "catches_projectiles")
		public boolean catches_projectiles = true;
		@EntrySyncData(value = "ignore_mob_griefing")
		public boolean ignore_mob_griefing = false;
		@EntrySyncData(value = "hypnotizes_mobs")
		public boolean hypnotizes_mobs = true;
		@EntrySyncData(value = "haunts_players")
		public boolean haunts_players = true;
		@EntrySyncData(value = "haunted_coffins")
		public boolean haunted_coffins = true;
		@EntrySyncData(value = "haunted_fog")
		public boolean haunted_fog = true;
		@EntrySyncData(value = "haunted_lightmap")
		public boolean haunted_lightmap = true;
		@EntrySyncData(value = "haunted_sounds")
		public boolean haunted_sounds = true;
		@EntrySyncData(value = "haunted_hud")
		public boolean haunted_hud = true;
	}

	public static class Sniffer {
		@EntrySyncData(value = "dig_cyan_rose_seeds")
		public boolean cyan_rose_seeds = true;
		@EntrySyncData(value = "dig_manedrop_germs")
		public boolean manedrop_germs = true;
		@EntrySyncData(value = "dig_dawntrail_seeds")
		public boolean dawntrail_seeds = true;
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

	public static TTEntityConfig get() {
		return get(false);
	}

	public static TTEntityConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static TTEntityConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
