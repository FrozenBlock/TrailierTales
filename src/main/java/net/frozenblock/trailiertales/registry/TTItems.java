package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.lib.item.api.sherd.SherdRegistry;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class TTItems {
	public static final SpawnEggItem APPARITION_SPAWN_EGG = new SpawnEggItem(
		TTEntities.APPARITION,
		11712721,
		10663385,
		new Item.Properties()
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item ECTOPLASM = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));

	public static final Item CYAN_ROSE_SEEDS = new ItemNameBlockItem(TTBlocks.CYAN_ROSE_CROP, new Item.Properties()
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item MANEDROP_GERM = new ItemNameBlockItem(TTBlocks.MANEDROP_CROP, new Item.Properties()
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item DAWNTRAIL_SEEDS = new ItemNameBlockItem(TTBlocks.DAWNTRAIL_CROP, new Item.Properties()
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Item BAIT_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item BLOOM_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item BOLT_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item BULLSEYE_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item CLUCK_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item CRAWL_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item CRESCENT_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item CULTIVATOR_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item DROUGHT_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item ESSENCE_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item EYE_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item FOCUS_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item HEIGHT_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item HUMP_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item ILLUMINATOR_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item INCIDENCE_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item LUMBER_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item NAVIGATOR_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item NEEDLES_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item PLUME_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item PROTECTION_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item SHED_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item SHINE_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item SHOWER_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item SPADE_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item SPROUT_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item VESSEL_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));
	public static final Item WITHER_POTTERY_SHERD = new Item(new Item.Properties().requiredFeatures(TTFeatureFlags.FEATURE_FLAG));

	public static final Item DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE = SmithingTemplateItem.createArmorTrimTemplate(
		TTTrimPatterns.DESOLATION,
		TTFeatureFlags.FEATURE_FLAG
	);
	public static final Item UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE = SmithingTemplateItem.createArmorTrimTemplate(
		TTTrimPatterns.UNDEAD,
		TTFeatureFlags.FEATURE_FLAG
	);
	public static final Item MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE = SmithingTemplateItem.createArmorTrimTemplate(
		TTTrimPatterns.MATRIX,
		TTFeatureFlags.FEATURE_FLAG
	);
	public static final Item GEODE_ARMOR_TRIM_SMITHING_TEMPLATE = SmithingTemplateItem.createArmorTrimTemplate(
		TTTrimPatterns.GEODE,
		TTFeatureFlags.FEATURE_FLAG
	);
	public static final Item OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE = SmithingTemplateItem.createArmorTrimTemplate(
		TTTrimPatterns.OVERGROWTH,
		TTFeatureFlags.FEATURE_FLAG
	);

	public static final Item MUSIC_DISC_FAUSSE_VIE = new Item(new Item.Properties()
		.stacksTo(1)
		.rarity(Rarity.RARE)
		.jukeboxPlayable(TTJukeboxSongs.FAUSSE_VIE)
		.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static void init() {
		registerItemAfter(Items.ALLAY_SPAWN_EGG, APPARITION_SPAWN_EGG, "apparition_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemBefore(Items.MAGMA_CREAM, ECTOPLASM, "ectoplasm", CreativeModeTabs.INGREDIENTS);

		registerItemAfter(Items.TORCHFLOWER_SEEDS, CYAN_ROSE_SEEDS, "cyan_rose_seeds", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(CYAN_ROSE_SEEDS, DAWNTRAIL_SEEDS, "dawntrail_seeds", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.PITCHER_POD, MANEDROP_GERM, "manedrop_germ", CreativeModeTabs.NATURAL_BLOCKS);

		registerSherdBefore(Items.BLADE_POTTERY_SHERD, BAIT_POTTERY_SHERD, "bait_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.BLADE_POTTERY_SHERD, BLOOM_POTTERY_SHERD, "bloom_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(BLOOM_POTTERY_SHERD, BOLT_POTTERY_SHERD, "bolt_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdBefore(Items.BURN_POTTERY_SHERD, BULLSEYE_POTTERY_SHERD, "bullseye_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdBefore(Items.DANGER_POTTERY_SHERD, CLUCK_POTTERY_SHERD, "cluck_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdBefore(CLUCK_POTTERY_SHERD, CRAWL_POTTERY_SHERD, "crawl_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(CRAWL_POTTERY_SHERD, CRESCENT_POTTERY_SHERD, "crescent_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(CRESCENT_POTTERY_SHERD, CULTIVATOR_POTTERY_SHERD, "cultivator_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.DANGER_POTTERY_SHERD, DROUGHT_POTTERY_SHERD, "drought_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdBefore(Items.EXPLORER_POTTERY_SHERD, ESSENCE_POTTERY_SHERD, "essence_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.EXPLORER_POTTERY_SHERD, EYE_POTTERY_SHERD, "eye_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.FLOW_POTTERY_SHERD, FOCUS_POTTERY_SHERD, "focus_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdBefore(Items.HOWL_POTTERY_SHERD, HEIGHT_POTTERY_SHERD, "height_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.HOWL_POTTERY_SHERD, HUMP_POTTERY_SHERD, "hump_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(HUMP_POTTERY_SHERD, ILLUMINATOR_POTTERY_SHERD, "illuminator_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(ILLUMINATOR_POTTERY_SHERD, INCIDENCE_POTTERY_SHERD, "incidence_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdBefore(Items.MINER_POTTERY_SHERD, LUMBER_POTTERY_SHERD, "lumber_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.MOURNER_POTTERY_SHERD, NAVIGATOR_POTTERY_SHERD, "navigator_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(NAVIGATOR_POTTERY_SHERD, NEEDLES_POTTERY_SHERD, "needles_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.PLENTY_POTTERY_SHERD, PLUME_POTTERY_SHERD, "plume_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.PRIZE_POTTERY_SHERD, PROTECTION_POTTERY_SHERD, "protection_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.SHEAF_POTTERY_SHERD, SHED_POTTERY_SHERD, "shed_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.SHELTER_POTTERY_SHERD, SHINE_POTTERY_SHERD, "shine_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(SHINE_POTTERY_SHERD, SHOWER_POTTERY_SHERD, "shower_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.SNORT_POTTERY_SHERD, SPADE_POTTERY_SHERD, "spade_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(SPADE_POTTERY_SHERD, SPROUT_POTTERY_SHERD, "sprout_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(SPROUT_POTTERY_SHERD, VESSEL_POTTERY_SHERD, "vessel_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(VESSEL_POTTERY_SHERD, WITHER_POTTERY_SHERD, "wither_pottery_sherd", CreativeModeTabs.INGREDIENTS);

		registerItemAfter(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE, "desolation_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE, UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, "undead_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE, "matrix_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE, GEODE_ARMOR_TRIM_SMITHING_TEMPLATE, "geode_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(GEODE_ARMOR_TRIM_SMITHING_TEMPLATE, OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE, "overgrowth_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);

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

	private static boolean actualRegister(@NotNull Item item, @NotNull String path) {
		if (BuiltInRegistries.ITEM.getOptional(TTConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, TTConstants.id(path), item);
			return true;
		}
		return false;
	}

	@SafeVarargs
	private static void registerSherdBefore(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		registerSherdBefore(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerSherdBefore(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterSherd(item, path);
		FrozenCreativeTabs.addBefore(comparedItem, item, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerSherdAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		registerItemAfter(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerSherdAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterSherd(item, path);
		FrozenCreativeTabs.addAfter(comparedItem, item, tabVisibility, tabs);
	}

	private static void actualRegisterSherd(@NotNull Item item, @NotNull String path) {
		if (actualRegister(item, path)) {
			SherdRegistry.register(item, TTConstants.id(path.replace("sherd", "pattern")));
		}
	}

}
