package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class TrailierEntityTags {
	public static final TagKey<EntityType<?>> APPARITION_TARGETABLE = bind("apparition_targetable") ;

	@NotNull
	private static TagKey<EntityType<?>> bind(@NotNull String path) {
		return TagKey.create(Registries.ENTITY_TYPE, TrailierConstants.id(path));
	}

}
