package net.frozenblock.trailiertales.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

// a fucking ghost
public class Ghost extends Monster {

	public Ghost(EntityType<? extends Ghost> entityType, Level world) {
		super(entityType, world);
	}

	@NotNull
	public static AttributeSupplier.Builder createGhostAttributes() {
		return Mob.createMobAttributes();
	}
}
