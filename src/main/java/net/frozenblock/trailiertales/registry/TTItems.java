package net.frozenblock.trailiertales.registry;

import java.util.function.Function;
import net.frozenblock.lib.item.api.PlaceInAirBlockItem;
import net.frozenblock.lib.item.api.sherd.SherdRegistry;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TTItems {
	public static final SpawnEggItem APPARITION_SPAWN_EGG = register("apparition_spawn_egg",
		properties -> new SpawnEggItem(
			TTEntityTypes.APPARITION,
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
	public static final Item ECTOPLASM_BLOCK = register("ectoplasm_block",
		properties -> new PlaceInAirBlockItem(TTBlocks.ECTOPLASM_BLOCK, properties),
		new Properties()
			.useBlockDescriptionPrefix()
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

	/**
	 * Don't forget to go to {@link TTDecoratedPotPatternRegistry} to register patterns!
	 */
	public static final Item AURORA_POTTERY_SHERD = registerSherd("aurora",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item BAIT_POTTERY_SHERD = registerSherd("bait",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item BLOOM_POTTERY_SHERD = registerSherd("bloom",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item BOLT_POTTERY_SHERD = registerSherd("bolt",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item BULLSEYE_POTTERY_SHERD = registerSherd("bullseye",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CARRIER_POTTERY_SHERD = registerSherd("carrier",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CLUCK_POTTERY_SHERD = registerSherd("cluck",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CRAWL_POTTERY_SHERD = registerSherd("crawl",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CRESCENT_POTTERY_SHERD = registerSherd("crescent",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item CULTIVATOR_POTTERY_SHERD = registerSherd("cultivator",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item DROUGHT_POTTERY_SHERD = registerSherd("drought",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item ENCLOSURE_POTTERY_SHERD = registerSherd("enclosure",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item ESSENCE_POTTERY_SHERD = registerSherd("essence",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item EYE_POTTERY_SHERD = registerSherd("eye",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item FOCUS_POTTERY_SHERD = registerSherd("focus",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item FROST_POTTERY_SHERD = registerSherd("frost",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item HARE_POTTERY_SHERD = registerSherd("hare",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item HEIGHT_POTTERY_SHERD = registerSherd("height",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item HUMP_POTTERY_SHERD = registerSherd("hump",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item ILLUMINATOR_POTTERY_SHERD = registerSherd("illuminator",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item INCIDENCE_POTTERY_SHERD = registerSherd("incidence",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item LUMBER_POTTERY_SHERD = registerSherd("lumber",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item NAVIGATOR_POTTERY_SHERD = registerSherd("navigator",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item NEEDLES_POTTERY_SHERD = registerSherd("needles",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item OMEN_POTTERY_SHERD = registerSherd("omen",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item PLUME_POTTERY_SHERD = registerSherd("plume",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item PROTECTION_POTTERY_SHERD = registerSherd("protection",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SHED_POTTERY_SHERD = registerSherd("shed",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SHINE_POTTERY_SHERD = registerSherd("shine",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SHOWER_POTTERY_SHERD = registerSherd("shower",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SPADE_POTTERY_SHERD = registerSherd("spade",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item SPROUT_POTTERY_SHERD = registerSherd("sprout",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item VESSEL_POTTERY_SHERD = registerSherd("vessel",
		Item::new,
		new Properties()
			.rarity(Rarity.UNCOMMON)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);
	public static final Item WITHER_POTTERY_SHERD = registerSherd("wither",
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
	public static final Item EMBRACE_ARMOR_TRIM_SMITHING_TEMPLATE = register("embrace_armor_trim_smithing_template",
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

	public static final Item MUSIC_DISC_STASIS = register("music_disc_stasis",
		Item::new,
		new Properties()
			.stacksTo(1)
			.rarity(Rarity.RARE)
			.jukeboxPlayable(TTJukeboxSongs.STASIS)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static final Item MUSIC_DISC_OSSUAIRE = register("music_disc_ossuaire",
		Item::new,
		new Properties()
			.stacksTo(1)
			.rarity(Rarity.RARE)
			.jukeboxPlayable(TTJukeboxSongs.OSSUAIRE)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
	);

	public static void init() {
	}

	private static @NotNull <T extends Item> T register(String name, @NotNull Function<Properties, Item> function, Item.@NotNull Properties properties) {
		return (T) Items.registerItem(ResourceKey.create(Registries.ITEM, TTConstants.id(name)), function, properties);
	}

	private static @NotNull <T extends Item> T registerSherd(String name, @NotNull Function<Properties, Item> function, Item.@NotNull Properties properties) {
		T item = (T) Items.registerItem(ResourceKey.create(Registries.ITEM, TTConstants.id(name + "_pottery_sherd")), function, properties);
		SherdRegistry.register(item, TTConstants.id(name));
		return item;
	}

	@Contract(pure = true)
	public static @NotNull Function<Properties, Item> createBlockItemWithCustomItemName(Block block) {
		return properties -> new BlockItem(block, properties.useItemDescriptionPrefix());
	}

}
