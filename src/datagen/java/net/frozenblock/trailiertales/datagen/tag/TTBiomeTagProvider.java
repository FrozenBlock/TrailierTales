package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.frozenblock.lib.datagen.api.FrozenBiomeTagProvider;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
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
		this.getOrCreateTagBuilder(TrailierBiomeTags.HAS_BADLANDS_RUINS)
			.add(Biomes.BADLANDS)
			.add(Biomes.WOODED_BADLANDS);

		this.getOrCreateTagBuilder(TrailierBiomeTags.HAS_CATACOMBS)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(BiomeTags.IS_MOUNTAIN)
			.addOptionalTag(BiomeTags.IS_HILL)
			.addOptionalTag(BiomeTags.IS_TAIGA)
			.addOptionalTag(BiomeTags.IS_JUNGLE)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptionalTag(ConventionalBiomeTags.IS_DESERT)
			.addOptionalTag(ConventionalBiomeTags.IS_SWAMP)
			.addOptionalTag(ConventionalBiomeTags.IS_UNDERGROUND)
			.addOptionalTag(ConventionalBiomeTags.IS_SAVANNA)
			.addOptionalTag(ConventionalBiomeTags.IS_PLAINS)
			.addOptionalTag(ConventionalBiomeTags.IS_SNOWY_PLAINS);

		this.getOrCreateTagBuilder(TrailierBiomeTags.HAS_DESERT_RUINS)
			.addOptionalTag(ConventionalBiomeTags.IS_DESERT);

		this.getOrCreateTagBuilder(TrailierBiomeTags.HAS_SAVANNA_RUINS)
			.add(Biomes.SAVANNA);

		this.getOrCreateTagBuilder(TrailierBiomeTags.HAS_JUNGLE_RUINS)
			.addOptionalTag(ConventionalBiomeTags.IS_JUNGLE);

		this.getOrCreateTagBuilder(TrailierBiomeTags.HAS_RUINS)
			.addOptionalTag(ConventionalBiomeTags.IS_PLAINS)
			.addOptionalTag(ConventionalBiomeTags.IS_SNOWY_PLAINS)
			.addOptionalTag(ConventionalBiomeTags.IS_FOREST)
			.addOptionalTag(ConventionalBiomeTags.IS_BIRCH_FOREST);
	}

	@NotNull
	private TagKey<Biome> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

}
