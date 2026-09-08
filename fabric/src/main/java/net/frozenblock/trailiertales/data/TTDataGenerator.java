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

package net.frozenblock.trailiertales.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.FrozenLibEarlyConstants;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.data.advancement.TTAdvancementProvider;
import net.frozenblock.trailiertales.data.loot.TTArchaeologyLootProvider;
import net.frozenblock.trailiertales.data.loot.TTBlockLootProvider;
import net.frozenblock.trailiertales.data.loot.TTChestLootProvider;
import net.frozenblock.trailiertales.data.loot.TTEntityLootProvider;
import net.frozenblock.trailiertales.data.model.TTModelProvider;
import net.frozenblock.trailiertales.data.recipe.TTBrewingRecipeProvider;
import net.frozenblock.trailiertales.data.recipe.TTRecipeProvider;
import net.frozenblock.trailiertales.data.sound.TTSoundTypeOverrides;
import net.frozenblock.trailiertales.data.tag.TTBiomeTagsProvider;
import net.frozenblock.trailiertales.data.tag.TTBlockTagsProvider;
import net.frozenblock.trailiertales.data.tag.TTDamageTypeTagsProvider;
import net.frozenblock.trailiertales.data.tag.TTEnchantmentsTagProvider;
import net.frozenblock.trailiertales.data.tag.TTEntityTagsProvider;
import net.frozenblock.trailiertales.data.tag.TTFeatureTagsProvider;
import net.frozenblock.trailiertales.data.tag.TTGameEventTagsProvider;
import net.frozenblock.trailiertales.data.tag.TTItemTagsProvider;
import net.frozenblock.trailiertales.data.tag.TTStructureTagsProvider;
import net.frozenblock.trailiertales.data.tag.TTVillagerTradesTagsProvider;
import net.frozenblock.trailiertales.data.trading.TTVillagerTrades;
import net.frozenblock.trailiertales.data.worldgen.feature.TTFeatureBootstrap;
import net.frozenblock.trailiertales.data.worldgen.structure.TTStructureProcessorListAdditions;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTClipGroups;
import net.frozenblock.trailiertales.registry.TTDecoratedPotPatterns;
import net.frozenblock.trailiertales.registry.TTEnchantments;
import net.frozenblock.trailiertales.registry.TTJukeboxSongs;
import net.frozenblock.trailiertales.registry.TTStructures;
import net.frozenblock.trailiertales.registry.TTTrimPatterns;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Blocks;

public final class TTDataGenerator implements DataGeneratorEntrypoint {
	public static BlockFamily FAMILY_CALCITE;

	static {
		if (FrozenLibEarlyConstants.IS_DATAGEN) {
			FAMILY_CALCITE = BlockFamilies.familyBuilder(Blocks.CALCITE)
				.stairs(TTBlocks.CALCITE_STAIRS.get())
				.slab(TTBlocks.CALCITE_SLAB.get())
				.wall(TTBlocks.CALCITE_WALL.get())
				.polished(TTBlocks.POLISHED_CALCITE.get())
				.getFamily();
		}
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		BlockFamilies.SMOOTH_SANDSTONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.SMOOTH_SANDSTONE_WALL.get());
		BlockFamilies.CUT_SANDSTONE.variants.put(BlockFamily.Variant.STAIRS, TTBlocks.CUT_SANDSTONE_STAIRS.get());
		BlockFamilies.CUT_SANDSTONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.CUT_SANDSTONE_WALL.get());

		BlockFamilies.SMOOTH_RED_SANDSTONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.SMOOTH_RED_SANDSTONE_WALL.get());
		BlockFamilies.CUT_RED_SANDSTONE.variants.put(BlockFamily.Variant.STAIRS, TTBlocks.CUT_RED_SANDSTONE_STAIRS.get());
		BlockFamilies.CUT_RED_SANDSTONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.CUT_RED_SANDSTONE_WALL.get());

		BlockFamilies.PRISMARINE_BRICKS.variants.put(BlockFamily.Variant.WALL, TTBlocks.PRISMARINE_BRICK_WALL.get());

		BlockFamilies.DARK_PRISMARINE.variants.put(BlockFamily.Variant.WALL, TTBlocks.DARK_PRISMARINE_WALL.get());

		BlockFamilies.END_STONE.variants.put(BlockFamily.Variant.STAIRS, TTBlocks.END_STONE_STAIRS.get());
		BlockFamilies.END_STONE.variants.put(BlockFamily.Variant.SLAB, TTBlocks.END_STONE_SLAB.get());
		BlockFamilies.END_STONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.END_STONE_WALL.get());

		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.CRACKED, TTBlocks.CRACKED_PURPUR_BLOCK.get());
		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.CHISELED, TTBlocks.CHISELED_PURPUR_BLOCK.get());
		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.WALL, TTBlocks.PURPUR_WALL.get());
		BlockFamilies.PURPUR.generateCraftingRecipe = true;

		BlockFamilies.STONE.variants.put(BlockFamily.Variant.WALL, TTBlocks.STONE_WALL.get());

		BlockFamilies.POLISHED_GRANITE.variants.put(BlockFamily.Variant.WALL, TTBlocks.POLISHED_GRANITE_WALL.get());
		BlockFamilies.POLISHED_DIORITE.variants.put(BlockFamily.Variant.WALL, TTBlocks.POLISHED_DIORITE_WALL.get());
		BlockFamilies.POLISHED_ANDESITE.variants.put(BlockFamily.Variant.WALL, TTBlocks.POLISHED_ANDESITE_WALL.get());

		FeatureFlagApi.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS
		if (FrozenLibEarlyConstants.IS_DATAGEN) pack.addProvider(TTModelProvider::new);

		// DATA
		pack.addProvider(TTEntityLootProvider::new);
		pack.addProvider(TTBlockLootProvider::new);
		pack.addProvider(TTChestLootProvider::new);
		pack.addProvider(TTArchaeologyLootProvider::new);
		pack.addProvider(TTRegistryProvider::new);
		pack.addProvider(TTBiomeTagsProvider::new);
		pack.addProvider(TTBlockTagsProvider::new);
		pack.addProvider(TTDamageTypeTagsProvider::new);
		pack.addProvider(TTItemTagsProvider::new);
		pack.addProvider(TTEntityTagsProvider::new);
		pack.addProvider(TTFeatureTagsProvider::new);
		pack.addProvider(TTGameEventTagsProvider::new);
		pack.addProvider(TTVillagerTradesTagsProvider::new);
		pack.addProvider(TTStructureTagsProvider::new);
		pack.addProvider(TTRecipeProvider::new);
		pack.addProvider(TTBrewingRecipeProvider::new);
		pack.addProvider(TTAdvancementProvider::new);
		pack.addProvider(TTEnchantmentsTagProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		TTConstants.log("Generating dynamic registries for Trailier Tales", TTConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.FEATURE, TTFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, TTFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.PROCESSOR_LIST, TTStructures::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, TTStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, TTStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, TTStructures::bootstrapStructureSet);
		registryBuilder.add(Registries.ENCHANTMENT, TTEnchantments::bootstrap);
		registryBuilder.add(Registries.JUKEBOX_SONG, TTJukeboxSongs::bootstrap);
		registryBuilder.add(Registries.DECORATED_POT_PATTERN, TTDecoratedPotPatterns::bootstrap);
		registryBuilder.add(Registries.TRIM_PATTERN, TTTrimPatterns::bootstrap);
		registryBuilder.add(Registries.VILLAGER_TRADE, TTVillagerTrades::bootstrap);

		// FrozenLib Registries
		registryBuilder.add(FrozenLibRegistries.SOUND_TYPE_OVERRIDE, TTSoundTypeOverrides::bootstrap);
		registryBuilder.add(FrozenLibRegistries.CLIP_GROUP, TTClipGroups::bootstrap);
		registryBuilder.add(FrozenLibRegistries.STRUCTURE_PROCESSOR_LIST_ADDITION, TTStructureProcessorListAdditions::bootstrap);
	}

	@Override
	public String getEffectiveModId() {
		return TTConstants.MOD_ID;
	}
}
