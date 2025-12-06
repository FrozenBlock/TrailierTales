/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class TTBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public TTBlockTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(TTBlocks.CHISELED_GRANITE_BRICKS)
			.add(TTBlocks.GRANITE_BRICKS)
			.add(TTBlocks.CRACKED_GRANITE_BRICKS)
			.add(TTBlocks.GRANITE_BRICK_STAIRS)
			.add(TTBlocks.GRANITE_BRICK_SLAB)
			.add(TTBlocks.GRANITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_GRANITE_BRICKS)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_GRANITE_WALL)

			.add(TTBlocks.CHISELED_DIORITE_BRICKS)
			.add(TTBlocks.DIORITE_BRICKS)
			.add(TTBlocks.CRACKED_DIORITE_BRICKS)
			.add(TTBlocks.DIORITE_BRICK_STAIRS)
			.add(TTBlocks.DIORITE_BRICK_SLAB)
			.add(TTBlocks.DIORITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DIORITE_BRICKS)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_DIORITE_WALL)

			.add(TTBlocks.CHISELED_ANDESITE_BRICKS)
			.add(TTBlocks.ANDESITE_BRICKS)
			.add(TTBlocks.CRACKED_ANDESITE_BRICKS)
			.add(TTBlocks.ANDESITE_BRICK_STAIRS)
			.add(TTBlocks.ANDESITE_BRICK_SLAB)
			.add(TTBlocks.ANDESITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_ANDESITE_BRICKS)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_ANDESITE_WALL)

			.add(TTBlocks.CALCITE_STAIRS)
			.add(TTBlocks.CALCITE_SLAB)
			.add(TTBlocks.CALCITE_WALL)
			.add(TTBlocks.POLISHED_CALCITE)
			.add(TTBlocks.POLISHED_CALCITE_STAIRS)
			.add(TTBlocks.POLISHED_CALCITE_SLAB)
			.add(TTBlocks.POLISHED_CALCITE_WALL)
			.add(TTBlocks.CHISELED_CALCITE_BRICKS)
			.add(TTBlocks.CALCITE_BRICKS)
			.add(TTBlocks.CRACKED_CALCITE_BRICKS)
			.add(TTBlocks.CALCITE_BRICK_STAIRS)
			.add(TTBlocks.CALCITE_BRICK_SLAB)
			.add(TTBlocks.CALCITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_CALCITE_BRICKS)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_WALL)

			.add(TTBlocks.CRACKED_TUFF_BRICKS)
			.add(TTBlocks.MOSSY_TUFF_BRICKS)
			.add(TTBlocks.MOSSY_TUFF_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_TUFF_BRICK_SLAB)
			.add(TTBlocks.MOSSY_TUFF_BRICK_WALL)

			.add(TTBlocks.CRACKED_BRICKS)
			.add(TTBlocks.MOSSY_BRICKS)
			.add(TTBlocks.MOSSY_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_BRICK_SLAB)
			.add(TTBlocks.MOSSY_BRICK_WALL)

			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILES)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL)

			.add(TTBlocks.SMOOTH_SANDSTONE_WALL)
			.add(TTBlocks.CUT_SANDSTONE_WALL)
			.add(TTBlocks.CUT_SANDSTONE_STAIRS)

			.add(TTBlocks.SMOOTH_RED_SANDSTONE_WALL)
			.add(TTBlocks.CUT_RED_SANDSTONE_WALL)
			.add(TTBlocks.CUT_RED_SANDSTONE_STAIRS)

			.add(TTBlocks.PRISMARINE_BRICK_WALL)
			.add(TTBlocks.DARK_PRISMARINE_WALL)

			.add(TTBlocks.CHORAL_END_STONE)
			.add(TTBlocks.CHORAL_END_STONE_STAIRS)
			.add(TTBlocks.CHORAL_END_STONE_SLAB)
			.add(TTBlocks.CHORAL_END_STONE_WALL)
			.add(TTBlocks.END_STONE_STAIRS)
			.add(TTBlocks.END_STONE_SLAB)
			.add(TTBlocks.END_STONE_WALL)

			.add(TTBlocks.CRACKED_END_STONE_BRICKS)
			.add(TTBlocks.CHISELED_END_STONE_BRICKS)
			.add(TTBlocks.CHORAL_END_STONE_BRICKS)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_SLAB)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_WALL)

			.add(TTBlocks.CRACKED_PURPUR_BLOCK)
			.add(TTBlocks.CHISELED_PURPUR_BLOCK)
			.add(TTBlocks.PURPUR_WALL)

			.add(TTBlocks.SURVEYOR);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(TTBlocks.SUSPICIOUS_RED_SAND)
			.add(TTBlocks.SUSPICIOUS_DIRT)
			.add(TTBlocks.SUSPICIOUS_CLAY);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE)
			.add(TTBlocks.LITHOPS);

		this.getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT)
			.add(TTBlocks.LITHOPS);

		this.getOrCreateTagBuilder(BlockTags.SAND)
			.add(TTBlocks.SUSPICIOUS_RED_SAND);

		this.getOrCreateTagBuilder(BlockTags.DIRT)
			.add(TTBlocks.SUSPICIOUS_DIRT);

		this.getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
			.add(TTBlocks.CYAN_ROSE);

		this.getOrCreateTagBuilder(BlockTags.TALL_FLOWERS)
			.add(TTBlocks.MANEDROP)
			.add(TTBlocks.GUZMANIA);

		this.getOrCreateTagBuilder(BlockTags.FLOWERS)
			.add(TTBlocks.DAWNTRAIL);

		this.getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
			.add(TTBlocks.POTTED_CYAN_ROSE)
			.add(TTBlocks.POTTED_LITHOPS);

		this.getOrCreateTagBuilder(BlockTags.CROPS)
			.add(TTBlocks.CYAN_ROSE_CROP)
			.add(TTBlocks.MANEDROP_CROP)
			.add(TTBlocks.GUZMANIA_CROP)
			.add(TTBlocks.DAWNTRAIL_CROP)
			.add(TTBlocks.LITHOPS_CROP);

		this.getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND)
			.add(TTBlocks.CYAN_ROSE_CROP)
			.add(TTBlocks.CYAN_ROSE)
			.add(TTBlocks.MANEDROP_CROP)
			.add(TTBlocks.GUZMANIA_CROP)
			.add(TTBlocks.DAWNTRAIL_CROP)
			.add(TTBlocks.DAWNTRAIL)
			.add(TTBlocks.LITHOPS_CROP)
			.add(TTBlocks.LITHOPS);

		this.getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
			.add(TTBlocks.DAWNTRAIL)
			.add(TTBlocks.LITHOPS);

		this.getOrCreateTagBuilder(BlockTags.REPLACEABLE_BY_TREES)
			.add(TTBlocks.DAWNTRAIL)
			.add(TTBlocks.LITHOPS);

		this.getOrCreateTagBuilder(BlockTags.STAIRS)
			.add(TTBlocks.GRANITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS)
			.add(TTBlocks.DIORITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS)
			.add(TTBlocks.ANDESITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS)
			.add(TTBlocks.CALCITE_STAIRS)
			.add(TTBlocks.POLISHED_CALCITE_STAIRS)
			.add(TTBlocks.CALCITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_TUFF_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlocks.CUT_SANDSTONE_STAIRS)
			.add(TTBlocks.CUT_RED_SANDSTONE_STAIRS)
			.add(TTBlocks.CHORAL_END_STONE_STAIRS)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS)
			.add(TTBlocks.END_STONE_STAIRS);

		this.getOrCreateTagBuilder(BlockTags.SLABS)
			.add(TTBlocks.GRANITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_SLAB)
			.add(TTBlocks.DIORITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_SLAB)
			.add(TTBlocks.ANDESITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB)
			.add(TTBlocks.CALCITE_SLAB)
			.add(TTBlocks.POLISHED_CALCITE_SLAB)
			.add(TTBlocks.CALCITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_TUFF_BRICK_SLAB)
			.add(TTBlocks.MOSSY_BRICK_SLAB)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlocks.CHORAL_END_STONE_SLAB)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_SLAB)
			.add(TTBlocks.END_STONE_SLAB);

		this.getOrCreateTagBuilder(BlockTags.WALLS)
			.add(TTBlocks.GRANITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_GRANITE_WALL)
			.add(TTBlocks.DIORITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_DIORITE_WALL)
			.add(TTBlocks.ANDESITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_ANDESITE_WALL)
			.add(TTBlocks.CALCITE_WALL)
			.add(TTBlocks.POLISHED_CALCITE_WALL)
			.add(TTBlocks.CALCITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_TUFF_BRICK_WALL)
			.add(TTBlocks.MOSSY_BRICK_WALL)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL)
			.add(TTBlocks.SMOOTH_SANDSTONE_WALL)
			.add(TTBlocks.CUT_SANDSTONE_WALL)
			.add(TTBlocks.SMOOTH_RED_SANDSTONE_WALL)
			.add(TTBlocks.CUT_RED_SANDSTONE_WALL)
			.add(TTBlocks.PRISMARINE_BRICK_WALL)
			.add(TTBlocks.DARK_PRISMARINE_WALL)
			.add(TTBlocks.CHORAL_END_STONE_WALL)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_WALL)
			.add(TTBlocks.END_STONE_WALL)
			.add(TTBlocks.PURPUR_WALL);

		this.getOrCreateTagBuilder(BlockTags.ANCIENT_CITY_REPLACEABLE)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILES)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);

		this.getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILES);

		this.getOrCreateTagBuilder(TTBlockTags.CAMEL_SPAWNABLE_ON)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT);

		this.getOrCreateTagBuilder(TTBlockTags.COFFIN_UNSPAWNABLE_ON)
			.add(Blocks.REDSTONE_WIRE)
			.add(Blocks.REDSTONE_TORCH)
			.add(Blocks.REDSTONE_WALL_TORCH)
			.add(Blocks.REDSTONE_BLOCK)
			.add(Blocks.REDSTONE_LAMP)
			.add(Blocks.LEVER)
			.add(Blocks.REPEATER)
			.add(Blocks.COMPARATOR)
			.addOptionalTag(BlockTags.BUTTONS)
			.addOptionalTag(BlockTags.PRESSURE_PLATES);

		this.getOrCreateTagBuilder(BlockTags.FEATURES_CANNOT_REPLACE)
			.add(TTBlocks.COFFIN);

		this.getOrCreateTagBuilder(BlockTags.IMPERMEABLE)
			.add(TTBlocks.ECTOPLASM_BLOCK);

		this.getOrCreateTagBuilder(TTBlockTags.SURVEYOR_CAN_SEE_THROUGH)
			.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
			.addOptionalTag(ConventionalBlockTags.GLASS_PANES);

		this.getOrCreateTagBuilder(TTBlockTags.SURVEYOR_CANNOT_SEE_THROUGH)
			.add(Blocks.TINTED_GLASS)
			.addOptional(TTModIntegrations.WILDER_WILD_INTEGRATION.getIntegration().id("echo_glass"));

		this.getOrCreateTagBuilder(TTBlockTags.CAMEL_SPAWNABLE_ON)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT);

		this.getOrCreateTagBuilder(ConventionalBlockTags.UNCOLORED_SANDSTONE_BLOCKS)
			.add(TTBlocks.SMOOTH_SANDSTONE_WALL)
			.add(TTBlocks.CUT_SANDSTONE_WALL)
			.add(TTBlocks.CUT_SANDSTONE_STAIRS);

		this.getOrCreateTagBuilder(ConventionalBlockTags.UNCOLORED_SANDSTONE_STAIRS)
			.add(TTBlocks.CUT_SANDSTONE_STAIRS);

		this.getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_BLOCKS)
			.add(TTBlocks.SMOOTH_RED_SANDSTONE_WALL)
			.add(TTBlocks.CUT_RED_SANDSTONE_WALL)
			.add(TTBlocks.CUT_RED_SANDSTONE_STAIRS);

		this.getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_STAIRS)
			.add(TTBlocks.CUT_RED_SANDSTONE_STAIRS);

		// SOUNDS

		this.getOrCreateTagBuilder(TTBlockTags.SOUND_UNPOLISHED_BRICKS)
			.add(
				Blocks.BRICKS,
				Blocks.BRICK_STAIRS,
				Blocks.BRICK_SLAB,
				Blocks.BRICK_WALL,
				TTBlocks.CRACKED_BRICKS,
				TTBlocks.MOSSY_BRICKS,
				TTBlocks.MOSSY_BRICK_STAIRS,
				TTBlocks.MOSSY_BRICK_SLAB,
				TTBlocks.MOSSY_BRICK_WALL
			).add(
				Blocks.STONE_BRICKS,
				Blocks.STONE_BRICK_STAIRS,
				Blocks.STONE_BRICK_SLAB,
				Blocks.STONE_BRICK_WALL,
				Blocks.CHISELED_STONE_BRICKS,
				Blocks.CRACKED_STONE_BRICKS,
				Blocks.MOSSY_STONE_BRICKS,
				Blocks.MOSSY_STONE_BRICK_STAIRS,
				Blocks.MOSSY_STONE_BRICK_SLAB,
				Blocks.MOSSY_STONE_BRICK_WALL,
				Blocks.INFESTED_STONE_BRICKS,
				Blocks.INFESTED_CHISELED_STONE_BRICKS,
				Blocks.INFESTED_CRACKED_STONE_BRICKS,
				Blocks.INFESTED_MOSSY_STONE_BRICKS
			).add(
				Blocks.PRISMARINE_BRICKS,
				Blocks.PRISMARINE_BRICK_STAIRS,
				Blocks.PRISMARINE_BRICK_SLAB,
				TTBlocks.PRISMARINE_BRICK_WALL
			)
			.add(
				Blocks.END_STONE_BRICKS,
				Blocks.END_STONE_BRICK_STAIRS,
				Blocks.END_STONE_BRICK_SLAB,
				Blocks.END_STONE_BRICK_WALL,
				TTBlocks.CHISELED_END_STONE_BRICKS,
				TTBlocks.CRACKED_END_STONE_BRICKS,
				TTBlocks.CHORAL_END_STONE_BRICKS,
				TTBlocks.CHORAL_END_STONE_BRICK_STAIRS,
				TTBlocks.CHORAL_END_STONE_BRICK_SLAB,
				TTBlocks.CHORAL_END_STONE_BRICK_WALL
			);

		this.getOrCreateTagBuilder(TTBlockTags.SOUND_POLISHED_BRICKS)
			.add(
				TTBlocks.GRANITE_BRICKS,
				TTBlocks.GRANITE_BRICK_STAIRS,
				TTBlocks.GRANITE_BRICK_SLAB,
				TTBlocks.GRANITE_BRICK_WALL,
				TTBlocks.CHISELED_GRANITE_BRICKS,
				TTBlocks.CRACKED_GRANITE_BRICKS,
				TTBlocks.MOSSY_GRANITE_BRICKS,
				TTBlocks.MOSSY_GRANITE_BRICK_STAIRS,
				TTBlocks.MOSSY_GRANITE_BRICK_SLAB,
				TTBlocks.MOSSY_GRANITE_BRICK_WALL
			).add(
				TTBlocks.DIORITE_BRICKS,
				TTBlocks.DIORITE_BRICK_STAIRS,
				TTBlocks.DIORITE_BRICK_SLAB,
				TTBlocks.DIORITE_BRICK_WALL,
				TTBlocks.CHISELED_DIORITE_BRICKS,
				TTBlocks.CRACKED_DIORITE_BRICKS,
				TTBlocks.MOSSY_DIORITE_BRICKS,
				TTBlocks.MOSSY_DIORITE_BRICK_STAIRS,
				TTBlocks.MOSSY_DIORITE_BRICK_SLAB,
				TTBlocks.MOSSY_DIORITE_BRICK_WALL
			).add(
				TTBlocks.ANDESITE_BRICKS,
				TTBlocks.ANDESITE_BRICK_STAIRS,
				TTBlocks.ANDESITE_BRICK_SLAB,
				TTBlocks.ANDESITE_BRICK_WALL,
				TTBlocks.CHISELED_ANDESITE_BRICKS,
				TTBlocks.CRACKED_ANDESITE_BRICKS,
				TTBlocks.MOSSY_ANDESITE_BRICKS,
				TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS,
				TTBlocks.MOSSY_ANDESITE_BRICK_SLAB,
				TTBlocks.MOSSY_ANDESITE_BRICK_WALL
			)
			.add(
				Blocks.POLISHED_BLACKSTONE_BRICKS,
				Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS,
				Blocks.POLISHED_BLACKSTONE_BRICK_SLAB,
				Blocks.POLISHED_BLACKSTONE_BRICK_WALL,
				Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
			);

		this.getOrCreateTagBuilder(TTBlockTags.SOUND_POLISHED_CALCITE)
			.add(
				TTBlocks.POLISHED_CALCITE,
				TTBlocks.POLISHED_CALCITE_SLAB,
				TTBlocks.POLISHED_CALCITE_WALL,
				TTBlocks.POLISHED_CALCITE_STAIRS
			);

		this.getOrCreateTagBuilder(TTBlockTags.SOUND_CALCITE_BRICKS)
			.add(
				TTBlocks.CALCITE_BRICKS,
				TTBlocks.CALCITE_BRICK_STAIRS,
				TTBlocks.CALCITE_BRICK_SLAB,
				TTBlocks.CALCITE_BRICK_WALL,
				TTBlocks.CHISELED_CALCITE_BRICKS,
				TTBlocks.CRACKED_CALCITE_BRICKS,
				TTBlocks.MOSSY_CALCITE_BRICKS,
				TTBlocks.MOSSY_CALCITE_BRICK_STAIRS,
				TTBlocks.MOSSY_CALCITE_BRICK_SLAB,
				TTBlocks.MOSSY_CALCITE_BRICK_WALL
			);

		this.getOrCreateTagBuilder(TTBlockTags.SOUND_POLISHED)
			.add(
				Blocks.POLISHED_ANDESITE,
				Blocks.POLISHED_ANDESITE_SLAB,
				Blocks.POLISHED_ANDESITE_STAIRS,
				TTBlocks.POLISHED_ANDESITE_WALL,
				Blocks.POLISHED_GRANITE,
				Blocks.POLISHED_GRANITE_SLAB,
				Blocks.POLISHED_GRANITE_STAIRS,
				TTBlocks.POLISHED_GRANITE_WALL,
				Blocks.POLISHED_DIORITE,
				Blocks.POLISHED_DIORITE_SLAB,
				Blocks.POLISHED_DIORITE_STAIRS,
				TTBlocks.POLISHED_DIORITE_WALL,
				Blocks.POLISHED_BLACKSTONE,
				Blocks.POLISHED_BLACKSTONE_SLAB,
				Blocks.POLISHED_BLACKSTONE_STAIRS,
				Blocks.POLISHED_BLACKSTONE_WALL,
				Blocks.CHISELED_POLISHED_BLACKSTONE,
				Blocks.POLISHED_BLACKSTONE_BUTTON,
				Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE
			);

		this.getOrCreateTagBuilder(TTBlockTags.SOUND_POLISHED_DEEPSLATE)
			.add(
				Blocks.POLISHED_DEEPSLATE,
				Blocks.POLISHED_DEEPSLATE_SLAB,
				Blocks.POLISHED_DEEPSLATE_WALL,
				Blocks.POLISHED_DEEPSLATE_STAIRS
			);

		this.getOrCreateTagBuilder(TTBlockTags.SOUND_POLISHED_TUFF)
			.add(
				Blocks.POLISHED_TUFF,
				Blocks.POLISHED_TUFF_SLAB,
				Blocks.POLISHED_TUFF_WALL,
				Blocks.POLISHED_TUFF_STAIRS
			);

		this.getOrCreateTagBuilder(TTBlockTags.SOUND_POLISHED_BASALT)
			.add(Blocks.POLISHED_BASALT);

		// WILDER WILD

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_slab_replaceable_worldgen"))
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB);

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_stair_replaceable_worldgen"))
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_wall_replaceable_worldgen"))
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);
	}

	@NotNull
	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

}
