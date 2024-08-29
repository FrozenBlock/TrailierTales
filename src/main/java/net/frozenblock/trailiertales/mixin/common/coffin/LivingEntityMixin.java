package net.frozenblock.trailiertales.mixin.common.coffin;

import java.util.Optional;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawner;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.registry.RegisterParticles;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements EntityCoffinInterface {

	@Shadow
	protected int lastHurtByPlayerTime;

	@Unique
	@Nullable
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

	@WrapOperation(
		method = "hurt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/advancements/critereon/PlayerHurtEntityTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;FFZ)V"
		)
	)
	public void trailierTales$onHurtByPlayer(
		PlayerHurtEntityTrigger instance, ServerPlayer player, Entity entity, DamageSource damage, float dealt, float taken, boolean blocked, Operation<Void> original
	) {
		if (this.trailierTales$entityCoffinData != null) {
			this.trailierTales$entityCoffinData.updateLastInteraction(entity.level().getGameTime());
		}
		original.call(instance, player, entity, damage, dealt, taken, blocked);
	}

	@WrapOperation(
		method = "hurt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/damagesource/DamageSource;FFZ)V"
		)
	)
	public void trailierTales$onHurtPlayer(
		EntityHurtPlayerTrigger instance, ServerPlayer player, DamageSource source, float dealt, float taken, boolean blocked, Operation<Void> original
	) {
		if (this.trailierTales$entityCoffinData != null) {
			this.trailierTales$entityCoffinData.updateLastInteraction(LivingEntity.class.cast(this).level().getGameTime());
		}
		original.call(instance, player, source, dealt, taken, blocked);
	}

	@Inject(method = "remove", at = @At("HEAD"))
	public void trailierTales$remove(Entity.RemovalReason reason, CallbackInfo info) {
		if (reason == Entity.RemovalReason.KILLED && this.lastHurtByPlayerTime > 0) {
			LivingEntity livingEntity = LivingEntity.class.cast(this);
			if (livingEntity.level() instanceof ServerLevel serverLevel && this.trailierTales$entityCoffinData != null) {
				Optional<CoffinSpawner> optionalCoffinSpawner = this.trailierTales$entityCoffinData.getSpawner(serverLevel);
				if (optionalCoffinSpawner.isPresent()) {
					CoffinSpawner coffinSpawner = optionalCoffinSpawner.get();
					CoffinSpawnerData spawnerData = coffinSpawner.getData();
					if (spawnerData.trackingEntity(livingEntity)) {
						Vec3 pos = livingEntity.getEyePosition();
						serverLevel.sendParticles(RegisterParticles.COFFIN_SOUL, pos.x, pos.y, pos.z, 4, 0.2D, 0D, 0.2D, 0D);
						serverLevel.sendParticles(ParticleTypes.POOF, pos.x, pos.y, pos.z, 2, 0.2D, 0D, 0.2D, 0D);
						double distance = livingEntity.distanceToSqr(pos);
						coffinSpawner.addSoulParticle(40 + (int)(distance * 1.25D));
					}
				}
			}
		}
	}

	@Inject(
		method = "baseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;",
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/LivingEntity;tickEffects()V"
			)
		)
	)
	public void trailierTales$baseTick(CallbackInfo info) {
		if (this.trailierTales$entityCoffinData != null) {
			LivingEntity livingEntity = LivingEntity.class.cast(this);
			this.trailierTales$entityCoffinData.tick(livingEntity, livingEntity.level());
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
