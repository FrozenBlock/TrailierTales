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
