package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class TrailierItemTags {

	public static final TagKey<Item> COPYABLE_SHERDS = bind("copyable_sherds");
	public static final TagKey<Item> POT_BASES = bind("pot_bases");
	public static final TagKey<Item> ANDESITE_BRICKS = bind("andesite_bricks");
	public static final TagKey<Item> DIORITE_BRICKS = bind("diorite_bricks");
	public static final TagKey<Item> GRANITE_BRICKS = bind("granite_bricks");

	@NotNull
	private static TagKey<Item> bind(@NotNull String path) {
		return TagKey.create(Registries.ITEM, TrailierTalesSharedConstants.id(path));
	}

}
