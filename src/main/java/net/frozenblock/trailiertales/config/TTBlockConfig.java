/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

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
			JsonType.JSON5
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(TTBlockConfig syncInstance) {
				var config = this.config();
				COFFIN_IGNORE_DOMOBSPAWNING = config.coffin.ignore_do_mob_spawning_gamerule;
				SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS = config.suspiciousBlocks.smooth_animations;
				SUSPICIOUS_BLOCK_PARTICLES = config.suspiciousBlocks.particle;
			}
		}
	);

	public static volatile boolean COFFIN_IGNORE_DOMOBSPAWNING = false;
	public static volatile boolean SMOOTH_SUSPICIOUS_BLOCK_ANIMATIONS = true;
	public static volatile boolean SUSPICIOUS_BLOCK_PARTICLES = false;

	@CollapsibleObject
	public final SuspiciousBlocks suspiciousBlocks = new SuspiciousBlocks();

	@CollapsibleObject
	public final Coffin coffin = new Coffin();

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

	public static class Coffin {
		@EntrySyncData(value = "ignore_do_mob_spawning_gamerule")
		public boolean ignore_do_mob_spawning_gamerule = false;
		@EntrySyncData(value = "wobble")
		public boolean wobble = true;
		@EntrySyncData(value = "wobble_activate")
		public boolean wobble_activate = true;
		@EntrySyncData(value = "wobble_loot")
		public boolean wobble_loot = false;
		@EntrySyncData(value = "wobble_potion")
		public boolean wobble_potion = false;
		@EntrySyncData(value = "wobble_experience_bottle")
		public boolean wobble_experience_bottle = false;
	}

	public static class BlockSounds {
		@EntrySyncData("unpolished_brick_sounds")
		public boolean unpolished_bricks = true;
		@EntrySyncData("polished_brick_sounds")
		public boolean polished_bricks = true;
		@EntrySyncData("polished_sounds")
		public boolean polished = true;
		@EntrySyncData("polished_basalt_sounds")
		public boolean polished_basalt = true;
		@EntrySyncData("polished_deepslate_sounds")
		public boolean polished_deepslate = true;
		@EntrySyncData("polished_tuff_sounds")
		public boolean polished_tuff = true;
		@EntrySyncData("polished_calcite_sounds")
		public boolean polished_calcite = true;
		@EntrySyncData("calcite_bricks_sounds")
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
