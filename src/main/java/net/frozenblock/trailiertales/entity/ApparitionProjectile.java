package net.frozenblock.trailiertales.entity;

import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class ApparitionProjectile extends ThrowableItemProjectile {

	public ApparitionProjectile(@NotNull EntityType<? extends ApparitionProjectile> entityType, @NotNull Level level) {
		super(entityType, level);
	}

	public ApparitionProjectile(@NotNull Level level, @NotNull LivingEntity shooter) {
		super(RegisterEntities.APPARITION_PROJECTILE, shooter, level);
	}

	public ApparitionProjectile(@NotNull Level level, double x, double y, double z) {
		super(RegisterEntities.APPARITION_PROJECTILE, x, y, z, level);
	}

	@Override
	@NotNull
	protected Item getDefaultItem() {
		return Items.COBBLESTONE;
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			ParticleOptions particleOptions = new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
			for (int i = 0; i < 8; ++i) {
				this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), 0D, 0D, 0D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	protected void onHitEntity(@NotNull EntityHitResult result) {
		super.onHitEntity(result);
		Entity entity = result.getEntity();
		if (entity instanceof Apparition apparition) {
			ItemStack apparitionStack = apparition.getVisibleItem();
			if (!apparitionStack.isEmpty()) {
				this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), apparitionStack.split(apparitionStack.getCount())));
			}
			apparition.setVisibleItem(this.getItem());
			this.discard();
		} else {
			this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
		}
		entity.hurt(entity.damageSources().thrown(this, this.getOwner()), 2F);
		this.discard();
	}

	@Override
	protected void onHitBlock(@NotNull BlockHitResult result) {
		super.onHitBlock(result);
		this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
		this.discard();
	}

}
