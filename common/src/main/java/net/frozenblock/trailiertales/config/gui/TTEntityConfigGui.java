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
import net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper;
import net.frozenblock.trailiertales.TTConstants;
import static net.frozenblock.trailiertales.TTConstants.tooltip;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.booleanEntry;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.entitySpawnEntry;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.world.entity.EntityTypes;

@ClientOnly
public final class TTEntityConfigGui {

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		// APPARITION
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, TTEntityTypes.APPARITION.get().getDescription(),
			false,
			TTConstants.tooltip("entity_category", TTEntityTypes.APPARITION.get().getDescription()),
			booleanEntry(builder, "picks_up_items", TTEntityConfig.APPARITION_PICKS_UP_ITEMS),
			booleanEntry(builder, "catches_projectiles", TTEntityConfig.APPARITION_CATCHES_PROJECTILES),
			booleanEntry(builder, "ignore_mob_griefing", TTEntityConfig.APPARITION_IGNORES_MOB_GRIEFING),
			booleanEntry(builder, "hypnotizes_mobs", TTEntityConfig.APPARITION_HYPNOTIZES_MOBS),
			booleanEntry(builder, "haunts_players", TTEntityConfig.APPARITION_HAUNTS_PLAYERS),
			booleanEntry(builder, "haunted_coffins", TTEntityConfig.APPARITION_HAUNTED_COFFINS),
			booleanEntry(builder, "haunted_fog", TTEntityConfig.APPARITION_HAUNTED_FOG),
			booleanEntry(builder, "haunted_lightmap", TTEntityConfig.APPARITION_HAUNTED_LIGHTMAP),
			booleanEntry(builder, "haunted_sounds", TTEntityConfig.APPARITION_HAUNTED_SOUNDS),
			booleanEntry(builder, "haunted_hud", TTEntityConfig.APPARITION_HAUNTED_HUD)
		);

		// SNIFFER
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, EntityTypes.SNIFFER.getDescription(),
			false,
			tooltip("entity_category", EntityTypes.SNIFFER.getDescription()),
			booleanEntry(builder, "sniffer_digs_cyan_rose_seeds", TTEntityConfig.SNIFFER_DIGS_CYAN_ROSE_SEEDS),
			booleanEntry(builder, "sniffer_digs_manedrop_germs", TTEntityConfig.SNIFFER_DIGS_MANEDROP_GERMS),
			booleanEntry(builder, "sniffer_digs_guzmania_seeds", TTEntityConfig.SNIFFER_DIGS_GUZMANIA_SEEDS),
			booleanEntry(builder, "sniffer_digs_dawntrail_seeds", TTEntityConfig.SNIFFER_DIGS_DAWNTRAIL_SEEDS),
			booleanEntry(builder, "sniffer_digs_lithops_seeds", TTEntityConfig.SNIFFER_DIGS_LITHOPS_SEEDS),
			entitySpawnEntry(builder, EntityTypes.SNIFFER, TTEntityConfig.SPAWN_SNIFFERS)
		);

		// VILLAGER
		// TODO: config currently does nothing
		/*
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, EntityTypes.VILLAGER.getDescription(),
			false,
			tooltip("entity_category", EntityTypes.VILLAGER.getDescription()),
			booleanEntry(builder, "sell_catacombs_map", TTEntityConfig.VILLAGER_SELLS_CATACOMBS_MAP)
		);
		 */

		// ARMOR STAND
		FrozenLibClothConfigGuiHelper.createSubCategory(builder, category, EntityTypes.ARMOR_STAND.getDescription(),
			false,
			tooltip("entity_category", EntityTypes.ARMOR_STAND.getDescription()),
			booleanEntry(builder, "armor_stand_arms", TTEntityConfig.ARMOR_STAND_HAS_ARMS)
		);
	}

	private TTEntityConfigGui() {}
}
