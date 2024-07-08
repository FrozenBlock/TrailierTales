package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class TrailierBiomeTags {

	public static final TagKey<Biome> HAS_BADLANDS_FORT = bind("has_badlands_fort");

	@NotNull
	private static TagKey<Biome> bind(@NotNull String path) {
		return TagKey.create(Registries.BIOME, TrailierConstants.id(path));
	}

}
