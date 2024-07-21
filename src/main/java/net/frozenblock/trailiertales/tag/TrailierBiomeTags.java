package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class TrailierBiomeTags {

	public static final TagKey<Biome> HAS_BADLANDS_RUINS = bind("has_badlands_ruins");
	public static final TagKey<Biome> HAS_CATACOMBS = bind("has_catacombs");
	public static final TagKey<Biome> HAS_DESERT_RUINS = bind("has_desert_ruins");
	public static final TagKey<Biome> HAS_JUNGLE_RUINS = bind("has_jungle_ruins");
	public static final TagKey<Biome> HAS_SAVANNA_RUINS = bind("has_savanna_ruins");
	public static final TagKey<Biome> HAS_RUINS = bind("has_ruins");

	@NotNull
	private static TagKey<Biome> bind(@NotNull String path) {
		return TagKey.create(Registries.BIOME, TrailierConstants.id(path));
	}

}
