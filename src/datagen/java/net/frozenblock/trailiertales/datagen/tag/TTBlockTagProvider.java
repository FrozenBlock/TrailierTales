package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.tag.TrailierBlockTags;
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
			.add(RegisterBlocks.CHISELED_GRANITE_BRICKS)
			.add(RegisterBlocks.GRANITE_BRICKS)
			.add(RegisterBlocks.CRACKED_GRANITE_BRICKS)
			.add(RegisterBlocks.GRANITE_BRICK_STAIRS)
			.add(RegisterBlocks.GRANITE_BRICK_SLAB)
			.add(RegisterBlocks.GRANITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_GRANITE_BRICKS)
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_WALL)

			.add(RegisterBlocks.CHISELED_DIORITE_BRICKS)
			.add(RegisterBlocks.DIORITE_BRICKS)
			.add(RegisterBlocks.CRACKED_DIORITE_BRICKS)
			.add(RegisterBlocks.DIORITE_BRICK_STAIRS)
			.add(RegisterBlocks.DIORITE_BRICK_SLAB)
			.add(RegisterBlocks.DIORITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_DIORITE_BRICKS)
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_WALL)

			.add(RegisterBlocks.CHISELED_ANDESITE_BRICKS)
			.add(RegisterBlocks.ANDESITE_BRICKS)
			.add(RegisterBlocks.CRACKED_ANDESITE_BRICKS)
			.add(RegisterBlocks.ANDESITE_BRICK_STAIRS)
			.add(RegisterBlocks.ANDESITE_BRICK_SLAB)
			.add(RegisterBlocks.ANDESITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICKS)
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_WALL)

			.add(RegisterBlocks.CALCITE_STAIRS)
			.add(RegisterBlocks.CALCITE_SLAB)
			.add(RegisterBlocks.CALCITE_WALL)
			.add(RegisterBlocks.POLISHED_CALCITE)
			.add(RegisterBlocks.POLISHED_CALCITE_STAIRS)
			.add(RegisterBlocks.POLISHED_CALCITE_SLAB)
			.add(RegisterBlocks.POLISHED_CALCITE_WALL)
			.add(RegisterBlocks.CHISELED_CALCITE_BRICKS)
			.add(RegisterBlocks.CALCITE_BRICKS)
			.add(RegisterBlocks.CRACKED_CALCITE_BRICKS)
			.add(RegisterBlocks.CALCITE_BRICK_STAIRS)
			.add(RegisterBlocks.CALCITE_BRICK_SLAB)
			.add(RegisterBlocks.CALCITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_CALCITE_BRICKS)
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_WALL)

			.add(RegisterBlocks.CRACKED_TUFF_BRICKS)
			.add(RegisterBlocks.MOSSY_TUFF_BRICKS)
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_WALL)

			.add(RegisterBlocks.CRACKED_BRICKS)
			.add(RegisterBlocks.MOSSY_BRICKS)
			.add(RegisterBlocks.MOSSY_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_BRICK_WALL)

			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILES)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL)

			.add(RegisterBlocks.CRACKED_END_STONE_BRICKS)
			.add(RegisterBlocks.CHISELED_END_STONE_BRICKS)
			.add(RegisterBlocks.CHORAL_END_STONE_BRICKS)
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_STAIRS)
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_SLAB)
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_WALL)

			.add(RegisterBlocks.PURPUR_WALL)

			.add(RegisterBlocks.SURVEYOR);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(RegisterBlocks.SUSPICIOUS_RED_SAND)
			.add(RegisterBlocks.SUSPICIOUS_DIRT)
			.add(RegisterBlocks.SUSPICIOUS_CLAY);

		this.getOrCreateTagBuilder(BlockTags.SAND)
			.add(RegisterBlocks.SUSPICIOUS_RED_SAND);

		this.getOrCreateTagBuilder(BlockTags.DIRT)
			.add(RegisterBlocks.SUSPICIOUS_DIRT);

		this.getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
			.add(RegisterBlocks.CYAN_ROSE);

		this.getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
			.add(RegisterBlocks.POTTED_CYAN_ROSE);

		this.getOrCreateTagBuilder(BlockTags.CROPS)
			.add(RegisterBlocks.CYAN_ROSE_CROP);

		this.getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND)
			.add(RegisterBlocks.CYAN_ROSE_CROP)
			.add(RegisterBlocks.CYAN_ROSE);

		this.getOrCreateTagBuilder(BlockTags.STAIRS)
			.add(RegisterBlocks.GRANITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_STAIRS)
			.add(RegisterBlocks.DIORITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_STAIRS)
			.add(RegisterBlocks.ANDESITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_STAIRS)
			.add(RegisterBlocks.CALCITE_STAIRS)
			.add(RegisterBlocks.POLISHED_CALCITE_STAIRS)
			.add(RegisterBlocks.CALCITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_STAIRS);

		this.getOrCreateTagBuilder(BlockTags.SLABS)
			.add(RegisterBlocks.GRANITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_SLAB)
			.add(RegisterBlocks.DIORITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_SLAB)
			.add(RegisterBlocks.ANDESITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_SLAB)
			.add(RegisterBlocks.CALCITE_SLAB)
			.add(RegisterBlocks.POLISHED_CALCITE_SLAB)
			.add(RegisterBlocks.CALCITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_SLAB);

		this.getOrCreateTagBuilder(BlockTags.WALLS)
			.add(RegisterBlocks.GRANITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_GRANITE_BRICK_WALL)
			.add(RegisterBlocks.DIORITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_DIORITE_BRICK_WALL)
			.add(RegisterBlocks.ANDESITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_ANDESITE_BRICK_WALL)
			.add(RegisterBlocks.CALCITE_WALL)
			.add(RegisterBlocks.POLISHED_CALCITE_WALL)
			.add(RegisterBlocks.CALCITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_CALCITE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_TUFF_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL)
			.add(RegisterBlocks.CHORAL_END_STONE_BRICK_WALL)
			.add(RegisterBlocks.PURPUR_WALL);

		this.getOrCreateTagBuilder(BlockTags.ANCIENT_CITY_REPLACEABLE)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILES)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL);

		this.getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICKS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILES);

		this.getOrCreateTagBuilder(TrailierBlockTags.COFFIN_UNSPAWNABLE_ON)
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
			.add(RegisterBlocks.COFFIN);

		// WILDER WILD

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_slab_replaceable_worldgen"))
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_SLAB);

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_stair_replaceable_worldgen"))
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_STAIRS);

		this.getOrCreateTagBuilder(getTag("wilderwild:sculk_wall_replaceable_worldgen"))
			.add(RegisterBlocks.MOSSY_COBBLED_DEEPSLATE_WALL)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_BRICK_WALL)
			.add(RegisterBlocks.MOSSY_DEEPSLATE_TILE_WALL);
	}

	@NotNull
	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

}
