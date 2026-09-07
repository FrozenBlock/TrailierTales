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

import net.frozenblock.lib.block.api.NonFallingBrushableBlock;
import net.frozenblock.lib.block.api.fire.FlammableBlockRegistry;
import net.frozenblock.lib.block.api.storage.hopper.HopperApi;
import net.frozenblock.lib.item.api.registry.CompostableRegistry;
import net.frozenblock.lib.platform.api.registry.DeferredBlock;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
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
import net.frozenblock.trailiertales.block.SurveyorBlock;
import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.references.TTBlockIds;
import net.frozenblock.trailiertales.references.TTBlockItemIds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class TTBlocks {
	private static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(TTConstants.MOD_ID).requiredFeatures(TTFeatureFlags.FEATURE_FLAG);

	// SUSPICIOUS BLOCKS
	public static final DeferredBlock<Block> SUSPICIOUS_RED_SAND = REGISTER.registerBlock(TTBlockItemIds.SUSPICIOUS_RED_SAND,
		properties -> new BrushableBlock(
			Blocks.RED_SAND,
			SoundEvents.BRUSH_SAND,
			SoundEvents.BRUSH_SAND_COMPLETED,
			properties
		),
		() -> Properties.of()
			.mapColor(MapColor.COLOR_ORANGE)
			.instrument(NoteBlockInstrument.SNARE)
			.strength(0.25F).sound(SoundType.SUSPICIOUS_SAND)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final DeferredBlock<NonFallingBrushableBlock> SUSPICIOUS_DIRT = REGISTER.registerBlock(TTBlockItemIds.SUSPICIOUS_DIRT,
		properties -> new NonFallingBrushableBlock(
			Blocks.DIRT,
			TTSounds.BRUSH_DIRT.get(),
			TTSounds.BRUSH_DIRT_COMPLETED.get(),
			properties
		),
		() -> Properties.of()
			.mapColor(MapColor.DIRT)
			.strength(0.25F)
			.sound(TTSoundTypes.SUSPICIOUS_DIRT)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final DeferredBlock<NonFallingBrushableBlock> SUSPICIOUS_CLAY = REGISTER.registerBlock(TTBlockItemIds.SUSPICIOUS_CLAY,
		properties -> new NonFallingBrushableBlock(
			Blocks.CLAY,
			TTSounds.BRUSH_CLAY.get(),
			TTSounds.BRUSH_CLAY_COMPLETED.get(),
			properties
		),
		() -> Properties.of()
			.mapColor(MapColor.CLAY)
			.instrument(NoteBlockInstrument.FLUTE)
			.strength(0.25F)
			.sound(TTSoundTypes.SUSPICIOUS_CLAY)
			.pushReaction(PushReaction.DESTROY)
	);

	// PLANTS
	public static final DeferredBlock<CyanRoseCropBlock> CYAN_ROSE_CROP = REGISTER.registerBlock(TTBlockItemIds.CYAN_ROSE_CROP,
		CyanRoseCropBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final DeferredBlock<FlowerBlock> CYAN_ROSE = REGISTER.registerBlock(TTBlockItemIds.CYAN_ROSE,
		properties -> new FlowerBlock(MobEffects.SATURATION, 0.5F, properties),
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final DeferredBlock<FlowerPotBlock> POTTED_CYAN_ROSE = REGISTER.registerFlowerPot(TTBlockIds.POTTED_CYAN_ROSE, CYAN_ROSE);

	public static final DeferredBlock<ManedropCropBlock> MANEDROP_CROP = REGISTER.registerBlock(TTBlockItemIds.MANEDROP_CROP,
		ManedropCropBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final DeferredBlock<DoublePlantBlock> MANEDROP = REGISTER.registerBlock(TTBlockItemIds.MANEDROP,
		DoublePlantBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
	);

	public static final DeferredBlock<DawntrailCropBlock> DAWNTRAIL_CROP = REGISTER.registerBlock(TTBlockItemIds.DAWNTRAIL_CROP,
		DawntrailCropBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final DeferredBlock<DawntrailBlock> DAWNTRAIL = REGISTER.registerBlock(TTBlockItemIds.DAWNTRAIL,
		DawntrailBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.randomTicks()
			.strength(0.2F)
			.sound(SoundType.VINE)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
	);

	public static final DeferredBlock<GuzmaniaCropBlock> GUZMANIA_CROP = REGISTER.registerBlock(TTBlockItemIds.GUZMANIA_CROP,
		GuzmaniaCropBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final DeferredBlock<DoublePlantBlock> GUZMANIA = REGISTER.registerBlock(TTBlockItemIds.GUZMANIA,
		DoublePlantBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.ignitedByLava()
			.pushReaction(PushReaction.DESTROY)
	);

	public static final DeferredBlock<LithopsCropBlock> LITHOPS_CROP = REGISTER.registerBlock(TTBlockItemIds.LITHOPS_CROP,
		LithopsCropBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final DeferredBlock<LithopsBlock> LITHOPS = REGISTER.registerBlock(TTBlockItemIds.LITHOPS, LithopsBlock::new, () -> Properties.ofFullCopy(Blocks.PINK_PETALS));
	public static final DeferredBlock<FlowerPotBlock> POTTED_LITHOPS = REGISTER.registerFlowerPot(TTBlockIds.POTTED_LITHOPS, LITHOPS);

	// STONE
	public static final DeferredBlock<WallBlock> STONE_WALL = REGISTER.registerWall(TTBlockItemIds.STONE_WALL, () -> Blocks.STONE);

	// GRANITE
	public static final DeferredBlock<WallBlock> POLISHED_GRANITE_WALL = REGISTER.registerWall(TTBlockItemIds.POLISHED_GRANITE_WALL, () -> Blocks.POLISHED_GRANITE);

	public static final DeferredBlock<Block> GRANITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.GRANITE_BRICKS, () -> Properties.ofFullCopy(Blocks.GRANITE));
	public static final DeferredBlock<Block> CHISELED_GRANITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CHISELED_GRANITE_BRICKS, () -> Properties.ofFullCopy(GRANITE_BRICKS.get()));
	public static final DeferredBlock<StairBlock> GRANITE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.GRANITE_BRICK_STAIRS, GRANITE_BRICKS);
	public static final DeferredBlock<SlabBlock> GRANITE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.GRANITE_BRICK_SLAB, GRANITE_BRICKS);
	public static final DeferredBlock<WallBlock> GRANITE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.GRANITE_BRICK_WALL, GRANITE_BRICKS);
	public static final DeferredBlock<Block> CRACKED_GRANITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_GRANITE_BRICKS, () -> Properties.ofFullCopy(GRANITE_BRICKS.get()));

	public static final DeferredBlock<Block> MOSSY_GRANITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_GRANITE_BRICKS, () -> Properties.ofFullCopy(GRANITE_BRICKS.get()));
	public static final DeferredBlock<StairBlock> MOSSY_GRANITE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_GRANITE_BRICK_STAIRS, MOSSY_GRANITE_BRICKS);
	public static final DeferredBlock<SlabBlock> MOSSY_GRANITE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_GRANITE_BRICK_SLAB, MOSSY_GRANITE_BRICKS);
	public static final DeferredBlock<WallBlock> MOSSY_GRANITE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_GRANITE_BRICK_WALL, MOSSY_GRANITE_BRICKS);

	// DIORITE
	public static final DeferredBlock<WallBlock> POLISHED_DIORITE_WALL = REGISTER.registerWall(TTBlockItemIds.POLISHED_DIORITE_WALL, () -> Blocks.POLISHED_DIORITE);

	public static final DeferredBlock<Block> DIORITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.DIORITE_BRICKS, () -> Properties.ofFullCopy(Blocks.DIORITE));
	public static final DeferredBlock<Block> CHISELED_DIORITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CHISELED_DIORITE_BRICKS, () -> Properties.ofFullCopy(DIORITE_BRICKS.get()));
	public static final DeferredBlock<StairBlock> DIORITE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.DIORITE_BRICK_STAIRS, DIORITE_BRICKS);
	public static final DeferredBlock<SlabBlock> DIORITE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.DIORITE_BRICK_SLAB, DIORITE_BRICKS);
	public static final DeferredBlock<WallBlock> DIORITE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.DIORITE_BRICK_WALL, DIORITE_BRICKS);
	public static final DeferredBlock<Block> CRACKED_DIORITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_DIORITE_BRICKS, () -> Properties.ofFullCopy(DIORITE_BRICKS.get()));

	public static final DeferredBlock<Block> MOSSY_DIORITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_DIORITE_BRICKS, () -> Properties.ofFullCopy(DIORITE_BRICKS.get()));
	public static final DeferredBlock<StairBlock> MOSSY_DIORITE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_DIORITE_BRICK_STAIRS, MOSSY_DIORITE_BRICKS);
	public static final DeferredBlock<SlabBlock> MOSSY_DIORITE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_DIORITE_BRICK_SLAB, MOSSY_DIORITE_BRICKS);
	public static final DeferredBlock<WallBlock> MOSSY_DIORITE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_DIORITE_BRICK_WALL, MOSSY_DIORITE_BRICKS);

	// ANDESITE
	public static final DeferredBlock<WallBlock> POLISHED_ANDESITE_WALL = REGISTER.registerWall(TTBlockItemIds.POLISHED_ANDESITE_WALL, () -> Blocks.POLISHED_ANDESITE);

	public static final DeferredBlock<Block> ANDESITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.ANDESITE_BRICKS, () -> Properties.ofFullCopy(Blocks.ANDESITE));
	public static final DeferredBlock<Block> CHISELED_ANDESITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CHISELED_ANDESITE_BRICKS, () -> Properties.ofFullCopy(ANDESITE_BRICKS.get()));
	public static final DeferredBlock<StairBlock> ANDESITE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.ANDESITE_BRICK_STAIRS, ANDESITE_BRICKS);
	public static final DeferredBlock<SlabBlock> ANDESITE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.ANDESITE_BRICK_SLAB, ANDESITE_BRICKS);
	public static final DeferredBlock<WallBlock> ANDESITE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.ANDESITE_BRICK_WALL, ANDESITE_BRICKS);
	public static final DeferredBlock<Block> CRACKED_ANDESITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_ANDESITE_BRICKS, () -> Properties.ofFullCopy(ANDESITE_BRICKS.get()));

	public static final DeferredBlock<Block> MOSSY_ANDESITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_ANDESITE_BRICKS, () -> Properties.ofFullCopy(ANDESITE_BRICKS.get()));
	public static final DeferredBlock<StairBlock> MOSSY_ANDESITE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_ANDESITE_BRICK_STAIRS, MOSSY_ANDESITE_BRICKS);
	public static final DeferredBlock<SlabBlock> MOSSY_ANDESITE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_ANDESITE_BRICK_SLAB, MOSSY_ANDESITE_BRICKS);
	public static final DeferredBlock<WallBlock> MOSSY_ANDESITE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_ANDESITE_BRICK_WALL, MOSSY_ANDESITE_BRICKS);

	// CALCITE
	public static final DeferredBlock<StairBlock> CALCITE_STAIRS = REGISTER.registerStair(TTBlockItemIds.CALCITE_STAIRS, () -> Blocks.CALCITE);
	public static final DeferredBlock<SlabBlock> CALCITE_SLAB = REGISTER.registerSlab(TTBlockItemIds.CALCITE_SLAB, () -> Blocks.CALCITE);
	public static final DeferredBlock<WallBlock> CALCITE_WALL = REGISTER.registerWall(TTBlockItemIds.CALCITE_WALL, () -> Blocks.CALCITE);

	public static final DeferredBlock<Block> POLISHED_CALCITE = REGISTER.registerSimpleBlock(TTBlockItemIds.POLISHED_CALCITE, () -> Properties.ofFullCopy(Blocks.CALCITE));
	public static final DeferredBlock<StairBlock> POLISHED_CALCITE_STAIRS = REGISTER.registerStair(TTBlockItemIds.POLISHED_CALCITE_STAIRS, () -> Blocks.CALCITE);
	public static final DeferredBlock<SlabBlock> POLISHED_CALCITE_SLAB = REGISTER.registerSlab(TTBlockItemIds.POLISHED_CALCITE_SLAB, POLISHED_CALCITE);
	public static final DeferredBlock<WallBlock> POLISHED_CALCITE_WALL = REGISTER.registerWall(TTBlockItemIds.POLISHED_CALCITE_WALL, POLISHED_CALCITE);

	public static final DeferredBlock<Block> CALCITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CALCITE_BRICKS,
		() -> Properties.ofFullCopy(Blocks.CALCITE).sound(TTSoundTypes.CALCITE_BRICKS)
	);
	public static final DeferredBlock<StairBlock> CALCITE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.CALCITE_BRICK_STAIRS, CALCITE_BRICKS);
	public static final DeferredBlock<SlabBlock> CALCITE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.CALCITE_BRICK_SLAB, CALCITE_BRICKS);
	public static final DeferredBlock<WallBlock> CALCITE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.CALCITE_BRICK_WALL, CALCITE_BRICKS);
	public static final DeferredBlock<Block> CRACKED_CALCITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_CALCITE_BRICKS, () -> Properties.ofFullCopy(CALCITE_BRICKS.get()));
	public static final DeferredBlock<Block> CHISELED_CALCITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CHISELED_CALCITE_BRICKS, () -> Properties.ofFullCopy(CALCITE_BRICKS.get()));

	public static final DeferredBlock<Block> MOSSY_CALCITE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_CALCITE_BRICKS, () -> Properties.ofFullCopy(CALCITE_BRICKS.get()));
	public static final DeferredBlock<StairBlock> MOSSY_CALCITE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_CALCITE_BRICK_STAIRS, MOSSY_CALCITE_BRICKS);
	public static final DeferredBlock<SlabBlock> MOSSY_CALCITE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_CALCITE_BRICK_SLAB, MOSSY_CALCITE_BRICKS);
	public static final DeferredBlock<WallBlock> MOSSY_CALCITE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_CALCITE_BRICK_WALL, MOSSY_CALCITE_BRICKS);

	// TUFF
	public static final DeferredBlock<Block> CRACKED_TUFF_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_TUFF_BRICKS, () -> Properties.ofFullCopy(Blocks.TUFF_BRICKS));
	public static final DeferredBlock<Block> MOSSY_TUFF_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_TUFF_BRICKS, () -> Properties.ofFullCopy(Blocks.TUFF_BRICKS));
	public static final DeferredBlock<StairBlock> MOSSY_TUFF_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_TUFF_BRICK_STAIRS, MOSSY_TUFF_BRICKS);
	public static final DeferredBlock<SlabBlock> MOSSY_TUFF_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_TUFF_BRICK_SLAB, MOSSY_TUFF_BRICKS);
	public static final DeferredBlock<WallBlock> MOSSY_TUFF_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_TUFF_BRICK_WALL, MOSSY_TUFF_BRICKS);

	// BRICKS
	public static final DeferredBlock<Block> CRACKED_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_BRICKS, () -> Properties.ofFullCopy(Blocks.BRICKS));
	public static final DeferredBlock<Block> MOSSY_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_BRICKS, () -> Properties.ofFullCopy(Blocks.BRICKS));
	public static final DeferredBlock<StairBlock> MOSSY_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_BRICK_STAIRS, MOSSY_BRICKS);
	public static final DeferredBlock<SlabBlock> MOSSY_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_BRICK_SLAB, MOSSY_BRICKS);
	public static final DeferredBlock<WallBlock> MOSSY_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_BRICK_WALL, MOSSY_BRICKS);

	// RESIN
	public static final DeferredBlock<Block> POLISHED_RESIN_BLOCK = REGISTER.registerSimpleBlock(TTBlockItemIds.POLISHED_RESIN_BLOCK, () -> Properties.ofFullCopy(Blocks.RESIN_BLOCK));
	public static final DeferredBlock<StairBlock> POLISHED_RESIN_STAIRS = REGISTER.registerStair(TTBlockItemIds.POLISHED_RESIN_STAIRS, POLISHED_RESIN_BLOCK);
	public static final DeferredBlock<SlabBlock> POLISHED_RESIN_SLAB = REGISTER.registerSlab(TTBlockItemIds.POLISHED_RESIN_SLAB, POLISHED_RESIN_BLOCK);
	public static final DeferredBlock<WallBlock> POLISHED_RESIN_WALL = REGISTER.registerWall(TTBlockItemIds.POLISHED_RESIN_WALL, POLISHED_RESIN_BLOCK);

	public static final DeferredBlock<Block> CRACKED_RESIN_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_RESIN_BRICKS, () -> Properties.ofFullCopy(Blocks.RESIN_BRICKS));

	public static final DeferredBlock<Block> PALE_MOSSY_RESIN_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.PALE_MOSSY_RESIN_BRICKS, () -> Properties.ofFullCopy(Blocks.RESIN_BRICKS));
	public static final DeferredBlock<StairBlock> PALE_MOSSY_RESIN_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_STAIRS, PALE_MOSSY_RESIN_BRICKS);
	public static final DeferredBlock<SlabBlock> PALE_MOSSY_RESIN_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_SLAB, PALE_MOSSY_RESIN_BRICKS);
	public static final DeferredBlock<WallBlock> PALE_MOSSY_RESIN_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.PALE_MOSSY_RESIN_BRICK_WALL, PALE_MOSSY_RESIN_BRICKS);

	// MOSSY DEEPSLATE
	public static final DeferredBlock<Block> MOSSY_COBBLED_DEEPSLATE = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE, () -> Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE));
	public static final DeferredBlock<StairBlock> MOSSY_COBBLED_DEEPSLATE_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_STAIRS, MOSSY_COBBLED_DEEPSLATE);
	public static final DeferredBlock<SlabBlock> MOSSY_COBBLED_DEEPSLATE_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_SLAB, MOSSY_COBBLED_DEEPSLATE);
	public static final DeferredBlock<WallBlock> MOSSY_COBBLED_DEEPSLATE_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_COBBLED_DEEPSLATE_WALL, MOSSY_COBBLED_DEEPSLATE);

	public static final DeferredBlock<Block> MOSSY_DEEPSLATE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_DEEPSLATE_BRICKS, () -> Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS));
	public static final DeferredBlock<StairBlock> MOSSY_DEEPSLATE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_STAIRS, MOSSY_DEEPSLATE_BRICKS);
	public static final DeferredBlock<SlabBlock> MOSSY_DEEPSLATE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_SLAB, MOSSY_DEEPSLATE_BRICKS);
	public static final DeferredBlock<WallBlock> MOSSY_DEEPSLATE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_DEEPSLATE_BRICK_WALL, MOSSY_DEEPSLATE_BRICKS);

	public static final DeferredBlock<Block> MOSSY_DEEPSLATE_TILES = REGISTER.registerSimpleBlock(TTBlockItemIds.MOSSY_DEEPSLATE_TILES, () -> Properties.ofFullCopy(Blocks.DEEPSLATE_TILES));
	public static final DeferredBlock<StairBlock> MOSSY_DEEPSLATE_TILE_STAIRS = REGISTER.registerStair(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_STAIRS, MOSSY_DEEPSLATE_TILES);
	public static final DeferredBlock<SlabBlock> MOSSY_DEEPSLATE_TILE_SLAB = REGISTER.registerSlab(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_SLAB, MOSSY_DEEPSLATE_TILES);
	public static final DeferredBlock<WallBlock> MOSSY_DEEPSLATE_TILE_WALL = REGISTER.registerWall(TTBlockItemIds.MOSSY_DEEPSLATE_TILE_WALL, MOSSY_DEEPSLATE_TILES);

	// SANDSTONE
	public static final DeferredBlock<WallBlock> SMOOTH_SANDSTONE_WALL = REGISTER.registerWall(TTBlockItemIds.SMOOTH_SANDSTONE_WALL, () -> Blocks.SMOOTH_SANDSTONE);
	public static final DeferredBlock<StairBlock> CUT_SANDSTONE_STAIRS = REGISTER.registerStair(TTBlockItemIds.CUT_SANDSTONE_STAIRS, () -> Blocks.CUT_SANDSTONE);
	public static final DeferredBlock<WallBlock> CUT_SANDSTONE_WALL = REGISTER.registerWall(TTBlockItemIds.CUT_SANDSTONE_WALL, () -> Blocks.CUT_SANDSTONE);

	// RED SANDSTONE
	public static final DeferredBlock<WallBlock> SMOOTH_RED_SANDSTONE_WALL = REGISTER.registerWall(TTBlockItemIds.SMOOTH_RED_SANDSTONE_WALL, () -> Blocks.SMOOTH_RED_SANDSTONE);
	public static final DeferredBlock<StairBlock> CUT_RED_SANDSTONE_STAIRS = REGISTER.registerStair(TTBlockItemIds.CUT_RED_SANDSTONE_STAIRS, () -> Blocks.CUT_RED_SANDSTONE);
	public static final DeferredBlock<WallBlock> CUT_RED_SANDSTONE_WALL = REGISTER.registerWall(TTBlockItemIds.CUT_RED_SANDSTONE_WALL, () -> Blocks.CUT_RED_SANDSTONE);

	// PRISMARINE
	public static final DeferredBlock<WallBlock> PRISMARINE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.PRISMARINE_BRICK_WALL, () -> Blocks.PRISMARINE_BRICKS);
	public static final DeferredBlock<WallBlock> DARK_PRISMARINE_WALL = REGISTER.registerWall(TTBlockItemIds.DARK_PRISMARINE_WALL, () -> Blocks.DARK_PRISMARINE);

	// END STONE
	public static final DeferredBlock<StairBlock> END_STONE_STAIRS = REGISTER.registerStair(TTBlockItemIds.END_STONE_STAIRS, () -> Blocks.END_STONE);
	public static final DeferredBlock<SlabBlock> END_STONE_SLAB = REGISTER.registerSlab(TTBlockItemIds.END_STONE_SLAB, () -> Blocks.END_STONE);
	public static final DeferredBlock<WallBlock> END_STONE_WALL = REGISTER.registerWall(TTBlockItemIds.END_STONE_WALL, () -> Blocks.END_STONE);

	public static final DeferredBlock<Block> CHORAL_END_STONE = REGISTER.registerSimpleBlock(TTBlockItemIds.CHORAL_END_STONE, () -> Properties.ofFullCopy(Blocks.END_STONE));
	public static final DeferredBlock<StairBlock> CHORAL_END_STONE_STAIRS = REGISTER.registerStair(TTBlockItemIds.CHORAL_END_STONE_STAIRS, CHORAL_END_STONE);
	public static final DeferredBlock<SlabBlock> CHORAL_END_STONE_SLAB = REGISTER.registerSlab(TTBlockItemIds.CHORAL_END_STONE_SLAB, CHORAL_END_STONE);
	public static final DeferredBlock<WallBlock> CHORAL_END_STONE_WALL = REGISTER.registerWall(TTBlockItemIds.CHORAL_END_STONE_WALL, CHORAL_END_STONE);

	public static final DeferredBlock<Block> CRACKED_END_STONE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_END_STONE_BRICKS, () -> Properties.ofFullCopy(Blocks.END_STONE_BRICKS));
	public static final DeferredBlock<Block> CHISELED_END_STONE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CHISELED_END_STONE_BRICKS, () -> Properties.ofFullCopy(Blocks.END_STONE_BRICKS));
	public static final DeferredBlock<Block> CHORAL_END_STONE_BRICKS = REGISTER.registerSimpleBlock(TTBlockItemIds.CHORAL_END_STONE_BRICKS, () -> Properties.ofFullCopy(Blocks.END_STONE_BRICKS));
	public static final DeferredBlock<StairBlock> CHORAL_END_STONE_BRICK_STAIRS = REGISTER.registerStair(TTBlockItemIds.CHORAL_END_STONE_BRICK_STAIRS, CHORAL_END_STONE_BRICKS);
	public static final DeferredBlock<SlabBlock> CHORAL_END_STONE_BRICK_SLAB = REGISTER.registerSlab(TTBlockItemIds.CHORAL_END_STONE_BRICK_SLAB, CHORAL_END_STONE_BRICKS);
	public static final DeferredBlock<WallBlock> CHORAL_END_STONE_BRICK_WALL = REGISTER.registerWall(TTBlockItemIds.CHORAL_END_STONE_BRICK_WALL, CHORAL_END_STONE_BRICKS);

	// PURPUR
	public static final DeferredBlock<Block> CRACKED_PURPUR_BLOCK = REGISTER.registerSimpleBlock(TTBlockItemIds.CRACKED_PURPUR_BLOCK, () -> Properties.ofFullCopy(Blocks.PURPUR_BLOCK));
	public static final DeferredBlock<WallBlock> PURPUR_WALL = REGISTER.registerWall(TTBlockItemIds.PURPUR_WALL, () -> Blocks.PURPUR_BLOCK);
	public static final DeferredBlock<Block> CHISELED_PURPUR_BLOCK = REGISTER.registerSimpleBlock(TTBlockItemIds.CHISELED_PURPUR_BLOCK, () -> Properties.ofFullCopy(Blocks.PURPUR_BLOCK));

	// CATACOMBS
	public static final DeferredBlock<CoffinBlock> COFFIN = REGISTER.registerBlock(TTBlockItemIds.COFFIN,
		CoffinBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.noOcclusion()
			.lightLevel(state -> state.getValue(TTBlockStateProperties.COFFIN_STATE).getLightLevel())
			.requiresCorrectToolForDrops()
			.sound(TTSoundTypes.COFFIN)
			.strength(50F)
			.isViewBlocking(Blocks::never)
	);
	public static final DeferredBlock<SurveyorBlock> SURVEYOR = REGISTER.registerBlock(TTBlockItemIds.SURVEYOR,
		SurveyorBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.STONE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(3F)
			.requiresCorrectToolForDrops()
			.isRedstoneConductor(Blocks::never)
	);
	public static final DeferredBlock<EctoplasmBlock> ECTOPLASM_BLOCK = REGISTER.registerBlock(TTBlockItemIds.ECTOPLASM_BLOCK,
		EctoplasmBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.COLOR_LIGHT_BLUE)
			.noOcclusion()
			.instabreak()
			.explosionResistance(1200F)
			.emissiveRendering(state -> true)
			.lightLevel(state -> 1)
			.sound(TTSoundTypes.ECTOPLASM)
			.isSuffocating(Blocks::never)
			.isViewBlocking(Blocks::never)
			.pushReaction(PushReaction.DESTROY)
			.dynamicShape()
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	public static void registerBlockProperties() {
		HopperApi.addBlacklistedType(TTBlockEntityTypes.COFFIN.get());

		CompostableRegistry.register(CYAN_ROSE.get(), 0.85F);
		CompostableRegistry.register(CYAN_ROSE_CROP.get(), 0.3F);
		CompostableRegistry.register(MANEDROP.get(), 0.85F);
		CompostableRegistry.register(MANEDROP_CROP.get(), 0.3F);
		CompostableRegistry.register(DAWNTRAIL.get(), 0.85F);
		CompostableRegistry.register(DAWNTRAIL_CROP.get(), 0.3F);
		CompostableRegistry.register(GUZMANIA.get(), 0.85F);
		CompostableRegistry.register(GUZMANIA_CROP.get(), 0.3F);
		CompostableRegistry.register(LITHOPS.get(), 0.3F);
		CompostableRegistry.register(LITHOPS_CROP.get(), 0.3F);

		FlammableBlockRegistry.register(CYAN_ROSE.get(), 60, 100);
		FlammableBlockRegistry.register(MANEDROP.get(), 60, 100);
		FlammableBlockRegistry.register(DAWNTRAIL.get(), 60, 100);
		FlammableBlockRegistry.register(GUZMANIA.get(), 60, 100);
		FlammableBlockRegistry.register(LITHOPS.get(), 60, 100);
	}

	private TTBlocks() {}
}
