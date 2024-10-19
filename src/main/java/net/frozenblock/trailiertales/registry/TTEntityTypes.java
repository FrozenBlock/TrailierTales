package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.TTFeatureFlags;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.ThrownItemProjectile;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public final class TTEntityTypes {
	public static final EntityType<Apparition> APPARITION = register(
		"apparition",
		EntityType.Builder.of(Apparition::new, MobCategory.MONSTER)
			.sized(0.98F, 0.98F)
			.eyeHeight(0.98F * 0.5F)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
			.build(TTConstants.string("apparition"))
	);

	public static final EntityType<ThrownItemProjectile> THROWN_ITEM_PROJECTILE = register(
		"thrown_item",
		EntityType.Builder.<ThrownItemProjectile>of(ThrownItemProjectile::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(64)
			.updateInterval(10)
			.requiredFeatures(TTFeatureFlags.FEATURE_FLAG)
			.build(TTConstants.string("thrown_item"))
	);

	static {
		FabricDefaultAttributeRegistry.register(APPARITION, Apparition.createApparitionAttributes());
	}

	public static void init() {
	}

	@NotNull
	private static <E extends Entity, T extends EntityType<E>> T register(@NotNull String path, @NotNull T entityType) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, TTConstants.id(path), entityType);
	}
}
