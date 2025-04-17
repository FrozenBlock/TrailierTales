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

package net.frozenblock.trailiertales.mixin.common.coffin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Optional;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawner;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
		method = "hurtServer",
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
		method = "hurtServer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/damagesource/DamageSource;FFZ)V"
		)
	)
	public void trailierTales$onHurtPlayer(
		EntityHurtPlayerTrigger instance, ServerPlayer player, DamageSource source, float dealt, float taken, boolean blocked, Operation<Void> original
	) {
		Entity entity = source.getEntity();
		if (entity == null) entity = source.getDirectEntity();
		if (entity instanceof EntityCoffinInterface coffinInterface && coffinInterface.trailierTales$getCoffinData() != null) {
			coffinInterface.trailierTales$getCoffinData().updateLastInteraction(entity.level().getGameTime());
		}
		original.call(instance, player, source, dealt, taken, blocked);
	}

	@ModifyReturnValue(method = "getExperienceReward", at = @At("RETURN"))
	public int trailierTales$getExperienceReward(
		int original,
		ServerLevel world, @Nullable Entity entity
	) {
		if (this.trailierTales$entityCoffinData != null) {
			if (entity instanceof Player player && player.hasEffect(TTMobEffects.SIEGE_OMEN)) {
				return original * 2;
			}
		}
		return original;
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
						serverLevel.sendParticles(TTParticleTypes.COFFIN_SOUL, pos.x, pos.y, pos.z, 4, 0.2D, 0D, 0.2D, 0D);
						serverLevel.sendParticles(ParticleTypes.POOF, pos.x, pos.y, pos.z, 2, 0.2D, 0D, 0.2D, 0D);
						double distance = livingEntity.distanceToSqr(pos);
						coffinSpawner.addSoulParticle(40 + (int)(distance * 1.25D));
					} else if (spawnerData.trackingApparition(livingEntity)) {
						coffinSpawner.onApparitionRemovedOrKilled(serverLevel);
					}
				}
			}
		}
	}

	@Inject(
		method = "baseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"
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
