package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class TTBlockTags {
	public static final TagKey<Block> COFFIN_UNSPAWNABLE_ON = bind("coffin_unspawnable_on");
	public static final TagKey<Block> CAMEL_SPAWNABLE_ON = bind("camel_spawnable_on");

	@NotNull
	private static TagKey<Block> bind(@NotNull String path) {
		return TagKey.create(Registries.BLOCK, TTConstants.id(path));
	}

}
