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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.lib.platform.api.attachment.DataAttachmentSyncPredicate;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentType;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.minecraft.world.item.ItemStack;

public final class TTAttachmentTypes {
	public static final DataAttachmentType<ItemStack> BOAT_BANNER = DataAttachmentType.create(
		TTConstants.id("boat_banner"),
		builder -> {
			builder.persistent(ItemStack.CODEC);
			builder.syncWith(ItemStack.STREAM_CODEC, DataAttachmentSyncPredicate.all());
			builder.initializer(() -> ItemStack.EMPTY);
		}
	);
	public static final DataAttachmentType<EntityCoffinData> ENTITY_COFFIN_DATA = DataAttachmentType.create(
		TTConstants.id("entity_coffin_data"),
		builder -> {
			builder.persistent(EntityCoffinData.CODEC);
		}
	);
	public static final DataAttachmentType<ItemStack> FALLING_BLOCK_ITEM = DataAttachmentType.create(
		TTConstants.id("falling_block_item"),
		builder -> {
			builder.persistent(ItemStack.CODEC);
		}
	);

	public static void init() {}
}
