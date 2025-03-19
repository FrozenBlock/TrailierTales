/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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

public final class TTWorldgenConfig {

	public static final Config<TTWorldgenConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			TTConstants.MOD_ID,
			TTWorldgenConfig.class,
			TTPreLoadConstants.configPath("worldgen", true),
			JsonType.JSON5
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(TTWorldgenConfig syncInstance) {
				var config = this.config();
				GENERATE_CATACOMBS = config.catacombs;
				GENERATE_GENERIC_RUINS = config.ruins.generic;
				GENERATE_SNOWY_RUINS = config.ruins.snowy;
				GENERATE_JUNGLE_RUINS = config.ruins.jungle;
				GENERATE_SAVANNA_RUINS = config.ruins.savanna;
				GENERATE_DESERT_RUINS = config.ruins.desert;
				GENERATE_BADLANDS_RUINS = config.ruins.badlands;
				GENERATE_DEEPSLATE_RUINS = config.ruins.deepslate;
			}
		}
	);

	public static volatile boolean GENERATE_CATACOMBS = true;
	public static volatile boolean GENERATE_GENERIC_RUINS = true;
	public static volatile boolean GENERATE_SNOWY_RUINS = true;
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

	@EntrySyncData("catacombs")
	public boolean catacombs = true;

	public static class Ruins {
		@EntrySyncData("generic")
		public boolean generic = true;

		@EntrySyncData("snowy")
		public boolean snowy = true;

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

		@EntrySyncData("generateDawntrail")
		public boolean generateDawntrail = false;
	}

	public static class EndCity {
		@EntrySyncData("generateCracked")
		public boolean generateCracked = true;

		@EntrySyncData("generateChoral")
		public boolean generateChoral = true;

		@EntrySyncData("generateChiseled")
		public boolean generateChiseled = true;
	}

	public static TTWorldgenConfig get() {
		return get(false);
	}

	public static TTWorldgenConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static TTWorldgenConfig getWithSync() {
		return INSTANCE.configWithSync();
	}
}
