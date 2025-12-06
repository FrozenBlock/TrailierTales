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

import java.util.function.Function;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.storage.api.NoInteractionStorage;
import net.frozenblock.lib.block.storage.api.hopper.HopperApi;
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
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class TTBlocks {
	public static final Block SUSPICIOUS_RED_SAND = register("suspicious_red_sand",
		properties -> new BrushableBlock(
			Blocks.RED_SAND,
			SoundEvents.BRUSH_SAND,
			SoundEvents.BRUSH_SAND_COMPLETED,
			properties
		),
		Properties.of()
			.mapColor(MapColor.COLOR_ORANGE)
			.instrument(NoteBlockInstrument.SNARE)
			.strength(0.25F).sound(SoundType.SUSPICIOUS_SAND)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block SUSPICIOUS_DIRT = register("suspicious_dirt",
		properties -> new NonFallingBrushableBlock(
			Blocks.DIRT,
			TTSounds.BRUSH_DIRT,
			TTSounds.BRUSH_DIRT_COMPLETED,
			properties
		),
		Properties.of()
			.mapColor(MapColor.DIRT)
			.strength(0.25F)
			.sound(TTSounds.SUSPICIOUS_DIRT)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block SUSPICIOUS_CLAY = register("suspicious_clay",
		properties -> new NonFallingBrushableBlock(
			Blocks.CLAY,
			TTSounds.BRUSH_CLAY,
			TTSounds.BRUSH_CLAY_COMPLETED,
			properties
		),
		Properties.of()
			.mapColor(MapColor.CLAY)
			.instrument(NoteBlockInstrument.FLUTE)
			.strength(0.25F)
			.sound(TTSounds.SUSPICIOUS_CLAY)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block CYAN_ROSE_CROP = registerWithoutItem("cyan_rose_crop",
		CyanRoseCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CYAN_ROSE = register("cyan_rose",
		properties -> new FlowerBlock(MobEffects.SATURATION, 0.5F, properties),
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block POTTED_CYAN_ROSE = registerWithoutItem("potted_cyan_rose",
		properties -> new FlowerPotBlock(CYAN_ROSE, properties),
		Blocks.flowerPotProperties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block MANEDROP_CROP = registerWithoutItem("manedrop_crop",
		ManedropCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MANEDROP = register("manedrop",
		DoublePlantBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block DAWNTRAIL_CROP = registerWithoutItem("dawntrail_crop",
		DawntrailCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final MultifaceSpreadeableBlock DAWNTRAIL = register("dawntrail",
		DawntrailBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.randomTicks()
			.strength(0.2F)
			.sound(SoundType.VINE)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block GUZMANIA_CROP = registerWithoutItem("guzmania_crop",
		GuzmaniaCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block GUZMANIA = register("guzmania",
		DoublePlantBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block LITHOPS_CROP = registerWithoutItem("lithops_crop",
		LithopsCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final LithopsBlock LITHOPS = register("lithops",
		LithopsBlock::new,
		Properties.ofFullCopy(Blocks.PINK_PETALS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block POTTED_LITHOPS = registerWithoutItem("potted_lithops",
		properties -> new FlowerPotBlock(LITHOPS, properties),
		Blocks.flowerPotProperties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	// GRANITE

	public static final Block POLISHED_GRANITE_WALL = register("polished_granite_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.POLISHED_GRANITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block GRANITE_BRICKS = register("granite_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.GRANITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHISELED_GRANITE_BRICKS = register("chiseled_granite_bricks",
		Block::new,
		Properties.ofFullCopy(GRANITE_BRICKS)
	);
	public static final Block GRANITE_BRICK_STAIRS = register("granite_brick_stairs",
		properties -> new StairBlock(GRANITE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(GRANITE_BRICKS)
	);
	public static final Block GRANITE_BRICK_SLAB = register("granite_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(GRANITE_BRICKS)
	);
	public static final Block GRANITE_BRICK_WALL = register("granite_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(GRANITE_BRICKS)
	);
	public static final Block CRACKED_GRANITE_BRICKS = register("cracked_granite_bricks",
		Block::new,
		Properties.ofFullCopy(GRANITE_BRICKS)
	);
	public static final BlockFamily FAMILY_GRANITE_BRICK = BlockFamilies.familyBuilder(GRANITE_BRICKS)
		.stairs(GRANITE_BRICK_STAIRS)
		.slab(GRANITE_BRICK_SLAB)
		.wall(GRANITE_BRICK_WALL)
		.cracked(CRACKED_GRANITE_BRICKS)
		.chiseled(CHISELED_GRANITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_GRANITE_BRICKS = register("mossy_granite_bricks",
		Block::new,
		Properties.ofFullCopy(GRANITE_BRICKS)
	);
	public static final Block MOSSY_GRANITE_BRICK_STAIRS = register("mossy_granite_brick_stairs",
		properties -> new StairBlock(MOSSY_GRANITE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_GRANITE_BRICKS)
	);
	public static final Block MOSSY_GRANITE_BRICK_SLAB = register("mossy_granite_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_GRANITE_BRICKS)
	);
	public static final Block MOSSY_GRANITE_BRICK_WALL = register("mossy_granite_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_GRANITE_BRICKS)
	);
	public static final BlockFamily FAMILY_MOSSY_GRANITE_BRICK = BlockFamilies.familyBuilder(MOSSY_GRANITE_BRICKS)
		.stairs(MOSSY_GRANITE_BRICK_STAIRS)
		.slab(MOSSY_GRANITE_BRICK_SLAB)
		.wall(MOSSY_GRANITE_BRICK_WALL)
		.getFamily();

	// DIORITE

	public static final Block POLISHED_DIORITE_WALL = register("polished_diorite_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.POLISHED_DIORITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block DIORITE_BRICKS = register("diorite_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.DIORITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block DIORITE_BRICK_STAIRS = register("diorite_brick_stairs",
		properties -> new StairBlock(DIORITE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(DIORITE_BRICKS)
	);
	public static final Block DIORITE_BRICK_SLAB = register("diorite_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(DIORITE_BRICKS)
	);
	public static final Block DIORITE_BRICK_WALL = register("diorite_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(DIORITE_BRICKS)
	);
	public static final Block CRACKED_DIORITE_BRICKS = register("cracked_diorite_bricks",
		Block::new,
		Properties.ofFullCopy(DIORITE_BRICKS)
	);
	public static final Block CHISELED_DIORITE_BRICKS = register("chiseled_diorite_bricks",
		Block::new,
		Properties.ofFullCopy(DIORITE_BRICKS)
	);
	public static final BlockFamily FAMILY_DIORITE_BRICK = BlockFamilies.familyBuilder(DIORITE_BRICKS)
		.stairs(DIORITE_BRICK_STAIRS)
		.slab(DIORITE_BRICK_SLAB)
		.wall(DIORITE_BRICK_WALL)
		.cracked(CRACKED_DIORITE_BRICKS)
		.chiseled(CHISELED_DIORITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_DIORITE_BRICKS = register("mossy_diorite_bricks",
		Block::new,
		Properties.ofFullCopy(DIORITE_BRICKS)
	);
	public static final Block MOSSY_DIORITE_BRICK_STAIRS = register("mossy_diorite_brick_stairs",
		properties -> new StairBlock(MOSSY_DIORITE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_DIORITE_BRICKS)
	);
	public static final Block MOSSY_DIORITE_BRICK_SLAB = register("mossy_diorite_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_DIORITE_BRICKS)
	);
	public static final Block MOSSY_DIORITE_BRICK_WALL = register("mossy_diorite_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_DIORITE_BRICKS)
	);
	public static final BlockFamily FAMILY_MOSSY_DIORITE_BRICK = BlockFamilies.familyBuilder(MOSSY_DIORITE_BRICKS)
		.stairs(MOSSY_DIORITE_BRICK_STAIRS)
		.slab(MOSSY_DIORITE_BRICK_SLAB)
		.wall(MOSSY_DIORITE_BRICK_WALL)
		.getFamily();

	// ANDESITE

	public static final Block POLISHED_ANDESITE_WALL = register("polished_andesite_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.POLISHED_ANDESITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block ANDESITE_BRICKS = register("andesite_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.ANDESITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block ANDESITE_BRICK_STAIRS = register("andesite_brick_stairs",
		properties -> new StairBlock(ANDESITE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(ANDESITE_BRICKS)
	);
	public static final Block ANDESITE_BRICK_SLAB = register("andesite_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(ANDESITE_BRICKS)
	);
	public static final Block ANDESITE_BRICK_WALL = register("andesite_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(ANDESITE_BRICKS)
	);
	public static final Block CRACKED_ANDESITE_BRICKS = register("cracked_andesite_bricks",
		Block::new,
		Properties.ofFullCopy(ANDESITE_BRICKS)
	);
	public static final Block CHISELED_ANDESITE_BRICKS = register("chiseled_andesite_bricks",
		Block::new,
		Properties.ofFullCopy(ANDESITE_BRICKS)
	);
	public static final BlockFamily FAMILY_ANDESITE_BRICK = BlockFamilies.familyBuilder(ANDESITE_BRICKS)
		.stairs(ANDESITE_BRICK_STAIRS)
		.slab(ANDESITE_BRICK_SLAB)
		.wall(ANDESITE_BRICK_WALL)
		.cracked(CRACKED_ANDESITE_BRICKS)
		.chiseled(CHISELED_ANDESITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_ANDESITE_BRICKS = register("mossy_andesite_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.ANDESITE)
	);
	public static final Block MOSSY_ANDESITE_BRICK_STAIRS = register("mossy_andesite_brick_stairs",
		properties -> new StairBlock(MOSSY_ANDESITE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_ANDESITE_BRICKS)
	);
	public static final Block MOSSY_ANDESITE_BRICK_SLAB = register("mossy_andesite_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_ANDESITE_BRICKS)
	);
	public static final Block MOSSY_ANDESITE_BRICK_WALL = register("mossy_andesite_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_ANDESITE_BRICKS)
	);
	public static final BlockFamily FAMILY_MOSSY_ANDESITE_BRICK = BlockFamilies.familyBuilder(MOSSY_ANDESITE_BRICKS)
		.stairs(MOSSY_ANDESITE_BRICK_STAIRS)
		.slab(MOSSY_ANDESITE_BRICK_SLAB)
		.wall(MOSSY_ANDESITE_BRICK_WALL)
		.getFamily();

	// CALCITE

	public static final Block CALCITE_STAIRS = register("calcite_stairs",
		properties -> new StairBlock(Blocks.CALCITE.defaultBlockState(), properties),
		Properties.ofFullCopy(Blocks.CALCITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CALCITE_SLAB = register("calcite_slab",
		SlabBlock::new,
		Properties.ofFullCopy(Blocks.CALCITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CALCITE_WALL = register("calcite_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.CALCITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block POLISHED_CALCITE = register("polished_calcite",
		Block::new,
		Properties.ofFullCopy(Blocks.CALCITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block POLISHED_CALCITE_STAIRS = register("polished_calcite_stairs",
		properties -> new StairBlock(Blocks.CALCITE.defaultBlockState(), properties),
		Properties.ofFullCopy(Blocks.CALCITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block POLISHED_CALCITE_SLAB = register("polished_calcite_slab",
		SlabBlock::new,
		Properties.ofFullCopy(POLISHED_CALCITE)
	);
	public static final Block POLISHED_CALCITE_WALL = register("polished_calcite_wall",
		WallBlock::new,
		Properties.ofFullCopy(POLISHED_CALCITE)
	);
	public static final BlockFamily FAMILY_POLISHED_CALCITE = BlockFamilies.familyBuilder(POLISHED_CALCITE)
		.stairs(POLISHED_CALCITE_STAIRS)
		.slab(POLISHED_CALCITE_SLAB)
		.wall(POLISHED_CALCITE_WALL)
		.getFamily();

	public static final Block CALCITE_BRICKS = register("calcite_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.CALCITE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
			.sound(TTSounds.CALCITE_BRICKS)
	);
	public static final Block CALCITE_BRICK_STAIRS = register("calcite_brick_stairs",
		properties -> new StairBlock(CALCITE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(CALCITE_BRICKS)
	);
	public static final Block CALCITE_BRICK_SLAB = register("calcite_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(CALCITE_BRICKS)
	);
	public static final Block CALCITE_BRICK_WALL = register("calcite_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(CALCITE_BRICKS)
	);
	public static final Block CRACKED_CALCITE_BRICKS = register("cracked_calcite_bricks",
		Block::new,
		Properties.ofFullCopy(CALCITE_BRICKS)
	);
	public static final Block CHISELED_CALCITE_BRICKS = register("chiseled_calcite_bricks",
		Block::new,
		Properties.ofFullCopy(CALCITE_BRICKS)
	);
	public static final BlockFamily FAMILY_CALCITE_BRICK = BlockFamilies.familyBuilder(CALCITE_BRICKS)
		.stairs(CALCITE_BRICK_STAIRS)
		.slab(CALCITE_BRICK_SLAB)
		.wall(CALCITE_BRICK_WALL)
		.cracked(CRACKED_CALCITE_BRICKS)
		.chiseled(CHISELED_CALCITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_CALCITE_BRICKS = register("mossy_calcite_bricks",
		Block::new,
		Properties.ofFullCopy(CALCITE_BRICKS)
	);
	public static final Block MOSSY_CALCITE_BRICK_STAIRS = register("mossy_calcite_brick_stairs",
		properties -> new StairBlock(MOSSY_CALCITE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_CALCITE_BRICKS)
	);
	public static final Block MOSSY_CALCITE_BRICK_SLAB = register("mossy_calcite_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_CALCITE_BRICKS)
	);
	public static final Block MOSSY_CALCITE_BRICK_WALL = register("mossy_calcite_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_CALCITE_BRICKS)
	);
	public static final BlockFamily FAMILY_MOSSY_CALCITE_BRICK = BlockFamilies.familyBuilder(MOSSY_CALCITE_BRICKS)
		.stairs(MOSSY_CALCITE_BRICK_STAIRS)
		.slab(MOSSY_CALCITE_BRICK_SLAB)
		.wall(MOSSY_CALCITE_BRICK_WALL)
		.getFamily();

	// TUFF

	public static final Block CRACKED_TUFF_BRICKS = register("cracked_tuff_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.TUFF_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_TUFF_BRICKS = register("mossy_tuff_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.TUFF_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_TUFF_BRICK_STAIRS = register("mossy_tuff_brick_stairs",
		properties -> new StairBlock(MOSSY_TUFF_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_TUFF_BRICKS)
	);
	public static final Block MOSSY_TUFF_BRICK_SLAB = register("mossy_tuff_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_TUFF_BRICKS)
	);
	public static final Block MOSSY_TUFF_BRICK_WALL = register("mossy_tuff_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_TUFF_BRICKS)
	);
	public static final BlockFamily FAMILY_MOSSY_TUFF_BRICKS = BlockFamilies.familyBuilder(MOSSY_TUFF_BRICKS)
		.stairs(MOSSY_TUFF_BRICK_STAIRS)
		.slab(MOSSY_TUFF_BRICK_SLAB)
		.wall(MOSSY_TUFF_BRICK_WALL)
		.getFamily();

	// BRICKS

	public static final Block CRACKED_BRICKS = register("cracked_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_BRICKS = register("mossy_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_BRICK_STAIRS = register("mossy_brick_stairs",
		properties -> new StairBlock(MOSSY_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_BRICKS)
	);
	public static final Block MOSSY_BRICK_SLAB = register("mossy_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_BRICKS)
	);
	public static final Block MOSSY_BRICK_WALL = register("mossy_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_BRICKS)
	);
	public static final BlockFamily FAMILY_MOSSY_BRICKS = BlockFamilies.familyBuilder(MOSSY_BRICKS)
		.stairs(MOSSY_BRICK_STAIRS)
		.slab(MOSSY_BRICK_SLAB)
		.wall(MOSSY_BRICK_WALL)
		.getFamily();

	// RESIN
	public static final Block POLISHED_RESIN_BLOCK = register("polished_resin_block",
		Block::new,
		Properties.ofFullCopy(Blocks.RESIN_BLOCK)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block CRACKED_RESIN_BRICKS = register("cracked_resin_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.RESIN_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block PALE_MOSSY_RESIN_BRICKS = register("pale_mossy_resin_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.RESIN_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block PALE_MOSSY_RESIN_BRICK_STAIRS = register("pale_mossy_resin_brick_stairs",
		properties -> new StairBlock(PALE_MOSSY_RESIN_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(PALE_MOSSY_RESIN_BRICKS)
	);
	public static final Block PALE_MOSSY_RESIN_BRICK_SLAB = register("pale_mossy_resin_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(PALE_MOSSY_RESIN_BRICKS)
	);
	public static final Block PALE_MOSSY_RESIN_BRICK_WALL = register("pale_mossy_resin_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(PALE_MOSSY_RESIN_BRICKS)
	);
	public static final BlockFamily FAMILY_PALE_MOSSY_RESIN_BRICKS = BlockFamilies.familyBuilder(PALE_MOSSY_RESIN_BRICKS)
		.stairs(PALE_MOSSY_RESIN_BRICK_STAIRS)
		.slab(PALE_MOSSY_RESIN_BRICK_SLAB)
		.wall(PALE_MOSSY_RESIN_BRICK_WALL)
		.getFamily();

	// MOSSY DEEPSLATE

	public static final Block MOSSY_COBBLED_DEEPSLATE = register("mossy_cobbled_deepslate",
		Block::new,
		Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_COBBLED_DEEPSLATE_STAIRS = register("mossy_cobbled_deepslate_stairs",
		properties -> new StairBlock(MOSSY_COBBLED_DEEPSLATE.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_COBBLED_DEEPSLATE)
	);
	public static final Block MOSSY_COBBLED_DEEPSLATE_SLAB = register("mossy_cobbled_deepslate_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_COBBLED_DEEPSLATE)
	);
	public static final Block MOSSY_COBBLED_DEEPSLATE_WALL = register("mossy_cobbled_deepslate_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_COBBLED_DEEPSLATE)
	);
	public static final BlockFamily FAMILY_MOSSY_COBBLED_DEEPSLATE = BlockFamilies.familyBuilder(MOSSY_COBBLED_DEEPSLATE)
		.stairs(MOSSY_COBBLED_DEEPSLATE_STAIRS)
		.slab(MOSSY_COBBLED_DEEPSLATE_SLAB)
		.wall(MOSSY_COBBLED_DEEPSLATE_WALL)
		.getFamily();

	public static final Block MOSSY_DEEPSLATE_BRICKS = register("mossy_deepslate_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_DEEPSLATE_BRICK_STAIRS = register("mossy_deepslate_brick_stairs",
		properties -> new StairBlock(MOSSY_DEEPSLATE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_DEEPSLATE_BRICKS)
	);
	public static final Block MOSSY_DEEPSLATE_BRICK_SLAB = register("mossy_deepslate_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_DEEPSLATE_BRICKS)
	);
	public static final Block MOSSY_DEEPSLATE_BRICK_WALL = register("mossy_deepslate_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_DEEPSLATE_BRICKS)
	);
	public static final BlockFamily FAMILY_MOSSY_DEEPSLATE_BRICKS = BlockFamilies.familyBuilder(MOSSY_DEEPSLATE_BRICKS)
		.stairs(MOSSY_DEEPSLATE_BRICK_STAIRS)
		.slab(MOSSY_DEEPSLATE_BRICK_SLAB)
		.wall(MOSSY_DEEPSLATE_BRICK_WALL)
		.getFamily();

	public static final Block MOSSY_DEEPSLATE_TILES = register("mossy_deepslate_tiles",
		Block::new,
		Properties.ofFullCopy(Blocks.DEEPSLATE_TILES)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block MOSSY_DEEPSLATE_TILE_STAIRS = register("mossy_deepslate_tile_stairs",
		properties -> new StairBlock(MOSSY_DEEPSLATE_TILES.defaultBlockState(), properties),
		Properties.ofFullCopy(Blocks.DEEPSLATE_TILE_STAIRS)
	);
	public static final Block MOSSY_DEEPSLATE_TILE_SLAB = register("mossy_deepslate_tile_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_DEEPSLATE_TILES)
	);
	public static final Block MOSSY_DEEPSLATE_TILE_WALL = register("mossy_deepslate_tile_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_DEEPSLATE_TILES)
	);
	public static final BlockFamily FAMILY_MOSSY_DEEPSLATE_TILES = BlockFamilies.familyBuilder(MOSSY_DEEPSLATE_TILES)
		.stairs(MOSSY_DEEPSLATE_TILE_STAIRS)
		.slab(MOSSY_DEEPSLATE_TILE_SLAB)
		.wall(MOSSY_DEEPSLATE_TILE_WALL)
		.getFamily();

	// SANDSTONE

	public static final Block SMOOTH_SANDSTONE_WALL = register("smooth_sandstone_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.SMOOTH_SANDSTONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block CUT_SANDSTONE_STAIRS = register("cut_sandstone_stairs",
		properties -> new StairBlock(Blocks.CUT_SANDSTONE.defaultBlockState(), properties),
		Properties.ofFullCopy(Blocks.CUT_SANDSTONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CUT_SANDSTONE_WALL = register("cut_sandstone_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.CUT_SANDSTONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	// RED SANDSTONE

	public static final Block SMOOTH_RED_SANDSTONE_WALL = register("smooth_red_sandstone_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.SMOOTH_RED_SANDSTONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block CUT_RED_SANDSTONE_STAIRS = register("cut_red_sandstone_stairs",
		properties -> new StairBlock(Blocks.CUT_RED_SANDSTONE.defaultBlockState(), properties),
		Properties.ofFullCopy(Blocks.CUT_RED_SANDSTONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CUT_RED_SANDSTONE_WALL = register("cut_red_sandstone_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.CUT_RED_SANDSTONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	// PRISMARINE

	public static final Block PRISMARINE_BRICK_WALL = register("prismarine_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block DARK_PRISMARINE_WALL = register("dark_prismarine_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.DARK_PRISMARINE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	// END STONE

	public static final Block END_STONE_STAIRS = register("end_stone_stairs",
		properties -> new StairBlock(Blocks.END_STONE.defaultBlockState(), properties),
		Properties.ofFullCopy(Blocks.END_STONE)
	);
	public static final Block END_STONE_SLAB = register("end_stone_slab",
		SlabBlock::new,
		Properties.ofFullCopy(Blocks.END_STONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block END_STONE_WALL = register("end_stone_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.END_STONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Block CHORAL_END_STONE = register("choral_end_stone",
		Block::new,
		Properties.ofFullCopy(Blocks.END_STONE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHORAL_END_STONE_STAIRS = register("choral_end_stone_stairs",
		properties -> new StairBlock(CHORAL_END_STONE.defaultBlockState(), properties),
		Properties.ofFullCopy(CHORAL_END_STONE)
	);
	public static final Block CHORAL_END_STONE_SLAB = register("choral_end_stone_slab",
		SlabBlock::new,
		Properties.ofFullCopy(CHORAL_END_STONE)
	);
	public static final Block CHORAL_END_STONE_WALL = register("choral_end_stone_wall",
		WallBlock::new,
		Properties.ofFullCopy(CHORAL_END_STONE)
	);
	public static final BlockFamily FAMILY_CHORAL_END_STONE = BlockFamilies.familyBuilder(CHORAL_END_STONE)
		.stairs(CHORAL_END_STONE_STAIRS)
		.slab(CHORAL_END_STONE_SLAB)
		.wall(CHORAL_END_STONE_WALL)
		.getFamily();

	public static final Block CRACKED_END_STONE_BRICKS = register("cracked_end_stone_bricks",
		Block::new,Properties.ofFullCopy(Blocks.END_STONE_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHISELED_END_STONE_BRICKS = register("chiseled_end_stone_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.END_STONE_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHORAL_END_STONE_BRICKS = register("choral_end_stone_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.END_STONE_BRICKS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHORAL_END_STONE_BRICK_STAIRS = register("choral_end_stone_brick_stairs",
		properties -> new StairBlock(CHORAL_END_STONE_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(CHORAL_END_STONE_BRICKS)
	);
	public static final Block CHORAL_END_STONE_BRICK_SLAB = register("choral_end_stone_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(CHORAL_END_STONE_BRICKS)
	);
	public static final Block CHORAL_END_STONE_BRICK_WALL = register("choral_end_stone_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(CHORAL_END_STONE_BRICKS)
	);
	public static final BlockFamily FAMILY_CHORAL_END_STONE_BRICKS = BlockFamilies.familyBuilder(CHORAL_END_STONE_BRICKS)
		.stairs(CHORAL_END_STONE_BRICK_STAIRS)
		.slab(CHORAL_END_STONE_BRICK_SLAB)
		.wall(CHORAL_END_STONE_BRICK_WALL)
		.getFamily();

	// PURPUR

	public static final Block CRACKED_PURPUR_BLOCK = register("cracked_purpur_block",
		Block::new,
		Properties.ofFullCopy(Blocks.PURPUR_BLOCK)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block PURPUR_WALL = register("purpur_wall",
		WallBlock::new,
		Properties.ofFullCopy(Blocks.PURPUR_BLOCK)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Block CHISELED_PURPUR_BLOCK = register("chiseled_purpur_block",
		Block::new,
		Properties.ofFullCopy(Blocks.PURPUR_BLOCK)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final CoffinBlock COFFIN = register("coffin",
		CoffinBlock::new,
		Properties.of()
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

	public static final SurveyorBlock SURVEYOR = register("surveyor",
		SurveyorBlock::new,
		Properties.of()
			.mapColor(MapColor.STONE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(3F)
			.requiresCorrectToolForDrops()
			.isRedstoneConductor(Blocks::never)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final EctoplasmBlock ECTOPLASM_BLOCK = registerWithoutItem("ectoplasm_block",
		EctoplasmBlock::new,
		Properties.of()
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
	}

	public static void registerBlockProperties() {
		HopperApi.addBlacklistedType(TTBlockEntityTypes.COFFIN);
		ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(), COFFIN);
	}

	private static <T extends Block> T registerWithoutItem(String path, Function<Properties, T> block, Properties properties) {
		final Identifier id = TTConstants.id(path);
		return doRegister(id, makeBlock(block, properties, id));
	}

	private static <T extends Block> T register(String path, Function<Properties, T> block, Properties properties) {
		final T registered = registerWithoutItem(path, block, properties);
		Items.registerBlock(registered);
		return registered;
	}

	private static <T extends Block> T doRegister(Identifier id, T block) {
		if (BuiltInRegistries.BLOCK.getOptional(id).isEmpty()) return Registry.register(BuiltInRegistries.BLOCK, id, block);
		throw new IllegalArgumentException("Block with id " + id + " is already in the block registry.");
	}

	private static <T extends Block> T makeBlock(Function<Properties, T> function, Properties properties, Identifier id) {
		return function.apply(properties.setId(ResourceKey.create(Registries.BLOCK, id)));
	}

}
