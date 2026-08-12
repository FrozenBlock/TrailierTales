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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.booleanEntry;

@Environment(EnvType.CLIENT)
public final class TTMiscConfigGui {

	private TTMiscConfigGui() {
		throw new UnsupportedOperationException("TTMiscConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		category.addEntry(booleanEntry(entryBuilder, "modify_advancements", TTMiscConfig.MODIFY_ADVANCEMENTS));
		category.addEntry(booleanEntry(entryBuilder, "distorted_catacombs_music", TTMiscConfig.DISTORTED_CATACOMBS_MUSIC));
	}
}
