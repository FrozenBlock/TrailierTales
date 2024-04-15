package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.block.CyanRoseCropBlock;
import net.frozenblock.trailiertales.block.NonFallingBrushableBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.level.block.SoundType;
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
		5F,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final Block POTTED_CYAN_ROSE = Blocks.flowerPot(CYAN_ROSE);

	public static void init() {
		registerBlockAfter(Blocks.SUSPICIOUS_SAND, "suspicious_red_sand", SUSPICIOUS_RED_SAND, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlockAfter(SUSPICIOUS_RED_SAND, "suspicious_dirt",SUSPICIOUS_DIRT, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlockAfter(SUSPICIOUS_DIRT, "suspicious_clay", SUSPICIOUS_CLAY, CreativeModeTabs.FUNCTIONAL_BLOCKS);

		registerBlockAfter(Blocks.TORCHFLOWER, "cyan_rose", CYAN_ROSE, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("cyan_rose_crop", CYAN_ROSE_CROP);
		registerBlock("potted_cyan_rose", POTTED_CYAN_ROSE);
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
