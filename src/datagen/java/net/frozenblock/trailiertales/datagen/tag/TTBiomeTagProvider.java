package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.frozenblock.lib.datagen.api.FrozenBiomeTagProvider;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public final class TTBiomeTagProvider extends FrozenBiomeTagProvider {

	public TTBiomeTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(TrailierBiomeTags.HAS_BADLANDS_FORT)
			.add(Biomes.BADLANDS);
	}

	@NotNull
	private TagKey<Biome> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

}
