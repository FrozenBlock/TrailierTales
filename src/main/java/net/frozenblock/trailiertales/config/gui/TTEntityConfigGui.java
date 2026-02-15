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
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.minecraft.world.entity.EntityType;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.booleanEntry;
import static net.frozenblock.trailiertales.config.gui.TTConfigGuiHelper.entitySpawnEntry;
import static net.frozenblock.wilderwild.WWConstants.tooltip;

@Environment(EnvType.CLIENT)
public final class TTEntityConfigGui {

	private TTEntityConfigGui() {
		throw new UnsupportedOperationException("TTEntityConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		// APPARITION
		var apparitionPicksUpItems = booleanEntry(entryBuilder, "picks_up_items", TTEntityConfig.APPARITION_PICKS_UP_ITEMS);
		var apparitionCatchesProjectiles = booleanEntry(entryBuilder, "catches_projectiles", TTEntityConfig.APPARITION_CATCHES_PROJECTILES);
		var apparitionIgnoresMobGriefing = booleanEntry(entryBuilder, "ignore_mob_griefing", TTEntityConfig.APPARITION_IGNORES_MOB_GRIEFING);
		var apparitionHypnotizesMobs = booleanEntry(entryBuilder, "hypnotizes_mobs", TTEntityConfig.APPARITION_HYPNOTIZES_MOBS);
		var apparitionHauntsPlayers = booleanEntry(entryBuilder, "haunts_players", TTEntityConfig.APPARITION_HAUNTS_PLAYERS);
		var hauntedCoffins = booleanEntry(entryBuilder, "haunted_coffins", TTEntityConfig.APPARITION_HAUNTED_COFFINS);
		var hauntedFog = booleanEntry(entryBuilder, "haunted_fog", TTEntityConfig.APPARITION_HAUNTED_FOG);
		var hauntedLightmap = booleanEntry(entryBuilder, "haunted_lightmap", TTEntityConfig.APPARITION_HAUNTED_LIGHTMAP);
		var hauntedSounds = booleanEntry(entryBuilder, "haunted_sounds", TTEntityConfig.APPARITION_HAUNTED_SOUNDS);
		var hauntedHUD = booleanEntry(entryBuilder, "haunted_hud", TTEntityConfig.APPARITION_HAUNTED_HUD);

		FrozenClothConfig.createSubCategory(entryBuilder, category, TTEntityTypes.APPARITION.getDescription(),
			false,
			tooltip("entity_category", TTEntityTypes.APPARITION.getDescription()),
			apparitionPicksUpItems, apparitionCatchesProjectiles, apparitionIgnoresMobGriefing, apparitionHypnotizesMobs,
			apparitionHauntsPlayers, hauntedCoffins, hauntedFog, hauntedLightmap, hauntedSounds, hauntedHUD
		);

		// SNIFFER
		var snifferDigsCyanRoseSeeds = booleanEntry(entryBuilder, "sniffer_digs_cyan_rose_seeds", TTEntityConfig.SNIFFER_DIGS_CYAN_ROSE_SEEDS);
		var snifferDigsManedropGerms = booleanEntry(entryBuilder, "sniffer_digs_manedrop_germs", TTEntityConfig.SNIFFER_DIGS_MANEDROP_GERMS);
		var snifferDigsGuzmaniaSeeds = booleanEntry(entryBuilder, "sniffer_digs_guzmania_seeds", TTEntityConfig.SNIFFER_DIGS_GUZMANIA_SEEDS);
		var sniffersDigDawntrailSeeds = booleanEntry(entryBuilder, "sniffer_digs_dawntrail_seeds", TTEntityConfig.SNIFFER_DIGS_DAWNTRAIL_SEEDS);
		var sniffersDigLithopsSeeds = booleanEntry(entryBuilder, "sniffer_digs_lithops_seeds", TTEntityConfig.SNIFFER_DIGS_LITHOPS_SEEDS);
		var spawnSniffer = entitySpawnEntry(entryBuilder, EntityType.SNIFFER, TTEntityConfig.SPAWN_SNIFFERS);

		FrozenClothConfig.createSubCategory(entryBuilder, category, EntityType.SNIFFER.getDescription(),
			false,
			tooltip("entity_category", EntityType.SNIFFER.getDescription()),
			snifferDigsCyanRoseSeeds, snifferDigsManedropGerms, snifferDigsGuzmaniaSeeds, sniffersDigLithopsSeeds, sniffersDigDawntrailSeeds,
			spawnSniffer
		);

		// VILLAGER
		var villagersSellCatacombsMap = booleanEntry(entryBuilder, "sell_catacombs_map", TTEntityConfig.VILLAGER_SELLS_CATACOMBS_MAP);

		FrozenClothConfig.createSubCategory(entryBuilder, category, EntityType.VILLAGER.getDescription(),
			false,
			tooltip("entity_category", EntityType.VILLAGER.getDescription()),
			villagersSellCatacombsMap
		);

		// ARMOR STAND
		var armorStandArms = booleanEntry(entryBuilder, "armor_stand_arms", TTEntityConfig.ARMOR_STAND_HAS_ARMS);

		FrozenClothConfig.createSubCategory(entryBuilder, category, EntityType.ARMOR_STAND.getDescription(),
			false,
			tooltip("entity_category", EntityType.ARMOR_STAND.getDescription()),
			armorStandArms
		);
	}
}
