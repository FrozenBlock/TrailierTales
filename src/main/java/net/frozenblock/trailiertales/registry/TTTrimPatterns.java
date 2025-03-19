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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import org.jetbrains.annotations.NotNull;

public final class TTTrimPatterns {
	public static final ResourceKey<TrimPattern> UNDEAD = create("undead");
	public static final ResourceKey<TrimPattern> MATRIX = create("matrix");
	public static final ResourceKey<TrimPattern> GEODE = create("geode");
	public static final ResourceKey<TrimPattern> OVERGROWTH = create("overgrowth");
	public static final ResourceKey<TrimPattern> MARTYR = create("martyr");
	public static final ResourceKey<TrimPattern> ZEPHYR = create("zephyr");
	public static final ResourceKey<TrimPattern> COT = create("cot");
	public static final ResourceKey<TrimPattern> EMBRACE = create("embrace");

	public static void init() {
	}

	private static @NotNull ResourceKey<TrimPattern> create(String path) {
		return ResourceKey.create(Registries.TRIM_PATTERN, TTConstants.id(path));
	}

	public static void bootstrap(BootstrapContext<TrimPattern> context) {
		// TODO 1.21.5 convert these
		TrimPatterns.register(context, /*TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE,*/ UNDEAD);
		TrimPatterns.register(context, /*TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE,*/ MATRIX);
		TrimPatterns.register(context, /*TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE,*/ GEODE);
		TrimPatterns.register(context, /*TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE,*/ OVERGROWTH);
		TrimPatterns.register(context, /*TTItems.MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE,*/ MARTYR);
		TrimPatterns.register(context, /*TTItems.ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE,*/ ZEPHYR);
		TrimPatterns.register(context, /*TTItems.COT_ARMOR_TRIM_SMITHING_TEMPLATE,*/ COT);
		TrimPatterns.register(context, /*TTItems.EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE,*/ EMBRACE);
	}
}
