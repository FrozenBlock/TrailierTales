package net.frozenblock.trailiertales.entity;

import net.frozenblock.trailiertales.registry.RegisterEntities;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class DamagingThrowableItemProjectile extends ThrowableItemProjectile {

	public DamagingThrowableItemProjectile(@NotNull EntityType<? extends DamagingThrowableItemProjectile> entityType, @NotNull Level level) {
		super(entityType, level);
	}

	public DamagingThrowableItemProjectile(@NotNull Level level, @NotNull LivingEntity shooter) {
		super(RegisterEntities.DAMAGING_THROWABLE_ITEM_PROJECTILE, shooter, level);
	}

	public DamagingThrowableItemProjectile(@NotNull Level level, double x, double y, double z) {
		super(RegisterEntities.DAMAGING_THROWABLE_ITEM_PROJECTILE, x, y, z, level);
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
			ItemStack apparitionStack = apparition.getInventory().getItems().getFirst();
			if (!apparitionStack.isEmpty()) {
				this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), apparitionStack.copyAndClear()));
			}
			apparition.setVisibleItem(this.getItem());
			this.discard();
		} else {
			this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
		}
		this.spawnParticles();
		entity.hurt(entity.damageSources().thrown(this, this.getOwner()), 2F);
		this.discard();
	}

	@Override
	protected void onHitBlock(@NotNull BlockHitResult result) {
		super.onHitBlock(result);
		this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
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
