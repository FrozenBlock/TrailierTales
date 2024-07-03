package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.entity.Ghost;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public final class RegisterEntities {
	private RegisterEntities() {}

	public static final EntityType<Ghost> GHOST = register(
		"ghost",
		FabricEntityType.Builder.createMob(Ghost::new, MobCategory.MONSTER, mob -> mob.defaultAttributes(Ghost::createGhostAttributes))
			.build(TrailierConstants.string("ghost")) // id is for datafixers
	);

	public static void init() {}

	@NotNull
	private static <E extends Entity, T extends EntityType<E>> T register(@NotNull String path, @NotNull T entityType) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, TrailierConstants.id(path), entityType);
	}
}
