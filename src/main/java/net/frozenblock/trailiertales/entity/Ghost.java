package net.frozenblock.trailiertales.entity;

import com.mojang.serialization.Dynamic;
import net.frozenblock.trailiertales.entity.ai.GhostAi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
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

	@Override
	@NotNull
	protected Brain.Provider<Ghost> brainProvider() {
		return Brain.provider(GhostAi.MEMORY_TYPES, GhostAi.SENSOR_TYPES);
	}

	@Override
	@NotNull
	protected Brain<Ghost> makeBrain(Dynamic<?> dynamic) {
		return GhostAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@SuppressWarnings("unchecked")
	@Override
	@NotNull
	public Brain<Ghost> getBrain() {
		return (Brain<Ghost>) super.getBrain();
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("ghostBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("ghostActivityUpdate");
		GhostAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
	}
}
