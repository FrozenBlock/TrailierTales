package net.lunade.onetwenty;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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

	public static void init() {
		Registry.register(BuiltInRegistries.BLOCK, Luna120SharedConstants.id("suspicious_red_sand"), SUSPICIOUS_RED_SAND);

		Registry.register(BuiltInRegistries.ITEM, Luna120SharedConstants.id("suspicious_red_sand"), SUSPICIOUS_RED_SAND_ITEM);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
			entries.addAfter(Items.SUSPICIOUS_SAND,  List.of(new ItemStack(SUSPICIOUS_RED_SAND_ITEM)), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
	}

}
