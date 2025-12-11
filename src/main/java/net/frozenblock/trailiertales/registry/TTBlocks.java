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

package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.storage.api.NoInteractionStorage;
import net.frozenblock.lib.block.storage.api.hopper.HopperApi;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.CyanRoseCropBlock;
import net.frozenblock.trailiertales.block.DawntrailBlock;
import net.frozenblock.trailiertales.block.DawntrailCropBlock;
import net.frozenblock.trailiertales.block.EctoplasmBlock;
import net.frozenblock.trailiertales.block.GuzmaniaCropBlock;
import net.frozenblock.trailiertales.block.LithopsBlock;
import net.frozenblock.trailiertales.block.LithopsCropBlock;
import net.frozenblock.trailiertales.block.ManedropCropBlock;
import net.frozenblock.trailiertales.block.NonFallingBrushableBlock;
import net.frozenblock.trailiertales.block.SurveyorBlock;
import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class TTBlocks {

	public static final Block SUSPICIOUS_RED_SAND = new BrushableBlock(
		Blocks.RED_SAND,
		SoundEvents.BRUSH_SAND,
		SoundEvents.BRUSH_SAND_COMPLETED,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_ORANGE)
			.instrument(NoteBlockInstrument.SNARE)
			.strength(0.25F).sound(SoundType.SUSPICIOUS_SAND)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block SUSPICIOUS_DIRT = new NonFallingBrushableBlock(
		Blocks.DIRT,
		TTSounds.BRUSH_DIRT,
		TTSounds.BRUSH_DIRT_COMPLETED,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.DIRT)
			.strength(0.25F)
			.sound(TTSounds.SUSPICIOUS_DIRT)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block SUSPICIOUS_CLAY = new NonFallingBrushableBlock(
		Blocks.CLAY,
		TTSounds.BRUSH_CLAY,
		TTSounds.BRUSH_CLAY_COMPLETED,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.CLAY)
			.instrument(NoteBlockInstrument.FLUTE)
			.strength(0.25F)
			.sound(TTSounds.SUSPICIOUS_CLAY)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block CYAN_ROSE_CROP = new CyanRoseCropBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CYAN_ROSE = new FlowerBlock(
		MobEffects.SATURATION,
		0.5F,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block POTTED_CYAN_ROSE = new FlowerPotBlock(
		CYAN_ROSE,
		BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block MANEDROP_CROP = new ManedropCropBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MANEDROP = new DoublePlantBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block GUZMANIA_CROP = new GuzmaniaCropBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block GUZMANIA = new DoublePlantBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block DAWNTRAIL_CROP = new DawntrailCropBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block DAWNTRAIL = new DawntrailBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.randomTicks()
			.strength(0.2F)
			.sound(SoundType.VINE)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block LITHOPS_CROP = new LithopsCropBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final LithopsBlock LITHOPS = new LithopsBlock(
		BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block POTTED_LITHOPS = Blocks.flowerPot(LITHOPS);

	// STONE

	public static final Block STONE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG));

	// GRANITE

	public static final Block POLISHED_GRANITE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_GRANITE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG));

	public static final Block GRANITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRANITE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block CHISELED_GRANITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS));
	public static final Block GRANITE_BRICK_STAIRS = new StairBlock(
		GRANITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS)
	);
	public static final Block GRANITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS));
	public static final Block GRANITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS));
	public static final Block CRACKED_GRANITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS));
	public static final BlockFamily FAMILY_GRANITE_BRICK = BlockFamilies.familyBuilder(GRANITE_BRICKS)
		.stairs(GRANITE_BRICK_STAIRS)
		.slab(GRANITE_BRICK_SLAB)
		.wall(GRANITE_BRICK_WALL)
		.cracked(CRACKED_GRANITE_BRICKS)
		.chiseled(CHISELED_GRANITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_GRANITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS));
	public static final Block MOSSY_GRANITE_BRICK_STAIRS = new StairBlock(
		MOSSY_GRANITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_GRANITE_BRICKS)
	);
	public static final Block MOSSY_GRANITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_GRANITE_BRICKS));
	public static final Block MOSSY_GRANITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_GRANITE_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_GRANITE_BRICK = BlockFamilies.familyBuilder(MOSSY_GRANITE_BRICKS)
		.stairs(MOSSY_GRANITE_BRICK_STAIRS)
		.slab(MOSSY_GRANITE_BRICK_SLAB)
		.wall(MOSSY_GRANITE_BRICK_WALL)
		.getFamily();

	// DIORITE

	public static final Block POLISHED_DIORITE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG));

	public static final Block DIORITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIORITE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block DIORITE_BRICK_STAIRS = new StairBlock(
		DIORITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS)
	);
	public static final Block DIORITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS));
	public static final Block DIORITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS));
	public static final Block CRACKED_DIORITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS));
	public static final Block CHISELED_DIORITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS));
	public static final BlockFamily FAMILY_DIORITE_BRICK = BlockFamilies.familyBuilder(DIORITE_BRICKS)
		.stairs(DIORITE_BRICK_STAIRS)
		.slab(DIORITE_BRICK_SLAB)
		.wall(DIORITE_BRICK_WALL)
		.cracked(CRACKED_DIORITE_BRICKS)
		.chiseled(CHISELED_DIORITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_DIORITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS));
	public static final Block MOSSY_DIORITE_BRICK_STAIRS = new StairBlock(
		MOSSY_DIORITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_DIORITE_BRICKS)
	);
	public static final Block MOSSY_DIORITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_DIORITE_BRICKS));
	public static final Block MOSSY_DIORITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_DIORITE_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_DIORITE_BRICK = BlockFamilies.familyBuilder(MOSSY_DIORITE_BRICKS)
		.stairs(MOSSY_DIORITE_BRICK_STAIRS)
		.slab(MOSSY_DIORITE_BRICK_SLAB)
		.wall(MOSSY_DIORITE_BRICK_WALL)
		.getFamily();

	// ANDESITE

	public static final Block POLISHED_ANDESITE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_ANDESITE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG));

	public static final Block ANDESITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block ANDESITE_BRICK_STAIRS = new StairBlock(
		ANDESITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(ANDESITE_BRICKS)
	);
	public static final Block ANDESITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ANDESITE_BRICKS));
	public static final Block ANDESITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(ANDESITE_BRICKS));
	public static final Block CRACKED_ANDESITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(ANDESITE_BRICKS));
	public static final Block CHISELED_ANDESITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(ANDESITE_BRICKS));
	public static final BlockFamily FAMILY_ANDESITE_BRICK = BlockFamilies.familyBuilder(ANDESITE_BRICKS)
		.stairs(ANDESITE_BRICK_STAIRS)
		.slab(ANDESITE_BRICK_SLAB)
		.wall(ANDESITE_BRICK_WALL)
		.cracked(CRACKED_ANDESITE_BRICKS)
		.chiseled(CHISELED_ANDESITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_ANDESITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE));
	public static final Block MOSSY_ANDESITE_BRICK_STAIRS = new StairBlock(
		MOSSY_ANDESITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_ANDESITE_BRICKS)
	);
	public static final Block MOSSY_ANDESITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_ANDESITE_BRICKS));
	public static final Block MOSSY_ANDESITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_ANDESITE_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_ANDESITE_BRICK = BlockFamilies.familyBuilder(MOSSY_ANDESITE_BRICKS)
		.stairs(MOSSY_ANDESITE_BRICK_STAIRS)
		.slab(MOSSY_ANDESITE_BRICK_SLAB)
		.wall(MOSSY_ANDESITE_BRICK_WALL)
		.getFamily();

	// CALCITE

	public static final Block CALCITE_STAIRS = new StairBlock(
		Blocks.CALCITE.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CALCITE_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block CALCITE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));

	public static final Block POLISHED_CALCITE = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block POLISHED_CALCITE_STAIRS = new StairBlock(
		Blocks.CALCITE.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block POLISHED_CALCITE_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_CALCITE));
	public static final Block POLISHED_CALCITE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_CALCITE));
	public static final BlockFamily FAMILY_POLISHED_CALCITE = BlockFamilies.familyBuilder(POLISHED_CALCITE)
		.stairs(POLISHED_CALCITE_STAIRS)
		.slab(POLISHED_CALCITE_SLAB)
		.wall(POLISHED_CALCITE_WALL)
		.getFamily();

	public static final Block CALCITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
		.sound(TTSounds.CALCITE_BRICKS)
	);
	public static final Block CALCITE_BRICK_STAIRS = new StairBlock(
		CALCITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS)
	);
	public static final Block CALCITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS));
	public static final Block CALCITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS));
	public static final Block CRACKED_CALCITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS));
	public static final Block CHISELED_CALCITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS));
	public static final BlockFamily FAMILY_CALCITE_BRICK = BlockFamilies.familyBuilder(CALCITE_BRICKS)
		.stairs(CALCITE_BRICK_STAIRS)
		.slab(CALCITE_BRICK_SLAB)
		.wall(CALCITE_BRICK_WALL)
		.cracked(CRACKED_CALCITE_BRICKS)
		.chiseled(CHISELED_CALCITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_CALCITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS));
	public static final Block MOSSY_CALCITE_BRICK_STAIRS = new StairBlock(
		MOSSY_CALCITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_CALCITE_BRICKS)
	);
	public static final Block MOSSY_CALCITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_CALCITE_BRICKS));
	public static final Block MOSSY_CALCITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_CALCITE_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_CALCITE_BRICK = BlockFamilies.familyBuilder(MOSSY_CALCITE_BRICKS)
		.stairs(MOSSY_CALCITE_BRICK_STAIRS)
		.slab(MOSSY_CALCITE_BRICK_SLAB)
		.wall(MOSSY_CALCITE_BRICK_WALL)
		.getFamily();

	// TUFF

	public static final Block CRACKED_TUFF_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF_BRICKS).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block MOSSY_TUFF_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF_BRICKS).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block MOSSY_TUFF_BRICK_STAIRS = new StairBlock(
		MOSSY_TUFF_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_TUFF_BRICKS)
	);
	public static final Block MOSSY_TUFF_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_TUFF_BRICKS));
	public static final Block MOSSY_TUFF_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_TUFF_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_TUFF_BRICKS = BlockFamilies.familyBuilder(MOSSY_TUFF_BRICKS)
		.stairs(MOSSY_TUFF_BRICK_STAIRS)
		.slab(MOSSY_TUFF_BRICK_SLAB)
		.wall(MOSSY_TUFF_BRICK_WALL)
		.getFamily();

	// BRICKS

	public static final Block CRACKED_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block MOSSY_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block MOSSY_BRICK_STAIRS = new StairBlock(
		MOSSY_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_BRICKS)
	);
	public static final Block MOSSY_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_BRICKS));
	public static final Block MOSSY_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_BRICKS = BlockFamilies.familyBuilder(MOSSY_BRICKS)
		.stairs(MOSSY_BRICK_STAIRS)
		.slab(MOSSY_BRICK_SLAB)
		.wall(MOSSY_BRICK_WALL)
		.getFamily();

	// MOSSY DEEPSLATE

	public static final Block MOSSY_COBBLED_DEEPSLATE = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_COBBLED_DEEPSLATE_STAIRS = new StairBlock(
		MOSSY_COBBLED_DEEPSLATE.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_COBBLED_DEEPSLATE)
	);
	public static final Block MOSSY_COBBLED_DEEPSLATE_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_COBBLED_DEEPSLATE));
	public static final Block MOSSY_COBBLED_DEEPSLATE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_COBBLED_DEEPSLATE));
	public static final BlockFamily FAMILY_MOSSY_COBBLED_DEEPSLATE = BlockFamilies.familyBuilder(MOSSY_COBBLED_DEEPSLATE)
		.stairs(MOSSY_COBBLED_DEEPSLATE_STAIRS)
		.slab(MOSSY_COBBLED_DEEPSLATE_SLAB)
		.wall(MOSSY_COBBLED_DEEPSLATE_WALL)
		.getFamily();

	public static final Block MOSSY_DEEPSLATE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_DEEPSLATE_BRICK_STAIRS = new StairBlock(
		MOSSY_DEEPSLATE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_DEEPSLATE_BRICKS)
	);
	public static final Block MOSSY_DEEPSLATE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_DEEPSLATE_BRICKS));
	public static final Block MOSSY_DEEPSLATE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_DEEPSLATE_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_DEEPSLATE_BRICKS = BlockFamilies.familyBuilder(MOSSY_DEEPSLATE_BRICKS)
		.stairs(MOSSY_DEEPSLATE_BRICK_STAIRS)
		.slab(MOSSY_DEEPSLATE_BRICK_SLAB)
		.wall(MOSSY_DEEPSLATE_BRICK_WALL)
		.getFamily();

	public static final Block MOSSY_DEEPSLATE_TILES = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_DEEPSLATE_TILE_STAIRS = new StairBlock(
		MOSSY_DEEPSLATE_TILES.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILE_STAIRS)
	);
	public static final Block MOSSY_DEEPSLATE_TILE_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_DEEPSLATE_TILES));
	public static final Block MOSSY_DEEPSLATE_TILE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_DEEPSLATE_TILES));
	public static final BlockFamily FAMILY_MOSSY_DEEPSLATE_TILES = BlockFamilies.familyBuilder(MOSSY_DEEPSLATE_TILES)
		.stairs(MOSSY_DEEPSLATE_TILE_STAIRS)
		.slab(MOSSY_DEEPSLATE_TILE_SLAB)
		.wall(MOSSY_DEEPSLATE_TILE_WALL)
		.getFamily();

	// SANDSTONE

	public static final Block SMOOTH_SANDSTONE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_SANDSTONE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block CUT_SANDSTONE_STAIRS = new StairBlock(
		Blocks.CUT_SANDSTONE.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CUT_SANDSTONE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_SANDSTONE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	// RED SANDSTONE

	public static final Block SMOOTH_RED_SANDSTONE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_RED_SANDSTONE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block CUT_RED_SANDSTONE_STAIRS = new StairBlock(
		Blocks.CUT_RED_SANDSTONE.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_RED_SANDSTONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CUT_RED_SANDSTONE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_RED_SANDSTONE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	// PRISMARINE

	public static final Block PRISMARINE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block DARK_PRISMARINE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	// END STONE

	public static final Block END_STONE_STAIRS = new StairBlock(
		Blocks.END_STONE.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE)
	);
	public static final Block END_STONE_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Block END_STONE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).requiredFeatures(TTFeatureFlags.FEATURE_FLAG));

	public static final Block CHORAL_END_STONE = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHORAL_END_STONE_STAIRS = new StairBlock(
		CHORAL_END_STONE.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(CHORAL_END_STONE)
	);
	public static final Block CHORAL_END_STONE_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CHORAL_END_STONE));
	public static final Block CHORAL_END_STONE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(CHORAL_END_STONE));
	public static final BlockFamily FAMILY_CHORAL_END_STONE = BlockFamilies.familyBuilder(CHORAL_END_STONE)
		.stairs(CHORAL_END_STONE_STAIRS)
		.slab(CHORAL_END_STONE_SLAB)
		.wall(CHORAL_END_STONE_WALL)
		.getFamily();

	public static final Block CRACKED_END_STONE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHISELED_END_STONE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHORAL_END_STONE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHORAL_END_STONE_BRICK_STAIRS = new StairBlock(
		CHORAL_END_STONE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(CHORAL_END_STONE_BRICKS)
	);
	public static final Block CHORAL_END_STONE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CHORAL_END_STONE_BRICKS));
	public static final Block CHORAL_END_STONE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(CHORAL_END_STONE_BRICKS));
	public static final BlockFamily FAMILY_CHORAL_END_STONE_BRICKS = BlockFamilies.familyBuilder(CHORAL_END_STONE_BRICKS)
		.stairs(CHORAL_END_STONE_BRICK_STAIRS)
		.slab(CHORAL_END_STONE_BRICK_SLAB)
		.wall(CHORAL_END_STONE_BRICK_WALL)
		.getFamily();

	// PURPUR

	public static final Block CRACKED_PURPUR_BLOCK = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_BLOCK)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block PURPUR_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_BLOCK)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHISELED_PURPUR_BLOCK = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_BLOCK)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final CoffinBlock COFFIN = new CoffinBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.noOcclusion()
			.lightLevel(state -> state.getValue(TTBlockStateProperties.COFFIN_STATE).getLightLevel())
			.requiresCorrectToolForDrops()
			.sound(TTSounds.COFFIN)
			.strength(50F)
			.isViewBlocking(Blocks::never)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final SurveyorBlock SURVEYOR = new SurveyorBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.STONE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(3F)
			.requiresCorrectToolForDrops()
			.isRedstoneConductor(Blocks::never)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final EctoplasmBlock ECTOPLASM_BLOCK = new EctoplasmBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_LIGHT_BLUE)
			.noOcclusion()
			.instabreak()
			.explosionResistance(1200F)
			.emissiveRendering(Blocks::always)
			.lightLevel(state -> 1)
			.sound(TTSounds.ECTOPLASM)
			.isSuffocating(Blocks::never)
			.isViewBlocking(Blocks::never)
			.pushReaction(PushReaction.DESTROY)
			.dynamicShape()
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static void init() {
		TTConstants.log("Registering Blocks for Trailier Tales.", TTConstants.UNSTABLE_LOGGING);

		registerBlockAfter(Blocks.SUSPICIOUS_SAND, "suspicious_red_sand", SUSPICIOUS_RED_SAND, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlockAfter(SUSPICIOUS_RED_SAND, "suspicious_dirt",SUSPICIOUS_DIRT, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlockAfter(SUSPICIOUS_DIRT, "suspicious_clay", SUSPICIOUS_CLAY, CreativeModeTabs.FUNCTIONAL_BLOCKS);

		registerBlockAfter(Blocks.TORCHFLOWER, "cyan_rose", CYAN_ROSE, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("cyan_rose_crop", CYAN_ROSE_CROP);
		registerBlock("potted_cyan_rose", POTTED_CYAN_ROSE);

		registerBlockAfter(Blocks.PITCHER_PLANT, "manedrop", MANEDROP, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("manedrop_crop", MANEDROP_CROP);

		registerBlockAfter(MANEDROP, "guzmania", GUZMANIA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("guzmania_crop", GUZMANIA_CROP);

		registerBlockAfter(GUZMANIA, "lithops", LITHOPS, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("lithops_crop", LITHOPS_CROP);
		registerBlock("potted_lithops", POTTED_LITHOPS);

		registerBlockAfter(Blocks.GLOW_LICHEN, "dawntrail", DAWNTRAIL, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("dawntrail_crop", DAWNTRAIL_CROP);

		registerBlockAfter(Blocks.STONE_SLAB, "stone_wall", STONE_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.POLISHED_GRANITE_SLAB, "polished_granite_wall", POLISHED_GRANITE_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.GRANITE_SLAB, "granite_bricks", GRANITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(GRANITE_BRICKS, "cracked_granite_bricks", CRACKED_GRANITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CRACKED_GRANITE_BRICKS, "granite_brick_stairs", GRANITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(GRANITE_BRICK_STAIRS, "granite_brick_slab", GRANITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(GRANITE_BRICK_SLAB, "granite_brick_wall", GRANITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(GRANITE_BRICK_WALL, "chiseled_granite_bricks", CHISELED_GRANITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHISELED_GRANITE_BRICKS, "mossy_granite_bricks", MOSSY_GRANITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_GRANITE_BRICKS, "mossy_granite_brick_stairs", MOSSY_GRANITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_GRANITE_BRICK_STAIRS, "mossy_granite_brick_slab", MOSSY_GRANITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_GRANITE_BRICK_SLAB, "mossy_granite_brick_wall", MOSSY_GRANITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.POLISHED_DIORITE_SLAB, "polished_diorite_wall", POLISHED_DIORITE_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.DIORITE_SLAB, "diorite_bricks", DIORITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(DIORITE_BRICKS, "cracked_diorite_bricks", CRACKED_DIORITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CRACKED_DIORITE_BRICKS, "diorite_brick_stairs", DIORITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(DIORITE_BRICK_STAIRS, "diorite_brick_slab", DIORITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(DIORITE_BRICK_SLAB, "diorite_brick_wall", DIORITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(DIORITE_BRICK_WALL, "chiseled_diorite_bricks", CHISELED_DIORITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHISELED_DIORITE_BRICKS, "mossy_diorite_bricks", MOSSY_DIORITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DIORITE_BRICKS, "mossy_diorite_brick_stairs", MOSSY_DIORITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DIORITE_BRICK_STAIRS, "mossy_diorite_brick_slab", MOSSY_DIORITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DIORITE_BRICK_SLAB, "mossy_diorite_brick_wall", MOSSY_DIORITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.POLISHED_ANDESITE_SLAB, "polished_andesite_wall", POLISHED_ANDESITE_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.ANDESITE_SLAB, "andesite_bricks", ANDESITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(ANDESITE_BRICKS, "cracked_andesite_bricks", CRACKED_ANDESITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CRACKED_ANDESITE_BRICKS, "andesite_brick_stairs", ANDESITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(ANDESITE_BRICK_STAIRS, "andesite_brick_slab", ANDESITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(ANDESITE_BRICK_SLAB, "andesite_brick_wall", ANDESITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(ANDESITE_BRICK_WALL, "chiseled_andesite_bricks", CHISELED_ANDESITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHISELED_ANDESITE_BRICKS, "mossy_andesite_bricks", MOSSY_ANDESITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_ANDESITE_BRICKS, "mossy_andesite_brick_stairs", MOSSY_ANDESITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_ANDESITE_BRICK_STAIRS, "mossy_andesite_brick_slab", MOSSY_ANDESITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_ANDESITE_BRICK_SLAB, "mossy_andesite_brick_wall", MOSSY_ANDESITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		FrozenCreativeTabs.addBefore(Blocks.DEEPSLATE, Blocks.CALCITE, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.CALCITE, "calcite_stairs", CALCITE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CALCITE_STAIRS, "calcite_slab", CALCITE_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CALCITE_SLAB, "calcite_wall", CALCITE_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CALCITE_WALL, "polished_calcite", POLISHED_CALCITE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_CALCITE, "polished_calcite_stairs", POLISHED_CALCITE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_CALCITE_STAIRS, "polished_calcite_slab", POLISHED_CALCITE_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_CALCITE_SLAB, "polished_calcite_wall", POLISHED_CALCITE_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_CALCITE_WALL, "calcite_bricks", CALCITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CALCITE_BRICKS, "cracked_calcite_bricks", CRACKED_CALCITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CRACKED_CALCITE_BRICKS, "calcite_brick_stairs", CALCITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CALCITE_BRICK_STAIRS, "calcite_brick_slab", CALCITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CALCITE_BRICK_SLAB, "calcite_brick_wall", CALCITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CALCITE_BRICK_WALL, "chiseled_calcite_bricks", CHISELED_CALCITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHISELED_CALCITE_BRICKS, "mossy_calcite_bricks", MOSSY_CALCITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_CALCITE_BRICKS, "mossy_calcite_brick_stairs", MOSSY_CALCITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_CALCITE_BRICK_STAIRS, "mossy_calcite_brick_slab", MOSSY_CALCITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_CALCITE_BRICK_SLAB, "mossy_calcite_brick_wall", MOSSY_CALCITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.TUFF_BRICKS, "cracked_tuff_bricks", CRACKED_TUFF_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.CHISELED_TUFF_BRICKS, "mossy_tuff_bricks", MOSSY_TUFF_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_TUFF_BRICKS, "mossy_tuff_brick_stairs", MOSSY_TUFF_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_TUFF_BRICK_STAIRS, "mossy_tuff_brick_slab", MOSSY_TUFF_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_TUFF_BRICK_SLAB, "mossy_tuff_brick_wall", MOSSY_TUFF_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.BRICKS, "cracked_bricks", CRACKED_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.BRICK_WALL, "mossy_bricks", MOSSY_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_BRICKS, "mossy_brick_stairs", MOSSY_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_BRICK_STAIRS, "mossy_brick_slab", MOSSY_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_BRICK_SLAB, "mossy_brick_wall", MOSSY_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.COBBLED_DEEPSLATE_WALL, "mossy_cobbled_deepslate", MOSSY_COBBLED_DEEPSLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_COBBLED_DEEPSLATE, "mossy_cobbled_deepslate_stairs", MOSSY_COBBLED_DEEPSLATE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_COBBLED_DEEPSLATE_STAIRS, "mossy_cobbled_deepslate_slab", MOSSY_COBBLED_DEEPSLATE_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_COBBLED_DEEPSLATE_SLAB, "mossy_cobbled_deepslate_wall", MOSSY_COBBLED_DEEPSLATE_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.DEEPSLATE_BRICK_WALL, "mossy_deepslate_bricks", MOSSY_DEEPSLATE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DEEPSLATE_BRICKS, "mossy_deepslate_brick_stairs", MOSSY_DEEPSLATE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DEEPSLATE_BRICK_STAIRS, "mossy_deepslate_brick_slab", MOSSY_DEEPSLATE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DEEPSLATE_BRICK_SLAB, "mossy_deepslate_brick_wall", MOSSY_DEEPSLATE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.DEEPSLATE_TILE_WALL, "mossy_deepslate_tiles", MOSSY_DEEPSLATE_TILES, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DEEPSLATE_TILES, "mossy_deepslate_tile_stairs", MOSSY_DEEPSLATE_TILE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DEEPSLATE_TILE_STAIRS, "mossy_deepslate_tile_slab", MOSSY_DEEPSLATE_TILE_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_DEEPSLATE_TILE_SLAB, "mossy_deepslate_tile_wall", MOSSY_DEEPSLATE_TILE_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.SMOOTH_SANDSTONE_SLAB, "smooth_sandstone_wall", SMOOTH_SANDSTONE_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockBefore(Blocks.CUT_SANDSTONE_SLAB, "cut_sandstone_stairs", CUT_SANDSTONE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.CUT_SANDSTONE_SLAB, "cut_sandstone_wall", CUT_SANDSTONE_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.SMOOTH_RED_SANDSTONE_SLAB, "smooth_red_sandstone_wall", SMOOTH_RED_SANDSTONE_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockBefore(Blocks.CUT_RED_SANDSTONE_SLAB, "cut_red_sandstone_stairs", CUT_RED_SANDSTONE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.CUT_RED_SANDSTONE_SLAB, "cut_red_sandstone_wall", CUT_RED_SANDSTONE_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.PRISMARINE_BRICK_SLAB, "prismarine_brick_wall", PRISMARINE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.DARK_PRISMARINE_SLAB, "dark_prismarine_wall", DARK_PRISMARINE_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.END_STONE, "end_stone_stairs", END_STONE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(END_STONE_STAIRS, "end_stone_slab", END_STONE_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(END_STONE_SLAB, "end_stone_wall", END_STONE_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(END_STONE_WALL, "choral_end_stone", CHORAL_END_STONE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHORAL_END_STONE, "choral_end_stone_stairs", CHORAL_END_STONE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHORAL_END_STONE_STAIRS, "choral_end_stone_slab", CHORAL_END_STONE_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHORAL_END_STONE_SLAB, "choral_end_stone_wall", CHORAL_END_STONE_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.END_STONE_BRICKS, "cracked_end_stone_bricks", CRACKED_END_STONE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.END_STONE_BRICK_WALL, "chiseled_end_stone_bricks", CHISELED_END_STONE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHISELED_END_STONE_BRICKS, "choral_end_stone_bricks", CHORAL_END_STONE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHORAL_END_STONE_BRICKS, "choral_end_stone_brick_stairs", CHORAL_END_STONE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHORAL_END_STONE_BRICK_STAIRS, "choral_end_stone_brick_slab", CHORAL_END_STONE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CHORAL_END_STONE_BRICK_SLAB, "choral_end_stone_brick_wall", CHORAL_END_STONE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.PURPUR_BLOCK, "cracked_purpur_block", CRACKED_PURPUR_BLOCK, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.PURPUR_SLAB, "purpur_wall", PURPUR_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PURPUR_WALL, "chiseled_purpur_block", CHISELED_PURPUR_BLOCK, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Blocks.TRIAL_SPAWNER, "coffin", COFFIN, CreativeModeTabs.SPAWN_EGGS);
		registerBlockAfter(Blocks.OBSERVER, "surveyor", SURVEYOR, CreativeModeTabs.REDSTONE_BLOCKS);

		actualRegisterBlock("ectoplasm_block", ECTOPLASM_BLOCK);
	}

	public static void registerBlockProperties() {
		HopperApi.addBlacklistedType(TTBlockEntityTypes.COFFIN);
		ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(), COFFIN);
	}

	private static void registerBlock(String path, Block block) {
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockBefore(ItemLike comparedItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemBefore(comparedItem, path, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockAfter(ItemLike comparedItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemAfter(comparedItem, path, block, tabs);
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockItemBefore(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility tabVisibility, ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addBefore(comparedItem, block, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerBlockItemAfter(ItemLike comparedItem, String name, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemAfter(comparedItem, name, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerBlockItemAfter(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility visibility, ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addAfter(comparedItem, block, visibility, tabs);
	}

	private static void actualRegisterBlock(String path, Block block) {
		if (BuiltInRegistries.BLOCK.getOptional(TTConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.BLOCK, TTConstants.id(path), block);
		}
	}

	private static void actualRegisterBlockItem(String path, Block block) {
		if (BuiltInRegistries.ITEM.getOptional(TTConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, TTConstants.id(path), new BlockItem(block, new Item.Properties()));
		}
	}

}
