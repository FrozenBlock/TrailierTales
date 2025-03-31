/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.mixin.common.boat;

import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChestBoat.class, priority = 100)
public abstract class ChestBoatMixin extends Boat {

	public ChestBoatMixin(EntityType<? extends Boat> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public void trailierTales$interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
		if (player.isSecondaryUseActive() && ChestBoat.class.cast(this) instanceof BoatBannerInterface bannerInterface) {
			if (bannerInterface.trailierTales$getBanner().isEmpty()) {
				ItemStack itemStack = player.getItemInHand(hand);
				if (itemStack.is(ItemTags.BANNERS)) {
					if (!this.level().isClientSide()) {
						this.spawnAtLocation(bannerInterface.trailierTales$getBanner(), 0.6F);
						bannerInterface.trailierTales$setBanner(itemStack.split(1));
						this.gameEvent(GameEvent.ENTITY_INTERACT, player);
					}
					info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));
				}
			} else {
				this.spawnAtLocation(bannerInterface.trailierTales$getBanner(), 0.6F);
				bannerInterface.trailierTales$setBanner(ItemStack.EMPTY);
				this.gameEvent(GameEvent.ENTITY_INTERACT, player);
				info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));
			}
		}
	}
}
