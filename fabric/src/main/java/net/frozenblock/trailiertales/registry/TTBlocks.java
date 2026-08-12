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

package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.api.storage.hopper.HopperApi;
import net.frozenblock.lib.block.storage.api.NoInteractionStorage;
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
import net.frozenblock.trailiertales.references.TTBlockIds;
import net.frozenblock.trailiertales.references.TTBlockItemIds;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class TTBlocks {
	// SUSPICIOUS BLOCKS
	public static final Block SUSPICIOUS_RED_SAND = Blocks.register(TTBlockItemIds.SUSPICIOUS_RED_SAND,
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
	);
	public static final Block SUSPICIOUS_DIRT = Blocks.register(TTBlockItemIds.SUSPICIOUS_DIRT,
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
	);
	public static final Block SUSPICIOUS_CLAY = Blocks.register(TTBlockItemIds.SUSPICIOUS_CLAY,
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
	);

	// PLANTS
	public static final Block CYAN_ROSE_CROP = Blocks.register(TTBlockItemIds.CYAN_ROSE_CROP,
		CyanRoseCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block CYAN_ROSE = Blocks.register(TTBlockItemIds.CYAN_ROSE,
		properties -> new FlowerBlock(MobEffects.SATURATION, 0.5F, properties),
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block POTTED_CYAN_ROSE = registerFlowerPot(TTBlockIds.POTTED_CYAN_ROSE, CYAN_ROSE);

	public static final Block MANEDROP_CROP = Blocks.register(TTBlockItemIds.MANEDROP_CROP,
		ManedropCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block MANEDROP = Blocks.register(TTBlockItemIds.MANEDROP,
		DoublePlantBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
	);

	public static final Block DAWNTRAIL_CROP = Blocks.register(TTBlockItemIds.DAWNTRAIL_CROP,
		DawntrailCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block DAWNTRAIL = Blocks.register(TTBlockItemIds.DAWNTRAIL,
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
	);

	public static final Block GUZMANIA_CROP = Blocks.register(TTBlockItemIds.GUZMANIA_CROP,
		GuzmaniaCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block GUZMANIA = Blocks.register(TTBlockItemIds.GUZMANIA,
		DoublePlantBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
	);

	public static final Block LITHOPS_CROP = Blocks.register(TTBlockItemIds.LITHOPS_CROP,
		LithopsCropBlock::new,
		Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block LITHOPS = Blocks.register(TTBlockItemIds.LITHOPS, LithopsBlock::new, Properties.ofFullCopy(Blocks.PINK_PETALS));
	public static final Block POTTED_LITHOPS = registerFlowerPot(TTBlockIds.POTTED_LITHOPS, LITHOPS);

	// STONE
	public static final Block STONE_WALL = Blocks.registerWall(TTBlockItemIds.STONE_WALL, Blocks.STONE);

	// GRANITE
	public static final Block POLISHED_GRANITE_WALL = Blocks.registerWall(TTBlockItemIds.POLISHED_GRANITE_WALL, Blocks.POLISHED_GRANITE);

	public static final Block GRANITE_BRICKS = Blocks.register(TTBlockItemIds.GRANITE_BRICKS, Properties.ofFullCopy(Blocks.GRANITE));
	public static final Block CHISELED_GRANITE_BRICKS = Blocks.register(TTBlockItemIds.CHISELED_GRANITE_BRICKS, Properties.ofFullCopy(GRANITE_BRICKS));
	public static final Block GRANITE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.GRANITE_BRICK_STAIRS, GRANITE_BRICKS);
	public static final Block GRANITE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.GRANITE_BRICK_SLAB, GRANITE_BRICKS);
	public static final Block GRANITE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.GRANITE_BRICK_WALL, GRANITE_BRICKS);
	public static final Block CRACKED_GRANITE_BRICKS = Blocks.register(TTBlockItemIds.CRACKED_GRANITE_BRICKS, Properties.ofFullCopy(GRANITE_BRICKS));
	public static final BlockFamily FAMILY_GRANITE_BRICK = BlockFamilies.familyBuilder(GRANITE_BRICKS)
		.stairs(GRANITE_BRICK_STAIRS)
		.slab(GRANITE_BRICK_SLAB)
		.wall(GRANITE_BRICK_WALL)
		.cracked(CRACKED_GRANITE_BRICKS)
		.chiseled(CHISELED_GRANITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_GRANITE_BRICKS = Blocks.register(TTBlockItemIds.MOSSY_GRANITE_BRICKS, Properties.ofFullCopy(GRANITE_BRICKS));
	public static final Block MOSSY_GRANITE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_GRANITE_BRICK_STAIRS, MOSSY_GRANITE_BRICKS);
	public static final Block MOSSY_GRANITE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_GRANITE_BRICK_SLAB, MOSSY_GRANITE_BRICKS);
	public static final Block MOSSY_GRANITE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_GRANITE_BRICK_WALL, MOSSY_GRANITE_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_GRANITE_BRICK = BlockFamilies.familyBuilder(MOSSY_GRANITE_BRICKS)
		.stairs(MOSSY_GRANITE_BRICK_STAIRS)
		.slab(MOSSY_GRANITE_BRICK_SLAB)
		.wall(MOSSY_GRANITE_BRICK_WALL)
		.getFamily();

	// DIORITE
	public static final Block POLISHED_DIORITE_WALL = Blocks.registerWall(TTBlockItemIds.POLISHED_DIORITE_WALL, Blocks.POLISHED_DIORITE);

	public static final Block DIORITE_BRICKS = Blocks.register(TTBlockItemIds.DIORITE_BRICKS, Properties.ofFullCopy(Blocks.DIORITE));
	public static final Block CHISELED_DIORITE_BRICKS = Blocks.register(TTBlockItemIds.CHISELED_DIORITE_BRICKS, Properties.ofFullCopy(DIORITE_BRICKS));
	public static final Block DIORITE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.DIORITE_BRICK_STAIRS, DIORITE_BRICKS);
	public static final Block DIORITE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.DIORITE_BRICK_SLAB, DIORITE_BRICKS);
	public static final Block DIORITE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.DIORITE_BRICK_WALL, DIORITE_BRICKS);
	public static final Block CRACKED_DIORITE_BRICKS = Blocks.register(TTBlockItemIds.CRACKED_DIORITE_BRICKS, Properties.ofFullCopy(DIORITE_BRICKS));
	public static final BlockFamily FAMILY_DIORITE_BRICK = BlockFamilies.familyBuilder(DIORITE_BRICKS)
		.stairs(DIORITE_BRICK_STAIRS)
		.slab(DIORITE_BRICK_SLAB)
		.wall(DIORITE_BRICK_WALL)
		.cracked(CRACKED_DIORITE_BRICKS)
		.chiseled(CHISELED_DIORITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_DIORITE_BRICKS = Blocks.register(TTBlockItemIds.MOSSY_DIORITE_BRICKS, Properties.ofFullCopy(DIORITE_BRICKS));
	public static final Block MOSSY_DIORITE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_DIORITE_BRICK_STAIRS, MOSSY_DIORITE_BRICKS);
	public static final Block MOSSY_DIORITE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_DIORITE_BRICK_SLAB, MOSSY_DIORITE_BRICKS);
	public static final Block MOSSY_DIORITE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_DIORITE_BRICK_WALL, MOSSY_DIORITE_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_DIORITE_BRICK = BlockFamilies.familyBuilder(MOSSY_DIORITE_BRICKS)
		.stairs(MOSSY_DIORITE_BRICK_STAIRS)
		.slab(MOSSY_DIORITE_BRICK_SLAB)
		.wall(MOSSY_DIORITE_BRICK_WALL)
		.getFamily();

	// ANDESITE
	public static final Block POLISHED_ANDESITE_WALL = Blocks.registerWall(TTBlockItemIds.POLISHED_ANDESITE_WALL, Blocks.POLISHED_ANDESITE);

	public static final Block ANDESITE_BRICKS = Blocks.register(TTBlockItemIds.ANDESITE_BRICKS, Properties.ofFullCopy(Blocks.ANDESITE));
	public static final Block CHISELED_ANDESITE_BRICKS = Blocks.register(TTBlockItemIds.CHISELED_ANDESITE_BRICKS, Properties.ofFullCopy(ANDESITE_BRICKS));
	public static final Block ANDESITE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.ANDESITE_BRICK_STAIRS, ANDESITE_BRICKS);
	public static final Block ANDESITE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.ANDESITE_BRICK_SLAB, ANDESITE_BRICKS);
	public static final Block ANDESITE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.ANDESITE_BRICK_WALL, ANDESITE_BRICKS);
	public static final Block CRACKED_ANDESITE_BRICKS = Blocks.register(TTBlockItemIds.CRACKED_ANDESITE_BRICKS, Properties.ofFullCopy(ANDESITE_BRICKS));
	public static final BlockFamily FAMILY_ANDESITE_BRICK = BlockFamilies.familyBuilder(ANDESITE_BRICKS)
		.stairs(ANDESITE_BRICK_STAIRS)
		.slab(ANDESITE_BRICK_SLAB)
		.wall(ANDESITE_BRICK_WALL)
		.cracked(CRACKED_ANDESITE_BRICKS)
		.chiseled(CHISELED_ANDESITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_ANDESITE_BRICKS = Blocks.register(TTBlockItemIds.MOSSY_ANDESITE_BRICKS, Properties.ofFullCopy(ANDESITE_BRICKS));
	public static final Block MOSSY_ANDESITE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_ANDESITE_BRICK_STAIRS, MOSSY_ANDESITE_BRICKS);
	public static final Block MOSSY_ANDESITE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_ANDESITE_BRICK_SLAB, MOSSY_ANDESITE_BRICKS);
	public static final Block MOSSY_ANDESITE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_ANDESITE_BRICK_WALL, MOSSY_ANDESITE_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_ANDESITE_BRICK = BlockFamilies.familyBuilder(MOSSY_ANDESITE_BRICKS)
		.stairs(MOSSY_ANDESITE_BRICK_STAIRS)
		.slab(MOSSY_ANDESITE_BRICK_SLAB)
		.wall(MOSSY_ANDESITE_BRICK_WALL)
		.getFamily();

	// CALCITE
	public static final Block CALCITE_STAIRS = Blocks.registerStair(TTBlockItemIds.CALCITE_STAIRS, Blocks.CALCITE);
	public static final Block CALCITE_SLAB = Blocks.registerSlab(TTBlockItemIds.CALCITE_SLAB, Blocks.CALCITE);
	public static final Block CALCITE_WALL = Blocks.registerWall(TTBlockItemIds.CALCITE_WALL, Blocks.CALCITE);

	public static final Block POLISHED_CALCITE = Blocks.register(TTBlockItemIds.POLISHED_CALCITE, Properties.ofFullCopy(Blocks.CALCITE));
	public static final Block POLISHED_CALCITE_STAIRS = Blocks.registerStair(TTBlockItemIds.POLISHED_CALCITE_STAIRS, Blocks.CALCITE);
	public static final Block POLISHED_CALCITE_SLAB = Blocks.registerSlab(TTBlockItemIds.POLISHED_CALCITE_SLAB, POLISHED_CALCITE);
	public static final Block POLISHED_CALCITE_WALL = Blocks.registerWall(TTBlockItemIds.POLISHED_CALCITE_WALL, POLISHED_CALCITE);
	public static final BlockFamily FAMILY_POLISHED_CALCITE = BlockFamilies.familyBuilder(POLISHED_CALCITE)
		.stairs(POLISHED_CALCITE_STAIRS)
		.slab(POLISHED_CALCITE_SLAB)
		.wall(POLISHED_CALCITE_WALL)
		.getFamily();

	public static final Block CALCITE_BRICKS = Blocks.register(TTBlockItemIds.CALCITE_BRICKS,
		Properties.ofFullCopy(Blocks.CALCITE).sound(TTSounds.CALCITE_BRICKS)
	);
	public static final Block CALCITE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.CALCITE_BRICK_STAIRS, CALCITE_BRICKS);
	public static final Block CALCITE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.CALCITE_BRICK_SLAB, CALCITE_BRICKS);
	public static final Block CALCITE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.CALCITE_BRICK_WALL, CALCITE_BRICKS);
	public static final Block CRACKED_CALCITE_BRICKS = Blocks.register(TTBlockItemIds.CRACKED_CALCITE_BRICKS, Properties.ofFullCopy(CALCITE_BRICKS));
	public static final Block CHISELED_CALCITE_BRICKS = Blocks.register(TTBlockItemIds.CHISELED_CALCITE_BRICKS, Properties.ofFullCopy(CALCITE_BRICKS));
	public static final BlockFamily FAMILY_CALCITE_BRICK = BlockFamilies.familyBuilder(CALCITE_BRICKS)
		.stairs(CALCITE_BRICK_STAIRS)
		.slab(CALCITE_BRICK_SLAB)
		.wall(CALCITE_BRICK_WALL)
		.cracked(CRACKED_CALCITE_BRICKS)
		.chiseled(CHISELED_CALCITE_BRICKS)
		.getFamily();

	public static final Block MOSSY_CALCITE_BRICKS = Blocks.register(TTBlockItemIds.MOSSY_CALCITE_BRICKS, Properties.ofFullCopy(CALCITE_BRICKS));
	public static final Block MOSSY_CALCITE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_CALCITE_BRICK_STAIRS, MOSSY_CALCITE_BRICKS);
	public static final Block MOSSY_CALCITE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_CALCITE_BRICK_SLAB, MOSSY_CALCITE_BRICKS);
	public static final Block MOSSY_CALCITE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_CALCITE_BRICK_WALL, MOSSY_CALCITE_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_CALCITE_BRICK = BlockFamilies.familyBuilder(MOSSY_CALCITE_BRICKS)
		.stairs(MOSSY_CALCITE_BRICK_STAIRS)
		.slab(MOSSY_CALCITE_BRICK_SLAB)
		.wall(MOSSY_CALCITE_BRICK_WALL)
		.getFamily();

	// TUFF
	public static final Block CRACKED_TUFF_BRICKS = Blocks.register(TTBlockItemIds.CRACKED_TUFF_BRICKS, Properties.ofFullCopy(Blocks.TUFF_BRICKS));
	public static final Block MOSSY_TUFF_BRICKS = Blocks.register(TTBlockItemIds.MOSSY_TUFF_BRICKS, Properties.ofFullCopy(Blocks.TUFF_BRICKS));
	public static final Block MOSSY_TUFF_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_TUFF_BRICK_STAIRS, MOSSY_TUFF_BRICKS);
	public static final Block MOSSY_TUFF_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_TUFF_BRICK_SLAB, MOSSY_TUFF_BRICKS);
	public static final Block MOSSY_TUFF_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_TUFF_BRICK_WALL, MOSSY_TUFF_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_TUFF_BRICKS = BlockFamilies.familyBuilder(MOSSY_TUFF_BRICKS)
		.stairs(MOSSY_TUFF_BRICK_STAIRS)
		.slab(MOSSY_TUFF_BRICK_SLAB)
		.wall(MOSSY_TUFF_BRICK_WALL)
		.getFamily();

	// BRICKS
	public static final Block CRACKED_BRICKS = Blocks.register(TTBlockItemIds.CRACKED_BRICKS, Properties.ofFullCopy(Blocks.BRICKS));
	public static final Block MOSSY_BRICKS = Blocks.register(TTBlockItemIds.MOSSY_BRICKS,Properties.ofFullCopy(Blocks.BRICKS));
	public static final Block MOSSY_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_BRICK_STAIRS, MOSSY_BRICKS);
	public static final Block MOSSY_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_BRICK_SLAB, MOSSY_BRICKS);
	public static final Block MOSSY_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_BRICK_WALL, MOSSY_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_BRICKS = BlockFamilies.familyBuilder(MOSSY_BRICKS)
		.stairs(MOSSY_BRICK_STAIRS)
		.slab(MOSSY_BRICK_SLAB)
		.wall(MOSSY_BRICK_WALL)
		.getFamily();

	// RESIN
	public static final Block POLISHED_RESIN_BLOCK = Blocks.register(TTBlockItemIds.POLISHED_RESIN_BLOCK, Properties.ofFullCopy(Blocks.RESIN_BLOCK));
	public static final Block POLISHED_RESIN_STAIRS = Blocks.registerStair(TTBlockItemIds.POLISHED_RESIN_STAIRS, POLISHED_RESIN_BLOCK);
	public static final Block POLISHED_RESIN_SLAB = Blocks.registerSlab(TTBlockItemIds.POLISHED_RESIN_SLAB, POLISHED_RESIN_BLOCK);
	public static final Block POLISHED_RESIN_WALL = Blocks.registerWall(TTBlockItemIds.POLISHED_RESIN_WALL, POLISHED_RESIN_BLOCK);
	public static final BlockFamily FAMILY_POLISHED_RESIN = BlockFamilies.familyBuilder(POLISHED_RESIN_BLOCK)
		.stairs(POLISHED_RESIN_STAIRS)
		.slab(POLISHED_RESIN_SLAB)
		.wall(POLISHED_RESIN_WALL)
		.getFamily();

	public static final Block CRACKED_RESIN_BRICKS = Blocks.register(TTBlockItemIds.CRACKED_RESIN_BRICKS, Properties.ofFullCopy(Blocks.RESIN_BRICKS));

	public static final Block PALE_MOSSY_RESIN_BRICKS = Blocks.register(TTBlockItemIds.PALE_MOSSY_RESIN_BRICKS, Properties.ofFullCopy(Blocks.RESIN_BRICKS));
	public static final Block PALE_MOSSY_RESIN_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_STAIRS, PALE_MOSSY_RESIN_BRICKS);
	public static final Block PALE_MOSSY_RESIN_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_SLAB, PALE_MOSSY_RESIN_BRICKS);
	public static final Block PALE_MOSSY_RESIN_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_WALL, PALE_MOSSY_RESIN_BRICKS);
	public static final BlockFamily FAMILY_PALE_MOSSY_RESIN_BRICKS = BlockFamilies.familyBuilder(PALE_MOSSY_RESIN_BRICKS)
		.stairs(PALE_MOSSY_RESIN_BRICK_STAIRS)
		.slab(PALE_MOSSY_RESIN_BRICK_SLAB)
		.wall(PALE_MOSSY_RESIN_BRICK_WALL)
		.getFamily();

	// MOSSY DEEPSLATE
	public static final Block MOSSY_COBBLED_DEEPSLATE = Blocks.register(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE, Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE));
	public static final Block MOSSY_COBBLED_DEEPSLATE_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_STAIRS, MOSSY_COBBLED_DEEPSLATE);
	public static final Block MOSSY_COBBLED_DEEPSLATE_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_SLAB, MOSSY_COBBLED_DEEPSLATE);
	public static final Block MOSSY_COBBLED_DEEPSLATE_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_WALL, MOSSY_COBBLED_DEEPSLATE);
	public static final BlockFamily FAMILY_MOSSY_COBBLED_DEEPSLATE = BlockFamilies.familyBuilder(MOSSY_COBBLED_DEEPSLATE)
		.stairs(MOSSY_COBBLED_DEEPSLATE_STAIRS)
		.slab(MOSSY_COBBLED_DEEPSLATE_SLAB)
		.wall(MOSSY_COBBLED_DEEPSLATE_WALL)
		.getFamily();

	public static final Block MOSSY_DEEPSLATE_BRICKS = Blocks.register(TTBlockItemIds.MOSSY_DEEPSLATE_BRICKS, Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS));
	public static final Block MOSSY_DEEPSLATE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_STAIRS, MOSSY_DEEPSLATE_BRICKS);
	public static final Block MOSSY_DEEPSLATE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_SLAB, MOSSY_DEEPSLATE_BRICKS);
	public static final Block MOSSY_DEEPSLATE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_WALL, MOSSY_DEEPSLATE_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_DEEPSLATE_BRICKS = BlockFamilies.familyBuilder(MOSSY_DEEPSLATE_BRICKS)
		.stairs(MOSSY_DEEPSLATE_BRICK_STAIRS)
		.slab(MOSSY_DEEPSLATE_BRICK_SLAB)
		.wall(MOSSY_DEEPSLATE_BRICK_WALL)
		.getFamily();

	public static final Block MOSSY_DEEPSLATE_TILES = Blocks.register(TTBlockItemIds.MOSSY_DEEPSLATE_TILES, Properties.ofFullCopy(Blocks.DEEPSLATE_TILES));
	public static final Block MOSSY_DEEPSLATE_TILE_STAIRS = Blocks.registerStair(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_STAIRS, MOSSY_DEEPSLATE_TILES);
	public static final Block MOSSY_DEEPSLATE_TILE_SLAB = Blocks.registerSlab(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_SLAB, MOSSY_DEEPSLATE_TILES);
	public static final Block MOSSY_DEEPSLATE_TILE_WALL = Blocks.registerWall(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_WALL, MOSSY_DEEPSLATE_TILES);
	public static final BlockFamily FAMILY_MOSSY_DEEPSLATE_TILES = BlockFamilies.familyBuilder(MOSSY_DEEPSLATE_TILES)
		.stairs(MOSSY_DEEPSLATE_TILE_STAIRS)
		.slab(MOSSY_DEEPSLATE_TILE_SLAB)
		.wall(MOSSY_DEEPSLATE_TILE_WALL)
		.getFamily();

	// SANDSTONE
	public static final Block SMOOTH_SANDSTONE_WALL = Blocks.registerWall(TTBlockItemIds.SMOOTH_SANDSTONE_WALL, Blocks.SMOOTH_SANDSTONE);
	public static final Block CUT_SANDSTONE_STAIRS = Blocks.registerStair(TTBlockItemIds.CUT_SANDSTONE_STAIRS, Blocks.CUT_SANDSTONE);
	public static final Block CUT_SANDSTONE_WALL = Blocks.registerWall(TTBlockItemIds.CUT_SANDSTONE_WALL, Blocks.CUT_SANDSTONE);

	// RED SANDSTONE
	public static final Block SMOOTH_RED_SANDSTONE_WALL = Blocks.registerWall(TTBlockItemIds.SMOOTH_RED_SANDSTONE_WALL, Blocks.SMOOTH_RED_SANDSTONE);
	public static final Block CUT_RED_SANDSTONE_STAIRS = Blocks.registerStair(TTBlockItemIds.CUT_RED_SANDSTONE_STAIRS, Blocks.CUT_RED_SANDSTONE);
	public static final Block CUT_RED_SANDSTONE_WALL = Blocks.registerWall(TTBlockItemIds.CUT_RED_SANDSTONE_WALL, Blocks.CUT_RED_SANDSTONE);

	// PRISMARINE
	public static final Block PRISMARINE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.PRISMARINE_BRICK_WALL, Blocks.PRISMARINE_BRICKS);
	public static final Block DARK_PRISMARINE_WALL = Blocks.registerWall(TTBlockItemIds.DARK_PRISMARINE_WALL, Blocks.DARK_PRISMARINE);

	// END STONE
	public static final Block END_STONE_STAIRS = Blocks.registerStair(TTBlockItemIds.END_STONE_STAIRS, Blocks.END_STONE);
	public static final Block END_STONE_SLAB = Blocks.registerSlab(TTBlockItemIds.END_STONE_SLAB, Blocks.END_STONE);
	public static final Block END_STONE_WALL = Blocks.registerWall(TTBlockItemIds.END_STONE_WALL, Blocks.END_STONE);

	public static final Block CHORAL_END_STONE = Blocks.register(TTBlockItemIds.CHORAL_END_STONE, Properties.ofFullCopy(Blocks.END_STONE));
	public static final Block CHORAL_END_STONE_STAIRS = Blocks.registerStair(TTBlockItemIds.CHORAL_END_STONE_STAIRS, CHORAL_END_STONE);
	public static final Block CHORAL_END_STONE_SLAB = Blocks.registerSlab(TTBlockItemIds.CHORAL_END_STONE_SLAB, CHORAL_END_STONE);
	public static final Block CHORAL_END_STONE_WALL = Blocks.registerWall(TTBlockItemIds.CHORAL_END_STONE_WALL, CHORAL_END_STONE);
	public static final BlockFamily FAMILY_CHORAL_END_STONE = BlockFamilies.familyBuilder(CHORAL_END_STONE)
		.stairs(CHORAL_END_STONE_STAIRS)
		.slab(CHORAL_END_STONE_SLAB)
		.wall(CHORAL_END_STONE_WALL)
		.getFamily();

	public static final Block CRACKED_END_STONE_BRICKS = Blocks.register(TTBlockItemIds.CRACKED_END_STONE_BRICKS, Properties.ofFullCopy(Blocks.END_STONE_BRICKS));
	public static final Block CHISELED_END_STONE_BRICKS = Blocks.register(TTBlockItemIds.CHISELED_END_STONE_BRICKS, Properties.ofFullCopy(Blocks.END_STONE_BRICKS));
	public static final Block CHORAL_END_STONE_BRICKS = Blocks.register(TTBlockItemIds.CHORAL_END_STONE_BRICKS, Properties.ofFullCopy(Blocks.END_STONE_BRICKS));
	public static final Block CHORAL_END_STONE_BRICK_STAIRS = Blocks.registerStair(TTBlockItemIds.CHORAL_END_STONE_BRICK_STAIRS, CHORAL_END_STONE_BRICKS);
	public static final Block CHORAL_END_STONE_BRICK_SLAB = Blocks.registerSlab(TTBlockItemIds.CHORAL_END_STONE_BRICK_SLAB, CHORAL_END_STONE_BRICKS);
	public static final Block CHORAL_END_STONE_BRICK_WALL = Blocks.registerWall(TTBlockItemIds.CHORAL_END_STONE_BRICK_WALL, CHORAL_END_STONE_BRICKS);
	public static final BlockFamily FAMILY_CHORAL_END_STONE_BRICKS = BlockFamilies.familyBuilder(CHORAL_END_STONE_BRICKS)
		.stairs(CHORAL_END_STONE_BRICK_STAIRS)
		.slab(CHORAL_END_STONE_BRICK_SLAB)
		.wall(CHORAL_END_STONE_BRICK_WALL)
		.getFamily();

	// PURPUR
	public static final Block CRACKED_PURPUR_BLOCK = Blocks.register(TTBlockItemIds.CRACKED_PURPUR_BLOCK, Properties.ofFullCopy(Blocks.PURPUR_BLOCK));
	public static final Block PURPUR_WALL = Blocks.registerWall(TTBlockItemIds.PURPUR_WALL, Blocks.PURPUR_BLOCK);
	public static final Block CHISELED_PURPUR_BLOCK = Blocks.register(TTBlockItemIds.CHISELED_PURPUR_BLOCK, Properties.ofFullCopy(Blocks.PURPUR_BLOCK));

	// CATACOMBS
	public static final Block COFFIN = Blocks.register(TTBlockItemIds.COFFIN,
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
	);
	public static final Block SURVEYOR = Blocks.register(TTBlockItemIds.SURVEYOR,
		SurveyorBlock::new,
		Properties.of()
			.mapColor(MapColor.STONE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(3F)
			.requiresCorrectToolForDrops()
			.isRedstoneConductor(Blocks::never)
	);
	public static final Block ECTOPLASM_BLOCK = Blocks.register(TTBlockItemIds.ECTOPLASM_BLOCK,
		EctoplasmBlock::new,
		Properties.of()
			.mapColor(MapColor.COLOR_LIGHT_BLUE)
			.noOcclusion()
			.instabreak()
			.explosionResistance(1200F)
			.emissiveRendering(state -> true)
			.lightLevel(state -> 1)
			.sound(TTSounds.ECTOPLASM)
			.isSuffocating(Blocks::never)
			.isViewBlocking(Blocks::never)
			.pushReaction(PushReaction.DESTROY)
			.dynamicShape()
	);

	public static void init() {}

	public static Block registerFlowerPot(ResourceKey<Block> id, Block potted) {
		return Blocks.register(id, properties -> new FlowerPotBlock(potted, properties), Blocks.flowerPotProperties());
	}

	public static void registerBlockProperties() {
		HopperApi.addBlacklistedType(TTBlockEntityTypes.COFFIN);
		ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(), COFFIN);

		CompostableRegistry.INSTANCE.add(CYAN_ROSE, 0.85F);
		CompostableRegistry.INSTANCE.add(CYAN_ROSE_CROP, 0.3F);
		CompostableRegistry.INSTANCE.add(MANEDROP, 0.85F);
		CompostableRegistry.INSTANCE.add(MANEDROP_CROP, 0.3F);
		CompostableRegistry.INSTANCE.add(DAWNTRAIL, 0.85F);
		CompostableRegistry.INSTANCE.add(DAWNTRAIL_CROP, 0.3F);
		CompostableRegistry.INSTANCE.add(GUZMANIA, 0.85F);
		CompostableRegistry.INSTANCE.add(GUZMANIA_CROP, 0.3F);
		CompostableRegistry.INSTANCE.add(LITHOPS, 0.3F);
		CompostableRegistry.INSTANCE.add(LITHOPS_CROP, 0.3F);

		final var flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableBlockRegistry.add(CYAN_ROSE, 60, 100);
		flammableBlockRegistry.add(MANEDROP, 60, 100);
		flammableBlockRegistry.add(DAWNTRAIL, 60, 100);
		flammableBlockRegistry.add(GUZMANIA, 60, 100);
		flammableBlockRegistry.add(LITHOPS, 60, 100);
	}
}
