/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.entity;

import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class ThrownItemProjectile extends ThrowableItemProjectile {

	public ThrownItemProjectile(@NotNull EntityType<? extends ThrownItemProjectile> entityType, @NotNull Level level) {
		super(entityType, level);
	}

	public ThrownItemProjectile(@NotNull Level level, @NotNull LivingEntity shooter) {
		super(TTEntityTypes.THROWN_ITEM_PROJECTILE, shooter, level);
	}

	public ThrownItemProjectile(@NotNull Level level, double x, double y, double z) {
		super(TTEntityTypes.THROWN_ITEM_PROJECTILE, x, y, z, level);
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
		final Entity entity = result.getEntity();
		this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem().copy()));
		entity.hurt(entity.damageSources().thrown(this, this.getOwner()), 2F);
		if (!(entity instanceof Apparition)) this.spawnParticles();
		this.discard();
	}

	@Override
	protected void onHitBlock(@NotNull BlockHitResult result) {
		super.onHitBlock(result);
		this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem().copy()));
		this.discard();
	}

	public void spawnParticles() {
		if (this.level() instanceof ServerLevel server) {
			EntityDimensions dimensions = this.getDimensions(Pose.STANDING);
			server.sendParticles(
				new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
				this.position().x + (dimensions.width() * 0.5),
				this.position().y + (dimensions.height() * 0.5),
				this.position().z + (dimensions.width() * 0.5),
				this.random.nextInt(5, 10),
				dimensions.width() / 4F,
				dimensions.height() / 4F,
				dimensions.width() / 4F,
				0.1D
			);
			this.discard();
		}
	}

}
