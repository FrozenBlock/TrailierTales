package net.lunade.onetwenty.data;

import net.lunade.onetwenty.util.Luna120SharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class Luna120ItemTags {

	public static final TagKey<Item> COPYABLE_SHERDS = bind("copyable_sherds");
	public static final TagKey<Item> POT_BASES = bind("pot_bases");

	@NotNull
	private static TagKey<Item> bind(@NotNull String path) {
		return TagKey.create(Registries.ITEM, Luna120SharedConstants.id(path));
	}

}
