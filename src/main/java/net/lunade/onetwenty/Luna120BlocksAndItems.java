package net.lunade.onetwenty;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lunade.onetwenty.block.NonFallingBrushableBlock;
import net.lunade.onetwenty.util.Luna120SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import java.util.List;

public class Luna120BlocksAndItems {

	public static final Block SUSPICIOUS_RED_SAND = new BrushableBlock(Blocks.RED_SAND, BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_ORANGE).strength(0.25f).sound(SoundType.SUSPICIOUS_SAND).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED);
	public static final BlockItem SUSPICIOUS_RED_SAND_ITEM = new BlockItem(SUSPICIOUS_RED_SAND, new Item.Properties());

	public static final Block SUSPICIOUS_DIRT = new NonFallingBrushableBlock(Blocks.DIRT, BlockBehaviour.Properties.of(Material.DEPRECATED, MaterialColor.DIRT).strength(0.25f).sound(SoundType.SUSPICIOUS_GRAVEL).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED);
	public static final BlockItem SUSPICIOUS_DIRT_ITEM = new BlockItem(SUSPICIOUS_DIRT, new Item.Properties());

	public static final Block SUSPICIOUS_CLAY = new NonFallingBrushableBlock(Blocks.CLAY, BlockBehaviour.Properties.of(Material.DEPRECATED, MaterialColor.CLAY).strength(0.25f).sound(SoundType.SUSPICIOUS_GRAVEL).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED);
	public static final BlockItem SUSPICIOUS_CLAY_ITEM = new BlockItem(SUSPICIOUS_CLAY, new Item.Properties());

	public static void init() {
		Registry.register(BuiltInRegistries.BLOCK, Luna120SharedConstants.id("suspicious_red_sand"), SUSPICIOUS_RED_SAND);
		Registry.register(BuiltInRegistries.ITEM, Luna120SharedConstants.id("suspicious_red_sand"), SUSPICIOUS_RED_SAND_ITEM);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
			entries.addAfter(Items.SUSPICIOUS_SAND,  List.of(new ItemStack(SUSPICIOUS_RED_SAND_ITEM)), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));

		Registry.register(BuiltInRegistries.BLOCK, Luna120SharedConstants.id("suspicious_dirt"), SUSPICIOUS_DIRT);
		Registry.register(BuiltInRegistries.ITEM, Luna120SharedConstants.id("suspicious_dirt"), SUSPICIOUS_DIRT_ITEM);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
			entries.addAfter(Items.SUSPICIOUS_GRAVEL,  List.of(new ItemStack(SUSPICIOUS_DIRT_ITEM)), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));

		Registry.register(BuiltInRegistries.BLOCK, Luna120SharedConstants.id("suspicious_clay"), SUSPICIOUS_CLAY);
		Registry.register(BuiltInRegistries.ITEM, Luna120SharedConstants.id("suspicious_clay"), SUSPICIOUS_CLAY_ITEM);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
			entries.addAfter(SUSPICIOUS_DIRT_ITEM,  List.of(new ItemStack(SUSPICIOUS_CLAY_ITEM)), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
	}

}
