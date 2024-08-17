package net.frozenblock.trailiertales.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.tag.TrailierItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;

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
			.add(RegisterItems.MUSIC_DISC_FAUSSE_VIE);

		this.getOrCreateTagBuilder(ItemTags.SAND)
			.add(RegisterBlocks.SUSPICIOUS_RED_SAND.asItem());

		this.getOrCreateTagBuilder(ItemTags.DECORATED_POT_SHERDS)
			.add(RegisterItems.BAIT_POTTERY_SHERD)
			.add(RegisterItems.BLOOM_POTTERY_SHERD)
			.add(RegisterItems.BOLT_POTTERY_SHERD)
			.add(RegisterItems.BULLSEYE_POTTERY_SHERD)
			.add(RegisterItems.CLUCK_POTTERY_SHERD)
			.add(RegisterItems.CRAWL_POTTERY_SHERD)
			.add(RegisterItems.CRESCENT_POTTERY_SHERD)
			.add(RegisterItems.CULTIVATOR_POTTERY_SHERD)
			.add(RegisterItems.ESSENCE_POTTERY_SHERD)
			.add(RegisterItems.EYE_POTTERY_SHERD)
			.add(RegisterItems.FOCUS_POTTERY_SHERD)
			.add(RegisterItems.HEIGHT_POTTERY_SHERD)
			.add(RegisterItems.HUMP_POTTERY_SHERD)
			.add(RegisterItems.ILLUMINATOR_POTTERY_SHERD)
			.add(RegisterItems.INCIDENCE_POTTERY_SHERD)
			.add(RegisterItems.LUMBER_POTTERY_SHERD)
			.add(RegisterItems.NAVIGATOR_POTTERY_SHERD)
			.add(RegisterItems.NEEDLES_POTTERY_SHERD)
			.add(RegisterItems.PLUME_POTTERY_SHERD)
			.add(RegisterItems.PROTECTION_POTTERY_SHERD)
			.add(RegisterItems.SHED_POTTERY_SHERD)
			.add(RegisterItems.SHINE_POTTERY_SHERD)
			.add(RegisterItems.SHOWER_POTTERY_SHERD)
			.add(RegisterItems.SPADE_POTTERY_SHERD)
			.add(RegisterItems.SPROUT_POTTERY_SHERD)
			.add(RegisterItems.VESSEL_POTTERY_SHERD)
			.add(RegisterItems.WITHER_POTTERY_SHERD);

		this.getOrCreateTagBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS)
			.add(RegisterItems.CYAN_ROSE_SEEDS)
			.add(RegisterItems.MANEDROP_GERM)
			.add(RegisterItems.DAWNTRAIL_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.CHICKEN_FOOD)
			.add(RegisterItems.CYAN_ROSE_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.PARROT_FOOD)
			.add(RegisterItems.CYAN_ROSE_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.SNIFFER_FOOD)
			.add(RegisterItems.CYAN_ROSE_SEEDS)
			.add(RegisterItems.MANEDROP_GERM)
			.add(RegisterItems.DAWNTRAIL_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
			.add(RegisterBlocks.CYAN_ROSE.asItem());

		this.getOrCreateTagBuilder(ItemTags.TALL_FLOWERS)
			.add(RegisterBlocks.MANEDROP.asItem());

		this.getOrCreateTagBuilder(ItemTags.FLOWERS)
			.add(RegisterBlocks.DAWNTRAIL.asItem());

		this.getOrCreateTagBuilder(ItemTags.STAIRS)
			.add(RegisterBlocks.GRANITE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.DIORITE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.ANDESITE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.CALCITE_STAIRS.asItem())
			.add(RegisterBlocks.POLISHED_CALCITE_STAIRS.asItem())
			.add(RegisterBlocks.CALCITE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS.asItem())
			.add(RegisterBlocks.CUT_SANDSTONE_STAIRS.asItem())
			.add(RegisterBlocks.CUT_RED_SANDSTONE_STAIRS.asItem())
			.add(RegisterBlocks.CHORAL_END_STONE_STAIRS.asItem())
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_STAIRS.asItem())
			.add(RegisterBlocks.END_STONE_STAIRS.asItem());

		this.getOrCreateTagBuilder(ItemTags.SLABS)
			.add(RegisterBlocks.GRANITE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.DIORITE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.ANDESITE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.CALCITE_SLAB.asItem())
			.add(RegisterBlocks.POLISHED_CALCITE_SLAB.asItem())
			.add(RegisterBlocks.CALCITE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_BRICK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB.asItem())
			.add(RegisterBlocks.CHORAL_END_STONE_SLAB.asItem())
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_SLAB.asItem())
			.add(RegisterBlocks.END_STONE_SLAB.asItem());

		this.getOrCreateTagBuilder(ItemTags.WALLS)
			.add(RegisterBlocks.GRANITE_BRICK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_WALL.asItem())
			.add(RegisterBlocks.DIORITE_BRICK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_WALL.asItem())
			.add(RegisterBlocks.ANDESITE_BRICK_WALL.asItem())
			.add(RegisterBlocks.CALCITE_WALL.asItem())
			.add(RegisterBlocks.POLISHED_CALCITE_WALL.asItem())
			.add(RegisterBlocks.CALCITE_BRICK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_BRICK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_WALL.asItem())
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL.asItem())
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_WALL.asItem())
			.add(RegisterBlocks.SMOOTH_SANDSTONE_WALL.asItem())
			.add(RegisterBlocks.CUT_SANDSTONE_WALL.asItem())
			.add(RegisterBlocks.SMOOTH_RED_SANDSTONE_WALL.asItem())
			.add(RegisterBlocks.CUT_RED_SANDSTONE_WALL.asItem())
			.add(RegisterBlocks.CHORAL_END_STONE_WALL.asItem())
			.add(RegisterBlocks.END_STONE_WALL.asItem())
			.add(RegisterBlocks.PURPUR_WALL.asItem());

		this.getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES)
			.add(RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE)
			.add(RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE)
			.add(RegisterItems.ZHEN_ARMOR_TRIM_SMITHING_TEMPLATE);

		this.getOrCreateTagBuilder(TrailierItemTags.BRUSH_ENCHANTABLE)
			.add(Items.BRUSH);

		this.getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
			.add(Items.BRUSH);
	}
}
