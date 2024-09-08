package net.frozenblock.trailiertales.tag;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class TTEntityTags {
	public static final TagKey<EntityType<?>> SURVEYOR_IGNORES = bind("surveyor_ignores") ;
	public static final TagKey<EntityType<?>> APPARITION_TARGETABLE = bind("apparition_targetable");

	@NotNull
	private static TagKey<EntityType<?>> bind(@NotNull String path) {
		return TagKey.create(Registries.ENTITY_TYPE, TTConstants.id(path));
	}

}
