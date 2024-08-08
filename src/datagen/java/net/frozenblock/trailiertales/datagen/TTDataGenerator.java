package net.frozenblock.trailiertales.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.datagen.advancement.TTAdvancementProvider;
import net.frozenblock.trailiertales.datagen.loot.TTArchaeologyLootProvider;
import net.frozenblock.trailiertales.datagen.loot.TTBlockLootProvider;
import net.frozenblock.trailiertales.datagen.loot.TTChestLootProvider;
import net.frozenblock.trailiertales.datagen.loot.TTEntityLootProvider;
import net.frozenblock.trailiertales.datagen.recipe.TTRecipeProvider;
import net.frozenblock.trailiertales.datagen.tag.TTBiomeTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTBlockTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTDamageTypeTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTEnchantmentTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTEntityTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTGameEventTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTItemTagProvider;
import net.frozenblock.trailiertales.datagen.tag.TTStructureTagProvider;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterEnchantments;
import net.frozenblock.trailiertales.registry.RegisterJukeboxSongs;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.worldgen.TrailierFeatureBootstrap;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class TTDataGenerator implements DataGeneratorEntrypoint {
	static final BlockFamily FAMILY_CALCITE = BlockFamilies.familyBuilder(Blocks.CALCITE)
		.stairs(RegisterBlocks.CALCITE_STAIRS)
		.slab(RegisterBlocks.CALCITE_SLAB)
		.wall(RegisterBlocks.CALCITE_WALL)
		.polished(RegisterBlocks.POLISHED_CALCITE)
		.getFamily();

	static final BlockFamily FAMILY_END_STONE = BlockFamilies.familyBuilder(Blocks.END_STONE)
		.stairs(RegisterBlocks.END_STONE_STAIRS)
		.slab(RegisterBlocks.END_STONE_SLAB)
		.wall(RegisterBlocks.END_STONE_WALL)
		.getFamily();

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		BlockFamilies.SMOOTH_SANDSTONE.variants.put(BlockFamily.Variant.WALL, RegisterBlocks.SMOOTH_SANDSTONE_WALL);
		BlockFamilies.CUT_SANDSTONE.variants.put(BlockFamily.Variant.STAIRS, RegisterBlocks.CUT_SANDSTONE_STAIRS);
		BlockFamilies.CUT_SANDSTONE.variants.put(BlockFamily.Variant.WALL, RegisterBlocks.CUT_SANDSTONE_WALL);

		BlockFamilies.SMOOTH_RED_SANDSTONE.variants.put(BlockFamily.Variant.WALL, RegisterBlocks.SMOOTH_RED_SANDSTONE_WALL);
		BlockFamilies.CUT_RED_SANDSTONE.variants.put(BlockFamily.Variant.STAIRS, RegisterBlocks.CUT_RED_SANDSTONE_STAIRS);
		BlockFamilies.CUT_RED_SANDSTONE.variants.put(BlockFamily.Variant.WALL, RegisterBlocks.CUT_RED_SANDSTONE_WALL);

		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.CRACKED, RegisterBlocks.CRACKED_PURPUR_BLOCK);
		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.CHISELED, RegisterBlocks.CHISELED_PURPUR_BLOCK);
		BlockFamilies.PURPUR.variants.put(BlockFamily.Variant.WALL, RegisterBlocks.PURPUR_WALL);
		BlockFamilies.PURPUR.generateRecipe = true;

		BlockFamilies.POLISHED_GRANITE.variants.put(BlockFamily.Variant.WALL, RegisterBlocks.POLISHED_GRANITE_WALL);
		BlockFamilies.POLISHED_DIORITE.variants.put(BlockFamily.Variant.WALL, RegisterBlocks.POLISHED_DIORITE_WALL);
		BlockFamilies.POLISHED_ANDESITE.variants.put(BlockFamily.Variant.WALL, RegisterBlocks.POLISHED_ANDESITE_WALL);

		FrozenFeatureFlags.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS

		pack.addProvider(TTModelProvider::new);

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
		TrailierConstants.log("Building datagen registries for Trailier Tales", TrailierConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.CONFIGURED_FEATURE, TrailierFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, TrailierFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.PROCESSOR_LIST, RegisterStructures::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, RegisterStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, RegisterStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, RegisterStructures::bootstrapStructureSet);
		registryBuilder.add(Registries.ENCHANTMENT, RegisterEnchantments::bootstrap);
		registryBuilder.add(Registries.JUKEBOX_SONG, RegisterJukeboxSongs::bootstrap);
	}

}
