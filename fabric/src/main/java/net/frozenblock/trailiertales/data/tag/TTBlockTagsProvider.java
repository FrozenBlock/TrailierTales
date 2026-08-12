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

package net.frozenblock.trailiertales.data.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.references.TTBlockIds;
import net.frozenblock.trailiertales.references.TTBlockItemIds;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class TTBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {

	public TTBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	private ResourceKey<Block> getKey(String namespace, String path) {
		return ResourceKey.create(this.registryKey, Identifier.fromNamespaceAndPath(namespace, path));
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		new TTBlockItemTagsProvider(tagId -> BlockItemTagsProvider.wrapForBlocks(this.tag(tagId.block()))).run();

		this.builder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(TTBlockItemIds.STONE_WALL)

			.add(TTBlockItemIds.CHISELED_GRANITE_BRICKS)
			.add(TTBlockItemIds.GRANITE_BRICKS)
			.add(TTBlockItemIds.CRACKED_GRANITE_BRICKS)
			.add(TTBlockItemIds.GRANITE_BRICK_STAIRS)
			.add(TTBlockItemIds.GRANITE_BRICK_SLAB)
			.add(TTBlockItemIds.GRANITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_GRANITE_BRICKS)
			.add(TTBlockItemIds.MOSSY_GRANITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_GRANITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_GRANITE_BRICK_WALL)
			.add(TTBlockItemIds.POLISHED_GRANITE_WALL)

			.add(TTBlockItemIds.CHISELED_DIORITE_BRICKS)
			.add(TTBlockItemIds.DIORITE_BRICKS)
			.add(TTBlockItemIds.CRACKED_DIORITE_BRICKS)
			.add(TTBlockItemIds.DIORITE_BRICK_STAIRS)
			.add(TTBlockItemIds.DIORITE_BRICK_SLAB)
			.add(TTBlockItemIds.DIORITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_DIORITE_BRICKS)
			.add(TTBlockItemIds.MOSSY_DIORITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_DIORITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_DIORITE_BRICK_WALL)
			.add(TTBlockItemIds.POLISHED_DIORITE_WALL)

			.add(TTBlockItemIds.CHISELED_ANDESITE_BRICKS)
			.add(TTBlockItemIds.ANDESITE_BRICKS)
			.add(TTBlockItemIds.CRACKED_ANDESITE_BRICKS)
			.add(TTBlockItemIds.ANDESITE_BRICK_STAIRS)
			.add(TTBlockItemIds.ANDESITE_BRICK_SLAB)
			.add(TTBlockItemIds.ANDESITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_ANDESITE_BRICKS)
			.add(TTBlockItemIds.MOSSY_ANDESITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_ANDESITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_ANDESITE_BRICK_WALL)
			.add(TTBlockItemIds.POLISHED_ANDESITE_WALL)

			.add(TTBlockItemIds.CALCITE_STAIRS)
			.add(TTBlockItemIds.CALCITE_SLAB)
			.add(TTBlockItemIds.CALCITE_WALL)
			.add(TTBlockItemIds.POLISHED_CALCITE)
			.add(TTBlockItemIds.POLISHED_CALCITE_STAIRS)
			.add(TTBlockItemIds.POLISHED_CALCITE_SLAB)
			.add(TTBlockItemIds.POLISHED_CALCITE_WALL)
			.add(TTBlockItemIds.CHISELED_CALCITE_BRICKS)
			.add(TTBlockItemIds.CALCITE_BRICKS)
			.add(TTBlockItemIds.CRACKED_CALCITE_BRICKS)
			.add(TTBlockItemIds.CALCITE_BRICK_STAIRS)
			.add(TTBlockItemIds.CALCITE_BRICK_SLAB)
			.add(TTBlockItemIds.CALCITE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_CALCITE_BRICKS)
			.add(TTBlockItemIds.MOSSY_CALCITE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_CALCITE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_CALCITE_BRICK_WALL)

			.add(TTBlockItemIds.CRACKED_TUFF_BRICKS)
			.add(TTBlockItemIds.MOSSY_TUFF_BRICKS)
			.add(TTBlockItemIds.MOSSY_TUFF_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_TUFF_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_TUFF_BRICK_WALL)

			.add(TTBlockItemIds.CRACKED_BRICKS)
			.add(TTBlockItemIds.MOSSY_BRICKS)
			.add(TTBlockItemIds.MOSSY_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_BRICK_WALL)

			.add(TTBlockItemIds.CRACKED_RESIN_BRICKS)
			.add(TTBlockItemIds.PALE_MOSSY_RESIN_BRICKS)
			.add(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_STAIRS)
			.add(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_SLAB)
			.add(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_WALL)

			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILES)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_WALL)

			.add(TTBlockItemIds.SMOOTH_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_SANDSTONE_STAIRS)

			.add(TTBlockItemIds.SMOOTH_RED_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_RED_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_RED_SANDSTONE_STAIRS)

			.add(TTBlockItemIds.PRISMARINE_BRICK_WALL)
			.add(TTBlockItemIds.DARK_PRISMARINE_WALL)

			.add(TTBlockItemIds.CHORAL_END_STONE)
			.add(TTBlockItemIds.CHORAL_END_STONE_STAIRS)
			.add(TTBlockItemIds.CHORAL_END_STONE_SLAB)
			.add(TTBlockItemIds.CHORAL_END_STONE_WALL)
			.add(TTBlockItemIds.END_STONE_STAIRS)
			.add(TTBlockItemIds.END_STONE_SLAB)
			.add(TTBlockItemIds.END_STONE_WALL)

			.add(TTBlockItemIds.CRACKED_END_STONE_BRICKS)
			.add(TTBlockItemIds.CHISELED_END_STONE_BRICKS)
			.add(TTBlockItemIds.CHORAL_END_STONE_BRICKS)
			.add(TTBlockItemIds.CHORAL_END_STONE_BRICK_STAIRS)
			.add(TTBlockItemIds.CHORAL_END_STONE_BRICK_SLAB)
			.add(TTBlockItemIds.CHORAL_END_STONE_BRICK_WALL)

			.add(TTBlockItemIds.CRACKED_PURPUR_BLOCK)
			.add(TTBlockItemIds.CHISELED_PURPUR_BLOCK)
			.add(TTBlockItemIds.PURPUR_WALL)

			.add(TTBlockItemIds.SURVEYOR);

		this.builder(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(TTBlockItemIds.SUSPICIOUS_RED_SAND)
			.add(TTBlockItemIds.SUSPICIOUS_DIRT)
			.add(TTBlockItemIds.SUSPICIOUS_CLAY);

		this.builder(BlockTags.MINEABLE_WITH_HOE)
			.add(TTBlockItemIds.LITHOPS);

		this.builder(BlockTags.SWORD_EFFICIENT)
			.add(TTBlockItemIds.LITHOPS);

		this.builder(BlockTags.FLOWER_POTS)
			.add(TTBlockIds.POTTED_CYAN_ROSE)
			.add(TTBlockIds.POTTED_LITHOPS);

		this.builder(BlockTags.CROPS)
			.add(TTBlockItemIds.CYAN_ROSE_CROP)
			.add(TTBlockItemIds.MANEDROP_CROP)
			.add(TTBlockItemIds.GUZMANIA_CROP)
			.add(TTBlockItemIds.DAWNTRAIL_CROP)
			.add(TTBlockItemIds.LITHOPS_CROP);

		this.builder(BlockTags.MAINTAINS_FARMLAND)
			.add(TTBlockItemIds.CYAN_ROSE_CROP)
			.add(TTBlockItemIds.CYAN_ROSE)
			.add(TTBlockItemIds.MANEDROP_CROP)
			.add(TTBlockItemIds.GUZMANIA_CROP)
			.add(TTBlockItemIds.DAWNTRAIL_CROP)
			.add(TTBlockItemIds.DAWNTRAIL)
			.add(TTBlockItemIds.LITHOPS_CROP)
			.add(TTBlockItemIds.LITHOPS);

		this.builder(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
			.add(TTBlockItemIds.DAWNTRAIL)
			.add(TTBlockItemIds.LITHOPS);

		this.builder(BlockTags.REPLACEABLE_BY_TREES)
			.add(TTBlockItemIds.CYAN_ROSE)
			.add(TTBlockItemIds.DAWNTRAIL)
			.add(TTBlockItemIds.LITHOPS);

		this.builder(BlockTags.ANCIENT_CITY_REPLACEABLE)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILES)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_WALL);

		this.builder(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILES);

		this.builder(TTBlockTags.SUPPORTS_LITHOPS)
			.addOptionalTag(BlockTags.SUPPORTS_DRY_VEGETATION)
			.addOptionalTag(BlockItemTags.SAND.block())
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(TTBlockTags.COFFIN_UNSPAWNABLE_ON)
			.add(BlockItemIds.REDSTONE_DUST)
			.add(BlockItemIds.REDSTONE_TORCH)
			.add(BlockIds.REDSTONE_WALL_TORCH)
			.add(BlockItemIds.REDSTONE_BLOCK)
			.add(BlockItemIds.REDSTONE_LAMP)
			.add(BlockItemIds.LEVER)
			.add(BlockItemIds.REPEATER)
			.add(BlockItemIds.COMPARATOR)
			.addOptionalTag(BlockItemTags.BUTTONS.block())
			.addOptionalTag(BlockTags.PRESSURE_PLATES);

		this.builder(BlockTags.FEATURES_CANNOT_REPLACE)
			.add(TTBlockItemIds.COFFIN);

		this.builder(BlockTags.IMPERMEABLE)
			.add(TTBlockItemIds.ECTOPLASM_BLOCK);

		this.builder(TTBlockTags.SURVEYOR_CAN_SEE_THROUGH)
			.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
			.addOptionalTag(ConventionalBlockTags.GLASS_PANES);

		this.builder(TTBlockTags.SURVEYOR_CANNOT_SEE_THROUGH)
			.add(BlockItemIds.TINTED_GLASS)
			.addOptional(this.getKey(TTModIntegrations.WILDER_WILD_INTEGRATION.getIntegration().getID(), "echo_glass"));

		this.builder(ConventionalBlockTags.UNCOLORED_SANDSTONE_BLOCKS)
			.add(TTBlockItemIds.SMOOTH_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_SANDSTONE_STAIRS);

		this.builder(ConventionalBlockTags.UNCOLORED_SANDSTONE_STAIRS)
			.add(TTBlockItemIds.CUT_SANDSTONE_STAIRS);

		this.builder(ConventionalBlockTags.RED_SANDSTONE_BLOCKS)
			.add(TTBlockItemIds.SMOOTH_RED_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_RED_SANDSTONE_WALL)
			.add(TTBlockItemIds.CUT_RED_SANDSTONE_STAIRS);

		this.builder(ConventionalBlockTags.RED_SANDSTONE_STAIRS)
			.add(TTBlockItemIds.CUT_RED_SANDSTONE_STAIRS);

		// SOUNDS

		this.builder(TTBlockTags.SOUND_UNPOLISHED_BRICKS)
			.add(
				BlockItemIds.BRICKS,
				BlockItemIds.BRICK_STAIRS,
				BlockItemIds.BRICK_SLAB,
				BlockItemIds.BRICK_WALL,
				TTBlockItemIds.CRACKED_BRICKS,
				TTBlockItemIds.MOSSY_BRICKS,
				TTBlockItemIds.MOSSY_BRICK_STAIRS,
				TTBlockItemIds.MOSSY_BRICK_SLAB,
				TTBlockItemIds.MOSSY_BRICK_WALL
			).add(
				BlockItemIds.STONE_BRICKS,
				BlockItemIds.STONE_BRICK_STAIRS,
				BlockItemIds.STONE_BRICK_SLAB,
				BlockItemIds.STONE_BRICK_WALL,
				BlockItemIds.CHISELED_STONE_BRICKS,
				BlockItemIds.CRACKED_STONE_BRICKS,
				BlockItemIds.MOSSY_STONE_BRICKS,
				BlockItemIds.MOSSY_STONE_BRICK_STAIRS,
				BlockItemIds.MOSSY_STONE_BRICK_SLAB,
				BlockItemIds.MOSSY_STONE_BRICK_WALL,
				BlockItemIds.INFESTED_STONE_BRICKS,
				BlockItemIds.INFESTED_CHISELED_STONE_BRICKS,
				BlockItemIds.INFESTED_CRACKED_STONE_BRICKS,
				BlockItemIds.INFESTED_MOSSY_STONE_BRICKS
			).add(
				BlockItemIds.PRISMARINE_BRICKS,
				BlockItemIds.PRISMARINE_BRICK_STAIRS,
				BlockItemIds.PRISMARINE_BRICK_SLAB,
				TTBlockItemIds.PRISMARINE_BRICK_WALL
			)
			.add(
				BlockItemIds.END_STONE_BRICKS,
				BlockItemIds.END_STONE_BRICK_STAIRS,
				BlockItemIds.END_STONE_BRICK_SLAB,
				BlockItemIds.END_STONE_BRICK_WALL,
				TTBlockItemIds.CHISELED_END_STONE_BRICKS,
				TTBlockItemIds.CRACKED_END_STONE_BRICKS,
				TTBlockItemIds.CHORAL_END_STONE_BRICKS,
				TTBlockItemIds.CHORAL_END_STONE_BRICK_STAIRS,
				TTBlockItemIds.CHORAL_END_STONE_BRICK_SLAB,
				TTBlockItemIds.CHORAL_END_STONE_BRICK_WALL
			);

		this.builder(TTBlockTags.SOUND_POLISHED_BRICKS)
			.add(
				TTBlockItemIds.GRANITE_BRICKS,
				TTBlockItemIds.GRANITE_BRICK_STAIRS,
				TTBlockItemIds.GRANITE_BRICK_SLAB,
				TTBlockItemIds.GRANITE_BRICK_WALL,
				TTBlockItemIds.CHISELED_GRANITE_BRICKS,
				TTBlockItemIds.CRACKED_GRANITE_BRICKS,
				TTBlockItemIds.MOSSY_GRANITE_BRICKS,
				TTBlockItemIds.MOSSY_GRANITE_BRICK_STAIRS,
				TTBlockItemIds.MOSSY_GRANITE_BRICK_SLAB,
				TTBlockItemIds.MOSSY_GRANITE_BRICK_WALL
			).add(
				TTBlockItemIds.DIORITE_BRICKS,
				TTBlockItemIds.DIORITE_BRICK_STAIRS,
				TTBlockItemIds.DIORITE_BRICK_SLAB,
				TTBlockItemIds.DIORITE_BRICK_WALL,
				TTBlockItemIds.CHISELED_DIORITE_BRICKS,
				TTBlockItemIds.CRACKED_DIORITE_BRICKS,
				TTBlockItemIds.MOSSY_DIORITE_BRICKS,
				TTBlockItemIds.MOSSY_DIORITE_BRICK_STAIRS,
				TTBlockItemIds.MOSSY_DIORITE_BRICK_SLAB,
				TTBlockItemIds.MOSSY_DIORITE_BRICK_WALL
			).add(
				TTBlockItemIds.ANDESITE_BRICKS,
				TTBlockItemIds.ANDESITE_BRICK_STAIRS,
				TTBlockItemIds.ANDESITE_BRICK_SLAB,
				TTBlockItemIds.ANDESITE_BRICK_WALL,
				TTBlockItemIds.CHISELED_ANDESITE_BRICKS,
				TTBlockItemIds.CRACKED_ANDESITE_BRICKS,
				TTBlockItemIds.MOSSY_ANDESITE_BRICKS,
				TTBlockItemIds.MOSSY_ANDESITE_BRICK_STAIRS,
				TTBlockItemIds.MOSSY_ANDESITE_BRICK_SLAB,
				TTBlockItemIds.MOSSY_ANDESITE_BRICK_WALL
			)
			.add(
				BlockItemIds.POLISHED_BLACKSTONE_BRICKS,
				BlockItemIds.POLISHED_BLACKSTONE_BRICK_STAIRS,
				BlockItemIds.POLISHED_BLACKSTONE_BRICK_SLAB,
				BlockItemIds.POLISHED_BLACKSTONE_BRICK_WALL,
				BlockItemIds.CRACKED_POLISHED_BLACKSTONE_BRICKS
			);

		this.builder(TTBlockTags.SOUND_POLISHED_CALCITE)
			.add(
				TTBlockItemIds.POLISHED_CALCITE,
				TTBlockItemIds.POLISHED_CALCITE_SLAB,
				TTBlockItemIds.POLISHED_CALCITE_WALL,
				TTBlockItemIds.POLISHED_CALCITE_STAIRS
			);

		this.builder(TTBlockTags.SOUND_CALCITE_BRICKS)
			.add(
				TTBlockItemIds.CALCITE_BRICKS,
				TTBlockItemIds.CALCITE_BRICK_STAIRS,
				TTBlockItemIds.CALCITE_BRICK_SLAB,
				TTBlockItemIds.CALCITE_BRICK_WALL,
				TTBlockItemIds.CHISELED_CALCITE_BRICKS,
				TTBlockItemIds.CRACKED_CALCITE_BRICKS,
				TTBlockItemIds.MOSSY_CALCITE_BRICKS,
				TTBlockItemIds.MOSSY_CALCITE_BRICK_STAIRS,
				TTBlockItemIds.MOSSY_CALCITE_BRICK_SLAB,
				TTBlockItemIds.MOSSY_CALCITE_BRICK_WALL
			);

		this.builder(TTBlockTags.SOUND_POLISHED)
			.add(
				BlockItemIds.POLISHED_ANDESITE,
				BlockItemIds.POLISHED_ANDESITE_SLAB,
				BlockItemIds.POLISHED_ANDESITE_STAIRS,
				TTBlockItemIds.POLISHED_ANDESITE_WALL,
				BlockItemIds.POLISHED_GRANITE,
				BlockItemIds.POLISHED_GRANITE_SLAB,
				BlockItemIds.POLISHED_GRANITE_STAIRS,
				TTBlockItemIds.POLISHED_GRANITE_WALL,
				BlockItemIds.POLISHED_DIORITE,
				BlockItemIds.POLISHED_DIORITE_SLAB,
				BlockItemIds.POLISHED_DIORITE_STAIRS,
				TTBlockItemIds.POLISHED_DIORITE_WALL,
				BlockItemIds.POLISHED_BLACKSTONE,
				BlockItemIds.POLISHED_BLACKSTONE_SLAB,
				BlockItemIds.POLISHED_BLACKSTONE_STAIRS,
				BlockItemIds.POLISHED_BLACKSTONE_WALL,
				BlockItemIds.CHISELED_POLISHED_BLACKSTONE,
				BlockItemIds.POLISHED_BLACKSTONE_BUTTON,
				BlockItemIds.POLISHED_BLACKSTONE_PRESSURE_PLATE
			);

		this.builder(TTBlockTags.SOUND_POLISHED_DEEPSLATE)
			.add(
				BlockItemIds.POLISHED_DEEPSLATE,
				BlockItemIds.POLISHED_DEEPSLATE_SLAB,
				BlockItemIds.POLISHED_DEEPSLATE_WALL,
				BlockItemIds.POLISHED_DEEPSLATE_STAIRS
			);

		this.builder(TTBlockTags.SOUND_POLISHED_TUFF)
			.add(
				BlockItemIds.POLISHED_TUFF,
				BlockItemIds.POLISHED_TUFF_SLAB,
				BlockItemIds.POLISHED_TUFF_WALL,
				BlockItemIds.POLISHED_TUFF_STAIRS
			);

		this.builder(TTBlockTags.SOUND_POLISHED_BASALT)
			.add(BlockItemIds.POLISHED_BASALT);

		this.builder(TTBlockTags.SOUND_POLISHED_RESIN)
			.add(TTBlockItemIds.POLISHED_RESIN_BLOCK, TTBlockItemIds.POLISHED_RESIN_STAIRS, TTBlockItemIds.POLISHED_RESIN_SLAB, TTBlockItemIds.POLISHED_RESIN_WALL);

		this.builder(TTBlockTags.SOUND_SUSPICIOUS_CLAY)
			.add(TTBlockItemIds.SUSPICIOUS_CLAY);

		this.builder(TTBlockTags.SOUND_SUSPICIOUS_GRAVEL)
			.add(BlockItemIds.SUSPICIOUS_GRAVEL);

		// WILDER WILD
		this.builder(getTag("wilderwild:sculk_slab_replaceable_worldgen"))
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_SLAB);

		this.builder(getTag("wilderwild:sculk_stair_replaceable_worldgen"))
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_STAIRS);

		this.builder(getTag("wilderwild:sculk_wall_replaceable_worldgen"))
			.add(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_WALL);

		this.builder(getTag("wilderwild:sculk_wall_replaceable"))
			.add(TTBlockItemIds.STONE_WALL);
	}

	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

}
