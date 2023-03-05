package net.lunade.onetwenty.data;

import net.lunade.onetwenty.util.Luna120SharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Luna120ItemTags {

	public static final TagKey<Item> COMMON_SNIFFER_LOOT = bind("common_sniffer_loot");
	public static final TagKey<Item> UNCOMMON_SNIFFER_LOOT = bind("uncommon_sniffer_loot");
	public static final TagKey<Item> RARE_SNIFFER_LOOT = bind("rare_sniffer_loot");

	private static TagKey<Item> bind(String path) {
		return TagKey.create(Registries.ITEM, Luna120SharedConstants.id(path));
	}

}
