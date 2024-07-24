package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class RegisterItems {

	public static final Item CYAN_ROSE_SEEDS = new ItemNameBlockItem(RegisterBlocks.CYAN_ROSE_CROP, new Item.Properties());

	public static final Item BULLSEYE_POTTERY_SHERD = new Item(new Item.Properties());
	public static final Item WITHER_POTTERY_SHERD = new Item(new Item.Properties());
	public static final Item BLOOM_POTTERY_SHERD = new Item(new Item.Properties());
	public static final Item INCIDENCE_POTTERY_SHERD = new Item(new Item.Properties());
	public static final Item CULTIVATOR_POTTERY_SHERD = new Item(new Item.Properties());
	public static final Item SPADE_POTTERY_SHERD = new Item(new Item.Properties());

	public static final Item DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE = SmithingTemplateItem.createArmorTrimTemplate(
		ResourceKey.create(Registries.TRIM_PATTERN, TrailierConstants.id("desolation"))
	);
	public static final Item UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE = SmithingTemplateItem.createArmorTrimTemplate(
		ResourceKey.create(Registries.TRIM_PATTERN, TrailierConstants.id("undead"))
	);

	public static final Item MUSIC_DISC_FAUSSE_VIE = new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(RegisterJukeboxSongs.FAUSSE_VIE));

	public static void init() {
		registerItemAfter(Items.TORCHFLOWER_SEEDS, CYAN_ROSE_SEEDS, "cyan_rose_seeds", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemBefore(Items.BURN_POTTERY_SHERD, BULLSEYE_POTTERY_SHERD, "bullseye_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.SNORT_POTTERY_SHERD, WITHER_POTTERY_SHERD, "wither_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.BLADE_POTTERY_SHERD, BLOOM_POTTERY_SHERD, "bloom_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.HOWL_POTTERY_SHERD, INCIDENCE_POTTERY_SHERD, "incidence_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerItemBefore(Items.DANGER_POTTERY_SHERD, CULTIVATOR_POTTERY_SHERD, "cultivator_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.SKULL_POTTERY_SHERD, SPADE_POTTERY_SHERD, "spade_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE, "desolation_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE, UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, "undead_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.MUSIC_DISC_RELIC, MUSIC_DISC_FAUSSE_VIE, "music_disc_fausse_vie", CreativeModeTabs.TOOLS_AND_UTILITIES);
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
		if (BuiltInRegistries.ITEM.getOptional(TrailierConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, TrailierConstants.id(path), item);
		}
	}

}
