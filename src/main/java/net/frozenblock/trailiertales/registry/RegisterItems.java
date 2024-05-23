package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class RegisterItems {

	public static final Item CYAN_ROSE_SEEDS = new ItemNameBlockItem(RegisterBlocks.CYAN_ROSE_CROP, new Item.Properties());
	public static final Item BULLSEYE_POTTERY_SHERD = new Item(new Item.Properties());
	public static final Item WITHER_POTTERY_SHERD = new Item(new Item.Properties());
	public static final Item DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE = new Item(new Item.Properties());

	public static void init() {
		registerItemAfter(Items.TORCHFLOWER_SEEDS, CYAN_ROSE_SEEDS, "cyan_rose_seeds", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemBefore(Items.BURN_POTTERY_SHERD, BULLSEYE_POTTERY_SHERD, "bullseye_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerItemBefore(BULLSEYE_POTTERY_SHERD, WITHER_POTTERY_SHERD, "wither_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE, "desolation_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
	}

	@SafeVarargs
	private static void registerInstrumentBefore(@NotNull Item comparedItem, @NotNull Item instrument, @NotNull String path, @NotNull TagKey<Instrument> tagKey, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegister(instrument, path);
		FrozenCreativeTabs.addInstrumentBefore(comparedItem, instrument, tagKey, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerItem(@NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.add(item, tabs);
	}

	@SafeVarargs
	private static void registerItemBefore(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		registerItemBefore(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerItemBefore(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.addBefore(comparedItem, item, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerItemAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		registerItemAfter(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerItemAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.addAfter(comparedItem, item, tabVisibility, tabs);
	}

	private static void actualRegister(@NotNull Item item, @NotNull String path) {
		if (BuiltInRegistries.ITEM.getOptional(TrailierTalesSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, TrailierTalesSharedConstants.id(path), item);
		}
	}

}
