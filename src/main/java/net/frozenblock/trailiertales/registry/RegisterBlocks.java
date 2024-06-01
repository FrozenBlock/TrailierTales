package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.CyanRoseCropBlock;
import net.frozenblock.trailiertales.block.NonFallingBrushableBlock;
import net.frozenblock.trailiertales.block.SurveyorBlock;
import net.frozenblock.trailiertales.block.impl.TrailierBlockStateProperties;
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
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class RegisterBlocks {

	public static final Block SUSPICIOUS_RED_SAND = new BrushableBlock(
		Blocks.RED_SAND,
		SoundEvents.BRUSH_SAND,
		SoundEvents.BRUSH_SAND_COMPLETED,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_ORANGE)
			.instrument(NoteBlockInstrument.SNARE)
			.strength(0.25f).sound(SoundType.SUSPICIOUS_SAND)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block SUSPICIOUS_DIRT = new NonFallingBrushableBlock(
		Blocks.DIRT,
		SoundEvents.BRUSH_GRAVEL,
		SoundEvents.BRUSH_GRAVEL_COMPLETED,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.DIRT)
			.strength(0.25f)
			.sound(SoundType.SUSPICIOUS_GRAVEL)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block SUSPICIOUS_CLAY = new NonFallingBrushableBlock(
		Blocks.CLAY,
		SoundEvents.BRUSH_GRAVEL,
		SoundEvents.BRUSH_GRAVEL_COMPLETED,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.CLAY)
			.instrument(NoteBlockInstrument.FLUTE)
			.strength(0.25f)
			.sound(SoundType.SUSPICIOUS_GRAVEL)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final Block CYAN_ROSE_CROP = new CyanRoseCropBlock(
		BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
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
	);
	public static final Block POTTED_CYAN_ROSE = Blocks.flowerPot(CYAN_ROSE);

	// GRANITE

	public static final Block CHISELED_POLISHED_GRANITE = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_GRANITE));

	public static final Block POLISHED_GRANITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_GRANITE));
	public static final Block POLISHED_GRANITE_BRICK_STAIRS = new StairBlock(
		POLISHED_GRANITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(POLISHED_GRANITE_BRICKS)
	);
	public static final Block POLISHED_GRANITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_GRANITE_BRICKS));
	public static final Block POLISHED_GRANITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_GRANITE_BRICKS));
	public static final BlockFamily FAMILY_POLISHED_GRANITE_BRICK = BlockFamilies.familyBuilder(POLISHED_GRANITE_BRICKS)
		.stairs(POLISHED_GRANITE_BRICK_STAIRS)
		.slab(POLISHED_GRANITE_BRICK_SLAB)
		.wall(POLISHED_GRANITE_BRICK_WALL)
		.getFamily();

	public static final Block CRACKED_POLISHED_GRANITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_GRANITE));

	public static final Block MOSSY_POLISHED_GRANITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_GRANITE));
	public static final Block MOSSY_POLISHED_GRANITE_BRICK_STAIRS = new StairBlock(
		MOSSY_POLISHED_GRANITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_GRANITE_BRICKS)
	);
	public static final Block MOSSY_POLISHED_GRANITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_GRANITE_BRICKS));
	public static final Block MOSSY_POLISHED_GRANITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_GRANITE_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_POLISHED_GRANITE_BRICK = BlockFamilies.familyBuilder(MOSSY_POLISHED_GRANITE_BRICKS)
		.stairs(MOSSY_POLISHED_GRANITE_BRICK_STAIRS)
		.slab(MOSSY_POLISHED_GRANITE_BRICK_SLAB)
		.wall(MOSSY_POLISHED_GRANITE_BRICK_WALL)
		.getFamily();

	// DIORITE

	public static final Block CHISELED_POLISHED_DIORITE = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE));

	public static final Block POLISHED_DIORITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE));
	public static final Block POLISHED_DIORITE_BRICK_STAIRS = new StairBlock(
		POLISHED_DIORITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(POLISHED_DIORITE_BRICKS)
	);
	public static final Block POLISHED_DIORITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_DIORITE_BRICKS));
	public static final Block POLISHED_DIORITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_DIORITE_BRICKS));
	public static final BlockFamily FAMILY_POLISHED_DIORITE_BRICK = BlockFamilies.familyBuilder(POLISHED_DIORITE_BRICKS)
		.stairs(POLISHED_DIORITE_BRICK_STAIRS)
		.slab(POLISHED_DIORITE_BRICK_SLAB)
		.wall(POLISHED_DIORITE_BRICK_WALL)
		.getFamily();

	public static final Block CRACKED_POLISHED_DIORITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE));

	public static final Block MOSSY_POLISHED_DIORITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE));
	public static final Block MOSSY_POLISHED_DIORITE_BRICK_STAIRS = new StairBlock(
		MOSSY_POLISHED_DIORITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_DIORITE_BRICKS)
	);
	public static final Block MOSSY_POLISHED_DIORITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_DIORITE_BRICKS));
	public static final Block MOSSY_POLISHED_DIORITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_DIORITE_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_POLISHED_DIORITE_BRICK = BlockFamilies.familyBuilder(MOSSY_POLISHED_DIORITE_BRICKS)
		.stairs(MOSSY_POLISHED_DIORITE_BRICK_STAIRS)
		.slab(MOSSY_POLISHED_DIORITE_BRICK_SLAB)
		.wall(MOSSY_POLISHED_DIORITE_BRICK_WALL)
		.getFamily();

	// ANDESITE

	public static final Block CHISELED_POLISHED_ANDESITE = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_ANDESITE));

	public static final Block POLISHED_ANDESITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_ANDESITE));
	public static final Block POLISHED_ANDESITE_BRICK_STAIRS = new StairBlock(
		POLISHED_ANDESITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(POLISHED_ANDESITE_BRICKS)
	);
	public static final Block POLISHED_ANDESITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_ANDESITE_BRICKS));
	public static final Block POLISHED_ANDESITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_ANDESITE_BRICKS));
	public static final BlockFamily FAMILY_POLISHED_ANDESITE_BRICK = BlockFamilies.familyBuilder(POLISHED_ANDESITE_BRICKS)
		.stairs(POLISHED_ANDESITE_BRICK_STAIRS)
		.slab(POLISHED_ANDESITE_BRICK_SLAB)
		.wall(POLISHED_ANDESITE_BRICK_WALL)
		.getFamily();

	public static final Block CRACKED_POLISHED_ANDESITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_ANDESITE));

	public static final Block MOSSY_POLISHED_ANDESITE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_ANDESITE));
	public static final Block MOSSY_POLISHED_ANDESITE_BRICK_STAIRS = new StairBlock(
		MOSSY_POLISHED_ANDESITE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_ANDESITE_BRICKS)
	);
	public static final Block MOSSY_POLISHED_ANDESITE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_ANDESITE_BRICKS));
	public static final Block MOSSY_POLISHED_ANDESITE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_POLISHED_ANDESITE_BRICKS));
	public static final BlockFamily FAMILY_MOSSY_POLISHED_ANDESITE_BRICK = BlockFamilies.familyBuilder(MOSSY_POLISHED_ANDESITE_BRICKS)
		.stairs(MOSSY_POLISHED_ANDESITE_BRICK_STAIRS)
		.slab(MOSSY_POLISHED_ANDESITE_BRICK_SLAB)
		.wall(MOSSY_POLISHED_ANDESITE_BRICK_WALL)
		.getFamily();

	// MOSSY DEEPSLATE

	public static final Block MOSSY_COBBLED_DEEPSLATE = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE));
	public static final Block MOSSY_COBBLED_DEEPSLATE_STAIRS = new StairBlock(
		MOSSY_COBBLED_DEEPSLATE.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)
	);
	public static final Block MOSSY_COBBLED_DEEPSLATE_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB));
	public static final Block MOSSY_COBBLED_DEEPSLATE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL));
	public static final BlockFamily FAMILY_MOSSY_COBBLED_DEEPSLATE = BlockFamilies.familyBuilder(MOSSY_COBBLED_DEEPSLATE)
		.stairs(MOSSY_COBBLED_DEEPSLATE_STAIRS)
		.slab(MOSSY_COBBLED_DEEPSLATE_SLAB)
		.wall(MOSSY_COBBLED_DEEPSLATE_WALL)
		.getFamily();

	public static final Block MOSSY_DEEPSLATE_BRICKS = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS));
	public static final Block MOSSY_DEEPSLATE_BRICK_STAIRS = new StairBlock(
		MOSSY_DEEPSLATE_BRICKS.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_STAIRS)
	);
	public static final Block MOSSY_DEEPSLATE_BRICK_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_SLAB));
	public static final Block MOSSY_DEEPSLATE_BRICK_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICK_WALL));
	public static final BlockFamily FAMILY_MOSSY_DEEPSLATE_BRICKS = BlockFamilies.familyBuilder(MOSSY_DEEPSLATE_BRICKS)
		.stairs(MOSSY_DEEPSLATE_BRICK_STAIRS)
		.slab(MOSSY_DEEPSLATE_BRICK_SLAB)
		.wall(MOSSY_DEEPSLATE_BRICK_WALL)
		.getFamily();

	public static final Block MOSSY_DEEPSLATE_TILES = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES));
	public static final Block MOSSY_DEEPSLATE_TILE_STAIRS = new StairBlock(
		MOSSY_DEEPSLATE_TILES.defaultBlockState(),
		BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILE_STAIRS)
	);
	public static final Block MOSSY_DEEPSLATE_TILE_SLAB = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILE_SLAB));
	public static final Block MOSSY_DEEPSLATE_TILE_WALL = new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILE_WALL));
	public static final BlockFamily FAMILY_MOSSY_DEEPSLATE_TILES = BlockFamilies.familyBuilder(MOSSY_DEEPSLATE_TILES)
		.stairs(MOSSY_DEEPSLATE_TILE_STAIRS)
		.slab(MOSSY_DEEPSLATE_TILE_SLAB)
		.wall(MOSSY_DEEPSLATE_TILE_WALL)
		.getFamily();

	public static final CoffinBlock COFFIN = new CoffinBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.noOcclusion()
			.lightLevel(state -> state.getValue(TrailierBlockStateProperties.COFFIN_STATE).getLightLevel())
			.requiresCorrectToolForDrops()
			.sound(RegisterSounds.COFFIN)
			.strength(50F)
			.isViewBlocking(Blocks::never)
	);

	public static final SurveyorBlock SURVEYOR = new SurveyorBlock(
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.STONE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(3.0F)
			.requiresCorrectToolForDrops()
			.isRedstoneConductor(Blocks::never)
	);

	public static void init() {
		registerBlockAfter(Blocks.SUSPICIOUS_SAND, "suspicious_red_sand", SUSPICIOUS_RED_SAND, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlockAfter(SUSPICIOUS_RED_SAND, "suspicious_dirt",SUSPICIOUS_DIRT, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlockAfter(SUSPICIOUS_DIRT, "suspicious_clay", SUSPICIOUS_CLAY, CreativeModeTabs.FUNCTIONAL_BLOCKS);

		registerBlockAfter(Blocks.TORCHFLOWER, "cyan_rose", CYAN_ROSE, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("cyan_rose_crop", CYAN_ROSE_CROP);
		registerBlock("potted_cyan_rose", POTTED_CYAN_ROSE);

		registerBlockBefore(Blocks.POLISHED_GRANITE, "chiseled_polished_granite", CHISELED_POLISHED_GRANITE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.POLISHED_GRANITE_SLAB, "polished_granite_bricks", POLISHED_GRANITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_GRANITE_BRICKS, "cracked_polished_granite_bricks", CRACKED_POLISHED_GRANITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CRACKED_POLISHED_GRANITE_BRICKS, "polished_granite_brick_stairs", POLISHED_GRANITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_GRANITE_BRICK_STAIRS, "polished_granite_brick_slab", POLISHED_GRANITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_GRANITE_BRICK_SLAB, "polished_granite_brick_wall", POLISHED_GRANITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_GRANITE_BRICK_WALL, "mossy_polished_granite_bricks", MOSSY_POLISHED_GRANITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_GRANITE_BRICKS, "mossy_polished_granite_brick_stairs", MOSSY_POLISHED_GRANITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_GRANITE_BRICK_SLAB, "mossy_polished_granite_brick_slab", MOSSY_POLISHED_GRANITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_GRANITE_BRICK_WALL, "mossy_polished_granite_brick_wall", MOSSY_POLISHED_GRANITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockBefore(Blocks.POLISHED_DIORITE, "chiseled_polished_diorite", CHISELED_POLISHED_DIORITE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.POLISHED_DIORITE_SLAB, "polished_diorite_bricks", POLISHED_DIORITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_DIORITE_BRICKS, "cracked_polished_diorite_bricks", CRACKED_POLISHED_DIORITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CRACKED_POLISHED_DIORITE_BRICKS, "polished_diorite_brick_stairs", POLISHED_DIORITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_DIORITE_BRICK_STAIRS, "polished_diorite_brick_slab", POLISHED_DIORITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_DIORITE_BRICK_SLAB, "polished_diorite_brick_wall", POLISHED_DIORITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_DIORITE_BRICK_WALL, "mossy_polished_diorite_bricks", MOSSY_POLISHED_DIORITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_DIORITE_BRICKS, "mossy_polished_diorite_brick_stairs", MOSSY_POLISHED_DIORITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_DIORITE_BRICK_SLAB, "mossy_polished_diorite_brick_slab", MOSSY_POLISHED_DIORITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_DIORITE_BRICK_WALL, "mossy_polished_diorite_brick_wall", MOSSY_POLISHED_DIORITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockBefore(Blocks.POLISHED_ANDESITE, "chiseled_polished_andesite", CHISELED_POLISHED_ANDESITE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Blocks.POLISHED_ANDESITE_SLAB, "polished_andesite_bricks", POLISHED_ANDESITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_ANDESITE_BRICKS, "cracked_polished_andesite_bricks", CRACKED_POLISHED_ANDESITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CRACKED_POLISHED_ANDESITE_BRICKS, "polished_andesite_brick_stairs", POLISHED_ANDESITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_ANDESITE_BRICK_STAIRS, "polished_andesite_brick_slab", POLISHED_ANDESITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_ANDESITE_BRICK_SLAB, "polished_andesite_brick_wall", POLISHED_ANDESITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(POLISHED_ANDESITE_BRICK_WALL, "mossy_polished_andesite_bricks", MOSSY_POLISHED_ANDESITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_ANDESITE_BRICKS, "mossy_polished_andesite_brick_stairs", MOSSY_POLISHED_ANDESITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_ANDESITE_BRICK_SLAB, "mossy_polished_andesite_brick_slab", MOSSY_POLISHED_ANDESITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(MOSSY_POLISHED_ANDESITE_BRICK_WALL, "mossy_polished_andesite_brick_wall", MOSSY_POLISHED_ANDESITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS);

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

		registerBlockAfter(Blocks.VAULT, "coffin", COFFIN, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlockAfter(Blocks.OBSERVER, "surveyor", SURVEYOR, CreativeModeTabs.REDSTONE_BLOCKS);
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
		if (BuiltInRegistries.BLOCK.getOptional(TrailierTalesSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.BLOCK, TrailierTalesSharedConstants.id(path), block);
		}
	}

	private static void actualRegisterBlockItem(String path, Block block) {
		if (BuiltInRegistries.ITEM.getOptional(TrailierTalesSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, TrailierTalesSharedConstants.id(path), new BlockItem(block, new Item.Properties()));
		}
	}

}
