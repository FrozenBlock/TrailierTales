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

package net.frozenblock.trailiertales.mixin.common.boat;

import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractBoat.class, priority = 100)
public abstract class AbstractBoatMixin extends VehicleEntity implements BoatBannerInterface {
	@Unique
	private final WalkAnimationState trailierTales$walkAnimation = new WalkAnimationState();

	public AbstractBoatMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Unique
	@Override
	public WalkAnimationState trailierTales$getWalkAnimationState() {
		return this.trailierTales$walkAnimation;
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void trailierTales$tick(CallbackInfo info) {
		this.trailierTales$calculateEntityAnimation(true);
	}

	@Unique
	public void trailierTales$calculateEntityAnimation(boolean flutter) {
		final float walkDifference = (float) Mth.length(this.getX() - this.xo, flutter ? this.getY() - this.yo : 0F, this.getZ() - this.zo);
		this.trailierTales$updateWalkAnimation(walkDifference);
	}

	@Unique
	protected void trailierTales$updateWalkAnimation(float limbDistance) {
		final float speed = Math.min(limbDistance, 1F);
		this.trailierTales$walkAnimation.update(speed, 0.1F, 1F);
	}

	@Inject(
		method = "interact",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/vehicle/VehicleEntity;interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/InteractionResult;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void trailierTales$interact(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> info) {
		this.trailierTales$interactWithBanner(player, hand, location, info);
	}
}
