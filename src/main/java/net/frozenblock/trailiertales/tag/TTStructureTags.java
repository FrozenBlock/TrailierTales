package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.NotNull;

public class TTStructureTags {
	public static final TagKey<Structure> ON_CATACOMBS_EXPLORER_MAPS = bind("on_catacombs_explorer_maps");

	@NotNull
	private static TagKey<Structure> bind(@NotNull String path) {
		return TagKey.create(Registries.STRUCTURE, TTConstants.id(path));
	}

}
