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

import java.util.function.Supplier;
import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractChestBoat.class, priority = 100)
public abstract class AbstractChestBoatMixin extends AbstractBoat implements BoatBannerInterface {

	public AbstractChestBoatMixin(EntityType<? extends AbstractBoat> type, Level level, Supplier<Item> supplier) {
		super(type, level, supplier);
	}

	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public void trailierTales$interact(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> info) {
		this.trailierTales$interactWithBanner(player, hand, location, info);
	}
}
