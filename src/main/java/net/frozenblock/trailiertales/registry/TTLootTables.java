package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.loot.v3.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.tag.TTStructureTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public class TTLootTables {
	public static final ResourceKey<LootTable> CATACOMBS_CORRIDOR = register("chests/catacombs/corridor");
	public static final ResourceKey<LootTable> CATACOMBS_TOMB = register("chests/catacombs/tomb");
	public static final ResourceKey<LootTable> CATACOMBS_TOMB_REWARD = register("chests/catacombs/tomb_reward");
	public static final ResourceKey<LootTable> CATACOMBS_DECORATED_POT = register("pots/catacombs/decorated_pot");
	public static final ResourceKey<LootTable> CATACOMBS_ARCHAEOLOGY_CORRIDOR = register("archaeology/catacombs/corridor");
	public static final ResourceKey<LootTable> CATACOMBS_ARCHAEOLOGY_CORRIDOR_RARE = register("archaeology/catacombs/corridor_rare");
	public static final ResourceKey<LootTable> CATACOMBS_ARCHAEOLOGY_TOMB = register("archaeology/catacombs/tomb");
	public static final ResourceKey<LootTable> DESERT_RUINS_ARCHAEOLOGY = register("archaeology/desert_ruins");
	public static final ResourceKey<LootTable> JUNGLE_RUINS_ARCHAEOLOGY = register("archaeology/jungle_ruins");
	public static final ResourceKey<LootTable> SAVANNA_RUINS_ARCHAEOLOGY = register("archaeology/savanna_ruins");
	public static final ResourceKey<LootTable> RUINS_ARCHAEOLOGY = register("archaeology/ruins");
	public static final ResourceKey<LootTable> DEEPSLATE_RUINS_ARCHAEOLOGY = register("archaeology/deepslate_ruins");
	public static final ResourceKey<LootTable> BADLANDS_RUINS_ARCHAEOLOGY = register("archaeology/badlands_ruins");

	public static final ResourceKey<LootTable> FOSSIL_ARCHAEOLOGY = register("archaeology/fossil");

	public static void init() {
		LootTableEvents.REPLACE.register((key, lootTable, source, registries) -> {
			if (BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.equals(key)) {
				// Removed Shelter
				// Added Bait
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(LootItem.lootTableItem(Items.ANGLER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(TTItems.BAIT_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.SNORT_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.SNIFFER_EGG))
							.add(LootItem.lootTableItem(Items.IRON_AXE))
							.add(LootItem.lootTableItem(Items.EMERALD).setWeight(2))
							.add(LootItem.lootTableItem(Items.WHEAT).setWeight(2))
							.add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(2))
							.add(LootItem.lootTableItem(Items.COAL).setWeight(2))
							.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(2))
					).build();
			} else if (BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.equals(key)) {
				// Removed Blade
				// Removed Mourner
				// Removed Plenty
				// Added Focus
				// Added Vessel
				// Added Incidence
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(LootItem.lootTableItem(TTItems.INCIDENCE_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.EXPLORER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(TTItems.VESSEL_POTTERY_SHERD))
							.add(LootItem.lootTableItem(TTItems.FOCUS_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.IRON_AXE))
							.add(LootItem.lootTableItem(Items.EMERALD).setWeight(2))
							.add(LootItem.lootTableItem(Items.WHEAT).setWeight(2))
							.add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(2))
							.add(LootItem.lootTableItem(Items.COAL).setWeight(2))
							.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(2))
					).build();
			} else if (BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.equals(key)) {
				// Removed Burn
				// Removed Danger
				// Added Protection
				// Added Crescent
				// Added Catacombs Explorer Map
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(
								LootItem.lootTableItem(Items.MAP)
									.apply(
										ExplorationMapFunction.makeExplorationMap()
											.setDestination(TTStructureTags.ON_CATACOMBS_EXPLORER_MAPS)
											.setMapDecoration(TTMapDecorationTypes.CATACOMBS)
											.setZoom((byte)1)
											.setSkipKnownStructures(false)
									)
									.apply(SetNameFunction.setName(Component.translatable("filled_map.trailiertales.catacombs"), SetNameFunction.Target.ITEM_NAME))
							)
							.add(LootItem.lootTableItem(TTItems.PROTECTION_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.SHEAF_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.FRIEND_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.HEARTBREAK_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.HOWL_POTTERY_SHERD))
							.add(LootItem.lootTableItem(TTItems.CRESCENT_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE))
							.add(LootItem.lootTableItem(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE))
							.add(LootItem.lootTableItem(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE))
							.add(LootItem.lootTableItem(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE))
							.add(LootItem.lootTableItem(Items.MUSIC_DISC_RELIC))
					).build();
			} else if (BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY.equals(key)) {
				// Removed Miner
				// Removed Skull
				// Added Spade
				// Added Carrier
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD))
							.add(LootItem.lootTableItem(TTItems.SPADE_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.PRIZE_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD))
							.add(LootItem.lootTableItem(Items.DIAMOND))
							.add(LootItem.lootTableItem(Items.TNT))
							.add(LootItem.lootTableItem(Items.GUNPOWDER))
							.add(LootItem.lootTableItem(Items.EMERALD))
					).build();
			} else if (BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY.equals(key)) {
				// Removed Arms Up
				// Added Carrier
				return LootTable.lootTable()
					.withPool(
						LootPool.lootPool()
							.setRolls(ConstantValue.exactly(1F))
							.add(LootItem.lootTableItem(TTItems.CARRIER_POTTERY_SHERD).setWeight(2))
							.add(LootItem.lootTableItem(Items.BREWER_POTTERY_SHERD).setWeight(2))
							.add(LootItem.lootTableItem(Items.BRICK))
							.add(LootItem.lootTableItem(Items.EMERALD))
							.add(LootItem.lootTableItem(Items.STICK))
							.add(
								LootItem.lootTableItem(Items.SUSPICIOUS_STEW)
									.apply(
										SetStewEffectFunction.stewEffect()
											.withEffect(MobEffects.NIGHT_VISION, UniformGenerator.between(7.0F, 10.0F))
											.withEffect(MobEffects.JUMP, UniformGenerator.between(7.0F, 10.0F))
											.withEffect(MobEffects.WEAKNESS, UniformGenerator.between(6.0F, 8.0F))
											.withEffect(MobEffects.BLINDNESS, UniformGenerator.between(5.0F, 7.0F))
											.withEffect(MobEffects.POISON, UniformGenerator.between(10.0F, 20.0F))
											.withEffect(MobEffects.SATURATION, UniformGenerator.between(7.0F, 10.0F))
									)
							)
					).build();
			}

			return null;
		});

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.SNIFFER_DIGGING.equals(key)) {
				if (TTEntityConfig.get().sniffer.cyan_rose_seeds) {
					((FabricLootTableBuilder) tableBuilder)
						.modifyPools(builder -> builder.add(LootItem.lootTableItem(TTItems.CYAN_ROSE_SEEDS)));
				}
				if (TTEntityConfig.get().sniffer.manedrop_germs) {
					((FabricLootTableBuilder) tableBuilder)
						.modifyPools(builder -> builder.add(LootItem.lootTableItem(TTItems.MANEDROP_GERM)));
				}
				if (TTEntityConfig.get().sniffer.dawntrail_seeds) {
					((FabricLootTableBuilder) tableBuilder)
						.modifyPools(builder -> builder.add(LootItem.lootTableItem(TTItems.DAWNTRAIL_SEEDS)));
				}
			}
		});
	}

	private static @NotNull ResourceKey<LootTable> register(String path) {
		return ResourceKey.create(Registries.LOOT_TABLE, TTConstants.id(path));
	}
}
