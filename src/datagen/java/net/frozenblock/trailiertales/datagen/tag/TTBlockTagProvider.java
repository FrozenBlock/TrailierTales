package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public final class TTBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public TTBlockTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(RegisterBlocks.SUSPICIOUS_RED_SAND)
			.add(RegisterBlocks.SUSPICIOUS_DIRT)
			.add(RegisterBlocks.SUSPICIOUS_CLAY);

		this.getOrCreateTagBuilder(BlockTags.SAND)
			.add(RegisterBlocks.SUSPICIOUS_RED_SAND);

		this.getOrCreateTagBuilder(BlockTags.DIRT)
			.add(RegisterBlocks.SUSPICIOUS_DIRT);
	}

	@NotNull
	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, new ResourceLocation(id));
	}

}
