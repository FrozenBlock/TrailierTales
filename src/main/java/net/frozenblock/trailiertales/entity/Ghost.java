package net.frozenblock.trailiertales.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

// a fucking ghost
public class Ghost extends PathfinderMob {

	public Ghost(EntityType<? extends PathfinderMob> entityType, Level world) {
		super(entityType, world);
	}

	@NotNull
	public static AttributeSupplier.Builder createGhostAttributes() {
		return Mob.createMobAttributes();
	}
}
