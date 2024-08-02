package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
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
		this.getOrCreateTagBuilder(ItemTags.DECORATED_POT_SHERDS)
			.add(RegisterItems.BAIT_POTTERY_SHERD)
			.add(RegisterItems.BLOOM_POTTERY_SHERD)
			.add(RegisterItems.BULLSEYE_POTTERY_SHERD)
			.add(RegisterItems.CULTIVATOR_POTTERY_SHERD)
			.add(RegisterItems.ESSENCE_POTTERY_SHERD)
			.add(RegisterItems.EYE_POTTERY_SHERD)
			.add(RegisterItems.INCIDENCE_POTTERY_SHERD)
			.add(RegisterItems.LUMBER_POTTERY_SHERD)
			.add(RegisterItems.NEEDLES_POTTERY_SHERD)
			.add(RegisterItems.PROTECTION_POTTERY_SHERD)
			.add(RegisterItems.SHINE_POTTERY_SHERD)
			.add(RegisterItems.SPADE_POTTERY_SHERD)
			.add(RegisterItems.WITHER_POTTERY_SHERD);

		this.getOrCreateTagBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS)
			.add(RegisterItems.CYAN_ROSE_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.CHICKEN_FOOD)
			.add(RegisterItems.CYAN_ROSE_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.PARROT_FOOD)
			.add(RegisterItems.CYAN_ROSE_SEEDS);

		this.getOrCreateTagBuilder(ItemTags.SNIFFER_FOOD)
			.add(RegisterItems.CYAN_ROSE_SEEDS);

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
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_STAIRS.asItem());

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
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_SLAB.asItem());

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
			.add(RegisterBlocks.PURPUR_WALL.asItem());

		this.getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES)
			.add(RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE.asItem())
			.add(RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE.asItem());

		this.getOrCreateTagBuilder(TrailierItemTags.BRUSH_ENCHANTABLE)
			.add(Items.BRUSH);

		this.getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
			.add(Items.BRUSH);
	}
}
