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

package net.frozenblock.trailiertales.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.datagen.advancement.TTAdvancementProvider;
import net.frozenblock.trailiertales.datagen.loot.TTArchaeologyLootProvider;
import net.frozenblock.trailiertales.datagen.loot.TTBlockLootProvider;
import net.frozenblock.trailiertales.datagen.loot.TTChestLootProvider;
import net.frozenblock.trailiertales.datagen.loot.TTEntityLootProvider;
import net.frozenblock.trailiertales.datagen.model.TTModelProvider;
import net.frozenblock.trailiertales.datagen.recipe.TTRecipeProvider;
import net.frozenblock.trailiertales.datagen.tag.TTBiomeTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTBlockTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTDamageTypeTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTEnchantmentTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTEntityTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTGameEventTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTItemTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTStructureTagProvider;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEnchantments;
import net.frozenblock.trailiertales.registry.TTJukeboxSongs;
import net.frozenblock.trailiertales.registry.TTStructures;
import net.frozenblock.trailiertales.registry.TTTrimPatterns;
import net.frozenblock.trailiertales.worldgen.TTFeatureBootstrap;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class TTDataGenerator implements DataGeneratorEntrypoint {
	public static BlockFamily FAMILY_CALCITE;
	public static BlockFamily FAMILY_END_STONE;

	static {
		if (FrozenBools.IS_DATAGEN) {
			FAMILY_CALCITE = BlockFamilies.familyBuilder(Blocks.CALCITE)
				.stairs(TTBlocks.CALCITE_STAIRS)
				.slab(TTBlocks.CALCITE_SLAB)
				.wall(TTBlocks.CALCITE_WALL)
				.polished(TTBlocks.POLISHED_CALCITE)
				.getFamily();

			FAMILY_END_STONE = BlockFamilies.familyBuilder(Blocks.END_STONE)
				.stairs(TTBlocks.END_STONE_STAIRS)
				.slab(TTBlocks.END_STONE_SLAB)
				.wall(TTBlocks.END_STONE_WALL)
				.getFamily();
		}
	}

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		BlockFamilies.SMOOTH_SANDSTONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.SMOOTH_SANDSTONE_WALL);
		BlockFamilies.CUT_SANDSTONE.variants.put(BlockFamily.Variant.STAIRS, TTBlocks.CUT_SANDSTONE_STAIRS);
		BlockFamilies.CUT_SANDSTONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.CUT_SANDSTONE_WALL);

		BlockFamilies.SMOOTH_RED_SANDSTONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.SMOOTH_RED_SANDSTONE_WALL);
		BlockFamilies.CUT_RED_SANDSTONE.variants.put(BlockFamily.Variant.STAIRS, TTBlocks.CUT_RED_SANDSTONE_STAIRS);
		BlockFamilies.CUT_RED_SANDSTONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.CUT_RED_SANDSTONE_WALL);

		BlockFamilies.PRISMARINE_BRICKS.variants.put(BlockFamily.Variant.WALL, TTBlocks.PRISMARINE_BRICK_WALL);

		BlockFamilies.DARK_PRISMARINE.variants.put(BlockFamily.Variant.WALL, TTBlocks.DARK_PRISMARINE_WALL);

		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.CRACKED, TTBlocks.CRACKED_PURPUR_BLOCK);
		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.CHISELED, TTBlocks.CHISELED_PURPUR_BLOCK);
		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.WALL, TTBlocks.PURPUR_WALL);
		BlockFamilies.PURPUR.generateRecipe = true;

		BlockFamilies.POLISHED_GRANITE.variants.put(BlockFamily.Variant.WALL, TTBlocks.POLISHED_GRANITE_WALL);
		BlockFamilies.POLISHED_DIORITE.variants.put(BlockFamily.Variant.WALL, TTBlocks.POLISHED_DIORITE_WALL);
		BlockFamilies.POLISHED_ANDESITE.variants.put(BlockFamily.Variant.WALL, TTBlocks.POLISHED_ANDESITE_WALL);

		FeatureFlagApi.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS

		if (FrozenBools.IS_DATAGEN) pack.addProvider(TTModelProvider::new);

		// DATA

		pack.addProvider(TTEntityLootProvider::new);
		pack.addProvider(TTBlockLootProvider::new);
		pack.addProvider(TTChestLootProvider::new);
		pack.addProvider(TTArchaeologyLootProvider::new);
		pack.addProvider(TTRegistryProvider::new);
		pack.addProvider(TTBiomeTagProvider::new);
		pack.addProvider(TTBlockTagProvider::new);
		pack.addProvider(TTDamageTypeTagProvider::new);
		pack.addProvider(TTItemTagProvider::new);
		pack.addProvider(TTEntityTagProvider::new);
		pack.addProvider(TTGameEventTagProvider::new);
		pack.addProvider(TTStructureTagProvider::new);
		pack.addProvider(TTRecipeProvider::new);
		pack.addProvider(TTAdvancementProvider::new);
		pack.addProvider(TTEnchantmentTagProvider::new);
	}

	@Override
	public void buildRegistry(@NotNull RegistrySetBuilder registryBuilder) {
		TTConstants.log("Building datagen registries for Trailier Tales", TTConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.CONFIGURED_FEATURE, TTFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, TTFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.PROCESSOR_LIST, TTStructures::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, TTStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, TTStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, TTStructures::bootstrapStructureSet);
		registryBuilder.add(Registries.ENCHANTMENT, TTEnchantments::bootstrap);
		registryBuilder.add(Registries.JUKEBOX_SONG, TTJukeboxSongs::bootstrap);
		registryBuilder.add(Registries.TRIM_PATTERN, TTTrimPatterns::bootstrap);
	}

}
