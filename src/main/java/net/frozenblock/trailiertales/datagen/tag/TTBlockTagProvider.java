package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class TTBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public TTBlockTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(TTBlocks.CHISELED_GRANITE_BRICKS)
			.add(TTBlocks.GRANITE_BRICKS)
			.add(TTBlocks.CRACKED_GRANITE_BRICKS)
			.add(TTBlocks.GRANITE_BRICK_STAIRS)
			.add(TTBlocks.GRANITE_BRICK_SLAB)
			.add(TTBlocks.GRANITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_GRANITE_BRICKS)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_GRANITE_WALL)

			.add(TTBlocks.CHISELED_DIORITE_BRICKS)
			.add(TTBlocks.DIORITE_BRICKS)
			.add(TTBlocks.CRACKED_DIORITE_BRICKS)
			.add(TTBlocks.DIORITE_BRICK_STAIRS)
			.add(TTBlocks.DIORITE_BRICK_SLAB)
			.add(TTBlocks.DIORITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DIORITE_BRICKS)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_DIORITE_WALL)

			.add(TTBlocks.CHISELED_ANDESITE_BRICKS)
			.add(TTBlocks.ANDESITE_BRICKS)
			.add(TTBlocks.CRACKED_ANDESITE_BRICKS)
			.add(TTBlocks.ANDESITE_BRICK_STAIRS)
			.add(TTBlocks.ANDESITE_BRICK_SLAB)
			.add(TTBlocks.ANDESITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_ANDESITE_BRICKS)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_ANDESITE_WALL)

			.add(TTBlocks.CALCITE_STAIRS)
			.add(TTBlocks.CALCITE_SLAB)
			.add(TTBlocks.CALCITE_WALL)
			.add(TTBlocks.POLISHED_CALCITE)
			.add(TTBlocks.POLISHED_CALCITE_STAIRS)
			.add(TTBlocks.POLISHED_CALCITE_SLAB)
			.add(TTBlocks.POLISHED_CALCITE_WALL)
			.add(TTBlocks.CHISELED_CALCITE_BRICKS)
			.add(TTBlocks.CALCITE_BRICKS)
			.add(TTBlocks.CRACKED_CALCITE_BRICKS)
			.add(TTBlocks.CALCITE_BRICK_STAIRS)
			.add(TTBlocks.CALCITE_BRICK_SLAB)
			.add(TTBlocks.CALCITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_CALCITE_BRICKS)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_WALL)

			.add(TTBlocks.CRACKED_TUFF_BRICKS)
			.add(TTBlocks.MOSSY_TUFF_BRICKS)
			.add(TTBlocks.MOSSY_TUFF_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_TUFF_BRICK_SLAB)
			.add(TTBlocks.MOSSY_TUFF_BRICK_WALL)

			.add(TTBlocks.CRACKED_BRICKS)
			.add(TTBlocks.MOSSY_BRICKS)
			.add(TTBlocks.MOSSY_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_BRICK_SLAB)
			.add(TTBlocks.MOSSY_BRICK_WALL)

			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILES)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL)

			.add(TTBlocks.SMOOTH_SANDSTONE_WALL)
			.add(TTBlocks.CUT_SANDSTONE_WALL)
			.add(TTBlocks.CUT_SANDSTONE_STAIRS)

			.add(TTBlocks.SMOOTH_RED_SANDSTONE_WALL)
			.add(TTBlocks.CUT_RED_SANDSTONE_WALL)
			.add(TTBlocks.CUT_RED_SANDSTONE_STAIRS)

			.add(TTBlocks.PRISMARINE_BRICK_WALL)
			.add(TTBlocks.DARK_PRISMARINE_WALL)

			.add(TTBlocks.CHORAL_END_STONE)
			.add(TTBlocks.CHORAL_END_STONE_STAIRS)
			.add(TTBlocks.CHORAL_END_STONE_SLAB)
			.add(TTBlocks.CHORAL_END_STONE_WALL)
			.add(TTBlocks.END_STONE_STAIRS)
			.add(TTBlocks.END_STONE_SLAB)
			.add(TTBlocks.END_STONE_WALL)

			.add(TTBlocks.CRACKED_END_STONE_BRICKS)
			.add(TTBlocks.CHISELED_END_STONE_BRICKS)
			.add(TTBlocks.CHORAL_END_STONE_BRICKS)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_SLAB)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_WALL)

			.add(TTBlocks.CRACKED_PURPUR_BLOCK)
			.add(TTBlocks.CHISELED_PURPUR_BLOCK)
			.add(TTBlocks.PURPUR_WALL)

			.add(TTBlocks.SURVEYOR);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(TTBlocks.SUSPICIOUS_RED_SAND)
			.add(TTBlocks.SUSPICIOUS_DIRT)
			.add(TTBlocks.SUSPICIOUS_CLAY);

		this.getOrCreateTagBuilder(BlockTags.SAND)
			.add(TTBlocks.SUSPICIOUS_RED_SAND);

		this.getOrCreateTagBuilder(BlockTags.DIRT)
			.add(TTBlocks.SUSPICIOUS_DIRT);

		this.getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
			.add(TTBlocks.CYAN_ROSE);

		this.getOrCreateTagBuilder(BlockTags.TALL_FLOWERS)
			.add(TTBlocks.MANEDROP);

		this.getOrCreateTagBuilder(BlockTags.FLOWERS)
			.add(TTBlocks.DAWNTRAIL);

		this.getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
			.add(TTBlocks.POTTED_CYAN_ROSE);

		this.getOrCreateTagBuilder(BlockTags.CROPS)
			.add(TTBlocks.CYAN_ROSE_CROP)
			.add(TTBlocks.MANEDROP_CROP)
			.add(TTBlocks.DAWNTRAIL_CROP);

		this.getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND)
			.add(TTBlocks.CYAN_ROSE_CROP)
			.add(TTBlocks.CYAN_ROSE)
			.add(TTBlocks.MANEDROP_CROP)
			.add(TTBlocks.DAWNTRAIL_CROP)
			.add(TTBlocks.DAWNTRAIL);

		this.getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
			.add(TTBlocks.DAWNTRAIL);

		this.getOrCreateTagBuilder(BlockTags.STAIRS)
			.add(TTBlocks.GRANITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_STAIRS)
			.add(TTBlocks.DIORITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_STAIRS)
			.add(TTBlocks.ANDESITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_STAIRS)
			.add(TTBlocks.CALCITE_STAIRS)
			.add(TTBlocks.POLISHED_CALCITE_STAIRS)
			.add(TTBlocks.CALCITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_TUFF_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlocks.CUT_SANDSTONE_STAIRS)
			.add(TTBlocks.CUT_RED_SANDSTONE_STAIRS)
			.add(TTBlocks.CHORAL_END_STONE_STAIRS)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_STAIRS)
			.add(TTBlocks.END_STONE_STAIRS);

		this.getOrCreateTagBuilder(BlockTags.SLABS)
			.add(TTBlocks.GRANITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_SLAB)
			.add(TTBlocks.DIORITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_SLAB)
			.add(TTBlocks.ANDESITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_SLAB)
			.add(TTBlocks.CALCITE_SLAB)
			.add(TTBlocks.POLISHED_CALCITE_SLAB)
			.add(TTBlocks.CALCITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_TUFF_BRICK_SLAB)
			.add(TTBlocks.MOSSY_BRICK_SLAB)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlocks.CHORAL_END_STONE_SLAB)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_SLAB)
			.add(TTBlocks.END_STONE_SLAB);

		this.getOrCreateTagBuilder(BlockTags.WALLS)
			.add(TTBlocks.GRANITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_GRANITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_GRANITE_WALL)
			.add(TTBlocks.DIORITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DIORITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_DIORITE_WALL)
			.add(TTBlocks.ANDESITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_ANDESITE_BRICK_WALL)
			.add(TTBlocks.POLISHED_ANDESITE_WALL)
			.add(TTBlocks.CALCITE_WALL)
			.add(TTBlocks.POLISHED_CALCITE_WALL)
			.add(TTBlocks.CALCITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_CALCITE_BRICK_WALL)
			.add(TTBlocks.MOSSY_TUFF_BRICK_WALL)
			.add(TTBlocks.MOSSY_BRICK_WALL)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL)
			.add(TTBlocks.SMOOTH_SANDSTONE_WALL)
			.add(TTBlocks.CUT_SANDSTONE_WALL)
			.add(TTBlocks.SMOOTH_RED_SANDSTONE_WALL)
			.add(TTBlocks.CUT_RED_SANDSTONE_WALL)
			.add(TTBlocks.PRISMARINE_BRICK_WALL)
			.add(TTBlocks.DARK_PRISMARINE_WALL)
			.add(TTBlocks.CHORAL_END_STONE_WALL)
			.add(TTBlocks.CHORAL_END_STONE_BRICK_WALL)
			.add(TTBlocks.END_STONE_WALL)
			.add(TTBlocks.PURPUR_WALL);

		this.getOrCreateTagBuilder(BlockTags.ANCIENT_CITY_REPLACEABLE)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILES)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);

		this.getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILES);

		this.getOrCreateTagBuilder(TTBlockTags.CAMEL_SPAWNABLE_ON)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.GRASS_BLOCK);

		this.getOrCreateTagBuilder(TTBlockTags.COFFIN_UNSPAWNABLE_ON)
			.add(Blocks.REDSTONE_WIRE)
			.add(Blocks.REDSTONE_TORCH)
			.add(Blocks.REDSTONE_WALL_TORCH)
			.add(Blocks.REDSTONE_BLOCK)
			.add(Blocks.REDSTONE_LAMP)
			.add(Blocks.LEVER)
			.add(Blocks.REPEATER)
			.add(Blocks.COMPARATOR)
			.addOptionalTag(BlockTags.BUTTONS)
			.addOptionalTag(BlockTags.PRESSURE_PLATES);

		this.getOrCreateTagBuilder(BlockTags.FEATURES_CANNOT_REPLACE)
			.add(TTBlocks.COFFIN);

		// WILDER WILD

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_slab_replaceable_worldgen"))
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_SLAB);

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_stair_replaceable_worldgen"))
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_wall_replaceable_worldgen"))
			.add(TTBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(TTBlocks.MOSSY_DEEPSLATE_TILE_WALL);
	}

	@NotNull
	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

}
