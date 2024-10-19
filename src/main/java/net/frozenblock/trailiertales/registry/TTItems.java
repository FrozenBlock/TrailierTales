package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.lib.item.api.sherd.SherdRegistry;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import java.util.function.Function;

public class TTItems {
	public static final SpawnEggItem APPARITION_SPAWN_EGG = register("apparition_spawn_egg",
		properties -> new SpawnEggItem(
			TTEntityTypes.APPARITION,
			11712721,
			10663385,
			properties
		),
		new Properties()
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item ECTOPLASM = register("ectoplasm",
		Item::new,
		new Properties()
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Item CYAN_ROSE_SEEDS = register("cyan_rose_seeds",
		createBlockItemWithCustomItemName(TTBlocks.CYAN_ROSE_CROP),
		new Properties()
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item MANEDROP_GERM = register("manedrop_germ",
		createBlockItemWithCustomItemName(TTBlocks.MANEDROP_CROP),
		new Properties()
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item DAWNTRAIL_SEEDS = register("dawntrail_seeds",
		createBlockItemWithCustomItemName(TTBlocks.DAWNTRAIL_CROP),
		new Properties()
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Item BAIT_POTTERY_SHERD = register("bait_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item BLOOM_POTTERY_SHERD = register("bloom_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item BOLT_POTTERY_SHERD = register("bolt_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item BULLSEYE_POTTERY_SHERD = register("bullseye_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CARRIER_POTTERY_SHERD = register("carrier_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CLUCK_POTTERY_SHERD = register("cluck_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CRAWL_POTTERY_SHERD = register("crawl_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CRESCENT_POTTERY_SHERD = register("crescent_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CULTIVATOR_POTTERY_SHERD = register("cultivator_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item DROUGHT_POTTERY_SHERD = register("drought_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item ESSENCE_POTTERY_SHERD = register("essence_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item EYE_POTTERY_SHERD = register("eye_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item FOCUS_POTTERY_SHERD = register("focus_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item HEIGHT_POTTERY_SHERD = register("height_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item HUMP_POTTERY_SHERD = register("hump_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item ILLUMINATOR_POTTERY_SHERD = register("illuminator_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item INCIDENCE_POTTERY_SHERD = register("incidence_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item LUMBER_POTTERY_SHERD = register("lumber_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item NAVIGATOR_POTTERY_SHERD = register("navigator_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item NEEDLES_POTTERY_SHERD = register("needles_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item OMEN_POTTERY_SHERD = register("omen_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item PLUME_POTTERY_SHERD = register("plume_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item PROTECTION_POTTERY_SHERD = register("protection_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SHED_POTTERY_SHERD = register("shed_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SHINE_POTTERY_SHERD = register("shine_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SHOWER_POTTERY_SHERD = register("shower_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SPADE_POTTERY_SHERD = register("spade_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SPROUT_POTTERY_SHERD = register("sprout_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item VESSEL_POTTERY_SHERD = register("vessel_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item WITHER_POTTERY_SHERD = register("wither_pottery_sherd",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Item UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE = register("undead_armor_trim_smithing_template",
		SmithingTemplateItem::createArmorTrimTemplate,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE = register("matrix_armor_trim_smithing_template",
		SmithingTemplateItem::createArmorTrimTemplate,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item GEODE_ARMOR_TRIM_SMITHING_TEMPLATE = register("geode_armor_trim_smithing_template",
		SmithingTemplateItem::createArmorTrimTemplate,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE = register("overgrowth_armor_trim_smithing_template",
		SmithingTemplateItem::createArmorTrimTemplate,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE = register("martyr_armor_trim_smithing_template",
		SmithingTemplateItem::createArmorTrimTemplate,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE = register("zephyr_armor_trim_smithing_template",
		SmithingTemplateItem::createArmorTrimTemplate,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item COT_ARMOR_TRIM_SMITHING_TEMPLATE = register("cot_armor_trim_smithing_template",
		SmithingTemplateItem::createArmorTrimTemplate,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Item MUSIC_DISC_FAUSSE_VIE = register("music_disc_fausse_vie",
		Item::new,
		new Properties()
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
		registerSherdBefore(Items.DANGER_POTTERY_SHERD, CARRIER_POTTERY_SHERD, "carrier_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(CARRIER_POTTERY_SHERD, CLUCK_POTTERY_SHERD, "cluck_pottery_sherd", CreativeModeTabs.INGREDIENTS);
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
		registerSherdBefore(Items.PLENTY_POTTERY_SHERD, OMEN_POTTERY_SHERD, "omen_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.PLENTY_POTTERY_SHERD, PLUME_POTTERY_SHERD, "plume_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.PRIZE_POTTERY_SHERD, PROTECTION_POTTERY_SHERD, "protection_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.SHEAF_POTTERY_SHERD, SHED_POTTERY_SHERD, "shed_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.SHELTER_POTTERY_SHERD, SHINE_POTTERY_SHERD, "shine_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(SHINE_POTTERY_SHERD, SHOWER_POTTERY_SHERD, "shower_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(Items.SNORT_POTTERY_SHERD, SPADE_POTTERY_SHERD, "spade_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(SPADE_POTTERY_SHERD, SPROUT_POTTERY_SHERD, "sprout_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(SPROUT_POTTERY_SHERD, VESSEL_POTTERY_SHERD, "vessel_pottery_sherd", CreativeModeTabs.INGREDIENTS);
		registerSherdAfter(VESSEL_POTTERY_SHERD, WITHER_POTTERY_SHERD, "wither_pottery_sherd", CreativeModeTabs.INGREDIENTS);

		registerItemAfter(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, "undead_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE, "matrix_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemBefore(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, GEODE_ARMOR_TRIM_SMITHING_TEMPLATE, "geode_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemBefore(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE, "overgrowth_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE, "zephyr_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(ZEPHYR_ARMOR_TRIM_SMITHING_TEMPLATE, MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE, "martyr_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(MARTYR_ARMOR_TRIM_SMITHING_TEMPLATE, COT_ARMOR_TRIM_SMITHING_TEMPLATE, "cot_armor_trim_smithing_template", CreativeModeTabs.INGREDIENTS);

		registerItemAfter(Items.MUSIC_DISC_RELIC, MUSIC_DISC_FAUSSE_VIE, "music_disc_fausse_vie", CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static @NotNull <T extends Item> T register(String name, @NotNull Function<Properties, Item> function) {
		return register(name, function, new Properties());
	}

	private static @NotNull <T extends Item> T register(String name, @NotNull Function<Properties, Item> function, Item.@NotNull Properties properties) {
		return (T) Items.registerItem(ResourceKey.create(Registries.ITEM, TTConstants.id(name)), function, properties);
	}

	public static Function<Properties, Item> createBlockItemWithCustomItemName(Block block) {
		return properties -> new BlockItem(block, properties.useItemDescriptionPrefix());
	}

	@SafeVarargs
	private static void registerInstrumentBefore(@NotNull Item comparedItem, @NotNull Item instrument, @NotNull String path, @NotNull TagKey<Instrument> tagKey, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		//actualRegister(instrument, path);
		FrozenCreativeTabs.addInstrumentBefore(comparedItem, instrument, tagKey, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerItem(@NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		//actualRegister(item, path);
		FrozenCreativeTabs.add(item, tabs);
	}

	@SafeVarargs
	private static void registerItemBefore(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		registerItemBefore(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerItemBefore(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		//actualRegister(item, path);
		FrozenCreativeTabs.addBefore(comparedItem, item, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerItemAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		registerItemAfter(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerItemAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		//actualRegister(item, path);
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
		registerSherdAfter(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerSherdAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterSherd(item, path);
		FrozenCreativeTabs.addAfter(comparedItem, item, tabVisibility, tabs);
	}

	private static void actualRegisterSherd(@NotNull Item item, @NotNull String path) {
		SherdRegistry.register(item, TTConstants.id(path.replace("sherd", "pattern")));
	}

}
