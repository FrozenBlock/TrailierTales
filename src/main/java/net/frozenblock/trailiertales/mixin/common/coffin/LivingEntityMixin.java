/*
 * Copyright 2025-2026 FrozenBlock
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
import net.frozenblock.trailiertales.registry.TTAttachmentTypes;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.minecraft.advancements.triggers.EntityHurtPlayerTrigger;
import net.minecraft.advancements.triggers.PlayerHurtEntityTrigger;
import net.minecraft.core.particles.ParticleTypes;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow
	protected int lastHurtByPlayerMemoryTime;

	@WrapOperation(
		method = "hurtServer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/advancements/triggers/PlayerHurtEntityTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;FFZ)V"
		)
	)
	public void trailierTales$onHurtByPlayer(
		PlayerHurtEntityTrigger instance, ServerPlayer player, Entity victim, DamageSource source, float originalDamage, float actualDamage, boolean blocked, Operation<Void> original
	) {
		final LivingEntity livingEntity = LivingEntity.class.cast(this);
		final EntityCoffinData coffinData = livingEntity.getAttached(TTAttachmentTypes.ENTITY_COFFIN_DATA);
		if (coffinData != null) coffinData.updateLastInteraction(livingEntity.level().getGameTime());

		original.call(instance, player, victim, source, originalDamage, actualDamage, blocked);
	}

	@WrapOperation(
		method = "hurtServer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/advancements/triggers/EntityHurtPlayerTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/damagesource/DamageSource;FFZ)V"
		)
	)
	public void trailierTales$onHurtPlayer(
		EntityHurtPlayerTrigger instance, ServerPlayer player, DamageSource source, float originalDamage, float actualDamage, boolean blocked, Operation<Void> original
	) {
		Entity entity = source.getEntity();
		if (entity == null) entity = source.getDirectEntity();

		if (entity != null) {
			final EntityCoffinData coffinData = entity.getAttached(TTAttachmentTypes.ENTITY_COFFIN_DATA);
			if (coffinData != null) coffinData.updateLastInteraction(entity.level().getGameTime());
		}

		original.call(instance, player, source, originalDamage, actualDamage, blocked);
	}

	@ModifyReturnValue(method = "getExperienceReward", at = @At("RETURN"))
	public int trailierTales$getExperienceReward(
		int original,
		ServerLevel level, @Nullable Entity killer
	) {
		final EntityCoffinData coffinData = LivingEntity.class.cast(this).getAttached(TTAttachmentTypes.ENTITY_COFFIN_DATA);
		if (coffinData != null && killer instanceof Player player && player.hasEffect(TTMobEffects.SIEGE_OMEN)) return original * 2;

		return original;
	}

	@Inject(method = "remove", at = @At("HEAD"))
	public void trailierTales$remove(Entity.RemovalReason reason, CallbackInfo info) {
		if (reason != Entity.RemovalReason.KILLED || this.lastHurtByPlayerMemoryTime <= 0) return;

		final LivingEntity livingEntity = LivingEntity.class.cast(this);
		final EntityCoffinData coffinData = livingEntity.getAttached(TTAttachmentTypes.ENTITY_COFFIN_DATA);
		if (!(livingEntity.level() instanceof ServerLevel serverLevel) || coffinData == null) return;

		final Optional<CoffinSpawner> optionalCoffinSpawner = coffinData.getSpawner(serverLevel);
		if (optionalCoffinSpawner.isEmpty()) return;

		final CoffinSpawner coffinSpawner = optionalCoffinSpawner.get();
		final CoffinSpawnerData spawnerData = coffinSpawner.getData();
		if (spawnerData.trackingEntity(livingEntity)) {
			final Vec3 pos = livingEntity.getEyePosition();
			final Vec3 coffinPos = Vec3.atCenterOf(coffinData.getCoffinPosition());
			serverLevel.sendParticles(TTParticleTypes.COFFIN_SOUL, pos.x, pos.y, pos.z, 4, 0.2D, 0D, 0.2D, 0D);
			serverLevel.sendParticles(ParticleTypes.POOF, pos.x, pos.y, pos.z, 2, 0.2D, 0D, 0.2D, 0D);
			final double distance = livingEntity.distanceToSqr(coffinPos);
			coffinSpawner.addSoulParticle(60 + (int)(distance * 1.25D));
		} else if (spawnerData.trackingApparition(livingEntity)) {
			coffinSpawner.onApparitionRemovedOrKilled(serverLevel);
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
		final LivingEntity livingEntity = LivingEntity.class.cast(this);
		final EntityCoffinData coffinData = livingEntity.getAttached(TTAttachmentTypes.ENTITY_COFFIN_DATA);
		if (coffinData == null) return;

		coffinData.tick(livingEntity, livingEntity.level());
	}
}
