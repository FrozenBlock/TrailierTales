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

import java.util.function.Consumer;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	@Inject(
		method = "addDetailsToTooltip",
		at = @At("HEAD")
	)
	private void trailierTales$addCoffinDetails(
		Item.TooltipContext context,
		TooltipDisplay display,
		@Nullable Player player,
		TooltipFlag tooltipFlag,
		Consumer<Component> builder,
		CallbackInfo info
	) {
		ItemStack self = ItemStack.class.cast(this);
		if (self.is(TTBlocks.COFFIN.get().asItem()) && display.shows(DataComponents.BLOCK_ENTITY_DATA)) {
			TypedEntityData<BlockEntityType<?>> blockEntityData = self.get(DataComponents.BLOCK_ENTITY_DATA);
			Spawner.appendHoverText(blockEntityData, builder, "SpawnData");
		}
	}
}
