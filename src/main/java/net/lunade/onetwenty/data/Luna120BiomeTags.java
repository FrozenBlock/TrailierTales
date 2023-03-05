package net.lunade.onetwenty.data;

import net.lunade.onetwenty.util.Luna120SharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class Luna120BiomeTags {

	public static final TagKey<Biome> HAS_TORCHFLOWER = bind("has_torchflower");

	public static final TagKey<Biome> HAS_SNIFFER = bind("has_sniffer");

	private static TagKey<Biome> bind(String path) {
		return TagKey.create(Registries.BIOME, Luna120SharedConstants.id(path));
	}

}
