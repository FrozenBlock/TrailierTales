package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.DamagingThrowableItemProjectile;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public final class RegisterEntities {
	private RegisterEntities() {}

	public static final EntityType<Apparition> APPARITION = register(
		"apparition",
		FabricEntityType.Builder.createMob(Apparition::new, MobCategory.MONSTER, mob -> mob.defaultAttributes(Apparition::createApparitionAttributes))
			.sized(0.98F, 0.98F)
			.eyeHeight(0.98F * 0.5F)
			.build(TrailierConstants.string("apparition")) // id is for datafixers
	);

	public static final EntityType<DamagingThrowableItemProjectile> DAMAGING_THROWABLE_ITEM_PROJECTILE = register(
		"damaging_throwable_item_projectile",
		EntityType.Builder.<DamagingThrowableItemProjectile>of(DamagingThrowableItemProjectile::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(64)
			.updateInterval(10)
			.build(TrailierConstants.string("damaging_throwable_item_projectile"))
	);

	public static void init() {}

	@NotNull
	private static <E extends Entity, T extends EntityType<E>> T register(@NotNull String path, @NotNull T entityType) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, TrailierConstants.id(path), entityType);
	}
}
