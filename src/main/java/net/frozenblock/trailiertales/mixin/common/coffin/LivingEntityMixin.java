package net.frozenblock.trailiertales.mixin.common.coffin;

import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.registry.RegisterParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements EntityCoffinInterface {

	@Shadow
	protected int lastHurtByPlayerTime;

	@Unique@Nullable
	private EntityCoffinData trailierTales$entityCoffinData = null;

	@Unique
	@Override
	@Nullable
	public EntityCoffinData trailierTales$getCoffinData() {
		return this.trailierTales$entityCoffinData;
	}

	@Unique
	@Override
	public void trailierTales$setCoffinData(EntityCoffinData entityCoffinData) {
		this.trailierTales$entityCoffinData = entityCoffinData;
	}

	@Inject(method = "remove", at = @At("HEAD"))
	public void trailierTales$remove(Entity.RemovalReason reason, CallbackInfo info) {
		if (reason == Entity.RemovalReason.KILLED && this.lastHurtByPlayerTime > 0) {
			LivingEntity livingEntity = LivingEntity.class.cast(this);
			if (livingEntity.level() instanceof ServerLevel serverLevel && this.trailierTales$entityCoffinData != null) {
				EntityCoffinData entityCoffinData = this.trailierTales$entityCoffinData;
				if (serverLevel.isLoaded(entityCoffinData.getPos())) {
					if (serverLevel.getBlockEntity(entityCoffinData.getPos()) instanceof CoffinBlockEntity coffinBlockEntity) {
						if (coffinBlockEntity.getCoffinSpawner().getUUID().equals(entityCoffinData.getCoffinUUID())) {
							CoffinSpawnerData spawnerData = coffinBlockEntity.getCoffinSpawner().getData();
							if (spawnerData.trackingEntity(livingEntity)) {
								Vec3 pos = livingEntity.getEyePosition();
								serverLevel.sendParticles(RegisterParticles.COFFIN_SOUL, pos.x, pos.y, pos.z, 4, 0.2D, 0D, 0.2D, 0D);
								serverLevel.sendParticles(ParticleTypes.POOF, pos.x, pos.y, pos.z, 2, 0.2D, 0D, 0.2D, 0D);
								double distance = livingEntity.distanceToSqr(pos);
								coffinBlockEntity.getCoffinSpawner().addSoulParticle((long) (40D + (distance * 1.25D)), serverLevel);
							}
						}
					}
				}
			}
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$addAdditionalSaveData(CompoundTag tag, CallbackInfo info) {
		if (this.trailierTales$entityCoffinData != null) {
			this.trailierTales$entityCoffinData.saveCompoundTag(tag);
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$readAdditionalSaveData(CompoundTag tag, CallbackInfo info) {
		EntityCoffinData coffinData = EntityCoffinData.loadCompoundTag(tag);
		if (coffinData != null) {
			this.trailierTales$entityCoffinData = coffinData;
		}
	}
}
