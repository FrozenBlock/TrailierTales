package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.tag.TTItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public final class TTItemTagProvider extends FabricTagProvider.ItemTagProvider {

	public TTItemTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@NotNull
	private TagKey<Item> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(ConventionalItemTags.MUSIC_DISCS)
			.add(TTItems.MUSIC_DISC_FAUSSE_VIE);

		this.getOrCreateTagBuilder(ItemTags.SAND)
			.add(TTBlocks.SUSPICIOUS_RED_SAND.asItem());

		this.getOrCreateTagBuilder(ItemTags.DECORATED_POT_SHERDS)
			.add(TTItems.BAIT_POTTERY_SHERD)
			.add(TTItems.BLOOM_POTTERY_SHERD)
			.add(TTItems.BOLT_POTTERY_SHERD)
			.add(TTItems.BULLSEYE_POTTERY_SHERD)
			.add(TTItems.CLUCK_POTTERY_SHERD)
			.add(TTItems.CRAWL_POTTERY_SHERD)
			.add(TTItems.CRESCENT_POTTERY_SHERD)
			.add(TTItems.CULTIVATOR_POTTERY_SHERD)
			.add(TTItems.ESSENCE_POTTERY_SHERD)
			.add(TTItems.EYE_POTTERY_SHERD)
			.add(TTItems.FOCUS_POTTERY_SHERD)
			.add(TTItems.HEIGHT_POTTERY_SHERD)
			.add(TTItems.HUMP_POTTERY_SHERD)
			.add(TTItems.ILLUMINATOR_POTTERY_SHERD)
			.add(TTItems.INCIDENCE_POTTERY_SHERD)
			.add(TTItems.LUMBER_POTTERY_SHERD)
			.add(TTItems.NAVIGATOR_POTTERY_SHERD)
			.add(TTItems.NEEDLES_POTTERY_SHERD)
			.add(TTItems.PLUME_POTTERY_SHERD)
			.add(TTItems.PROTECTION_POTTERY_SHERD)
			.add(TTItems.SHED_POTTERY_SHERD)
			.add(TTItems.SHINE_POTTERY_SHERD)
			.add(TTItems.SHOWER_POTTERY_SHERD)
			.add(TTItems.SPADE_POTTERY_SHERD)
			.add(TTItems.SPROUT_POTTERY_SHERD)
			.add(TTItems.VESSEL_POTTERY_SHERD)
			.add(TTItems.WITHER_POTTERY_SHERD);

		this.getOrCreateTagBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS)
			.add(TTItems.CYAN_ROSE_SEEDS)
			.add(TTItems.MANEDROP_GERM)
			.add(TTItems.DAWNTRAIL_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.CHICKEN_FOOD)
			.add(TTItems.CYAN_ROSE_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.PARROT_FOOD)
			.add(TTItems.CYAN_ROSE_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.SNIFFER_FOOD)
			.add(TTItems.CYAN_ROSE_SEEDS)
			.add(TTItems.MANEDROP_GERM)
			.add(TTItems.DAWNTRAIL_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
			.add(TTBlocks.CYAN_ROSE.asItem());

		this.getOrCreateTagBuilder(ItemTags.TALL_FLOWERS)
			.add(TTBlocks.MANEDROP.asItem());

		this.getOrCreateTagBuilder(ItemTags.FLOWERS)
			.add(TTBlocks.DAWNTRAIL.asItem());

		this.getOrCreateTagBuilder(ItemTags.STAIRS)
			.add(TTBlocks.GRANITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.DIORITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.ANDESITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.CALCITE_STAIRS.asItem())
			.add(TTBlocks.POLISHED_CALCITE_STAIRS.asItem())
			.add(TTBlocks.CALCITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_TUFF_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.asItem())
			.add(TTBlocks.CUT_SANDSTONE_STAIRS.asItem())
			.add(TTBlocks.CUT_RED_SANDSTONE_STAIRS.asItem())
			.add(TTBlocks.CHORAL_END_STONE_STAIRS.asItem())
			.add(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS.asItem())
			.add(TTBlocks.END_STONE_STAIRS.asItem());

		this.getOrCreateTagBuilder(ItemTags.SLABS)
			.add(TTBlocks.GRANITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_GRANITE_BRICK_SLAB.asItem())
			.add(TTBlocks.DIORITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_DIORITE_BRICK_SLAB.asItem())
			.add(TTBlocks.ANDESITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB.asItem())
			.add(TTBlocks.CALCITE_SLAB.asItem())
			.add(TTBlocks.POLISHED_CALCITE_SLAB.asItem())
			.add(TTBlocks.CALCITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_CALCITE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_TUFF_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB.asItem())
			.add(TTBlocks.CHORAL_END_STONE_SLAB.asItem())
			.add(TTBlocks.CHORAL_END_STONE_BRICK_SLAB.asItem())
			.add(TTBlocks.END_STONE_SLAB.asItem());

		this.getOrCreateTagBuilder(ItemTags.WALLS)
			.add(TTBlocks.GRANITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_GRANITE_BRICK_WALL.asItem())
			.add(TTBlocks.DIORITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_DIORITE_BRICK_WALL.asItem())
			.add(TTBlocks.ANDESITE_BRICK_WALL.asItem())
			.add(TTBlocks.CALCITE_WALL.asItem())
			.add(TTBlocks.POLISHED_CALCITE_WALL.asItem())
			.add(TTBlocks.CALCITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_CALCITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_TUFF_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL.asItem())
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL.asItem())
			.add(TTBlocks.CHORAL_END_STONE_BRICK_WALL.asItem())
			.add(TTBlocks.SMOOTH_SANDSTONE_WALL.asItem())
			.add(TTBlocks.CUT_SANDSTONE_WALL.asItem())
			.add(TTBlocks.SMOOTH_RED_SANDSTONE_WALL.asItem())
			.add(TTBlocks.CUT_RED_SANDSTONE_WALL.asItem())
			.add(TTBlocks.CHORAL_END_STONE_WALL.asItem())
			.add(TTBlocks.END_STONE_WALL.asItem())
			.add(TTBlocks.PURPUR_WALL.asItem());

		this.getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES)
			.add(TTItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE)
			.add(TTItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE)
			.add(TTItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE)
			.add(TTItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE)
			.add(TTItems.OVERGROWTH_ARMOR_TRIM_SMITHING_TEMPLATE);

		this.getOrCreateTagBuilder(TTItemTags.BRUSH_ENCHANTABLE)
			.add(Items.BRUSH);

		this.getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
			.add(Items.BRUSH);
	}
}
