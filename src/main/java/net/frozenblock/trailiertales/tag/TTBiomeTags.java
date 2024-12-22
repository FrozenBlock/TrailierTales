package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class TTBiomeTags {
	public static final TagKey<Biome> HAS_BADLANDS_RUINS = bind("has_structure/badlands_ruins");
	public static final TagKey<Biome> HAS_CATACOMBS = bind("has_structure/catacombs");
	public static final TagKey<Biome> HAS_DESERT_RUINS = bind("has_structure/desert_ruins");
	public static final TagKey<Biome> HAS_JUNGLE_RUINS = bind("has_structure/jungle_ruins");
	public static final TagKey<Biome> HAS_SAVANNA_RUINS = bind("has_structure/savanna_ruins");
	public static final TagKey<Biome> HAS_DEEPSLATE_RUINS = bind("has_structure/deepslate_ruins");
	public static final TagKey<Biome> HAS_RUINS = bind("has_structure/ruins");
	public static final TagKey<Biome> HAS_SMALL_TRAIL_RUINS = bind("has_structure/small_trail_ruins");
	public static final TagKey<Biome> HAS_SNOWY_RUINS = bind("has_structure/snowy_ruins");

	@NotNull
	private static TagKey<Biome> bind(@NotNull String path) {
		return TagKey.create(Registries.BIOME, TTConstants.id(path));
	}

}
