package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class TrailierBlockTags {
	public static final TagKey<Block> COFFIN_UNSPAWNABLE_ON = bind("coffin_unspawnable_on");

	@NotNull
	private static TagKey<Block> bind(@NotNull String path) {
		return TagKey.create(Registries.BLOCK, TrailierConstants.id(path));
	}

}
