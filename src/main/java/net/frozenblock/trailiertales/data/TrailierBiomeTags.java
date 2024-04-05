package net.frozenblock.trailiertales.data;

import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class TrailierBiomeTags {

	@NotNull
	private static TagKey<Biome> bind(@NotNull String path) {
		return TagKey.create(Registries.BIOME, TrailierTalesSharedConstants.id(path));
	}

}
