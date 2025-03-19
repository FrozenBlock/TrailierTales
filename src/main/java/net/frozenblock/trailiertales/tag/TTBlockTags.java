package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class TTBlockTags {
	public static final TagKey<Block> SOUND_UNPOLISHED_BRICKS = bind("sound/unpolished_bricks");
	public static final TagKey<Block> SOUND_POLISHED_BRICKS = bind("sound/polished_bricks");
	public static final TagKey<Block> SOUND_POLISHED_CALCITE = bind("sound/polished_calcite");
	public static final TagKey<Block> SOUND_CALCITE_BRICKS = bind("sound/calcite_bricks");
	public static final TagKey<Block> SOUND_POLISHED = bind("sound/polished");
	public static final TagKey<Block> SOUND_POLISHED_DEEPSLATE = bind("sound/polished_deepslate");
	public static final TagKey<Block> SOUND_POLISHED_TUFF = bind("sound/polished_tuff");
	public static final TagKey<Block> SOUND_POLISHED_BASALT = bind("sound/polished_basalt");

	public static final TagKey<Block> COFFIN_UNSPAWNABLE_ON = bind("coffin_unspawnable_on");
	public static final TagKey<Block> CAMEL_SPAWNABLE_ON = bind("camel_spawnable_on");

	public static final TagKey<Block> SURVEYOR_CAN_SEE_THROUGH = bind("surveyor_can_see_through");
	public static final TagKey<Block> SURVEYOR_CANNOT_SEE_THROUGH = bind("surveyor_cannot_see_through");

	@NotNull
	private static TagKey<Block> bind(@NotNull String path) {
		return TagKey.create(Registries.BLOCK, TTConstants.id(path));
	}

}
