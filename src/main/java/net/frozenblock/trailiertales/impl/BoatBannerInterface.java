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

package net.frozenblock.trailiertales.impl;

import net.frozenblock.trailiertales.registry.TTAttachmentTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface BoatBannerInterface {
	WalkAnimationState trailierTales$getWalkAnimationState();

	default void trailierTales$interactWithBanner(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> info) {
		if (!player.isSecondaryUseActive()) return;

		final Entity entity = Entity.class.cast(this);
		final ItemStack bannerItem = entity.getAttached(TTAttachmentTypes.BOAT_BANNER);
		if (bannerItem == null || bannerItem.isEmpty()) {
			final ItemStack stack = player.getItemInHand(hand);
			if (!stack.is(ItemTags.BANNERS)) return;
			if (!entity.level().isClientSide()) {
				entity.setAttached(TTAttachmentTypes.BOAT_BANNER, stack.split(1));
				entity.gameEvent(GameEvent.ENTITY_INTERACT, player);
			}
			info.setReturnValue(InteractionResult.SUCCESS);
			return;
		}

		if (entity.level() instanceof ServerLevel serverLevel) entity.spawnAtLocation(serverLevel, bannerItem, 0.6F);
		entity.removeAttached(TTAttachmentTypes.BOAT_BANNER);
		entity.gameEvent(GameEvent.ENTITY_INTERACT, player);
		info.setReturnValue(InteractionResult.SUCCESS);
	}
}
