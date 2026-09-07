/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.booleanEntry;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class TTMiscConfigGui {

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "modify_advancements", TTMiscConfig.MODIFY_ADVANCEMENTS));
		category.addEntry(booleanEntry(builder, "distorted_catacombs_music", TTMiscConfig.DISTORTED_CATACOMBS_MUSIC));
	}

	private TTMiscConfigGui() {}
}
